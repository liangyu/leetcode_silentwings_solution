package LC1801_2100;
import java.util.*;
public class LC1868_ProductofTwoRunLengthEncodedArrays {
    /**
     * Run-length encoding is a compression algorithm that allows for an integer array nums with many segments of
     * consecutive repeated numbers to be represented by a (generally smaller) 2D array encoded.
     * Each encoded[i] = [vali, freqi] describes the ith segment of repeated numbers in nums where vali is the value
     * that is repeated freqi times.
     *
     * For example, nums = [1,1,1,2,2,2,2,2] is represented by the run-length encoded array encoded = [[1,3],[2,5]].
     * Another way to read this is "three 1s followed by five 2s".
     * The product of two run-length encoded arrays encoded1 and encoded2 can be calculated using the following steps:
     *
     * Expand both encoded1 and encoded2 into the full arrays nums1 and nums2 respectively.
     * Create a new array prodNums of length nums1.length and set prodNums[i] = nums1[i] * nums2[i].
     * Compress prodNums into a run-length encoded array and return it.
     * You are given two run-length encoded arrays encoded1 and encoded2 representing full arrays nums1 and nums2
     * respectively. Both nums1 and nums2 have the same length. Each encoded1[i] = [vali, freqi] describes the ith
     * segment of nums1, and each encoded2[j] = [valj, freqj] describes the jth segment of nums2.
     *
     * Return the product of encoded1 and encoded2.
     *
     * Note: Compression should be done such that the run-length encoded array has the minimum possible length.
     *
     * Input: encoded1 = [[1,3],[2,3]], encoded2 = [[6,3],[3,3]]
     * Output: [[6,6]]
     *
     * Constraints:
     *
     * 1 <= encoded1.length, encoded2.length <= 10^5
     * encoded1[i].length == 2
     * encoded2[j].length == 2
     * 1 <= vali, freqi <= 10^4 for each encoded1[i].
     * 1 <= valj, freqj <= 10^4 for each encoded2[j].
     * The full arrays that encoded1 and encoded2 represent are the same length.
     * @param encoded1
     * @param encoded2
     * @return
     */
    // S1: Two Pointers
    // time = O(Math.max(m, n)), space = O(1));
    public List<List<Integer>> findRLEArray(int[][] encoded1, int[][] encoded2) {
        List<List<Integer>> res = new ArrayList<>();

        int m = encoded1.length, n = encoded2.length;
        int i = 0, j = 0;
        while (i < m && j < n) {
            int match = Math.min(encoded1[i][1], encoded2[j][1]);
            encoded1[i][1] -= match;
            encoded2[j][1] -= match;
            int product = encoded1[i][0] * encoded2[j][0];
            if (!res.isEmpty() && res.get(res.size() - 1).get(0) == product) {
                List<Integer> list = res.get(res.size() - 1);
                list.set(1, list.get(1) + match);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(product);
                list.add(match);
                res.add(list);
            }
            if (encoded1[i][1] == 0) i++;
            if (encoded2[j][1] == 0) j++;
        }
        return res;
    }

    // S2: Two Pointers
    // time = O(Math.max(m, n)), space = O(1));
    public List<List<Integer>> findRLEArray2(int[][] encoded1, int[][] encoded2) {
        List<List<Integer>> res = new ArrayList<>();

        int m = encoded1.length, n = encoded2.length;
        Queue<int[]> queue = new LinkedList<>();
        int i = 0, j = 0, k = 0;
        int val1 = 0, val2 = 0, freq1 = 0, freq2 = 0;
        while (i < m && j < n) {
            if (!queue.isEmpty()) {
                int[] cur = queue.poll();
                if (cur[0] == 1) {
                    val1 = cur[1];
                    freq1 = cur[2];
                    val2 = encoded2[j][0];
                    freq2 = encoded2[j][1];
                } else {
                    val2 = cur[1];
                    freq2 = cur[2];
                    val1 = encoded1[i][0];
                    freq1 = encoded1[i][1];
                }
            } else {
                val1 = encoded1[i][0];
                freq1 = encoded1[i][1];
                val2 = encoded2[j][0];
                freq2 = encoded2[j][1];
            }
            int overlap = Math.min(freq1, freq2);
            int product = val1 * val2;
            if (res.size() > 0 && product == res.get(k - 1).get(0)) {
                res.get(k - 1).set(1, res.get(k - 1).get(1) + overlap);
                k--;
            } else {
                res.add(new ArrayList<>());
                res.get(k).add(product);
                res.get(k).add(overlap);
            }
            k++;
            if (freq1 < freq2) {
                queue.offer(new int[]{2, val2, freq2 - freq1});
                i++;
            } else if (freq1 > freq2) {
                queue.offer(new int[]{1, val1, freq1 - freq2});
                j++;
            } else {
                i++;
                j++;
            }
        }
        return res;
    }
}
