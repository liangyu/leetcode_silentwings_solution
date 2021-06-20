package LC901_1200;
import java.util.*;
public class LC1041_RobotBoundedInCircle {
    /**
     * On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three
     * instructions:
     *
     * "G": go straight 1 unit;
     * "L": turn 90 degrees to the left;
     * "R": turn 90 degrees to the right.
     * The robot performs the instructions given in order, and repeats them forever.
     *
     * Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.
     *
     * Input: instructions = "GGLLGG"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= instructions.length <= 100
     * instructions[i] is 'G', 'L' or, 'R'.
     * @param instructions
     * @return
     */
    // S1: Math
    // time = O(n), space = O(1)
    public boolean isRobotBounded(String instructions) {
        // corner case
        if (instructions == null || instructions.length() == 0) return false;

        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int x = 0, y = 0, idx = 0;

        for (char c : instructions.toCharArray()) {
            if (c == 'L') idx = (idx + 3) % 4;
            else if (c == 'R') idx = (idx + 1) % 4;
            else {
                x += dir[idx][0];
                y += dir[idx][1];
            }
        }
        return (x == 0 && y == 0) || idx != 0;
    }

    // S2: brute-force
    // time = O(n), space = O(1)
    public boolean isRobotBounded2(String instructions) {
        // corner case
        if (instructions == null || instructions.length() == 0) return false;

        char dir = 'N';
        int x = 0, y = 0, k = 4;
        HashSet<Pair> set = new HashSet<>();
        set.add(new Pair(0, 0));
        while (k-- > 0) {
            for (int i = 0; i < instructions.length(); i++) {
                char c = instructions.charAt(i);
                if (c == 'G') {
                    if (dir == 'N') y++;
                    if (dir == 'W') x--;
                    if (dir == 'S') y--;
                    if (dir == 'E') x++;
                } else if (c == 'L') {
                    if (dir == 'N') {dir = 'W'; continue;}
                    if (dir == 'W') {dir = 'S'; continue;}
                    if (dir == 'S') {dir = 'E'; continue;}
                    if (dir == 'E') {dir = 'N'; continue;}
                } else {
                    if (dir == 'N') {dir = 'E'; continue;}
                    if (dir == 'W') {dir = 'N'; continue;}
                    if (dir == 'S') {dir = 'W'; continue;}
                    if (dir == 'E') {dir = 'S'; continue;}
                }
            }
            if (!set.add(new Pair(x, y))) return true;
        }
        return false;
    }

    private class Pair {
        private int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x * 31 + y;
        }

        public boolean equals(Object o) {
            if (o == null) return false;
            if (o instanceof Pair) {
                Pair that = (Pair) o;
                return this.x == that.x && this.y == that.y;
            } else return false;
        }
    }
}
