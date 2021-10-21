package LC1801_2100;
import java.util.*;
public class LC2043_SimpleBankSystem {
    /**
     * You have been tasked with writing a program for a popular bank that will automate all its incoming transactions
     * (transfer, deposit, and withdraw). The bank has n accounts numbered from 1 to n. The initial balance of each
     * account is stored in a 0-indexed integer array balance, with the (i + 1)th account having an initial balance of
     * balance[i].
     *
     * Execute all the valid transactions. A transaction is valid if:
     *
     * The given account number(s) are between 1 and n, and
     * The amount of money withdrawn or transferred from is less than or equal to the balance of the account.
     * Implement the Bank class:
     *
     * Bank(long[] balance) Initializes the object with the 0-indexed integer array balance.
     * boolean transfer(int account1, int account2, long money) Transfers money dollars from the account numbered
     * account1 to the account numbered account2. Return true if the transaction was successful, false otherwise.
     * boolean deposit(int account, long money) Deposit money dollars into the account numbered account. Return true
     * if the transaction was successful, false otherwise.
     * boolean withdraw(int account, long money) Withdraw money dollars from the account numbered account. Return true
     * if the transaction was successful, false otherwise.
     *
     * Input
     * ["Bank", "withdraw", "transfer", "deposit", "transfer", "withdraw"]
     * [[[10, 100, 20, 50, 30]], [3, 10], [5, 1, 20], [5, 20], [3, 4, 15], [10, 50]]
     * Output
     * [null, true, true, true, false, false]
     *
     * Constraints:
     *
     * n == balance.length
     * 1 <= n, account, account1, account2 <= 10^5
     * 0 <= balance[i], money <= 10^12
     * At most 104 calls will be made to each function transfer, deposit, withdraw.
     * @param balance
     */
    // time = O(1), space = O(1)
    private long[] balance;
    private int n;
    public LC2043_SimpleBankSystem(long[] balance) {
        this.balance = balance;
        n = balance.length;
    }

    public boolean transfer(int account1, int account2, long money) {
        if (account1 <= n && account2 <= n && balance[account1 - 1] >= money) {
            balance[account1 - 1] -= money;
            balance[account2 - 1] += money;
            return true;
        }
        return false;
    }

    public boolean deposit(int account, long money) {
        if (account <= n) {
            balance[account - 1] += money;
            return true;
        }
        return false;
    }

    public boolean withdraw(int account, long money) {
        if (account <= n && balance[account - 1] >= money) {
            balance[account - 1] -= money;
            return true;
        }
        return false;
    }
}
