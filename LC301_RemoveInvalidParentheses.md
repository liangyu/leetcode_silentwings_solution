# LC301 Remove Invalid Parentheses

标签（空格分隔）： LeetCode Java DFS

---
    /**
     * Remove the minimum number of invalid parentheses in order to make the input string valid.
     * Return all possible results.
     *
     * Note: The input string may contain letters other than the parentheses ( and ).
     *
     * Input: "()())()"
     * Output: ["()()()", "(())()"]
     *
     * Input: "(a)())()"
     * Output: ["(a)()()", "(a())()"]
     *
     * @param s
     * @return
     */

【难点误区】

1. 想不到用预算要去掉的左右括号数量来优化时间复杂度！
2. base case出现cover不全的情况
3. 在选择keep左右括号的同时，需要注意相邻相同括号间遵循“一加全加”的逻辑，用一个i去向后遍历，k来统计相同的数量，并同时都append进入path里。

【解题思路】

本题是DFS的经典问题，核心难点在于如何去重，最trick的地方就是如何可以通过预先计算出要去除多少个左括号和多少个右括号来大大减少时间复杂度！

就S1的最优DFS解来说，核心思想是：

1. 预算去掉多少左括号和右括号，其基本思路是遇到左括号就左括号数量rml++，遇到右括号时首先看能不能先和左括号匹配，有左括号可以匹配的话，那就先消耗左括号，即让rml--；反之没有左括号可以匹配的话，再增加右括号的数量rmr++。这样到了最后，还剩下的左括号rml和右括号rmr的数目即为各自所有删除的数目。
2. DFS的基本参数按套路来有s, idx, path, res, delta (对于括号匹配来说必备!)，加上针对这题独有的rml和rmr就构成了。
3. DFS的base case，走到底且delta == 0 并且rml == 0, rmr == 0，即该删的左括号和右括号的指标都完成了且剩下的左右括号数量相等，显然就成功过了。而如果走到底却并没有实现上述条件，肯定是fail，同时如果任意时刻delta < 0，或者rml < 0, rmr < 0，即右括号多出来了，或者左括号或者右括号删多了，显然也是不行的，fail。
4. 接下来就是分3种case讨论，分别是左括号，右括号和其他字符。对于左括号和右括号来说，又分为删除和保留2叉，删除的话没说的，直接搞掉看下一位，idx + 1，然后左括号删除指标rml或者右括号删除指标rmr对应 - 1。保留的话，则要注意查重，遇到多个相同括号彼此相邻叠在一起的话，遵循要keep一起keep的原则，否则接下来一定会出现重复的case，所以要用一个指针 i 去向后traverse，同时用一个变量k去count有多少重复的括号相邻在一起，最后把这些相邻的数目都加入，统计到delta的变化中去即可。这里注意的是，delta反应的是path里括号种类与数量的变化，而与rml, rmr无关！！！rml与rmr是预先算好的一定会删除的左右括号的数量，跟delta与path无关！所以这里出来的dfs只会变更idx -> i，delta -> delta + / - k，而path则会在遍历中append进这些相邻的相同括号。
5. 最后一个case就是其他符号，没啥好说的，直接append进去，对其他元素毫无影响，接着看下一个即可。

```
/**
 *  (((() -> ()  要去重 -> 保留1个，去掉3个，有很多不同路径得到同一个结果
 *  要得到互不相同的结果 -> 用hashmap去重，太慢！O(2^n) -> 能不能不用hash来得到一个独一无二的结果 -> 本题精华！！！
 *  DFS怎么去重？？？ 出现重复的根源在哪？
 *  (((( => (( => C(4, 2)种选择方式 = 4 * 3 / 2 = 6
 *  OOXX
 *  OXOX
 *  OXXO
 *  XOOX
 *  XOXO
 *  XXOO
 *  额外加一种规则 => 相同的话必须先取，比如如果取2个，就必须取前面遇见的2个 => DFS搜索过程中就不会有任何重复了
 *
 *  ())() => )) =>
 *  XOOXX 只有唯一的一条DFS路径得到它！比如XXOXO就不符合。
 *
 *  (((()
 *  XXX
 *  XXO
 *  XOO
 *  OX
 *  OO
 *
 *  作规定：
 *  if (s[i] == curStr.back())
 *      add(s[i])
*   else
 *      add(S[i])
 *      skip(s[i])
 *  这样就不会有重复 => 保证生成的dfs路径是唯一的
 *  ((((((((
 *  XXXXOOOO
 *      ((((
 *
 *   cur = (((
 *         OOX
 *         XOO (与上面重复)
 */
```


