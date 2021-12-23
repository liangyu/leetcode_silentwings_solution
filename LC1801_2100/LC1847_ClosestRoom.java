package LC1801_2100;
import java.util.*;
public class LC1847_ClosestRoom {
    /**
     * There is a hotel with n rooms. The rooms are represented by a 2D integer array rooms where rooms[i] = [roomIdi,
     * sizei] denotes that there is a room with room number roomIdi and size equal to sizei. Each roomIdi is guaranteed
     * to be unique.
     *
     * You are also given k queries in a 2D array queries where queries[j] = [preferredj, minSizej]. The answer to the
     * jth query is the room number id of a room such that:
     *
     * The room has a size of at least minSizej, and
     * abs(id - preferredj) is minimized, where abs(x) is the absolute value of x.
     * If there is a tie in the absolute difference, then use the room with the smallest such id. If there is no such
     * room, the answer is -1.
     *
     * Return an array answer of length k where answer[j] contains the answer to the jth query.
     *
     * Input: rooms = [[2,2],[1,2],[3,2]], queries = [[3,1],[3,3],[5,2]]
     * Output: [3,-1,3]
     *
     * Constraints:
     *
     * n == rooms.length
     * 1 <= n <= 10^5
     * k == queries.length
     * 1 <= k <= 10^4
     * 1 <= roomIdi, preferredj <= 10^7
     * 1 <= sizei, minSizej <= 10^7
     * @param rooms
     * @param queries
     * @return
     */
    // S1: Sort + TreeSet
    // time = O(mlogm + (m + n) * logn), space = O(m + n)
    // m: number of queries, n: number of rooms
    public int[] closestRoom(int[][] rooms, int[][] queries) {
        int[] res = new int[queries.length];
        // step 1: sort the rooms by size in descending order
        Arrays.sort(rooms, (o1, o2) -> o2[1] - o1[1]); // O(nlogn)

        // step 2: sort the queries by size in descending order and save in a new array
        int[][] que = new int[queries.length][3];
        for (int i = 0; i < queries.length; i++) { // O(m)
            que[i][0] = queries[i][0];
            que[i][1] = queries[i][1];
            que[i][2] = i; // save the original index of queries
        }
        Arrays.sort(que, (o1, o2) -> o2[1] - o1[1]); // O(mlogm)

        // step 3: search in the treeSet
        TreeSet<Integer> set = new TreeSet<>();
        int i = 0;
        for (int[] q : que) { // O(m)
            while (i < rooms.length && rooms[i][1] >= q[1]) {
                set.add(rooms[i][0]);  // O(logn)
                i++;
            }

            int ans = -1, diff = Integer.MAX_VALUE;
            Integer ck = set.ceiling(q[0]);
            Integer fk = set.floor(q[0]);

            if (ck != null) {
                if (Math.abs(ck - q[0]) < diff) {
                    diff = Math.abs(ck - q[0]);
                    ans = ck;
                }
            }

            if (fk != null) {
                if (Math.abs(fk - q[0]) <= diff) { // 注意：= 的时候也要更新！！！
                    diff = Math.abs(fk - q[0]);
                    ans = fk;
                }
            }
            res[q[2]] = ans;
        }
        return res;
    }

    // S2: HashMap + TreeSet (Time Limit Exceeded!!!)
    public int[] closestRoom2(int[][] rooms, int[][] queries) {
        int[] res = new int[queries.length];
        // step 1: build map
        HashMap<Integer, TreeSet<Integer>> map = new HashMap<>();
        int min = Integer.MAX_VALUE,max = Integer.MIN_VALUE;
        for (int[] r : rooms) {
            map.putIfAbsent(r[1], new TreeSet<>());
            map.get(r[1]).add(r[0]);
            min = Math.min(min, r[1]);
            max = Math.max(max, r[1]);
        }

        // step 2: search
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            if (q[1] > max) res[i] = -1;
            else res[i] = helper(map, q, min, max);
        }
        return res;
    }

    private int helper(HashMap<Integer, TreeSet<Integer>> map, int[] q, int min, int max) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        int start = Math.max(q[1], min);
        for (int i = start; i <= max; i++) {
            if (map.containsKey(i)) {
                TreeSet<Integer> set = map.get(i);
                Integer fk = set.floor(q[0]);
                Integer ck = set.ceiling(q[0]);
                if (fk != null) treeSet.add(fk);
                if (ck != null) treeSet.add(ck);
            }
        }

        int res = 100001;
        Integer fk = treeSet.floor(q[0]);
        Integer ck = treeSet.ceiling(q[0]);
        if (fk != null && ck != null) {
            int val1 = Math.abs(fk - q[0]);
            int val2 = Math.abs(ck - q[0]);
            if (val1 == val2) {
                res = Math.min(fk, ck);
            } else {
                res = val1 < val2 ? fk : ck;
            }
        } else res = fk != null ? fk : ck;
        return res;
    }
}
/**
 * 优先考虑minSize比较大的case，随着minSize bar的降低，把room size比较大的房间放入pool里面
 * 单调递增的过程，只要插入就行，不需要再拿出来
 * LC1697, LC1707 -> 根据minSize排个序
 * 新加一个房间O(logN),只加不删，找一个最接近的房间上界和下界O(logN)
 * 真正在pool里面看的是id
 * 1697 off-line querying + Union Find
 * 1707 off-line querying + trie
 * 1847 off-line querying + heap
 * python: sorted container
 * c++: pbds
 * 不但能找到最接近的id,还能知道这个id是container里的第几个
 */