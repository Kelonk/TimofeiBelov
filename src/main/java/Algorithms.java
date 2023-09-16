package main.java;

import java.util.Scanner;
import java.util.function.Function;

public class Algorithms {

    public static Scanner in = new Scanner(System.in);

    public static int binarySearch(int key, int[] arr, boolean first) {
        int left = -1;
        int right = arr.length;

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
            if (left > 0 && arr[left] == key) {
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

    public static void main(String[] args){
        long n = in.nextLong(); // number of copies to make
        long x = in.nextLong(); // speed of first
        long y = in.nextLong(); // speed of second
        long t = Math.min(x, y); // minimum time
        n--;
        long l = 0;
        long r = Math.max(x, y) * n; // maximum theoretical time
        while (l + 1 < r){
            long m = (l + r) / 2;
            if (m/x + m/y < n){
                l = m;
            } else {
                r = m;
            }
        }
        System.out.println(t + r);
        //System.out.println(binarySolution((value) -> value * value - 2, 0, 10, 0));
    }
}
