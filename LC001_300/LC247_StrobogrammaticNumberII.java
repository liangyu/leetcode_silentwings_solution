package LC001_300;
import java.util.*;
public class LC247_StrobogrammaticNumberII {
    /**
     * Given an integer n, return all the strobogrammatic numbers that are of length n. You may return the answer in
     * any order.
     *
     * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
     *
     * Input: n = 2
     * Output: ["11","69","88","96"]
     *
     * Constraints:
     *
     * 1 <= n <= 14
     * @param n
     * @return
     */
    // S1: backtracking
    // time = O(5^(n/2)), space = O(5^(n/2))
    public List<String> findStrobogrammatic(int n) {
        return helper(n, n);
    }

    private List<String> helper(int n, int m) {
        // base case
        if (n == 0) return new ArrayList<>(Arrays.asList(""));
        if (n == 1) return new ArrayList<>(Arrays.asList("0", "1", "8"));

        List<String> list = helper(n - 2, m);
        List<String> res = new ArrayList<>();

        for (String s : list) {
            // "00" is not legal here, so "0" can be added at the head and tail => "0" can only be added in the middle
            if (n != m) res.add("0" + s + "0");
            res.add("1" + s + "1");
            res.add("6" + s + "9");
            res.add("8" + s + "8");
            res.add("9" + s + "6");
        }
        return res;
    }

    // S2
    public List<String> findStrobogrammatic2(int n) {
        HashMap<String, String> map = new HashMap<>();
        map.put("0", "0");
        map.put("1", "1");
        map.put("6", "9");
        map.put("8", "8");
        map.put("9", "6");

        List<String> res = new ArrayList<>();
        String curr = "";
        if(n % 2 == 1) {
            helper(res, "0", map, n - 1);
            helper(res, "8", map, n - 1);
            helper(res, "1", map, n - 1);
        } else {
            helper(res, "", map, n);
        }
        return res;
    }

    private void helper(
            List<String> res,
            String curr,
            HashMap<String, String> map,
            int remain) {

        if(remain == 0) {
            res.add(curr);
            return;
        }

        for(String key : map.keySet()) {
            if(remain == 2 && key == "0") continue;
            helper(res, key + curr + map.get(key), map, remain - 2);
        }
    }
}
