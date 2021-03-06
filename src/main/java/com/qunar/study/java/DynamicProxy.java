package com.qunar.study.java;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by dujian on 2019/11/24
 * jdk代理的方式是直接创建class文件的方式，生成代理类的接口
 * 并且在InvocationHandler中写出方法执行前和执行后的处理即可.
 * <p>
 * jdk自带代理
 */
public class DynamicProxy {
    interface ITest{
        void print();

        void print2();
    }

    interface ITest2 {
        void print3();
    }

    class TestDemo implements ITest, ITest2 {
        @Override
        public void print() {
            System.out.println("实际调用:TestDemo");
        }

        @Override
        public void print2() {
            System.out.println("实际调用2:TestDemo");
        }

        @Override
        public void print3() {
            System.out.println("实际调用3:TestDemo");
        }
    }

    class JDKProxy<T> implements InvocationHandler {
        private T targetObject;

        public Object newProxy(T targetObject) {
            this.targetObject = targetObject;
            return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                    targetObject.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("代理类前置工作");
            Object result = method.invoke(targetObject, args);
            System.out.println("代理类后置工作");
            return result;
        }
    }

    @Test
    public void test() {
        JDKProxy<ITest> jdkProxy = new JDKProxy<>();
        TestDemo test = (TestDemo) jdkProxy.newProxy(new TestDemo());
        test.print();
        test.print2();
        test.print3();
    }

}
