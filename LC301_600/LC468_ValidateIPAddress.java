package LC301_600;

public class LC468_ValidateIPAddress {
    /**
     * Given a string IP, return "IPv4" if IP is a valid IPv4 address, "IPv6" if IP is a valid IPv6 address or "Neither"
     * if IP is not a correct IP of any type.
     *
     * A valid IPv4 address is an IP in the form "x1.x2.x3.x4" where 0 <= xi <= 255 and xi cannot contain leading zeros.
     * For example, "192.168.1.1" and "192.168.1.0" are valid IPv4 addresses but "192.168.01.1", while "192.168.1.00"
     * and "192.168@1.1" are invalid IPv4 addresses.
     *
     * A valid IPv6 address is an IP in the form "x1:x2:x3:x4:x5:x6:x7:x8" where:
     *
     * 1 <= xi.length <= 4
     * xi is a hexadecimal string which may contain digits, lower-case English letter ('a' to 'f') and upper-case
     * English letters ('A' to 'F').
     * Leading zeros are allowed in xi.
     * For example, "2001:0db8:85a3:0000:0000:8a2e:0370:7334" and "2001:db8:85a3:0:0:8A2E:0370:7334" are valid IPv6
     * addresses, while "2001:0db8:85a3::8A2E:037j:7334" and "02001:0db8:85a3:0000:0000:8a2e:0370:7334" are invalid
     * IPv6 addresses.
     *
     * Input: IP = "172.16.254.1"
     * Output: "IPv4"
     *
     * Constraints:
     *
     * IP consists only of English letters, digits and the characters '.' and ':'.
     * @param IP
     * @return
     */
    // time = O(1), space = O(1)
    public String validIPAddress(String IP) {
        if (isIPv4(IP)) return "IPv4";
        if (isIPv6(IP.toUpperCase())) return "IPv6";
        return "Neither";
    }

    private boolean isIPv4(String ip) {
        int count = 0;
        for (char c : ip.toCharArray()) {
            if (c == '.') count++;
        }
        if (count != 3) return false;

        String[] fields = ip.split("\\.");
        if (fields.length != 4) return false;

        for (String field : fields) {
            if (field.equals("") || field.length() > 3) return false;
            for (int i = 0; i < field.length(); i++) {
                if (!Character.isDigit(field.charAt(i))) return false;
            }

            int num = Integer.parseInt(field);
            if (!String.valueOf(num).equals(field) || num < 0 || num > 255) return false; // "01.01.01.01"
        }
        return true;
    }

    private boolean isIPv6(String ip) {
        int count = 0;
        for (char c : ip.toCharArray()) {
            if (c == ':') count++;
        }
        if (count != 7) return false;

        String[] fields = ip.split(":");
        if (fields.length != 8) return false;

        for (String field : fields) {
            if (field.equals("") || field.length() > 4) return false;
            for (int i = 0; i < field.length(); i++) {
                if (!Character.isDigit(field.charAt(i)) && (field.charAt(i) < 'A' || field.charAt(i) > 'F')) {
                    return false;
                }
            }
        }
        return true;
    }
}
