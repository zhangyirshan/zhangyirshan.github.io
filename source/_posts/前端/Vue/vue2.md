---
title: vue2
p: 前端/Vue/vue2
date: 2022-05-10 21:32:29
tags: Vue
categories: [前端,Vue]
---
## 全局事件总线

> 一种组件间通信的方式，适用于任意组件间通信

### 安装全局事件总线

```vue
new Vue({...
beforeCreate(){
    Vue.prototype.$bus = this // 安装全局事件总线，$bus就是当前应用的vm
}
...
})
```

### 使用事件总线

1. 接收数据：A组件像接收数据，则在A组件中给$bus绑定自定义事件，事件的回调留在A组件自身

```vue
method(){
    demo(data){......}
}
....
mounted(){
    this.$bus.$on('xxxx',this.demo)
}
```

2. 提供数据：`this.$bus.$emit('xxxx',数据)`

***最好在beforeDestroy钩子中，用$off去解绑当前组件所用到的事件***

## nextTick

1. 语法：`this.$nextTick(回调函数)`
2. 作用：在下一次DOM更新结束后执行其指定的回调。
3. 应用场景：当改变数据后，要基于更新后的新DOM进行某些操作时，要在nextTick所指定的回调函数中执行。
    例如：在v-show隐藏的input框中，需要显示后聚焦。

## vue解决组件加载慢问题

v-clock：本质是一个特殊属性，Vue实力创建完毕并接管容器后，会删掉v-clock属性。使用css配合v-clock可以解决网速慢时页面展示出{{xxx}}的问题
v-pre：跳过其所在的节点的编译过程，可以利用给他跳过没有指令语法、没有使用插值语法的节点，会加快编译

```vue
<style>
    [v-clock]: {
        display: none
    }
</style>
<h2 v-pre>{{name}}</h2>
<h2 v-clock>{{name}}</h2>
<export>
    vue加载成功后悔移除v-clock属性
</export>
```

## 自定义指令

directives

需求定义一个v-big指令，和v-text功能类似，但会把绑定的数值放大10倍

big(element,binding){
    
}