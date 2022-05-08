package LC2101_2400;
import java.util.*;
public class LC2254_DesignVideoSharingPlatform {
    /**
     * You have a video sharing platform where users can upload and delete videos. Each video is a string of digits,
     * where the ith digit of the string represents the content of the video at minute i. For example, the first digit
     * represents the content at minute 0 in the video, the second digit represents the content at minute 1 in the video,
     * and so on. Viewers of videos can also like and dislike videos. Internally, the platform keeps track of the number
     * of views, likes, and dislikes on each video.
     *
     * When a video is uploaded, it is associated with the smallest available integer videoId starting from 0. Once a
     * video is deleted, the videoId associated with that video can be reused for another video.
     *
     * Implement the VideoSharingPlatform class:
     *
     * VideoSharingPlatform() Initializes the object.
     * int upload(String video) The user uploads a video. Return the videoId associated with the video.
     * void remove(int videoId) If there is a video associated with videoId, remove the video.
     * String watch(int videoId, int startMinute, int endMinute) If there is a video associated with videoId, increase
     * the number of views on the video by 1 and return the substring of the video string starting at startMinute and
     * ending at min(endMinute, video.length - 1) (inclusive). Otherwise, return "-1".
     * void like(int videoId) Increases the number of likes on the video associated with videoId by 1 if there is a
     * video associated with videoId.
     * void dislike(int videoId) Increases the number of dislikes on the video associated with videoId by 1 if there is
     * a video associated with videoId.
     * int[] getLikesAndDislikes(int videoId) Return a 0-indexed integer array values of length 2 where values[0] is
     * the number of likes and values[1] is the number of dislikes on the video associated with videoId. If there is no
     * video associated with videoId, return [-1].
     * int getViews(int videoId) Return the number of views on the video associated with videoId, if there is no video
     * associated with videoId, return -1.
     *
     * Input
     * ["VideoSharingPlatform", "upload", "upload", "remove", "remove", "upload", "watch", "watch", "like", "dislike",
     * "dislike", "getLikesAndDislikes", "getViews"]
     * [[], ["123"], ["456"], [4], [0], ["789"], [1, 0, 5], [1, 0, 1], [1], [1], [1], [1], [1]]
     * Output
     * [null, 0, 1, null, null, 0, "456", "45", null, null, null, [1, 2], 2]
     *
     * Constraints:
     *
     * 1 <= video.length <= 10^5
     * The sum of video.length over all calls to upload does not exceed 105
     * video consists of digits.
     * 0 <= videoId <= 10^5
     * 0 <= startMinute < endMinute < 10^5
     * startMinute < video.length
     * The sum of endMinute - startMinute over all calls to watch does not exceed 105.
     * At most 105 calls in total will be made to all functions.
     */
    HashMap<Integer, String> map; // {id -> str}
    TreeSet<Integer> set;
    int[] views, likes, dislikes;
    public LC2254_DesignVideoSharingPlatform() {
        map = new HashMap<>();
        set = new TreeSet<>();
        views = new int[100005];
        likes = new int[100005];
        dislikes = new int[100005];
    }
    // time = O(logn), space = O(n)
    public int upload(String video) {
        int id = -1;
        if (set.size() == 0) id = map.size();
        else {
            id = set.first();
            set.remove(id);
        }
        map.put(id, video);
        return id;
    }
    // time = O(logn), space = O(n)
    public void remove(int videoId) {
        if (!map.containsKey(videoId)) return;
        map.remove(videoId);
        set.add(videoId);
        views[videoId] = 0;
        likes[videoId] = 0;
        dislikes[videoId] = 0;
    }
    // time = O(1), space = O(n)
    public String watch(int videoId, int startMinute, int endMinute) {
        if (!map.containsKey(videoId)) return "-1";
        views[videoId]++;
        String video = map.get(videoId);
        return video.substring(startMinute, Math.min(video.length(), endMinute + 1));
    }
    // time = O(1), space = O(n)
    public void like(int videoId) {
        if (!map.containsKey(videoId)) return;
        likes[videoId]++;
    }
    // time = O(1), space = O(n)
    public void dislike(int videoId) {
        if (!map.containsKey(videoId)) return;
        dislikes[videoId]++;
    }
    // time = O(1), space = O(n)
    public int[] getLikesAndDislikes(int videoId) {
        if (!map.containsKey(videoId)) return new int[]{-1};
        return new int[]{likes[videoId], dislikes[videoId]};
    }
    // time = O(1), space = O(n)
    public int getViews(int videoId) {
        if (!map.containsKey(videoId)) return -1;
        return views[videoId];
    }
}
