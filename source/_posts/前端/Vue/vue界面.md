---
title: vue界面
p: 前端/Vue/vue界面
date: 2019-12-18 21:32:29
tags: Vue
categories: [前端,Vue]
---
## 动画效果

transition标签包含的内容会执行css中的动画效果，
进入动画css名（transition名-enter-active）
离开动画css名（transition名-leave-active）

appear属性，初始化加载进入动画（等效于:appear="true"）

```js
<template>
<transition name="hello" appear>
    <h1 v-show="isShow"> hello</h1>
</transition>
</template>

<style scoped>
.hello-enter-active{
    animation: test 0.5s linear;
}
.hello-leave-active{
    animation: test 0.5s reverse;
}

@keyframes test{
    from{
        transform: translateX(-100%)
    }
    to{
        transform: translateX(0px)
    }
}
</style>
```
