package demo.day07;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC 数据库连接工具类。
 *
 * 连接参数从 classpath 下的 db.properties 读取，不写死在代码里：
 *   - db.properties        本地真实配置，已加入 .gitignore，不会提交
 *   - db.properties.example 模板文件，随代码提交，别人照着填
 *
 * 这样密码不会出现在 Git 历史里。
 */
public final class DbUtil {

    private static final String CONFIG_FILE = "db.properties";

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties props = new Properties();
        try (InputStream in = DbUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new IllegalStateException(
                        "找不到 " + CONFIG_FILE + "，请把 db.properties.example 复制成 db.properties 并填写数据库密码");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("读取 " + CONFIG_FILE + " 失败", e);
        }

        URL = props.getProperty("db.url");
        USER = props.getProperty("db.user");
        PASSWORD = props.getProperty("db.password");

        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException("db.properties 缺少 db.url / db.user / db.password");
        }
    }

    private DbUtil() {
    }

    /** 获取一个数据库连接 */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 自测入口：能打印出版本号就说明配置正确。
     * 运行：java -cp "target/classes;target/lib/*" demo.day07.DbUtil
     */
    public static void main(String[] args) {
        System.out.println("正在连接数据库...");
        try (Connection conn = getConnection()) {
            System.out.println("连接成功！");
            System.out.println("数据库产品: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("数据库版本: " + conn.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            System.out.println("连接失败: " + e.getMessage());
        }
    }
}
