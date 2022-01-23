package LC2100_2400;

public class LC2129_CapitalizetheTitle {
    /**
     * You are given a string title consisting of one or more words separated by a single space, where each word
     * consists of English letters. Capitalize the string by changing the capitalization of each word such that:
     *
     * If the length of the word is 1 or 2 letters, change all letters to lowercase.
     * Otherwise, change the first letter to uppercase and the remaining letters to lowercase.
     * Return the capitalized title.
     *
     * Input: title = "capiTalIze tHe titLe"
     * Output: "Capitalize The Title"
     *
     * Constraints:
     *
     * 1 <= title.length <= 100
     * title consists of words separated by a single space without any leading or trailing spaces.
     * Each word consists of uppercase and lowercase English letters and is non-empty.
     * @param title
     * @return
     */
    // time = O(n), space = O(n)
    public String capitalizeTitle(String title) {
        String[] words = title.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            word = word.toLowerCase();
            if (word.length() > 2) {
                sb.append((char)(word.charAt(0) - 'a' + 'A')).append(word.substring(1));
            } else sb.append(word);
            sb.append(' ');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
