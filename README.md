# Cool-master
     该项目会持续不断的更新!
     
     此项目，是在学些的过程中不断的总结。 在这里面我会用到现在最先进的技术，以及学习过程中的一些总结。 本人只是一个小菜鸟。如有不当的地方，
     请谅解！
     
     2017-4-26 >>>>>   greenDao  一种简介轻便的ORM  接入比较简单，greendao通过映射将代码自动生成. 省去了创建过程，以及sql的编写。
        而与其他几种对比来讲 从性能上来讲，它比其他几种框架要更好。 实验链接:http://www.jianshu.com/p/8287873d97cd




public class TestCase {
    public static int number;
    public static boolean isinited;
    public static void main(String[] args) {
        new Thread(
                () -> {
                    while (!isinited) {
                        Thread.yield();
                    }
                    System.out.println(number);
                }
        ).start();
        number = 20;
        isinited = true;
    }
}
