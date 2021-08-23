---
title: lua基本规则
date: 2021-08-03 14:36:04
tags: lua
categories: lua
---

## 注释

1. 单行注释 `--`
2. 多行注释 `--[[ --]]`

## 空

nil代表null如果想清除全局变量只需要`b=nil`

## Lua 数据类型

Lua 是动态类型语言，变量不要类型定义,只需要为变量赋值。 值可以存储在变量中，作为参数传递或结果返回。
Lua 中有 8 个基本类型分别为：nil、boolean、number、string、userdata、function、thread 和 table。
|数据类型|描述|
|--|--|
|nil|这个最简单，只有值nil属于该类，表示一个无效值（在条件表达式中相当于false）。|
|boolean|包含两个值：false和true。|
|number|表示双精度类型的实浮点数|
|string|字符串由一对双引号或单引号来表示|
|function|由 C 或 Lua 编写的函数|
|userdata|表示任意存储在变量中的C数据结构|
|thread|表示执行的独立线路，用于执行协同程序|
|table|Lua 中的表（table）其实是一个"关联数组"（associative arrays），数组的索引可以是数字、字符串或表类型。在 Lua 里，table 的创建是通过"构造表达式"来完成，最简单构造表达式是{}，用来创建一个空表。|

在lua中+只能用来作加法运算，不能拼接字符串，字符串拼接需要用..
字符串前加#计算字符串的长度

### table

lua中table如果删除中间的元素，后边的元素并不会补齐，而是保持原来的key

```lua
tab1 = {}
tab2 = {key=100,key2="3123"}
print(tab2.key)
print(tab2.key1)
print(tab2['key2'])
tab3 = {"appe",123,"esda"}
```

## 函数

- optional_function_scope: 该参数是可选的制定函数是全局函数还是局部函数，未设置该参数默认为全局函数，如果你需要设置函数为局部函数需要使用关键字 local。
- function_name: 指定函数名称。
- argument1, argument2, argument3..., argumentn: 函数参数，多个参数以逗号隔开，函数也可以不带参数。
- function_body: 函数体，函数中需要执行的代码语句块。
- result_params_comma_separated: 函数返回值，Lua语言函数可以返回多个值，每个值以逗号隔开。

```lua
optional_function_scope function function_name( argument1, argument2, argument3..., argumentn)
    function_body
    return result_params_comma_separated
end
```

不需要在函数前标定返回结果类型，可以无返回结果，单返回结果，多返回结果

**函数也可以作为参数传递**

```lua
do
    function foo(...)
        for i = 1, select('#', ...) do  -->获取参数总数
            local arg = select(i, ...); -->读取参数，arg 对应的是右边变量列表的第一个参数
            print("arg", arg);
        end
    end

	function testFun(fun,...)
		local arg = {...}
		for i,v in ipairs(arg) do
			fun(i,v)
		end
	end

	testFun(foo,1,2,3,4,5,6)
end

结果：
arg	1
arg	1
arg	2
arg	2
arg	3
arg	3
arg	4
arg	4
arg	5
arg	5
arg	6
arg	6
```

### 多返回值函数

```lua
function maximum (a)
    local mi = 1             -- 最大值索引
    local m = a[mi]          -- 最大值
    for i,val in ipairs(a) do
       if val > m then
           mi = i
           m = val
       end
    end
    return m, mi
end
m,mi = maximum({8,10,23,12,5})
print(m,mi)
```

### 可变参数

> Lua 函数可以接受可变数目的参数，和 C 语言类似，在函数参数列表中使用三点 ... 表示函数有可变的参数。

我们可以将可变参数赋值给一个变量,通过#来计算可变参数个数，或者直接通过select("#",...) 来计算可变参数个数
有时候我们可能需要几个固定参数加上可变参数，固定参数必须放在变长参数之前

- select('#', …) 返回可变参数的长度。
- select(n, …) 用于返回从起点 n 开始到结束位置的所有参数列表。 

...默认为arg，除了保存参数也会保存参数的个数在末尾，索引需要将arg={。。。}重新赋值一下

```lua
function average(...)
   result = 0
   local arg={...}    --> arg 为一个表，局部变量
   for i,v in ipairs(arg) do
      result = result + v
   end
   print("总共传入 " .. #arg .. " 个数")
   return result/#arg
end

print("平均值为",average(10,5,3,4,5,6))
```

## 变量

1. 局部变量的销毁是在所在语句块结束,变量前加local代表局部变量

```lua
do
	a = 10
	local b = 20
	print(a,b)
end
print(a,b)
10	20
10	nil
```

变量的赋值是先通过计算，算出右边的结果然后统一赋值给左边

