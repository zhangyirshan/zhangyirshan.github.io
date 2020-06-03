---
title: PreparedStatement和Statement
date: 2020-06-03 15:08:40
tags: Java
categories: [Java,Java工具类]
---

网上有很多文章讨论PrepardStatement与Statement的区别，不过要完完全全的作出比较难度很大，因为每个数据库对底层的实现及应用场合不一样，Oracle对PrepardStatement的支持最好，Mysql对PrepardStatement支持最差。Statement执行单个sql语句速度较快而PrepardStatement执行批处理的效率较高。

以Oracle为例
 
1. Statement为每一条Sql语句生成执行计划， 如果要执行两条sql语句
    select colume from table where colume=1;
    select colume from table where colume=2;
    会生成两个执行计划，一千个查询就生成一千个执行计划。而生成计划是非常消耗资源的

2. PreparedStatement用于使用绑定变量重用执行计划
    select * from xxx.sometable t where t.id=?;
    通过set方法给sql语句按占位符"?"先后顺序赋值，只需要生成一个执行计划，可以重复使用。

当处理批量SQL语句时，这个时候就可以体现PrepareStatement的优势，由于采用Cache机制，则预先编译的语句，就会放在Cache中，下次执行相同SQL语句时，则可以直接从Cache中取出来,效率要比statement高好几倍

PrepardStatement的优点
<1>、用PrepardStatement写成的sql语句，容易阅读，维护方便。
<2>、批处理效率高，执行速度快。
<3>、安全，可以防止sql注入攻击。

缺点:当执行批处理时，你无法得知这个批处理总共影响了多少行。

```java
/**
* PrepareStatement 测试插入数据库
*/
/**
* 如果使用Statement，那么就必须在SQL语句中，实际地去嵌入值，比如之前的insert语句
*
* 但是这种方式有一个弊端，第一，是容易发生SQL注入，SQL注入，简单来说，就是，你的网页的用户
* 在使用，比如论坛的留言板，电商网站的评论页面，提交内容的时候，可以使用'1 or 1'，诸如此类的
* 非法的字符，然后你的后台，如果在插入评论数据到表中的时候，如果使用Statement，就会原封不动的
* 将用户填写的内容拼接在SQL中，此时可能会发生对数据库的意外的损坏，甚至数据泄露，这种情况就叫做
* SQL注入
*
* 第二种弊端，就是性能的低下，比如insert into test_user(name,age) values('张三',25)
* insert into test_user(name,age) values('李四',26)
* 其实两条SQL语句的结构大同小异，但是如果使用这种方式，在MySQL中执行SQL语句的时候，却需要对
* 每一条SQL语句都实现编译，编译的耗时在整个SQL语句的执行耗时中占据了大部分的比例
* 所以，Statement会导致执行大量类似SQL语句的时候的，性能低下
*
* 如果使用PreparedStatement，那么就可以解决上述的两个问题
* 1、SQL注入，使用PreparedStatement时，是可以在SQL语句中，对值所在的位置使用?这种占位符的
* 使用占位符之后，实际的值，可以通过另外一份放在数组中的参数来代表。此时PreparedStatement会对
* 值做特殊的处理，往往特殊处理后，就会导致不法分子的恶意注入的SQL代码失效
* 2、提升性能，使用PreparedStatement之后，其实结构类似的SQL语句，都变成一样的了，因为值的地方
* 都会变成?，那么一条SQL语句，在MySQL中只会编译一次，后面的SQL语句过来，就直接拿编译后的执行计划
* 加上不同的参数直接执行，可以大大提升性能
*/
private static void preparedStatement() {
// 总结一下JDBC的最基本的使用过程
// 1、加载驱动类：Class.forName()
// 2、获取数据库连接：DriverManager.getConnection()
// 3、创建SQL语句执行句柄：Connection.createStatement()
// 4、执行SQL语句：Statement.executeUpdate()
// 5、释放数据库连接资源：finally，Connection.close()

// 定义数据库连接对象
// 引用JDBC相关的所有接口或者是抽象类的时候，必须是引用java.sql包下的
// java.sql包下的，才代表了java提供的JDBC接口，只是一套规范
// 至于具体的实现，则由数据库驱动来提供，切记不要引用诸如com.mysql.jdbc包的类
Connection conn=null;
//定义SQL语句执行句柄:PrepareStatement对象
//PreparedStatement对象,其实就是底层会基于Connection数据库连接
//可以让我们方便的针对数据库中的表,执行增删改查的SQL语句
//比如和insert update delete和select语句
PreparedStatement ps=null;
try {
    // 第一步，加载数据库的驱动，我们都是面向java.sql包下的接口在编程，所以
    // 要想让JDBC代码能够真正操作数据库，那么就必须第一步先加载进来你要操作的数据库的驱动类
    // 使用Class.forName()方式来加载数据库的驱动类
    // Class.forName()是Java提供的一种基于反射的方式，直接根据类的全限定名（包+类）
    // 从类所在的磁盘文件（.class文件）中加载类对应的内容，并创建对应的Class对象
    Class.forName("com.mysql.jdbc.Driver");
    // 获取数据库的连接
    // 使用DriverManager.getConnection()方法获取针对数据库的连接
    // 需要给方法传入三个参数，包括url、user、password
    // 其中url就是有特定格式的数据库连接串，包括“主协议:子协议://主机名:端口号//数据库”
    conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/spark_project?characterEncoding=utf8",
            "root",
            "root"
    );
    // 基于数据库连接Connection对象，创建SQL语句执行句柄，Statement对象
    // prepareStatement对象，就是用来基于底层的Connection代表的数据库连接
    // 允许我们通过java程序，通过prepareStatement对象，向MySQL数据库发送SQL语句
    // 从而实现通过发送的SQL语句来执行增删改查等逻辑
    // 第一个，SQL语句中，值所在的地方，都用问好代表
    String sql = "insert into user(name,age) values(?,?)";
    ps = conn.prepareStatement(sql);
    // 第二个，必须调用PreparedStatement的setX()系列方法，对指定的占位符设置实际的值
    ps.setString(1,"李四");
    ps.setInt(2,26);
    // Statement.executeUpdate()方法，就可以用来执行insert、update、delete语句
    // 返回类型是个int值，也就是SQL语句影响的行数
    // 第三个，执行SQL语句时，直接使用executeUpdate()即可，不用传入任何参数
    int rtn = ps.executeUpdate();

    System.out.println("SQL语句影响了【" + rtn + "】行。");
}catch (Exception e){
    e.printStackTrace();
}finally {
    try {
        // 最后一定要记得在finally代码块中，尽快在执行完SQL语句之后，就释放数据库连接
        if (ps != null){
            ps.close();
        }
        if (conn !=null){
            conn.close();
        }
    }catch (Exception e){
        e.printStackTrace();
    }
}
}
```

