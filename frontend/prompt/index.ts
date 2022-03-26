var myWindowId;
const contentBox = document.querySelector("#content");
const promptBox = document.querySelector("#prompt");
const saveButton = document.getElementById("save");


/* set mouse movement events */
contentBox.addEventListener("mouseover", () => {
    contentBox.setAttribute("contenteditable", "true");
});
contentBox.addEventListener("mouseout", () => {
    contentBox.setAttribute("contenteditable", "false");
});

promptBox.addEventListener("mouseover", () => {
    promptBox.setAttribute("contenteditable", "true");
});
promptBox.addEventListener("mouseout", () => {
    promptBox.setAttribute("contenteditable", "false");
});

/* set save button action */
saveButton.addEventListener("click", () => {
    browser.tabs.query({windowId: myWindowId, active: true}).then((tabs) => {
        let contentToStore = {};
        contentToStore[tabs[0].url] = contentBox.textContent;
        browser.storage.local.set(contentToStore).then(() => {}, () => contentBox.textContent = "set error");
    });
});

/*
Update the sidebar's content.
1) Get the active tab in this sidebar's window.
2) Get its stored content.
3) Put it in the content box.
*/
function updateContent() {
    browser.tabs.query({windowId: myWindowId, active: true})
        .then((tabs) => {
            return browser.storage.local.get(tabs[0].url);
        }, () => {contentBox.textContent = "updateContext query fail"; return "myNullUrl"})
        .then((storedInfo) => {
            contentBox.textContent = storedInfo[Object.keys(storedInfo)[0]].toLocaleString();
        }, () => {contentBox.textContent = "updateContext storage.local.get fail";} )
        .then(() => {}, () => contentBox.textContent = "");
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
browser.windows.getCurrent({populate: true}).then((windowInfo) => {
    myWindowId = windowInfo.id;
    updateContent();
});
