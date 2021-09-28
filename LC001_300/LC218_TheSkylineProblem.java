package LC001_300;
import java.util.*;
public class LC218_TheSkylineProblem {
    /**
     * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed
     * from a distance. Given the locations and heights of all the buildings, return the skyline formed by these
     * buildings collectively.
     *
     * The geometric information of each building is given in the array buildings where
     * buildings[i] = [lefti, righti, heighti]:
     *
     * lefti is the x coordinate of the left edge of the ith building.
     * righti is the x coordinate of the right edge of the ith building.
     * heighti is the height of the ith building.
     * You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
     *
     * The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form
     * [[x1,y1],[x2,y2],...]. Each key point is the left endpoint of some horizontal segment in the skyline except the
     * last point in the list, which always has a y-coordinate 0 and is used to mark the skyline's termination where the
     * rightmost building ends. Any ground between the leftmost and rightmost buildings should be part of
     * the skyline's contour.
     *
     * Note: There must be no consecutive horizontal lines of equal height in the output skyline. For instance,
     * [...,[2 3],[4 5],[7 5],[11 5],[12 7],...] is not acceptable; the three lines of height 5 should be merged into
     * one in the final output as such: [...,[2 3],[4 5],[12 7],...]
     *
     * Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
     * Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
     *
     * Constraints:
     *
     * 1 <= buildings.length <= 10^4
     * 0 <= lefti < righti <= 2^31 - 1
     * 1 <= heighti <= 2^31 - 1
     * buildings is sorted by lefti in non-decreasing order.
     * @param buildings
     * @return
     */
    // S1: Sweep Line
    // time = O(n^2), space = O(n)
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (buildings == null || buildings.length == 0 || buildings[0] == null || buildings[0].length == 0) {
            return res;
        }

        List<int[]> heights = new ArrayList<>();
        for (int[] b : buildings) {
            heights.add(new int[]{b[0], -b[2]});
            heights.add(new int[]{b[1], b[2]});
        }

        Collections.sort(heights, (o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]);
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        pq.offer(0);

