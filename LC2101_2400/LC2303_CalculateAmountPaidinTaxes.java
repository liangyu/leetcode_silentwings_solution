package LC2101_2400;

public class LC2303_CalculateAmountPaidinTaxes {
    /**
     * You are given a 0-indexed 2D integer array brackets where brackets[i] = [upperi, percenti] means that the ith
     * tax bracket has an upper bound of upperi and is taxed at a rate of percenti. The brackets are sorted by upper
     * bound (i.e. upperi-1 < upperi for 0 < i < brackets.length).
     *
     * Tax is calculated as follows:
     *
     * The first upper0 dollars earned are taxed at a rate of percent0.
     * The next upper1 - upper0 dollars earned are taxed at a rate of percent1.
     * The next upper2 - upper1 dollars earned are taxed at a rate of percent2.
     * And so on.
     * You are given an integer income representing the amount of money you earned. Return the amount of money that
     * you have to pay in taxes. Answers within 10-5 of the actual answer will be accepted.
     *
     * Input: brackets = [[3,50],[7,10],[12,25]], income = 10
     * Output: 2.65000
     *
     * Input: brackets = [[1,0],[4,25],[5,50]], income = 2
     * Output: 0.25000
     *
     * Constraints:
     *
     * 1 <= brackets.length <= 100
     * 1 <= upperi <= 1000
     * 0 <= percenti <= 100
     * 0 <= income <= 1000
     * upperi is sorted in ascending order.
     * All the values of upperi are unique.
     * The upper bound of the last tax bracket is greater than or equal to income.
     * @param brackets
     * @param income
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public double calculateTax(int[][] brackets, int income) {
        double res = 0;
        int last = 0;
        for (int[] x : brackets) {
            int amount = x[0] - last, tax = x[1];
            res += Math.min(amount, income) * tax / 100.0;
            last = x[0];
            income -= Math.min(amount, income);
            if (income == 0) break;
        }
        return res;
    }

    // S2
    // time = O(n), space = O(1)
    public double calculateTax2(int[][] brackets, int income) {
        double res = 0, last = 0;
        for (int[] x : brackets) {
            res += x[1] * Math.max(0.0, Math.min(income, x[0]) - last);
            last = x[0];
        }
        return res / 100;
    }
}
