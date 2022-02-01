package LC901_1200;
import java.util.*;
public class LC1146_SnapshotArray {
    /**
     * Implement a SnapshotArray that supports the following interface:
     *
     * SnapshotArray(int length) initializes an array-like data structure with the given length.  Initially, each
     * element equals 0.
     * void set(index, val) sets the element at the given index to be equal to val.
     * int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap()
     * minus 1.
     * int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given
     * snap_id
     *
     * Input: ["SnapshotArray","set","snap","set","get"]
     * [[3],[0,5],[],[0,6],[0,0]]
     * Output: [null,null,0,null,5]
     *
     * Constraints:
     *
     * 1 <= length <= 50000
     * At most 50000 calls will be made to set, snap, and get.
     * 0 <= index < length
     * 0 <= snap_id < (the total number of times we call snap())
     * 0 <= val <= 10^9
     * @param length
     */
    TreeMap<Integer, Integer>[] snaps;
    int snapId = 0;
    public LC1146_SnapshotArray(int length) {
        snaps = new TreeMap[length];
        for (int i = 0; i < length; i++) snaps[i] = new TreeMap<>();
    }

    // time = O(logn), space = O(n)
    public void set(int index, int val) {
        snaps[index].put(snapId, val);
    }

    // time = O(1), space = O(1)
    public int snap() {
        return snapId++;
    }

    // time = O(logn), space = O(n)
    public int get(int index, int snap_id) {
        Integer idx = snaps[index].floorKey(snap_id);
        return idx == null ? 0 : snaps[index].get(idx);
    }
}
/**
 * snaps[index] = [{snap_id, val}  eg.{{0,1},{1,2},{2,2},{3,4},{4,4},...}
 * 50000 * 50000
 * 如何更高效的来实现这个系统呢?
 * 可能两次快照之间数据没有变化，就造成了冗余
 * 如果数据没有变动，就没有必要把没变动的数据用快照存下来
 * 每次拍快照不需要存每个元素的值，只要存那些被改变过的值
 * 每两次快照之间，那些被set过的值就是被改变过的值 => set
 * ref: LC1724
 */