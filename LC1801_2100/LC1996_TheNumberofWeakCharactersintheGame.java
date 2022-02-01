package LC1801_2100;
import java.util.*;
public class LC1996_TheNumberofWeakCharactersintheGame {
    /**
     * You are playing a game that contains multiple characters, and each of the characters has two main properties:
     * attack and defense. You are given a 2D integer array properties where properties[i] = [attacki, defensei]
     * represents the properties of the ith character in the game.
     *
     * A character is said to be weak if any other character has both attack and defense levels strictly greater than
     * this character's attack and defense levels. More formally, a character i is said to be weak if there exists
     * another character j where attackj > attacki and defensej > defensei.
     *
     * Return the number of weak characters.
     *
     * Input: properties = [[5,5],[6,3],[3,6]]
     * Output: 0
     *
     * Constraints:
     *
     * 2 <= properties.length <= 10^5
     * properties[i].length == 2
     * 1 <= attacki, defensei <= 10^5
     * @param properties
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int numberOfWeakCharacters(int[][] properties) {
        // corner case
        if (properties == null || properties.length == 0 || properties[0] == null || properties[0].length == 0) {
            return 0;
        }

        int n = properties.length;
        Arrays.sort(properties, (o1, o2) -> o1[0] != o2[0] ? o2[0] - o1[0] : o1[1] - o2[1]);

        int count = 0, max = 0;
        for (int[] p : properties) {
            count += max > p[1] ? 1 : 0;
            max = Math.max(max, p[1]);
        }
        return count;
    }

    // S2: sort + monotonic stack
    // time = O(nlogn), space = O(n)
    public int numberOfWeakCharacters2(int[][] properties) {
        // corner case
        if (properties == null || properties.length == 0 || properties[0] == null || properties[0].length == 0) {
            return 0;
        }

        Arrays.sort(properties, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);

        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (int i = 0; i < properties.length; i++) {
            while (!stack.isEmpty() && stack.peek() < properties[i][1]) {
                stack.pop();
                res++;
            }
            stack.push(properties[i][1]);
        }
        return res;
    }
}
/**
 * ref: LC354  双属性，防止出现长度相同的时候宽度的嵌套，宽度按照降序排列，避免误操作！
 * 同时要找满足这2个条件的人，无法暴力
 * 对于这一类题目，同时要处理2个属性比较困难
 * 固定一个属性，另一个比较复杂的再观察
 * 把所有人按攻击力从小到大排序 -> 靠前的人攻击力比较低
 * attack:   [1 2 3 4 5 6 7 8]
 * defense:  [3 5 3 2 1 3 4 7]
 * 唯一要确定的是靠前defense的人比后面的低
 * 这里用一个单调栈，如果栈顶元素比新来的要低，
 * [3 5 3] [2 1] [3] [4] 7  -> 把3弹掉，5进栈  => 防御力递减的stack
 * 先排序，再用单调栈来找弱角色
 * 这里唯一要注意的是，如果攻击力是相同的
 * => 在排序的时候，对相同攻击力排序的时候，按照防御力递减的顺序排序，这样就不会出现弹栈的误操作！！！(类似Russian doll)
 * 固定一个属性，化简一个属性，遍历一个属性，集中精力搞另一个属性！！！
 */
