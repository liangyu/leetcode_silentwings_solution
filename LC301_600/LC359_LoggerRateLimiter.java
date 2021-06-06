package LC301_600;
import java.util.*;
public class LC359_LoggerRateLimiter {
    /**
     * Design a logger system that receives a stream of messages along with their timestamps. Each unique message
     * should only be printed at most every 10 seconds (i.e. a message printed at timestamp t will prevent other
     * identical messages from being printed until timestamp t + 10).
     *
     * All messages will come in chronological order. Several messages may arrive at the same timestamp.
     *
     * Implement the Logger class:
     *
     * Logger() Initializes the logger object.
     * bool shouldPrintMessage(int timestamp, string message) Returns true if the message should be printed in the
     * given timestamp, otherwise returns false.
     *
     * Input
     * ["Logger", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage",
     * "shouldPrintMessage", "shouldPrintMessage"]
     * [[], [1, "foo"], [2, "bar"], [3, "foo"], [8, "bar"], [10, "foo"], [11, "foo"]]
     * Output
     * [null, true, true, false, false, false, true]
     *
     * Constraints:
     *
     * 0 <= timestamp <= 10^9
     * Every timestamp will be passed in non-decreasing order (chronological order).
     * 1 <= message.length <= 30
     * At most 104 calls will be made to shouldPrintMessage.
     */
    /** Initialize your data structure here. */
    // time = O(1), space = O(n)
    private HashMap<String, Integer> map;
    public LC359_LoggerRateLimiter() {
        map = new HashMap<>();
    }

    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
     If this method returns false, the message will not be printed.
     The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {
        if (map.containsKey(message)) {
            if (map.get(message) > timestamp) return false;
        }
        map.put(message, timestamp + 10);
        return true;
    }
}
