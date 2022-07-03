package LC901_1200;
import java.util.*;
public class LC929_UniqueEmailAddresses {
    /**
     * Every valid email consists of a local name and a domain name, separated by the '@' sign. Besides lowercase
     * letters, the email may contain one or more '.' or '+'.
     *
     * For example, in "alice@leetcode.com", "alice" is the local name, and "leetcode.com" is the domain name.
     * If you add periods '.' between some characters in the local name part of an email address, mail sent there will
     * be forwarded to the same address without dots in the local name. Note that this rule does not apply to domain
     * names.
     *
     * For example, "alice.z@leetcode.com" and "alicez@leetcode.com" forward to the same email address.
     * If you add a plus '+' in the local name, everything after the first plus sign will be ignored. This allows
     * certain emails to be filtered. Note that this rule does not apply to domain names.
     *
     * For example, "m.y+name@email.com" will be forwarded to "my@email.com".
     * It is possible to use both of these rules at the same time.
     *
     * Given an array of strings emails where we send one email to each email[i], return the number of different
     * addresses that actually receive mails.
     *
     * Input: emails = ["test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= emails.length <= 100
     * 1 <= emails[i].length <= 100
     * email[i] consist of lowercase English letters, '+', '.' and '@'.
     * Each emails[i] contains exactly one '@' character.
     * All local and domain names are non-empty.
     * Local names do not start with a '+' character.
     * @param emails
     * @return
     */
    // S1
    // time = O(n * k), space = O(n * k)
    public int numUniqueEmails(String[] emails) {
        HashSet<String> set = new HashSet<>();

        for (String s : emails) {
            String[] strs = s.split("@");
            StringBuilder sb = new StringBuilder();
            for (char c : strs[0].toCharArray()) {
                if (c == '+') break;
                else if (c != '.') sb.append(c);
            }
            sb.append('@').append(strs[1]);
            set.add(sb.toString());
        }
        return set.size();
    }

    // S2
    // time = O(n), space = O(n)
    public int numUniqueEmails2(String[] emails) {
        // corner case
        if (emails == null || emails.length == 0) return 0;

        HashSet<String> set = new HashSet<>();
        for (String email : emails) {
            String[] parts = email.split("@");
            String[] local = parts[0].split("\\+");
            set.add(local[0].replace(".", "") + '@' + parts[1]);
        }
        return set.size();
    }
}
