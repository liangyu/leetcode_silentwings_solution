package LC001_300;
import java.util.*;
public class LC284_PeekingIterator {
    /**
     *Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator
     * that support the peek() operation -- it essentially peek() at the element that will be returned by the next
     * call to next().
     *
     * Follow up: How would you extend your design to be generic and work with all types, not just integer?
     *
     * @param iterator
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
