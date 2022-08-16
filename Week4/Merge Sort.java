import java.util.*;

class Program {
    public static int[] mergeSort(int[] array) {
        int[] aux = array.clone();
        helper(array, aux, 0, array.length - 1);
        return array;
    }

    private static void helper(int[] main, int[] aux, int start, int end) {
        if (start == end) {
            return;
        }
        int middle = start + (end - start) / 2;
        helper(aux, main, start, middle);
        helper(aux, main, middle + 1, end);
        doMerge(main, aux, start, middle, end);
    }

    private static void doMerge(int[] main, int[] aux, int start, int middle, int end) {
        int ptrOne = start, ptrTwo = middle + 1, ptrMain = start;
        while (ptrOne <= middle && ptrTwo <= end) {
            if (aux[ptrOne] <= aux[ptrTwo]) {
                main[ptrMain] = aux[ptrOne];
                ptrOne += 1;
            } else {
                main[ptrMain] = aux[ptrTwo];
                ptrTwo += 1;
            }
            ptrMain += 1;
        }
        while (ptrOne <= middle) {
            main[ptrMain++] = aux[ptrOne++];
        }
        while (ptrTwo <= end) {
            main[ptrMain++] = aux[ptrTwo++];
        }
    }
}
