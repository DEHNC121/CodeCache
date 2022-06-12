import html from './background.html'


// @ts-ignore
const HOSTpromise = browser.storage.sync.get({host: "52.236.88.159:443"}) //"http://52.236.88.159:443"
HOSTpromise.then(console.log, console.log);


export function html_inject() {
    return html;
}

export const get_query = (): string => {
    const url = new URL(document.URL);
    return url.searchParams.get('q');
}

export const get_answers = async (query: string): Promise<Array<object>> => {
    return HOSTpromise.then( async res => {
        let HOST = res.host;
        console.log(HOST);

        const ans = await fetch("http://" + HOST + "/search?q=" + query);
        const json = await ans.json();
        return json.answers;
    }).catch(err => console.log(err));
};



export const add_ans = async (query: string, ans : string): Promise<Array<object>> => {
    console.log("ADD " + JSON.stringify({"question": query, "text": ans}));

    return HOSTpromise.then( async res => {
        let HOST = res.host;
        console.log(HOST);

        const response = await fetch("http://" + HOST + "/add", {
                method: 'POST',
                body: JSON.stringify({"question": query, "text": ans})//.replace(/\\n/g,"\\n")
            }
        );
        let json = await response.json();
        return json.answers;
    }).catch(err => console.log(err));
    //TODO: error???
};

export const remove_ans = async(query: string, qId: string, ans: string, aId: string): Promise<Array<object>> => {
    console.log("REMOVE " + JSON.stringify({"question": query, "questionId" : qId, "text": ans, "textId": aId}));

    // POST + /delete
    return HOSTpromise.then( async res => {
        let HOST = res.host;
        console.log(HOST);

        const response = await fetch("http://" + HOST + "/delete", {
                method: 'POST',
                body: JSON.stringify({"question": query, "questionId" : qId, "text": ans, "textId": aId})
            }
        );
        let json = await response.json();
        return json.answers;
    }).catch(err => console.log(err));
};


// return [{"question": "question???", "text": "int a;\ncout<<a<<endl;\nreturn 1;"},
//    {"question": "what?", "text": "that"}];