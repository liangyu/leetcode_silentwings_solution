package LC601_900;
import java.util.*;
public class LC636_ExclusiveTimeofFunctions {
    /**
     * On a single-threaded CPU, we execute a program containing n functions. Each function has a unique ID between
     * 0 and n-1.
     *
     * Function calls are stored in a call stack: when a function call starts, its ID is pushed onto the stack, and when
     * a function call ends, its ID is popped off the stack. The function whose ID is at the top of the stack is the
     * current function being executed. Each time a function starts or ends, we write a log with the ID, whether it
     * started or ended, and the timestamp.
     *
     * You are given a list logs, where logs[i] represents the ith log message formatted as a string
     * "{function_id}:{"start" | "end"}:{timestamp}". For example, "0:start:3" means a function call with function ID 0
     * started at the beginning of timestamp 3, and "1:end:2" means a function call with function ID 1 ended at the end
     * of timestamp 2. Note that a function can be called multiple times, possibly recursively.
     *
     * A function's exclusive time is the sum of execution times for all function calls in the program. For example, if
     * a function is called twice, one call executing for 2 time units and another call executing for 1 time unit, the
     * exclusive time is 2 + 1 = 3.
     *
     * Return the exclusive time of each function in an array, where the value at the ith index represents the exclusive
     * time for the function with ID i.
     *
     * Input: n = 2, logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
     * Output: [3,4]
     *
     * Constraints:
     *
     * 1 <= n <= 100
     * 1 <= logs.length <= 500
     * 0 <= function_id < n
     * 0 <= timestamp <= 10^9
     * No two start events will happen at the same timestamp.
     * No two end events will happen at the same timestamp.
     * Each function has an "end" log for each "start" log.
     * @param n
     * @param logs
     * @return
     */
    // time = O(n), space = O(n)
    public int[] exclusiveTime(int n, List<String> logs) {
        Stack<int[]> stack = new Stack<>();
        int[] res = new int[n];

        for (String s : logs) {
            String[] strs = s.split(":");
            int id = Integer.parseInt(strs[0]);
            boolean flag = strs[1].equals("start") ? true : false;
            int timestamp = Integer.parseInt(strs[2]);

            if (flag) stack.push(new int[]{id, timestamp});
            else {
                int start = stack.pop()[1];
                int duration = timestamp - start + 1;
                res[id] += duration;

                if (!stack.isEmpty()) {
                    int prevId = stack.peek()[0];
                    res[prevId] -= duration; // 处理完一个就去掉
                }
            }
        }
        return res;
    }
}
/**
 * 看到这种先要处理一个东西，没处理完就处理另一个东西，回头再来处理 -> stack
 */
