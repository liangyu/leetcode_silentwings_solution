package LC301_600;
import java.util.*;
public class LC599_MinimumIndexSumofTwoLists {
    /**
     * Suppose Andy and Doris want to choose a restaurant for dinner, and they both have a list of favorite restaurants
     * represented by strings.
     *
     * You need to help them find out their common interest with the least list index sum. If there is a choice tie
     * between answers, output all of them with no order requirement. You could assume there always exists an answer.
     *
     * Input: list1 = ["Shogun","Tapioca Express","Burger King","KFC"], list2 = ["Piatti","The Grill at Torrey Pines",
     * "Hungry Hunter Steakhouse","Shogun"]
     * Output: ["Shogun"]
     *
     * Constraints:
     *
     * 1 <= list1.length, list2.length <= 1000
     * 1 <= list1[i].length, list2[i].length <= 30
     * list1[i] and list2[i] consist of spaces ' ' and English letters.
     * All the stings of list1 are unique.
     * All the stings of list2 are unique.
     * @param list1
     * @param list2
     * @return
     */
    public String[] findRestaurant(String[] list1, String[] list2) {
        List<String> res = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();

        int m = list1.length, n = list2.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) map.put(list1[i], i);
        for (int i = 0; i < n; i++) {
            if (map.containsKey(list2[i])) {
                int sum = i + map.get(list2[i]);
                if (sum < min) {
                    min = sum;
                    res.clear();
                    res.add(list2[i]);
                } else if (sum == min) res.add(list2[i]);
            }
        }
        String[] ans = new String[res.size()];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }
}
