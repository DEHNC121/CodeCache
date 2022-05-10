import new_ans_html from './new_ans.html';

import {add} from './rest';
// const remove = () => {
//
// }
//
// const set_remove_button = (button: HTMLButtonElement) => {
//     button.onclick(remove);
// }

export const set_save_button = (parent_div: HTMLElement) => {
    let button = document.getElementById("save-button-CodeCache") as HTMLButtonElement;

    button.addEventListener("click", e => {
        //TODO: REST
        parent_div.remove();

        let add = document.getElementById("add-button-CodeCache") as HTMLButtonElement;
        add.disabled = false;
    });
}

export const set_add_button = (html_back: HTMLElement) => {
    let button = document.getElementById("add-button-CodeCache") as HTMLButtonElement;

    button.addEventListener("click", e => {
        let foot = document.getElementById("CodeCache-foot");
        let div = document.createElement('div');
        div.id = "add-ans-outer-div-CodeCache";
        div.insertAdjacentHTML("afterbegin", new_ans_html);

        html_back.insertBefore(div, foot);

        button.disabled = true;

        set_save_button(div);
    });
}

export const insert_answers = (answs: Array<any>, html_back: HTMLElement) => {

    let foot = document.getElementById("CodeCache-foot")

    for (let a in answs) {
        //html_back.insertAdjacentHTML("afterbegin", answer);
        let div = document.createElement('div');
        div.className = "ans-CodeCache";

        let inn_div = document.createElement('div');
        inn_div.className = "ans-inn-CodeCache";

        let question = document.createElement('div');
        question.className = "question-text-CodeCache";
        question.textContent = answs[a].question;
        inn_div.appendChild(question);

        let text = document.createElement('div');
        text.className = "ans-text-CodeCache";
        text.textContent = answs[a].text;
        inn_div.appendChild(text);

        div.appendChild(inn_div);

        let button = document.createElement('button');
        button.className = "remove-button-CodeCache";
        button.textContent = "remove";
        div.appendChild(button)

        //html_back.appendChild(div);
        html_back.insertBefore(div, foot)
    }
}

