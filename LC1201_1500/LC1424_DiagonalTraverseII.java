package LC1201_1500;
import java.util.*;
public class LC1424_DiagonalTraverseII {
    /**
     * Given a list of lists of integers, nums, return all elements of nums in diagonal order as shown in the below
     * images.
     *
     * Input: nums = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: [1,4,2,7,5,3,8,6,9]
     *
     * Input: nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
     * Output: [1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]
     *
     * Input: nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
     * Output: [1,4,2,5,3,8,6,9,7,10,11]
     *
     * @param nums
     * @return
     */
    // S1: LinkedHashMap (最优解) -> 对角线i + j唯一且相同，LinkedHashMap 保持插入排序，效率最优
    // time = O(n), space = O(n)   n: total amount of numbers in the list
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        // corner case
        if (nums == null || nums.size() == 0) return new int[0];

        HashMap<Integer, List<Integer>> map = new LinkedHashMap<>();
        int count = 0;

        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {
                map.putIfAbsent(i + j, new ArrayList<>());
                map.get(i + j).add(nums.get(i).get(j));
                count++;
            }
        }

        int[] res = new int[count];
        int k = 0;
        for (int key : map.keySet()) {
            List<Integer> list = map.get(key);
            for (int i = list.size() - 1; i >= 0; i--) { // 最后的结果必须倒过来输出，因为输入的时候是先从后面开始的
                res[k++] = list.get(i);
            }
        }
        return res;
    }

    // S2: Group + Sort
    // time = O(nlogn), space = O(n)   n: total amount of numbers in the list
    public int[] findDiagonalOrder2(List<List<Integer>> nums) {
        // corner case
        if (nums == null || nums.size() == 0) return new int[0];

        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {   // O(n)
                list.add(new Cell(i, i + j, nums.get(i).get(j)));
            }
        }

        Collections.sort(list, (o1, o2) -> o1.sum != o2.sum ? o1.sum - o2.sum : o2.x - o1.x); // O(nlogn)
        int[] res = new int[list.size()];
        int i = 0;
        for (Cell cell : list) res[i++] = nums.get(cell.x).get(cell.sum - cell.x); // o(n)
        return res;
    }

    private class Cell {
        private int x;
        private int sum; // sum = x + y
        private int val; // nums[x][y]
        public Cell(int x, int sum, int val) {
            this.x = x;
            this.sum = sum;
            this.val = val;
        }
    }
}

// 1. Notice that numbers with equal sums of row and column indexes belong to the same diagonal.
// 2. Store them in tuples (sum, row, val), sort them, and then regroup the answer.