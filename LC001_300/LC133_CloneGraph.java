package LC001_300;
import java.util.*;
public class LC133_CloneGraph {
    /**
     * Given a reference of a node in a connected undirected graph.
     *
     * Return a deep copy (clone) of the graph.
     *
     * Each node in the graph contains a val (int) and a list (List[Node]) of its neighbors.
     *
     * Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
     * Output: [[2,4],[1,3],[2,4],[1,3]]
     *
     * Constraints:
     *
     * 1 <= Node.val <= 100
     * Node.val is unique for each node.
     * Number of Nodes will not exceed 100.
     * There is no repeated edges and no self-loops in the graph.
     * The Graph is connected and all nodes can be visited starting from the given node.
     * @param node
     * @return
     */
    // S1: DFS
    // time = O(V + E) = O(n), space = O(V) = O(n)
    public Node cloneGraph(Node node) {
        // corner case
        if (node == null) return node;

        HashMap<Node, Node> map = new HashMap<>();
        return dfs(node, map);
    }

    private Node dfs(Node cur, HashMap<Node, Node> map) {
        // base case - fail
        if (map.containsKey(cur)) return map.get(cur);

        map.put(cur, new Node(cur.val));
        for (Node next : cur.neighbors) {
            map.get(cur).neighbors.add(dfs(next, map));
        }
        return map.get(cur);
    }

    // S2: BFS
    // time = O(V + E) = O(n), space = O(V) = O(n)
    public Node cloneGraph2(Node node) {
        // corner case
        if (node == null) return node;

        HashMap<Node, Node> map = new HashMap<>();
        map.put(node, new Node(node.val));

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node next : cur.neighbors) {
                if (!map.containsKey(next)) {
                    queue.offer(next);
                    map.put(next, new Node(next.val));
                }
                map.get(cur).neighbors.add(map.get(next));
            }
        }
        return map.get(node);
    }


    class Node {
        public int val;
        public List<Node> neighbors;
        public Node(int val) {
            this.val = val;
        }
    }
}
/**
 * 对于图的copy，必用Map建立原图和新图之间节点的对应。
 * DFS时，递归函数作用于原图root所有的邻接节点adjacent。
 * 如果邻接节点adjacent已经存在于Map的映射里（说明已经访问过），则只需建立root和adjacent各自对应的两个节点在新图里的邻里关系。
 * 否则，邻接节点adjacent不存在于Map的映射里，说明它未曾被考虑过，则需要先在新图里建立一个adjacent的对应节点，再建立邻里关系。
 * BFS一般都是用队列。对于队首的原图节点root，查看其所有邻接节点adjacent。
 * 如果adjacent在Map中没有建立其映射关系，则将adjacent入列，并且新建adjacent在新图中的对应节点，并建立在新图中的邻里关系。
 * 反之，邻接节点adjacent存在于Map的映射里，说明它已经被考虑过，则只需要建立root和adjacent对应节点的邻里关系，不需要入列任何节点。
 */
