package LC2101_2400;
import java.util.*;
public class LC2126_DestroyingAsteroids {
    /**
     * You are given an integer mass, which represents the original mass of a planet. You are further given an integer
     * array asteroids, where asteroids[i] is the mass of the ith asteroid.
     *
     * You can arrange for the planet to collide with the asteroids in any arbitrary order. If the mass of the planet
     * is greater than or equal to the mass of the asteroid, the asteroid is destroyed and the planet gains the mass of
     * the asteroid. Otherwise, the planet is destroyed.
     *
     * Return true if all asteroids can be destroyed. Otherwise, return false.
     *
     * Input: mass = 10, asteroids = [3,9,19,5,21]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= mass <= 10^5
     * 1 <= asteroids.length <= 10^5
     * 1 <= asteroids[i] <= 10^5
     * @param mass
     * @param asteroids
     * @return
     */
    // S1: Sort
    // time = O(nlogn), space = O(1)
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        long curMass = mass;

        for (int x : asteroids) {
            if (x > curMass) return false;
            curMass += x;
        }
        return true;
    }

    // S2: TreeMap
    // time = O(nlogn), space = O(n)
    public boolean asteroidsDestroyed2(int mass, int[] asteroids) {
        TreeMap<Long, Integer> map = new TreeMap<>();
        for (int x : asteroids) map.put((long) x, map.getOrDefault((long) x, 0) + 1);

        long total = (long) mass;
        while (map.size() > 0) {
            Long fk = map.floorKey(total);
            if (fk == null) return false;
            total += fk;
            map.put(fk, map.get(fk) - 1);
            if (map.get(fk) == 0) map.remove(fk);
        }
        return true;
    }
}
