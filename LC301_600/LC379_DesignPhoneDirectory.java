package LC301_600;

public class LC379_DesignPhoneDirectory {
    /**
     * Design a phone directory that initially has maxNumbers empty slots that can store numbers. The directory should
     * store numbers, check if a certain slot is empty or not, and empty a given slot.
     *
     * Implement the PhoneDirectory class:
     *
     * PhoneDirectory(int maxNumbers) Initializes the phone directory with the number of available slots maxNumbers.
     * int get() Provides a number that is not assigned to anyone. Returns -1 if no number is available.
     * bool check(int number) Returns true if the slot number is available and false otherwise.
     * void release(int number) Recycles or releases the slot number.
     *
     * Input
     * ["PhoneDirectory", "get", "get", "check", "get", "check", "release", "check"]
     * [[3], [], [], [2], [], [2], [2], [2]]
     * Output
     * [null, 0, 1, true, 2, false, null, true]
     *
     * Constraints:
     *
     * 1 <= maxNumbers <= 10^4
     * 0 <= number < maxNumbers
     * At most 2 * 10^4 calls will be made to get, check, and release.
     */
    /** Initialize your data structure here
     @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    // time = O(n), space = O(n)
    int[] ne;
    int p;
    public LC379_DesignPhoneDirectory(int maxNumbers) {
        ne = new int[maxNumbers];
        for (int i = 0; i < maxNumbers; i++) ne[i] = (i + 1) % maxNumbers; // maxNumbers - 1 -> 0 -> 1 -> ...
        p = 0;
    }

    /** Provide a number which is not assigned to anyone.
     @return - Return an available number. Return -1 if none is available. */
    public int get() {
        if (ne[p] == -1) return -1; // -1 stands for "unavailable"
        int res = p;
        p = ne[p];
        ne[res] = -1;
        return res;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        return ne[number] != -1;
    }

    /** Recycle or release a number. */
    public void release(int number) {
        if (ne[number] != -1) return; // no need to release
        ne[number] = p;
        p = number;
    }
}
