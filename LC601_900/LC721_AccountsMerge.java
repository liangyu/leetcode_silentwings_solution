package LC601_900;
import com.sun.source.tree.Tree;

import java.util.*;
public class LC721_AccountsMerge {
    /**
     * Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is
     * a name, and the rest of the elements are emails representing emails of the account.
     *
     * Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some
     * email that is common to both accounts. Note that even if two accounts have the same name, they may belong to
     * different people as people could have the same name. A person can have any number of accounts initially, but
     * all of their accounts definitely have the same name.
     *
     * After merging the accounts, return the accounts in the following format: the first element of each account is
     * the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned
     * in any order.
     *
     * Input:
     * accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"],
     * ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
     * Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],
     * ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
     *
     * Note:
     *
     * The length of accounts will be in the range [1, 1000].
     * The length of accounts[i] will be in the range [1, 10].
     * The length of accounts[i][j] will be in the range [1, 30].
     *
     * @param accounts
     * @return
     */
    // S1: DFS
    // time = O(n * k * log(n * k)), space = O(n * k), n: num of accounts, k: max length of an account
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        List<List<String>> res = new ArrayList<>();
        // corner case
        if (accounts == null || accounts.size() == 0 || accounts.get(0) == null || accounts.get(0).size() == 0) {
            return res;
        }

        HashMap<String, String> emailToOwner = new HashMap<>();
        HashMap<String, List<String>> emailToEmail = new HashMap<>();

        // Step 1: build the graph between correlated emails
        for (List<String> account : accounts) {
            String owner = account.get(0);
            String root = account.get(1);
            for (int i = 1; i < account.size(); i++) {
                emailToEmail.putIfAbsent(root, new ArrayList<>());
                emailToEmail.get(root).add(account.get(i));
                emailToEmail.putIfAbsent(account.get(i), new ArrayList<>());
                emailToEmail.get(account.get(i)).add(root);
                emailToOwner.put(account.get(i), owner);
            }
        }

        // Step 2: create a set to save each email
        HashSet<String> set = new HashSet<>();
        for (String email : emailToEmail.keySet()) {
            if (set.add(email)) {
                Stack<String> stack = new Stack<>(); // use stack to do the dfs
                List<String> emails = new LinkedList<>(); // maintain a list for current res list
                stack.push(email);
                while (!stack.isEmpty()) {
                    String node = stack.pop();
                    emails.add(node);
                    for (String next : emailToEmail.get(node)) {
                        if (set.add(next)) stack.push(next);
                    }
                }
                Collections.sort(emails); // in sorted order
                emails.add(0, emailToOwner.get(email)); // draw the owner info out of the email
                res.add(emails);
            }
        }
        return res;
    }

    // S2: Union Find
    // time = O(n * k * log(n * k)), space = O(n * k), n: num of accounts, k: max length of an account
    HashMap<String, String> parent; // email -> rootEmail
    HashMap<String, String> owner; // email -> person
    HashMap<String, TreeSet<String>> res; // rootEmail -> set of emails
    public List<List<String>> accountsMerge2(List<List<String>> accounts) {
        parent = new HashMap<>();
        owner = new HashMap<>();
        res = new HashMap<>();

        for (List<String> group : accounts) {
            String person = group.get(0);
            String email0 = group.get(1);
            if (!parent.containsKey(email0)) parent.put(email0, email0);
            owner.put(email0, person);

            for (int i = 2; i < group.size(); i++) {
                String email = group.get(i);
                if (!parent.containsKey(email)) parent.put(email, email);
                if (findParent(email0) != findParent(email)) union(email0, email);
                owner.put(email, person);
            }
        }

        for (String email : parent.keySet()) {
            String rootEmail = findParent(email); // 注意：这里一定要调用路径压缩找到最原始的祖先email，parent里指向的未必是最早的祖先！！！
            res.putIfAbsent(rootEmail, new TreeSet());
            res.get(rootEmail).add(email);
        }

        List<List<String>> ans = new ArrayList<>();
        for (String rootEmail : res.keySet()) {
            String person = owner.get(rootEmail);
            List<String> temp = new ArrayList<>();
            temp.add(person);
            for (String email : res.get(rootEmail)) {
                temp.add(email);
            }
            ans.add(temp);
        }
        return ans;
    }

    private String findParent(String x) {
        if (!x.equals(parent.get(x))) {
            parent.put(x, findParent(parent.get(x)));
        }
        return parent.get(x);
    }

    private void union(String x, String y) {
        x = parent.get(x);
        y = parent.get(y);
        if (x.compareTo(y) < 0) parent.put(y, x);
        else parent.put(x, y);
    }
}
/**
 * 此题是accounts的聚类，本质就是Union Find，不要被owner的名字所干扰。
 * 初始化时，每个account的Father都是本身。对于一组account里的所有邮箱怎么做归并：完全等价于线性地处理每两个相邻邮箱的归并。
 * 每个group的Father可以定义为字典序最小的那个。
 * 归并完之后，再遍历一次所有的邮箱，按照其Father分类就是答案所要求的分类。每个分类对应的人名，就是Father account对应的人名。
 * 只能根据rootEmail来分组，而不能根据人名来分组，因为会有重名
 *
 */
