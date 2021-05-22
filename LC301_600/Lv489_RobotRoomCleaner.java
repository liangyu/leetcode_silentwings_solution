package LC301_600;
import java.util.*;
public class Lv489_RobotRoomCleaner {
    /**
     * Given a robot cleaner in a room modeled as a grid.
     *
     * Each cell in the grid can be empty or blocked.
     *
     * The robot cleaner with 4 given APIs can move forward, turn left or turn right. Each turn it made is 90 degrees.
     *
     * When it tries to move into a blocked cell, its bumper sensor detects the obstacle and it stays on the current cell.
     *
     * Design an algorithm to clean the entire room using only the 4 given APIs shown below.
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
     *
     * Input:
     * room = [
     *   [1,1,1,1,1,0,1,1],
     *   [1,1,1,1,1,0,1,1],
     *   [1,0,1,1,1,1,1,1],
     *   [0,0,0,1,0,0,0,0],
     *   [1,1,1,1,1,1,1,1]
     * ],
     * row = 1,
     * col = 3
     *
     * Notes:
     *
     * The input is only given to initialize the room and the robot's position internally. You must solve this problem
     * "blindfolded". In other words, you must control the robot using only the mentioned 4 APIs, without knowing the
     * room layout and the initial robot's position.
     * The robot's initial position will always be in an accessible cell.
     * The initial direction of the robot will be facing up.
     * All accessible cells are connected, which means the all cells marked as 1 will be accessible by the robot.
     * Assume all four edges of the grid are all surrounded by wall.
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
 * DFS(x, y, north) -> turn right, move => dfs(i0, j0, east)
 *                                      => turn 180, move, adjust dir => (x, y, south) => move
 *                                      => dfs(i1, j1, south)
 *                                      => ...
 *                                      => dfs(i2, j2, west)
 *                                      => ...
 *                                      => dfs(i3, j3, north)
 *                                      => return to (x, y, north) // backtracking
 */
