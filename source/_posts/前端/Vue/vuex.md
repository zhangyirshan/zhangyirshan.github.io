---
title: vuex
p: 前端/Vue/vuex
date: 2019-12-18 21:32:29
tags: Vue
categories: [前端,Vue]
---
## vuex理解

### vuex 是什么

1) github 站点: <https://github.com/vuejs/vuex>
2) 在线文档: <https://vuex.vuejs.org/zh-cn/>
3) 简单来说: 对 vue 应用中多个组件的共享状态进行集中式的管理(读/写)

使用vuex后，页面的属性就多store,getters属性了

### 状态自管理应用

1) state: 驱动应用的数据源
2) view: 以声明方式将 state 映射到视图
3) actions: 响应在 view 上的用户输入导致的状态变化(包含 n 个更新状态的方法)
{% asset_img vuex.png vuex%}

### 多组件共享状态的问题

1) 多个视图依赖于同一状态
2) 来自不同视图的行为需要变更同一状态
3) 以前的解决办法
a. 将数据以及操作数据的行为都定义在父组件
b. 将数据以及操作数据的行为传递给需要的各个子组件(有可能需要多级传递)
4) vuex 就是用来解决这个问题的

{% asset_img vuex(1).png vuex%}

## vuex 核心概念和 API

### state

1) vuex 管理的状态对象
2) 它应该是唯一的

```js
const state = {
xxx: initValue
}
```

### mutations

1) 包含多个直接更新 state 的方法(回调函数)的对象
2) 谁来触发: action 中的 commit('mutation 名称')
3) 只能包含同步的代码, 不能写异步代码

```js
const mutations = {
yyy (state, {data1}) {
// 更新 state 的某个属性
}
}
```

### actions

1) 包含多个事件回调函数的对象
2) 通过执行: commit()来触发 mutation 的调用, 间接更新 state
3) 谁来触发: 组件中: $store.dispatch('action 名称', data1) // 'zzz' 4) 可以包含异步代码(定时器, ajax)

```js
const actions = {
zzz ({commit, state}, data1) {
commit('yyy', {data1})
}
}
```

### getters

1) 包含多个计算属性(get)的对象
2) 谁来读取: 组件中: $store.getters.xxx

```js
const getters = {
mmm (state) {
return ...
```
