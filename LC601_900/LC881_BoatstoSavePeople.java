package LC601_900;
import java.util.*;
public class LC881_BoatstoSavePeople {
    /**
     * The i-th person has weight people[i], and each boat can carry a maximum weight of limit.
     *
     * Each boat carries at most 2 people at the same time, provided the sum of the weight of those people is at most
     * limit.
     *
     * Return the minimum number of boats to carry every given person.  (It is guaranteed each person can be carried
     * by a boat.)
     *
     * Input: people = [1,2], limit = 3
     * Output: 1
     * Explanation: 1 boat (1, 2)
     *
     * Input: people = [3,2,2,1], limit = 3
     * Output: 3
     * Explanation: 3 boats (1, 2), (2) and (3)
     *
     * Note:
     *
     * 1 <= people.length <= 50000
     * 1 <= people[i] <= limit <= 30000
     *
     * @param people
     * @param limit
     * @return
     */
    // S1: TreeMap
    // time = O(nlogn), space = O(n)
    public int numRescueBoats(int[] people, int limit) {
        // corner case
        if (people == null || people.length == 0) return 0;

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int p : people) { // O(n)
            treeMap.put(p, treeMap.getOrDefault(p, 0) + 1);
        }

        int res = 0;
        while (treeMap.size() > 0) {
            int key = treeMap.lastKey(); // O(logn)
            int count1 = treeMap.get(key);
            if (count1 > 1) treeMap.put(key, count1 - 1);
            else treeMap.remove(key);
            res++;
            // can fit another person
            Integer fk = treeMap.floorKey(limit - key);
            if (fk != null) {
                int count2 = treeMap.get(fk);
                if (count2 > 1) treeMap.put(fk, count2 - 1);
                else treeMap.remove(fk);
            }
        }
        return res;
    }

    // S2: Two Pointers + Greedy 最优解！！！
    // time = O(nlogn), space = O(1)
    public int numRescueBoats2(int[] people, int limit) {
        // corner case
        if (people == null || people.length == 0) return 0;

        Arrays.sort(people); // O(nlogn)

        int i = 0, j = people.length - 1;
        int res = 0;
        while (i <= j) {
            if (people[i] + people[j] <= limit) i++; // case 1: 如果能fit 一轻一重2个人，则把轻的people[i]带上
            j--; // case 2: 如果仅能fit重的一人，那就把当前剩下最重的人带走
            res++; // 无论case 1还是2，都会在这一轮遍历中使用掉一艘船，所以要res++
        }
        return res;
    }


}
