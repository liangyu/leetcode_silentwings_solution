package LC1801_2100;

public class LC2073_TimeNeededtoBuyTickets {
    /**
     * There are n people in a line queuing to buy tickets, where the 0th person is at the front of the line and the
     * (n - 1)th person is at the back of the line.
     *
     * You are given a 0-indexed integer array tickets of length n where the number of tickets that the ith person would
     * like to buy is tickets[i].
     *
     * Each person takes exactly 1 second to buy a ticket. A person can only buy 1 ticket at a time and has to go back
     * to the end of the line (which happens instantaneously) in order to buy more tickets. If a person does not have
     * any tickets left to buy, the person will leave the line.
     *
     * Return the time taken for the person at position k (0-indexed) to finish buying tickets.
     *
     * Input: tickets = [2,3,2], k = 2
     * Output: 6
     *
     * Constraints:
     *
     * n == tickets.length
     * 1 <= n <= 100
     * 1 <= tickets[i] <= 100
     * 0 <= k < n
     * @param tickets
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int timeRequiredToBuy(int[] tickets, int k) {
        int n = tickets.length, diff = 0, count = 0;
        int total = tickets[k] * n;
        for (int i = 0; i < n; i++) {
            if (tickets[i] < tickets[k]) {
                diff += tickets[k] - tickets[i];
                if (i > k) count++;
            }
        }
        return total - diff - (n - 1 - k - count);
    }
}
