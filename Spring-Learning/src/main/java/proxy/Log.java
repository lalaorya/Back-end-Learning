package proxy;

import org.aspectj.lang.ProceedingJoinPoint;

public class Log {
    public void before(){
        System.out.println("打日志");
    }

    public void after(){
        System.out.println("关闭资源");
    }

    public void around(ProceedingJoinPoint jp){

        try {
            System.out.println("环绕前");
            jp.proceed();
            System.out.println("环绕后");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
