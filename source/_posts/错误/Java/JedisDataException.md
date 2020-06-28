---
title: Redis的查询异常
p: 错误/Java/Redis的查询异常
date: 2020-06-22 21:01:39
tags: 错误,Java
categories: [错误,Java]
---
```Java
[MG][ERROR][2020-06-19 09:08:58,904]|REDIS查询异常|[qtp1371957475-16]
redis.clients.jedis.exceptions.JedisDataException: value sent to redis cannot be null
	at redis.clients.util.SafeEncoder.encode(SafeEncoder.java:28) ~[werewolf-client-jp.jar:?]
	at redis.clients.jedis.Client.hget(Client.java:211) ~[werewolf-client-jp.jar:?]
	at redis.clients.jedis.Jedis.hget(Jedis.java:760) ~[werewolf-client-jp.jar:?]
	at com.mega.server.jedis.MegaJedisUtil.getHashValue(MegaJedisUtil.java:24) ~[werewolf-client-jp.jar:?]
	at com.mega.werewolf.server.handler.EnterRoomHandler.handle(EnterRoomHandler.java:35) ~[werewolf-client-jp.jar:?]
	at com.mega.werewolf.server.HttpHandler.dispatch(HttpHandler.java:71) ~[werewolf-client-jp.jar:?]
	at com.mega.werewolf.server.HttpHandler.handle(HttpHandler.java:55) ~[werewolf-client-jp.jar:?]
	at org.eclipse.jetty.server.handler.HandlerWrapper.handle(HandlerWrapper.java:127) ~[werewolf-client-jp.jar:?]
	at org.eclipse.jetty.server.Server.handle(Server.java:500) ~[werewolf-client-jp.jar:?]
	at org.eclipse.jetty.server.HttpChannel.lambda$handle$1(HttpChannel.java:383) ~[werewolf-client-jp.jar:?]
	at org.eclipse.jetty.server.HttpChannel.dispatch(HttpChannel.java:547) [werewolf-client-jp.jar:?]
	at org.eclipse.jetty.server.HttpChannel.handle(HttpChannel.java:375) [werewolf-client-jp.jar:?]
	at org.eclipse.jetty.server.HttpConnection.onFillable(HttpConnection.java:270) [werewolf-client-jp.jar:?]
	at org.eclipse.jetty.io.AbstractConnection$ReadCallback.succeeded(AbstractConnection.java:311) [werewolf-client-jp.jar:?]
	at org.eclipse.jetty.io.FillInterest.fillable(FillInterest.java:103) [werewolf-client-jp.jar:?]
	at org.eclipse.jetty.io.ChannelEndPoint$2.run(ChannelEndPoint.java:117) [werewolf-client-jp.jar:?]
	at org.eclipse.jetty.util.thread.QueuedThreadPool.runJob(QueuedThreadPool.java:806) [werewolf-client-jp.jar:?]
	at org.eclipse.jetty.util.thread.QueuedThreadPool$Runner.run(QueuedThreadPool.java:938) [werewolf-client-jp.jar:?]
	at java.lang.Thread.run(Thread.java:748) [?:1.8.0_191]
```

redis.set(key, value)

1.如果key是null

redis.clients.jedis.exceptions.JedisDataException: value sent to redis cannot be null

2.如果value是null

redis.clients.jedis.exceptions.JedisDataException: value sent to redis cannot be null
   

所以说，哪个都不能为null。

```Java
String port = MegaJedisUtil.getHashValue(ServerWerewolfConst.WF_ROOM_SERVER, String.valueOf(roomNo));
String ip = MegaJedisUtil.getHashValue(ServerWerewolfConst.WF_PORT_IP, port);
if (StringUtil.isEmpty(ip) ||StringUtil.isEmpty(port)) {
    LogTool.logError("ip或port为空");
    JSONObject object = new JSONObject();
    object.put("sign", "0");
    return object;
}
```
