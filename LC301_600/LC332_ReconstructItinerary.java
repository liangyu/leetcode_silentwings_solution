package LC301_600;
import java.util.*;
public class LC332_ReconstructItinerary {
    /**
     * You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival
     * airports of one flight. Reconstruct the itinerary in order and return it.
     *
     * All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there
     * are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as
     * a single string.
     *
     * For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
     * You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
     *
     * Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
     * Output: ["JFK","MUC","LHR","SFO","SJC"]
     *
     * Constraints:
     *
     * 1 <= tickets.length <= 300
     * tickets[i].length == 2
     * fromi.length == 3
     * toi.length == 3
     * fromi and toi consist of uppercase English letters.
     * fromi != toi
     * @param tickets
     * @return
     */
    // S1
    // time = O(nlogn), space = O(n)
    HashMap<String, List<String>> map;
    public List<String> findItinerary(List<List<String>> tickets) {
        map = new HashMap<>();
        Collections.sort(tickets, (o1, o2) -> o2.get(1).compareTo(o1.get(1)));

        for (List<String> ticket : tickets) {
            String from = ticket.get(0), to = ticket.get(1);
            map.putIfAbsent(from, new ArrayList<>());
            map.get(from).add(to);
        }

        List<String> path = new ArrayList<>();
        dfs("JFK", path);
        Collections.reverse(path); // reverse: start + path2 + path1
        return path;
    }

    private void dfs(String start, List<String> path) {
        // path: path 1 + path2 + start 两步走：第一步走到底，第二步走个环
        while (map.getOrDefault(start, new ArrayList<>()).size() > 0) {
            String next = map.get(start).get(map.get(start).size() - 1);
            map.get(start).remove(map.get(start).size() - 1);
            dfs(next, path);
        }
        path.add(start);
    }

    // S2: Heap
    // time = O(mlogm), space = O(m)  m: number of edges
    public List<String> findItinerary2(List<List<String>> tickets) {
        List<String> res = new ArrayList<>();
        HashMap<String, PriorityQueue<String>> map = new HashMap<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            map.putIfAbsent(from, new PriorityQueue<>());
            map.get(from).add(to);
        }
        dfs(map, "JFK", res);
        return res;
    }

    private void dfs(HashMap<String, PriorityQueue<String>> map, String cur, List<String> res) {
        PriorityQueue<String> pq = map.getOrDefault(cur, new PriorityQueue<>());
        while (!pq.isEmpty()) {
            String next = pq.poll();
            dfs(map, next, res);
        }
        res.add(0, cur);
    }
}
/**
 * 保证欧拉路径的存在
 * 无脑dfs效率比较低，一条路可能会走多遍
 * 这题本质就是一个欧拉一笔画的问题。
 * 欧拉路径：从一个点出发，到达另外一个点，所有的边都经过且只经过1次。
 * 欧拉回路：欧拉路径中，终点能回到起点。
 * 无向图：
 * (a) 如果只有两个点的度是奇数，其他的点的度都是偶数，则存在从一个奇数度点到另一个奇数度点的欧拉路径（不是回路）。
 * (b) 如果所有的点的度都是偶数，那么就是欧拉回路。
 * 有向图：
 * (a) 如果最多有一个点出度大于入度by1，且最多有一个点入度大于出度by1，那么就有一条从前者（如果没有则可以任意）到后者（如果没有则可以任意）
 * 的欧拉路径。
 * (b) 如果所有的点的入度等于出度，那么就存在欧拉回路。
 *
 * 本题保证了是这张图构成了欧拉路径，于是有一个非常好的性质：
 * 每条边都是必须遍历的，而且只需要遍历一次，因为它肯定是最终欧拉路径的一部分。
 * 所以对边的遍历，我们都不该浪费（某种意义上可以存着再利用）。
 * 探索欧拉路径的过程中，不像无脑DFS那样会有被“废弃”的支路。
 * 因此，构造欧拉路径的时间复杂度只需要O(E)。
 *
 * 具体构造欧拉路径的算法:
 * 假设我们第一次到达B点，开始往后遍历，保证每条边只走一次。每走一步就删除这条边，不会走重复路线。
 * 接下来只有两种可能：选择某条支路走遍了所有后续节点并走到了终点，完美地构建了B之后的所有欧拉路径。
 * 或者选择某条支路走到了终点，但没有遍历完所有后续节点；我们只好回溯走另外一条支路，一番探索之后最终返回B点（此时B点没有任何未走的出度）停止。
 *        -> D -> E
 * A -> B <-> F
 * 最理想的情况是一次遍历走完所有想走的点 B->F->B->D->E.
 * 但是我们在B的支路选择上不可能总是这么幸运，可能会走这样一条路 B->D->E，这样走到了尽头。
 * 但是B还有另一条支路->F没有走，如果我们尝试继续走那一条的话，就是 B->F->B，然后停止，因为此时B没有任何未走过的边了。
 * 那我们构造欧拉路径的思想是：B + path2 + path1，其中path1是从B点出发，选择任意支路并能够顺利走到终点的欧拉路径。
 * path2是在path1走完之后，再从B点出发，最终走回B点的路径。
 * 注意，如果足够幸运，path1走遍了B后面的所有边，那么path2就不存在了。
 * 因为我们要最小化字典序，所以我们每次的分叉总会优先选择字典序较小的一支。
 * 如果这一支是类似上例中的"->D->E"这样直通到底的path1，那我们也无能为力，D注定是无法往前提的；
 * 否则的话我们就会先进入path2，从而更小化了整个欧拉路径的字典序。
 * path1, path2, B
 * <==============
 *
 * 欧拉路径：
 * 1. 起点：出度 = 入度 + 1
 * 2. 终点：入度 = 出度 + 1
 * 3. 其他点： 入度 = 出度
 * 做法：从起点开始dfs一遍，回溯的路径就是欧拉回路的逆序
 */
