# LC1570 Dot Product of Two Sparse Vectors

标签（空格分隔）： LeetCode Java HashTable

---
    /**
     * Given two sparse vectors, compute their dot product.
     *
     * Implement class SparseVector:
     *
     * SparseVector(nums) Initializes the object with the vector nums
     * dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
     * A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently
     * and compute the dot product between two SparseVector.
     *
     * Follow up: What if only one of the vectors is sparse?
     *
     * Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
     * Output: 8
     *
     * Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
     * Output: 6
     *
     * Constraints:
     *
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * 0 <= nums1[i], nums2[i] <= 100
     *
     * @param nums
     */
     
【难点误区】

构造一个HashMap在vector之中，然后只存入非0的值，再遍历其中一个，在另一个的map里去对应看是否有相应的非0值与之相乘。


【解题思路】

本题可以非常直观的想到对于稀疏型数据结构来说，只要用HashMap来存储非0元素的index以及对应的value即可。然后点乘的话，只有两个vec对应的index上都非0，即都在各自的HashMap里有值才能对res有所贡献，否则一方为0，乘积肯定也为0， 对res毫无贡献。



```java     
class SparseVector {
    // time = O(n), space = O(k) k: the number of non-zero elements
    private HashMap<Integer, Integer> map;
    SparseVector(int[] nums) {
        map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                map.put(i, nums[i]);
            }
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        int res = 0;
        for (int key : this.map.keySet()) {
            if (vec.map.containsKey(key)) {
                res += this.map.get(key) * vec.map.get(key);
            }
        }
        return res;
    }
}

// Your SparseVector object will be instantiated and called as such:
// SparseVector v1 = new SparseVector(nums1);
// SparseVector v2 = new SparseVector(nums2);
// int ans = v1.dotProduct(v2);
```
