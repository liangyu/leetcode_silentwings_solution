package LC1801_2100;
import java.util.*;
public class LC2092_FindAllPeopleWithSecret {
    /**
     * You are given an integer n indicating there are n people numbered from 0 to n - 1. You are also given a
     * 0-indexed 2D integer array meetings where meetings[i] = [xi, yi, timei] indicates that person xi and person yi
     * have a meeting at timei. A person may attend multiple meetings at the same time. Finally, you are given an
     * integer firstPerson.
     *
     * Person 0 has a secret and initially shares the secret with a person firstPerson at time 0. This secret is then
     * shared every time a meeting takes place with a person that has the secret. More formally, for every meeting, if
     * a person xi has the secret at timei, then they will share the secret with person yi, and vice versa.
     *
     * The secrets are shared instantaneously. That is, a person may receive the secret and share it with people in
     * other meetings within the same time frame.
     *
     * Return a list of all the people that have the secret after all the meetings have taken place. You may return the
     * answer in any order.
     *
     * Input: n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
     * Output: [0,1,2,3,5]
     *
     * Constraints:
     *
     * 2 <= n <= 10^5
     * 1 <= meetings.length <= 10^5
     * meetings[i].length == 3
     * 0 <= xi, yi <= n - 1
     * xi != yi
     * 1 <= timei <= 10^5
     * 1 <= firstPerson <= n - 1
     * @param n
     * @param meetings
     * @param firstPerson
     * @return
     */
    // S1: Union Find
    // time = O((m * (logm + logn)), space = O(n)
//    private int[] parent;
//    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
//        parent = new int[n];
//        for (int i = 0; i < n; i++) parent[i] = i;
//
//        union(0, firstPerson);
//        Arrays.sort(meetings, (o1, o2) -> o1[2] - o2[2]); // O(mlogm)
//
//        int i = 0, m = meetings.length;
//        while (i < m) { // O(mlogn)
//            List<Integer> temp = new ArrayList<>();
//            int time = meetings[i][2];
//            while (i < m && meetings[i][2] == time) {
//                int a = meetings[i][0], b = meetings[i][1];
//                if (findParent(a) != findParent(b)) union(a, b);
//                temp.add(a);
//                temp.add(b);
//                i++;
//            }
//            for (int x : temp) { // check if they were unioned to 0
//                if (findParent(x) != 0) parent[x] = x; // not pointing to 0 -> revert
//            }
//        }
//        List<Integer> res = new ArrayList<>();
//        for (i = 0; i < n; i++) {
//            if (findParent(i) == 0) res.add(i);
//        }
//        return res;
//    }
//
//    private int findParent(int x) {
//        if (x != parent[x]) parent[x] = findParent(parent[x]);
//        return parent[x];
//    }
//
//    private void union(int x, int y) {
//        x = parent[x];
//        y = parent[y];
//        if (x < y) parent[y] = x;
//        else parent[x] = y;
//    }

    // S1.2: Union Find
    // time = O((m * (logm + logn)), space = O(n)
    private int[] parent;
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        Arrays.sort(meetings, (o1, o2) -> o1[2] - o2[2]);
        union(0, firstPerson);
        HashSet<Integer> set = new HashSet<>();
        set.add(0);
        set.add(firstPerson);

        for (int i = 0; i < meetings.length; i++) {
            int j = i;
            int time = meetings[i][2];
            HashSet<Integer> persons = new HashSet<>();
            while (j < meetings.length && meetings[j][2] == time) {
                int a = meetings[j][0], b = meetings[j][1];
                persons.add(a);
                persons.add(b);
                if (findParent(a) != findParent(b)) union(a, b);
                j++;
            }
            for (int x : persons) {
                if (findParent(x) == 0) set.add(x);
                else parent[x] = x;
            }
            i = j - 1;
        }
        List<Integer> res = new ArrayList<>();
        res.addAll(set);
        return res;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }
}
/**
 * 同一个时刻允许有多个meeting，并且可以有同一个人
 * 同一个时刻里的会议是可以瞬时传递的
 * 把meeting按照时间顺序排列下
 * Set{...}
 * meeting {a, b}
 * meeting {a, c}
 * meeting {b, d}
 * meeting时刻相同，会有些麻烦
 * a,c知道秘密后不得不往回看，不太好处理
 * 先处理有知情者参加的meeting，然后在剩下的里面找有传播者的meeting => 拓扑排序？写好不容易
 * 更直观的写法就是Union Find，有传导性的算法
 * {a,b,c,d} -> 只要有一个在set里面，那么整个都会知情
 * 知情者祖先都为0 -> root都指向0
 * meeting {x, y}
 * {x, y} => {x}, {y} 拆开来
 * meeting {x, a} -> x变成知情者，由于之前x,y union在一起了，y也知情了？不对，因为是上个回合知情的，所以为了避免这个，必须把x,y拆开来。
 * 我们只希望知情者union起来，不知情的Union后要拆开来。
 */