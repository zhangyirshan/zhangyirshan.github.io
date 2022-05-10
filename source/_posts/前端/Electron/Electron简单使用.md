---
title: Electron简单使用
p: 前端/Electron/Electron简单使用
date: 2022-04-02 21:32:29
tags: Electron
categories: [前端,Electron]
---
## Electron简介

nodejs框架，用于开发跨平台桌面应用，可搭配Vue使用

[Electron官网](https://www.electronjs.org/zh/docs/latest/tutorial/quick-start)

创建项目使用`yarn create electron-forge 项目名`

## 常用代码

### 其他js页面使用require

在html页面中引入其他js代码，并且代码中需要使用require(),需要配置两个参数`nodeIntegration: true,contextIsolation: false`

```js
const createWindow = () => {
  // Create the browser window.
  const mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences:{
      // html中有webview中打开
      webviewTag: true,
      nodeIntegration: true,
      contextIsolation: false
    }
  });

  // and load the index.html of the app.
  mainWindow.loadFile(path.join(__dirname, 'index.html'));
  // Open the DevTools.
  mainWindow.webContents.openDevTools();
 };
```

### 添加页面组件事件监听

```js
let holder = document.querySelector('#holder');
// 拖拽事件监听
holder.addEventListener('drop',(e)=>{
    // 取消默认 
    e.preventDefault;
    // 阻止冒泡
    e.stopPropagation();
})
```

### 主进程和渲染进程通信

#### 主进程

```js
const { app, BrowserWindow,ipcMain} = require('electron');
// 监听渲染进程发送过来的zy-message事件
ipcMain.on('zy-message',(event, args)=>{
  event.reply('zy-reply','这是主进程答复')
  console.log(args)
})
const createWindow = () => {
  // Create the browser window.
  const mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences:{
      webviewTag: true,
      nodeIntegration: true,
      contextIsolation: false
    }
  });

  // and load the index.html of the app.
  mainWindow.loadFile(path.join(__dirname, 'index.html'));

  // Open the DevTools.
  mainWindow.webContents.openDevTools();

  setTimeout(()=>{
    // 主动发消息给渲染进程
    mainWindow.webContents.send('zy-reply', "创建窗口之后，主进程主动发送数据给渲染进程");
  },2000)
 };
```

#### 渲染进程

```js
// 渲染进程
let {ipcRenderer} = require('electron');
// 监听zy-reply消息
ipcRenderer.on('zy-reply',((event, args) => {
    console.log(event);
    console.log(args);
}))

// 主动发送消息给主进程
ipcRenderer.send('zy-message', "渲染进程发送消息给主进程");
```

### dialog弹窗

```js
const {dialog} = require('electron');
// openFile允许选择文件
// openDirectory润许选择文件夹
// multiSelection允许多选
// createDirectory允许创建文件夹
dialog.showOpenDialog({
    properties: ['openFile', 'multiSelections']
}).then((result) => {
    console.log(result.filePaths);
    console.log(result.canceled);
});

// 监听关闭事件设置二次警告弹窗
 mainWindow.on("close", (event) => {
    event.preventDefault();
    dialog.showMessageBox(mainWindow, {
      type: 'warning',
      title: "关闭",
      message: "是否要关闭窗口",
      buttons: ["取消", "关闭"]
    }).then((val) => {
      if (val.response === 1) {
        app.exit();
      }
    });
  });
```

### remote模块

> 在渲染进程中使用主进程的BrowserWindow创建新的窗口

### Menu模块

> 菜单栏

```js
const {Menu} = require('electron')
let temp = [
  {
    lable: "菜单栏",
    submenu: [
      {lable: '子菜单项',
       click:()=>{

       }
      }
    ] 
  }
]
var m = Menu.buildFromtemplate(template)
Menu.setApplicationMenu(m)
```
