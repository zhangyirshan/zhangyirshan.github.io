// let a = 12
// console.log(a.toString()) // =>'12'
// console.log((NaN).toString())

//null和undefined是禁止直接toString的
//(null).toString // =>报错
// 但是和undefined一样转换为字符串的结果就是'null'/'undefined'

// console.log([].toString())
// console.log([12].toString())
// console.log([12,23].toString())
// console.log(/^$/.toString())
//普通对象.toString()的结果是"[object Object]" => ?
//Object.prototype.toString方法不是转换为字符串的，而是用来检测数据类型的
console.log({name:'xxx',age:12}.toString())

//===========字符串拼接
//四则运算法则中，除加法之外，其余都是数学计算，只有假发可能存在字符串拼接（一旦遇到字符串，则不是数学运算，而是字符串拼接）
console.log('10'+10)//=》'1010'
//用Number来转换（默认）
console.log('10'-10)
console.log('10px'-10)//=》NaN-10=NaN

let a = 10 + null + true + [] + true + undefined + '珠峰' + null + 32 + false + {name:"sss"}
/*
10 + null -> 10 + 0 = 10
10 + true -> 10 + 1 = 11
11 + [] -> 11 + '' = '11' 空数组变为数字，先要经历变为空字符串，遇到字符串直接变为字符串拼接
*/
console.log(a)// 11trueundefined珠峰null32false[object Object]