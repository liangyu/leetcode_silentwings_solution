package LC301_600;
import java.util.*;
public class LC355_DesignTwitter {
    /**
     * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and is able to
     * see the 10 most recent tweets in the user's news feed.
     *
     * Implement the Twitter class:
     *
     * Twitter() Initializes your twitter object.
     * void postTweet(int userId, int tweetId) Composes a new tweet with ID tweetId by the user userId. Each call to
     * this function will be made with a unique tweetId.
     * List<Integer> getNewsFeed(int userId) Retrieves the 10 most recent tweet IDs in the user's news feed. Each item
     * in the news feed must be posted by users who the user followed or by the user themself. Tweets must be ordered
     * from most recent to least recent.
     * void follow(int followerId, int followeeId) The user with ID followerId started following the user with ID
     * followeeId.
     * void unfollow(int followerId, int followeeId) The user with ID followerId started unfollowing the user with ID
     * followeeId.
     *
     * Input
     * ["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed"]
     * [[], [1, 5], [1], [1, 2], [2, 6], [1], [1, 2], [1]]
     * Output
     * [null, null, [5], null, null, [6, 5], null, [5]]
     *
     * Constraints:
     *
     * 1 <= userId, followerId, followeeId <= 500
     * 0 <= tweetId <= 10^4
     * All the tweets have unique IDs.
     * At most 3 * 104 calls will be made to postTweet, getNewsFeed, follow, and unfollow.
     */
    // time = O(m * n), space = O(m * n)
    HashMap<Integer, List<int[]>> tweets; // userid -> {timestamp, tweetId}
    HashMap<Integer, HashSet<Integer>> friends; // userId -> set of followeeId
    int timestamp;
    public LC355_DesignTwitter() {
        tweets = new HashMap<>();
        friends = new HashMap<>();
        timestamp = 0;
    }

    // time = O(1), space = O(n)
    public void postTweet(int userId, int tweetId) {
        timestamp++;
        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new int[]{timestamp, tweetId});
        friends.putIfAbsent(userId, new HashSet<>());
        if (!friends.get(userId).contains(userId)) friends.get(userId).add(userId);
    }

    // time = O(m * n), space = O(m * n)
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new LinkedList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        for (int i : friends.getOrDefault(userId, new HashSet<>())) {
            int n = tweets.getOrDefault(i, new ArrayList<>()).size();
            for (int j = n - 1; j >= 0; j--) {
                int[] p = tweets.get(i).get(j);
                if (pq.size() < 10) pq.offer(p);
                else if (pq.peek()[0] < p[0]){
                    pq.offer(p);
                    pq.poll();
                } else break;
            }
        }
        while (!pq.isEmpty()) res.add(0, pq.poll()[1]);
        return res;
    }

    // time = O(1), space = O(n)
    public void follow(int followerId, int followeeId) {
        friends.putIfAbsent(followerId, new HashSet<>());
        friends.get(followerId).add(followeeId);
    }

    // time = O(1), space = O(n)
    public void unfollow(int followerId, int followeeId) {
        if (friends.get(followerId).contains(followeeId) && followerId != followeeId) {
            friends.get(followerId).remove(followeeId);
        }
    }
}
/**
 * pull: user只存自己的，等另一个想要读帖子，会到所有好友里拉10个帖子
 * push: 帖子推给所有好友的信箱里，每个人的信箱最多10个容量就够了，新的覆盖老的
 * 本题用pull model
 * 这里因为有follow和unfollow，所以用pull。
 * 新粉丝只会受到关注之后的帖子，之前的帖子永远得不到
 */