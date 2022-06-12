import new_ans_html from './new_ans.html';
import {add_ans, get_answers, get_query, remove_ans} from './rest';
import {text} from "stream/consumers";

const update_ans = (answs: Array<any>, originalQuery: string) => {
    const ans = document.querySelectorAll('.ans-CodeCache');

    ans.forEach(a => {
        a.remove();
    });

    insert_answers(originalQuery, answs, document.getElementById("CodeCache-background"));
}

const set_remove_button = (button: HTMLButtonElement, originalQuery: string, query: string, qId: string, ans: string, aId: string) => {

    button.addEventListener("click", async e => {

        let answers = await remove_ans(originalQuery, query, qId, ans, aId);

        update_ans(answers, originalQuery);
    });
}

export const set_save_button = (parent_div: HTMLElement,  query: string) => {
    let button = document.getElementById("save-button-CodeCache") as HTMLButtonElement;
    //TODO: the possibility to change the question
    
    button.addEventListener("click", async e => {

        /* REST */
        let textfield = document.getElementById("CodeCache-textfield") as HTMLTextAreaElement;
        let answers = await add_ans(query, query, textfield.value);

        /* CLEANUP */
        parent_div.remove();

        update_ans(answers, query);

        let add = document.getElementById("add-button-CodeCache") as HTMLButtonElement;
        add.disabled = false;
    });
}

export const set_add_button = (html_back: HTMLElement, query: string) => {
    let button = document.getElementById("add-button-CodeCache") as HTMLButtonElement;


    button.addEventListener("click", e => {
        let foot = document.getElementById("CodeCache-foot");
        let foot_inner = document.getElementById("CodeCache-inner-foot");
        let div = document.createElement('div');
        div.id = "add-ans-outer-div-CodeCache";
        div.insertAdjacentHTML("afterbegin", new_ans_html);

        foot.insertBefore(div, foot_inner);

        button.disabled = true;

        set_save_button(div, query);
    });
}

export const insert_answers = (originalQuery: string, answs: Array<any>, html_back: HTMLElement) => {

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
        set_remove_button(button, originalQuery, answs[a].question, answs[a].questionId, answs[a].text, answs[a].textId);
        div.appendChild(button);

        //html_back.appendChild(div);
        html_back.insertBefore(div, foot)
    }
}

