---
title: Stream

date: 2022-10-15 13:26:21

summary: Stream | java流式处理及函数式方法

tags:

- TODO
- Stream

---


## 问题验证

### 两个并行流之间的执行顺序

如果一段程序中有两个并行流, 会执行完第一个并行流, 再去执行第二个并行流

```java
public class MyStream {
    public static void main(String[] args) {
        // 测试多个并行流的执行顺序
        IntStream.range(0, 1000)
                //转换成并行流
                .parallel()
                .forEach(x -> System.out.println("AAAAAAAAAAAAA: " + x + ": " + Thread.currentThread().getName()));
        System.out.println("======================== new Stream ========================");
        IntStream.range(0, 1000)
                //转换成并行流
                .parallel()
                .forEach(x -> System.out.println("BBBBBBBBBBBB: " + x + ": " + Thread.currentThread().getName()));


    }
}
```

