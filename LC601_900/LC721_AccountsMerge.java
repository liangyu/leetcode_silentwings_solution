package LC601_900;
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
    // time = O(∑AlogA), space = O(∑A), A: the length of accounts[i]
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
}
