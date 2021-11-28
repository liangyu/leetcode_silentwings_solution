package LC1801_2100;

public class LC2086_MinimumNumberofBucketsRequiredtoCollectRainwaterfromHouses {
    /**
     * You are given a 0-indexed string street. Each character in street is either 'H' representing a house or '.'
     * representing an empty space.
     *
     * You can place buckets on the empty spaces to collect rainwater that falls from the adjacent houses. The rainwater
     * from a house at index i is collected if a bucket is placed at index i - 1 and/or index i + 1. A single bucket,
     * if placed adjacent to two houses, can collect the rainwater from both houses.
     *
     * Return the minimum number of buckets needed so that for every house, there is at least one bucket collecting
     * rainwater from it, or -1 if it is impossible.
     *
     * Input: street = "H..H"
     * Output: 2
     *
     * Input: street = ".H.H."
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= street.length <= 10^5
     * street[i] is either'H' or '.'.
     * @param street
     * @return
     */
    // time = O(n), space = O(n)
    public int minimumBuckets(String street) {
        char[] chars = street.toCharArray();
        int n = chars.length;
        if (n == 1) return chars[0] == 'H' ? -1 : 0;

        int count = 0;
        for (int i = 1; i < n - 1; i++) {
            if (chars[i] == '.' && chars[i - 1] == 'H' && chars[i + 1] == 'H') {
                chars[i] = 'B';
                chars[i - 1] = 'h';
                chars[i + 1] = 'h';
                count++;
            }
        }

        for (int i = 0; i < n; i++) {
            if (chars[i] == '.') {
                if (i - 1 >= 0 && chars[i - 1] == 'H') {
                    chars[i] = 'B';
                    chars[i - 1] = 'h';
                    count++;
                } else if (i + 1 < n && chars[i + 1] == 'H') {
                    chars[i] = 'B';
                    chars[i + 1] = 'h';
                    count++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (chars[i] == 'H') return -1;
        }
        return count;
    }
}
