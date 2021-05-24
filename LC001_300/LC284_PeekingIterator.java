package LC001_300;
import java.util.*;
public class LC284_PeekingIterator {
    /**
     * Design an iterator that supports the peek operation on a list in addition to the hasNext and the next operations.
     *
     * Implement the PeekingIterator class:
     *
     * PeekingIterator(int[] nums) Initializes the object with the given integer array nums.
     * int next() Returns the next element in the array and moves the pointer to the next element.
     * bool hasNext() Returns true if there are still elements in the array.
     * int peek() Returns the next element in the array without moving the pointer.
     *
     * Input
     * ["PeekingIterator", "next", "peek", "next", "next", "hasNext"]
     * [[[1, 2, 3]], [], [], [], [], []]
     * Output
     * [null, 1, 2, 2, 3, false]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * All the calls to next and peek are valid.
     * At most 1000 calls will be made to next, hasNext, and peek.
     *
     *
     * Follow up: How would you extend your design to be generic and work with all types, not just integer?
     */
    // time = O(1), space = O(1)
    private Iterator<Integer> iter;
    private Integer next; // 创建一个next指针
    public LC284_PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        iter = iterator;
        if (iter.hasNext()) next = iterator.next();
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return next;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        Integer res = next;
        next = iter.hasNext() ? iter.next() : null;
        return res;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }
}
