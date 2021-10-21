package LC1201_1500;
import java.util.*;
public class LC1311_GetWatchedVideosbyYourFriends {
    /**
     * There are n people, each person has a unique id between 0 and n-1. Given the arrays watchedVideos and friends,
     * where watchedVideos[i] and friends[i] contain the list of watched videos and the list of friends respectively for
     * the person with id = i.
     *
     * Level 1 of videos are all watched videos by your friends, level 2 of videos are all watched videos by the friends
     * of your friends and so on. In general, the level k of videos are all watched videos by people with the shortest
     * path exactly equal to k with you. Given your id and the level of videos, return the list of videos ordered by
     * their frequencies (increasing). For videos with the same frequency order them alphabetically from least to
     * greatest.
     *
     * Input: watchedVideos = [["A","B"],["C"],["B","C"],["D"]], friends = [[1,2],[0,3],[0,3],[1,2]], id = 0, level = 1
     * Output: ["B","C"]
     *
     * Constraints:
     *
     * n == watchedVideos.length == friends.length
     * 2 <= n <= 100
     * 1 <= watchedVideos[i].length <= 100
     * 1 <= watchedVideos[i][j].length <= 8
     * 0 <= friends[i].length < n
     * 0 <= friends[i][j] < n
     * 0 <= id < n
     * 1 <= level < n
     * if friends[i] contains j, then friends[j] contains i
     * @param watchedVideos
     * @param friends
     * @param id
     * @param level
     * @return
     */
    // time = O(n + m + vlogv)，space = O(n + v) 其中n是人数，m是好友关系的总数，v是电影的总数
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        int n = watchedVideos.size();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(id);
        boolean[] visited = new boolean[n];
        visited[id] = true;

        int step = 1; // 注意这里step = 1，第一层遍历就到达了level 1的朋友圈，而不是第0层的自己
        List<Integer> persons = new ArrayList<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                for (int f : friends[cur]) {
                    if (visited[f]) continue;
                    visited[f] = true;
                    queue.offer(f);
                    if (step == level) persons.add(f);
                }
            }
            if (step == level) break;
            step++;
        }

        HashSet<String> set = new HashSet<>();
        HashMap<String, Integer> map = new HashMap<>();
        for (int p : persons) {
            for (String v : watchedVideos.get(p)) {
                set.add(v);
                map.put(v, map.getOrDefault(v, 0) + 1);
            }
        }

        List<Pair> temp = new ArrayList<>();
        for (String v : set) temp.add(new Pair(map.get(v), v));
        Collections.sort(temp, ((o1, o2) -> o1.freq != o2.freq ? o1.freq - o2.freq : o1.video.compareTo(o2.video)));
        List<String> res = new ArrayList<>();
        for (Pair p : temp) res.add(p.video);
        return res;
    }

    private class Pair {
        private int freq;
        private String video;
        public Pair(int freq, String video) {
            this.freq = freq;
            this.video = video;
        }
    }
}
/**
 * 搜索，探测到k度好友
 * 用dfs不是很方便
 * 1 - 2 - 3
 * 两个跨度的关系，如果走另一条分支，可能1和3又是直接好友，必须把分支都走一遍
 * 第一次碰到3并不能判断3是否是1的1度好友 => bfs
 * 假设某人是自己的好友的好友，同时也是自己的直接好友，那么只能记做是level为1的好友。因此此题注定用BFS的效率更高。
 * 我们先用BFS找到level为k的好友，然后取出他们所看过的视频。再统计这些视频的观看次数。最后排序。
 */