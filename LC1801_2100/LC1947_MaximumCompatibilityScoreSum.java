package LC1801_2100;
import java.util.*;
public class LC1947_MaximumCompatibilityScoreSum {
    /**
     * There is a survey that consists of n questions where each question's answer is either 0 (no) or 1 (yes).
     *
     * The survey was given to m students numbered from 0 to m - 1 and m mentors numbered from 0 to m - 1. The answers
     * of the students are represented by a 2D integer array students where students[i] is an integer array that
     * contains the answers of the ith student (0-indexed). The answers of the mentors are represented by a 2D integer
     * array mentors where mentors[j] is an integer array that contains the answers of the jth mentor (0-indexed).
     *
     * Each student will be assigned to one mentor, and each mentor will have one student assigned to them. The
     * compatibility score of a student-mentor pair is the number of answers that are the same for both the student
     * and the mentor.
     *
     * For example, if the student's answers were [1, 0, 1] and the mentor's answers were [0, 0, 1], then their
     * compatibility score is 2 because only the second and the third answers are the same.
     * You are tasked with finding the optimal student-mentor pairings to maximize the sum of the compatibility scores.
     *
     * Given students and mentors, return the maximum compatibility score sum that can be achieved.
     *
     * Input: students = [[1,1,0],[1,0,1],[0,0,1]], mentors = [[1,0,0],[0,0,1],[1,1,0]]
     * Output: 8
     *
     * Constraints:
     *
     * m == students.length == mentors.length
     * n == students[i].length == mentors[j].length
     * 1 <= m, n <= 8
     * students[i][k] is either 0 or 1.
     * mentors[j][k] is either 0 or 1.
     * @param students
     * @param mentors
     * @return
     */
    // time = O(n! * n^2), space = O(n)
    private int res = 0;
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        HashSet<Integer> visited = new HashSet<>();
        for (int i = 0; i < mentors.length; i++) {
            visited.add(i);
            dfs(students, 0, mentors, i, match(students[0], mentors[i]), visited);
            visited.remove(i);
        }
        return res;
    }

    private void dfs(int[][] students, int s, int[][] mentors, int m, int sum, HashSet<Integer> visited) {
        // base case
        if (s == students.length - 1) {
            res = Math.max(res, sum);
            return;
        }

        for (int i = 0; i < mentors.length; i++) {
            if (!visited.contains(i)) {
                visited.add(i);
                dfs(students, s + 1, mentors, i, sum + match(students[s + 1], mentors[i]), visited);
                visited.remove(i);
            }
        }
    }

    private int match(int[] student, int[] mentor) { // O(n)
        int count = 0;
        for (int i = 0; i < student.length; i++) {
            if (student[i] == mentor[i]) count++;
        }
        return count;
    }
}
