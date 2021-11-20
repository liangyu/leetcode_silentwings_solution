package LC1801_2100;

public class LC2069_WalkingRobotSimulationII {
    /**
     * A width x height grid is on an XY-plane with the bottom-left cell at (0, 0) and the top-right cell at
     * (width - 1, height - 1). The grid is aligned with the four cardinal directions
     * ("North", "East", "South", and "West"). A robot is initially at cell (0, 0) facing direction "East".
     *
     * The robot can be instructed to move for a specific number of steps. For each step, it does the following.
     *
     * Attempts to move forward one cell in the direction it is facing.
     * If the cell the robot is moving to is out of bounds, the robot instead turns 90 degrees counterclockwise and
     * retries the step.
     * After the robot finishes moving the number of steps required, it stops and awaits the next instruction.
     *
     * Implement the Robot class:
     *
     * Robot(int width, int height) Initializes the width x height grid with the robot at (0, 0) facing "East".
     * void move(int num) Instructs the robot to move forward num steps.
     * int[] getPos() Returns the current cell the robot is at, as an array of length 2, [x, y].
     * String getDir() Returns the current direction of the robot, "North", "East", "South", or "West".
     *
     * Input
     * ["Robot", "move", "move", "getPos", "getDir", "move", "move", "move", "getPos", "getDir"]
     * [[6, 3], [2], [2], [], [], [2], [1], [4], [], []]
     * Output
     * [null, null, null, [4, 0], "East", null, null, null, [1, 2], "West"]
     *
     * Constraints:
     *
     * 2 <= width, height <= 100
     * 1 <= num <= 10^5
     * At most 10^4 calls in total will be made to move, getPos, and getDir.
     */
    // S1: Simulation
    // time = O(m + n), space = O(1)
    private int x, y, d;
    int width, height, total;
    private int[][] directions = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public Robot(int width, int height) {
        this.width = width;
        this.height = height;
        x = 0;
        y = 0;
        d = 0;
        total = (width + height) * 2 - 4;
    }

    public void move(int num) {
        while (num > 0) {
            int remain = 0;
            if (d == 0) remain = width - 1 - x;
            else if (d == 1) remain = height - 1 - y;
            else if (d == 2) remain = x - 0;
            else if (d == 3) remain = y - 0;

            if (remain >= num) { // not going to hit wall
                x += directions[d][0] * num;
                y += directions[d][1] * num;
                num = 0;
            } else {
                x += directions[d][0] * remain;
                y += directions[d][1] * remain;
                num -= remain;
                d = (d + 1) % 4;

                num %= total; // 把周期去掉，绕圈的直接跳过
                if (num == 0 && atCorner(x, y)) d = (d - 1 + 4) % 4; // 方向在四个角套圈后会变化
            }
        }
    }

    public int[] getPos() {
        return new int[]{x, y};
    }

    public String getDir() {
        if (d == 0) return "East";
        else if (d == 1) return "North";
        else if (d == 2) return "West";
        else return "South";
    }

    private boolean atCorner(int x, int y) {
        if (x == 0 && y == 0) return true;
        if (x == 0 && y == height - 1) return true;
        if (x == width - 1 && y == 0) return true;
        if (x == width - 1 && y == height - 1) return true;
        return false;
    }

    // S2: Simulation
    // time = O(m + n), space = O(1)
//    private int[][] directions = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
//    private int k, m, n, x, y, t;
//    public Robot(int width, int height) {
//        k = 0;
//        this.m = width;
//        this.n = height;
//        this.x = 0;
//        this.y = 0;
//        this.t = (m + n) * 2 - 4;
//    }
//
//    public void move(int num) {
//        int sum = num % t;
//        if (sum == 0 && x == 0 && y == 0) k = 3;
//        while (sum > 0) {
//            boolean flag = false;
//            if (k == 0 || k == 2) {
//                int diff = k == 0 ? m - 1 - x : x;
//                if (diff <= sum) {
//                    x += directions[k][0] * diff;
//                    sum -= diff;
//                    if (sum > 0 || diff == 0) flag = true;
//                } else {
//                    x += directions[k][0] * sum;
//                    sum = 0;
//                }
//            }
//            if (k == 1 || k == 3) {
//                int diff = k == 1 ? n - 1 - y : y;
//                if (diff <= sum) {
//                    y += directions[k][1] * diff;
//                    sum -= diff;
//                    if (sum > 0 || diff == 0) flag = true;
//                } else {
//                    y += directions[k][1] * sum;
//                    sum = 0;
//                }
//            }
//            if (flag) k = (k + 1) % 4;
//        }
//    }
//
//    public int[] getPos() {
//        return new int[]{x, y};
//    }
//
//    public String getDir() {
//        if (k == 0) return "East";
//        if (k == 1) return "North";
//        if (k == 2) return "West";
//        return "South"; // k == 3
//    }
}

/**
 * Your Robot object will be instantiated and called as such:
 * Robot obj = new Robot(width, height);
 * obj.move(num);
 * int[] param_2 = obj.getPos();
 * String param_3 = obj.getDir();
 */