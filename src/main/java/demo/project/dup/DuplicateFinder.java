package demo.project.dup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 项目 B：重复文件查找器
 *
 * 需求文档：E:\Deepseek-prograss\first\学习任务\项目B-重复文件查找器.md
 *
 * 找出一个目录下内容完全相同的文件，报告重复组和能省下多少空间。
 * 用法：
 *   java -cp target/classes demo.project.dup.DuplicateFinder [目录]
 *
 * 关键设计：不要一上来就对所有文件算哈希——那太慢。
 * 先用文件大小过滤，只有大小相同的才值得算哈希。
 */
public class DuplicateFinder {

    public static void main(String[] args) throws IOException {
        Path root = Path.of(args.length > 0 ? args[0] : ".").toAbsolutePath().normalize();
        System.out.println("扫描目录: " + root);

        // TODO 1：遍历所有普通文件，按【文件大小】分组
        //
        //   提示：
        //   Map<Long, List<Path>> bySize = new HashMap<>();
        //   用 Files.walk(root) 得到 Stream<Path>，过滤 Files::isRegularFile
        //   对每个文件 Files.size(path) 得到大小，作为 Map 的 key
        //
        //   注意：Files.walk 返回的流必须用 try-with-resources 包起来，否则文件句柄泄漏


        // TODO 2：只保留「大小相同的文件多于 1 个」的分组
        //
        //   提示：bySize.entrySet().removeIf(e -> e.getValue().size() < 2);
        //   或者新建一个 Map 只装 size() >= 2 的


        // TODO 3：对剩下的候选文件，计算 MD5，按哈希值分组
        //
        //   Map<String, List<Path>> byHash = new HashMap<>();
        //   对每个候选文件调用 md5(path)（下面已经写好了）


        // TODO 4：输出结果
        //
        //   遍历 byHash，只打印 value.size() > 1 的组：
        //     - 打印组内所有文件路径
        //     - 算出「重复文件个数 × 单文件大小」= 可释放空间
        //   最后打印汇总：一共多少组重复、可释放多少字节（转成 KB/MB 更好看）
        //
        //   如果一组都没有，打印「没有找到重复文件」


        System.out.println("扫描完成");
    }

    /**
     * 计算文件的 MD5（已写好，直接用）
     *
     * 为什么用 MD5：内容相同 → 哈希值必定相同；
     * 哈希值相同 → 内容几乎必定相同（碰撞概率极低）。
     * 所以先比大小、再比哈希，能把「逐字节比较」的代价降到最低。
     */
    private static String md5(Path file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = Files.readAllBytes(file);
            byte[] hash = digest.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IllegalStateException("JDK 不支持 MD5", e);
        }
    }

    /** 把字节数转成好读的字符串（已写好，直接用） */
    private static String human(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        if (bytes < 1024L * 1024 * 1024) return String.format("%.1f MB", bytes / 1024.0 / 1024.0);
        return String.format("%.2f GB", bytes / 1024.0 / 1024.0 / 1024.0);
    }
}
