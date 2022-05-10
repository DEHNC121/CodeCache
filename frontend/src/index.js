"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var rest_1 = require("./rest");
var display_setting_1 = require("./display_setting");
// inject html
var parent_name = "rso";
document.getElementById(parent_name).insertAdjacentHTML("afterbegin", (0, rest_1.html_inject)());
// get query parameters
var url = new URL(document.URL);
var query_params = url.searchParams.get('q');
console.log(query_params);
//REST API communication
(0, rest_1.get_answers)(query_params).then(function (response) {
    console.log(response);
    (0, display_setting_1.insert_answers)(response, document.getElementById("CodeCache-background"));
});
//# sourceMappingURL=index.js.map