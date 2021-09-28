package LC901_1200;
import java.util.*;
public class LC1024_VideoStitching {
    /**
     * You are given a series of video clips from a sporting event that lasted time seconds. These video clips can be
     * overlapping with each other and have varying lengths.
     *
     * Each video clip is described by an array clips where clips[i] = [starti, endi] indicates that the ith clip
     * started at starti and ended at endi.
     *
     * We can cut these clips into segments freely.
     *
     * For example, a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7].
     * Return the minimum number of clips needed so that we can cut the clips into segments that cover the entire
     * sporting event [0, time]. If the task is impossible, return -1.
     *
     * Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], time = 10
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= clips.length <= 100
     * 0 <= starti <= endi <= 100
     * 1 <= time <= 100
     * @param clips
     * @param time
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int videoStitching(int[][] clips, int time) {
        Arrays.sort(clips, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]); // 左端点相等时，长区间优先

        if (clips[0][0] != 0) return -1;
        int right = clips[0][1], idx = 0;

        int n = clips.length, count = 1;
        if (clips[0][1] >= time) return count;

        while (idx < n) {
            int farReach = right;
            while (idx < n && clips[idx][0] <= right) {
                farReach = Math.max(farReach, clips[idx][1]);
                idx++;
            }
            if (farReach == right) return -1;

            count++; // 选中一个区间得以拓展farReach,所以count++
            if (farReach >= time) return count;
            right = farReach;
        }
        return -1;
    }
}
/**
 * 按照前端点排序
 * 找起始点早的区间
 * 如果两区间都是从0开始，我们就取长度较长的区间，因为不选白不选，多覆盖一些，可能可以节省一个其他区间的选择。
 * 基本思想：按照区间起始点的顺序排序，如果有2个区间左端点相等，就取区间大的。
 * 为了保证从这开始后面也被覆盖掉，我会找其他interval左端点稍微靠前一些
 */
