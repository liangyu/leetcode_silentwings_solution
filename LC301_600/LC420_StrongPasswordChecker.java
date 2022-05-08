package LC301_600;

public class LC420_StrongPasswordChecker {
    /**
     * A password is considered strong if the below conditions are all met:
     *
     * It has at least 6 characters and at most 20 characters.
     * It contains at least one lowercase letter, at least one uppercase letter, and at least one digit.
     * It does not contain three repeating characters in a row (i.e., "...aaa..." is weak, but "...aa...a..." is strong,
     * assuming other conditions are met).
     * Given a string password, return the minimum number of steps required to make password strong. if password is
     * already strong, return 0.
     *
     * In one step, you can:
     *
     * Insert one character to password,
     * Delete one character from password, or
     * Replace one character of password with another character.
     *
     * Input: password = "a"
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= password.length <= 50
     * password consists of letters, digits, dot '.' or exclamation mark '!'.
     * @param password
     * @return
     */
    // time = O(n), space = O(1)
    public int strongPasswordChecker(String password) {
        // a: 数字数量，b：小写字母数量，c：大写字母数量, n：长度，k：字符种类数（强密码要求包含小写、大写、数字）
        int a = 0, b = 0, c = 0, n = password.length(), k = 0;
        for (char ch : password.toCharArray()) {
            if (ch >= '0' && ch <= '9') a = 1;
            else if (ch >= 'a' && ch <= 'z') b = 1;
            else if (ch >= 'A' && ch <= 'Z') c = 1;
        }
        k = a + b + c; // 自始至终，种类要求是一定要满足的！
        if (n < 6) return Math.max(6 - n, 3 - k); // case 1: only consider add
        else {
            // p：要修改的次数，del：要删除的次数，res：答案要求的操作次数
            int p = 0, del = n - 20, res = del;
            // 求一下所有的连续段
            int[] d = new int[3]; // 模3余0/1/2的连续段的数量
            for (int i = 0; i < n; i++) {
                int j = i;
                while (j < n && password.charAt(j) == password.charAt(i)) j++;
                int len = j - i; // 当前连续段的长度
                p += len / 3; // 要求修改的次数，向下取整
                if (len >= 3) d[len % 3]++;
                i = j - 1; // 双指针更新i
            }
            // 如果不超过20，就是只考虑修改和种类
            if (n <= 20) return Math.max(p, 3 - k); // case 2: only consider replace (可以兼顾改成缺失的种类)
            // 否则，就是要考虑删除，并把删除转化成等价的替换操作，再同上操作。 从d[0]开始考虑 case 3: consider del + modify
            // case 3.1: 3x 类型 x x x -> rep1 = 3 / 3, 这时del一个,rep2=(3-1)/3 就会造成rep2 = rep1 - 1，因为这里是向下取整！
            if (d[0] >= 0 && del > 0) {
                int t = Math.min(d[0] * 1, del); // 每个连续3个相同字符段删除1个，等价于1次替换1个字符操作！
                del -= t;
                p -= t;
            }
            // case 3.2: 3x + 1 类型 x x x x -> rep1 = 4 / 3, 这时del两个,rep2=(4-2)/3 才会造成rep2 = rep1 - 1！
            if (d[1] >= 0 && del > 0) {
                int t = Math.min(d[1] * 2, del); // 每个连续4个字符段要删除2个，等价于1次替换字符操作 -> x x y x
                del -= t;
                p -= t / 2;
            }
            // case 3.3: 剩下的p都是余2的情况 3x + 2 -> x x x x x => rep1 = 5/3, rep2 = (5-3)/3 才会造成rep2=rep1-1!
            if (p >= 0 && del > 0) { // 注意这里不是d[2]，因为还有上面d[0],d[1]经过操作后最终都变成了d[2]这种类型，所以直接看p!
                int t = Math.min(p * 3, del); // x x x x x -> x x y x x => 3次del操作才等价于1次替换操作！
                p -= t / 3;
            }
            // 一定要删除的，加上考虑修改和种类的情况，这里剩余的p是原来计算得到的p减去那些在del里被包含抵消的那些p之后剩下仍然需要的replace操作！
            return res + Math.max(p, 3 - k);
        }
    }
}
/**
 * 分类统计
 * n: 字符串长度
 * k: 字符串种类数
 * 1. n < 6  补 6 - n 个字符，
 * 要把种类数补到3，3 - k
 * max {6 - n, 3 - k}
 *    n <= 4  加2个字符，至少有一类字符，最多缺2类，把种类也补上 => 6 - n，并且加在2个相同之后隔开，使之不会出现3个字符相同的情况
 * x x x x => x x y x x z
 *    n == 5
 * (1) k = 1 -> 补充2类，选择把中间那个改成一个新的类，然后添加一个新的字母，数量够了，种类也够了
 * x x x x x =>  x x y x x z
 * (2) k > 1 => 至少有2类，最多缺1类
 * x x x y y  => x x (z) x y y 在中间断开
 * 2. n >= 6 && n <= 20
 * 3 - k
 * 把所有长度 >= 3的字符全部找出来，假设当前长度这一段是s
 * => x x x x x -> 插入的话，连续2个字母之后插入一个，(s - 1) / 2 次
 * 删除的话至少得删除到只剩2个  => s - 2 次
 * 修改的话 => x x y x x 步数一定是最少的 => s / 3
 * 当s >= 3的时候，尽量多用修改操作，少用另外2个操作 => max(sum(ki/3), 3-k) => 边修改边引入新的种类，有交集
 * 3. n > 20  => 删除 n - 20
 * 希望能够在删除的时候尽量减少修改的操作，所以就是尽量在连续段里面去做删除。
 * 连续段按照模3余0、余1、余2可以分成三类。
 * 由于做修改操作时，操作的数量是r/3下取整，所以可以注意到，如果对模3余0的一段删除一个数，那么接下来这一段的修改次数就可以减少1，改善了情况。
 * 统计所有长度 >= 3的连续段，尽量满足第一类
 * 3x           -1  -> 3x + 2
 * 3x + 1       -2  -> 3x + 2
 * 3x + 2
 * 对于形如3x的连续段，只要删除1个字符就能改善；
 * 对于形如3x+1的连续段，要删除2个字符才能改善；
 * 对于形如3x+2的连续段，要删除3个字符才能改善。
 * 而且改善后的一定是形如3x+2的连续段。
 * 所以就可以把要删除的字符的数量，和这三类连续段的数量来比较一下。看一下一共能改善（减少）多少个连续3，也就是能减少多少次修改操作。
 */