package LC1201_1500;

public class LC1231_DivideChocolate {
    /**
     * You have one chocolate bar that consists of some chunks. Each chunk has its own sweetness given by the array
     * sweetness.
     *
     * You want to share the chocolate with your k friends so you start cutting the chocolate bar into k + 1 pieces
     * using k cuts, each piece consists of some consecutive chunks.
     *
     * Being generous, you will eat the piece with the minimum total sweetness and give the other pieces to your
     * friends.
     *
     * Find the maximum total sweetness of the piece you can get by cutting the chocolate bar optimally.
     *
     * Input: sweetness = [1,2,3,4,5,6,7,8,9], k = 5
     * Output: 6
     *
     * Constraints:
     *
     * 0 <= k < sweetness.length <= 10^4
     * 1 <= sweetness[i] <= 10^5
     * @param sweetness
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int maximizeSweetness(int[] sweetness, int k) {
        // corner case
        if (sweetness == null || sweetness.length == 0 || k < 0) return 0;

        int sum = 0;
        for (int x : sweetness) sum += x;

        int left = 1, right = sum; // right = Integer.MAX_VALUE
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (count(sweetness, mid) >= k + 1) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private int count(int[] nums, int t) {
        int count = 0, sum = 0;
        for (int x : nums) {
            sum += x;
            if (sum >= t) { // 注意：这里是当切出来的sum >= t的时候，才能算作是有效的一块，否则低于t的都不能算，因为最小甜度得是t！！！
                count++;
                sum = 0;
            }
        }
        return count;
    }
}
