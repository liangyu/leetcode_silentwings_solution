package LC1801_2100;
import java.util.*;
public class LC2081_SumofkMirrorNumbers {
    /**
     * A k-mirror number is a positive integer without leading zeros that reads the same both forward and backward in
     * base-10 as well as in base-k.
     *
     * For example, 9 is a 2-mirror number. The representation of 9 in base-10 and base-2 are 9 and 1001 respectively,
     * which read the same both forward and backward.
     * On the contrary, 4 is not a 2-mirror number. The representation of 4 in base-2 is 100, which does not read the
     * same both forward and backward.
     * Given the base k and the number n, return the sum of the n smallest k-mirror numbers.
     *
     * Input: k = 2, n = 5
     * Output: 25
     *
     * Input: k = 7, n = 17
     * Output: 20379000
     *
     * Constraints:
     *
     * 2 <= k <= 9
     * 1 <= n <= 30
     * @param k
     * @param n
     * @return
     */
    // S1
    // time = O(10^5), space = O(1)
    public long kMirror(int k, int n) {
        int len = 1;
        List<Long> res = new ArrayList<>();
        while (true) {
            for (long i = (long)Math.pow(10, len - 1); i < (long)Math.pow(10, len); i++) {
                long a = getPalindrome(i, 0); // 0 - odd; 1 - even
                if (checkOK(a, k)) res.add(a);
                if (res.size() == n) return getSum(res);
            }
            for (long i = (long)Math.pow(10, len - 1); i < (long)Math.pow(10, len); i++) {
                long a = getPalindrome(i, 1); // 0 - odd; 1 - even
                if (checkOK(a, k)) res.add(a);
                if (res.size() == n) return getSum(res);
            }
            len++;
        }
    }

    private long getPalindrome(long x, int flag) {
        long y = x;
        if (flag == 0) x /= 10;
        while (x > 0) {
            y = y * 10 + x % 10;
            x /= 10;
        }
        return y;
    }

    private boolean checkOK(long num, int k) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(num % k);
            num /= k;
        }
        String s = sb.toString();
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            i++;
            j--;
        }
        return true;
    }

    private long getSum(List<Long> res) {
        long sum = 0;
        for (long x : res) sum += x;
        return sum;
    }

    // S2: 打表法
    // time = O(1), space = O(1)
    public long kMirror2(int k, int n) {
        long ans[][] = {{1L,4L,9L,16L,25L,58L,157L,470L,1055L,1772L,9219L,18228L,33579L,65802L,105795L,159030L,212865L,
                286602L,872187L,2630758L,4565149L,6544940L,9674153L,14745858L,20005383L,25846868L,39347399L,759196316L,
                1669569335L,2609044274L},{1L,3L,7L,15L,136L,287L,499L,741L,1225L,1881L,2638L,31730L,80614L,155261L,
                230718L,306985L,399914L,493653L,1342501L,2863752L,5849644L,9871848L,14090972L,18342496L,22630320L,
                28367695L,36243482L,44192979L,71904751L,155059889L},{1L,3L,6L,11L,66L,439L,832L,1498L,2285L,3224L,
                11221L,64456L,119711L,175366L,233041L,739646L,2540727L,4755849L,8582132L,12448815L,17500320L,22726545L,
                27986070L,33283995L,38898160L,44577925L,98400760L,721411086L,1676067545L,53393239260L},{1L,3L,6L,10L,
                16L,104L,356L,638L,1264L,1940L,3161L,18912L,37793L,10125794L,20526195L,48237967L,78560270L,126193944L,
                192171900L,1000828708L,1832161846L,2664029984L,3500161622L,4336343260L,6849225412L,9446112364L,
                12339666346L,19101218022L,31215959143L,43401017264L},{1L,3L,6L,10L,15L,22L,77L,188L,329L,520L,863L,
                1297L,2074L,2942L,4383L,12050L,19827L,41849L,81742L,156389L,325250L,1134058L,2043967L,3911648L,7009551L,
                11241875L,15507499L,19806423L,24322577L,28888231L},{1L,3L,6L,10L,15L,21L,29L,150L,321L,563L,855L,17416L,
                83072L,2220384L,6822448L,13420404L,20379000L,29849749L,91104965L,321578997L,788407661L,1273902245L,
                1912731081L,2570225837L,3428700695L,29128200347L,69258903451L,115121130305L,176576075721L,241030621167L},
                {1L,3L,6L,10L,15L,21L,28L,37L,158L,450L,783L,1156L,1570L,2155L,5818L,14596L,27727L,41058L,67520L,94182L,
                        124285L,154588L,362290L,991116L,1651182L,3148123L,5083514L,7054305L,11253219L,66619574L},{1L,3L,
                6L,10L,15L,21L,28L,36L,227L,509L,882L,1346L,1901L,2547L,3203L,10089L,35841L,63313L,105637L,156242L,
                782868L,2323319L,4036490L,5757761L,7586042L,9463823L,11349704L,13750746L,16185088L,18627530L}};
        return ans[k - 2][n - 1];
    }
}
/**
 * 直接构造回文数
 * 2      4     3
 * xy => xyyx, xyx
 *  3       6       5
 * xyz => xyzzyx, xyzyx
 * 不停对k取余 => k位数
 * 先构造十进制回文，再check是否是k进制回文
 * 我们是否可以反过来做，遍历所有从小到大的k进制回文数，再查验是否是十进制回文数呢？
 * 因为k小于10，遍历k进制回文数的效率不及遍历十进制回文数的效率高。
 * k越小，在相同范围内，k进制的数字就越长，回文数概率就越高。
 * 比如十进制的两位数，只有11-99这9种回文数。但是对应的二进制表示却是从1010到1100011：
 * 期间有（部分）四位数的回文、（任意）五位数的回文、（任意）六位数的回文、（部分）七位数的回文。
 * 在需要满足既是k进制回文、又是10进制回文的前提下，我们遍历10进制回文数需要尝试的次数更少。
 * [11,22,33,...99] => 9
 * [1011,...,110011] => 二进制的回文数密度比十进制更大，还不如同样的数字范围check 9次效率更高 => 构造十进制回文去check k 进制回文
 */
