javascript:function videoCustomerJs() {
    var videoTouchMode = false;
    var videoEl = document.querySelector('video');
    console.log('Query video element: ', videoEl);
    console.log('Query the video container: ', document.querySelector('#player'));
    function onEvent () {
        videoEl.addEventListener('play', function () {
            console.log('call play');
        });
        videoEl.addEventListener('ended', function () {
            console.log('call ended');
        });
        videoEl.addEventListener('playing', function () {
            console.log('call playing',videoEl.duration,videoEl.currentTime);
        });
        videoEl.addEventListener('pause', function () {
            console.log('call pause');
        });
        videoEl.addEventListener('waiting', function () {
            console.log('call waiting');
        });
    };
    if (!videoEl) {
        var targetNode = document.querySelector('#player') ? document.querySelector('#player') : document.body;
        var config = { childList: true,subtree: true};
        var callback = function () {
            console.log('Element mutation observer!');
            videoEl = document.querySelector('video');
            if (videoEl) {
                onEvent();
                observer.disconnect();
            }
        };
        var observer = new MutationObserver(callback);
        observer.observe(targetNode, config);
        return;
   }
    onEvent();
}