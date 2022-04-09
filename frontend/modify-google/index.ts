const parent_name = "appbar";
const next_sibling = "extbar";

// ---------- PANEL ----------

let panel=document.createElement("div");
panel.id = "CodeCache-addon";
panel.className = "panel-CodeCache";

let appbar = document.getElementById(parent_name)
let num_results = document.getElementById(next_sibling)
appbar.insertBefore(panel, num_results);

// ---------- QUERY FIELD ----------

let query = document.createElement("div");
query.className = "query-CodeCache";

const url = new URL(document.URL)
let query_params = url.searchParams.get('q');

query.textContent = query_params;

panel.appendChild(query);

// ---------- TEXT FIELD ----------

let textf = document.createElement("textarea")
textf.className = "textfield-CodeCache";

panel.appendChild(textf);

// ---------- SAVE BUTTON ----------

let saveButton = document.createElement("button");
saveButton.className = "save-button-CodeCache";
saveButton.textContent = "save";

panel.appendChild(saveButton);

