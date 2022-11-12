import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStreamAPI {

    List<Employee> emps = Arrays.asList(
            new Employee("张三", 42, 9999.99, Employee.Status.BUSY),
            new Employee("李四", 59, 6669.99, Employee.Status.FREE),
            new Employee("王五", 18, 3333.99, Employee.Status.BUSY),
            new Employee("赵六", 15, 2222.99, Employee.Status.VOCATION),
            new Employee("田七", 12, 5555.99, Employee.Status.VOCATION),
            new Employee("田七", 13, 5555.99, Employee.Status.VOCATION),
            new Employee("田七", 18, 5555.99, Employee.Status.FREE),
            new Employee("田七", 18, 5555.99, Employee.Status.BUSY),
            new Employee("java", 21, 5555.99, Employee.Status.BUSY)
    );

    /**
     * 1.给定一个数字列表，如何返回一个由美国数的平方构成的列表呢？
     * 给定【1,2,3,4,5】，应返回【1,4,9,16,25】
     */
    @Test
    public void test1(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> collect = list.stream().map(e -> e * e).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 怎样用map和reduce方法数一数六中有多少个Employee呢？
     */
    @Test
    public void test2(){
        Integer reduce = emps.stream().map(employee -> 1).reduce(0, Integer::sum);
        System.out.println(reduce);
    }
}
