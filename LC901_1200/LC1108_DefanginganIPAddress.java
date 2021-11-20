package LC901_1200;

public class LC1108_DefanginganIPAddress {
    /**
     * Given a valid (IPv4) IP address, return a defanged version of that IP address.
     *
     * A defanged IP address replaces every period "." with "[.]".
     *
     * Input: address = "1.1.1.1"
     * Output: "1[.]1[.]1[.]1"
     *
     * Constraints:
     *
     * The given address is a valid IPv4 address.
     * @param address
     * @return
     */
    // time = O(n), space = O(n)
    public String defangIPaddr(String address) {
        // corner case
        if (address == null || address.length() == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (char c : address.toCharArray()) {
            if (c != '.') sb.append(c);
            else sb.append("[.]");
        }
        return sb.toString();
    }
}
