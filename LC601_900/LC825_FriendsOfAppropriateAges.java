package LC601_900;
import java.util.*;
public class LC825_FriendsOfAppropriateAges {
    /**
     * Some people will make friend requests. The list of their ages is given and ages[i] is the age of the ith person.
     *
     * Person A will NOT friend request person B (B != A) if any of the following conditions are true:
     *
     * age[B] <= 0.5 * age[A] + 7
     * age[B] > age[A]
     * age[B] > 100 && age[A] < 100
     * Otherwise, A will friend request B.
     *
     * Note that if A requests B, B does not necessarily request A.  Also, people will not friend request themselves.
     *
     * How many total friend requests are made?
     *
     * Input: [16,16]
     * Output: 2
     *
     * Notes:
     *
     * 1 <= ages.length <= 20000.
     * 1 <= ages[i] <= 120.
     * @param ages
     * @return
     */
    // S1: HashMap
    // time = O(n^2), space = O(n)
    public int numFriendRequests(int[] ages) {
        // corner case
        if (ages == null || ages.length <= 1) return 0;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int age : ages) map.put(age, map.getOrDefault(age, 0) + 1);

        int res = 0;
        for (int a : map.keySet()) {
            for (int b : map.keySet()) {
                if (helper(a, b)) res += map.get(a) * (map.get(b) - (a == b ? 1 : 0));
            }
        }
        return res;
    }

    private boolean helper(int a, int b) {
        if (b <= 0.5 * a + 7 || b > a || b > 100 && a < 100) return false;
        return true;
    }

    // S2: BS
    // time = O(n^2), space = O(n)
    public int numFriendRequests2(int[] ages) {
        // corner case
        if (ages == null || ages.length <= 1) return 0;

        int[] nums = new int[121];
        for (int x : ages) nums[x]++;

        int bound = 15, presum = 0, count = 0;
        for (int i = 15; i < 121; i++) {
            if (bound <= 0.5 * i + 7) {
                presum -= nums[bound];
                bound++;
            }
            if (nums[i] == 0) continue;
            count += nums[i] * presum + nums[i] * (nums[i] - 1);
            presum += nums[i];
        }
        return count;
    }
}
/**
 * age[B] <= 0.5 * age[A] + 7   => age[B] > age[A]*0.5+7  => age[B] >= age[A]*0.5 + 8
 * age[B] > age[A]   => age[B] <= age[A]  => 0.5*age[A] + 7 < age[A]  -> age[A] > 14 => age[A] >= 15
 * age[B] > 100 && age[A] < 100
 * 条件 3 是蕴含在条件 2 中的，即如果满足条件 3 那么一定满足条件 2。
 */
