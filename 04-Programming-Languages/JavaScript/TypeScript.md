---
title: TypeScript
keywords: TypeScript
summary: TypeScript
tags:
  - TypeScript
categories:
  - 04-Programming-Languages
  - JavaScript
date: 2022-10-18 10:59:47
---

官方文档 https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#object-types

在线运行 https://www.typescriptlang.org/play

## 语法介绍

### 基础介绍

|关键字|名称|解释|用例|
|:---:|:---:|:---|:---|
|boolean|布尔值|最基本的数据类型就是简单的值|let isDone: boolean = false;|
|number|数字|和JavaScript一样，TypeScript里的所有数字都是浮点数。<br>这些浮点数的类型是 number。<br>除了支持十进制和十六进制字面量，<br>TypeScript还支持ECMAScript 2015中引入的二进制和八进制字面量。|let decLiteral: number = 6;<br>let hexLiteral: number = 0xf00d;<br>let binaryLiteral: number = 0b1010;<br>let octalLiteral: number = 0o744;|
|string|字符串|我们使用 string表示文本数据类型。<br>和JavaScript一样，可以使用双引号（ "）或单引号（'）表示字符串。<br>还可以使用模板字符串<br>这种字符串是被反引号包围（ `），并且以${ expr }这种形式嵌入表达式。|let myName: string = \'bob\';<br>myName = \'smith\';<br>let sentence: string = \`Hello, my name is ${ myName }.\`;<br>console.log(sentence);|
|array|数组|有两种方式可以定义数组。<br>第一种，可以在元素类型后面接上 []，表示由此类型元素组成的一个数组<br>第二种方式是使用数组泛型，Array<元素类型>|let myList1: number[] = [1, 2, 3];<br>console.log(myList1);<br>let myList2: Array\<number> = [1, 2, 3];<br>console.log(myList2);|
|Tuple|元组|元组类型允许表示一个已知元素数量和类型的数组，各元素的类型不必相同。<br>当访问一个已知索引的元素，会得到正确的类型<br>当访问一个越界的元素，会使用联合类型替代<br>[扩展使用](https://www.typescriptlang.org/docs/handbook/2/objects.html#tuple-types)|let x: [string, number];<br>let x: [boolean, ...string, number];<br>|
|enum|枚举|使用枚举类型可以为一组数值赋予友好的名字。<br>使用枚举可以更简单的表明意图或创建一组特殊意义的集合<br>枚举支持数字类型和字符串类型<br>[扩展使用](https://www.typescriptlang.org/docs/handbook/enums.html)|enum Color {RAD = 'red', BLUD = 0}<br>console.log(Color.RAD) // red<br>console.log(Color[0]) // BLUD|
|any|Any|编程阶段还不清楚类型的变量指定一个类型|let notSure: any = 4;<br>notSure = "maybe a string instead";<br>notSure = false; // okay, definitely a boolean|
|void<br>null<br>undefined|Void<br>Null<br>Undefined|void类型像是与any类型相反，它表示没有任何类型。<br>当一个函数没有返回值时，你通常会见到其返回值类型是 void.<br>声明一个void类型的变量没有什么大用，因为你只能为它赋予undefined和null.<br>undefined和null两者各自有自己的类型分别叫做undefined和null.<br>默认情况下null和undefined是所有类型的子类型.|let unusable: void = undefined;<br>// Not much else we can assign to these variables!<br>let u: undefined = undefined;<br>let n: null = null;|
|never|Never|never类型表示的是那些永不存在的值的类型<br>never类型是任何类型的子类型，也可以赋值给任何类型|// 返回never的函数必须存在无法达到的终点<br>function error(message: string): never {<br>    throw new Error(message);<br>}|
|object|Object|指任何带有属性的JavaScript值-非基础类型<br>对象类型也可以指定其部分或全部属性是可选的。|function printName(obj: { first: string; last?: string }) {<br>  // ...<br>}<br>// Both OK<br>printName({ first: "Bob" });<br>printName({ first: "Alice", last: "Alisson" });|
|type|Type Aliases|多次使用同一个类型并以一个名字来引用它是很常见的。|type Point = {<br>  x: number;<br>  y: number;<br>};<br>// Exactly the same as the earlier example<br>function printCoord(pt: Point) {<br>  console.log("The coordinate's x value is " + pt.x);<br>  console.log("The coordinate's y value is " + pt.y);<br>}<br>printCoord({ x: 100, y: 100 });|
|interface|Interfaces|接口声明是命名一个对象类型的另一种方式。|interface Point {<br>  x: number;<br>  y: number;<br>}<br>function printCoord(pt: Point) {<br>  console.log("The coordinate's x value is " + pt.x);<br>  console.log("The coordinate's y value is " + pt.y);<br>}<br>printCoord({ x: 100, y: 100 });|
|class|Class|TypeScript实现了 `class` 关键字 <br> [扩展使用](https://www.typescriptlang.org/docs/handbook/2/classes.html#class-members)||

#### 元组使用详情

```ts
let x: [string, number]; // Declare a tuple type
// Initialize it
x = ['hello', 10]; // OK
// Initialize it incorrectly
x = [10, 'hello']; // Error
console.log(x[0].substr(1)); // OK
console.log(x[1].substr(1)); // Error, 'number' does not have 'substr'
x[3] = 'world'; // OK, 字符串可以赋值给(string | number)类型
console.log(x[5].toString()); // OK, 'string' 和 'number' 都有 toString
x[6] = true; // Error, 布尔不是(string | number)类型
```

##### 元组的进阶使用

Tuples can also have rest elements, which have to be an array/tuple type.

```ts
type StringNumberBooleans = [string, number, ...boolean[]];
type StringBooleansNumber = [string, ...boolean[], number];
type BooleansStringNumber = [...boolean[], string, number];
```

- `StringNumberBooleans` describes a tuple whose first two elements are `string` and `number` respectively, but which may have any number of `boolean`s following.
- `StringBooleansNumber` describes a tuple whose first element is `string` and then any number of `boolean`s and ending with a `number`.
- `BooleansStringNumber` describes a tuple whose starting elements are any number of `boolean`s and ending with a `string` then a `number`.

A tuple with a rest element has no set “length” - it only has a set of well-known elements in different positions.

```ts
const a: StringNumberBooleans = ["hello", 1];
const b: StringNumberBooleans = ["beautiful", 2, true];
const c: StringNumberBooleans = ["world", 3, true, false, true, false, true];
```

Why might optional and rest elements be useful? Well, it allows TypeScript to correspond tuples with parameter lists. Tuples types can be used in [rest parameters and arguments](https://www.typescriptlang.org/docs/handbook/2/functions.html#rest-parameters-and-arguments), so that the following:

```ts
function readButtonInput(...args: [string, number, ...boolean[]]) {
  const [name, version, ...input] = args;
  // ...
}
```

is basically equivalent to:

```ts
function readButtonInput(name: string, version: number, ...input: boolean[]) {
  // ...
}
```

This is handy when you want to take a variable number of arguments with a rest parameter, and you need a minimum number of elements, but you don’t want to introduce intermediate variables.

#### 枚举的使用详情

- The enum member is initialized with a constant enum expression. A constant enum expression is a subset of TypeScript expressions that can be fully evaluated at compile time. An expression is a constant enum expression if it is:

    1. a literal enum expression (basically a string literal or a numeric literal)
    2. a reference to previously defined constant enum member (which can originate from a different enum)
    3. a parenthesized constant enum expression
    4. one of the `+`, `-`, `~` unary operators applied to constant enum expression
    5. `+`, `-`, `*`, `/`, `%`, `<<`, `>>`, `>>>`, `&`, `|`, `^` binary operators with constant enum expressions as operands

  It is a compile time error for constant enum expressions to be evaluated to `NaN` or `Infinity`.

In all other cases enum member is considered computed.

```
enum FileAccess {
  // constant members
  None,
  Read = 1 << 1,
  Write = 1 << 2,
  ReadWrite = Read | Write,
  // computed member
  G = "123".length,
}
```

```ts
enum LogLevel {
    ERROR,
    WARN,
    INFO,
    DEBUG,
}

/**
 * This is equivalent to:
 * type LogLevelStrings = 'ERROR' | 'WARN' | 'INFO' | 'DEBUG';
 */
type LogLevelStrings = keyof typeof LogLevel;

function printImportant(key: LogLevelStrings, message: string) {
    const num = LogLevel[key];
    if (num <= LogLevel.WARN) {
        console.log("Log level key is:", key);
        console.log("Log level value is:", num);
        console.log("Log level message is:", message);
    }
}

printImportant("ERROR", "This is a message");
```

#### 对象的使用详情
In JavaScript, the fundamental way that we group and pass around data is through objects. In TypeScript, we represent those through *object types*.

As we’ve seen, they can be anonymous:

```
function greet(person: { name: string; age: number }) {
  return "Hello " + person.name;
}
```

or they can be named by using either an interface

```
interface Person {
  name: string;
  age: number;
}
 
function greet(person: Person) {
  return "Hello " + person.name;
}
```

or a type alias.

```
type Person = {
  name: string;
  age: number;
};
 
function greet(person: Person) {
  return "Hello " + person.name;
}
```

In all three examples above, we’ve written functions that take objects that contain the property `name` (which must be a `string`) and `age` (which must be a `number`).

#### 类型别名和接口的区别

类型别名和接口非常相似，在很多情况下你可以在它们之间自由选择。几乎所有接口的功能都可以在类型中使用，关键的区别在于，类型不能被重新打开以添加新的属性，而接口则总是可以扩展的。

You’ll learn more about these concepts in later chapters, so don’t worry if you don’t understand all of these right away.

- Prior to TypeScript version 4.2, type alias names [*may* appear in error messages](https://www.typescriptlang.org/play?#code/PTAEGEHsFsAcEsA2BTATqNrLusgzngIYDm+oA7koqIYuYQJ56gCueyoAUCKAC4AWHAHaFcoSADMaQ0PCG80EwgGNkALk6c5C1EtWgAsqOi1QAb06groEbjWg8vVHOKcAvpokshy3vEgyyMr8kEbQJogAFND2YREAlOaW1soBeJAoAHSIkMTRmbbI8e6aPMiZxJmgACqCGKhY6ABGyDnkFFQ0dIzMbBwCwqIccabcYLyQoKjIEmh8kwN8DLAc5PzwwbLMyAAeK77IACYaQSEjUWZWhfYAjABMAMwALA+gbsVjoADqgjKESytQPxCHghAByXigYgBfr8LAsYj8aQMUASbDQcRSExCeCwFiIQh+AKfAYyBiQFgOPyIaikSGLQo0Zj-aazaY+dSaXjLDgAGXgAC9CKhDqAALxJaw2Ib2RzOISuDycLw+ImBYKQflCkWRRD2LXCw6JCxS1JCdJZHJ5RAFIbFJU8ADKC3WzEcnVZaGYE1ABpFnFOmsFhsil2uoHuzwArO9SmAAEIsSFrZB-GgAjjA5gtVN8VCEc1o1C4Q4AGlR2AwO1EsBQoAAbvB-gJ4HhPgB5aDwem-Ph1TCV3AEEirTp4ELtRbTPD4vwKjOfAuioSQHuDXBcnmgACC+eCONFEs73YAPGGZVT5cRyyhiHh7AAON7lsG3vBggB8XGV3l8-nVISOgghxoLq9i7io-AHsayRWGaFrlFauq2rg9qaIGQHwCBqChtKdgRo8TxRjeyB3o+7xAA), sometimes in place of the equivalent anonymous type (which may or may not be desirable). Interfaces will always be named in error messages.
- Type aliases may not participate [in declaration merging, but interfaces can](https://www.typescriptlang.org/play?#code/PTAEEEDtQS0gXApgJwGYEMDGjSfdAIx2UQFoB7AB0UkQBMAoEUfO0Wgd1ADd0AbAK6IAzizp16ALgYM4SNFhwBZdAFtV-UAG8GoPaADmNAcMmhh8ZHAMMAvjLkoM2UCvWad+0ARL0A-GYWVpA29gyY5JAWLJAwGnxmbvGgALzauvpGkCZmAEQAjABMAMwALLkANBl6zABi6DB8okR4Jjg+iPSgABboovDk3jjo5pbW1d6+dGb5djLwAJ7UoABKiJTwjThpnpnGpqPBoTLMAJrkArj4kOTwYmycPOhW6AR8IrDQ8N04wmo4HHQCwYi2Waw2W1S6S8HX8gTGITsQA).
- Interfaces may only be used to [declare the shapes of objects, not rename primitives](https://www.typescriptlang.org/play?#code/PTAEAkFMCdIcgM6gC4HcD2pIA8CGBbABwBtIl0AzUAKBFAFcEBLAOwHMUBPQs0XFgCahWyGBVwBjMrTDJMAshOhMARpD4tQ6FQCtIE5DWoixk9QEEWAeV37kARlABvaqDegAbrmL1IALlAEZGV2agBfampkbgtrWwMAJlAAXmdXdy8ff0Dg1jZwyLoAVWZ2Lh5QVHUJflAlSFxROsY5fFAWAmk6CnRoLGwmILzQQmV8JmQmDzI-SOiKgGV+CaYAL0gBBdyy1KCQ-Pn1AFFplgA5enw1PtSWS+vCsAAVAAtB4QQWOEMKBuYVUiVCYvYQsUTQcRSBDGMGmKSgAAa-VEgiQe2GLgKQA).
- Interface names will [*always* appear in their original form](https://www.typescriptlang.org/play?#code/PTAEGEHsFsAcEsA2BTATqNrLusgzngIYDm+oA7koqIYuYQJ56gCueyoAUCKAC4AWHAHaFcoSADMaQ0PCG80EwgGNkALk6c5C1EtWgAsqOi1QAb06groEbjWg8vVHOKcAvpokshy3vEgyyMr8kEbQJogAFND2YREAlOaW1soBeJAoAHSIkMTRmbbI8e6aPMiZxJmgACqCGKhY6ABGyDnkFFQ0dIzMbBwCwqIccabcYLyQoKjIEmh8kwN8DLAc5PzwwbLMyAAeK77IACYaQSEjUWY2Q-YAjABMAMwALA+gbsVjNXW8yxySoAADaAA0CCaZbPh1XYqXgOIY0ZgmcK0AA0nyaLFhhGY8F4AHJmEJILCWsgZId4NNfIgGFdcIcUTVfgBlZTOWC8T7kAJ42G4eT+GS42QyRaYbCgXAEEguTzeXyCjDBSAAQSE8Ai0Xsl0K9kcziExDeiQs1lAqSE6SyOTy0AKQ2KHk4p1V6s1OuuoHuzwArMagA) in error messages, but *only* when they are used by name.

For the most part, you can choose based on personal preference, and TypeScript will tell you if it needs something to be the other kind of declaration. If you would like a heuristic, use `interface` until you need to use features from `type`.

## 应用实例

### 根据不同后端环境切换接口集

#### 应用场景

+ 前端使用同样的代码, 接口区分单体/微服务, 故需要根据配置使用不用的接口文档
+ vue2 升级到 vue3 保证语法规范且尽量少的修改

#### vue2使用样例

```js
// 全局参数
const path = process.env.VUE_APP_SERVER_TYPE
const system = require('@api/api-' + path)
module.exports = system
// import { demoUrl } from '@/api/api'
```

#### require/exports和import/export的区别

require/exports和import/export https://blog.csdn.net/xiaoxiaoluckylucky/article/details/118437651
require/exports与import/export，有啥不一样的 https://blog.51cto.com/u_15089765/2600897

# 方式1: 定义所有接口, 根据变量动态加载

```ts
// 需要导出default才能被外部直接用 {使用}
// export const defaultApi: PlatformApi = import.meta.env.VITE_GLOB_APP_SERVER_TYPE === 'vue' ? apiVue : apiCloud;
const defaultApi: PlatformApi =
    import.meta.env.VITE_GLOB_APP_SERVER_TYPE === 'vue' ? apiVue : apiCloud;
export default defaultApi;

export interface PlatformApi {
    demoUrl?: string
}
```

```ts
// ROORO: No matching export in "src/api/api.ts" for import "demoUrl"
import {demoUrl} from '/@/api/api';
import defaultApi from '/@/api/api';

const {demoUrl} = defaultApi
console.log(defaultApi.demoUrl)
```

+ 使用时需要通过`defaultApi`对象二次导入

# 方式2 替换构建路径

vite.config.ts

```ts
export default ({command, mode}: ConfigEnv): UserConfig => {
    const {VITE_GLOB_APP_SERVER_TYPE} = viteEnv;
    return {
        resolve: {
            alias: [
                {
                    // '/@/api/api' 该路径过于常用, 会覆盖别的匹配模式
                    find: '/@/api/api-zh',
                    replacement: `${pathResolve('src')}/api/api-${VITE_GLOB_APP_SERVER_TYPE}`,
                },
            ],
        },
    };
};
```

```ts
import {demoUrl} from 'api-zh';
```

+ 方法名有写错的风险

# 方式3 moduleSuffixes

moduleSuffixes https://www.typescriptlang.org/tsconfig#moduleSuffixes

tsconfig.json

```json
{
  "compilerOptions": {
    "moduleSuffixes": [
      ".service-vue",
      ".service-cloud",
      ""
    ]
  }
}
```

+ 原来的文件改名为 api.service-vue&api.service-cloud
+ 引用正常引用即可 `官方提示需要 import * as Api from '/@/api/api';`

```js
import {demoUrl} from '/@/api/api';
```

+ 版本 typescript 4.7.0+ & node16+ ; 舍弃

