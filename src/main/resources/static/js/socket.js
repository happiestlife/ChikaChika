document.addEventListener("DOMContentLoaded", () => {
    console.log('dom loaded');

    const webSocket = new WebSocket("ws://127.0.0.1:8080/api/connect");

    webSocket.addEventListener("open", () => {
        console.log("web socket open!");
    });
    webSocket.addEventListener("close", () => {
        console.log("web socket closed...");
    });
    webSocket.addEventListener("error", (e) => {
        alert("Connection with server is failed... Please refresh your browser and try again.");
        console.log(e);
    });

    webSocket.addEventListener("message", (msg) => {
        console.log("Server send message!");
        console.log(JSON.parse(msg.data));
    });


    document.querySelector('#sendMsgBtn').addEventListener('click', () => {
        if(!webSocket){
           return;
        }

        webSocket.send('send me data');
    });
});