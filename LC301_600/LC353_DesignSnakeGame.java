package LC301_600;
import java.util.*;
public class LC353_DesignSnakeGame {
    /**
     * Design a Snake game that is played on a device with screen size height x width. Play the game online if you are
     * not familiar with the game.
     *
     * The snake is initially positioned at the top left corner (0, 0) with a length of 1 unit.
     *
     * You are given an array food where food[i] = (ri, ci) is the row and column position of a piece of food that the
     * snake can eat. When a snake eats a piece of food, its length and the game's score both increase by 1.
     *
     * Each piece of food appears one by one on the screen, meaning the second piece of food will not appear until the
     * snake eats the first piece of food.
     *
     * When a piece of food appears on the screen, it is guaranteed that it will not appear on a block occupied by the
     * snake.
     *
     * The game is over if the snake goes out of bounds (hits a wall) or if its head occupies a space that its body
     * occupies after moving (i.e. a snake of length 4 cannot run into itself).
     *
     * Implement the SnakeGame class:
     *
     * SnakeGame(int width, int height, int[][] food) Initializes the object with a screen of size height x width and
     * the positions of the food.
     * int move(String direction) Returns the score of the game after applying one direction move by the snake. If the
     * game is over, return -1.
     *
     * Input
     * ["SnakeGame", "move", "move", "move", "move", "move", "move"]
     * [[3, 2, [[1, 2], [0, 1]]], ["R"], ["D"], ["R"], ["U"], ["L"], ["U"]]
     * Output
     * [null, 0, 0, 1, 1, 2, -1]
     *
     * Constraints:
     *
     * 1 <= width, height <= 10^4
     * 1 <= food.length <= 50
     * food[i].length == 2
     * 0 <= ri < height
     * 0 <= ci < width
     * direction.length == 1
     * direction is 'U', 'D', 'L', or 'R'.
     * At most 104 calls will be made to move.
     * @param width
     * @param height
     * @param food
     */
    // time = O(1), space = O(n)
    int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    String dir = "URDL";
    int m, n, x, y, idx, step, len, score;
    int[][] food;
    HashMap<Integer, List<Integer>> map;
    public LC353_DesignSnakeGame(int width, int height, int[][] food) {
        this.m = height;
        this.n = width;
        this.x = 0;
        this.y = 0;
        this.idx = 0;
        this.step = 0;
        this.len = 1;
        this.score = 0;
        this.food = food;
        this.map = new HashMap<>();
        map.putIfAbsent(0, new ArrayList<>());
        map.get(0).add(0);
    }

    public int move(String direction) {
        int k = dir.indexOf(direction);
        x += directions[k][0];
        y += directions[k][1];
        int val = x * n + y;
        step++;

        if (x < 0 || x >= m || y < 0 || y >= n) return -1;
        if (map.containsKey(val)) {
            List<Integer> list = map.get(val);
            if (step - list.get(list.size() - 1) + 1 <= len) return -1;
        }
        if (idx < food.length && x == food[idx][0] && y == food[idx][1]) {
            score++;
            len++;
            idx++;
        }
        map.putIfAbsent(val, new ArrayList<>());
        map.get(val).add(step);
        return score;
    }
}
