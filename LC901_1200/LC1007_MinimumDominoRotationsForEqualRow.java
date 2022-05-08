package LC901_1200;
import java.util.*;
public class LC1007_MinimumDominoRotationsForEqualRow {
    /**
     * In a row of dominoes, tops[i] and bottoms[i] represent the top and bottom halves of the ith domino. (A domino is
     * a tile with two numbers from 1 to 6 - one on each half of the tile.)
     *
     * We may rotate the ith domino, so that tops[i] and bottoms[i] swap values.
     *
     * Return the minimum number of rotations so that all the values in tops are the same, or all the values in bottoms
     * are the same.
     *
     * If it cannot be done, return -1.
     *
     * Input: tops = [2,1,2,4,2,2], bottoms = [5,2,6,2,3,2]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= tops.length <= 2 * 10^4
     * bottoms.length == tops.length
     * 1 <= tops[i], bottoms[i] <= 6
     * @param tops
     * @param bottoms
     * @return
     */
    // S1: Ennumeration
    // time = O(n), space = O(n)
    public int minDominoRotations(int[] tops, int[] bottoms) {
        int res = Integer.MAX_VALUE;
        int n = tops.length;
        for (int i = 1; i <= 6; i++) {
            int[] nums = helper(tops, bottoms, i);
            if (nums[0] == -1) continue;
            if (nums[0] + nums[1] >= n) {
                res = Math.min(res, n - Math.max(nums[0], nums[1]));
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private int[] helper(int[] tops, int[] bottoms, int t) {
        int[] res = new int[2];
        int n = tops.length;
        for (int i = 0; i < n; i++) {
            if (tops[i] == t) res[0]++;
            if (bottoms[i] == t) res[1]++;
            if (tops[i] != t && bottoms[i] != t) return new int[]{-1, -1};
        }
        return res;
    }

    // S2: two hashset
    // time = O(n), space = O(n)
    public int minDominoRotations2(int[] tops, int[] bottoms) {
        HashSet<Integer>[] nums1 = new HashSet[7];
        HashSet<Integer>[] nums2 = new HashSet[7];

        for (int i = 0; i <= 6; i++) nums1[i] = new HashSet<>();
        for (int i = 0; i <= 6; i++) nums2[i] = new HashSet<>();

        int n = tops.length;
        for (int i = 0; i < n; i++) {
            int top = tops[i], bot = bottoms[i];
            nums1[top].add(i);
            nums2[bot].add(i);
        }

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= 6; i++) {
            if (nums1[i].size() + nums2[i].size() >= n) {
                boolean flag = true;
                for (int j = 0; j < n; j++) {
                    if (!nums1[i].contains(j) && !nums2[i].contains(j)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) res = Math.min(res, n - Math.max(nums1[i].size(), nums2[i].size()));
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
