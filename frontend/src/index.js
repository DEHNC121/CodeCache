var parent_name = "appbar";
//import {get_answers} from "./rest";
//import * as rest from "./rest";
var html = "<div class=\"panel-CodeCache\" id=\"CodeCache-addon\">\n" +
    "    <div class=\"query-CodeCache\" id=\"codecache-query-field\"></div>\n" +
    "    <label>\n" +
    "        <textarea class=\"textfield-CodeCache\"></textarea>\n" +
    "    </label>\n" +
    "    <button class=\"save-button-CodeCache\">save</button>\n" +
    "</div>\n";
document.getElementById(parent_name).insertAdjacentHTML("afterbegin", html);
var query = document.getElementById("codecache-query-field");
var url = new URL(document.URL);
var query_params = url.searchParams.get('q');
query.textContent = query_params;
console.log('hello');
//console.log(rest.get_answers(query_params));
console.log("bye");
//# sourceMappingURL=index.js.map