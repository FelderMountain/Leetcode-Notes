import java.util.*;

public class BinarySearch {

    public static int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        int result = 0;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (list.get(middle) == target) {
                return middle;
            } else if (list.get(middle) < target) {
                result = middle;
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return list.get(result) < target ? result : -1;
    }
}
