# LC721 Accounts Merge

标签（空格分隔）： LeetCode Java DFS UnionFid

---
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

【难点误区】

本题最难想的地方就是运用Union Find的思维，在DFS的建图方法中，想到选出一个email作为组里剩下email的root，然后与这些剩下的email都作双向一一映射，从而在下面的DFS中进行图上遍历和union。


【解题思路】

本题主要两种方法：DFS和Union Find。这里比较倾向于采用DFS的方法，更简单直观。

对于DFS，主要分两大块来解决，建图和遍历。

建图的话，基本上分2部分，一部分就是每个email对其owner进行一一映射建图；另一部分则是对于每一组email，我们把这个组里的第一个email作为root，组里剩下的email都和它有一一映射的关系，同时它也对组里剩下的email进行映射，即建立双向映射关系，放在同一个emailToEmail的HashMap里。

然后就是遍历，遇到这种图上遍历，一定要查重，因为可能重复遍历到已经访问过的节点，最简单粗暴的方法就是用一个HashSet来查重。然后就是图上遍历，这里采用DFS。为了避免通过recursion来做，我们可以使用一个stack来往深度访问，只要HashSet里尚未存在，表明这条路就没走过，那么我们可以一路发散下去，套用stack深度遍历模板，即三段论：

```java
stack.push(root);
while (!stack.isEmpty()) {
	V cur = stack.pop();
	// to do sth.
	for (V next : map.get(cur)) {
		if (set.add(next)) stack.push(next);
	}
}
```
最后按照题目要求进行排序，然后把owner通过emailToOwner的HashMap找到加到头部，然后加入res list即可。


```java     
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
```
