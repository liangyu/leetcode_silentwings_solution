package LC2101_2400;
import java.util.*;
public class LC2197_ReplaceNonCoprimeNumbersinArray {
    /**
     * You are given an array of integers nums. Perform the following steps:
     *
     * Find any two adjacent numbers in nums that are non-coprime.
     * If no such numbers are found, stop the process.
     * Otherwise, delete the two numbers and replace them with their LCM (Least Common Multiple).
     * Repeat this process as long as you keep finding two adjacent non-coprime numbers.
     * Return the final modified array. It can be shown that replacing adjacent non-coprime numbers in any arbitrary
     * order will lead to the same result.
     *
     * The test cases are generated such that the values in the final array are less than or equal to 108.
     *
     * Two values x and y are non-coprime if GCD(x, y) > 1 where GCD(x, y) is the Greatest Common Divisor of x and y.
     *
     * Input: nums = [6,4,3,2,7,6,2]
     * Output: [12,7,6]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * The test cases are generated such that the values in the final array are less than or equal to 108.
     * @param nums
     * @return
     */
    // S1: LinkedList
    // time = O(nlogn), space = O(n)
    public List<Integer> replaceNonCoprimes(int[] nums) {
        List<Integer> res = new LinkedList<>();
        for (int x : nums) {
            while (true) {
                int last = res.isEmpty() ? 1 : res.get(res.size() - 1);
                int val = helper(last, x);
                if (val == 1) break;
                x *= res.get(res.size() - 1) / val;
                res.remove(res.size() - 1);
            }
            res.add(x);
        }
        return res;
    }

    private int helper(int a, int b) {
        return b == 0 ? a : helper(b, a % b);
    }

    // S2: stack
    // time = O(nlogn), space = O(n)
    public List<Integer> replaceNonCoprimes2(int[] nums) {
        List<Integer> res = new LinkedList<>();
        int n = nums.length;
        if (n == 1) {
            res.add(nums[0]);
            return res;
        }

        Stack<Long> stack1 = new Stack<>();
        Stack<Long> stack2 = new Stack<>();
        for (int i = n - 1; i >= 0; i--) stack1.push((long) nums[i]);

        int size = stack1.size(), time = 0;
        boolean flag = true;
        while (flag && stack1.size() > 1 || !flag && stack2.size() > 1) {
            long a = flag ? stack1.pop() : stack2.pop();
            long b = flag ? stack1.pop() : stack2.pop();
            long c = 0;
            if (gcd(a, b) > 1) {
                c = lcm(a, b);
                if (flag) stack1.push(c);
                else stack2.push(c);
            } else {
                if (flag) stack2.push(a);
                else stack1.push(a);
                if (flag) {
                    if (stack1.size() > 0) stack1.push(b);
                    else stack2.push(b);
                } else {
                    if (stack2.size() > 0) stack2.push(b);
                    else stack1.push(b);
                }
            }
            if (flag && stack1.size() == 1) stack2.push(stack1.pop());
            if (!flag && stack2.size() == 1) stack1.push(stack2.pop());
            if (flag && stack1.size() == 0) {
                // while (!stack2.isEmpty()) stack1.push(stack2.pop());
                if (stack2.size() == size) break;
                time++;
                flag = !flag;
                size = stack2.size();
            } else if (!flag && stack2.size() == 0) {
                if (stack1.size() == size) break;
                time++;
                flag = !flag;
                size = stack1.size();
            }
        }

        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            long val = !stack1.isEmpty() ? stack1.pop() : stack2.pop();
            res.add((int) val);
        }

        while (flag && !stack1.isEmpty()) {
            long val = stack1.pop();
            res.add((int) val);
        }

        if (time % 2 == 0) Collections.reverse(res);
        return res;
    }

    private long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private long lcm(long a, long b) {
        long g = gcd(a, b);
        return a * b / g;
    }
}
