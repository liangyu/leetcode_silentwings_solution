package LC1501_1800;
import java.util.*;
public class LC1622_FancySequence {
    /**
     * Write an API that generates fancy sequences using the append, addAll, and multAll operations.
     *
     * Implement the Fancy class:
     *
     * Fancy() Initializes the object with an empty sequence.
     * void append(val) Appends an integer val to the end of the sequence.
     * void addAll(inc) Increments all existing values in the sequence by an integer inc.
     * void multAll(m) Multiplies all existing values in the sequence by an integer m.
     * int getIndex(idx) Gets the current value at index idx (0-indexed) of the sequence modulo 109 + 7. If the index is
     * greater or equal than the length of the sequence, return -1.
     *
     * Input
     * ["Fancy", "append", "addAll", "append", "multAll", "getIndex", "addAll", "append", "multAll", "getIndex",
     * "getIndex", "getIndex"]
     * [[], [2], [3], [7], [2], [0], [3], [10], [2], [0], [1], [2]]
     * Output
     * [null, null, null, null, null, 10, null, null, null, 26, 34, 20]
     *
     * Constraints:
     *
     * 1 <= val, inc, m <= 100
     * 0 <= idx <= 10^5
     * At most 105 calls total will be made to append, addAll, multAll, and getIndex.
     */
    long mul, add;
    long M = (long)(1e9 + 7);
    List<Long> nums;
    public LC1622_FancySequence() {
        mul = 1;
        add = 0;
        nums = new ArrayList<>();
    }

    // time = O(logn), space = O(n)
    public void append(int val) {
        nums.add((val - add + M) % M * inv(mul) % M);
    }

    // time = O(1), space = O(1)
    public void addAll(int inc) {
        add += inc;
        add %= M;
    }

    // time = O(1), space = O(1)
    public void multAll(int m) {
        mul *= m;
        add *= m;
        mul %= M;
        add %= M;
    }

    // time = O(1), space = O(n)
    public int getIndex(int idx) {
        if (idx >= nums.size()) return -1;
        return (int)((nums.get(idx) * mul + add) % M);
    }

    private long quickPow(long x, long y) {
        long res = 1, cur = x;
        while (y > 0) {
            if ((y & 1) != 0) res = res * cur % M;
            cur = cur * cur % M;
            y >>= 1;
        }
        return res;
    }

    private long inv(long x) {
        return quickPow(x, M - 2);
    }
}
/**
 * inverse element
 * 0 1 2 3 4 5 6 7 8 9    i ...
 *  *       + * * +
 *  a       b c d e
 * getIdx(0) = ((((((nums[0]*a)+b)*c)*d)+e) = nums[0]*mul + add
 * getIdx(1) = ((((((nums[1]*a)+b)*c)*d)+e) = nums[1]*mul + add
 * getIdx(2) = (((((nums[2]+b)*c)*d)+e)
 *           = ((((((val*a)+b)*c)*d)+e)
 *      val = nums[2]/a
 * 先抵消掉之前的一部分效果，再apply
 * before appending(nums[i]),
 * {mul, add}
 * append val, s.t. val * mul + add = nums[i]
 * {mul', add'}
 * getIdx(i) => val*mul'+add'
 * 事实上，我们并不需要存一个真实的val，我们只要存一个与val关于M同余的值就行了
 * append: val = (nums[i]-add) / mul
 * val' = （nums[i]-add)*inv(mul)
 * val' % M == val % M
 * val' * k % M == val * k % M  => 这样可能就是一个整数了
 * 对于同余而言，并不care是小数还是整数，小数的时候依然可以定义"同余"这个概念的。
 * ((nums[i]-add)/mul)%M == (nums[i]-add)%M /mul/M   (X)
 * "逆元" (inverse element)
 * if x / a ≡ x * b (mod M) 同余
 * b = inv(a)
 * a = inv(b)
 * 充要条件 <=> a and M 互质
 * 一般来说，M 已经是质数了，所以a与M一定互质
 */
