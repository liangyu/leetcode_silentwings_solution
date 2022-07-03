package LC2101_2400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class LC2271_MaximumWhiteTilesCoveredbyaCarpet {
    /**
     * You are given a 2D integer array tiles where tiles[i] = [li, ri] represents that every tile j in the range
     * li <= j <= ri is colored white.
     *
     * You are also given an integer carpetLen, the length of a single carpet that can be placed anywhere.
     *
     * Return the maximum number of white tiles that can be covered by the carpet.
     *
     * Input: tiles = [[1,5],[10,11],[12,18],[20,25],[30,32]], carpetLen = 10
     * Output: 9
     *
     * Input: tiles = [[10,11],[1,1]], carpetLen = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= tiles.length <= 5 * 10^4
     * tiles[i].length == 2
     * 1 <= li <= ri <= 10^9
     * 1 <= carpetLen <= 10^9
     * The tiles are non-overlapping.
     * @param tiles
     * @param carpetLen
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int maximumWhiteTiles(int[][] tiles, int carpetLen) {
        Arrays.sort(tiles, (o1, o2) -> o1[0] - o2[0]);
        int n = tiles.length;
        int[] presum = new int[n];
        for (int i = 0; i < n; i++) presum[i] = (i == 0 ? 0 : presum[i - 1]) + (tiles[i][1] - tiles[i][0] + 1);

        int j = 0, res = 0;
        for (int i = 0; i < n; i++) {
            while (j < n && tiles[i][0] + carpetLen - 1 >= tiles[j][1]) j++;
            int len = presum[j - 1] - (i == 0 ? 0 : presum[i - 1]);
            // 注意：这里有可能会搞出负数，所以要和0比较，最小只能取0！！！
            if (j < n) len += Math.max(0, tiles[i][0] + carpetLen - 1 - tiles[j][0] + 1);
            res = Math.max(res, len);
        }
        return res;
    }
}
/**
 * 由于覆盖多段区间时,如果毛毯左边落在区间中间,右移一格的毛毯,左侧也会损失一格,不会使结果变得更好,而左移要么增加一格,要么不变,
 * 不会使得结果变得更差,所以每次都将毛毯放在区间左侧开头
 * 所以本题我们只要逐一考察毯子的左边界应该对齐哪个tile的左边界即可
 * 在这个过程中，毯子的右边界的位置自然也是单调递增的。所以本题就是维护一个双指针的滑窗。
 * 假设毯子的左边界对应的是tiles[i][0]，那么我们向右移动指针j来定位毯子右边界解出的是哪个tile，
 * 直至tiles[i][0]+carpetLen-1 < tiles[j][1]。
 * tiles[i][0]+carpetLen-1 - tiles[j][0] + 1.
 */