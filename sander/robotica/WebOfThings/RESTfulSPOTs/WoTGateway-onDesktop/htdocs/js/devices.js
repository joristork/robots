    var devURL = "/devices.json";
    var refreshPeriod = 60000; // 60 sec

    if (navigator.appName == "Microsoft Internet Explorer") {
        http = new ActiveXObject("Microsoft.XMLHTTP");
    } else {
        http = new XMLHttpRequest();
    }

    function makeDeviceList(devList) {
        var content = "";
        var d = new Date();

        if (devList.length > 0) {
            content += "<table border='1'>\n" + "<tr align='center'>" +
                "<th>" + "No." + "</th>" +
                "<th>" + "Id" + "</th>" +
                "<th>" + "Service addr" + "</th>" +
                "<th>" + "Last Heard/" + "<br/>" + "Expiry" + "</th>" +
//                "<th>" + "CheckIn" + "</th>" +
                "<th>" + "Status" + "</th>" +
                "<th>" + "Resource <br/> cache" + "</th>" +
                "<th>" + "Request <br/> queue" + "</th>" +
                "</tr>\n";

             for (var i = 0; i < devList.length; i++) {
                content += "<tr align='center'>" +
                    "<td>" + (i + 1) + "</td>" +
                    "<td>" + "<a href='/" + devList[i].id + "/.well-known/r'>" +
                        devList[i].id + "</a>" + "</td>" +
                    "<td>" + devList[i].address + "<br/>" +
                        (devList[i].compression ? "chttp" : "http") + " over " +
                        devList[i].protocol + "</td>" +
                    "<td>" + getDurationEstimate(d.getTime() -
                        devList[i].lastHeard) + "<br/>" +
                        "In " + millisToString(devList[i].expiry) + "</td>" +
//                    "<td>" + (devList[i].lastCheckIn == 0 ? " " :
//                        getDurationEstimate(d.getTime() -
//                        devList[i].lastCheckIn)) + "</td>" +
                    "<td>" + devList[i].status + "</td>";

                    if (devList[i].resCacheSummary == "empty") {
                        content += "<td>empty</td>";
                    } else {
                        content += "<td>" + "<a href='" +
                            devList[i].resCache + "'>" +
                            devList[i].resCacheSummary + "</a>" + "</td>"
                    }

                    if (devList[i].reqQueueSummary == "empty") {
                        content += "<td>empty</td>";
                    } else {
                        content += "<td>" + "<a href='" +
                            devList[i].reqQueue + "'>" +
                            devList[i].reqQueueSummary + "</a>" + "</td>";
                    }
             }

        }

        content += "</table>"
        //alert(content);
        return content;

    }

    function showDevices() {
        document.getElementById("checking").innerHTML = "Checking ...";
        //alert("hello");
        //var JSONdata = $.ajax({ type: "GET", url: devURL, async: false }).responseText;
        http.open("GET", devURL); //gethealth.json or getsitehealth
        http.onreadystatechange=function() {
            if (http.readyState == 4) {
                var list = eval("(" + http.responseText + ")");
                document.getElementById("checking").innerHTML = "[Updated: " +
                    getCompactCurrentTimestamp() + ", once every " +
                    (refreshPeriod/1000) + "s]";
                document.getElementById("devlistsize").innerHTML = list.length;
                document.getElementById("devlist").innerHTML = makeDeviceList(list);
            }
        }
        http.send(null);

        setTimeout("showDevices()", refreshPeriod);
    }

