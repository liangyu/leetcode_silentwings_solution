package LC001_300;
import java.util.*;
public class LC251_Flatten2DVector {
    /**
     * Design an iterator to flatten a 2D vector. It should support the next and hasNext operations.
     *
     * Implement the Vector2D class:
     *
     * Vector2D(int[][] vec) initializes the object with the 2D vector vec.
     * next() returns the next element from the 2D vector and moves the pointer one step forward. You may assume that
     * all the calls to next are valid.
     * hasNext() returns true if there are still some elements in the vector, and false otherwise.
     *
     * Input
     * ["Vector2D", "next", "next", "next", "hasNext", "hasNext", "next", "hasNext"]
     * [[[[1, 2], [3], [4]]], [], [], [], [], [], [], []]
     * Output
     * [null, 1, 2, 3, true, true, 4, false]
     *
     * Constraints:
     *
     * 0 <= vec.length <= 200
     * 0 <= vec[i].length <= 500
     * -500 <= vec[i][j] <= 500
     * At most 105 calls will be made to next and hasNext.
     *
     *
     * Follow up: As an added challenge, try to code it using only iterators in C++ or iterators in Java.
     * @param vec
     */
    // time = O(1), space = O(1)
    private int[][] vector;
    private int indexList, indexElement;
    public LC251_Flatten2DVector(int[][] vec) {
        vector = vec;
    }

    public int next() {
        if (!hasNext()) throw new RuntimeException();
        return vector[indexList][indexElement++];
    }

    public boolean hasNext() {
        while (indexList < vector.length) {
            if (indexElement < vector[indexList].length) return true;
            else {
                indexList++;
                indexElement = 0;
            }
        }
        return false;
    }
}
