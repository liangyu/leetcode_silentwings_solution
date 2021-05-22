package LC1801_2100;
import java.util.*;
public class LC1812_DetermineColorofaChessboardSquare {
    /**
     * You are given coordinates, a string that represents the coordinates of a square of the chessboard.
     *
     * Return true if the square is white, and false if the square is black.
     *
     * The coordinate will always represent a valid chessboard square. The coordinate will always have the letter first,
     * and the number second.
     *
     * Input: coordinates = "a1"
     * Output: false
     *
     * Constraints:
     *
     * coordinates.length == 2
     * 'a' <= coordinates[0] <= 'h'
     * '1' <= coordinates[1] <= '8'
     * @param coordinates
     * @return
     */
    // time = O(1), space = O(1)
    public boolean squareIsWhite(String coordinates) {
        return coordinates.charAt(0) % 2 != coordinates.charAt(1) % 2;
    }
}
