import html from './prompt.html'

export function html_inject(){
    return html;
}

export const get_answers = (query: string): Array<string> => {
    return ["hello","bye",query];
};

//52.236.88.159:443/note?q=tekstdowyszukania
//52.236.88.159:443/note/[int to string]