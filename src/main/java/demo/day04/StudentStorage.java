package demo.day04;

import demo.day02.Student;

import java.util.List;

/**
 * W4：学生数据持久化
 *
 * 需求文档：E:\Deepseek-prograss\first\学习任务\W4-数据持久化.md
 *
 * 目标：让程序关掉再打开，数据还在。
 * 文件格式用 CSV（逗号分隔），每行一个学生：
 *
 *   001,张三,85,92,78
 *   002,李四,99,62,75
 *
 * 注意：这两个方法都要抛出/捕获 IOException，
 * 而且无论如何都要保证「用完关掉文件」——用 try-with-resources。
 */
public class StudentStorage {

    /** 数据文件放在项目根目录（相对路径，以运行时的工作目录为准） */
    public static final String FILE_PATH = "students.csv";

    /**
     * TODO 1：保存
     *
     * 步骤：
     * 1) 用 try-with-resources 打开一个写文件的 BufferedWriter：
     *
     *    try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE_PATH))) {
     *        ...
     *    } catch (IOException e) {
     *        System.out.println("保存失败：" + e.getMessage());
     *    }
     *
     *    （记得 import java.nio.file.Files / java.nio.file.Path / java.io.BufferedWriter / java.io.IOException）
     *
     * 2) 遍历 students，每个学生拼成一行：学号,姓名,语文,数学,英语
     *    用 writer.write(...) 写入，然后 writer.newLine() 换行
     *
     * 3) 用 String.format 或者字符串拼接都行
     *    提示：s.getId() + "," + s.getName() + "," + s.getChinese() + ","
     *          + s.getMath() + "," + s.getEnglish()
     */
    public static void save(List<Student> students) {
        // TODO 1 写在这里
    }

    /**
     * TODO 2：加载
     *
     * 步骤：
     * 1) 先判断文件在不在：Files.exists(Path.of(FILE_PATH))
     *    不存在 → 直接返回空的 ArrayList（第一次运行没有文件，不是错误）
     *
     * 2) 用 try-with-resources 打开 BufferedReader 逐行读：
     *
     *    try (BufferedReader reader = Files.newBufferedReader(Path.of(FILE_PATH))) {
     *        String line;
     *        while ((line = reader.readLine()) != null) {
     *            ...
     *        }
     *    } catch (IOException e) {
     *        System.out.println("读取失败：" + e.getMessage());
     *    }
     *
     * 3) 每行用 line.split(",") 切成 5 段，注意得到的是 String[]
     *    第 1、2 段直接是字符串，第 3、4、5 段要用 Integer.parseInt(...) 转成 int
     *
     * 4) new Student(...) 加进结果列表
     *
     * 5) 遇到格式不对的行（比如段数不是 5）要跳过并打印提示，不要整个程序崩掉
     *    提示：if (parts.length != 5) { System.out.println("跳过坏行：" + line); continue; }
     *
     * 6) 最后 return 结果列表
     */
    public static List<Student> load() {
        // TODO 2 写在这里
        return java.util.List.of();   // ← 这行是占位，写完删掉
    }
}
