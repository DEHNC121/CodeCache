"use strict";
// const remove = () => {
//
// }
//
// const set_remove_button = (button: HTMLButtonElement) => {
//     button.onclick(remove);
// }
Object.defineProperty(exports, "__esModule", { value: true });
exports.insert_answers = void 0;
var insert_answers = function (answs, html_back) {
    var foot = document.getElementById("CodeCache-foot");
    for (var a in answs) {
        //html_back.insertAdjacentHTML("afterbegin", answer);
        var div = document.createElement('div');
        div.className = "ans-CodeCache";
        var text = document.createElement('div');
        text.className = "ans-text-CodeCache";
        text.textContent = answs[a].text;
        div.appendChild(text);
        var button = document.createElement('button');
        button.className = "remove-button-CodeCache";
        button.textContent = "remove";
        div.appendChild(button);
        //html_back.appendChild(div);
        html_back.insertBefore(div, foot);
    }
};
exports.insert_answers = insert_answers;
//# sourceMappingURL=display_setting.js.map