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

        int res = 0, n = citations.length;
        while (res < n && citations[n - 1 - res] > res) res++;
        return res;
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
        int k = n;
        for (int i = papers[n]; i < k; i += papers[k]) k--;
        return k;
    }
}
