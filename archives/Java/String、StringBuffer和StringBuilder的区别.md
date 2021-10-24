字符串类型在所有的编程语言中都是特殊的类型，因为它最基础也最常用。

## String

String是Immutable不可变类的典型实现，相关的设计模式是不可变模式。所有的字段都是用final修饰，好处是提高多线程程序的性能，当多个线程共享同一个String类型时不需要考虑线程安全问题，降低多线程程序复杂度。

由于String不可变，因此拼接、裁剪字符串等操作都会产生新的字符串，效率较低。

```java
String s = “a” + ”b“
```

在JDK8中，字符串拼接操作会被转化为StringBuilder操作

在JDK9中，提供了StringConcatFactory作为字符串拼接的入口，不过还是依赖StringBuider

无论是JDK8还是JDK9，拼接字符串都要在堆中new新的对象

### 字符串常量池

为了避免产生大量的Strng对象，引入了字符串常量池机制，字符串常量池位于永久代，可以复用字符串对象

![image-20210424225209906](https://i.loli.net/2021/06/27/6OgZ3wSEvpdHftT.png)

```JAVA
String s = "a";
```

如果是直接赋值的方式，会先检查字符串常量池中有无相同的字符串对象引用。如果有返回对象引用，如果没有在堆中创建对象并把对象引用放入常量池

```JAVA
String s = new String("a");
```

如果是new方式的话则不放入常量池，也不会从常量池中找。那new出来的字符对象是不是不能放入常量池，也不是的，Java 6 之后提供了**intern**方法，能够提供JVM将new出来的字符串对象放入常量池以供复用

```java
String s = new String("a");
s.intern()
```

但是并不推荐使用intern方法，因为：

- 开发不方便，开发阶段也无法预料哪些字符串被大量重复使用
- 会造成永久代溢出，永久代也不会被FULLGC之外垃圾收集回收，导致oom

## StringBuffer和StringBuilder

底层是char类型的数组，后改为byte类型的数组，默认数组大小为16，都继承了AbstractStringBuilder

- StringBuffer是线程安全的，通过synchronized来实现线程安全
- StringBuilder是线程不安全的，通过synchronized来实现线程安全