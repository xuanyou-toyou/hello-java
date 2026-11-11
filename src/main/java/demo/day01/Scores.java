package demo.day01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

/**
 * 练习 1：成绩统计器
 *
 * 需求文档：E:\Deepseek-prograss\first\学习任务\W1-练习1-成绩统计器.md
 *
 * 规则：下面每个 TODO 都要你自己写，不要复制别处代码。
 * 写完后运行 main，对照需求文档里的示例核对输出。
 */
public class Scores {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> scores = readScores(scanner);
        scanner.close();

        if (scores.isEmpty()) {
            System.out.println("没有输入任何成绩");
            return;
        }

        // TODO 1：打印总人数（提示：scores.size()）
        System.out.printf("总人数 ： %d\n", scores.size());
        // TODO 2：打印平均分，保留 1 位小数（提示：String.format("%.1f", avg)）
        int i = 0;
        for(Integer score : scores){
            i += score;
        }
        double avg = (double) i / scores.size();
        System.out.printf("平均分 ：%.1f\n", avg);
        // TODO 3：打印最高分和最低分
        //         ——不要在 main 里直接写 for 循环，把它们抽成两个独立方法
        max(scores);
        min(scores);
        // TODO 4：打印及格率（>=60 的人数占比，保留 1 位小数，带 % 号）

        int j = 0;//计数器
        for(Integer score : scores){
            if(score >= 60)
                j++;
        }
        System.out.printf("及格率 ：%.1f%%\n", (double)(100.0 * j/scores.size()));
        // TODO 5：打印从高到低排序后的成绩（提示：Collections.sort + reverse）
        Collections.sort(scores);
        Collections.reverse(scores);
        System.out.print(scores);
    }

    /**
     * 最大数
     */
    private static int max(List<Integer> scores){
        int max = 0;
        for(Integer i : scores){
            if(i > max){
                max = i;
            }
        }

        System.out.printf("最大数 ：%d\n", max);
        return max;
    }

    /**
     * 最小数
     */
    private static int min(List<Integer> scores){
        int min = 100;
        for(Integer i : scores){
            if(i < min){
                min = i;
            }
        }

        System.out.printf("最小数 ：%d\n", min);
        return min;
    }

    /**
     * 读入成绩，遇到 -1 结束。
     * 非法输入（不在 0-100 之间）要提示「成绩必须在 0-100 之间」并跳过这一项。
     */
    private static List<Integer> readScores(Scanner scanner) {
        List<Integer> scores = new ArrayList<>();
        System.out.println("请输入成绩（0-100 的整数），输入 -1 结束：");

        // TODO 0：用 while 循环反复读入整数
        //   - 读到 -1 就 break
        //   - 读到 0~100 就加进 scores
        //   - 读到其他数字，提示并跳过
        //   提示：scanner.hasNextInt() 可以先判断是不是整数，避免输入字母时崩掉
        int n = 0;
        while (n != -1){

            if(scanner.hasNextInt()){
                n = scanner.nextInt();
            } else {
                System.out.println("你输入的不是数字,请重新输入");
                scanner.next();
                continue;
            }

            if(n == -1){
                break;
            }
            if(n <= 100 && n >= 0) {
                scores.add(n);
            } else{
                System.out.println("你输入的数字应处于0-100之间，请重新输入");
            }
        }

        return scores;
    }
}
