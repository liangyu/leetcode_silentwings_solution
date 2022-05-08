package LC1201_1500;
import java.util.*;
public class LC1418_DisplayTableofFoodOrdersinaRestaurant {
    /**
     * Given the array orders, which represents the orders that customers have done in a restaurant. More specifically
     * orders[i]=[customerNamei,tableNumberi,foodItemi] where customerNamei is the name of the customer, tableNumberi is
     * the table customer sit at, and foodItemi is the item customer orders.
     *
     * Return the restaurant's “display table”. The “display table” is a table whose row entries denote how many of each
     * food item each table ordered. The first column is the table number and the remaining columns correspond to each
     * food item in alphabetical order. The first row should be a header whose first column is “Table”, followed by the
     * names of the food items. Note that the customer names are not part of the table. Additionally, the rows should be
     * sorted in numerically increasing order.
     *
     * Input: orders = [["David","3","Ceviche"],["Corina","10","Beef Burrito"],["David","3","Fried Chicken"],
     * ["Carla","5","Water"],["Carla","5","Ceviche"],["Rous","3","Ceviche"]]
     * Output: [["Table","Beef Burrito","Ceviche","Fried Chicken","Water"],["3","0","2","1","0"],["5","0","1","0","1"],
     * ["10","1","0","0","0"]]
     *
     * Constraints:
     *
     * 1 <= orders.length <= 5 * 10^4
     * orders[i].length == 3
     * 1 <= customerNamei.length, foodItemi.length <= 20
     * customerNamei and foodItemi consist of lowercase and uppercase English letters and the space character.
     * tableNumberi is a valid integer between 1 and 500.
     * @param orders
     * @return
     */
    // time = O(nlogn), space = O(n)
    public List<List<String>> displayTable(List<List<String>> orders) {
        List<List<String>> res = new ArrayList<>();
        TreeMap<Integer, HashMap<String, Integer>> map = new TreeMap<>();
        TreeSet<String> set = new TreeSet<>();

        for (List<String> order : orders) {
            int table = Integer.parseInt(order.get(1));
            String food = order.get(2);
            map.putIfAbsent(table, new HashMap<>());
            map.get(table).put(food, map.get(table).getOrDefault(food, 0) + 1);
            set.add(food);
        }

        List<String> columns = new ArrayList<>();
        columns.add("Table");
        columns.addAll(set);
        res.add(columns);

        for (int table : map.keySet()) {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(table));
            for (int i = 1; i < columns.size(); i++) {
                String food = columns.get(i);
                if (map.get(table).containsKey(food)) list.add(String.valueOf(map.get(table).get(food)));
                else list.add("0");
            }
            res.add(list);
        }
        return res;
    }
}
