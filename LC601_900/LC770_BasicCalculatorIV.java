package LC601_900;
import java.util.*;
public class LC770_BasicCalculatorIV {
    /**
     * Given an expression such as expression = "e + 8 - a + 5" and an evaluation map such as {"e": 1} (given in terms
     * of evalvars = ["e"] and evalints = [1]), return a list of tokens representing the simplified expression, such as
     * ["-1*a","14"]
     *
     * An expression alternates chunks and symbols, with a space separating each chunk and symbol.
     * A chunk is either an expression in parentheses, a variable, or a non-negative integer.
     * A variable is a string of lowercase letters (not including digits.) Note that variables can be multiple letters,
     * and note that variables never have a leading coefficient or unary operator like "2x" or "-x".
     * Expressions are evaluated in the usual order: brackets first, then multiplication, then addition and subtraction.
     *
     * For example, expression = "1 + 2 * 3" has an answer of ["7"].
     * The format of the output is as follows:
     *
     * For each term of free variables with a non-zero coefficient, we write the free variables within a term in sorted
     * order lexicographically.
     * For example, we would never write a term like "b*a*c", only "a*b*c".
     * Terms have degrees equal to the number of free variables being multiplied, counting multiplicity. We write the
     * largest degree terms of our answer first, breaking ties by lexicographic order ignoring the leading coefficient
     * of the term.
     * For example, "a*a*b*c" has degree 4.
     * The leading coefficient of the term is placed directly to the left with an asterisk separating it from the
     * variables (if they exist.) A leading coefficient of 1 is still printed.
     * An example of a well-formatted answer is ["-2*a*a*a", "3*a*a*b", "3*b*b", "4*a", "5*c", "-6"].
     * Terms (including constant terms) with coefficient 0 are not included.
     * For example, an expression of "0" has an output of [].
     *
     * Input: expression = "e + 8 - a + 5", evalvars = ["e"], evalints = [1]
     * Output: ["-1*a","14"]
     *
     * Constraints:
     *
     * 1 <= expression.length <= 250
     * expression consists of lowercase English letters, digits, '+', '-', '*', '(', ')', ' '.
     * expression does not contain any leading or trailing spaces.
     * All the tokens in expression are separated by a single space.
     * 0 <= evalvars.length <= 100
     * 1 <= evalvars[i].length <= 20
     * evalvars[i] consists of lowercase English letters.
     * evalints.length == evalvars.length
     * -100 <= evalints[i] <= 100
     * @param expression
     * @param evalvars
     * @param evalints
     * @return
     */
    HashMap<String, Integer> vars = new HashMap<>();
    private char[] chars;
    private int i = 0;
    public List<String> basicCalculatorIV(String expression, String[] evalvars, int[] evalints) {
        this.chars = expression.toCharArray();
        for (int k = 0; k < evalvars.length; k++) {
            vars.put(evalvars[k], evalints[k]);
        }

        //Consider order of term first, then lexicographic comparison
        Map<String, Integer> resultMap = expression();
        PriorityQueue<String> queue =
                new PriorityQueue<>((a,b) -> {
                    int orderDiff = b.split("\\*").length - a.split("\\*").length;
                    if (orderDiff != 0) {
                        return orderDiff;
                    }
                    else {
                        return a.compareTo(b);
                    }
                });

        for (String s: resultMap.keySet()) {
            queue.offer(s);
        }

        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String var = queue.poll();
            if (var.equals("")) {
                continue;
            }

            StringBuilder sb = new StringBuilder();
            int value = resultMap.get(var);
            if (value != 0) {
                sb.append(value).append("*").append(var);
                result.add(sb.toString());
            }
        }

