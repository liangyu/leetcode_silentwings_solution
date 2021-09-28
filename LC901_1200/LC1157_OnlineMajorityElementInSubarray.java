package LC901_1200;
import java.util.*;
public class LC1157_OnlineMajorityElementInSubarray {
    /**
     * Design a data structure that efficiently finds the majority element of a given subarray.
     *
     * The majority element of a subarray is an element that occurs threshold times or more in the subarray.
     *
     * Implementing the MajorityChecker class:
     *
     * MajorityChecker(int[] arr) Initializes the instance of the class with the given array arr.
     * int query(int left, int right, int threshold) returns the element in the subarray arr[left...right] that occurs
     * at least threshold times, or -1 if no such element exists.
     *
     * Input
     * ["MajorityChecker", "query", "query", "query"]
     * [[[1, 1, 2, 2, 1, 1]], [0, 5, 4], [0, 3, 3], [2, 3, 2]]
     * Output
     * [null, 1, -1, 2]
     *
     * Constraints:
     *
     * 1 <= arr.length <= 2 * 10^4
     * 1 <= arr[i] <= 2 * 10^4
     * 0 <= left <= right < arr.length
     * threshold <= right - left + 1
     * 2 * threshold > right - left + 1
     * At most 10^4 calls will be made to query.
     * @param arr
     */
    // S1：Greedy + B.S.
    HashMap<Integer, List<Integer>> map; // val -> {pos}
    List<int[]> nums;
    public LC1157_OnlineMajorityElementInSubarray(int[] arr) {
        map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.putIfAbsent(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i);
        }

        nums = new ArrayList<>();
        for (int key : map.keySet()) {
            nums.add(new int[]{map.get(key).size(), key}); // {freq, val}
        }

        Collections.sort(nums, (o1, o2) -> o2[0] - o1[0]);
    }

    public int query(int left, int right, int threshold) {
        int total = right - left + 1;
        for (int i = 0; i < nums.size(); i++) {
            int[] x = nums.get(i);
            int a = lowerBound(map.get(x[1]), left);
            int b = upperBound(map.get(x[1]), right);
            total -= b - a + 1;
            if (b - a + 1 >= threshold) return x[1];
            if (map.get(x[1]).size() < threshold) return -1;
            if (total < threshold) return -1;
        }
        return -1;
    }

    private int lowerBound(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) left = mid + 1;
            else right = mid;
        }
        return list.get(left) >= target ? left : left + 1;
    }

    private int upperBound(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (list.get(mid) <= target) left = mid;
            else right = mid - 1;
        }
        return list.get(left) <= target ? left : left - 1;
    }

    // S2: Segment Tree
    // init: time = O(n), space = O(n); query: time = O(logn), space = O(n)
    HashMap<Integer, List<Integer>> map2;
    int[] nums2;
    SegTreeNode root;
//    public LC1157_OnlineMajorityElementInSubarray(int[] arr) {
//        nums2 = arr.clone();
//        int n = arr.length;
//        map = new HashMap<>();
//        root = new SegTreeNode(0, n - 1);
//        init(root, 0, n - 1);
//
//        for (int i = 0; i < arr.length; i++) {
//            map.putIfAbsent(arr[i], new ArrayList<>());
//            map.get(arr[i]).add(i);
//        }
//    }

    public int query2(int left, int right, int threshold) {
        int[] x = queryRange(root, left, right);

        if (map.containsKey(x[0])) {
            int a = lowerBound(map2.get(x[0]), left);
            int b = upperBound(map2.get(x[0]), right);

            int count = b - a + 1;
            if (count >= threshold) return x[0];
        }
        return -1;
    }

    private class SegTreeNode {
        private SegTreeNode left, right;
        int start, end;
        int val, freqDiff;
        public SegTreeNode(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private void init(SegTreeNode node, int a, int b) { // recursion
        // base case - single point
        if (a == b) {
            node.val = nums2[a];
            node.freqDiff = 1; // 1 - 0 = 1
            return;
        }
        int mid = (a + b) / 2;
        if (node.left == null) { // 要开必须左右一起开
            node.left = new SegTreeNode(a, mid);
            node.right = new SegTreeNode(mid + 1, b);
        }
        init(node.left, a, mid);
        init(node.right, mid + 1, b);

        if (node.left.val == node.right.val) {
            node.val = node.left.val;
            node.freqDiff = node.left.freqDiff + node.right.freqDiff;
        } else if (node.left.freqDiff > node.right.freqDiff) {
            node.val = node.left.val;
            node.freqDiff = node.left.freqDiff - node.right.freqDiff;
        } else {
            node.val = node.right.val;
            node.freqDiff = node.right.freqDiff - node.left.freqDiff;
        }
    }

    private int[] queryRange(SegTreeNode node, int a, int b) {
        if (b < node.start || a > node.end) return new int[]{0, 0};
        if (a <= node.start && node.end <= b) return new int[]{node.val, node.freqDiff};

        int[] L = queryRange(node.left, a, b);
        int[] R = queryRange(node.right, a, b);

        if (L[0] == R[0]) {
            return new int[]{L[0], L[1] + R[1]};
        } else if (L[1] >= R[1]) {
            return new int[]{L[0], L[1] - R[1]};
        } else {
            return new int[]{R[0], R[1] - L[1]};
        }
    }
}
/**
 * 任何区间内不可能有2个元素 >= threshold (抽屉原理)
 * 贪心想法：把数值都遍历一遍，优先遍历频次较高的那些
 * 预处理：
 * val: [pos0, pos1, l, pos2, pos3, ..., posN-1, r, posN]
 * [l, r] 二分查找，跟threshold比较一下；不是的话，就再换个val2重新做一遍
 * 贪心 + 二分搜索 -> 有个条件没用到，对于threshold的范围没用到 2 * threshold > right - left + 1
 * => 线段树的做法，比较高级的用法
 *
 * S2: Segment Tree
 * 线段树每个结点都有个info
 * 在这道题里，区间的info指的是什么呢？
 * 这个node的info
 * node -> val => the majority candidate within the range
 * node -> freqDiff => the frequency diff between the best majority candidate and all other members
 * Boyer-Moore majority vote algorithm => LC169
 * a, b  如果a和b都不是这个集合里的majority,那么我们把这两个数都删掉的话，不影响这个集合里剩余元素的归属
 * 1 1 1 1 1 1 2 2 3 3
 * a = 2, b = 3
 * 如果a和b里有一个是majority的话，把a和b删掉的话，也不会影响这个majority
 * 找2个数，如果2个数不同：
 * 1. 频率优势会扩大，因为删除2个少数派
 * 2. 如果删除1个多数派，一个少数派，那么频率优势不变
 * 它能反推是不是majority
 *            {4, 2}
 *       /            \
 * [4 4 4 4 5]   [5 5 5 4 4]  [5 5 5 6 6]
 * node: {4, 3}   {5, 1}
 * val: 并不一定代表majority element, 可能只是best candidate,如果它都不是，那么就没有majority
 * freqDiff
 * 如果抽中2个都是相同的数，这个时候majority element就是这个数，freqDiff就做和。
 * 如果freqDiff == 0，那么majority就没有意义了，一定不存在majority element
 */
