package LC1201_1500;
import java.util.*;
public class LC1236_WebCrawler {
    /**
     * Given a url startUrl and an interface HtmlParser, implement a web crawler to crawl all links that are under the
     * same hostname as startUrl.
     *
     * Return all urls obtained by your web crawler in any order.
     *
     * Input:
     * urls = [
     *   "http://news.yahoo.com",
     *   "http://news.yahoo.com/news",
     *   "http://news.yahoo.com/news/topics/",
     *   "http://news.google.com",
     *   "http://news.yahoo.com/us"
     * ]
     * edges = [[2,0],[2,1],[3,2],[3,1],[0,4]]
     * startUrl = "http://news.yahoo.com/news/topics/"
     * Output: [
     *   "http://news.yahoo.com",
     *   "http://news.yahoo.com/news",
     *   "http://news.yahoo.com/news/topics/",
     *   "http://news.yahoo.com/us"
     * ]
     *
     * Constraints:
     *
     * 1 <= urls.length <= 1000
     * 1 <= urls[i].length <= 300
     * startUrl is one of the urls.
     * Hostname label must be from 1 to 63 characters long, including the dots, may contain only the ASCII letters from
     * 'a' to 'z', digits  from '0' to '9' and the hyphen-minus character ('-').
     * The hostname may not start or end with the hyphen-minus character ('-').
     * See:  https://en.wikipedia.org/wiki/Hostname#Restrictions_on_valid_hostnames
     * You may assume there're no duplicates in url library.
     * @param startUrl
     * @param htmlParser
     * @return
     */
    // time = O(n), space = O(n)
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        List<String> res = new ArrayList<>();
        // corner case
        if (startUrl == null || startUrl.length() == 0) return res;

        HashSet<String> set = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(startUrl);
        set.add(startUrl);

        // get hostname
        String[] strs = startUrl.split("/");
        String hostname = strs[2];

        while (!queue.isEmpty()) {
            String cur = queue.poll();
            for (String next : htmlParser.getUrls(cur)) {
                if (!set.contains(next) && next.contains(hostname)) {
                    queue.offer(next);
                    set.add(next);
                }
            }
        }
        res.addAll(set);
        return res;
    }

    interface HtmlParser {
        // Return a list of all urls from a webpage of given url.
        public List<String> getUrls(String url);
    }
}
