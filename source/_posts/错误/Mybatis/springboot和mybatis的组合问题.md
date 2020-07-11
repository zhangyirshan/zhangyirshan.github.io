---
title: SpringBoot和Mybatis的组合问题
date: 2020-07-10 22:16:23
tags: [错误,SpringBoot,Mybatis]
categories: [错误,SpringBoot]
---
## 问题状况

```java
List<Audioconverttochn> audioConvertToCHNList = gameService.selectAudioConvertToCHN(hangUpList);
audioConvertToCHNList.removeIf(audioConvertToCHN -> StringUtils.isNotBlank(audioConvertToCHN.getResult()));


public List<Audioconverttochn> selectAudioConvertToCHN(List<UserCreditReport> list) {
    if (CollectionUtils.isEmpty(list)) {
        list = new ArrayList<>();
        UserCreditReport userCreditReport = new UserCreditReport();
        userCreditReport.setUserNo(0);
        userCreditReport.setGamerecordNo(0);
        list.add(userCreditReport);
    }
    return new ArrayList<>(gameDao.selectAudioConvertToCHN(list));
}

// 第二次返回的接口地址和第一次的相同，正常sql查询 会有3条记录，但是对象中只存在一条
List<Audioconverttochn> audioConvertToCHNList = gameService.selectAudioConvertToCHN(hangUpList);
```
传入一个参数
如果你在service方法中传入相同的参数，会得到同一个对象

## 解决办法

new ArrayList(creditDao.selectxxx());
