package LC301_600;
import java.util.*;
public class LC350_IntersectionofTwoArraysII {
    /**
     * Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must
     * appear as many times as it shows in both arrays and you may return the result in any order.
     *
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2,2]
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 1000
     *
     *
     * Follow up:
     *
     * What if the given array is already sorted? How would you optimize your algorithm?
     * What if nums1's size is small compared to nums2's size? Which algorithm is better?
     * What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements
     * into the memory at once?
     * @param nums1
     * @param nums2
     * @return
     */
    // S1: HashMap
    // time = O(m + n), space = O(m + n)
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int x : nums1) map.put(x, map.getOrDefault(x, 0) + 1);

        List<Integer> res = new ArrayList<>();
        for (int x : nums2) {
            if (map.containsKey(x)) {
                res.add(x);
                map.put(x, map.get(x) - 1);
                if (map.get(x) == 0) map.remove(x);
            }
        }
        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }

    // S2: Sort
    // time = O(mlogm + nlogn), space = O(1)
    public int[] intersect2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int m = nums1.length, n = nums2.length;
        int i = 0, j = 0;
        List<Integer> res = new ArrayList<>();
        while (i < m && j < n) {
            if (nums1[i] == nums2[j]) {
                res.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] < nums2[j]) i++;
            else j++;
        }
        int[] ans = new int[res.size()];
        for (i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }
}
/**
 * follow-up:
 * 1. use two pointers (S2)
 * 2. use HashMap is better (S1)
 * 3. use external sort:
 * 先分解成若干个可以塞入内存的小文件，然后把一个文件塞入内存里，排个序，然后输出到一个文件里
 * => 先分解成若干个小文件，这些小文件内部都有序，
 * 然后把若干个小文件归并到一个文件里即可。
 * 归并还是分块递归 => 先比如每三路归并 n / 3 => 再三路归并 n / 3^2 => O(logn)
 *
 * 思考题：
 *
 * 如果给定的数组已经排好序，你可以怎样优化你的算法？
 * 答：可以用双指针扫描。这样可以把空间复杂度降为 O(1)O(1)，但时间复杂度还是 O(n)O(n)；
 * 如果数组nums1的长度小于数组nums2的长度，哪种算法更好？、
 * 答：可以把nums1存入哈希表，然后遍历nums2。这样可以使用更少的内存，但时空复杂度仍是 O(n)O(n)；
 * 如果数组nums2存储在硬盘上，然而内存是有限的，你不能将整个数组都读入内存，该怎么做？
 * 答：如果nums1可以存入内存，则可以将nums1存入哈希表，然后分块将nums2读入内存，进行查找；如果两个数组都不能存入内存，
 * 可以先将两个数组分别排序，比如可以用外排序，然后用类似于双指针扫描的方法，将两个数组分块读入内存，进行查找。
 */
