- Git 命令归纳

  要明确的是，所有的git相关（除了git push）的操作都是在本地git服务器上执行的，只有当你 git push后，才会把本地的Git操作同步到服务器上的同一分支

  - git --help    查看所有git命令
  - git branch insue2    创建insue2分支
  - git branch -d insue2    删除insue2分支
  - git switch insue2    切换到insue2分支
  - git add    添加到暂存区
  - git commit    提交到本地Git服务器
  - git push   推送
  - git pull  main    从远端main分支拉取（可能会产生冲突，需要处理）
  - git marge main    把当前分支合并到merge分支

  如果要把远程服务器上的分支合并，在本地是操作不了的，要去github/gitlab上提交merge请求，删除分支也一样

- lamda表达式

  - lambda表达式返回的其实都是一个类/接口的实现，是匿名内部类的简化写法

  - 基本使用

    - 不需要指定具体函数名，因为可以使用lambda表达式的接口都只有一个抽象方法，重写的也只能是该方法
    - 方法不需要参数类型，有自动类型推断

    ```java
    // Lambda表达式的书写形式
    Runnable run = () -> System.out.println("Hello World");// 1
    ActionListener listener = event -> System.out.println("button clicked");// 2
    Runnable multiLine = () -> {// 3 代码块
        System.out.print("Hello");
        System.out.println(" Hoolee");
    };
    BinaryOperator<Long> add = (Long x, Long y) -> x + y;// 4
    BinaryOperator<Long> addImplicit = (x, y) -> x + y;// 5 类型推断
    ```

  - 底层原理

    - 匿名内部类的原理

      ```JAVA
      new Thread(new Runnable(){
      			@Override
      			public void run(){
      				System.out.println("Anonymous Class Thread run()");
      			}
      		}).start();;
      ```

      会生成两个class文件，调用的时候会调用该类的方法。匿名内部类的this指的是新创建的对象，与外部this不同

    - lambda表达式的原理

      ```java
      new Thread(
      		() -> System.out.println("Lambda Thread run()")
      ).start();;
      ```

      不会生成两个class文件，这个类会被包装成一个私有方法，并通过*invokedynamic*指令进行调用。因此在lambda表达式中的this和外部的this相同。

    - 自定义lambda函数接口

      ```java
      // 自定义函数接口
      @FunctionalInterface
      public interface ConsumerInterface<T>{
      	void accept(T t);
      }
      ```

  - Lamdba表达式在集合中的使用
  
    由于Collection、List以及Map这些父接口新增了新方法，这些新方法都需要引入匿名内部类作为形参，这些接口都是函数式接口，因此可以使用lambda表达式进行简化操作
  
    ![image-20211029143345511](C:/Documents(%E8%B5%84%E6%96%99)/Learning/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88/img/image-20211029143345511.png)
  
    ```java
    // 使用forEach()结合Lambda表达式迭代
    ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too"));
    list.forEach( str -> {
            if(str.length()>3)
                System.out.println(str);
        });
    
    // 使用removeIf()结合Lambda表达式实现
    ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too"));
    list.removeIf(str -> str.length()>3); // 删除长度大于3的元素
    
    // 使用Lambda表达式实现
    ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too"));
    list.replaceAll(str -> {
        if(str.length()>3)
            return str.toUpperCase();
        return str;
    });
    
    // List.sort()方法结合Lambda表达式
    ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you", "too"));
    list.sort((str1, str2) -> str1.length()-str2.length());
    
    // 使用forEach()结合Lambda表达式迭代Map
    HashMap<Integer, String> map = new HashMap<>();
    map.put(1, "one");
    map.put(2, "two");
    map.put(3, "three");
    map.forEach((k, v) -> System.out.println(k + "=" + v));
    }
    
    // Java8使用Map.getOrDefault()
    // 存放返回value(4),不存在返回默认NoValue
    System.out.println(map.getOrDefault(4, "NoValue")); // 2
    
    // 不存在才put
    putIfAbsent(Object key,Object value);
    
    
    
    ```

- stream

  stream是Java8新提供的特性，旨在用于补充以及优化集合类。stream代表数据流，st它更注重对元素进行可计算的操作

  stream和集合的区别有：

  - stream本身不存储数据，数据是以管道的方式传输过来计算的，它是数据源的视图，对stream的操作不会影响到原数据源的数据

  - 为函数式编程而生

  - 惰式执行。对*stream*的操作分为为两类，**中间操作(\*intermediate operations\*)和结束操作(\*terminal operations\*)**，二者特点是：

    1. **中间操作总是会惰式执行**，调用中间操作只会生成一个标记了该操作的新*stream*，仅此而已。
    2. **结束操作会触发实际计算**，计算发生时会把所有中间操作积攒的操作以*pipeline*的方式执行，这样可以减少迭代次数。计算完成之后*stream*就会失效。

    区分中间操作和结束操作就是看函数返回的是否还是一个stream，是的话一般就是中间操作。

  - **可消费性**。*stream*只能被“消费”一次，一旦遍历过就会失效，就像容器的迭代器那样，想要再次遍历必须重新生成。

  流的操作是以管道的方式串起来的。流管道包含一个数据源，接着包含零到N个中间操作，最后以一个终点操作结束。

  ```JAVA
  stream.filter((str)->{return str.length()>3;}).forEach((str)->{
  System.out.println(str);});
  ```

  ![image-20211029152457173](C:/Documents(%E8%B5%84%E6%96%99)/Learning/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88/img/image-20211029152457173.png)

  - 基本用法

    ```java
    // 使用Stream.forEach()迭代
    Stream<String> stream = Stream.of("I", "love", "you", "too");
    stream.forEach(str -> System.out.println(str));
    
    // 保留长度等于3的字符串
    Stream<String> stream= Stream.of("I", "love", "you", "too");
    stream.filter(str -> str.length()==3)
        .forEach(str -> System.out.println(str));
    
    Stream<String> stream= Stream.of("I", "love", "you", "too", "too");
    stream.distinct()
        .forEach(str -> System.out.println(str));
    
    Stream<String> stream= Stream.of("I", "love", "you", "too");
    stream.sorted((str1, str2) -> str1.length()-str2.length())
        .forEach(str -> System.out.println(str));
    
    // map:对每个元素进行操作
    Stream<String> stream　= Stream.of("I", "love", "you", "too");
    stream.map(str -> str.toUpperCase())
        .forEach(str -> System.out.println(str));
    ```

    // TODO

    // https://objcoding.com/2019/03/04/lambda/#%E6%94%B6%E9%9B%86%E5%99%A8

​    



