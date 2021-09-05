## 关于equals()和hashcode()的一些约定


本文章主要讨论和回答一下几个问题：

- equals()的四大特性

- equals()和hashcode()之间的关系，为什么我们经常说这两个方法要么都重写，要么都不重写？

- HashMap、HashSet等容器为什么要求一定要重写equals()以及hashcode()

## equals()

equals和hashcode方法我们都很了解，是Object类中的定义的方法，这意味着所有的类都隐式实现了这两个方法。

Object类中的equals方法的默认实现是比较对象标识（根据对象头信息），但是这个对我们没有任何意义。因此一般情况下我们要重写equals方法

equals方法一般有以下四个约定：

- 自反：对象必须等于自身

- 对称：x.equals(y) 必须返回与 y.equals(x) 相同的结果

- 传递性：如果 x.equals(y) 和 y.equals(z) 那么 x.equals(z)

- 一致：仅当包含在 equals() 中的属性发生更改时，equals() 的值才应更改

使用IDEA智能重写equals方法如下，比较两个对象相关属性的值是否全部一致。：

```JAVA
public class Student {
    private String name;
    private int age;
}
```

```JAVA
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name);
    }
```

## hashcode()

hashcode方法也同样定义在Object类中，返回一个整数，**表示该类的实例状态**，要根据类的相等性定义来计算这个值，也就是说hashcode方法调用了equals方法，因此重写hashcode必须先重写equals，这样类的hashcode值才有意义。

hashcode同样有取得共识的约定：

- 内部一致性：只有当 equals() 中的属性发生变化时，hashCode() 的值才会发生变化
- **相等一致性：彼此相等的对象必须返回相同的 hashCode**
- hash碰撞：不相等的对象可能具有相同的哈希码

从第二个约定我们可以推出，重写equals方法也必须同时重写hashcode方法，不然就违反了第二个规定

看到这里，我想我们已经解决了前面提出的第二个问题，equals和hashcode必须都被重写或者都不重写

但是，这只是一个约定，并非强制要求，如果不遵循这个约定会有什么问题呢？我们通过hashmap来举例

## hashmap的key如何实现唯一性

我们知道map为了保证map的key是唯一的，我们需要重写key类的hashcode方法和equals方法。为什么呢？因为

key的添加过程是这样的：

- 先查看key的hashcode是否已经存在

  - 如果不存在，说明当前容器没有此key，直接添加

  - 如果存在，有可能是相同的key，也有可能是产生了hash碰撞。使用equals进行进一步比较

因此使用hashmap必须重写这两个方法

如果不重写的话，可能会有重复的key被放入map中。举个例子：

```JAVA
        HashMap<Student, Integer> studentIntegerHashMap = new HashMap<Student, Integer>();
        Student tom1 = new Student("tom", 11);
        Student tom2 = new Student("tom", 11);
        studentIntegerHashMap.put(tom1,1);
        studentIntegerHashMap.put(tom2,1);
```

正常情况下tom2是不会被添加到map集合中的，但是如果你不重写hashcode方法，使用的就是本地的hashcode方法，这两个对象的hashcode一定不同，因此都能被添加进集合中，这显然是我们不想看到的。至于HashSet，有的朋友应该知道，HashSet的底层是通过HashMap实现的，因此也同样要实现这两个方法才能“去重”

## 总结

在本篇文章中，我们讨论了 equals() 和 hashCode() 的约定和使用。我们应该记住以下几点：

- 如果我们覆盖 equals()，则始终覆盖 hashCode()，反过来也一样

- 考虑使用 IDE 或第三方库来生成 equals() 和 hashCode() 方法

## 参考

- [杨晓峰Java核心技术第9讲 | 对比Hashtable、HashMap、TreeMap有什么不同？](https://time.geekbang.org/column/intro/100006701)
- [Java equals() and hashCode() Contracts](https://www.baeldung.com/java-equals-hashcode-contracts#)