jdbc(java database connectivity，java数据库连接)的api中的主要的四个类之一的java.sql.statement要求开发者付出大量的时间和精力。在使用statement获取jdbc访问时所具有的一个共通的问题是输入适当格式的日期和时间戳：2002-02-05 20:56 或者 02/05/02 8:56 pm。
通过使用java.sql.preparedstatement，这个问题可以自动解决。一个preparedstatement是从java.sql.connection对象和所提供的sql字符串得到的，sql字符串中包含问号（?），这些问号标明变量的位置，然后提供变量的值，最后执行语句，例如：

```java
stringsql = "select * from people p where p.id = ? and p.name = ?";
preparedstatement ps = connection.preparestatement(sql);
ps.setint(1,id);
ps.setstring(2,name);
resultset rs = ps.executequery();
使用preparedstatement的另一个优点是字符串不是动态创建的。下面是一个动态创建字符串的例子：
stringsql = "select * from people p where p.i = "+id;

这允许jvm（javavirtual machine，java虚拟机）和驱动/数据库缓存语句和字符串并提高性能。
preparedstatement也提供数据库无关性。当显示声明的sql越少，那么潜在的sql语句的数据库依赖性就越小。
由于preparedstatement具备很多优点，开发者可能通常都使用它，只有在完全是因为性能原因或者是在一行sql语句中没有变量的时候才使用通常的statement。
一个完整的preparedstatement的例子：
package jstarproject;
import java.sql.*;
public class mypreparedstatement {
    private final string db_driver="com.microsoft.jdbc.sqlserver.sqlserverdriver";
    private final string url = "jdbc:microsoft:sqlserver://127.0.0.1:1433;databasename=pubs";
    public mypreparedstatement(){}
    public void query() throws sqlexception{
        connection conn = this.getconnection();
        string strsql = "select emp_id from employee where emp_id = ?";
        preparedstatement pstmt = conn.preparestatement(strsql);
        pstmt.setstring(1,"pma42628m");
        resultset rs = pstmt.executequery();

        while(rs.next()){
            string fname = rs.getstring("emp_id");
            system.out.println("the fname is " + fname);
        }
        rs.close();
        pstmt.close();
        conn.close();
    }
    private connection getconnection() throws sqlexception{
        // class.
        connection conn = null;
        try {
        class.forname(db_driver);
        conn = drivermanager.getconnection(url,"sa","sa");
        }
        catch (classnotfoundexception ex) {}
        return conn;
    }
        //main
    public static void main(string[] args) throws sqlexception {
        mypreparedstatement jdbctest1 = new mypreparedstatement();
        jdbctest1.query();
    }
}
```

