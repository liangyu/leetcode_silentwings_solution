package LC601_900;

public class LC780_ReachingPoints {
    /**
     * Given four integers sx, sy, tx, and ty, return true if it is possible to convert the point (sx, sy) to the point
     * (tx, ty) through some operations, or false otherwise.
     *
     * The allowed operation on some point (x, y) is to convert it to either (x, x + y) or (x + y, y).
     *
     * Input: sx = 1, sy = 1, tx = 3, ty = 5
     * Output: true
     *
     * Constraints:
     *
     * 1 <= sx, sy, tx, ty <= 10^9
     * @param sx
     * @param sy
     * @param tx
     * @param ty
     * @return
     */
    // time = O(log(max(tx,ty))), space = O(1)
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        if (sx > tx || sy > ty) return false;
        if (sx == tx) return (ty - sy) % sx == 0; // sx无法再更改了，只能在sy基础上不断增量sx直至看是否能叨叨ty
        if (sy == ty) return (tx - sx) % sy == 0; // 增长的幅度是否是整数倍

        if (tx > ty) return reachingPoints(sx, sy, tx % ty, ty);
        else if (tx < ty) return reachingPoints(sx, sy, tx, ty % tx);
        return false;
    }
}
/**
 * 都是正数，值总是在不停变大的。
 * if tx > ty => 最后一步做的是(x+y, x),反之做的是(x, x+y)
 * if tx == ty => return false
 * 本题的突破点在于，x和y都是正数！于是，当我们发现tx>ty时，我们一定可以推断出，其之前的状态必须是(tx-ty,ty)并且执行了(x+y,y)的操作；
 * 这是因为如果执行了另一种操作，其结果不可能使得第一个分量大于第二个分量。
 * 同理，如果我们发现tx<ty时，我们一定可以推断出，其之前的状态必须是(tx,ty-tx)并且执行了(x,y+x)的操作。
 * 那么如果tx==ty呢？这是不可能的，因为(x,x+y)或者(x+y,y)都不可能有两个相等的分量。
 * if (tx>ty) return reachingPoints(sx,sy,tx-ty,ty);
 *   else if (tx<ty) return reachingPoints(sx,sy,tx,ty-tx);
 *   else return false;
 * 我们可以再做进一步的优化。
 * 当tx>ty时，我们知道其之前的状态是(tx-ty,ty)。那么如果此时依然有tx-ty>ty呢？
 * 同理推出，再之前的状态就是(tx-2ty,ty)。
 * 那么如果此时依然有tx-2ty>ty呢？
 * 同理推出，再之前的状态就是(tx-3ty,ty)...我们就可以一直进行下去，直至第一个分量小于第二个分量。
 * 而这个终结的状态，其实就是(tx%ty,ty)!
 * 于是效率更高的递归方法是：
 *   if (tx>ty) return reachingPoints(sx,sy,tx%ty,ty);
 *   else if (tx<ty) return reachingPoints(sx,sy,tx,ty%tx);
 *   else return false;
 */