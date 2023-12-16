function show(tagObj){
    tagObj.style.display = '';
}

function hide(tagObj){
    tagObj.style.display = 'none';
}

document.addEventListener("DOMContentLoaded", () => {
    console.log('dom loaded');

    const BrushedLevel = {
        NONE: {
            level: 0,
            color: '#FFFFFFFF'
        },
        LITTLE: {
            level: 1,
            color: '#F0F8FFFF'
        },
        MEDIUM: {
            level: 2,
            color: '#87CEFAFF'
        },
        COMPLETE: {
            level: 3,
            color: '#1E90FFFF'
        }
    };
    const Status = {
        SUCCESS: 'S',
        ERROR: 'E'
    };

    function init() {
        // 1. 상수화
        const consts = [BrushedLevel, Status];
        consts.forEach(c => {
            Object.freeze(c);
        });

        initEvent();
    }

    function initEvent() {
        const startBtn = document.querySelector('#startBtn');
        const resetBtn = document.querySelector('#resetBtn');

        startBtn.addEventListener('click', (e) => {
            const webSocket = new WebSocket("ws://127.0.0.1:8080/api/connect");
            webSocket.addEventListener("open", () => {
                console.log("web socket open!");
            });
            webSocket.addEventListener("close", () => {
                console.log("web socket closed...");
            });
            webSocket.addEventListener("error", (e) => {
                console.log(e);
                alert("Connection with server is failed... Please refresh your browser and try again.");
            });

            console.log(webSocket)

            /**
             * msg.data 형식
             * {
             *     status: {char} s / e,
             *     errorMsg: {string},
             *     brushedSection: {int},
             *     brushedLevel: {int}
             * }
             */
            webSocket.addEventListener("message", (msg) => {
                console.log("Server send message!");

                const errorMsgDv = document.querySelector('#errorMsg');
                const data = JSON.parse(msg.data);
                console.log(data);

                if(data.status == Status.SUCCESS){
                    hide(errorMsgDv);
                    errorMsgDv.innerHTML =  '';

                    const sectionDv = document.querySelector(`#section${data.brushedSection}`);
                    Object.keys(BrushedLevel).forEach(key => {
                        if(BrushedLevel[key].level == data.brushedLevel){
                            sectionDv.style.setProperty('background-color', BrushedLevel[key].color);
                        }
                    });
                }
                else{
                    errorMsgDv.innerHTML = data.errorMsg;
                    show(errorMsgDv);
                }
            });
        });

        resetBtn.addEventListener('click', async () => {
            await fetch('http://localhost:8080/api/reset', {
                method: 'POST'
            }).then(res => {
                alert('reset success!');
            });
        });
    }

    init();
});