为什么要始终使用PreparedStatement代替Statement?为什么要始终使用PreparedStatement代替Statement?


在JDBC应用中,如果你已经是稍有水平开发者,你就应该始终以PreparedStatement代替Statement.也就是说,在任何时候都不要使用Statement.
基于以下的原因:
一.代码的可读性和可维护性.
虽然用PreparedStatement来代替Statement会使代码多出几行,但这样的代码无论从可读性还是可维护性上来说.都比直接用Statement的代码高很多档次:

```java
stmt.executeUpdate("insert into tb_name (col1,col2,col2,col4) values ('"+var1+"','"+var2+"',"+var3+",'"+var4+"')");

perstmt = con.prepareStatement("insert into tb_name (col1,col2,col2,col4) values (?,?,?,?)");
perstmt.setString(1,var1);
perstmt.setString(2,var2);
perstmt.setString(3,var3);
perstmt.setString(4,var4);
perstmt.executeUpdate();
```

不用我多说,对于第一种方法.别说其他人去读你的代码,就是你自己过一段时间再去读,都会觉得伤心.

二.PreparedStatement尽最大可能提高性能.

每一种数据库都会尽最大努力对预编译语句提供最大的性能优化.因为预编译语句有可能被重复调用.所以语句在被DB的编译器编译后的执行代码被缓存下来,那么下次调用时只要是相同的预编译语句就不需要编译,只要将参数直接传入编译过的语句执行代码中(相当于一个涵数)就会得到执行.这并不是说只有一个Connection中多次执行的预编译语句被缓存,而是对于整个DB中,只要预编译的语句语法和缓存中匹配.那么在任何时候就可以不需要再次编译而可以直接执行.而statement的语句中,即使是相同一操作,而由于每次操作的数据不同所以使整个语句相匹配的机会极小,几乎不太可能匹配.比如:
insert into tb_name (col1,col2) values ('11','22');
insert into tb_name (col1,col2) values ('11','23');
即使是相同操作但因为数据内容不一样,所以整个个语句本身不能匹配,没有缓存语句的意义.事实是没有数据库会对普通语句编译后的执行代码缓存.

当然并不是所以预编译语句都一定会被缓存,数据库本身会用一种策略,比如使用频度等因素来决定什么时候不再缓存已有的预编译结果.以保存有更多的空间存储新的预编译语句.

三.最重要的一点是极大地提高了安全性.

即使到目前为止,仍有一些人连基本的恶义SQL语法都不知道.
String sql = "select * from tb_name where name= '"+varname+"' and passwd='"+varpasswd+"'";
如果我们把[' or '1' = '1]作为varpasswd传入进来.用户名随意,看看会成为什么?

select * from tb_name = '随意' and passwd = '' or '1' = '1';
因为'1'='1'肯定成立,所以可以任何通过验证.更有甚者:
把[';drop table tb_name;]作为varpasswd传入进来,则:
select * from tb_name = '随意' and passwd = '';drop table tb_name;有些数据库是不会让你成功的,但也有很多数据库就可以使这些语句得到执行.

而如果你使用预编译语句.你传入的任何内容就不会和原来的语句发生任何匹配的关系.只要全使用预编译语句,你就用不着对传入的数据做任何过虑.而如果使用普通的statement,有可能要对drop,;等做费尽心机的判断和过虑.
