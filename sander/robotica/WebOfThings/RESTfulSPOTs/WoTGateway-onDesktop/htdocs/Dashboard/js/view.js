// Assumptions:
// deviceObj is an external array of device objects
var activePageCount = -1;
var currentPage = -1;
var ourDiv = null;
// This must be set before including tabber.js
var tabberOptions = {

  /* Optional: instead of letting tabber run during the onload event,
     we'll start it up manually. This can be useful because the onload
     even runs after all the images have finished loading, and we can
     run tabber at the bottom of our page to start it up faster. See the
     bottom of this page for more info. Note: this variable must be set
     BEFORE you include tabber.js.
  */
  'manualStartup':true,

  /* Optional: code to run after each tabber object has initialized */

  'onLoad': function(argsObj) {
    /* Display an alert only after tab2 */
    //if (argsObj.tabber.id == 'tab2') {
    //  alert('Finished loading tab2!');
    //}
  },

  /* Optional: code to run when the user clicks a tab. If this
     function returns boolean false then the tab will not be changed
     (the click is canceled). If you do not return a value or return
     something that is not boolean false, */

  'onClick': function(argsObj) {

    var t = argsObj.tabber; /* Tabber object */
    var id = t.id; /* ID of the main tabber DIV */
    var i = argsObj.index; /* Which tab was clicked (0 is the first tab) */
    var e = argsObj.event; /* Event object */

    
    currentPage = i;
    //alert('Switching to page ' + i + "title " + t.tabs[i].headingText);
    return true;
    //if (id == 'tab2') {
    //  return confirm('Swtich to '+t.tabs[i].headingText+'?\nEvent type: '+e.type);
    //}
    //return false;
  },

  /* Optional: set an ID for each tab navigation link */
  'addLinkId': true

};


function initView(div) {
    var content = "";
    ourDiv = div;
    for (var i = 0; i < NUMBER_OF_PAGES; i++) {
        content += "<div class='tabbertab' title='Page " + (i + 1) + "'>\n" +
        "<p><div id='tab" + i + "'>";
        // Insert a table on each page
        content += "<table border='0'>";
        for (var j = 0; j < ROWS_PER_PAGE; j++) {
            content += "<tr>";
            for (var k = 0; k < COLS_PER_PAGE; k++) {
                content += "<td>" +
                    "<center>" +
                    "<div id='id-" + i + "-" + j + "-" + k + "'></div>" + //<br/>" +
                    "<div id='dev-" + i + "-" + j + "-" + k + "'></div>"+
                    "</center>" +
                    "</td>";
            }
            content += "</tr>\n";
        }
        content += "</table>\n";
        content += "</div></p>\n" +
    "</div>\n";
    }
    //alert("View content\n" + content);
    ourDiv.innerHTML = content;
    // XXX: do we need to rest currentPage?

    // Create the tabs ...
    tabberAutomatic(tabberOptions);
    //alert(getCompactCurrentTimestamp() + "Initialized view");
}

function updateView() {
    var page = 0;
    var row = 0;
    var col = 0;
    var cnt = deviceObj.length;
    var inactiveDeviceCnt = 0;
    var now = new Date();

    if (!AUTO_REFRESH) return;
    if (cnt > TOTAL_DEVICES_VIEWABLE) {
        cnt = TOTAL_DEVICES_VIEWABLE;
        alert("Displaying only the first " + cnt + " devices.\n");       
    }

    for (var ii = 0; ii < deviceObj.length; ii++) {
        if (deviceObj[ii].lastHeard < now.getTime() - DELAY_THRESHOLD) {
            inactiveDeviceCnt++;
        }
    }

    // If we deleted any items, we should clean out all the tiles ...
    for (var p = 0; p < NUMBER_OF_PAGES; p++) {
        for (var q = 0; q < ROWS_PER_PAGE; q++) {
            for (var r = 0; r < COLS_PER_PAGE; r++) {
                document.getElementById("id-" + p + "-" + q + "-" + r).innerHTML = "";
                document.getElementById("dev-" + p + "-" + q + "-" + r).innerHTML = "";
            }
        }
    }
    
    // Split the list across the various tabs
    for (var i = 0; i < cnt; i++) {
        page = Math.floor((i/DEVICES_PER_PAGE));
        row = Math.floor((i % DEVICES_PER_PAGE)/COLS_PER_PAGE);
        col = (i % COLS_PER_PAGE);        
//        alert("Device " + i +
//            " goes on <" + page + "," + row + "," + col + ">");
        document.getElementById("id-" + page + "-" + row + "-" + col).
            innerHTML = "<ul id=\"sddm\"><li><a href=\"#\" onmouseover=\"mopen('" +
                deviceObj[i].id.substring(5) + "')\" onmouseout=\"mclosetime()\">" +
                deviceObj[i].id.substring(5) + "</a>" +
                "<div id=\"" + deviceObj[i].id.substring(5) + "\" onmouseover=\"" +
                "mcancelclosetime()\" onmouseout=\"mclosetime()\">" +
                "<a href=\"#\" onclick=\"blink('" + deviceObj[i].id + "')\">Blink</a>" +
                "<a href=\"#\" onclick=\"changeColor('" + deviceObj[i].id + "')\">Change color ...</a>" +
                "<a href=\"#\" onclick=\"changeName('" + deviceObj[i].id + "')\">Change name ...</a>" +
                "<a href=\"#\" onclick=\"makeEndNode('" + deviceObj[i].id + "')\">Make endnode ...</a>" +
                "<a href=\"#\" onclick=\"resetCounters('" + deviceObj[i].id + "')\">Reset counters ...</a>" +
                "<a href=\"#\" onclick=\"removeDevice('" + deviceObj[i].id + "')\">Remove ...</a>" +
                "</div>" + "</li></ul>";
//        alert("Drawing tile " + "dev-" + page + "-" + row + "-" + col);
        drawTile(deviceObj[i],
            document.getElementById("dev-" + page + "-" + row + "-" + col));
    }
    document.getElementById("devicecnt").innerHTML = deviceObj.length;
    document.getElementById("activedevicecnt").innerHTML = deviceObj.length - inactiveDeviceCnt;
    document.getElementById("updatetime").innerHTML = getCompactCurrentTimestamp();
    activePageCount = Math.ceil(cnt / DEVICES_PER_PAGE);
    if (AUTO_SCROLL) {
        currentPage = (currentPage + 1) % activePageCount;
        // We assume that initView has already been called before
        if (ourDiv != null) ourDiv.tabber.tabShow(currentPage);
    }
    //alert(getCompactCurrentTimestamp() + "Updated view to page " + currentPage);
 }


function resetCounters(devId) {
    var device = findDevice(deviceObj, devId);

    if (device == null) {
        updateStatus("Did not find device " + devId);
    } else {
        var resp = confirm("This will reset successful/total request counts for " + devId + ". Continue?");
        if (resp) {
            device.successfulRequests = 0;
            device.failedRequests = 0;
            updateStatus("Counters reset for " + devId);
        }
    }
}

function removeDevice(devId) {
    var resp = confirm("This will remove " + devId +
        " from the display\n until it is rediscovered. Continue?");
    
    if (resp) {
        for (var i = 0; i < deviceObj.length; i++) {
            if (deviceObj[i].id == devId) {
                deviceObj.splice(i, 1);
                updateStatus("Device " + devId + " removed.");
                return;
            }
        }
    }
}