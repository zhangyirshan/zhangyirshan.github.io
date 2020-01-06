// let a = 12
// let b = a
// b = 13
// console.log(a)

// let n = { name: '张议' }
// let m = n
// m.name = '周家豪'
// console.log(n.name)

// let n = [10,20]
// let m = n
// let x = m
// m[0] = 100
// x = [30,40]
// x[0] = 200
// m = x
// m[1] = 300
// n[2] = 400
// // n,m,x分别时多少
// console.log('n:',n)
// console.log('m:',m)
// console.log('x:',x)
// 答案 n：【100，20，400】m：【200，300】x：【200，300】

let a = {
    n:1
}
let b = a
a.x = a = {
    n:2
}
console.log(a.x)
console.log(b)