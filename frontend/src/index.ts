import {get_answers, html_inject} from "./rest";
import {insert_answers, set_add_button} from "./display_setting";

// inject html
const parent_name = "rso";
document.getElementById(parent_name).insertAdjacentHTML("afterbegin", html_inject());

set_add_button(document.getElementById("CodeCache-background"));
// get query parameters
const url = new URL(document.URL);
let query_params = url.searchParams.get('q');
console.log(query_params);

//REST API communication
get_answers(query_params).then(response => {
    console.log(response);
    insert_answers(response, document.getElementById("CodeCache-background"));
});


