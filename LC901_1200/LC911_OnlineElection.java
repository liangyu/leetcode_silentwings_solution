package LC901_1200;
import java.util.*;
public class LC911_OnlineElection {
    /**
     * You are given two integer arrays persons and times. In an election, the ith vote was cast for persons[i] at time
     * times[i].
     *
     * For each query at a time t, find the person that was leading the election at time t. Votes cast at time t will
     * count towards our query. In the case of a tie, the most recent vote (among tied candidates) wins.
     *
     * Implement the TopVotedCandidate class:
     *
     * TopVotedCandidate(int[] persons, int[] times) Initializes the object with the persons and times arrays.
     * int q(int t) Returns the number of the person that was leading the election at time t according to the mentioned
     * rules.
     *
     * Input
     * ["TopVotedCandidate", "q", "q", "q", "q", "q", "q"]
     * [[[0, 1, 1, 0, 0, 1, 0], [0, 5, 10, 15, 20, 25, 30]], [3], [12], [25], [15], [24], [8]]
     * Output
     * [null, 0, 1, 1, 0, 0, 1]
     *
     * Constraints:
     *
     * 1 <= persons.length <= 5000
     * times.length == persons.length
     * 0 <= persons[i] < persons.length
     * 0 <= times[i] <= 109
     * times is sorted in a strictly increasing order.
     * times[0] <= t <= 109
     * At most 104 calls will be made to q.
     * @param persons
     * @param times
     */
    private int[] times;
    private List<Integer> lead;
    // time = O(nlogn), space = O(n)
    public LC911_OnlineElection(int[] persons, int[] times) {
        this.times = times;
        List<int[]> list = new ArrayList<>();
        int n = persons.length;
        for (int i = 0; i < n; i++) list.add(new int[]{times[i], persons[i]});
        Collections.sort(list, (o1, o2) -> o1[0] - o2[0]);
        Arrays.sort(times);

        int leadPerson = -1, leadVotes = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        lead = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int p = list.get(i)[1];
            map.put(p, map.getOrDefault(p, 0) + 1);
            if (map.get(p) >= leadVotes) {
                leadVotes = map.get(p);
                leadPerson = p;
            }
            lead.add(leadPerson);
        }
    }
    // time = O(logn), space = O(1)
    public int q(int t) {
        int idx = upperBound(times, t); // 第一个 <= t的时刻
        return lead.get(idx);
    }

    private int upperBound(int[] nums, int t) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid] <= t) left = mid;
            else right = mid - 1;
        }
        return nums[left] <= t ? left : left - 1;
    }
    /**
     * Your TopVotedCandidate object will be instantiated and called as such:
     * TopVotedCandidate obj = new TopVotedCandidate(persons, times);
     * int param_1 = obj.q(t);
     */
}