---
title: Java编程风格
p: 编程风格/Java编程风格
date: 2019-12-12 17:51:30
tags: 编程风格
categories: [编程风格,Java编程风格]
---

```java
/**
 * 通过卷宗主键和创建人Id获得批注列表，以最后修改时间升序排列
 * @return 批注实体列表
 * @author dingjsh
 * time 2018年1月28日上午10:56:08
 * @version 4.0.0
 */
@ApiOperation(value = "根据卷宗Id和创建人id查询批注", notes = "用于iframe的pdf渲染批注位置")
@GetMapping(value = "/iframe/{caseId}", produces = "application/json;charset=UTF-8")
@ApiResponses({ @ApiResponse(code = 200, message = "操作成功, 返回数据."),
@ApiResponse(code = 400, message = "参数不正确"), })
public ResponseEntity<List<JzpzEntity>> getPzForPdf(@PathVariable(name = "caseId") @ApiParam(value = "caseId", required = true) String caseId) {}
```

@ApiOperation是批注类，用在swagger上。
@GetMapping不仅要设置value还要设置produces属性,并根据程序功能进行修改
