import java.util.*;
import java.util.function.Function;


public class Algorithms {
    public enum MathActions {
        Plus,
        Substract,
        Mult,
        Divide
    }

    public static long mod;

    public static int step = 10;

    public static Scanner in = new Scanner(System.in);

    public static boolean checkCoverage(int[] positions, int number, int length, boolean max){
        int covered_coord = positions[0] + length;
        number--;
        for (int i = 1; i < positions.length; i++){
            if (positions[i] > covered_coord){
                covered_coord = positions[i] + length;
                number--;
                if (number < 0){
                    // to cover more segments when available needed
                    return max;
                }
            }
        }
        return max ? number == 0 : true;
    }

    public static boolean checkCoverageWithBin(int[] positions, int number, int length){
        int covered_coord = positions[0] + length;
        number--;
        int location = -1;
        while (number >= 0){
            location = binarySearch(location, positions.length, covered_coord, positions, false);
            location = Math.abs(location);
            if (location >= positions.length - 1){
                break;
            }
            covered_coord = positions[location + (positions[location] == covered_coord ? 1 : 0)] + length;
            number --;
        }
        return location >= positions.length - 1;
    }

    public static int binarySearch(int key, int[] arr, boolean first) {
        return binarySearch(-1, arr.length, key, arr, first);
    }

    public static int binarySearch(int left, int right, int key, int[] arr, boolean first) {
        while (left + 1 < right){
            int mid = (left + right) / 2;
            if ((first && arr[mid] < key) || (!first && arr[mid] <= key)){
                left = mid;
            } else {
                right = mid;
            }
        }

        if (first) {
            if (right < arr.length && arr[right] == key) {
                return right;
            } else {
                return -1 - right;
            }
        } else {
            if (left >= 0 && arr[left] == key) {
                return left;
            } else {
                return -2 - left;
            }
        }
    }

    public static double binarySolution(Function<Double, Double> func, double min_v, double max_v, double eps) {
        if (eps == 0) { eps = 0.000001d;}
        double mid = 0;
        int act_times = (int) Math.log(Math.log(2) * (max_v - min_v) / eps);
        for (int i = 0; i < act_times; i++) {
            mid = (min_v + max_v) / 2;
            if (func.apply(min_v) * func.apply(mid) > 0){
                min_v = mid;
            } else {
                max_v = mid;
            }
        }
        return mid;
    }

    public static ArrayList<Integer> primesFind(int max){
        ArrayList<Integer> primes = new ArrayList<>();
        boolean[] located = new boolean[max];
        for (int i = 2; i < max; i++){
            if (!located[i]){
                primes.add(i);
                for (int j = i + i; j < max; j += i){
                    located[j] = true;
                }
            }
        }
        return primes;
    }

    public static ArrayList<Integer> breakOnPrimes(int number){
        ArrayList<Integer> factors = new ArrayList<>();
        for (int i = 2; i * i <= number; i++){
            if (number % i == 0){
                number /= i;
                factors.add(i);
                i--;
            }
        }
        factors.add(number);
        return factors;
    }

    public static long calculateWithMod(long mod, long digit1, long digit2, MathActions action){
        long ans = 0;
        Algorithms.mod = mod;
        switch (action) {
            case Plus -> {
                digit1 %= mod;
                digit2 %= mod;
                ans = (digit1 + digit2) % mod;
                return (ans + mod) % mod;
            }
            case Substract -> {
                digit1 %= mod;
                digit2 %= mod;
                ans = (digit1 - digit2) % mod;
                return (ans + mod) % mod;
            }
            case Mult -> {
                digit1 %= mod;
                digit2 %= mod;
                ans = (digit1 * digit2) % mod;
                return (ans + mod) % mod;
            }
            case Divide -> {
                return calculateWithMod(mod, digit1, binPow(digit2, mod - 2), MathActions.Mult);
            }
            default -> {
                return ans;
            }
        }
    }

    public static long calculateWithMod(long digit1, long digit2, MathActions action){
        return calculateWithMod(mod, digit1, digit2, action);
    }

    public static long binPow(long value, long pow){
        if (pow == 0){
            return 1;
        }
        if (pow % 2 == 0){
            return binPow(calculateWithMod(value, value, MathActions.Mult), pow / 2);
        }
        return calculateWithMod(
                binPow(calculateWithMod(value, value, MathActions.Mult), (pow - 1) / 2),
                value,
                MathActions.Mult);
    }

    public static int[] smallestPrimes(int max){
        int[] delPrimes = new int[max + 1];
        for(int i = 0; i <= max; i++){
            delPrimes[i] = i;
        }

        for (int i = 2; i <= max; i++){
            for (int j = i + i; j <= max; j += i){
                delPrimes[j] = Math.min(delPrimes[j], i);
            }
        }
        return delPrimes;
    }

    public static long sumAlg1(int[] primes){ // gives sum of smallest simple primes of not primes from 2 to N
        long ans = 0;
        for(int i = 2; i < primes.length; i++){
            if (primes[i] != i){
                ans += primes[i];
            }
        }
        return ans;
    }

