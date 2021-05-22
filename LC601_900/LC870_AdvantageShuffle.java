package LC601_900;
import java.util.*;
public class LC870_AdvantageShuffle {
    /**
     * Given two arrays A and B of equal size, the advantage of A with respect to B is the number of indices i for
     * which A[i] > B[i].
     *
     * Return any permutation of A that maximizes its advantage with respect to B.
     *
     * Input: A = [2,7,11,15], B = [1,10,4,11]
     * Output: [2,11,7,15]
     *
     * Note:
     *
     * 1 <= A.length = B.length <= 10000
     * 0 <= A[i] <= 10^9
     * 0 <= B[i] <= 10^9
     * @param A
     * @param B
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[] advantageCount(int[] A, int[] B) {
        // corner case
        if (A == null || A.length == 0 || B == null || B.length == 0) return new int[0];

        int n = A.length;
        int[] res = new int[n];

        Arrays.sort(A); // O(nlogn)
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
        for (int i = 0; i < n; i++) maxHeap.offer(new int[]{i, B[i]}); // O(nlogn)

        int hi = n - 1, lo = 0;
        while (!maxHeap.isEmpty()) {  // O(nlogn)
            int idx = maxHeap.peek()[0], val = maxHeap.poll()[1];
            if (A[hi] > val) res[idx] = A[hi--];
            else res[idx] = A[lo++];
        }
        return res;
    }
}
