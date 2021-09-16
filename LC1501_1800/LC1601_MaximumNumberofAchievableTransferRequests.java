package LC1501_1800;

public class LC1601_MaximumNumberofAchievableTransferRequests {
    /**
     * We have n buildings numbered from 0 to n - 1. Each building has a number of employees. It's transfer season,
     * and some employees want to change the building they reside in.
     *
     * You are given an array requests where requests[i] = [fromi, toi] represents an employee's request to transfer
     * from building fromi to building toi.
     *
     * All buildings are full, so a list of requests is achievable only if for each building, the net change in employee
     * transfers is zero. This means the number of employees leaving is equal to the number of employees moving in.
     * For example if n = 3 and two employees are leaving building 0, one is leaving building 1, and one is leaving
     * building 2, there should be two employees moving to building 0, one employee moving to building 1, and one
     * employee moving to building 2.
     *
     * Return the maximum number of achievable requests.
     *
     * Input: n = 5, requests = [[0,1],[1,0],[0,1],[1,2],[2,0],[3,4]]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= n <= 20
     * 1 <= requests.length <= 16
     * requests[i].length == 2
     * 0 <= fromi, toi < n
     * @param n
     * @param requests
     * @return
     */
    // S1: brute-force
    // time = O(m * n * 2^m), space = O(n)
    public int maximumRequests(int n, int[][] requests) {
        int m = requests.length, res = 0;
        for (int state = 0; state < (1 << m); state++) {
            if (check(state, n, requests)) res = Math.max(res, Integer.bitCount(state));
        }
        return res;
    }

    private boolean check(int s, int n, int[][] requests) {
        int[] building = new int[20]; // 看每幢楼的净输入

        int m = requests.length;
        for (int i = 0; i < m; i++) {
            if (((s >> i) & 1) == 1) {
                building[requests[i][0]]--;
                building[requests[i][1]]++;
            }
        }
        for (int i = 0; i < n; i++) {
            if (building[i] != 0) return false;
        }
        return true;
    }

    // S2: Greedy + bitmask
    // time = O(m^2 * k), space = O(m)
    public int maximumRequests2(int n, int[][] requests) {
        int m = requests.length, res = 0;
        for (int k = m; k >= 1; k--) { // O(m)
            int[] arr = new int[m];
            for (int i = m - k; i < m; i++) arr[i] = 1; // 初始化k个1都在最后面   O(k)
            while (true) {
                int state = 0;
                for (int i = 0; i < m; i++) { // O(m)
                    if (arr[i] == 1) {
                        state += (1 << i);
                    }
                }
                if (check(state, n, requests)) return k; // O(m)
                if (!nextPermutation(arr)) break; // O(m)
            }
        }
        return 0;
    }

    private boolean nextPermutation(int[] nums) { // O(n)
        // corner case
        if (nums == null || nums.length == 0) return false;

        int n = nums.length;
        int i = n - 1;

        while (i >= 1 && nums[i] <= nums[i - 1]) i--;

        // case 1: i == 0, no way to go to next permutation -> go to lowest order
        if (i == 0) {
            reverse(nums, 0);
            return false;
        }

        // case 2: i != 0 ->
        i--;
        int j = n - 1;
        while (j > i && nums[j] <= nums[i]) j--;
        swap(nums, i, j);
        reverse(nums, i + 1);
        return true;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i++, j--);
        }
    }

    // S3: Gospher's hack
    // time = O(m * 2^m), space = O(1)
    public int maximumRequests3(int n, int[][] requests) {
        int m = requests.length, res = 0;
        for (int k = m; k >= 1; k--) {
            int state = (1 << k) - 1;
            while (state < (1 << m)) {
                // do something(state)
                if (check(state, n, requests)) return k;

                int c = state & -state;
                int r = state + c;
                state = (((r ^ state) >> 2) / c) | r; // k 必须从1开始
            }
        }
        return 0;
    }
}
/**
 * 每幢楼净输入为0
 * 下手点：1 <= requests.length <= 16 => 2^16 = 65536 不会TLE
 * 遍历所有2^16组合，马上就可以知道所有building是否净输入为0
 * 2^16*20*16,遍历每一种request的组合，每一种都看下给20栋楼带来多大的冲击
 * bitMask
 * m: 2^m
 * for (int state = 0; state < (1 << m); state++)
 *
 * S2: 改进 -> 优先考察那些比特位是1比较多的那些state
 * 5
 * 11111 OK?
 * 11110  11101 11011 10111 01111   OK => 4
 * 不用再考虑1比较少的state，eg. 11010
 * 后面直接可以跳出来
 * 2个方法：
 * 都是4个1和1个0的全排列 => C++里有nextPermutation
 * 00111 => 01011 => 01101 => 01110
 *
 * S3: Gospher's hack -> 直接可以iterate含有k个bit 1的state
 * iterate all the m-bit integers where there are k 1-bits
 */
