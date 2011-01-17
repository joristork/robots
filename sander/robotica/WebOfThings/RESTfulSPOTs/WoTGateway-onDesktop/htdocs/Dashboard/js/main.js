
function main() {
    document.getElementById("starttime").innerHTML = getCompactCurrentTimestamp();
    document.getElementById("updatetime").innerHTML = getCompactCurrentTimestamp();
//    document.getElementById("updateperiod").innerHTML = MODEL_REFRESH_PERIOD/1000;
    
    initModel();
    initView(document.getElementById("dashboard"));
    startModelUpdater();
    setTimeout("startViewUpdater()", INITIAL_VIEW_REFRESH_LAG);
}

function startModelUpdater() {
    updateModel();
    setTimeout("startModelUpdater()", MODEL_REFRESH_PERIOD);
}

function startViewUpdater() {
    var start = new Date();
    var end;

    updateView();
    end = new Date();
//    alert("View update took " + (end.getTime() - start.getTime()) + " ms.")
    setTimeout("startViewUpdater()", VIEW_REFRESH_PERIOD);
}
