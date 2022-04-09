var parent_name = "appbar";
var next_sibling = "extbar";
// ---------- PANEL ----------
var panel = document.createElement("div");
panel.id = "CodeCache-addon";
panel.className = "panel-CodeCache";
var appbar = document.getElementById(parent_name);
var num_results = document.getElementById(next_sibling);
appbar.insertBefore(panel, num_results);
// ---------- QUERY FIELD ----------
var query = document.createElement("div");
query.className = "query-CodeCache";
var url = new URL(document.URL);
var query_params = url.searchParams.get('q');
query.textContent = query_params;
panel.appendChild(query);
// ---------- TEXT FIELD ----------
var textf = document.createElement("textarea");
textf.className = "textfield-CodeCache";
panel.appendChild(textf);
// ---------- SAVE BUTTON ----------
var saveButton = document.createElement("button");
saveButton.className = "save-button-CodeCache";
saveButton.textContent = "save";
panel.appendChild(saveButton);
//# sourceMappingURL=index.js.map