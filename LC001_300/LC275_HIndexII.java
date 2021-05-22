package LC001_300;
import java.util.*;
public class LC275_HIndexII {
    /**
     * Given an array of integers citations where citations[i] is the number of citations a researcher received for
     * their ith paper and citations is sorted in an ascending order, return compute the researcher's h-index.
     *
     * According to the definition of h-index on Wikipedia: A scientist has an index h if h of their n papers have at
     * least h citations each, and the other n − h papers have no more than h citations each.
     *
     * If there are several possible values for h, the maximum one is taken as the h-index.
     *
     * You must write an algorithm that runs in logarithmic time.
     *
     * Input: citations = [0,1,3,5,6]
     * Output: 3
     *
     * Constraints:
     *
     * n == citations.length
     * 1 <= n <= 10^5
     * 0 <= citations[i] <= 1000
     * citations is sorted in ascending order.
     * @param citations
     * @return
     */
    // time = O(logn), space = O(1)
    public int hIndex(int[] citations) {
        // corner case
        if (citations == null || citations.length == 0) return 0;

        int n = citations.length;
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (citations[mid] == n - mid) return n - mid;
            else if (citations[mid] < n - mid) left = mid + 1;
            else right = mid - 1;
        }
        return n - left;
    }
}
