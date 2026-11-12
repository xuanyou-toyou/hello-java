package demo.day02;

import java.util.ArrayList;
import java.util.List;

/**
 * 练习 2 的使用方：创建学生、放进列表、做统计。
 *
 * 需求文档：E:\Deepseek-prograss\first\学习任务\W2-练习2-学生类.md
 */
public class StudentDemo {

    public static void main(String[] args) {

        // TODO 6：创建 3 个 Student 对象，名字/学号/三科成绩自己编
        //   例：Student s1 = new Student("张三", "001", 85, 92, 78);
        Student s1 = new Student("张三", "001", 85, 92, 78);
        Student s2 = new Student("李四", "002", 99, 62, 75);
        Student s3 = new Student("王五", "003", 20, 30, 18);

        // TODO 7：把这 3 个学生放进一个 List<Student> 里
        //   提示：List<Student> list = new ArrayList<>();  记得 import java.util.ArrayList
        List<Student> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);

        // TODO 8：用 for 循环遍历，打印每个学生
        //   直接 System.out.println(student) 就会自动调用 toString()
        for(Student s : list){
            System.out.println(s);
        }

        // TODO 9：找出总分最高的学生并打印他的名字
        //   提示：先假设第一个最高，然后逐个比较
        Student max = s1;
        for(int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            if(s.getTotal() > max.getTotal()){
                max = s;
            }
        }
        System.out.println("总分最高的学生是: " + max.getName());


        // TODO 10：算全班平均分（三个学生的平均分再取平均）
        //   提示：遍历累加 student.getAverage()，最后除以 list.size()
        //   注意除以 list.size() 时要转成 double
        double average = 0;
        for(int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            average += s.getAverage();
            if(i == list.size() - 1){
                System.out.printf("全班平均分是: %.1f\n", average / list.size() * 1.0);
            }
        }


        System.out.println("— 练习 2 运行结束 —");
    }
}
