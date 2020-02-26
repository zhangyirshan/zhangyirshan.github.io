---
title: script位置问题
p: 前端/JS/script位置问题
date: 2020-02-11 19:31:43
tags: [前端,JS]
categories: [前端,JS]
---

页面自上而下加载如果你的script标签放在开头，将会无法加载body中的元素，但是你放在结尾就不会有问题，
但是如果你非要放在开头可以按照下方的样子做

```js
window.onload = function () {
    ...
}
```

或者从外部引用js文件
