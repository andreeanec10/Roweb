package com;

import java.util.*;

public class Main {

    public static StringBuilder genSolution(final int[] A, final int diff) {
        //naive solution
        StringBuilder s1 = new StringBuilder();
        for (int i = 0; i < A.length - 1; i+=1) {
            for (int j = i+1; j < A.length; j+=1) {
                if (A[i] - A[j] == diff) {
                    s1.append(i).append("-").append(j).append(",");
                }
            }
        }
        //this program has O(n^2) complexity
        //but also does not find all pairs: eg. if we have A[6] = 4 and A[7] = 5,
        //it does not find A[7]-A[6]
        return s1;
    }

    public static void divideEtImpera(final ArrayList<int[]> array, final int start,
                                      final int stop, final int[] x, final int diff, StringBuilder s2) {
        int mid = (start + stop)/2;
        if (array.get(mid)[0] == (x[0] + diff)) {
            s2.append(array.get(mid)[1]).append("-").append(x[1]).append(",");
        } else if (start != stop){
            if (array.get(mid)[0] + diff < x[0]) {
                divideEtImpera(array, start, mid, x, diff, s2);
            } else {
                divideEtImpera(array, mid+1, stop, x, diff, s2);
            }
        }
    }

    public static StringBuilder genFasterSolutin(final int[] A, final int diff) {
        StringBuilder s2 = new StringBuilder();
        /*Sort method from arrays has a complexity
        similar to merge sort. That means the time complexity of the sort method
        in worst case is O(n * log n)*/
        ArrayList <int[]> array = new ArrayList<>();
        for (int i = 0; i < A.length; i+=1) {
            array.add(new int[] {A[i], i});
        }
        Collections.sort(array, new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                /*if the difference is greater then 0 means
                that it is useful to sort the array in an ascending order*/
                if (diff > 0)
                    return Integer.compare(o1[0], o2[0]);
                /*otherwise in a descending one*/
                return Integer.compare(o2[0], o1[0]);
            }
        });
        //now I am searching for the number
        for (int i = 0; i < A.length-1; i+=1) {
            int start = i+1;
            int stop = A.length;
            divideEtImpera(array,start, stop, array.get(i), diff, s2);
        }
        return s2;
    }

    public static void main(String[] args) {
        int[] A = {1,3, 4, 8, 9, 11, 5, 7, 6};
        int diff = -1;
        //first algorithm has a O(n^2) time complexity
        System.out.println(genSolution(A, diff));
        //second algorithm has a O(n * log n) time complexity
        System.out.println(genFasterSolutin(A, diff));
    }
}
