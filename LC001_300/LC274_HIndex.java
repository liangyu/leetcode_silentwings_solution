package LC001_300;
import java.util.*;
public class LC274_HIndex {
    /**
     * Given an array of integers citations where citations[i] is the number of citations a researcher received for t
     * heir ith paper, return compute the researcher's h-index.
     *
     * According to the definition of h-index on Wikipedia: A scientist has an index h if h of their n papers have
     * at least h citations each, and the other n − h papers have no more than h citations each.
     *
     * If there are several possible values for h, the maximum one is taken as the h-index.
     *
     * Input: citations = [3,0,6,1,5]
     * Output: 3
     *
     * Constraints:
     *
     * n == citations.length
     * 1 <= n <= 5000
     * 0 <= citations[i] <= 1000
     * @param citations
     * @return
     */
    // S1: Sort
    // time = O(nlogn), space = O(1)
    public int hIndex(int[] citations) {
        // corner case
        if (citations == null || citations.length == 0) return 0;

        Arrays.sort(citations);

        int n = citations.length, count = 0;
        for (int i = n - 1; i >= 0; i--) { // 从后往前数
            if (citations[i] < n - i) break; // 注意个数是n - i
            count++;
        }
        return count;
    }

    // S2: 最优解
    // time = O(n), space = O(n)
    public int hIndex2(int[] citations) {
        // corner case
        if (citations == null || citations.length == 0) return 0;

        int n = citations.length;
        int[] papers = new int[n + 1];
        for (int c : citations) {
            papers[Math.min(c, n)]++;
        }
        int count = 0;
        for (int i = n; i >= 0; i--) {
            count += papers[i];
            if (count >= i) return i;
        }
        return 0;
    }
}
/**
 * 可以发现 \text{H}H 指数不可能大于总的论文发表数，所以对于引用次数超过论文发表数的情况，我们可以将其按照总的论文发表数来计算即可。
 * 这样我们可以限制参与排序的数的大小为 [0,n]（其中 n 为总的论文发表数），使得计数排序的时间复杂度降低到 O(n)。
 *
 */
