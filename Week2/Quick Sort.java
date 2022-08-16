// 给我一个array以及一个范围, 我能把这个范围里面的数字都给从小到大sort好. 这是quicksort.
class Solution {
    public static int[] quickSort(int[] array) {
        helper(array, 0, array.length - 1);
        return array;
    }

    private static void helper(int[] array, int start, int end) {
        if (start >= end)
            return;
        int pivot = start, left = start + 1, right = end;
        while (left <= right) {
            if (array[left] > array[pivot] && array[right] < array[pivot]) {
                swap(array, left, right);
                left += 1;
                right -= 1;
                continue;
            }
            if (array[left] <= array[pivot]) {
                left += 1;
            }
            if (array[right] >= array[pivot]) {
                right -= 1;
            }
        }
        swap(array, pivot, right);
        boolean isLeftShorter = (right - start) < (end - right);
        if (isLeftShorter) {
            helper(array, start, right - 1);
            helper(array, right + 1, end);
        } else {
            helper(array, right + 1, end);
            helper(array, start, right - 1);
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

// 为什么和right换? right后面指的都是比pivot大或者相等的. left左侧的
// 都是比pivot小或者相等的. 也就是left指向的数字可能和pivot相等或者更大.
// 假如比pivot大, 那么换到这里就出现left左侧有大于pivot的出现.
// 但是right的话指向的数字一定比pivot小或者相等. 和right换, 那么left左侧
// 的数还是都小于pivot或者等于. right右侧也还都pivot大或相等. 因此left算是一条分界线.
// 它的左侧都是比pivot小. 如果左侧部分排一下序, 那么right此时指的就是pivot的值.
// 因为left左侧最大就是pivot, 那么right肯定指向这个值. 等到过了right就会出现
// 大于pivot的情况. 把right右侧的排一下序, left指向的最小可能就是pivot(可能有重复值)
// 不然就是比pivot大的值. 因此和right交换, pivot就到达了它的最终位置.
// 还有就是如果和left交换, 可能会有出界的情况, 而和right永远不会. 极端情况
// 就是left在pivot的右侧, right和pivot重合. 然而left在right右侧, right
// 指向数组最右端也就是此时left出界的情况是有可能的.

// 再说为什么是让left > right, 循环才停止. 有这种可能, left和right都往中间
// 靠拢一步, 那么此时落到同一个数字上, 但是此时我们不知道这个数字和pivot的关系.
// 可能等于pivot, 可能比pivot大, 可能比pivot小, 比pivot小或者相等还好说. 一旦
// 比pivot大就会出现left左侧有大于pivot的情况出现.

// quick sort的逻辑就是取一个第一个元素作为pivot, 然后通过left和right两个指针把
// 除了pivot外的数组分成两个部分, 一部分(left左侧)是小于等于pivot, 一部分(right右侧)
// 是大于等于pivot. 然后我们要做的就是把pivot放到第一部分的最后一个位置即可. 因为pivot
// 就是这里面最大的, 它就应该排在最后面(当然也有可能有别的值也等于pivot), right指向的就是第一部分的最后一个位置.
// 在第一部分中其他等于pivot
// 的值和pivot选谁放在最后一个位置都是可以的, 我们选择pivot放在那里, 因为我们不知道
// 第一部分有没有和pivot相同的值, 即使知道也不知道在哪里. 更何况把pivot直接放在最后效果一样.

// 通过一个十分长的数组, 并且很正常的数组来去想象整个逻辑发生的过程. 不要想一些corner case.
// 这些corner case经过验证是可以被cover的, 因此在写逻辑的时候没必要纠结自己.
// 先写出逻辑, 再去验证corner case. 这里的corner case包括比如left一点儿没动或者right
// 一点儿没动. 我们应该想象left和right在一个很长的数组中间互相超过然后停止. 此时去想逻辑
// 就明了了.
