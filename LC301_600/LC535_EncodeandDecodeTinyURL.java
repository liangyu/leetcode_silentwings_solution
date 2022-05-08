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

    // S2: Hash
    // time = O(1), space = O(1)
    public class Codec {
        HashMap<String, String> map;
        String chars = "01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Encodes a URL to a shortened URL.
        public String encode(String longUrl) {
            map = new HashMap<>();
            while (true) {
                String shortUrl = "http://tinyurl.com/" + random_str(6);
                if (!map.containsKey(shortUrl)) {
                    map.put(shortUrl, longUrl);
                    return shortUrl;
                }
            }
        }

        // Decodes a shortened URL to its original URL.
        public String decode(String shortUrl) {
            return map.get(shortUrl);
        }

        private String random_str(int k) {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            while (k-- > 0) {
                sb.append(chars.charAt(random.nextInt(62)));
            }
            return sb.toString();
        }
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
 * 递增的62进制数
 * ace4EFa ace4EFb => 重复？再来一次
 * decode => hashmap存下来
 *
 * 随机化
 * 0 - 9, a - z, A - Z
 * k位62进制数
 * p = (62^k - n) / (62^k)
 * 1 / p
 * 1 + (1 - p) + (1 - p)^2 + (1 - p)^3 + ...
 * (1-p)*s = (1-p) + (1-p)^2 + ...
 * s - (1-p)*s = s * p = 1  => s = 1/p
 * s = p + 2 * (1-p) * p + 3 * (1-p)^2 * p + ...
 * s/p = 1 * (1-p)^0 + 2 * (1-p)^1 + ...
 * (1-p)*(s/p) = (1-p) + 2 * (1-p)^2 + ...
 * (s/p)*(1-(1-p)) = 1 + (1-p) + (1-P)^2 + ... => s = 1 + (1-p) + (1-p)^2
 */
