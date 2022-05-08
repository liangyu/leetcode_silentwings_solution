package LC601_900;
import java.util.*;
public class LC631_DesignExcelSumFormula {
    /**
     * Design the basic function of Excel and implement the function of the sum formula.
     *
     * Implement the Excel class:
     *
     * Excel(int height, char width) Initializes the object with the height and the width of the sheet. The sheet is an
     * integer matrix mat of size height x width with the row index in the range [1, height] and the column index in the
     * range ['A', width]. All the values should be zero initially.
     * void set(int row, char column, int val) Changes the value at mat[row][column] to be val.
     * int get(int row, char column) Returns the value at mat[row][column].
     * int sum(int row, char column, List<String> numbers) Sets the value at mat[row][column] to be the sum of cells
     * represented by numbers and returns the value at mat[row][column]. This sum formula should exist until this cell
     * is overlapped by another value or another sum formula. numbers[i] could be on the format:
     * "ColRow" that represents a single cell.
     * For example, "F7" represents the cell mat[7]['F'].
     * "ColRow1:ColRow2" that represents a range of cells. The range will always be a rectangle where "ColRow1"
     * represent the position of the top-left cell, and "ColRow2" represents the position of the bottom-right cell.
     * For example, "B3:F7" represents the cells mat[i][j] for 3 <= i <= 7 and 'B' <= j <= 'F'.
     * Note: You could assume that there will not be any circular sum reference.
     *
     * For example, mat[1]['A'] == sum(1, "B") and mat[1]['B'] == sum(1, "A").
     *
     * Input
     * ["Excel", "set", "sum", "set", "get"]
     * [[3, "C"], [1, "A", 2], [3, "C", ["A1", "A1:B2"]], [2, "B", 2], [3, "C"]]
     * Output
     * [null, null, 4, null, 6]
     *
     * Constraints:
     *
     * 1 <= height <= 26
     * 'A' <= width <= 'Z'
     * 1 <= row <= height
     * 'A' <= column <= width
     * -100 <= val <= 100
     * 1 <= numbers.length <= 5
     * numbers[i] has the format "ColRow" or "ColRow1:ColRow2".
     * At most 100 calls will be made to set, get, and sum.
     * @param height
     * @param width
     */
    int[][] arr;
    List<String>[][] exp;
    public LC631_DesignExcelSumFormula(int height, char width) {
        int h = height, w = width - 'A';
        arr = new int[h + 1][w + 1];
        exp = new List[h + 1][w + 1];
        for (int i = 0; i <= h; i++) {
            for (int j = 0; j <= w; j++) {
                exp[i][j] = new ArrayList<>();
            }
        }
    }

    // time = O((r * c)^2), space = O(h * w)
    public void set(int row, char column, int val) {
        int r = row, c = column - 'A';
        arr[r][c] = val;
        exp[r][c] = new ArrayList<>();
    }

    // time = O((r * c)^2 + 2 * r * c * l), space = O(h * w)
    public int get(int row, char column) {
        int r = row, c = column - 'A';
        if (exp[r][c].size() == 0) return arr[r][c];

        int res = 0;
        for (String s : exp[r][c]) {
            int pos = s.indexOf(":");
            if (pos == -1) {
                int[] x = parse(s);
                res += get(x[0], (char)(x[1] + 'A'));
            } else {
                int[] x1 = parse(s.substring(0, pos));
                int[] x2 = parse(s.substring(pos + 1));

                for (int i = x1[0]; i <= x2[0]; i++) {
                    for (int j = x1[1]; j <= x2[1]; j++) {
                        res += get(i, (char)(j + 'A'));
                    }
                }
            }
        }
        return res;
    }

    // time = O(1), space = O(h * w)
    public int sum(int row, char column, String[] numbers) {
        int r = row, c = column - 'A';
        exp[r][c] = Arrays.asList(numbers);
        return get(row, column);
    }

    private int[] parse(String s) {
        int col = s.charAt(0) - 'A';
        int row = Integer.parseInt(s.substring(1));
        return new int[]{row, col};
    }
}
/**
 * Set：对Val的对应位置赋值。如果该位置已经有Exp，则需要将Exp清空。
 * Get：如果该位置没有Exp，则从Val里读取。否则就需要解析表达式，对每个位置上递归调用Get，然后相加。
 * Sum：对Exp的对应位置赋值，然后调用Get。
 */