package demo.day03;

import demo.day02.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生数据的文件持久化（CSV）。
 *
 * 文件格式：一行一个学生，字段顺序固定为
 *
 *     学号,姓名,语文,数学,英语
 *
 * 这个顺序只在 {@link #toLine} 和 {@link #fromLine} 两处出现，
 * 两个方法必须严格对应——改一个就要改另一个。
 */
public final class StudentStorage {

    /** 数据文件路径（相对于运行时的工作目录） */
    public static final String FILE_PATH = "students.csv";

    /** 每行的字段个数，用于过滤格式错误的行 */
    private static final int COLUMN_COUNT = 5;

    // 工具类，不允许实例化
    private StudentStorage() {
    }

    // ==================== 对外接口 ====================

    /** 把学生列表整体写入文件（覆盖写） */
    public static void save(List<Student> students) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE_PATH))) {
            for (Student student : students) {
                writer.write(toLine(student));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("保存失败：" + e.getMessage());
        }
    }

    /** 从文件读回学生列表；文件不存在时返回空列表 */
    public static List<Student> load() {
        List<Student> students = new ArrayList<>();
        Path path = Path.of(FILE_PATH);

        if (!Files.exists(path)) {
            return students;                 // 第一次运行没有文件，不是错误
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = fromLine(line);
                if (student == null) {
                    System.out.println("跳过格式错误的行：" + line);
                    continue;
                }
                students.add(student);
            }
        } catch (IOException e) {
            System.out.println("读取失败：" + e.getMessage());
        }
        return students;
    }

    // ==================== 序列化（顺序只在这里定义） ====================

    /** 学生对象 → CSV 一行：学号,姓名,语文,数学,英语 */
    private static String toLine(Student student) {
        return String.join(",",
                student.getId(),
                student.getName(),
                String.valueOf(student.getChinese()),
                String.valueOf(student.getMath()),
                String.valueOf(student.getEnglish()));
    }

    /**
     * CSV 一行 → 学生对象。与 {@link #toLine} 严格对应。
     *
     * 注意 Student 的构造参数是 (name, id, ...)，而文件里是 学号在前，
     * 所以传入时 parts[0] 是 id、parts[1] 是 name，顺序不能照抄。
     *
     * @return 解析失败（列数不对或数字非法）时返回 null，由调用方跳过
     */
    private static Student fromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != COLUMN_COUNT) {
            return null;
        }
        try {
            return new Student(
                    parts[1],                                    // name
                    parts[0],                                    // id
                    Integer.parseInt(parts[2]),                  // chinese
                    Integer.parseInt(parts[3]),                  // math
                    Integer.parseInt(parts[4]));                 // english
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