```java     
// S1: DFS 最优解！！！
// time = O(2^n), space = O(n)
public List<String> removeInvalidParentheses(String s) {
    List<String> res = new ArrayList<>();
    // corner case
    if (s == null) return res;

    int[] rmlr = calrmlr(s); // 能够预算去掉多少左括号，去掉多少右括号
    dfs(s, new StringBuilder(), 0, rmlr[0], rmlr[1], 0, res);
    return res;
}

private void dfs(String s, StringBuilder path, int idx, int rml, int rmr, int delta, List<String> res) {
    // base case - success
    if (idx == s.length() && delta == 0 && rml == 0 && rmr == 0) {
        res.add(path.toString());
        return;
    }

    // base case - fail
    if (rml < 0 || rmr < 0 || delta < 0 || idx == s.length()) {
        return;
    }

    char c = s.charAt(idx);
    int len = path.length();

    // case 1: '('
    if (c == '(') {
        // case 1.1: remove '('
        dfs(s, path, idx + 1, rml - 1, rmr, delta, res);

        // case 1.2: keep '(' -> 连续的多个相同括号，要加就全都得加，否则就会有重复！！！
        int i = idx, k = 0;
        while (i < s.length() && s.charAt(i) == '(') {
            path.append('(');
            i++;
            k++;
        }
        dfs(s, path, i, rml, rmr, delta + k, res);
        path.setLength(len);
    } else if (c == ')') { // case 2: ')'
        // case 2.1: remove ')'
        dfs(s, path, idx + 1, rml, rmr - 1, delta, res);

        // case 2.2: keep ')'
        int i = idx, k = 0;
        while (i < s.length() && s.charAt(i) == ')') {
            path.append(')');
            i++;
            k++;
        }
        dfs(s, path, i, rml, rmr, delta - k, res);
        path.setLength(len);
    } else { // case 3: other chars
        path.append(c);
        dfs(s, path, idx + 1, rml, rmr, delta, res);
        path.setLength(len);
    }
}

// 预先计算要去掉多少左括号，多少右括号，大大减少了时间复杂度！！！
private int[] calrmlr(String s) {
    int rml = 0, rmr = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') {
            rml++;
        } else if (c == ')') {
            if (rml > 0) rml--;
            else rmr++;
        }
    }
    return new int[]{rml, rmr};
}
```
```java
// S2: DFS 精简版
// time = O(2^n), space = O(n)
private int maxLen = 0;
public List<String> removeInvalidParentheses2(String s) {
    List<String> res = new ArrayList<>();
    // corner case
    if (s == null) return res;

    String curStr = "";
    dfs(curStr, s, 0, 0, res); // pos, count
    return res;
}

private void dfs(String curStr, String s, int i, int count, List<String> res) {
    // base case
    if (count < 0) return; // 非法条件
    if (i == s.length()) {
        if (count == 0) {
            if (curStr.length() > maxLen) {
                res.clear();
                res.add(curStr);
                maxLen = curStr.length();
            } else if (curStr.length() == maxLen) {
                res.add(curStr);
            }
        }
        return;
    }

    // case 0: 非括号字符 -> 直接加入，不影响括号之间的匹配
    if (s.charAt(i) != '(' && s.charAt(i) != ')') {
        dfs(curStr + s.substring(i, i + 1), s, i + 1, count, res);
        return;
    }

    // case 1: choose s[i]
    dfs(curStr + s.substring(i, i + 1), s, i + 1, count + (s.charAt(i) == '(' ? 1 : -1), res);

    // case 2: not choose s[i] -> 避免做重复的DFS的技巧，不同路径得到同一个结果，及时剪枝！！！
    if (curStr.length() == 0 || s.charAt(i) != curStr.charAt(curStr.length() - 1)) { // 不选
        dfs(curStr, s, i + 1, count, res);
    }
}
```
