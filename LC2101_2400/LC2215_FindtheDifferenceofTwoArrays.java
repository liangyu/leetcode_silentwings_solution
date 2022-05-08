package LC2101_2400;
import java.util.*;
public class LC2215_FindtheDifferenceofTwoArrays {
    /**
     * Given two 0-indexed integer arrays nums1 and nums2, return a list answer of size 2 where:
     *
     * answer[0] is a list of all distinct integers in nums1 which are not present in nums2.
     * answer[1] is a list of all distinct integers in nums2 which are not present in nums1.
     * Note that the integers in the lists may be returned in any order.
     *
     * Input: nums1 = [1,2,3], nums2 = [2,4,6]
     * Output: [[1,3],[4,6]]
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 1000
     * -1000 <= nums1[i], nums2[i] <= 1000
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(m + n), space = O(m + n)
    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {
        List<List<Integer>> res = new ArrayList<>();

        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        for (int x : nums1) set1.add(x);
        for (int x : nums2) set2.add(x);

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        for (int x : set1) {
            if (!set2.contains(x)) list1.add(x);
        }

        for (int x : set2) {
            if (!set1.contains(x)) list2.add(x);
        }
        res.add(list1);
        res.add(list2);
        return res;
    }
}
