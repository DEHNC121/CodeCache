import html from './background.html'

const HOST = "http://52.236.88.159:443"

export function html_inject() {
    return html;
}

export const get_answers = async (query: string): Promise<Array<object>> => {
    //return ["hello","bye",query];
    const ans = await fetch("http://52.236.88.159:443/search?q=" + query);
    const json = await ans.json();
    return json.answers;

   // return [{"question": "question???", "text": "int a;\ncout<<a<<endl;\nreturn 1;"},
    //    {"question": "what?", "text": "that"}];
};

export const add = () => {

};



//52.236.88.159:443/note?q=tekstdowyszukania
//52.236.88.159:443/note/[int to string]