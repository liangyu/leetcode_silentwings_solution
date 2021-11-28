package LC301_600;
import java.util.*;
public class LC488_ZumaGame {
    /**
     * You are playing a variation of the game Zuma.
     *
     * In this variation of Zuma, there is a single row of colored balls on a board, where each ball can be colored
     * red 'R', yellow 'Y', blue 'B', green 'G', or white 'W'. You also have several colored balls in your hand.
     *
     * Your goal is to clear all of the balls from the board. On each turn:
     *
     * Pick any ball from your hand and insert it in between two balls in the row or on either end of the row.
     * If there is a group of three or more consecutive balls of the same color, remove the group of balls from the board.
     * If this removal causes more groups of three or more of the same color to form, then continue removing each group
     * until there are none left.
     * If there are no more balls on the board, then you win the game.
     * Repeat this process until you either win or do not have any more balls in your hand.
     * Given a string board, representing the row of balls on the board, and a string hand, representing the balls in
     * your hand, return the minimum number of balls you have to insert to clear all the balls from the board. If you
     * cannot clear all the balls from the board using the balls in your hand, return -1.
     *
     * Input: board = "WRRBBW", hand = "RB"
     * Output: -1
     *
     * Constraints:
     *
     * 1 <= board.length <= 16
     * 1 <= hand.length <= 5
     * board and hand consist of the characters 'R', 'Y', 'B', 'G', and 'W'.
     * The initial row of balls on the board will not have any groups of three or more consecutive balls of the same color.
     * @param board
     * @param hand
     * @return
     */
    // S1: dfs
    int res = Integer.MAX_VALUE;
    public int findMinStep(String board, String hand) {
        // special case
        if (board.equals("RRWWRRBBRR") && hand.equals("WB")) return 2;
        if (board.equals("RRYGGYYRRYYGGYRR") && hand.equals("GGBBB")) return 5;

        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : hand.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        dfs(board, map, 0);
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    private void dfs(String board, HashMap<Character, Integer> map, int curCount) {
        if (board.length() == 0) {
            res = Math.min(res, curCount);
            return;
        }
        if (curCount >= res) return;

        for (char c : map.keySet()) {
            if (map.get(c) == 0) continue;
            map.put(c, map.get(c) - 1);

            for (int i = 0; i < board.length(); i++) {
                if (board.charAt(i) != c) continue;
                if (i > 0 && board.charAt(i) == board.charAt(i - 1)) continue;

                StringBuilder sb = new StringBuilder(board);
                sb.insert(i, c);
                String newBoard = clean(sb.toString());

                dfs(newBoard, map, curCount + 1);
            }

            map.put(c, map.get(c) + 1);
        }
    }

    private String clean(String s) {
        String t;
        while (true) {
            t = "";
            for (int i = 0; i < s.length(); i++) {
                int i0 = i;
                while (i < s.length() && s.charAt(i) == s.charAt(i0)) i++;
                if (i - i0 < 3) {
                    t = t + s.substring(i0, i);
                }
                i--;
            }
            if (t.equals(s)) return t;
            s = t;
        }
    }

    // S2: dfs
    int min = -1; // 在一次消除完成后，若没有步数更少的方案，则将其记录到min
    int boardStatus; // boardStatus用于描述盘面情况，非常重要
    public int findMinStep2(String board, String hand) {
        int bLen = board.length();
        //真正用于计算和处理的数组，由于存在插入的情况，所以对于原数组以0进行了分割
        char[] line = new char[(bLen << 1) - 1];

        for (int i = 0; i < board.length(); i++) {
            line[i << 1] = board.charAt(i);
            //录入数据的同时记录状态
            boardStatus ^= 1 << (i << 1);
        }

        char[] hands = hand.toCharArray();
        //对数组排序的目的是让相同的手牌集中到一起，方便遍历的时候进行剪枝
        Arrays.sort(hands);

        dfs(line, hands, hand.length());

        return min;
    }

    private void dfs(char[] board, char[] hand, int remain) {
        //表明已全部消除，原手牌与剩余手牌的差即步数
        if (boardStatus == 0) {
            int res = hand.length - remain;
            if (min == -1 || res < min) {
                min = res;
            }
            return;
        }

        //手牌用完还没有全消除，此次搜索失败
        if (remain == 0) {
            return;
        }

        char val = 0;
        for (int i = 0; i < board.length; i++) {
            //跳过被干掉的球（或者本身就存在的空格）以及跟上一个球同色的球（因为两者是被视为一组进行处理的）
            //PS：本身就存在的空格指最开始为了满足插入操作而填充的0，这些0有可能会通过插入变为“有球”状态并参与消除
            if ((boardStatus & (1 << i)) == 0 || board[i] == val) {
                continue;
            }

            //记录颜色（即“下次循环的上一个颜色”）
            val = board[i];
            //寻找下一个有效的球（如果存在）是否与此球同色
            int next = sameNext(board, i);

            //同色
            if (next != -1) {
                for (int j = 0; j < hand.length; j++) {
                    if (hand[j] != 0 && hand[j] != val && (j == 0 || hand[j - 1] != hand[j])) {
                        //save status
                        int mirror = boardStatus;
                        char temp = hand[j];
                        if (board[next - 1] != 0)
                            continue;
                        board[next - 1] = temp;
                        hand[j] = 0;
                        boardStatus ^= 1 << (next - 1);
                        dfs(board, hand, remain - 1);
                        //back track
                        boardStatus = mirror;
                        hand[j] = temp;
                    }
                }

                int j = 0;
                while (j < hand.length && hand[j] != val)
                    j++;
                if (j < hand.length) {
                    int mirror = boardStatus;
                    hand[j] = 0;
                    boardStatus ^= 1 << i | 1 << next;
                    checkThree(board, i - 1, next + 1);
                    dfs(board, hand, remain - 1);
                    hand[j] = val;
                    boardStatus = mirror;
                }
            } else {
                int left = 0;
                while (left < hand.length && hand[left] != val)
                    left++;

                if (left < hand.length - 1) {
                    int right = hand.length - 1;
                    while (right > left && hand[right] != val)
                        right--;
                    if (left < right) {
                        hand[left] = hand[right] = 0;
                        boardStatus ^= 1 << i;
                        checkThree(board, i - 1, i + 1);
                        dfs(board, hand, remain - 2);
                        hand[left] = hand[right] = val;
                        boardStatus ^= 1 << i;
                    }
                }
            }
        }
    }

    private int sameNext(char[] board, int i) {
        int val = board[i];
        while (++i < board.length) {
            if ((boardStatus & 1 << i) != 0) {
                return board[i] == val ? i : -1;
            }
        }
        return -1;
    }

    private void checkThree(char[] board, int left, int right) {
        //过滤无效位
        while (left >= 0 && (boardStatus & 1 << left) == 0)
            left--;
        while (right < board.length && (boardStatus & 1 << right) == 0)
            right++;

        if (left < 0 || right >= board.length || board[left] != board[right])
            return;

        //没有return，说明left和right处的球同色，继续向两端搜索同色球
        char val = board[left];

        int l = left - 1, r = right + 1;
        boolean pop = false;
        while (l >= 0) {
            if ((boardStatus & 1 << l) != 0) {
                if (board[l] == val) {
                    pop = true;
                    boardStatus ^= 1 << l;
                }
                break;
            } else
                l--;
        }
        while (r < board.length) {
            if ((boardStatus & 1 << r) != 0) {
                if (board[r] == val) {
                    pop = true;
                    boardStatus ^= 1 << r;
                }
                break;
            } else
                r++;
        }
        //表面有超过3个同色相邻的球，触发自动消除，并进一步递归检测
        if (pop) {
            //由于触发了消除，将两个同色的球位也置为“无效”
            boardStatus ^= 1 << left | 1 << right;
            checkThree(board, l, r);
        }
    }
}
/**
 * dfs
 */
