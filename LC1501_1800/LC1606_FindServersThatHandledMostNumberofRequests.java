package LC1501_1800;
import java.util.*;
public class LC1606_FindServersThatHandledMostNumberofRequests {
    /**
     * You have k servers numbered from 0 to k-1 that are being used to handle multiple requests simultaneously. Each
     * server has infinite computational capacity but cannot handle more than one request at a time. The requests are
     * assigned to servers according to a specific algorithm:
     *
     * The ith (0-indexed) request arrives.
     * If all servers are busy, the request is dropped (not handled at all).
     * If the (i % k)th server is available, assign the request to that server.
     * Otherwise, assign the request to the next available server (wrapping around the list of servers and starting
     * from 0 if necessary). For example, if the ith server is busy, try to assign the request to the (i+1)th server,
     * then the (i+2)th server, and so on.
     * You are given a strictly increasing array arrival of positive integers, where arrival[i] represents the arrival
     * time of the ith request, and another array load, where load[i] represents the load of the ith request (the time
     * it takes to complete). Your goal is to find the busiest server(s). A server is considered busiest if it handled
     * the most number of requests successfully among all the servers.
     *
     * Return a list containing the IDs (0-indexed) of the busiest server(s). You may return the IDs in any order.
     *
     * Input: k = 3, arrival = [1,2,3,4,5], load = [5,2,3,3,3]
     * Output: [1]
     *
     * Constraints:
     *
     * 1 <= k <= 10^5
     * 1 <= arrival.length, load.length <= 10^5
     * arrival.length == load.length
     * 1 <= arrival[i], load[i] <= 10^9
     * arrival is strictly increasing.
     * @param k
     * @param arrival
     * @param load
     * @return
     */
    // time = O((n + k) * logk), space = O(n + k)
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        List<Integer> res = new ArrayList<>();
        int n = arrival.length;
        PriorityQueue<int[]> busy = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        TreeSet<Integer> free = new TreeSet<>();
        for (int i = 0; i < k; i++) free.add(i); // O(klogk)

        int[] count = new int[k];
        for (int i = 0; i < n; i++) { // O(nlogk)
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) free.add(busy.poll()[1]);
            if (free.isEmpty()) continue;
            Integer ck = free.ceiling(i % k);
            if (ck == null) ck = free.first();
            count[ck]++;
            free.remove(ck);
            busy.offer(new int[]{arrival[i] + load[i], ck});
        }

        int maxCount = 0;
        for (int i = 0; i < k; i++) {
            if (count[i] >= maxCount) {
                if (count[i] > maxCount) {
                    maxCount = count[i];
                    res = new ArrayList<>();
                }
                res.add(i);
            }
        }
        return res;
    }
}
/**
 * 模拟过程
 * 把所有的服务器都存下来
 * free: 0,...,n-1
 * arrival[i]: 优先找i%k => O(logn)
 * if (free.find(>=i%k))
 *      ...
 * else
 *      free[0]
 * free.erase(id)
 * busy: {expected finish time, server id} ... => pq
 */