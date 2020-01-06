let a = 10;
if (a <= 0) {
    console.log('哈哈')
} else if (a > 0 && a < 10) {
    console.log('呵呵')
} else {
    console.log('吼吼')
}
if(!a) {
    // 条件可以多样性：等于、大于、小于的比较或一个值或者取反等 =>最后都是要计算出TRUE或FALSE
}

// 三元运算符
// 条件？条件成立处理的事情：不成立处理的事情
// 1. 如果处理的事情比较多，我们用括号抱起来，每一件事情用逗号分隔
// 2. 如果不需要处理事情，可以使用null/undefined占位
a >= 10 ? console.log('呵呵') : console.log('吼吼')

(a > 0 && a < 20) ? (a++, console.log(a)) : null

// switch case: 一个变量再不同值情况下的不同操作
// 1. 每一种case情况结束后最好都加上BREAK
// 2. default等价于else，以上都不成立干的事情
// 3. 不加break，当前条件成立执行完成后，后面条件不论是否成立都要执行，指导遇到break为止
// 4. 每一种case情况的比较用的都是==="绝对相等"
let b = '10'
if(b === 1) {
    console.log('aaaaa')
}else if(b === 5){
    console.log('bbbbb')
}else if (b == 10){
    console.log('cccccc')
}else {
    console.log('jjjjjjjj')
}

switch(b){
    case 1 :
        console.log('aaaaa')
        break
    case 5 :
        console.log('bbbbb')
        break
    case 10 :
        console.log('ccccc')
        break
    default :
        console.log('jjjjjjjj')
}