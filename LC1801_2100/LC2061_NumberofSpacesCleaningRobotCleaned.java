package LC1801_2100;
import java.util.*;
public class LC2061_NumberofSpacesCleaningRobotCleaned {
    /**
     * A room is represented by a 0-indexed 2D binary matrix room where a 0 represents an empty space and a 1 represents
     * a space with an object. The top left corner of the room will be empty in all test cases.
     *
     * A cleaning robot starts at the top left corner of the room and is facing right. The robot will continue heading
     * straight until it reaches the edge of the room or it hits an object, after which it will turn 90 degrees
     * clockwise and repeat this process. The starting space and all spaces that the robot visits are cleaned by it.
     *
     * Return the number of clean spaces in the room if the robot runs indefinetely.
     *
     * Input: room = [[0,0,0],[1,1,0],[0,0,0]]
     * Output: 7
     *
     * Constraints:
     *
     * m == room.length
     * n == room[r].length
     * 1 <= m, n <= 300
     * room[r][c] is either 0 or 1.
     * room[0][0] == 0
     * @param room
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public int numberOfCleanRooms(int[][] room) {
        int m = room.length, n = room[0].length;
        HashMap<Integer, Integer> map = new HashMap<>();
        int x = 0, y = 0, d = 0, count = 1;
        map.put(0, 0);

        while (true) {
            for (int k = d; k < d + 5; k++) {
                int i = x + dir[k % 4][0];
                int j = y + dir[k % 4][1];
                int idx = i * n + j;
                if (map.containsKey(idx) && map.get(idx) == k % 4) return count;
                if (i < 0 || i >= m || j < 0 || j >= n || room[i][j] == 1) continue;
                x = i;
                y = j;
                d = k % 4;
                if (!map.containsKey(x * n + j)) count++;
                map.put(x * n + y, d);
                break;
            }
            if (x == 0 && y == 0 && d == 0) return count;
        }
    }
}
