import html from './background.html'

const HOST = "http://52.236.88.159:443"

export function html_inject() {
    return html;
}

export const get_answers = async (query: string): Promise<Array<object>> => {
    //return ["hello","bye",query];
    const ans = await fetch("http://52.236.88.159:443/note/int%20to%20string");

    const json = await ans.json();

    return json.answers;
};

export const insert_answers = (answs: Array<any>, html_back: HTMLElement) => {

    let save = document.getElementById("add-button")

    for (let a in answs) {
        //html_back.insertAdjacentHTML("afterbegin", answer);
        let div = document.createElement('div');
        div.className = "ans-CodeCache";

        let text = document.createElement('div');
        text.className = "ans-text-CodeCache";
        text.textContent = answs[a].text;
        div.appendChild(text);

        let button = document.createElement('button');
        button.className = "remove-button-CodeCache";
        button.textContent = "remove";
        div.appendChild(button)

        //html_back.appendChild(div);
        html_back.insertBefore(div, save)
    }
}

//52.236.88.159:443/note?q=tekstdowyszukania
//52.236.88.159:443/note/[int to string]