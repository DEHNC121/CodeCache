import {get_answers, html_inject} from "./rest";

const parent_name = "appbar";


let html = "<div class=\"panel-CodeCache\" id=\"CodeCache-addon\">\n" +
    "    <div class=\"query-CodeCache\" id=\"codecache-query-field\"></div>\n" +
    "    <label>\n" +
    "        <textarea class=\"textfield-CodeCache\"></textarea>\n" +
    "    </label>\n" +
    "    <button class=\"save-button-CodeCache\">save</button>\n" +
    "</div>\n"

console.log(html_inject());

document.getElementById(parent_name).insertAdjacentHTML("afterbegin", html_inject());


let query = document.getElementById("CodeCache-query-field");
const url = new URL(document.URL);
let query_params = url.searchParams.get('q');

console.log(query_params);
query.textContent = query_params;


console.log(get_answers(query_params));

