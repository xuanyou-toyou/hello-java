package demo.day07;

import demo.day02.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生数据访问对象（DAO = Data Access Object）。
 *
 * 职责：把「Java 对象」和「数据库表」互相转换。
 * 上层（StudentManager）只管调方法，不关心 SQL 怎么写。
 *
 * 表结构（student_db.student）：
 *   id VARCHAR(20) PRIMARY KEY
 *   name VARCHAR(20)
 *   chinese / math / english INT
 *
 * ⚠️ 所有 SQL 都用 PreparedStatement + ? 占位符，绝不用字符串拼接。
 *    原因见 TODO 下面的说明。
 */
public class StudentDao {

    // ============================================================
    // TODO 1：查询全部
    // ============================================================
    /**
     * SQL：SELECT id, name, chinese, math, english FROM student ORDER BY id
     *
     * 模板（照着填）：
     *
     *   List<Student> list = new ArrayList<>();
     *   String sql = "SELECT ... FROM student ORDER BY id";
     *   try (Connection conn = DbUtil.getConnection();
     *        PreparedStatement ps = conn.prepareStatement(sql);
     *        ResultSet rs = ps.executeQuery()) {
     *
     *       while (rs.next()) {
     *           // 从结果集取每列：rs.getString("id")、rs.getInt("chinese")
     *           // 组装成 Student，加进 list
     *       }
     *   } catch (SQLException e) {
     *       System.out.println("查询失败: " + e.getMessage());
     *   }
     *   return list;
     */
    public List<Student> findAll() {
        // TODO 1 写在这里
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student ORDER BY id";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                Student s = new Student(rs.getString("name"), rs.getString("id"),
                        rs.getInt("chinese"), rs.getInt("math"), rs.getInt("english"));
                list.add(s);
            }
        } catch (SQLException e){
            System.out.println("查询失败：" + e.getMessage());
        }
        return list;
    }

    // ============================================================
    // TODO 2：按学号查询单个
    // ============================================================
    /**
     * SQL：SELECT ... FROM student WHERE id = ?
     *
     * 关键点：
     *   1) SQL 里写 ? 占位，不要拼字符串
     *   2) 用 ps.setString(1, id) 把参数填进去（1 表示第 1 个 ?）
     *   3) executeQuery 后用 if (rs.next()) 判断有没有查到
     *   4) 查不到返回 null
     */
    public Student findById(String id) {
        // TODO 2 写在这里

        String sql = "SELECT * FROM student WHERE id = ?";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql) ){

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){

                    return new Student(rs.getString("name"), rs.getString("id"),
                            rs.getInt("chinese"), rs.getInt("math"), rs.getInt("english"));

                }
            }

        } catch (SQLException e){
            System.out.println("查询失败：" + e.getMessage());
        }
        return null;
    }

    // ============================================================
    // TODO 3：新增
    // ============================================================
    /**
     * SQL：INSERT INTO student (id, name, chinese, math, english) VALUES (?, ?, ?, ?, ?)
     *
     * 关键点：
     *   1) 写操作用 ps.executeUpdate()，返回「影响的行数」
     *   2) 5 个参数依次 setString / setInt，下标从 1 开始
     *   3) 返回 true 表示插入成功
     *
     * 注意：学号重复会抛 SQLIntegrityConstraintViolationException（主键冲突），
     *      要单独 catch 并提示「学号已存在」，不要让程序崩。
     */
    public boolean insert(Student student) {
        // TODO 3 写在这里
        String sql = "INSERT INTO student (id, name, chinese, math, english) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getChinese());
            ps.setInt(4, student.getMath());
            ps.setInt(5, student.getEnglish());

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e){
            // 主键冲突（学号重复）是正常的业务情况，不打印技术错误
            // 具体提示由调用方根据返回值决定
        }
        catch (SQLException e){
            System.out.println("插入失败：" + e.getMessage());
        }
        return false;
    }

    // ============================================================
    // TODO 4：修改（按学号更新三科成绩）
    // ============================================================
    /**
     * SQL：UPDATE student SET chinese = ?, math = ?, english = ? WHERE id = ?
     *
     * 返回 executeUpdate() > 0（影响行数大于 0 说明改到了）
     */
    public boolean update(Student student) {
        // TODO 4 写在这里
        String sql = "UPDATE student SET chinese = ?, math = ?, english = ? WHERE id = ?";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, student.getChinese());
            ps.setInt(2, student.getMath());
            ps.setInt(3, student.getEnglish());
            ps.setString(4, student.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e){
            System.out.println("更新失败：" + e.getMessage());
        }
        return false;
    }

    // ============================================================
    // TODO 5：按学号删除
    // ============================================================
    /**
     * SQL：DELETE FROM student WHERE id = ?
     *
     * 返回 executeUpdate() > 0
     */
    public boolean deleteById(String id) {
        // TODO 5 写在这里
        String sql = "DELETE FROM student WHERE id = ?";
        try(Connection conn = DbUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e){
            System.out.println("删除失败：" + e.getMessage());
        }
        return false;
    }
}
