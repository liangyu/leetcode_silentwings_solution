package LC2101_2400;
import java.util.*;
public class LC2286_BookingConcertTicketsinGroups {
    /**
     * A concert hall has n rows numbered from 0 to n - 1, each with m seats, numbered from 0 to m - 1. You need to
     * design a ticketing system that can allocate seats in the following cases:
     *
     * If a group of k spectators can sit together in a row.
     * If every member of a group of k spectators can get a seat. They may or may not sit together.
     * Note that the spectators are very picky. Hence:
     *
     * They will book seats only if each member of their group can get a seat with row number less than or equal to
     * maxRow. maxRow can vary from group to group.
     * In case there are multiple rows to choose from, the row with the smallest number is chosen. If there are multiple
     * seats to choose in the same row, the seat with the smallest number is chosen.
     * Implement the BookMyShow class:
     *
     * BookMyShow(int n, int m) Initializes the object with n as number of rows and m as number of seats per row.
     * int[] gather(int k, int maxRow) Returns an array of length 2 denoting the row and seat number (respectively) of
     * the first seat being allocated to the k members of the group, who must sit together. In other words, it returns
     * the smallest possible r and c such that all [c, c + k - 1] seats are valid and empty in row r, and r <= maxRow.
     * Returns [] in case it is not possible to allocate seats to the group.
     * boolean scatter(int k, int maxRow) Returns true if all k members of the group can be allocated seats in rows 0 to
     * maxRow, who may or may not sit together. If the seats can be allocated, it allocates k seats to the group with
     * the smallest row numbers, and the smallest possible seat numbers in each row. Otherwise, returns false.
     *
     * Input
     * ["BookMyShow", "gather", "gather", "scatter", "scatter"]
     * [[2, 5], [4, 0], [2, 0], [5, 1], [5, 1]]
     * Output
     * [null, [0, 0], [], true, false]
     *
     * Constraints:
     *
     * 1 <= n <= 5 * 10^4
     * 1 <= m, k <= 10^9
     * 0 <= maxRow <= n - 1
     * At most 5 * 10^4 calls in total will be made to gather and scatter.
     * @param n
     * @param m
     */
    int m;
    int[] booked, maxGather;
    long[] maxScatter;
    public LC2286_BookingConcertTicketsinGroups(int n, int m) {
        this.m = m;
        this.booked = new int[n];
        this.maxGather = new int[n];
        Arrays.fill(maxGather, m);
        this.maxScatter = new long[n];
        maxScatter[0] = m;
        for (int i = 1; i < n; i++) maxScatter[i] = maxScatter[i - 1] + m;
    }

    // time = O(maxRow), space = O(n)
    public int[] gather(int k, int maxRow) {
        int[] res = new int[0];
        if (maxGather[maxRow] < k) return res;

        for (int i = 0; i <= maxRow; i++) {
            if (k <= m - booked[i]) {
                res = new int[]{i, booked[i]};
                booked[i] += k;
                return res;
            }
        }

        updatedMaxGatter(maxRow);
        return res;
    }

    // time = O(maxRow), space = O(n)
    public boolean scatter(int k, int maxRow) {
        if (maxScatter[maxRow] < k) return false;

        if (!updateMaxScatter(k, maxRow)) return false;

        for (int i = 0; i <= maxRow && k > 0; i++) {
            int rem = (int) Math.min(m - booked[i], (long) k);
            booked[i] += rem;
            k -= rem;
        }
        return true;
    }

    private void updatedMaxGatter(int maxRow) {
        maxGather[0] = m - booked[0];
        for (int i = 1; i <= maxRow; i++) {
            maxGather[i] = Math.max(maxGather[i - 1], m - booked[i]);
        }
    }

    private boolean updateMaxScatter(int k, int maxRow) {
        long temp = 0;
        for (int i = 0; i <= maxRow; i++) {
            temp += m - booked[i];
            maxScatter[i] = temp;
            if (temp >= k) return true;
        }
        return false;
    }

