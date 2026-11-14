package demo.project.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

/**
 * 项目 A：简易 HTTP 文件服务器
 *
 * 需求文档：E:\Deepseek-prograss\first\学习任务\项目A-HTTP文件服务器.md
 *
 * 用 JDK 自带的 HttpServer（零依赖），把指定目录变成可以通过浏览器访问的网站。
 * 用法：
 *   java -cp target/classes demo.project.http.FileServer [目录]
 *   不传目录就用当前目录。
 *
 * 这个项目会让你真正理解「请求 → 响应」是怎么回事，
 * 等 W9 学 Spring Boot 时你是带着理解去学，不是背注解。
 */
public class FileServer {

    private static final int PORT = 8080;

    /** 服务器的根目录，所有请求都限制在这个目录以内 */
    private static Path rootDir;

    public static void main(String[] args) throws IOException {
        rootDir = Path.of(args.length > 0 ? args[0] : ".").toAbsolutePath().normalize();

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", FileServer::handle);
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();

        System.out.println("服务已启动: http://localhost:" + PORT);
        System.out.println("根目录    : " + rootDir);
        System.out.println("按 Ctrl+C 停止");
    }

    /**
     * 处理一个请求。
     *
     * TODO：完整实现下面 7 步
     */
    private static void handle(HttpExchange exchange) throws IOException {
        try {
            // 1) 取请求路径
            //    String rawPath = exchange.getRequestURI().getPath();
            //    注意浏览器会传 "/" 表示根目录
            //
            // 2) 拼成本地文件路径
            //    Path target = rootDir.resolve(rawPath.substring(1)).normalize();
            //    （substring(1) 是为了去掉开头那个 /）
            //
            // 3) 【安全】检查是否越权
            //    如果 target 不在 rootDir 之内，返回 403
            //    判断方法：if (!target.startsWith(rootDir)) { 返回 403; return; }
            //
            //    为什么必须检查？因为请求 "/../etc/passwd" 会跑到根目录外面去，
            //    这是真实存在过的漏洞类型（路径穿越）。
            //
            // 4) 文件不存在 → 返回 404，提示「文件不存在」
            //
            // 5) 是目录 → 列出该目录下的所有文件名，生成一段简单的 HTML 返回
            //    提示：Files.list(target) 得到路径流，取 getFileName()
            //
            // 6) 是普通文件 → 读出字节，发给客户端
            //    提示：
            //      byte[] data = Files.readAllBytes(target);
            //      exchange.getResponseHeaders().set("Content-Type", contentTypeOf(target));
            //      exchange.sendResponseHeaders(200, data.length);
            //      try (OutputStream os = exchange.getResponseBody()) { os.write(data); }
            //
            // 7) 最外层 finally 里交换体已经由上面关闭；
            //    如果中间任何一步 return 了，记得 exchange.close()

        } finally {
            exchange.close();
        }
    }

    /** 按扩展名猜 Content-Type，让浏览器能正确显示图片和网页 */
    private static String contentTypeOf(Path file) {
        String name = file.getFileName().toString().toLowerCase();
        if (name.endsWith(".html") || name.endsWith(".htm")) return "text/html; charset=utf-8";
        if (name.endsWith(".css")) return "text/css; charset=utf-8";
        if (name.endsWith(".js")) return "application/javascript; charset=utf-8";
        if (name.endsWith(".png")) return "image/png";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
        if (name.endsWith(".gif")) return "image/gif";
        if (name.endsWith(".json")) return "application/json; charset=utf-8";
        if (name.endsWith(".txt") || name.endsWith(".md")) return "text/plain; charset=utf-8";
        return "application/octet-stream";
    }

    /** 返回一段简单 HTML（已写好，直接用） */
    private static void sendHtml(HttpExchange exchange, int status, String html) throws IOException {
        byte[] bytes = html.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
