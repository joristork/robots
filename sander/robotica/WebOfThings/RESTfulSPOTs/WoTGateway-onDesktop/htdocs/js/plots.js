    var devURL = "/spots";
    var refreshPeriod = 5000; // 30 sec

    if (navigator.appName == "Microsoft Internet Explorer") {
        http = new ActiveXObject("Microsoft.XMLHTTP");
    } else {
        http = new XMLHttpRequest();
    }

    var valueNames = [
        "Light" // , "Memory" //, "Temp"
    ];

    var valueURLs = [
        "l"// "m/free"  "t",
    ];

    function makePlotsTable(devList) {
        var content = "";
        
        if (devList.length > 0) {
            content += "<table>\n" + "<tr align='center'>" +
                "<th>" + "Device" + "</th>";
            
            for (var j = 0; j < valueNames.length; j++) {
                content += "<th>" + valueNames[j] + "</th>";
            }
            
            content += "</tr>\n";

            for (var i = 0; i < devList.length; i++) {
                content += "<tr>" + "<td>" + devList[i] + "</td>";
                for (j = 0; j < valueNames.length; j++) {
                    content += "<td>" + "<div id=\"" + devList[i] + "-" +
                        valueNames[j] + "\">" + "</td>";
                }

                content += "</tr>\n";
            }

            content += "</table>";
        }

        return content;
    }

    function updatePlot(deviceId, valueName, valueURL) {
        document.getElementById(deviceId + "-" + valueName).innerHTML =
            valueName + " updated at " + getCompactCurrentTimestamp();

        var lhttp;
        if (navigator.appName == "Microsoft Internet Explorer") {
            lhttp = new ActiveXObject("Microsoft.XMLHTTP");
        } else {
            lhttp = new XMLHttpRequest();
        }

        document.getElementById(deviceId + "-" + valueName).innerHTML =
            "Checking ...";
        lhttp.open("GET", "/" + deviceId + "/" + valueURL);
        lhttp.onreadystatechange=function() {
            if (lhttp.readyState == 4) {
                document.getElementById(deviceId + "-" + valueName).innerHTML =
                    lhttp.responseText;
            }
        }
        lhttp.send(null);
    }

    function drawPlots(devList) {
        for (var i = 0; i < devList.length; i++) {
            for (var j = 0; j < valueNames.length; j++) {
                updatePlot(devList[i], valueNames[j], valueURLs[j]);
            }
        }
        //setTimeout("drawPlots(devList)", refreshPeriod);
    }

    function showPlots() {
        //alert("Making plots");
        document.getElementById("checking").innerHTML = "Checking ...";
        http.open("GET", devURL); 
        http.onreadystatechange=function() {
            if (http.readyState == 4) {
                var list = eval("(" + http.responseText + ")");
                document.getElementById("checking").innerHTML = "[Updated: " +
                    getCompactCurrentTimestamp() + "]";
                document.getElementById("devlistsize").innerHTML = list.length;
                document.getElementById("plots").innerHTML = makePlotsTable(list);
                drawPlots(list);
            }
        }
        http.send(null);

        setTimeout("showPlots()", refreshPeriod);
    }