    public static int findSmallest(int[] digits, int left, int right, int min){
        int smallest = digits[left - 1];
        for (int i = 0; i < left - 1; i++){
            smallest = Math.min(smallest, digits[i]);
            if (smallest == min){
                return min;
            }
        }
        for (int i = right - 1; i < digits.length; i++){
            smallest = Math.min(smallest, digits[i]);
            if (smallest == min){
                return min;
            }
        }
        return smallest;
    }

    public static void TaskC_0(){
        System.out.println(binarySolution((value) -> value * value - 2, 0, 10, 0));
    }

    public static void Task00_1(){
        int candidats = in.nextInt();
        int[] speeds = new int[candidats];
        int[] minLeft = new int[candidats + 1];
        int[] minRight = new int[candidats + 1];
        minLeft[0] = Integer.MAX_VALUE;
        for (int i = 0; i < candidats; i++){
            speeds[i] = in.nextInt();
            minLeft[i + 1] = Math.min(minLeft[i], speeds[i]);
        }
        minRight[0] = Integer.MAX_VALUE;
        for (int i = 0; i < candidats; i++){
            minRight[i + 1] = Math.min(minRight[i], speeds[candidats - i - 1]);
        }
        int trials = in.nextInt();
        for (int i = 0; i < trials; i++){
            System.out.println(Math.min(minLeft[in.nextInt()], minRight[candidats - in.nextInt() + 1]));
        }
    }

    public static void Task00_2(){
        int mandr = in.nextInt();
        double[] sounds = new double[mandr];
        double[] sum = new double[mandr + 1];
        sum[0] = 0;
        in.nextLine();
        String[] line = in.nextLine().split(" ");
        for (int i = 0; i < mandr; i++){
            sounds[i] = Math.log10(Float.parseFloat(line[i]));
            sum[i + 1] = sum[i] + sounds[i];
        }
        int trials = in.nextInt();
        for (int i = 0; i < trials; i++){
            int left = in.nextInt();
            int right = in.nextInt();
            double val = Math.pow(10, (sum[right + 1] - sum[left]) / (right - left + 1));
            System.out.printf("%.6f\n", val);
        }
    }

    public static void Task00_3(){
        int points = in.nextInt();
        int[] positions = new int[points];
        int number = in.nextInt();

        for (int i = 0; i < points; i++) {
            positions[i] = in.nextInt();
        }
        positions = Arrays.stream(positions).sorted().toArray();

        int max = positions[points - 1] - positions[0]; // distance between the furthest ones
        int min = -1; // set as minimum so that 0 is reachable

        if (number >= points || max == 0){ // enough points or all at one position
            System.out.println(0);
            return;
        }

        while (min + 1 < max){
            int mid = (max + min) / 2;
            if (checkCoverageWithBin(positions, number, mid)){
                max = mid;
            } else {
                min = mid;
            }
        }
        System.out.println(max);
    }

    public static void Task00_4(){
        int points = in.nextInt(); // >=3
        int[] positions = new int[points];
        int number = in.nextInt(); // >=2
        int min = Integer.MAX_VALUE; // minimum distance between adjacent
        positions[0] = in.nextInt();
        for (int i = 1; i < points; i++){
            positions[i] = in.nextInt();
            min = Math.min(min, positions[i] - positions[i - 1]);
        }
        //positions = Arrays.stream(positions).sorted().toArray(); // already sorted
        min--;
        int max = positions[points - 1] - positions[0]; // maximum distance

        if (number >= points || max == 0){
            System.out.println(min);
            return;
        }

        while (min + 1 < max){
            int mid = (max + min) / 2;
            if (!checkCoverage(positions, number, mid, true)){
                max = mid;
            } else {
                min = mid;
            }
        }
        System.out.println(max);
    }

    public static void Task00_5(){
        int count = in.nextInt();
        int[] numbers = new int[count];
        //int max = Integer.MIN_VALUE;
        for (int i = 0; i < count; i++){
            numbers[i] = in.nextInt();
            //max = Math.max(max, numbers[i]);
        }

        for (int val: numbers){
            var res = breakOnPrimes(val);
            for (int i = 0; i < res.size(); i++){
                System.out.print(res.get(i) + (i == res.size() - 1 ? "\n" : " "));
            }
        }

    }

    public static void Task00_6(){
        long a = in.nextLong();
        long b = in.nextLong();
        long c = in.nextLong();
        long d = in.nextLong();
        long mod = 1000000007;

        long val1 = calculateWithMod(mod, a, d, MathActions.Mult);
        long val2 = calculateWithMod(mod, b, c, MathActions.Mult);
        long val3 = calculateWithMod(mod, val1, val2, MathActions.Plus);
        long val4 = calculateWithMod(mod, b, d, MathActions.Mult);
        System.out.println(calculateWithMod(mod, val3, val4, MathActions.Divide));
    }

    public static void Task00_7(){
        int num = in.nextInt();
        System.out.println(sumAlg1(smallestPrimes(num)));
    }

    public static void main(String[] args){
        Task00_3();
    }
}
