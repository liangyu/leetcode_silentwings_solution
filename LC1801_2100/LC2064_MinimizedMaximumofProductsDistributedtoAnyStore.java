package LC1801_2100;

public class LC2064_MinimizedMaximumofProductsDistributedtoAnyStore {
    /**
     * You are given an integer n indicating there are n specialty retail stores. There are m product types of varying
     * amounts, which are given as a 0-indexed integer array quantities, where quantities[i] represents the number of
     * products of the ith product type.
     *
     * You need to distribute all products to the retail stores following these rules:
     *
     * A store can only be given at most one product type but can be given any amount of it.
     * After distribution, each store will be given some number of products (possibly 0). Let x represent the maximum
     * number of products given to any store. You want x to be as small as possible, i.e., you want to minimize the
     * maximum number of products that are given to any store.
     * Return the minimum possible x.
     *
     * Input: n = 6, quantities = [11,6]
     * Output: 3
     *
     * Constraints:
     *
     * m == quantities.length
     * 1 <= m <= n <= 10^5
     * 1 <= quantities[i] <= 10^5
     * @param n
     * @param quantities
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int minimizedMaximum(int n, int[] quantities) {
        int left = 1, right = 100000; // 注意：这里left一定要从1开始，否则下面 / t的时候会出错，/0是不行的！！！
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (helper(quantities, n, mid)) right = mid;
            else left = mid + 1;
        }
        return left;
    }

    private boolean helper(int[] quantites, int n, int t) {
        int count = 0;
        for (int q : quantites) {
            count += (q - 1) / t + 1; // count += (int) Math.ceil(q * 1.0 / t);
        }
        return count <= n;
    }
}
/**
 * 希望尽量的能够平铺 => 典型二分搜值
 * 设定一个上限，得到所需要征用的商店数目，和n比较
 * 如果比n小，说明上限可以降一下，还有其他商店可以用；否则就可以升一下，因为商店不够了。
 */