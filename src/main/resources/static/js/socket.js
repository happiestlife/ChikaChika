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
            level: 'NONE',
            color: '#FFFFFFFF'
        },
        LITTLE: {
            level: 'LITTLE',
            color: '#F0F8FFFF'
        },
        MEDIUM: {
            level: 'MEDIUM',
            color: '#87CEFAFF'
        },
        COMPLETE: {
            level: 'COMPLETE',
            color: '#1E90FFFF'
        }
    };
    const Status = {
        SUCCESS: 'S',
        ERROR: 'E'
    };

    const _section = {
        ulTeeth: document.querySelector('#ulTeeth'),
        ufTeeth: document.querySelector('#ufTeeth'),
        urTeeth: document.querySelector('#urTeeth'),
        llTeeth: document.querySelector('#llTeeth'),
        lfTeeth: document.querySelector('#lfTeeth'),
        lrTeeth: document.querySelector('#lrTeeth')
    };
    const _alertMsgTag = document.querySelector('#alertMsg');
    const _errorMsgDv = document.querySelector('#errorMsg');

    let _webSocket;

    function init() {
        // 1. 상수화
        const consts = [BrushedLevel, Status];
        consts.forEach(c => {
            Object.freeze(c);
        });

        // 2. 이벤트 초기화
        initEvent();

        // 3. 이빨 하이라이트 부분 숨기기
        Object.keys(_section).forEach(key => {
            hide(_section[key]);
        });
    }

    function initEvent() {
        const startBtn = document.querySelector('#startBtn');
        const resetBtn = document.querySelector('#resetBtn');
        const testBtn  = document.querySelector('#testBtn');

        startBtn.addEventListener('click', connectToWebSocket);

        resetBtn.addEventListener('click', async () => {
            const teethTags = document.querySelectorAll('.teeth');
            teethTags.forEach((t) => {
                t.style.setProperty('background-color', 'white');
            });

            hide(_alertMsgTag);

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
             *     status: {string} s / e,
             *     msg: {string},
             *     brushedSection: {string},
             *     brushedLevel: {string},
             *     nextBrushSection: {string}
             * }
             */
            _webSocket.addEventListener("message", (msg) => {
                console.log("Server send message!");

                const data = JSON.parse(msg.data);
                console.log(data);

                if(data.status == Status.SUCCESS){
                    hide(_errorMsgDv);
                    _errorMsgDv.innerHTML =  '';

                    _alertMsgTag.innerHTML = data.msg ?? '';
                    show(_alertMsgTag);

                    const sectionDv = document.querySelector(`#${data.brushedSection}`);
                    if(!!sectionDv) {
                        Object.keys(BrushedLevel).forEach(key => {
                            if (BrushedLevel[key].level == data.brushedLevel) {
                                sectionDv.style.setProperty('background-color', BrushedLevel[key].color);
                            }
                        });
                    }

                    if (data.brushedSection != data.nextBrushSection) {
                        const nxtSectionPrefix = data.nextBrushSection.substring(0, 2);
                        console.log(data.brushedSection, data.nextBrushSection)
                        console.log(nxtSectionPrefix)
                        Object.keys(_section).forEach((key) => {
                            console.log(key, key.startsWith(nxtSectionPrefix))
                            if (key.startsWith(nxtSectionPrefix)) {
                                show(_section[key]);
                            }
                            else{
                                hide(_section[key]);
                            }
                        });
                    }
                    else{
                        Object.keys(_section).forEach((key) => {
                            hide(_section[key]);
                        });
                    }
                }
                else{
                    _errorMsgDv.innerHTML = data.msg;
                    show(_errorMsgDv);

                    hide(_alertMsgTag);
                }
            });
        }
    }

    init();
});