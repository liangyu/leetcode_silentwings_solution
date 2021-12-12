package LC1201_1500;
import java.util.*;
public class LC1487_MakingFileNamesUnique {
    /**
     * Given an array of strings names of size n. You will create n folders in your file system such that, at the ith
     * minute, you will create a folder with the name names[i].
     *
     * Since two files cannot have the same name, if you enter a folder name which is previously used, the system will
     * have a suffix addition to its name in the form of (k), where, k is the smallest positive integer such that the
     * obtained name remains unique.
     *
     * Return an array of strings of length n where ans[i] is the actual name the system will assign to the ith folder
     * when you create it.
     *
     * Input: names = ["pes","fifa","gta","pes(2019)"]
     * Output: ["pes","fifa","gta","pes(2019)"]
     *
     * Input: names = ["onepiece","onepiece(1)","onepiece(2)","onepiece(3)","onepiece"]
     * Output: ["onepiece","onepiece(1)","onepiece(2)","onepiece(3)","onepiece(4)"]
     * Constraints:
     *
     * 1 <= names.length <= 5 * 10^4
     * 1 <= names[i].length <= 20
     * names[i] consists of lower case English letters, digits and/or round brackets.
     * @param names
     * @return
     */
    // time = O(n), space = O(n)
    public String[] getFolderNames(String[] names) {
        int n = names.length;
        HashMap<String, Integer> map = new HashMap<>();
        String[] res = new String[n];

        for (int i = 0; i < n; i++) {
            if (map.containsKey(names[i])) {
                while (map.containsKey(names[i] + "(" + map.get(names[i]) + ")")) map.put(names[i], map.get(names[i]) + 1);
                String newName = names[i] + "(" + map.get(names[i]) + ")";
                map.put(names[i], map.get(names[i]) + 1);
                map.put(newName, 1);
                res[i] = newName;
            } else {
                map.put(names[i], 1);
                res[i] = names[i];
            }
        }
        return res;
    }
}
