---
title: HEXO的图片上传问题
date: 2019-11-23 22:16:23
tags: [错误,博客]
categories: [错误,博客,图片]
---
## 问题状况

{% asset_img 图片上传错误.png 图片上传错误 [图片上传错误] %}
如果你的hexo安装了img插件但是还是不显示，后台输出地址404。

## 解决办法

1. 首先找到你的node_modules\hexo-asset-image@0.0.5@hexo-asset-image和node_modules\hexo-asset-image这两个文件包，删除。
2. 接下来

    ```npm
    npm install https://github.com/CodeFalling/hexo-asset-image --save
    ```

    执行这条指令。

3. 打开/node_modules/hexo-asset-image/index.js，将内容更换为下面的代码

    ```js
    'use strict';
    var cheerio = require('cheerio');

    // http://stackoverflow.com/questions/14480345/how-to-get-the-nth-occurrence-in-a-string
    function getPosition(str, m, i) {
    return str.split(m, i).join(m).length;
    }

    var version = String(hexo.version).split('.');
    hexo.extend.filter.register('after_post_render', function(data){
    var config = hexo.config;
    if(config.post_asset_folder){
            var link = data.permalink;
        if(version.length > 0 && Number(version[0]) == 3)
        var beginPos = getPosition(link, '/', 1) + 1;
        else
        var beginPos = getPosition(link, '/', 3) + 1;
        // In hexo 3.1.1, the permalink of "about" page is like ".../about/index.html".
        var endPos = link.lastIndexOf('/') + 1;
        link = link.substring(beginPos, endPos);

        var toprocess = ['excerpt', 'more', 'content'];
        for(var i = 0; i < toprocess.length; i++){
        var key = toprocess[i];
        var $ = cheerio.load(data[key], {
            ignoreWhitespace: false,
            xmlMode: false,
            lowerCaseTags: false,
            decodeEntities: false
        });

        $('img').each(function(){
            if ($(this).attr('src')){
                // For windows style path, we replace '\' to '/'.
                var src = $(this).attr('src').replace('\\', '/');
                if(!/http[s]*.*|\/\/.*/.test(src) &&
                !/^\s*\//.test(src)) {
                // For "about" page, the first part of "src" can't be removed.
                // In addition, to support multi-level local directory.
                var linkArray = link.split('/').filter(function(elem){
                    return elem != '';
                });
                var srcArray = src.split('/').filter(function(elem){
                    return elem != '' && elem != '.';
                });
                if(srcArray.length > 1)
                    srcArray.shift();
                src = srcArray.join('/');
                $(this).attr('src', config.root + link + src);
                console.info&&console.info("update link as:-->"+config.root + link + src);
                }
            }else{
                console.info&&console.info("no src attr, skipped...");
                console.info&&console.info($(this));
            }
        });
        data[key] = $.html();
        }
    }
    });
    ```

4. 打开_config.yml文件，修改下述内容

    ```js
    post_asset_folder: true
    ```

## 问题推测

1. 出现了多个版本的包出现了混乱
2. 文件没有修改。
