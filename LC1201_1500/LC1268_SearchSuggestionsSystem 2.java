package LC1201_1500;
import java.util.*;
public class LC1268_SearchSuggestionsSystem {
    /**
     * Given an array of strings products and a string searchWord. We want to design a system that suggests at most
     * three product names from products after each character of searchWord is typed. Suggested products should have
     * common prefix with the searchWord. If there are more than three products with a common prefix return the three
     * lexicographically minimums products.
     *
     * Return list of lists of the suggested products after each character of searchWord is typed.
     *
     * Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
     * Output: [
     * ["mobile","moneypot","monitor"],
     * ["mobile","moneypot","monitor"],
     * ["mouse","mousepad"],
     * ["mouse","mousepad"],
     * ["mouse","mousepad"]
     * ]
     *
     * Constraints:
     *
     * 1 <= products.length <= 1000
     * There are no repeated elements in products.
     * 1 <= Σ products[i].length <= 2 * 10^4
     * All characters of products[i] are lower-case English letters.
     * 1 <= searchWord.length <= 1000
     * All characters of searchWord are lower-case English letters.
     * @param products
     * @param searchWord
     * @return
     */
    // time = O((m + n) * logn + m^2), space = O(n)
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<>();
        // corner case
        if (products == null || products.length == 0 || searchWord == null || searchWord.length() == 0) return res;

        TreeMap<String, Integer> map = new TreeMap<>();
        Arrays.sort(products); // O(nlogn)

        for (int i = 0; i < products.length; i++) map.put(products[i], i); // O(n)

        String word = "";
        for (char c : searchWord.toCharArray()) { // O(m)
            word += c; // O(m)
            String ck = map.ceilingKey(word); // O(logn)
            String fk = map.floorKey(word + "~"); // 加上一个额外字符"~"来确保word本身不会成为fk
            if (ck == null || fk == null) break;

            int start = map.get(ck), end = Math.min(map.get(ck) + 3, map.get(fk) + 1);
            List<String> ans = new ArrayList<>();
            for (int i = start; i < end; i++) ans.add(products[i]);
            res.add(ans);
        }
        while (res.size() < searchWord.length()) res.add(new ArrayList<>());
        return res;
    }
}
