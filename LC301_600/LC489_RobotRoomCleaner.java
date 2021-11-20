package LC301_600;
import java.util.*;
public class LC489_RobotRoomCleaner {
    /**
     * You are controlling a robot that is located somewhere in a room. The room is modeled as an m x n binary grid
     * where 0 represents a wall and 1 represents an empty slot.
     *
     * The robot starts at an unknown location in the root that is guaranteed to be empty, and you do not have access to
     * the grid, but you can move the robot using the given API Robot.
     *
     * You are tasked to use the robot to clean the entire room (i.e., clean every empty cell in the room). The robot
     * with the four given APIs can move forward, turn left, or turn right. Each turn is 90 degrees.
     *
     * When the robot tries to move into a wall cell, its bumper sensor detects the obstacle, and it stays on the
     * current cell.
     *
     * Design an algorithm to clean the entire room using the following APIs:
     *
     * interface Robot {
     *   // returns true if next cell is open and robot moves into the cell.
     *   // returns false if next cell is obstacle and robot stays on the current cell.
     *   boolean move();
     *
     *   // Robot will stay on the same cell after calling turnLeft/turnRight.
     *   // Each turn will be 90 degrees.
     *   void turnLeft();
     *   void turnRight();
     *
     *   // Clean the current cell.
     *   void clean();
     * }
     * Note that the initial direction of the robot will be facing up. You can assume all four edges of the grid are all
     * surrounded by a wall.
     *
     * Input: room = [[1,1,1,1,1,0,1,1],[1,1,1,1,1,0,1,1],[1,0,1,1,1,1,1,1],[0,0,0,1,0,0,0,0],[1,1,1,1,1,1,1,1]],
     * row = 1, col = 3
     * Output: Robot cleaned all rooms.
     *
     * Constraints:
     *
     * m == room.length
     * n == room[i].length
     * 1 <= m <= 100
     * 1 <= n <= 200
     * room[i][j] is either 0 or 1.
     * 0 <= row < m
     * 0 <= col < n
     * room[row][col] == 1
     * All the empty cells can be visited from the starting position.
     * @param robot
     */
    // time = O(n - m), space = O(n - m)
    // n : number of cells, m: number of obstacles
    // We visit each non-obstacle cell once and only once.At each visit, we will check 4 directions around the cell.
    // We employed a hashtable to keep track of whether a non-obstacle cell is visited or not.
    private static final int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public void cleanRoom(Robot robot) {
        HashSet<String> visited = new HashSet<>(); // x + "#" + y
        visited.add(0 + "#" + 0); // encoding
        dfs(robot, 0, 0, 0, visited); // {x, y, dir}
    }

    private void dfs(Robot robot, int x, int y, int curDir, HashSet<String> visited) {
        robot.clean();
        for (int k = 1; k <= 4; k++) {
            robot.turnRight();
            int nextDir = (curDir + k) % 4; // avoid going out of boundary
            int i = x + dir[nextDir][0];
            int j = y + dir[nextDir][1];

            String code = i + "#" + j;

            // {i, j, nextDir} 回溯的过程必须自己手动实现，模拟退栈的过程，第一人称视角
            if (!visited.contains(code) && robot.move()) {
                visited.add(code);
                dfs(robot, i, j, nextDir, visited);
                robot.turnRight();
                robot.turnRight();
                robot.move();
                robot.turnRight();
                robot.turnRight();
            }
            // {x, y, nextDir}
        }
    }

    interface Robot {
        // Returns true if the cell in front is open and robot moves into the cell.
        // Returns false if the cell in front is blocked and robot stays in the current cell.
        public boolean move();
        // Robot will stay in the same cell after calling turnLeft/turnRight.
        // Each turn will be 90 degrees.
        public void turnLeft();
        public void turnRight();

        // Clean the current cell
        public void clean();
    }
}
/**
 * 本题看上去是一个常规的DFS，但是难点在于机器人的运动是第一视角，DFS的回溯过程必须靠“手工”实现。
 * 假设当前我们访问了dfs(x,y,north)，表示当前位置是(x,y)，朝向是北。
 * 如果我们想递归访问它的东边的节点(i,j)，你需要依次做如下的事情：
 * 1. 向右转，使得朝向东边，
 * 2. 前进
 * 3. 递归调用dfs(i,j,east)
 * 4. 180度转弯
 * 5. 前进
 * 6. 180度转弯
 * =====
 * 8. 向右转，使得朝向南边
 * 9. 前进
 * 10. 递归调用dfs(i,j,south)
 * 11. ...
 * 可见我们完成一次dfs之后，必须手工完成掉头、退回、调整方向，才能进行另一次平行的dfs尝试。
 */
