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
}