        int prev = 0;
        for (int[] h : heights) {
            if (h[1] < 0) pq.offer(-h[1]);
            else pq.remove(h[1]);
            int cur = pq.peek();
            if (prev != cur) {
                res.add(Arrays.asList(h[0], cur));
                prev = cur;
            }
        }
        return res;
    }

    // S2: Interval
    // time = O(n^2), space = O(n)
    public List<List<Integer>> getSkyline2(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (buildings == null || buildings.length == 0 || buildings[0] == null || buildings[0].length == 0) {
            return res;
        }

        List<EndPoint> eps = new ArrayList<>();
        for (int[] b : buildings) {
            eps.add(new EndPoint(b[0], b[2], true));
            eps.add(new EndPoint(b[1], b[2], false));
        }

        Collections.sort(eps);
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

        for (EndPoint ep : eps) {
            if (ep.isStart) {
                int maxHeight = maxHeap.isEmpty() ? 0 : maxHeap.peek();
                if (maxHeight < ep.height) {
                    res.add(Arrays.asList(ep.val, ep.height));
                }
                maxHeap.offer(ep.height);
            } else {
                maxHeap.remove(ep.height);
                int maxHeight = maxHeap.isEmpty() ? 0 : maxHeap.peek();
                if (maxHeight < ep.height) {
                    res.add(Arrays.asList(ep.val, maxHeight));
                }
            }
        }
        return res;
    }

    private class EndPoint implements Comparable<EndPoint> {
        private int val;
        private int height;
        private boolean isStart;
        public EndPoint(int val, int height, boolean isStart) {
            this.val = val;
            this.height = height;
            this.isStart = isStart;
        }

        @Override
        public int compareTo(EndPoint that) {
            if (this.val != that.val) {
                return this.val - that.val;
            } else {
                // case 1: both are left endpoints -> h l
                if (this.isStart && that.isStart) {
                    return that.height - this.height;
                } else if (!this.isStart && !that.isStart) { // case 2: both are right endpoint -> l h
                    return this.height - that.height;
                } else { // case 3 & 4: one left, one right -> left -> 1st
                    return this.isStart ? -1 : 1;
                }
            }
        }
    }

    // S3: TreeMap
    // time = O(nlogn), space = O(n)
    public List<List<Integer>> getSkyline3(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();
        for (int[] b: buildings) {
            heights.add(new int[]{b[0], - b[2]});
            heights.add(new int[]{b[1], b[2]});
        }
        Collections.sort(heights, (a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]);
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        map.put(0,1);
        int prev = 0;

        for (int[] h: heights) {
            if (h[1] < 0) {
                map.put(-h[1], map.getOrDefault(-h[1], 0) + 1);
            } else {
                int cnt = map.get(h[1]);
                if (cnt == 1) {
                    map.remove(h[1]);
                } else {
                    map.put(h[1], cnt - 1);
                }
            }
            int cur = map.firstKey();
            if (prev != cur) {
                res.add(Arrays.asList(h[0], cur));
                prev = cur;
            }
        }
        return res;
    }

    // S4: Segment Tree
    private List<int[]> height; // {idx, h}
    public List<List<Integer>> getSkyline4(int[][] buildings) {
        height = new ArrayList<>();

        TreeSet<Integer> set = new TreeSet<>(); // 必须用TreeSet给下面离散化的点先排好序
        for (int[] building : buildings) {
            set.add(building[0]);
            set.add(building[1]);
        }
        // 离散化
        int id = 0;
        HashMap<Integer, Integer> pos2idx = new HashMap<>();
        HashMap<Integer, Integer> idx2pos = new HashMap<>();
        for (int x : set) { // 按坐标点顺序来离散化赋予标签id
            pos2idx.put(x, id);
            idx2pos.put(id, x);
            id++;
        }

        int n = pos2idx.size();
        SegTreeNode root = new SegTreeNode(0, n - 1);
        init(root, 0, n - 1);

        Arrays.sort(buildings, (o1, o2) -> o1[2] - o2[2]);

        for (int[] building : buildings) {
            updateRange(root, pos2idx.get(building[0]), pos2idx.get(building[1]) - 1, building[2]); // [ ) 约定左闭右开区间
        }
        // 看每一个点的高度
        dfs(root);

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < height.size(); i++) {
            int[] h = height.get(i); // {idx, h}
            res.add(Arrays.asList(idx2pos.get(h[0]), h[1]));
            while (i + 1 < height.size() && height.get(i + 1)[1] == height.get(i)[1]) i++; // 略过
        }
        return res;
    }

    private class SegTreeNode {
        private SegTreeNode left, right;
        int start, end;
        int info, tag;
        public SegTreeNode(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private void init(SegTreeNode node, int a, int b) {
        // base case -> single point
        if (a == b) {
            node.info = 0; // 单点值初始化为0
            return;
        }

        int mid = (a + b) / 2;
        if (node.left == null) {
            node.left = new SegTreeNode(a, mid);
            node.right = new SegTreeNode(mid + 1, b);
        }
        init(node.left, a, mid);
        init(node.right, mid + 1, b);
        // info
        node.info = 0;
    }

    private void updateRange(SegTreeNode node, int a, int b, int val) {
        if (b < node.start || a > node.end) return;
        if (node.start == node.end) { // single point -> leaf node
            node.info = Math.max(node.info, val);
            return;
        }
        if (a <= node.start && node.end <= b && val >= node.info) {
            node.info = val;
            node.tag = 1; // 表示从这个结点往后，每个叶子节点都要increase by val
            return;
        }

        // recursion
        pushdown(node); // erase lazy tag and propagate down
        node.info = Math.max(node.info, val);
        updateRange(node.left, a, b, val);
        updateRange(node.right, a, b, val);
    }

    private void pushdown(SegTreeNode node) {
        if (node.tag != 0) {
            node.left.info = node.info;
            node.right.info = node.info;
            node.left.tag = 1;
            node.right.tag = 1;
            node.tag = 0;
        }
    }

    private void dfs(SegTreeNode node) {
        if (node.start == node.end || node.tag == 1) {
            height.add(new int[]{node.start, node.info}); // 存放每个叶子节点的idx和h
            return;
        }
        dfs(node.left);
        dfs(node.right);
    }
}
/**
 * sweep line 扫描线算法
 * 所有点：起始点 -> 最高点; 终止点 -> 第二高点
 * updateRange(root, a, b) -> use lazyTag 非常高频率的updateRange,对最终点只query1次，有些信息先hold住
 * // queryRange(root, a, b)
 * querySingle() => dfs
 * 有range的更新，有range的单点查询 => 线段树
 * ref: LC370 range addition
 * node.info -> max
 * 对每个叶子节点，只关心高度
 * 对range而言，info取的是区间里的最大值
 * 什么时候标记lazyTag? => 我要给整体区间新的高度完全大于现在区间的最大高度，这个时候就可以用lazyTag
 * 不是这个情况，就不需要这么做，还是只能递归，不能对整个区间进行整体操作。
 * 离散化：点的编号才有意义，数值本身没有意义 => 开7个叶子节点即可 => pos -> idx
 * [3:5] -> 6
 * 3: 6 => [3,4)
 * 4: 6 => [4,5)
 * 5: ? => [3,5)
 */
