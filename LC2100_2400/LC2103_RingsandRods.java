package LC2100_2400;
import java.util.*;
public class LC2103_RingsandRods {
    /**
     * There are n rings and each ring is either red, green, or blue. The rings are distributed across ten rods labeled
     * from 0 to 9.
     *
     * You are given a string rings of length 2n that describes the n rings that are placed onto the rods. Every two
     * characters in rings forms a color-position pair that is used to describe each ring where:
     *
     * The first character of the ith pair denotes the ith ring's color ('R', 'G', 'B').
     * The second character of the ith pair denotes the rod that the ith ring is placed on ('0' to '9').
     * For example, "R3G2B1" describes n == 3 rings: a red ring placed onto the rod labeled 3, a green ring placed onto
     * the rod labeled 2, and a blue ring placed onto the rod labeled 1.
     *
     * Return the number of rods that have all three colors of rings on them.
     *
     * Input: rings = "B0B6G0R6R0R6G9"
     * Output: 1
     *
     * Constraints:
     *
     * rings.length == 2 * n
     * 1 <= n <= 100
     * rings[i] where i is even is either 'R', 'G', or 'B' (0-indexed).
     * rings[i] where i is odd is a digit from '0' to '9' (0-indexed).
     * @param rings
     * @return
     */
    // time = O(n), space = O(n)
    public int countPoints(String rings) {
        HashSet<Character>[] rods = new HashSet[10];
        for (int i = 0; i < 10; i++) rods[i] = new HashSet<>();

        for (int i = 0; i < rings.length(); i += 2) {
            char color = rings.charAt(i);
            int idx = rings.charAt(i + 1) - '0';
            rods[idx].add(color);
        }

        int count = 0;
        for (int i = 0; i < 10; i++) {
            if (rods[i].size() == 3) count++;
        }
        return count;
    }
}
