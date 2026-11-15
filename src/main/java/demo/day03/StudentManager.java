package demo.day03;

import demo.day02.Student;
import demo.day07.StudentDao;

import java.util.List;
import java.util.Scanner;

/**
 * 学生管理系统（控制台版）。
 *
 * 功能：增 / 删 / 改 / 查 / 列表。
 * 数据通过 {@link StudentDao} 存入 MySQL 数据库（库名 student_db），
 * 每次操作直接写库，无需手动保存；启动时直接查询数据库。
 */
public class StudentManager {

    private static final StudentDao dao = new StudentDao();
    private static final Scanner scanner = new Scanner(System.in);

    /** 表格表头与数据行使用同一套列宽，保证对齐 */
    private static final String TABLE_HEADER =
            String.format("%-8s %-8s %4s %4s %4s %5s %6s",
                    "学号", "姓名", "语文", "数学", "英语", "总分", "平均分");
    private static final String TABLE_ROW_FORMAT = "%-8s %-8s %4d %4d %4d %5d %6.1f%n";

    public static void main(String[] args) {
        System.out.println("=== 学生管理系统 ===");

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

    // ==================== 业务操作 ====================

    /** 添加学生：学号重复由数据库主键约束拦截，DAO 返回 false 即表示失败 */
    private static void addStudent() {
        String name = readText("姓名：");
        String id = readText("学号：");

        int chinese = readInt("语文：");
        int math = readInt("数学：");
        int english = readInt("英语：");

        boolean ok = dao.insert(new Student(name, id, chinese, math, english));
        if (ok) {
            System.out.println("添加成功。");
        } else {
            System.out.println("学号 " + id + " 已存在，添加失败。");
        }
    }

    /** 查看全部学生 */
    private static void listAll() {
        List<Student> list = dao.findAll();

        if (list.isEmpty()) {
            System.out.println("暂无学生。");
            return;
        }
        printTableHeader();
        for (Student student : list) {
            printStudent(student);
        }
    }

    /** 按学号查询单个学生 */
    private static void findById() {
        String id = readText("请输入学号：");
        Student student = dao.findById(id);

        if (student == null) {
            System.out.println("未找到学号为 " + id + " 的学生。");
            return;
        }
        printTableHeader();
        printStudent(student);
    }

    /** 按学号删除学生 */
    private static void deleteById() {
        String id = readText("请输入学号：");

        if (dao.deleteById(id)) {
            System.out.println("已删除学号为 " + id + " 的学生。");
        } else {
            System.out.println("未找到学号为 " + id + " 的学生。");
        }
    }

    /** 按学号修改某个科目的成绩，输入 -1 结束 */
    private static void updateStudent() {
        String id = readText("请输入学号：");
        Student student = dao.findById(id);

        if (student == null) {
            System.out.println("未找到学号为 " + id + " 的学生。");
            return;
        }

        System.out.println("请输入要修改的科目（语文 / 数学 / 英语），输入 -1 结束：");

        while (true) {
            String subject = readText("科目：");
            switch (subject) {
                case "语文" -> {
                    student.setChinese(readInt("新成绩："));
                    System.out.println("已更新语文成绩。");
                }
                case "数学" -> {
                    student.setMath(readInt("新成绩："));
                    System.out.println("已更新数学成绩。");
                }
                case "英语" -> {
                    student.setEnglish(readInt("新成绩："));
                    System.out.println("已更新英语成绩。");
                }
                case "-1" -> {
                    dao.update(student);      // ← 关键：退出前落盘
                    return;
                }
                default -> System.out.println("没有这个科目，请重新输入。");
            }
        }
    }

    // ==================== 公共工具 ====================


    private static void printTableHeader() {
        System.out.println(TABLE_HEADER);
    }

    private static void printStudent(Student student) {
        System.out.printf(TABLE_ROW_FORMAT,
                student.getId(),
                student.getName(),
                student.getChinese(),
                student.getMath(),
                student.getEnglish(),
                student.getTotal(),
                student.getAverage());
    }

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
            if (!scanner.hasNext()) {
                return "";
            }
            String value = scanner.next().trim();
            if (!value.isEmpty()) {
                return value;
            }
        }
    }
}
