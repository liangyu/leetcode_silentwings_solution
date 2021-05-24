package LC001_300;
import java.util.*;
public class LC281_ZigzagIterator {
    /**
     * Given two vectors of integers v1 and v2, implement an iterator to return their elements alternately.
     *
     * Implement the ZigzagIterator class:
     *
     * ZigzagIterator(List<int> v1, List<int> v2) initializes the object with the two vectors v1 and v2.
     * boolean hasNext() returns true if the iterator still has elements, and false otherwise.
     * int next() returns the current element of the iterator and moves the iterator to the next element.
     *
     * Input: v1 = [1,2], v2 = [3,4,5,6]
     * Output: [1,3,2,4,5,6]
     *
     * Constraints:
     *
     * 0 <= v1.length, v2.length <= 1000
     * 1 <= v1.length + v2.length <= 2000
     * -2^31 <= v1[i], v2[i] <= 2^31 - 1
     *
     *
     * Follow up: What if you are given k vectors? How well can your code be extended to such cases?
     *
     * Clarification for the follow-up question:
     *
     * The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" does not look right to
     * you, replace "Zigzag" with "Cyclic".
     *
     * Example:
     *
     * Input: v1 = [1,2,3], v2 = [4,5,6,7], v3 = [8,9]
     * Output: [1,4,8,2,5,9,3,6,7]
     * @param v1
     * @param v2
     */
    // S1: Two Lists
    // time = O(n), space = O(1)
    private boolean first;
    private int idx1, idx2;
    private List<Integer> list1, list2;
    public LC281_ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        list1 = v1;
        list2 = v2;
        first = true;
        idx1 = 0;
        idx2 = 0;
    }

    public int next() {
        if ((first || idx2 >= list2.size()) && idx1 < list1.size()) {
            first = false;
            return list1.get(idx1++);
        } else {
            first = true;
            return list2.get(idx2++);
        }
    }

    public boolean hasNext() {
        return idx1 < list1.size() || idx2 < list2.size();
    }

    // S2: iterator
    // time = O(n), space = O(1)
//    private LinkedList<Iterator> list;
//    public LC281_ZigzagIterator(List<Integer> v1, List<Integer> v2) {
//        list = new LinkedList<>();
//        if (!v1.isEmpty()) list.add(v1.iterator());
//        if (!v2.isEmpty()) list.add(v2.iterator());
//    }
//
//    public int next() {
//        Iterator cur = list.remove();
//        int res = (Integer)cur.next();
//        if (cur.hasNext()) list.add(cur);
//        return res;
//    }
//
//    public boolean hasNext() {
//        return !list.isEmpty();
//    }
}
