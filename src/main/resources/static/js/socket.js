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
    const SECTION_CNT = 16;

    let _webSocket;
    let _brushCompleteSectionCnt = 0;

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
        const testBtn = document.querySelector('#testBtn');

        startBtn.addEventListener('click', connectToWebSocket);

        resetBtn.addEventListener('click', async () => {
            const teethTags = document.querySelectorAll('.teeth');
            teethTags.forEach((t) => {
                t.style.setProperty('background-color', 'white');
            });

            const alertMsgTag = document.querySelector('#alertMsg');
            hide(alertMsgTag);

            _brushCompleteSectionCnt = 0;

            await fetch('http://localhost:8080/api/reset', {
                method: 'POST'
            }).then(res => {
                alert('reset success!');
            });
        });

        testBtn.addEventListener('click', async () => {
            const teethTagIds = ["uf_outside", "uf_inside",
                "ur_outside", "ur_inside", "ur_above",
                "ul_outside", "ul_inside", "ul_above",
                "lf_outside", "lf_inside",
                "lr_outside", "lr_inside", "lr_above",
                "ll_outside", "ll_inside", "ll_above"];

            for (const id of teethTagIds) {
                console.log(id);
                await fetch('http://localhost:8080/api/teeth', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        brushedSection: id,
                        brushedCnt: 10
                    }),
                });
            }
        });


        function connectToWebSocket() {
            if(!!_webSocket){
                _webSocket.close();
            }

            _webSocket = new WebSocket("ws://127.0.0.1:8080/api/connect");
            _webSocket.addEventListener("open", () => {
                console.log("web socket open!");
            });
            _webSocket.addEventListener("close", () => {
                console.log("web socket closed...");
            });
            _webSocket.addEventListener("error", (e) => {
                console.log(e);
                alert("Connection with server is failed... Please refresh your browser and try again.");
            });

            console.log(_webSocket)

            /**
             * msg.data 형식
             * {
             *     status: {char} s / e,
             *     errorMsg: {string},
             *     brushedSection: {int},
             *     brushedLevel: {int}
             * }
             */
            _webSocket.addEventListener("message", (msg) => {
                console.log("Server send message!");

                const alertMsgTag = document.querySelector('#alertMsg');
                const errorMsgDv = document.querySelector('#errorMsg');
                const data = JSON.parse(msg.data);
                console.log(data);

                if(data.status == Status.SUCCESS){
                    hide(errorMsgDv);
                    errorMsgDv.innerHTML =  '';

                    const sectionDv = document.querySelector(`#${data.brushedSection}`);
                    Object.keys(BrushedLevel).forEach(key => {
                        if(BrushedLevel[key].level == data.brushedLevel){
                            sectionDv.style.setProperty('background-color', BrushedLevel[key].color);
                            if(BrushedLevel[key].level == BrushedLevel.COMPLETE.level){
                                _brushCompleteSectionCnt++;
                            }
                        }
                    });

                    if (_brushCompleteSectionCnt == SECTION_CNT) {
                        show(alertMsgTag);
                    }
                    else{
                        hide(alertMsgTag);
                    }
                }
                else{
                    errorMsgDv.innerHTML = data.errorMsg;
                    show(errorMsgDv);

                    hide(alertMsgTag);
                }
            });
        }
    }

    init();
});