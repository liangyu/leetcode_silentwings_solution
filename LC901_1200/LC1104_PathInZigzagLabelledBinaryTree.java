package LC901_1200;
import java.util.*;
public class LC1104_PathInZigzagLabelledBinaryTree {
    /**
     * In an infinite binary tree where every node has two children, the nodes are labelled in row order.
     *
     * In the odd numbered rows (ie., the first, third, fifth,...), the labelling is left to right, while in the even
     * numbered rows (second, fourth, sixth,...), the labelling is right to left.
     *
     * Given the label of a node in this tree, return the labels in the path from the root of the tree to the node with
     * that label.
     *
     * Input: label = 14
     * Output: [1,3,4,14]
     *
     * Constraints:
     *
     * 1 <= label <= 10^6
     * @param label
     * @return
     */
    public List<Integer> pathInZigZagTree(int label) {
        List<Integer> res = new LinkedList<>();
        while (label > 0) {
            res.add(0, label);
            int n = (int) (Math.log(label) / Math.log(2));
            label = (int) (Math.pow(2, n) - 1 + Math.pow(2, n - 1) - label / 2);
        }
        return res;
    }
}
/**
 * 最底层的初始节点是label，它的层级编号就是n = log2(label)
 * 第n-1层的节点编号范围就是pow(2, n-1)到pow(2, n) -1
 * 不管是否顺序逆序，label的父节点不是label/2，而是label/2在该行节点编号中的中轴对称位置。
 * 令p是label/2的对称位置，那么由对称性肯定有label/2-pow(2,n-1) = pow(2,n)-1 - p，即有p = pow(2,n)-1 - label/2 + pow(2,n-1).
 */