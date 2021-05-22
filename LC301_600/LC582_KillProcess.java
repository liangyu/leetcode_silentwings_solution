package LC301_600;
import java.util.*;
public class LC582_KillProcess {
    /**
     * Given n processes, each process has a unique PID (process id) and its PPID (parent process id).
     *
     * We use two list of integers to represent a list of processes, where the first list contains PID for each process
     * and the second list contains the corresponding PPID.
     *
     * Now given the two lists, and a PID representing a process you want to kill, return a list of PIDs of processes
     * that will be killed in the end. You should assume that when a process is killed, all its children processes will
     * be killed. No order is required for the final answer.
     *
     * Input:
     * pid =  [1, 3, 10, 5]
     * ppid = [3, 0, 5, 3]
     * kill = 5
     * Output: [5,10]
     *
     * @param pid
     * @param ppid
     * @param kill
     * @return
     */
    // time = O(n), space = O(n)
    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        List<Integer> res = new LinkedList<>();
        // corner case
        if (pid == null || pid.size() == 0 || ppid == null || ppid.size() == 0) return res;

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int size = pid.size();

        for (int i = 0; i < size; i++) { // O(n)
            int parent = ppid.get(i);
            map.putIfAbsent(parent, new ArrayList<>());
            map.get(parent).add(pid.get(i));
        }

        if (!map.containsKey(kill)) return Arrays.asList(kill);
        List<Integer> list = map.get(kill);
        res.add(kill);
        for (int next : list) {  // O(n) -> no node can be a child of two nodes
            dfs(map, next, res);
        }
        return res;
    }

    private void dfs(HashMap<Integer, List<Integer>> map, int cur, List<Integer> res) {
        res.add(cur);
        // base case
        if (!map.containsKey(cur)) {
            return;
        }

        for (int next : map.get(cur)) {
            dfs(map, next, res);
        }
    }
}
