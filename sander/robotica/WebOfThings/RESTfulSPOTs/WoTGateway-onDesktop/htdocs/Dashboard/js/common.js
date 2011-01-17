function dateToCompactTimestamp(d) {
    var ts = "";

    ts = d.getFullYear() + "/" + (d.getMonth() + 1) + "/" + d.getDate() + " " +
    d.getHours() + ":" + ((d.getMinutes() < 10) ? "0" : "") +
    d.getMinutes() + ":" + ((d.getSeconds() < 10) ? "0" : "") +
    d.getSeconds();

    return ts;
}

function millisToHHMMSS(millis) {
    var ts = "";
    var d = new Date();

    d.setTime(millis);
    ts = d.getHours() + ":" + 
        ((d.getMinutes() < 10) ? "0" : "") + d.getMinutes() + ":" +
        ((d.getSeconds() < 10) ? "0" : "") + d.getSeconds();

    return ts;
}

function getCompactTimestamp(millis) {
    var d = new Date();

    d.setTime(millis);
    return dateToCompactTimestamp(d);
}

function getCompactCurrentTimestamp() {
    var d = new Date();

    return dateToCompactTimestamp(d);
}

function millisToString(milliseconds) {
    var tmp = milliseconds/1000;
    var ret = "";

    if (tmp > 0x1e13380) {
        ret = ret + (tmp / 0x1e13380).toFixed(0) + " yr ";
        tmp %= 0x1e13380;
    }
    if (tmp > 0x15180) {
        ret = ret + (tmp / 0x15180).toFixed(0) + " day ";
        tmp %= 0x15180;
    }
    if (tmp > 3600) {
        ret = ret + (tmp / 3600).toFixed(0) + " hr ";
        tmp %= 3600;
    }
    if (tmp > 60) {
        ret = ret + (tmp / 60).toFixed(0) + " min ";
        tmp %= 60;
    }

    if (ret == "")
        ret = ret + tmp.toFixed(0) + " sec";

    return ret;
}

function getDurationEstimate(millis) {
    var tmp = millis/1000;
    var ret = "";

    if (tmp > 0x1e13380) {
        ret = (tmp / 0x1e13380).toFixed(0) + " yrs ";
    } else if (tmp > 0x15180) {
        ret = (tmp / 0x15180).toFixed(0) + " days ";
    } else if (tmp > 3600) {
        ret = (tmp / 3600).toFixed(0) + " hrs ";
    } else if (tmp > 60) {
        ret = (tmp / 60).toFixed(0) + " mins ";
    } else {
        ret = tmp.toFixed(0) + " sec ";
    }

    return (ret + "ago");
}