package demo.day03;

import demo.day02.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * W3：学生管理系统（控制台版）
 *
 * 需求文档：E:\Deepseek-prograss\first\学习任务\W3-学生管理系统.md
 *
 * 复用 demo.day02.Student 这个类。
 * 菜单循环、输入工具方法已经写好了，你只需要实现 TODO 1~5。
 */
public class StudentManager {

    private static final List<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== 学生管理系统 ===");
        students.addAll(StudentStorage.load());
        System.out.println("已加载 " + students.size() + " 条学生记录");
        while (true) {
            showMenu();
            int choice = readInt("请选择：");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> listAll();
                case 3 -> findById();
                case 4 -> deleteById();
                case 5 -> updateStudent();
                case 0 -> {
                    System.out.println("已退出。");
                    return;
                }
                default -> System.out.println("没有这个选项，请重新选择。");
            }
            StudentStorage.save(students);
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("""
                1. 添加学生
                2. 查看全部
                3. 按学号查询
                4. 删除学生
                5. 修改学生
                0. 退出""");
    }

    // ============================================================
    //  TODO 1：添加学生
    // ============================================================
    private static void addStudent() {
        // 步骤：
        // 1) 依次读入：姓名、学号、语文、数学、英语
        //    用 readText("姓名：") 和 readInt("语文：")
        Student student = new Student(readText("姓名："), readText("学号："), readInt("语文："), readInt("数学："), readInt("英语："));
        // 2) 先检查学号是否已存在 —— 遍历 students，用 getId().equals(新学号) 比较
        //    字符串比较必须用 equals，不能用 ==
        // 3) 已存在 → 打印「学号 xxx 已存在」并 return
        // 4) 不存在 → new Student(...)，add 进 students，打印「添加成功」
        for(Student s : students){
            if(s.getId().equals(student.getId())){
                System.out.println("您输入的学号重复了，请重新输入！");
                return;
            }

        }
        students.add(student);
        System.out.println("添加成功");

    }

    // ============================================================
    //  TODO 2：查看全部
    // ============================================================
    private static void listAll() {
        // 步骤：
        // 1) students 是空的 → 打印「暂无学生」并 return
        // 2) 否则先打印表头，再遍历打印每个学生
        //    需要展示：学号 姓名 语文 数学 英语 总分 平均分
        //    提示：printf("%-8s %-8s %4d %4d %4d %5d %6.1f%n", ...)

        if(students.isEmpty()){
            System.out.println("「暂无学生」");
        }else{
            System.out.println("学号      姓名      语文  数学  英语   总分  平均分");
            for(Student s : students){
                System.out.printf("%-8s %-8s %4d %4d %4d %5d %6.1f%n", s.getId(), s.getName(), s.getChinese(), s.getMath(), s.getEnglish(), s.getTotal(), s.getAverage());
            }
        }


    }

    // ============================================================
    //  TODO 3：按学号查询
    // ============================================================
    private static void findById() {
        // 步骤：
        // 1) 读入学号
        // 2) 遍历查找，找到就打印这个学生并 return
        // 3) 循环结束仍未找到 → 打印「未找到学号为 xxx 的学生」
        String id = readText("请输入需要查询的学生学号：");
        for(Student s : students){
            if(s.getId().equals(id)){
                printStudent(s);
                return;
            }
        }
        System.out.printf("「未找到学号为 %s 的学生」\n", id);
    }

    // ============================================================
    //  TODO 4：删除学生
    // ============================================================
    private static void deleteById() {
        // 步骤：
        // 1) 读入学号
        // 2) 遍历找出下标；找到就 students.remove(下标)，打印「删除成功」，return
        // 3) 找不到就打印「未找到」
        //
        // 注意：不要在 for 循环里边遍历边 remove，会跳过元素。
        //       先记住下标，循环结束后再删。
        String id = readText("请输入需要删除的学生学号：");
        int index = 0;
        //计数器，如果为0则没有找到相同学号的学生
        int temp = 0;
        for (Student s : students) {
            if(s.getId().equals(id)) {
                temp++;
                break;
            }
            index++;
        }
        if(temp == 0){
            System.out.printf("「未找到学号为 %s 的学生」\n", id);
            return;
        }
        students.remove(index);
        System.out.printf("已经删除学号为%s的学生\n", id);
    }

    // ============================================================
    //  TODO 5：修改学生
    // ============================================================
    private static void updateStudent() {
        // 步骤：
        // 1) 读入学号，找到对应学生（连同下标一起记下来）
        // 2) 找不到 → 打印「未找到」
        // 3) 找到后重新读入三科成绩，然后二选一实现：
        //
        //    方案 A：给 demo.day02.Student 补 3 个 setter
        //            public void setChinese(int chinese) { this.chinese = chinese; }
        //            然后调用 s.setChinese(新值)
        //
        //    方案 B：不动 Student，直接替换整个对象
        //            students.set(下标, new Student(原姓名, 原学号, 新语文, 新数学, 新英语))
        //
        //    两种都行，A 更符合「封装」的习惯，B 更省事。
        String id = readText("请输入需要修改的学生学号：");
        int index = 0;
        //计数器，如果为0则没有找到相同学号的学生
        int temp = 0;
        for (Student s : students) {
            if(s.getId().equals(id)) {
                temp++;
                break;
            }
            index++;
        }
        if(temp == 0){
            System.out.printf("「未找到学号为 %s 的学生」\n", id);
            return;
        }
        Student s = students.get(index);
        System.out.println("请输入您要修改的科目，如果修改完毕，输入-1就可以退出");
        final String math = "数学";
        final String english = "英语";
        final String chinese = "语文";

        while(true) {
            String str = readText("");
            switch (str) {
                case "数学" -> {
                    s.setMath(readInt("输入数学成绩："));
                    System.out.println("修改完毕,可以继续修改其他学科！");
                }
                case "语文" -> {
                    s.setChinese(readInt("请输入语文成绩："));
                    System.out.println("修改完毕,可以继续修改其他学科！");
                }
                case "英语" -> {
                    s.setEnglish(readInt("请输入英文成绩："));
                    System.out.println("修改完毕,可以继续修改其他学科！");
                }
                case "-1" -> {
                    return;
                }
                default -> System.out.println("没有这个选项，请重新选择");
            }
        }
    }

    // ============================================================
    //  下面两个工具方法已经写好，直接用，不用改
    //  添加新工具（只为单独打印输出学生信息）
    // ============================================================

    /** 读一个整数；输入非数字会提示重试，不会死循环 */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
            System.out.println("请输入数字。");
            scanner.next();
        }
    }

    /** 读一个非空字符串 */
    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNext()) {
                String value = scanner.next().trim();
                if (!value.isEmpty()) return value;
            } else {
                return "";
            }
        }
    }

    /** 打印某个学生 */
    private static void printStudent(Student s){
        System.out.printf("%-8s %-8s %4d %4d %4d %5d %6.1f%n", s.getId(), s.getName(), s.getChinese(), s.getMath(), s.getEnglish(), s.getTotal(), s.getAverage());
    }
}
