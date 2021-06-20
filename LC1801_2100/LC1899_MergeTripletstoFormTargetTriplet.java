package LC1801_2100;
import java.util.*;
public class LC1899_MergeTripletstoFormTargetTriplet {
    /**
     * A triplet is an array of three integers. You are given a 2D integer array triplets, where triplets[i] =
     * [ai, bi, ci] describes the ith triplet. You are also given an integer array target = [x, y, z] that describes
     * the triplet you want to obtain.
     *
     * To obtain target, you may apply the following operation on triplets any number of times (possibly zero):
     *
     * Choose two indices (0-indexed) i and j (i != j) and update triplets[j] to become [max(ai, aj), max(bi, bj),
     * max(ci, cj)].
     * For example, if triplets[i] = [2, 5, 3] and triplets[j] = [1, 7, 5], triplets[j] will be updated to [max(2, 1),
     * max(5, 7), max(3, 5)] = [2, 7, 5].
     * Return true if it is possible to obtain the target triplet [x, y, z] as an element of triplets, or false otherwise.
     *
     * Input: triplets = [[2,5,3],[1,8,4],[1,7,5]], target = [2,7,5]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= triplets.length <= 10^5
     * triplets[i].length == target.length == 3
     * 1 <= ai, bi, ci, x, y, z <= 1000
     * @param triplets
     * @param target
     * @return
     */
    // time = O(n), space = O(1)
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        int[] temp = new int[]{0, 0, 0};
        for (int[] t : triplets) {
            if (t[0] > target[0] || t[1] > target[1] || t[2] > target[2]) continue;
            temp[0] = Math.max(temp[0], t[0]);
            temp[1] = Math.max(temp[1], t[1]);
            temp[2] = Math.max(temp[2], t[2]);
            if (temp[0] == target[0] && temp[1] == target[1] && temp[2] == target[2]) return true;
        }
        return false;
    }
}
