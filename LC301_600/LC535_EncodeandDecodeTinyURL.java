package LC301_600;
import java.util.*;
public class LC535_EncodeandDecodeTinyURL {
    /**
     * TinyURL is a URL shortening service where you enter a URL such as https://leetcode.com/problems/design-tinyurl
     * and it returns a short URL such as http://tinyurl.com/4e9iAk.
     *
     * Design the encode and decode methods for the TinyURL service. There is no restriction on how your encode/decode
     * algorithm should work. You just need to ensure that a URL can be encoded to a tiny URL and the tiny URL can be
     * decoded to the original URL.
     *
     * @param longUrl
     * @return
     */
    // time = O(1), space = O(1)
    HashMap<String, String> map = new HashMap<>();
    String mapping = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        boolean isFound = false;
        String url = "";
        while (!isFound) {
            for (int i = 0; i < 6; i++) {
                int idx = random.nextInt(mapping.length());
                sb.append(mapping.charAt(idx));
            }
            url = "http://tingurl.com/" + sb.toString();
            if (!map.containsKey(url)) {
                map.put(url, longUrl);
                isFound = true;
            }
        }
        return url;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return map.get(shortUrl);
    }
}
/**
 * 26 + 26 + 10 = 62
 * 62^6 = 56  -> 62^7 = 3.52e12  10M -> 300 year
 * use hashmap -> 如何作映射
 * 0000001
 * 0000002
 * ....
 * 000000A
 * ZZZZZZZ
 * => hash难写就用随机数
 * ace4EFa ace4EFb => 重复？再来一次
 * decode => hashmap存下来
 */
