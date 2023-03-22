function jump2Apm() {
  window.open('http://10.114.54.124');
}
  
chrome.action.onClicked.addListener((tab) => {
  // if(!tab.url.includes("chrome://")) {
  //   chrome.scripting.executeScript({
  //     target: { tabId: tab.id },
  //     function: jump2Apm
  //   });
  // }
  chrome.tabs.create({url: 'http://10.114.54.124'})
});
