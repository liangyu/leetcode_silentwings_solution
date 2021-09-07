package LC301_600;

public class LC302_SmallestRectangleEnclosingBlackPixels {
    /**
     * You are given an image that is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel.
     *
     * The black pixels are connected (i.e., there is only one black region). Pixels are connected horizontally and
     * vertically.
     *
     * Given two integers x and y that represent the location of one of the black pixels, return the area of the
     * smallest (axis-aligned) rectangle that encloses all black pixels.
     *
     * Input: image = [["0","0","1","0"],["0","1","1","0"],["0","1","0","0"]], x = 0, y = 2
     * Output: 6
     *
     * Constraints:
     *
     * m == image.length
     * n == image[i].length
     * 1 <= m, n <= 100
     * image[i][j] is either '0' or '1'.
     * 1 <= x < m
     * 1 <= y < n
     * image[x][y] == '1'.
     * The black pixels in the image only form one component.
     * @param image
     * @param x
     * @param y
     * @return
     */
    // time = O(nlogm + mlogn), space = O(1)
    public int minArea(char[][] image, int x, int y) {
        // corner case
        if (image == null || image.length == 0 || image[0] == null || image[0].length == 0) return 0;

        int m = image.length, n = image[0].length;

        int up = upRegion(image, 0, x, 0, n - 1);
        int down = downRegion(image, x, m - 1, 0, n - 1);
        int left = leftRegion(image, 0, m - 1, 0, y);
        int right = rightRegion(image, 0, m - 1, y, n - 1);

        return (down - up + 1) * (right - left + 1);
    }

    private int upRegion(char[][] image, int xl, int xr, int yl, int yr) {
        int left = xl, right = xr;
        while (left < right) {
            int mid = left + (right - left) / 2;
            boolean flag = false;
            for (int i = yl; i <= yr; i++) {
                if (image[mid][i] == '1') {
                    flag = true;
                    break;
                }
            }
            if (!flag) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private int downRegion(char[][] image, int xl, int xr, int yl, int yr) {
        int left = xl, right = xr;
        while (left < right) {
            int mid = right - (right - left) / 2;
            boolean flag = false;
            for (int i = yl; i <= yr; i++) {
                if (image[mid][i] == '1') {
                    flag = true;
                    break;
                }
            }
            if (!flag) right = mid - 1;
            else left = mid;
        }
        return left;
    }

    private int leftRegion(char[][] image, int xl, int xr, int yl, int yr) {
        int left = yl, right = yr;
        while (left < right) {
            int mid = left + (right - left) / 2;
            boolean flag = false;
            for (int i = xl; i <= xr; i++) {
                if (image[i][mid] == '1') {
                    flag = true;
                    break;
                }
            }
            if (!flag) left = mid + 1;
            else right = mid;
        }
        return left;
    }

    private int rightRegion(char[][] image, int xl, int xr, int yl, int yr) {
        int left = yl, right = yr;
        while (left < right) {
            int mid = right - (right - left) / 2;
            boolean flag = false;
            for (int i = xl; i <= xr; i++) {
                if (image[i][mid] == '1') {
                    flag = true;
                    break;
                }
            }
            if (!flag) right = mid - 1;
            else left = mid;
        }
        return left;
    }
}
