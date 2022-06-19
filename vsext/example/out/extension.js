"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.deactivate = exports.activate = void 0;
const vscode = require("vscode");
const node_fetch_1 = require("node-fetch");
// this method is called when your extension is activated
// your extension is activated the very first time the command is executed
function activate(context) {
    // Use the console to output diagnostic information (console.log) and errors (console.error)
    // This line of code will only be executed once when your extension is activated
    console.log('Congratulations, your extension "example" is now active!');
    // The command has been defined in the package.json file
    // Now provide the implementation of the command with registerCommand
    // The commandId parameter must match the command field in package.json
    let disposable = vscode.commands.registerCommand("example.search", async () => {
        // The code you place here will be executed every time your command is executed
        // Display a message box to the user
        const editor = vscode.window.activeTextEditor;
        if (!editor) {
            vscode.window.showInformationMessage("editor does not exist");
            return;
        }
        let hostIp = context.globalState.get("hostIp");
        if (hostIp === undefined) {
            hostIp = "52.236.88.159:443";
        }
        const text = editor.document.getText(editor.selection);
        // vscode.window.showInformationMessage(`selected text: ${text}`);
        const response = await (0, node_fetch_1.default)(`http://${hostIp}/search?q=${text.replace(" ", "+")}`);
        const datajson = await response.json();
        const data = datajson.answers;
        console.log(data);
        const quickPick = vscode.window.createQuickPick();
        quickPick.items = data.map((x) => ({ detail: x.question, label: x.text }));
        quickPick.onDidChangeSelection(([item]) => {
            if (item) {
                // vscode.window.showInformationMessage(item.label);
                editor.edit(edit => {
                    edit.replace(editor.selection, item.label);
                });
                quickPick.dispose();
            }
        });
        quickPick.onDidHide(() => quickPick.dispose());
        quickPick.show();
    });
    context.subscriptions.push(disposable);
    let command2 = vscode.commands.registerCommand("example.updatehost", async () => {
        // The code you place here will be executed every time your command is executed
        // Display a message box to the user
        const editor = vscode.window.activeTextEditor;
        if (!editor) {
            vscode.window.showInformationMessage("editor does not exist");
            return;
        }
        const inputBox = vscode.window.createInputBox();
        inputBox.onDidAccept(() => {
            context.globalState.update("hostIp", inputBox.value);
            vscode.window.showInformationMessage(`Host changed to ${inputBox.value}`);
            inputBox.dispose();
        });
        inputBox.onDidHide(() => inputBox.dispose());
        inputBox.show();
    });
    let command3 = vscode.commands.registerCommand("example.add", async () => {
        // The code you place here will be executed every time your command is executed
        // Display a message box to the user
        const editor = vscode.window.activeTextEditor;
        if (!editor) {
            vscode.window.showInformationMessage("editor does not exist");
            return;
        }
        let hostIp = context.globalState.get("hostIp");
        if (hostIp === undefined) {
            hostIp = "52.236.88.159:443";
        }
        const text = editor.document.getText(editor.selection);
        const inputBox = vscode.window.createInputBox();
        inputBox.onDidAccept(async () => {
            const question = inputBox.value;
            const response = await (0, node_fetch_1.default)(`http://${hostIp}/add`, {
                method: 'POST',
                body: JSON.stringify({ "question": question, "text": text }) //.replace(/\\n/g,"\\n")
            });
            let json = await response.json();
            vscode.window.showInformationMessage(`Added answer to the question: ${question}`);
            inputBox.dispose();
        });
        inputBox.onDidHide(() => inputBox.dispose());
        inputBox.show();
    });
    //   let command4 = vscode.commands.registerCommand(
    // 	"example.updatehost",
    // 	async () => {
    // 	  // The code you place here will be executed every time your command is executed
    // 	  // Display a message box to the user
    // 	  const editor = vscode.window.activeTextEditor;
    // 	  if (!editor) {
    // 		vscode.window.showInformationMessage("editor does not exist");
    // 		return;
    // 	  }
    // 	  const inputBox = vscode.window.createInputBox();
    // 	  inputBox.onDidAccept(async () => {
    // 		const question = inputBox.value;
    // 		let hostIp = context.globalState.get("hostIp");
    // 		if (hostIp === undefined) {
    // 				hostIp = "52.236.88.159:443";
    // 		}
    // 		const text = editor.document.getText(editor.selection);
    // 		// vscode.window.showInformationMessage(`selected text: ${text}`);
    // 		const response = await fetch(
    // 			`http://${hostIp}/search?q=${text.replace(" ", "+")}`
    // 		);
    // 		const datajson = await response.json();
    // 		const data = datajson.answers;
    // 		console.log(data);
    // 		const quickPick = vscode.window.createQuickPick();
    // 		quickPick.items = data.map((x: any) => ({ label: x.text, description: x.question}));
    // 		quickPick.onDidChangeSelection(([item]) => {
    // 			if (item) {
    // 			// vscode.window.showInformationMessage(item.label);
    // 			editor.edit(edit => {
    // 				edit.replace(editor.selection, item.label);
    // 			});
    // 			quickPick.dispose();
    // 			}
    // 		});
    // 		quickPick.onDidHide(() => quickPick.dispose());
    // 		quickPick.show();
    // 		inputBox.dispose();
    // 	  });
    // 	  inputBox.onDidHide(() => inputBox.dispose());
    // 	  inputBox.show();
    // 	}
    //   );
    context.subscriptions.push(disposable);
    context.subscriptions.push(command2);
    context.subscriptions.push(command3);
}
exports.activate = activate;
// this method is called when your extension is deactivated
function deactivate() { }
exports.deactivate = deactivate;
//# sourceMappingURL=extension.js.map