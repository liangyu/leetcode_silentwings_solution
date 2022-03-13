package LC2101_2400;
import java.util.*;
public class LC2166_DesignBitset {
    /**
     * A Bitset is a data structure that compactly stores bits.
     *
     * Implement the Bitset class:
     *
     * Bitset(int size) Initializes the Bitset with size bits, all of which are 0.
     * void fix(int idx) Updates the value of the bit at the index idx to 1. If the value was already 1, no change
     * occurs.
     * void unfix(int idx) Updates the value of the bit at the index idx to 0. If the value was already 0, no change
     * occurs.
     * void flip() Flips the values of each bit in the Bitset. In other words, all bits with value 0 will now have value
     * 1 and vice versa.
     * boolean all() Checks if the value of each bit in the Bitset is 1. Returns true if it satisfies the condition,
     * false otherwise.
     * boolean one() Checks if there is at least one bit in the Bitset with value 1. Returns true if it satisfies the
     * condition, false otherwise.
     * int count() Returns the total number of bits in the Bitset which have value 1.
     * String toString() Returns the current composition of the Bitset. Note that in the resultant string, the character
     * at the ith index should coincide with the value at the ith bit of the Bitset.
     *
     * Input
     * ["Bitset", "fix", "fix", "flip", "all", "unfix", "flip", "one", "unfix", "count", "toString"]
     * [[5], [3], [1], [], [], [0], [], [], [0], [], []]
     * Output
     * [null, null, null, null, false, null, null, true, null, 2, "01010"]
     *
     * Constraints:
     *
     * 1 <= size <= 10^5
     * 0 <= idx <= size - 1
     * At most 10^5 calls will be made in total to fix, unfix, flip, all, one, count, and toString.
     * At least one call will be made to all, one, count, or toString.
     * At most 5 calls will be made to toString.
     */
    class Bitset {
        // time = O(n), space = O(n)
        char[] set, rev;
        int one, n;
        public Bitset(int size) {
            this.n = size;
            set = new char[n];
            rev = new char[n];
            Arrays.fill(set, '0');
            Arrays.fill(rev, '1');
        }

        public void fix(int idx) {
            if (set[idx] == '1') return;
            set[idx] = '1';
            rev[idx] = '0';
            one++;
        }

        public void unfix(int idx) {
            if (set[idx] == '0') return;
            set[idx] = '0';
            rev[idx] = '1';
            one--;
        }

        public void flip() {
            char[] temp = set;
            set = rev;
            rev = temp;
            one = n - one;
        }

        public boolean all() {
            return one == n;
        }

        public boolean one() {
            return one > 0;
        }

        public int count() {
            return one;
        }

        public String toString() {
            return String.valueOf(set);
        }
    }

    // S2
    // time = O(n), space = O(n)
    class Bitset2 {
        int[] flips;
        int totalFlip;
        int m, cnt;
        public Bitset2(int size) {
            flips = new int[size];
            this.m = size;
            this.cnt = 0;
            totalFlip = 0;
        }

        public void fix(int idx) {
            if ((flips[idx] + totalFlip) % 2 == 0) {
                flips[idx]++;
                cnt++;
            }
        }

        public void unfix(int idx) {
            if ((flips[idx] + totalFlip) % 2 == 1) {
                flips[idx]++;
                cnt--;
            }
        }

        public void flip() {
            totalFlip++;
            cnt = m - cnt;
        }

        public boolean all() {
            return cnt == m;
        }

        public boolean one() {
            return cnt >= 1;
        }

        public int count() {
            return cnt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < m; i++) {
                int t = (flips[i] + totalFlip) % 2;
                sb.append(t == 0 ? '0' : '1');
            }
            return sb.toString();
        }
    }
}
/**
 *
 */
