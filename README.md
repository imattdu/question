## question

[question](http://123.56.135.43:8080/)

### 概述

该项目的主要有用户、关注、消息、问题四个模块



### 项目地址

[question](http://123.56.135.43:8080/)





### 出现的问题

1.打包时去除多有main函数

2.部署的时候可能会延迟，查看日志文件若没有出错则不会出错

3.空指针：如果不判断cookies就会空指针异常

```java
String ticket = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ticket".equals(cookie.getName())) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
```

4.**Caused by: com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure**

由于数据库回收了连接，而系统的缓冲池不知道，继续使用被回收的连接所致的。

mysql默认空闲时间是8H

方法一：修改空闲时间

方法二：可以通过配置，让缓冲池去测试连接是否被回收，如果被回收，则不继续使用



登陆注册、前缀树（过滤）、redis、消息队列、邮件、ssl协议、推拉、倒排

