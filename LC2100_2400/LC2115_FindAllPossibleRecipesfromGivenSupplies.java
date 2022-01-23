package LC2100_2400;
import java.util.*;
public class LC2115_FindAllPossibleRecipesfromGivenSupplies {
    /**
     * You have information about n different recipes. You are given a string array recipes and a 2D string array
     * ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients
     * from ingredients[i]. Ingredients to a recipe may need to be created from other recipes, i.e., ingredients[i] may
     * contain a string that is in recipes.
     *
     * You are also given a string array supplies containing all the ingredients that you initially have, and you have
     * an infinite supply of all of them.
     *
     * Return a list of all the recipes that you can create. You may return the answer in any order.
     *
     * Note that two recipes may contain each other in their ingredients.
     *
     * Input: recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
     * Output: ["bread"]
     *
     * Constraints:
     *
     * n == recipes.length == ingredients.length
     * 1 <= n <= 100
     * 1 <= ingredients[i].length, supplies.length <= 100
     * 1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
     * recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters.
     * All the values of recipes and supplies combined are unique.
     * Each ingredients[i] does not contain any duplicate values.
     * @param recipes
     * @param ingredients
     * @param supplies
     * @return
     */
    // S1: Topological Sort (DFS)
    // time = O(V + E), space = O(V + E)
    HashSet<String> set;
    HashMap<String, Integer> map;
    HashMap<String, Integer> status;
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        List<String> res = new ArrayList<>();
        set = new HashSet<>();
        for (String s : supplies) set.add(s);
        map = new HashMap<>();
        int n = recipes.length;
        for (int i = 0; i < n; i++) map.put(recipes[i], i);
        status = new HashMap<>();

        for (int i = 0; i < n; i++) {
            if (containsCycle(recipes[i], ingredients)) continue;
            res.add(recipes[i]);
        }
        return res;
    }

    private boolean containsCycle(String s, List<List<String>> ingredients) {
        if (status.getOrDefault(s, 0) == 1) return true;
        if (status.getOrDefault(s, 0) == 2) return false;

        status.put(s, 1);
        int i = map.get(s);
        List<String> list = ingredients.get(i);
        boolean flag = true;
        for (String x : list) {
            if (set.contains(x)) continue;
            if (!map.containsKey(x) || containsCycle(x, ingredients)) return true;
        }
        status.put(s, 2);
        return false;
    }

    // S2: Topological Sort (BFS)
    // time = O(V + E), space = O(V + E)
    public List<String> findAllRecipes2(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        HashMap<String, List<String>> map = new HashMap<>();
        HashMap<String, Integer> indegree = new HashMap<>();

        int n = recipes.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < ingredients.get(i).size(); j++) {
                String ingredient = ingredients.get(i).get(j), recipe = recipes[i];
                map.putIfAbsent(ingredient, new ArrayList<>());
                map.get(ingredient).add(recipe);
                indegree.put(recipe, indegree.getOrDefault(recipe, 0) + 1);
            }
        }

        HashSet<String> set = new HashSet<>();
        for (String r : recipes) set.add(r);
        Queue<String> queue = new LinkedList<>();
        for (String s : supplies) queue.offer(s);

        List<String> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            if (set.contains(cur)) res.add(cur);

            for (String next : map.getOrDefault(cur, new ArrayList<>())) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) queue.offer(next);
            }
        }
        return res;
    }
}
/**
 * recipe里套recipe
 * 与先修课程一样的思路
 * 拓扑结构
 * 都是单向的有向图
 * 有些点是没有入度的
 * 拓扑排序 -> 剥洋葱
 */