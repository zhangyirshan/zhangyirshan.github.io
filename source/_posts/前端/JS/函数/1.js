// 求两个数的和,算完和后乘以10，然除以2
// sum使函数名，代表这个函数本身
// sum（）是让函数执行，代表的使函数执行返回的结果
function sum(a,b) {
    console.log('形参变量',a,b)
    if(a === undefined){
        a = 0
    }
    if (typeof b === 'undefined') { 
        b = 0
    }
    console.log('结果',(a + b)*5)
    return (a + b)*5
}

// ========= 形参的细节
// 创建函数的时候我们设置了形参变量，但如果执行的时候并没有给传递对应的实参值，那么形参变量默认的值是：undefined
sum()
sum(10)
sum(10,20)
sum(10,20,30)

// =========== 函数中的返回值
// 函数执行的时候，函数体内部创建的变量我们是无法获取和操作的（闭包），如果想要获取内部信息，我们需要基于return返回值机制，把信息返回才可以
// return的一定是值
// 没有写return，函数默认返回值是undefined
console.dir(console.log)

function sum1(n,m){
    if(n === undefined || m === undefined){
        // 函数体中遇到return，后面代码则不再执行了
        return
    }
    let result = n + m
}

// ========== 匿名函数
// 匿名函数之函数表达式：把一个匿名函数本身作为值赋值给其他东西，这种函数一般不是手动触发执行，而是靠其他程序驱动触发执行
// （例如：触发某个事件的时候把它执行等）
document.body.onclick = function () {}
setTimeout(function(){},1000) // =》设置定时器，1000MS后执行匿名函数

// 匿名函数之自执行函数：创建完一个匿名函数，紧接着就把当前函数加小括号执行
(function(n){
    // n => 100
})(100);// 创建完成后立刻执行
