### Changing the server address
- go to about:addons
- navigate to _Options_ for CodeCache addon
- the rest is self-explanatory



### Frontend
One can create their own frontend. It needn't even be an addon :)
The server responds to all the following, with a json containing top 5 answers for a given query.
Json example:
``` json
{
"answers" : [
{ "question": "czemu", "questionId": 158, "text": "jeszcze nie", "textId": 8 },
{ "question": "czemu nei", "questionId": 153, "text": "2", "textId": 2 },
{ "question": "czemu tak", "questionId": 155, "text": "4", "textId": 4 },
{ "question": "czemu am", "questionId": 157, "text": "6", "textId": 6 },
{ "question": "czemu tak jest", "questionId": 152, "text": "1", "textId": 1 }
]
}
```

- getting answers: "http://" + HOST + "/search?q=" + query
- adding an answer: "http://" + HOST + "/add", {
                                    method: 'POST',
                                    body: JSON.stringify({"question": query, "text": ans})
                                }
- removing an answer: "http://" + HOST + "/delete", {
                                      method: 'POST',
                                      body: JSON.stringify({"question": query, "questionId" : qId, "text": ans, "textId": aId})
                                  }
