package LC1801_2100;
import java.util.*;
public class LC1826_FaultySensor {
    /**
     * An experiment is being conducted in a lab. To ensure accuracy, there are two sensors collecting data
     * simultaneously. You are given 2 arrays sensor1 and sensor2, where sensor1[i] and sensor2[i] are the ith data
     * points collected by the two sensors.
     *
     * However, this type of sensor has a chance of being defective, which causes exactly one data point to be dropped.
     * After the data is dropped, all the data points to the right of the dropped data are shifted one place to the
     * left, and the last data point is replaced with some random value. It is guaranteed that this random value will
     * not be equal to the dropped value.
     *
     * For example, if the correct data is [1,2,3,4,5] and 3 is dropped, the sensor could return [1,2,4,5,7] (the last
     * position can be any value, not just 7).
     * We know that there is a defect in at most one of the sensors. Return the sensor number (1 or 2) with the defect.
     * If there is no defect in either sensor or if it is impossible to determine the defective sensor, return -1.
     *
     * Input: sensor1 = [2,3,4,5], sensor2 = [2,1,3,4]
     * Output: 1
     *
     * Constraints:
     *
     * sensor1.length == sensor2.length
     * 1 <= sensor1.length <= 100
     * 1 <= sensor1[i], sensor2[i] <= 100
     * @param sensor1
     * @param sensor2
     * @return
     */
    // time = O(n), space = O(1)
    public int badSensor(int[] sensor1, int[] sensor2) {
        // corner case
        if (sensor1 == null || sensor1.length == 0 || sensor2 == null || sensor2.length == 0) return -1;
        if (sensor1.length != sensor2.length) return -1;

        int n = sensor1.length;
        for (int i = 0; i < n - 1; i++) {
            if (sensor1[i] != sensor2[i]) {
                while (i < n - 1 && sensor1[i] == sensor2[i + 1]) i++;
                return i + 1 == n ? 1 : 2;
            }
        }
        return -1;
    }
}
