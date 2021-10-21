package LC1801_2100;

public class LC2029_StoneGameIX {
    /**
     * Alice and Bob continue their games with stones. There is a row of n stones, and each stone has an associated
     * value. You are given an integer array stones, where stones[i] is the value of the ith stone.
     *
     * Alice and Bob take turns, with Alice starting first. On each turn, the player may remove any stone from stones.
     * The player who removes a stone loses if the sum of the values of all removed stones is divisible by 3. Bob will
     * win automatically if there are no remaining stones (even if it is Alice's turn).
     *
     * Assuming both players play optimally, return true if Alice wins and false if Bob wins.
     *
     * Input: stones = [2,1]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= stones.length <= 10^5
     * 1 <= stones[i] <= 10^4
     * @param stones
     * @return
     */
    // time = O(2^n), space = O(n)
    public boolean stoneGameIX(int[] stones) {
        // corner case
        if (stones == null || stones.length == 0) return false;

        int[] count = new int[3];
        for (int x : stones) count[x % 3]++;

        // pick number whose remainder = 1
        int[] temp = count.clone();
        if (temp[1] > 0) {
            temp[1]--;
            if (!win(temp, 1, 1)) return true; // {temp, sum, person}
        }

        temp = count;
        // pick number whose remainder = 2
        if (temp[2] > 0) {
            temp[2]--;
            if (!win(temp, 2, 1)) return true;
        }

        return false;
    }

    private boolean win(int[] count, int sum, int turn) {
        // base case
        if (count[0] + count[1] + count[2] == 0) { // 全部取完
            if (turn == 1) return true;
            return false;
        }

        if (count[0] > 0) {
            count[0]--;
            return !win(count, sum, 1 - turn); // 唯一决策问题，我赢不赢完全取决于对手
        } else if (sum % 3 == 1) {
            if (count[1] > 0) {
                count[1]--;
                return !win(count, sum + 1, 1 - turn);
            } else return false;
        } else  {
            if (count[2] > 0) {
                count[2]--;
                return !win(count, sum + 2, 1 - turn);
            } else return false;
        }
    }
}
/**
 * 只看余数，3种情况：
 * count[0], count[1], count[2]
 * 扔给我的sum，一定不会被3整除，如果被3整除，对方扔过来之前就已经输了
 * 我做决策：以上3种
 * 1. sum + 0 -> 不会被3整除，我这一轮是安全的，把问题扔给队首，那是不是一定要选它 -> 一定选它，不选白不选，否则你不选，对方肯定选
 * sum + x，只要一开局大家都抢余数为0的数，直至全部消耗完 => 2选1
 * 2. if sum % 3 == 1 choose count[1] otherwise fail
 *    if sum % 3 == 2 choose count[2] otherwise fail
 * 3. pass to the rival
 * 此时余数为1的话，不得不从count[1]里找一个数；否则也以此类推
 * 其实每一步都是固定的策略，没有任何分支
 * 所有东西都没了，没得选了，额外标记下Bob赢
 * Alice先走，开局只能选余数为1或者2的数
 * 递归recursion，每次只能消灭一个元素
 * 1,[1,2],1,2,1,2,
 */
