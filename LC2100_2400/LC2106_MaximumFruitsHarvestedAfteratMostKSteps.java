package LC2100_2400;
import java.util.*;
public class LC2106_MaximumFruitsHarvestedAfteratMostKSteps {
    /**
     * Fruits are available at some positions on an infinite x-axis. You are given a 2D integer array fruits where
     * fruits[i] = [positioni, amounti] depicts amounti fruits at the position positioni. fruits is already sorted by
     * positioni in ascending order, and each positioni is unique.
     *
     * You are also given an integer startPos and an integer k. Initially, you are at the position startPos. From any
     * position, you can either walk to the left or right. It takes one step to move one unit on the x-axis, and you can
     * walk at most k steps in total. For every position you reach, you harvest all the fruits at that position, and the
     * fruits will disappear from that position.
     *
     * Return the maximum total number of fruits you can harvest.
     *
     * Input: fruits = [[2,8],[6,3],[8,6]], startPos = 5, k = 4
     * Output: 9
     *
     * Input: fruits = [[0,9],[4,1],[5,7],[6,2],[7,4],[10,9]], startPos = 5, k = 4
     * Output: 14
     *
     * Constraints:
     *
     * 1 <= fruits.length <= 10^5
     * fruits[i].length == 2
     * 0 <= startPos, positioni <= 2 * 10^5
     * positioni-1 < positioni for any i > 0 (0-indexed)
     * 1 <= amounti <= 10^4
     * 0 <= k <= 2 * 10^5
     * @param fruits
     * @param startPos
     * @param k
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int[] presum = new int[200005];
        int n = presum.length;
        for (int[] f : fruits) presum[f[0] + 1] = f[1];
        for (int i = 1; i < n; i++) presum[i] += presum[i - 1];
        startPos++; // 注意：坐标全部向右移动一位！！！

        int res = 0;
        // first go left, then right
        for (int d = 0; d <= k; d++) {
            int left = startPos - d;
            if (left - 1 < 0) break;
            int right = k - 2 * d + startPos;
            if (right >= n) right = n - 1;
            if (k - 2 * d < 0) right = startPos;
            res = Math.max(res, presum[right] - presum[left - 1]);
        }

        // first go right, then left
        for (int d = 0; d <= k; d++) {
            int right = startPos + d;
            if (right >= n) break;
            int left = startPos - (k - 2 * d);
            if (left - 1 < 0) left = 1;
            if (k - 2 * d < 0) left = startPos;
            res = Math.max(res, presum[right] - presum[left - 1]);
        }
        return res;
    }

    // S2: Two Pointers
    // time = O(n), space = O(n)
    public int maxTotalFruits2(int[][] fruits, int startPos, int k) {
        int n = fruits.length;
        int[] pos = new int[n], presum = new int[n];
        for (int i = 0; i < n; i++) {
            pos[i] = fruits[i][0];
            if (i == 0) presum[i] = fruits[i][1];
            else presum[i] = presum[i - 1] + fruits[i][1];
        }

        int res = 0;
        // first left, then go right
        // 2x + y <= k => y <= k => j - startPos <= k
        int idx = lowerBound(pos, startPos); // find the 1st pos >= startPos
        int i = 0;
        for (int j = idx; j < n; j++) {
            while (pos[i] <= startPos && 2 * (startPos - pos[i]) + pos[j] - startPos > k) i++; // 从左到右寻找左边界i
            if (startPos - pos[i] >= 0) res = Math.max(res, presum[j] - (i == 0 ? 0 : presum[i - 1]));
            // startPos未必就在水果点上，而A,B一定在水果点上，
            // 所以当跳出上面的while loop时，由于i++,A点可能落在了一个越过startPos的水果点上 => presum区间为[i:j]双闭区间
            // 同时注意，我们需要检查x=0情况下，B点本身是否是一个可行解。即如果pos[B]-startPos<=k，那么此时双闭区间[A,B]内的val都是有效答案。
            else if (pos[j] - startPos <= k) res = Math.max(res, presum[j] - (i == 0 ? 0 : presum[i - 1]));
        }

        // first go right, then go left
        idx = upperBound(pos, startPos);
        int j = n - 1;
        for (i = idx; i >= 0; i--) {
            while (pos[j] >= startPos && startPos - pos[i] + 2 * (pos[j] - startPos) > k) j--;
            if (pos[j] - startPos >= 0) res = Math.max(res, presum[j] - (i == 0 ? 0 : presum[i - 1]));
            else if (startPos - pos[i] <= k) res = Math.max(res, presum[j] - (i == 0 ? 0 : presum[i - 1]));
        }
        return res;
    }

    private int lowerBound(int[] nums, int t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < t) left = mid + 1;
            else right = mid;
        }
        return nums[left] >= t ? left : left + 1;
    }

    private int upperBound(int[] nums, int t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid] <= t) left = mid;
            else right = mid - 1;
        }
        return nums[left] <= t ? left : left - 1;
    }

    // S3: Two Pointers + BS
    // time = O(nlogn), space = O(n)
    public int maxTotalFruits3(int[][] fruits, int startPos, int k) {
        int n = fruits.length;
        int[] pos = new int[n], presum = new int[n];
        for (int i = 0; i < n; i++) {
            pos[i] = fruits[i][0];
            if (i == 0) presum[i] = fruits[i][1];
            else presum[i] = presum[i - 1] + fruits[i][1];
        }

        int res = 0;
        // first left, then go right
        // 2x + y <= k => y <= k => j - startPos <= k
        int idx = lowerBound(pos, startPos); // find the 1st pos >= startPos
        for (int j = idx; j < n; j++) {
            if (pos[j] - startPos > k) break;
            int d = (k - (pos[j] - startPos)) / 2; // x <= (k - y) / 2
            int i = lowerBound(pos, startPos - d); // 注意：距离转化为要找的pos: 左边界 = startPos - d;
            if (i >= 0 && i < n) res = Math.max(res, presum[j] - (i == 0 ? 0 : presum[i - 1]));
        }

        // first go right, then go left
        idx = upperBound(pos, startPos);
        for (int i = idx; i >= 0; i--) {
            if (startPos - pos[i] > k) break;
            int d = (k - (startPos - pos[i])) / 2;
            int j = upperBound(pos, startPos + d); // 寻找右边界的pos: 右边界 = startPos + d;
            if (j >= 0 && j < n) res = Math.max(res, presum[j] - (i == 0 ? 0 : presum[i - 1]));
        }
        return res;
    }
}
/**
 * 我们只会转一次方向，然后一路走到头，不可能再回头
 * 任何多余一次的转向都是浪费！
 * 2x + y <= k   y!= 0 => B是必须的
 * A和B不能离太远，B往右走，A也一定牵动着往右走 => two pointers
 * for循环遍历windows的右边界 => O(n)
 * 注意一个corner case: 如果B走的非常远，但如果A跨过start，那是无效的，但并不意味着B没有意义，只要y <= k即可。
 * S2: BS
 * 已知B，就知道A的期望
 * d <= (k - y) / 2
 * A >= s - d
 */
