package 递归问题;

/*
    递归函数就是系统在帮你压栈

	所有的递归函数都可以改成非递归函数（自己压栈
		1.一个函数调用子过程之前，会把之前的信息完全保存，压到栈里面
		2.子过程执行结束后，会重新回到父过程压栈执行接下来的函数部分
		3.递归本质上就是不停的压栈

	递归行为分析复杂度
		master公式的使用：T(N) = a*T(N/b) + O(N^d)
			1) log(b,a) > d -> 复杂度为O(N^log(b,a))
			2) log(b,a) = d -> 复杂度为O(N^d * logN)
			3) log(b,a) < d -> 复杂度为O(N^d)

 */
public class dfj {
}
