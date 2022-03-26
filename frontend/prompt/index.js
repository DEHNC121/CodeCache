var myWindowId;
var contentBox = document.querySelector("#content");
var promptBox = document.querySelector("#prompt");
var saveButton = document.getElementById("save");
/* set mouse movement events */
contentBox.addEventListener("mouseover", function () {
    contentBox.setAttribute("contenteditable", "true");
});
contentBox.addEventListener("mouseout", function () {
    contentBox.setAttribute("contenteditable", "false");
});
promptBox.addEventListener("mouseover", function () {
    promptBox.setAttribute("contenteditable", "true");
});
promptBox.addEventListener("mouseout", function () {
    promptBox.setAttribute("contenteditable", "false");
});
/* set save button action */
saveButton.addEventListener("click", function () {
    browser.tabs.query({ windowId: myWindowId, active: true }).then(function (tabs) {
        var contentToStore = {};
        contentToStore[tabs[0].url] = contentBox.textContent;
        browser.storage.local.set(contentToStore).then(function () { }, function () { return contentBox.textContent = "set error"; });
    });
});
/*
Update the sidebar's content.
1) Get the active tab in this sidebar's window.
2) Get its stored content.
3) Put it in the content box.
*/
function updateContent() {
    browser.tabs.query({ windowId: myWindowId, active: true })
        .then(function (tabs) {
        return browser.storage.local.get(tabs[0].url);
    }, function () { contentBox.textContent = "updateContext query fail"; return "myNullUrl"; })
        .then(function (storedInfo) {
        contentBox.textContent = storedInfo[Object.keys(storedInfo)[0]].toLocaleString();
    }, function () { contentBox.textContent = "updateContext storage.local.get fail"; })
        .then(function () { }, function () { return contentBox.textContent = ""; });
}
/*
Update content when a new tab becomes active.
*/
browser.tabs.onActivated.addListener(updateContent);
/*
Update content when a new page is loaded into a tab.
*/
browser.tabs.onUpdated.addListener(updateContent);
/*
When the sidebar loads, get the ID of its window and update its content.
*/
browser.windows.getCurrent({ populate: true }).then(function (windowInfo) {
    myWindowId = windowInfo.id;
    updateContent();
});
//# sourceMappingURL=index.js.map