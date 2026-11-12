package demo.day02;

/**
 * 练习 2：学生类（面向对象基础）
 *
 * 需求文档：E:\Deepseek-prograss\first\学习任务\W2-练习2-学生类.md
 *
 * 这个类后面会直接用在 W3 的「学生管理系统」里，所以认真写。
 * 注意：所有字段都用 private，外部只能通过 getter 访问 —— 这叫封装。
 */
public class Student {

    private String name;
    private String id;
    private int chinese;
    private int math;
    private int english;

    // TODO 1：全参构造方法
    //   参数顺序：name, id, chinese, math, english
    //   里面把参数赋给同名的字段。注意 this.name = name 这种写法为什么需要 this
    public Student(String name, String id, int chinese, int math, int english){
        this.name = name;
        this.id = id;
        this.chinese = chinese;
        this.math = math;
        this.english = english;
    }

    // TODO 2：getter
    //   name / id / chinese / math / english 各写一个，共 5 个
    //   格式：public String getName() { return name; }
    public String getName(){return name;}
    public String getId(){return id;}
    public int getMath(){return math;}
    public int getChinese(){return chinese;}
    public int getEnglish(){return english;}


    // TODO 3：getTotal()
    //   返回三科总分（int）
    public int getTotal(){
        return (math + chinese + english);
    }

    // TODO 4：getAverage()
    //   返回三科平均分（double）
    //   注意：如果用 (chinese + math + english) / 3 会是整数除法，必须想办法变成浮点
    public double getAverage(){
        return (getTotal() / 3.0);
    }

    // TODO 5：toString()
    //   返回格式（注意是 String，不是 void）：
    //   Student{name='张三', id='001', 总分=255, 平均分=85.0}
    //   技巧：String.format("...%d...%.1f...", getTotal(), getAverage())
    //   在方法上写 @Override 让编译器帮你检查签名有没有写错
    @Override
    public String toString(){
        return String.format("name = %s, id = %s, 总分 = %d, 平均分 = %.1f", name, id, getTotal(), getAverage());
    }
}
