package LC301_600;
import java.util.*;
public class LC470_ImplementRand10UsingRand7 {
    /**
     * Given the API rand7() that generates a uniform random integer in the range [1, 7], write a function rand10() that
     * generates a uniform random integer in the range [1, 10]. You can only call the API rand7(), and you shouldn't
     * call any other API. Please do not use a language's built-in random API.
     *
     * Each test case will have one internal argument n, the number of times that your implemented function rand10()
     * will be called while testing. Note that this is not an argument passed to rand10().
     *
     * Input: n = 1
     * Output: [2]
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     *
     * Follow up:
     *
     * What is the expected value for the number of calls to rand7() function?
     * Could you minimize the number of calls to rand7()?
     * @return
     */
    // time = O(1), space = O(1)
    public int rand10() {
        int t = (rand7() - 1) * 7 + rand7();
        if (t > 40) return rand10();
        return (t - 1) % 10 + 1;
    }

    // supplemental method
    public int rand7() {
        Random random = new Random();
        return random.nextInt(7) + 1;
    };
}
/**
 * 调用2次rand7(), 7 * 7 = 49种 1~49
 * 只保留1-40，后面9个砍掉
 * 1~40按照对10的余数来分组
 * 每个组里4个数
 * rand7() -> 期望，保留概率40/49 -> 轮数 1/(40/49 * 2) = 49/20 平均2次多
 */