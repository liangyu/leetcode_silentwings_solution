package LC1201_1500;
import java.util.*;
public class LC1452_PeopleWhoseListofFavoriteCompaniesIsNotaSubsetofAnotherList {
    /**
     * Given the array favoriteCompanies where favoriteCompanies[i] is the list of favorites companies for the ith
     * person (indexed from 0).
     *
     * Return the indices of people whose list of favorite companies is not a subset of any other list of favorites
     * companies. You must return the indices in increasing order.
     *
     * Input: favoriteCompanies = [["leetcode","google","facebook"],["google","microsoft"],["google","facebook"],
     * ["google"],["amazon"]]
     * Output: [0,1,4]
     *
     * Constraints:
     *
     * 1 <= favoriteCompanies.length <= 100
     * 1 <= favoriteCompanies[i].length <= 500
     * 1 <= favoriteCompanies[i][j].length <= 20
     * All strings in favoriteCompanies[i] are distinct.
     * All lists of favorite companies are distinct, that is, If we sort alphabetically each list then
     * favoriteCompanies[i] != favoriteCompanies[j].
     * All strings consist of lowercase English letters only.
     * @param favoriteCompanies
     * @return
     */
    // S1: brute-force
    // time = O(n^2 * m), space = O(n * m)
    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        List<HashSet<String>> arr = new ArrayList<>();
        for (int i = 0; i < favoriteCompanies.size(); i++) {
            HashSet<String> set = new HashSet<>(favoriteCompanies.get(i));
            arr.add(set);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < arr.size(); j++) {
                if (i == j) continue;
                boolean included = true;
                for (String s : arr.get(i)) {
                    if (!arr.get(j).contains(s)) {
                        included = false;
                        break;
                    }
                }
                if (included) { // 只要有一个j满足included = true，就表示当前i是失败的；反之，只有每个j下的included都是false，i才是解！
                    flag = false;
                    break;
                }
            }
            if (flag) res.add(i);
        }
        return res;
    }

    // S2: bitset
    // time = O(n * m), space = O(n * m)
    public List<Integer> peopleIndexes2(List<List<String>> favoriteCompanies) {
        int n = favoriteCompanies.size();
        HashMap<String, BitSet> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < favoriteCompanies.get(i).size(); j++) {
                map.putIfAbsent(favoriteCompanies.get(i).get(j), new BitSet(100));
                map.get(favoriteCompanies.get(i).get(j)).set(i); // 对每个公司的bitset，第i位都把它置1
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            BitSet bs = new BitSet(100);
            bs.set(0, 100);
            for (String s : favoriteCompanies.get(i)) {
                bs.and(map.get(s));
            }
            if (bs.cardinality() == 1) res.add(i); // 求完and交集之后结果只有1个人喜欢这公司，那势必就只有我自己，满足条件！！！
        }
        return res;
    }
}
/**
 * i, j
 * n * n * m = 100 * 100 * 50 = 5 * 10^6
 * 暴力做是可以过的
 * S2: 不需要考虑所有的j => 只要考虑那些persons in all companyList[i]
 * i喜欢的公司有哪些人喜欢，只有这些人的公司名单才有可能覆盖i
 * [google, FB, Amazon] => [google] * [FB] * [Amazon]
 * 搞出每个公司有哪些人喜欢，对每个公司喜欢的人取个交集
 * 求交集：bit mask
 * 11100 & 01100 & 00100 = 001000 只有第3个人三家公司都喜欢
 * 但是这里人数有100个，太长了，2^100
 * 介绍新的数据结构：bitset<100> bs; 相当于100位的数组，好处是继承了很多bit manipulation & | ^  set() reset()  count()
 */
