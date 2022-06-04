import {get_answers, html_inject, get_query} from "./rest";
import {insert_answers, set_add_button} from "./display_setting";

// inject html
const parent_name = "rso";
document.getElementById(parent_name).insertAdjacentHTML("afterbegin", html_inject());

// get query parameters
let query_params = get_query()
console.log(query_params);

// set button behaviour
set_add_button(document.getElementById("CodeCache-background"), query_params);

//REST GET answers
get_answers(query_params).then(response => {
    console.log(response);
    insert_answers(response, document.getElementById("CodeCache-background"));
});