        if (resultMap.containsKey("") && resultMap.get("") != 0) {
            result.add(resultMap.get("").toString());
        }
        return result;
    }

    private Map<String, Integer> add(Map<String, Integer> m, Map<String, Integer> p, boolean subtract) {
        for (Map.Entry<String, Integer> entry: p.entrySet()) {
            String key = entry.getKey();
            int val = entry.getValue();
            if (subtract) {
                val *= -1;
            }
            m.put(key, m.getOrDefault(key, 0) + val);
        }
        return m;
    }

    private Map<String, Integer> multiply(Map<String, Integer> m, Map<String, Integer> p) {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> outer: m.entrySet()) {
            String outerVar = outer.getKey();
            Integer outerVal = outer.getValue();
            for(Map.Entry<String, Integer> inner: p.entrySet()) {
                String innerVar = inner.getKey();
                Integer innerVal = inner.getValue();

                String var = getVarName(outerVar, innerVar);
                result.put(var, result.getOrDefault(var, 0) + innerVal * outerVal);
            }
        }
        return result;
    }

    //Sort variables of multiplication term lexicographically
    private String getVarName(String a, String b) {
        String[] first = a.split("\\*");
        String[] second = b.split("\\*");
        String[] combine = new String[first.length + second.length];
        int k = 0;
        for (String s: first) {
            combine[k] = s;
            k++;
        }
        for (String s: second) {
            combine[k] = s;
            k++;
        }

        Arrays.sort(combine);
        StringBuilder sb = new StringBuilder();
        for (String s: combine) {
            if (s.equals("")) {
                continue;
            }
            sb.append(s).append("*");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    //Look at current char without advancing index
    private char peek() {
        while(i < chars.length && chars[i] == ' ') {
            i++;
        }
        if (i >= chars.length) {
            return 0;
        }
        return chars[i];
    }

    //Get current char and advance index
    private char getNext() {
        if (i >= chars.length) {
            return 0;
        }
        return chars[i++];
    }

    private int number() {
        int result = 0;
        while (peek() >= '0' && peek() <= '9') {
            result = result * 10 + (getNext() - '0');
        }
        return result;
    }

    private String var() {
        String result = "";
        StringBuilder sb = new StringBuilder();
        while (peek() >= 'a' && peek() <= 'z') {
            sb.append(getNext());
        }
        return sb.toString();
    }

    private Map<String, Integer> factor() {
        Map<String, Integer> result = new HashMap<>();
        result.put("", 1);
        if (peek() == '-'){
            result.put("", -1);
            getNext();
        }
        if (peek() == '('){
            getNext();
            result = multiply(result, expression());
            getNext();
        }
        else if (peek() >= '0' && peek() <= '9') {
            Map<String, Integer> num = new HashMap<>();
            num.put("", number());
            result = multiply(result, num);
        }
        else if (peek() >= 'a' && peek() <= 'z') {
            String var = var();
            if (vars.containsKey(var)) {
                Map<String, Integer> num = new HashMap<>();
                num.put("", vars.get(var));
                result = multiply(result, num);
            }
            else {
                Map<String, Integer> num = new HashMap<>();
                num.put(var, 1);
                result = multiply(result, num);
            }
        }
        return result;
    }

    private Map<String, Integer> term() {
        Map<String, Integer> result = factor();
        while (peek() == '*'){
            if (getNext() == '*') {
                result = multiply(result, factor());
            }
        }
        return result;
    }

    private Map<String, Integer> expression() {
        Map<String, Integer> result = new HashMap<>();
        result.put("", 0);
        if (peek() != '+' || peek() != '-') {
            result = add(result, term(), false);
        }
        while (peek() == '+' || peek() == '-') {
            if (getNext() == '+') {
                result = add(result, term(), false);
            }
            else {
                result = add(result, term(), true);
            }
        }
        return result;
    }

    // S2:
    HashMap<String, Integer> map;
    Stack<List<Item>> num;
    Stack<Character> op;
    public List<String> basicCalculatorIV2(String expression, String[] evalvars, int[] evalints) {
        map = new HashMap<>();
        num = new Stack<>();
        op = new Stack<>();

        int n = evalvars.length;
        for (int i = 0; i < n; i++) map.put(evalvars[i], evalints[i]);
        List<Item> t = calc(expression);
        List<String> res = new ArrayList<>();
        for (Item item : t) res.add(item.toString());
        return res;
    }

    private List<Item> add(List<Item> a, List<Item> b, int sign) {
        List<Item> res = new ArrayList<>();
        int i = 0, j = 0;
        while (i < a.size() && j < b.size()) { // 二路归并
            if (a.get(i).equals(b.get(j))) {
                Item t = new Item(a.get(i).c + b.get(j).c * sign, a.get(i).vars);
                if (t.c != 0) res.add(t); // 常数项存在
                i++;
                j++;
            } else if (a.get(i).compareTo(b.get(j)) < 0) res.add(a.get(i++));
            else {
                res.add(new Item(b.get(j).c * sign, b.get(j).vars));
                j++;
            }
        }
        while (i < a.size()) res.add(a.get(i++));
        while (j < b.size()) {
            res.add(new Item(b.get(j).c * sign, b.get(j).vars));
            j++;
        }
        return res;
    }

    private List<Item> mul(List<Item> a, List<Item> b) {
        List<Item> res = new ArrayList<>();
        for (Item x : a) {
            List<Item> items = new ArrayList<>();
            for (Item y : b) {
                Item t = new Item(x.c * y.c, new ArrayList<>());
                t.vars.addAll(x.vars);
                t.vars.addAll(y.vars);
                Collections.sort(t.vars);
                items.add(t);
            }
            res = add(res, items, 1);
        }
        return res;
    }

    private void eval() {
        List<Item> b = num.pop(), a = num.pop();
        char c = op.pop();
        List<Item> x = new ArrayList<>();
        if (c == '+') x = add(a, b, 1);
        else if (c == '-') x = add(a, b, -1);
        else x = mul(a, b);
        num.push(x);
    }

    private List<Item> calc(String s) {
        HashMap<Character, Integer> pr = new HashMap<>();
        pr.put('+', 1);
        pr.put('-', 1);
        pr.put('*', 2);

        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == ' ') continue;
            if (Character.isLowerCase(c) || Character.isDigit(c)) {
                List<Item> items = new ArrayList<>();
                if (Character.isLowerCase(c)) {
                    StringBuilder sb = new StringBuilder();
                    int j = i;
                    while (j < n && Character.isLowerCase(s.charAt(j))) sb.append(s.charAt(j++));
                    String var = sb.toString();
                    i = j - 1;
                    if (map.containsKey(var)) {
                        if (map.get(var) != 0) items.add(new Item(map.get(var), new ArrayList<>()));
                    }
                    else items.add(new Item(1, Arrays.asList(var)));
                } else { // digit
                    int j = i;
                    while (j < n && Character.isDigit(s.charAt(j))) j++;
                    int x = Integer.parseInt(s.substring(i, j));
                    i = j - 1;
                    if (x != 0) items.add(new Item(x, new ArrayList<>()));
                }
                num.push(items);
            } else if (c == '(') op.push(c);
            else if (c == ')') {
                while (!op.isEmpty() && op.peek() != '(') eval();
                op.pop();
            } else {
                while (!op.isEmpty() && op.peek() != '(' && pr.get(op.peek()) >= pr.get(c)) eval();
                op.push(c);
            }
        }
        while (!op.isEmpty()) eval();
        return num.peek();
    }

    private class Item {
        int c;
        List<String> vars;
        public Item(int c, List<String> vars) {
            this.c = c;
            this.vars = vars;
        }

        @Override
        public String toString() {
            String res = String.valueOf(c);
            for (String var : vars) res += "*" + var;
            return res;
        }

        public boolean equals(Item that) {
            if (this.vars.size() != that.vars.size()) return false;
            for (int i = 0; i < this.vars.size(); i++) {
                if (!this.vars.get(i).equals(that.vars.get(i))) return false;
            }
            return true;
        }

        public int compareTo(Item that) {
            if (this.vars.size() > that.vars.size()) return -1;
            if (this.vars.size() < that.vars.size()) return 1;
            for (int i = 0; i < this.vars.size(); i++) {
                int x = this.vars.get(i).compareTo(that.vars.get(i));
                if (x != 0) return x;
            }
            return 0;
        }
    }
}
/**
 * 把其中的每一项换成了一个多项式
 * (2+2) * (3-1) =>
 * (x + 2) * (y - 3)
 * (f1+f2) * (f3+f4)
 * f1 = x
 * f2 = 2
 * f3 = y
 * f4 = -3
 * +-* 去实现一个多项式的+-*即可
 * f5 = f1 + f2
 * f6 = f3 + f4
 * f5 * f6 = f7
 * 本质上还是个表达式求值的问题
 * 写的时候把模板稍微扩展下
 * 存多项式
 */