//console.log([val]):在控制台输出内容
//==:进行比较
// console.log(1==2)
// console.log('AA'==NaN)
// console.log(10==NaN)
// console.log(NaN==NaN)
// console.log('---------------')

//isNaN([val])
// console.log(isNaN(10))//false
// console.log(isNaN('AAA'))//true
/*
 * Number('AAA') => NaN
 * isNaN(NaN) =>true
 */
// console.log(isNaN('10'))//false
/*
 * Number('10') => 10
 * isNaN(10) =>FALSE
 */
// console.log(isNaN(NaN))//ture
//把字符串转换为数字，只要字符串中包含任意一个非有效数字字符（第一个点除外）结果都是NaN，空字符串会变为数字零
// console.log(Number('12.5'))//12.5
// console.log(Number('12.5px'))//NaN
// console.log(Number('12.5.4'))//NaN
// console.log(Number(''))//0
// console.log(Number(true))//1
// console.log(Number(false))//0
// console.log(isNaN(false))//false
// console.log(Number(null))//0
// console.log(Number(undefined))//NaN
// //把引用数字类型转换为数字，是先把它基于toString方法转换为字符串，然后再转换为数字
// console.log(Number({name:'10'}))//NaN
// console.log(Number({}))//NaN
// //{}/{xxx:'xxx'}.toString() => "[object Object]" => NaN
// console.log(Number([]))//0
// //[].toString => '' => 0
// console.log(Number([12]))//12
// //[12].toString => '12' => 12
// console.log(Number([12,23]))//NaN
//[12,23].toString => '12,23' => NaN

let str = '12.5px'
console.log(Number(str))//NaN
console.log(parseInt(str))//12
console.log(parseFloat(str))//12.5
console.log(parseFloat('width:12.5px'))//NaN
console.log('10'==10)