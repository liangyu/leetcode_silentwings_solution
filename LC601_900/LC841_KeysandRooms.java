package LC601_900;
import java.util.*;
public class LC841_KeysandRooms {
    /**
     * There are N rooms and you start in room 0.  Each room has a distinct number in 0, 1, 2, ..., N-1, and each room
     * may have some keys to access the next room.
     *
     * Formally, each room i has a list of keys rooms[i], and each key rooms[i][j] is an integer in [0, 1, ..., N-1]
     * where N = rooms.length.  A key rooms[i][j] = v opens the room with number v.
     *
     * Initially, all the rooms start locked (except for room 0).
     *
     * You can walk back and forth between rooms freely.
     *
     * Return true if and only if you can enter every room.
     *
     * Input: [[1],[2],[3],[]]
     * Output: true
     *
     * Note:
     *
     * 1 <= rooms.length <= 1000
     * 0 <= rooms[i].length <= 1000
     * The number of keys in all rooms combined is at most 3000.
     *
     * @param rooms
     * @return
     */
    // time = O(m + n), space = O(n)
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        // corner case
        if (rooms == null || rooms.size() == 0) return true;

        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[rooms.size()];
        stack.push(0);
        visited[0] = true;

        while (!stack.isEmpty()) {
            int key = stack.pop();
            for (int next : rooms.get(key)) {
                if (!visited[next]) {
                    stack.push(next);
                    visited[next] = true;
                }
            }
        }

        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }
}
