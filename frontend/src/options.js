// document.getElementById("CodeCacheOptionsButton").textContent = ":((";

function saveOptions(e) {
    e.preventDefault();
    console.log("save?");
    browser.storage.sync.set({
        host: document.querySelector("#host").value
    });
    document.getElementById("CodeCacheOptionsButton").textContent = ("SAVE"); //+ document.querySelector("#host").value);
}

function restoreOptions() {

    function setCurrentChoice(result) {
        document.querySelector("#host").value = result.color || "52.236.88.159:443";
    }

    function onError(error) {
        console.log(`Error: ${error}`);
    }

    let getting = browser.storage.sync.get("host");
    getting.then(setCurrentChoice, onError);
}

document.addEventListener("DOMContentLoaded", restoreOptions);
document.querySelector("form").addEventListener("submit", saveOptions);