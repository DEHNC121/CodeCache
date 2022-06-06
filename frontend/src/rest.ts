import html from './background.html'


// @ts-ignore
const HOST = browser.storage.sync.get({host: "52.236.88.159:443"}) //"http://52.236.88.159:443"
HOST.then(console.log, console.log);

export function html_inject() {
    return html;
}

export const get_query = (): string => {
    const url = new URL(document.URL);
    return url.searchParams.get('q');
}

export const get_answers = async (query: string): Promise<Array<object>> => {

    const ans = await fetch("http://" + HOST + "/search?q=" + query);
    const json = await ans.json();
    return json.answers;

   // return [{"question": "question???", "text": "int a;\ncout<<a<<endl;\nreturn 1;"},
    //    {"question": "what?", "text": "that"}];
};

export const add_ans = async (query: string, ans : string): Promise<Array<object>> => {
    console.log("ADD " + ans);
    const response = await fetch("http://" + HOST + "/add", {
            method: 'POST',
            body: JSON.stringify({"question": query, "text": ans})
        }
    );
    let json = await response.json();
    return json.answers;

    //TODO: error???
};

export const remove_ans = async(query: string, ans : string): Promise<Array<object>> => {
    console.log("REMOVE " + ans);
    // TODO: POST + /delete
    return [{"question": "question???", "text": "int a;\ncout<<a<<endl;\nreturn 1;"},
        {"question": "what?", "text": "that"}];
};



//52.236.88.159:443/note?q=tekstdowyszukania
//52.236.88.159:443/note/[int to string]