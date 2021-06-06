package LC1801_2100;
import java.util.*;
public class LC1842_NextPalindromeUsingSameDigits {
    /**
     * You are given a numeric string num, representing a very large palindrome.
     *
     * Return the smallest palindrome larger than num that can be created by rearranging its digits. If no such
     * palindrome exists, return an empty string "".
     *
     * A palindrome is a number that reads the same backward as forward.
     *
     * Input: num = "1221"
     * Output: "2112"
     *
     * Constraints:
     *
     * 1 <= num.length <= 10^5
     * num is a palindrome.
     * @param num
     * @return
     */
    // time = O(n), space = O(n)
    public String nextPalindrome(String num) {
        // corner case
        if (num == null || num.length() == 0) return "";

        int n = num.length();
        char[] arr = num.toCharArray();

        // find the next permutation of num[0:n/2-1]
        if (!nextPermutation(arr, n / 2 - 1)) return "";

        // copy the 1st half
        int i = 0, j = n - 1;
        while (i < j) arr[j--] = arr[i++];
        return String.valueOf(arr);
    }

    private boolean nextPermutation(char[] nums, int n) {
        // step 1: find the 1st nums[i] < nums[i + 1] from the right side
        int i = n - 1;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;

        // step 2: if such i does not exisit => nums is non-decreasing => return false
        if (i == -1) return false;

        // step 3: nums[i+1,n] is non-decreasing, find the 1st j nums[i] < nums[j] from the right side
        int left = i + 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[i]) left = mid + 1;
            else right = mid;
        }

        // step 4: swap values at index i and j, so now nums[i+1,n] is still non-decreasing
        int j = nums[left] > nums[i] ? left : left - 1; // remember to check the last spot when left == right
        swap(nums, i, j);

        // step 5: reverse nums[i+1,n]
        reverse(nums, i + 1, n);
        return true;
    }

    private void reverse(char[] nums, int i, int j) {
        while (i < j) swap(nums, i++, j--);
    }

    private void swap(char[] nums, int i, int j) {
        char temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
