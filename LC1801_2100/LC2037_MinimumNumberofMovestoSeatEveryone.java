package LC1801_2100;
import java.util.*;
public class LC2037_MinimumNumberofMovestoSeatEveryone {
    /**
     * There are n seats and n students in a room. You are given an array seats of length n, where seats[i] is the
     * position of the ith seat. You are also given the array students of length n, where students[j] is the position
     * of the jth student.
     *
     * You may perform the following move any number of times:
     *
     * Increase or decrease the position of the ith student by 1 (i.e., moving the ith student from position x to x + 1
     * or x - 1)
     * Return the minimum number of moves required to move each student to a seat such that no two students are in the
     * same seat.
     *
     * Note that there may be multiple seats or students in the same position at the beginning.
     *
     * Input: seats = [3,1,5], students = [2,7,4]
     * Output: 4
     *
     * Constraints:
     *
     * n == seats.length == students.length
     * 1 <= n <= 100
     * 1 <= seats[i], students[j] <= 100
     * @param seats
     * @param students
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort(seats);
        Arrays.sort(students);

        int n = seats.length, res = 0;
        for (int i = 0; i < n; i++) {
            res += Math.abs(students[i] - seats[i]);
        }
        return res;
    }
}