    // S2: Segment Tree
    // time = O(
    class BookMyShow {
        int n, m;
        int[] seats;
        int p = 0;
        SegTreeNode root;
        SegTreeNode2 root2;
        // time = O(n), space = O(n)
        public BookMyShow(int n, int m) {
            this.n = n;
            this.m = m;

            root = new SegTreeNode(0, n - 1, m);
            root2 = new SegTreeNode2(0, n - 1, m);

            seats = new int[n];
            for (int i = 0; i < n; i++) seats[i] = m;
        }
        // time = O(logn), space = O(n)
        public int[] gather(int k, int maxRow) {
            int low = 0, high = maxRow;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (root2.queryRange(0, mid) >= k) high = mid;
                else low = mid + 1;
            }
            if (root2.queryRange(low, low) < k) return new int[0];
            seats[low] -= k;
            root.updateRange(low, low, seats[low]);
            root2.updateRange(low, low, seats[low]);
            return new int[]{low, m - (seats[low] + k)};
        }
        // time = O((n + q) * logn), space = O(n)
        public boolean scatter(int k, int maxRow) {
            if (root.queryRange(0, maxRow) < k) return false;
            while (k > 0) {
                int tickets = Math.min(k, seats[p]);
                seats[p] -= tickets;
                k -= tickets;
                root.updateRange(p, p, seats[p]);
                root2.updateRange(p, p, seats[p]);
                if (seats[p] == 0) p++;
            }
            return true;
        }

        private class SegTreeNode {
            SegTreeNode left, right;
            int start, end;
            long info;
            boolean tag;

            public SegTreeNode(int a, int b, int val) {
                start = a;
                end = b;
                tag = false;
                if (a == b) {
                    info = val;
                    return;
                }

                int mid = (a + b) / 2;
                if (left == null) {
                    left = new SegTreeNode(a, mid, val);
                    right = new SegTreeNode(mid + 1, b, val);
                    info = left.info + right.info;
                }
            }

            private void pushDown() {
                if (tag == true && left != null) {
                    left.info = info;
                    right.info = info;
                    left.tag = true;
                    right.tag = true;
                    tag = false;
                }
            }

            private void updateRange(int a, int b, int val) {
                if (b < start || a > end) return;
                if (a <= start && end <= b) {
                    info = val * (end - start + 1);
                    tag = true;
                    return;
                }

                if (left != null) {
                    pushDown();
                    left.updateRange(a, b, val);
                    right.updateRange(a, b, val);
                    info = left.info + right.info;
                }
            }

            private long queryRange(int a, int b) {
                if (b < start || a > end) return 0;
                if (a <= start && end <= b) return info;
                if (left != null) {
                    pushDown();
                    long res = left.queryRange(a, b) + right.queryRange(a, b);
                    info = left.info + right.info;
                    return res;
                }
                return info;
            }
        }

        private class SegTreeNode2 {
            SegTreeNode2 left, right;
            int start, end;
            int info;
            boolean tag;

            public SegTreeNode2(int a, int b, int val) {
                start = a;
                end = b;
                tag = false;
                if (a == b) {
                    info = val;
                    return;
                }

                int mid = (a + b) / 2;
                if (left == null) {
                    left = new SegTreeNode2(a, mid, val);
                    right = new SegTreeNode2(mid + 1, b, val);
                    info = Math.max(left.info, right.info);
                }
            }

            private void pushDown() {
                if (tag == true && left != null) {
                    left.info = info;
                    right.info = info;
                    left.tag = true;
                    right.tag = true;
                    tag = false;
                }
            }

            private void updateRange(int a, int b, int val) {
                if (b < start || a > end) return;
                if (a <= start && end <= b) {
                    info = val;
                    tag = true;
                    return;
                }

                if (left != null) {
                    pushDown();
                    left.updateRange(a, b, val);
                    right.updateRange(a, b, val);
                    info = Math.max(left.info, right.info);
                }
            }

            private int queryRange(int a, int b) {
                if (b < start || a > end) return Integer.MIN_VALUE;
                if (a <= start && end <= b) return info;
                if (left != null) {
                    pushDown();
                    int res = Math.max(left.queryRange(a, b), right.queryRange(a, b));
                    info = Math.max(left.info, right.info);
                    return res;
                }
                return info;
            }
        }
    }
/**
 * Your BookMyShow object will be instantiated and called as such:
 * BookMyShow obj = new BookMyShow(n, m);
 * int[] param_1 = obj.gather(k,maxRow);
 * boolean param_2 = obj.scatter(k,maxRow);
 */
}
/**
 * seats[i]: the empty seats for the i-th row
 * p -> 0,1,2,... the smallest row # which has empty seats
 * queryRangeSum[0, maxRow] >= k ? => 动态变动seats
 * updateRange(a, b, val)
 * gather:
 * the smallest row # which has more than k empty seats
 * queryRangeMax[0, r] >= k
 */