```lua
a,b = b,a -- a = b b = a
print(a,b)
-- 其他语言可能是先把b赋值给a，然后再把a的值赋值给b，这样ab的值都是b了
-- 但是lua是先把，b和a的值算出，然后再赋值给a,b,所以语句执行后相当于ab的值调换
```

## 循环

### for 循环

Lua 编程语言中 for语句有两大类:

- 数值for循环
- 泛型for循环

var 从 exp1 变化到 exp2，每次变化以 exp3 为步长递增 var，并执行一次 "执行体"。exp3 是可选的，如果不指定，默认为1。

```lua
for var=exp1,exp2,exp3 do  
    <执行体>  
end
```

泛型 for 循环通过一个迭代器函数来遍历所有值，类似 java 中的 foreach 语句。
Lua 编程语言中泛型 for 循环语法格式:
是数组索引值，v是对应索引的数组元素值。ipairs是Lua提供的一个迭代器函数，用来迭代数组。

```lua
--打印数组a的所有值  
a = {"one", "two", "three"}
for i, v in ipairs(a) do
    print(i, v)
end 
```

#### pairs和ipairs的区别

> 区别在于，pairs可以遍历到表中所有的key，对于key的类型没有要求，遇到nil时可以跳过，不会影响后面的遍历.ipairs遍历时只能取key为整数值，遇到nil时终止遍历

### repeat...until 循环

Lua 编程语言中 repeat...until 循环语句不同于 for 和 while循环，for 和 while 循环的条件语句在当前循环执行开始时判断，而 repeat...until 循环的条件语句在当前循环结束后判断。

```lua
repeat
   statements
until( condition )
```

### 自定义迭代函数

```lua
for 变量列表 in 迭代函数,状态变量,控制变量 do
    循环体
end
```

1. 调用迭代函数，（吧状态变量和控制变量当作参数传递给 迭代函数）状态变量只会在第一次调用的时候赋值
2. 如果迭代函数的返回值为nil，退出for循环
    如果不是nil的话，把返回值赋值给变量列表，并执行循环体

```lua
function square(state,control)
	if control >= state then
		return nil
	else
		control = control + 1
		return control,control^2
	end
end

for i,j in square,9,0 do
	print(i,j)
end
```

## 运算符

- 不等于~=
- 求幂^
- 逻辑运算符 and or not

```lua
print(not 0) -- false，数值就是数值类型，不会自动转为布尔类型
print(~ 1) -- 会报错，不允许这么写
```

## 字符串

> 定义字符串的三种方式'' "" [[]]

## 表单table

```lua
-- table连接
table.concat("数组","连接符，可省略","起始索引，可省略","终止索引，可省略")
-- table插入，不指定在末尾插入
table.inseet("数组","插入位置","要插入数据")
-- table删除，会自动补齐
table.remove("数组","删除索引位置")
```

## 模块与包

模块类似于一个封装库，从 Lua 5.1 开始，Lua 加入了标准的模块管理机制，可以把一些公用的代码放在一个文件里，以 API 接口的形式在其他地方调用，有利于代码的重用和降低代码耦合度。

Lua 的模块是由变量、函数等已知元素组成的 table，因此创建一个模块很简单，就是创建一个 table，然后把需要导出的常量、函数放入其中，最后返回这个 table 就行。以下为创建自定义模块 module.lua，文件代码格式如下：

```lua
-- 文件名为 module.lua
-- 定义一个名为 module 的模块
module = {}
 
-- 定义一个常量
module.constant = "这是一个常量"
 
-- 定义一个函数
function module.func1()
    io.write("这是一个公有函数！\n")
end
 
local function func2()
    print("这是一个私有函数！")
end
 
function module.func3()
    func2()
end
 
return module
```

### require 函数

> Lua提供了一个名为require的函数用来加载模块。要加载一个模块，只需要简单地调用就可以了。例如：`require("<模块名>")`或者`require "<模块名>"`

执行 require 后会返回一个由模块常量或函数组成的 table，并且还会定义一个包含该 table 的全局变量。

```lua
-- test_module.lua 文件
-- module 模块为上文提到到 module.lua
require("module")
print(module.constant)
module.func3()

这是一个常量
这是一个私有函数！
```

或者给加载的模块定义一个别名变量，方便调用：

```lua
-- test_module2.lua 文件
-- module 模块为上文提到到 module.lua
-- 别名变量 m
local m = require("module")
print(m.constant)
m.func3()

这是一个常量
这是一个私有函数！
```

### 加载机制

> 对于自定义的模块，模块文件不是放在哪个文件目录都行，函数 require 有它自己的文件路径加载策略，它会尝试从 Lua 文件或 C 程序库中加载模块。
