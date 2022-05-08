package LC2101_2400;

public class LC2241_DesignanATMMachine {
    /**
     * There is an ATM machine that stores banknotes of 5 denominations: 20, 50, 100, 200, and 500 dollars. Initially
     * the ATM is empty. The user can use the machine to deposit or withdraw any amount of money.
     *
     * When withdrawing, the machine prioritizes using banknotes of larger values.
     *
     * For example, if you want to withdraw $300 and there are 2 $50 banknotes, 1 $100 banknote, and 1 $200 banknote,
     * then the machine will use the $100 and $200 banknotes.
     * However, if you try to withdraw $600 and there are 3 $200 banknotes and 1 $500 banknote, then the withdraw
     * request will be rejected because the machine will first try to use the $500 banknote and then be unable to use
     * banknotes to complete the remaining $100. Note that the machine is not allowed to use the $200 banknotes instead
     * of the $500 banknote.
     * Implement the ATM class:
     *
     * ATM() Initializes the ATM object.
     * void deposit(int[] banknotesCount) Deposits new banknotes in the order $20, $50, $100, $200, and $500.
     * int[] withdraw(int amount) Returns an array of length 5 of the number of banknotes that will be handed to the
     * user in the order $20, $50, $100, $200, and $500, and update the number of banknotes in the ATM after withdrawing.
     * Returns [-1] if it is not possible (do not withdraw any banknotes in this case).
     *
     * Input
     * ["ATM", "deposit", "withdraw", "deposit", "withdraw", "withdraw"]
     * [[], [[0,0,1,2,1]], [600], [[0,1,0,1,1]], [600], [550]]
     * Output
     * [null, null, [0,0,1,0,1], null, [-1], [0,1,0,0,1]]
     *
     * Constraints:
     *
     * banknotesCount.length == 5
     * 0 <= banknotesCount[i] <= 10^9
     * 1 <= amount <= 10^9
     * At most 5000 calls in total will be made to withdraw and deposit.
     * At least one call will be made to each function withdraw and deposit.
     */
    // time = O(1), space = O(1)
    long[] notes;
    int[] money;
    public LC2241_DesignanATMMachine() {
        notes = new long[5];
        money = new int[]{20, 50, 100, 200, 500};
    }

    public void deposit(int[] banknotesCount) {
        for (int i = 0; i < 5; i++) notes[i] += banknotesCount[i];
    }

    public int[] withdraw(int amount) {
        int[] res = new int[5];
        for (int i = 4; i >= 0; i--) {
            if (notes[i] > 0) {
                long count = amount / money[i];
                if (notes[i] >= count) {
                    amount -= count * money[i];
                    res[i] = (int) count;
                    notes[i] -= count;
                } else {
                    amount -= notes[i] * money[i];
                    res[i] = (int) notes[i];
                    notes[i] = 0;
                }
            }
            if (amount == 0) return res;
        }
        // amount > 0
        for (int i = 0; i < 5; i++) notes[i] += res[i]; // setback
        return new int[]{-1};
    }
}
