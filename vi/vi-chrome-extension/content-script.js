let already = false
function insertApmNode(treeList) {
    if(!treeList) return console.info("treeList null");
    if(already) return console.info("insert already");
    let li = document.createElement("li");
    li.className = 'tree-item';
    li.addEventListener('click', () => window.open('http://10.114.54.124'));
    li.innerHTML = `
    <span data-v-0b16807e="" class="button menus" style="padding-left: 26px;">
        <span data-v-0b16807e="" class="">
            <i data-v-0b16807e="" class="menu-icon icon iconfont2 icon-fabu"></i><!---->
            <span data-v-0b16807e="" class="el-tooltip text" aria-describedby="el-tooltip-6123" tabindex="0">APM </span>
        </span>
        <span data-v-0b16807e="">
            <i data-v-0b16807e="" class="expand-icon el-icon-arrow-right"></i>
        </span>
    </span>`;
    treeList.appendChild(li);
    console.info("insert successful");
    already = true;
}
let treeList = document.querySelector('.tree-list')
if(!treeList) treeList=document.querySelector('.tree-list')
if (treeList) {
    insertApmNode(treeList)
}
const observedContainer = document.querySelector('html')
if (observedContainer) {
    const config = { attributes: true, childList: true, subtree: true }
    const callback = (mutationsList) => {
        for (const mutation of mutationsList) {
            if (mutation.type === 'childList') {
                for (const node of mutation.addedNodes) {
                    // if (node.classList && node.classList.contains('tree-list')) {
                        if(!treeList){
                            treeList = document.querySelector('.tree-list')
                            insertApmNode(treeList);
                        }
                    // }
                }
            }
        }
    }

    window.matchMedia('(prefers-color-scheme: dark)').addListener(() => {
    })

    // const observedContainer = document.querySelector('html')
    const observer = new MutationObserver(callback)
    observer.observe(observedContainer, config)
}


