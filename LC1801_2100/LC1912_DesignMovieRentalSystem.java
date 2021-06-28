package LC1801_2100;
import java.util.*;
public class LC1912_DesignMovieRentalSystem {
    /**
     * You have a movie renting company consisting of n shops. You want to implement a renting system that supports
     * searching for, booking, and returning movies. The system should also support generating a report of the currently
     * rented movies.
     *
     * Each movie is given as a 2D integer array entries where entries[i] = [shopi, moviei, pricei] indicates that there
     * is a copy of movie moviei at shop shopi with a rental price of pricei. Each shop carries at most one copy of a
     * movie moviei.
     *
     * The system should support the following functions:
     *
     * Search: Finds the cheapest 5 shops that have an unrented copy of a given movie. The shops should be sorted by
     * price in ascending order, and in case of a tie, the one with the smaller shopi should appear first. If there are
     * less than 5 matching shops, then all of them should be returned. If no shop has an unrented copy, then an empty
     * list should be returned.
     * Rent: Rents an unrented copy of a given movie from a given shop.
     * Drop: Drops off a previously rented copy of a given movie at a given shop.
     * Report: Returns the cheapest 5 rented movies (possibly of the same movie ID) as a 2D list res where
     * res[j] = [shopj, moviej] describes that the jth cheapest rented movie moviej was rented from the shop shopj.
     * The movies in res should be sorted by price in ascending order, and in case of a tie, the one with the smaller
     * shopj should appear first, and if there is still tie, the one with the smaller moviej should appear first. If
     * there are fewer than 5 rented movies, then all of them should be returned. If no movies are currently being rented,
     * then an empty list should be returned.
     * Implement the MovieRentingSystem class:
     *
     * MovieRentingSystem(int n, int[][] entries) Initializes the MovieRentingSystem object with n shops and the
     * movies in entries.
     * List<Integer> search(int movie) Returns a list of shops that have an unrented copy of the given movie as
     * described above.
     * void rent(int shop, int movie) Rents the given movie from the given shop.
     * void drop(int shop, int movie) Drops off a previously rented movie at the given shop.
     * List<List<Integer>> report() Returns a list of cheapest rented movies as described above.
     * Note: The test cases will be generated such that rent will only be called if the shop has an unrented copy of the
     * movie, and drop will only be called if the shop had previously rented out the movie.
     *
     * Input
     * ["MovieRentingSystem", "search", "rent", "rent", "report", "drop", "search"]
     * [[3, [[0, 1, 5], [0, 2, 6], [0, 3, 7], [1, 1, 4], [1, 2, 7], [2, 1, 5]]], [1], [0, 1], [1, 2], [], [1, 2], [2]]
     * Output
     * [null, [1, 0, 2], null, null, [[0, 1], [1, 2]], null, [0, 1]]
     *
     * Constraints:
     *
     * 1 <= n <= 3 * 10^5
     * 1 <= entries.length <= 10^5
     * 0 <= shopi < n
     * 1 <= moviei, pricei <= 10^4
     * Each shop carries at most one copy of a movie moviei.
     * At most 10^5 calls in total will be made to search, rent, drop and report.
     * @param n
     * @param entries
     */
    HashMap<Integer, TreeSet<Entry>> map; // <id, entry>
    HashMap<String, Integer> movies; // <entry, price>
    TreeSet<Entry> rent; // rented entry
    public LC1912_DesignMovieRentalSystem(int n, int[][] entries) {
        map = new HashMap<>();
        movies = new HashMap<>();
        rent = new TreeSet<>((o1, o2) -> o1.price != o2.price ? o1.price - o2.price : (o1.shop != o2.shop ? o1.shop - o2.shop : o1.id - o2.id));

        for (int[] e : entries) {
            int shop = e[0], id = e[1], price = e[2];
            Entry entry = new Entry(shop, id, price);
            map.putIfAbsent(id, new TreeSet<>((o1, o2) -> o1.price != o2.price ? o1.price - o2.price : o1.shop - o2.shop));
            map.get(id).add(entry);
            movies.put(shop + "+" + id, price);
        }
    }

    // time = O(n), space = O(n)
    public List<Integer> search(int movie) {
        List<Integer> res = new ArrayList<>();
        if (!map.containsKey(movie)) return res;
        TreeSet<Entry> set = map.get(movie);
        List<Entry> temp = new ArrayList<>();
        int k = 5;
        for (Entry entry : set) {
            if (k > 0) {
                res.add(entry.shop);
                k--;
            } else break;
        }
        return res;
    }

    // time = O(logn), space = O(n)
    public void rent(int shop, int movie) {
        int price = movies.get(shop + "+" + movie);
        rent.add(new Entry(shop, movie, price));
        map.get(movie).remove(new Entry(shop, movie, price));
    }

    // time = O(logn), space = O(n)
    public void drop(int shop, int movie) {
        int price = movies.get(shop + "+" + movie);
        rent.remove(new Entry(shop, movie, price));
        map.get(movie).add(new Entry(shop, movie, price));
    }

    // time = O(n), space = O(n)
    public List<List<Integer>> report() {
        List<List<Integer>> res = new ArrayList<>();
        int k = 5;
        for (Entry entry : rent) {
            if (k > 0) {
                res.add(Arrays.asList(entry.shop, entry.id));
                k--;
            }
        }
        return res;
    }

    private class Entry {
        private int shop, id, price;
        public Entry(int shop, int id, int price) {
            this.shop = shop;
            this.id = id;
            this.price = price;
        }
    }
}
