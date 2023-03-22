let monitor = document.getElementById("monitor");
monitor.addEventListener("click", async () => {
  chrome.tabs.create({url: 'http://10.114.54.124'})
});
let profiler = document.getElementById("profiler");
profiler.addEventListener("click", async () => {
  let [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
  if(!tab.url.includes("chrome://")) {
    chrome.scripting.executeScript({
      target: { tabId: tab.id },
      func: jump2Profiler,
    });
  }

});

function jump2Profiler() {
  alert("todo");
}