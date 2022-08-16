class RandomizedSet {
    List<Integer> list;
    Map<Integer, Integer> map;
    Random rand;

    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
        rand = new Random();
    }

    public boolean insert(int val) {
        if (!map.containsKey(val)) {
            list.add(val);
            map.put(val, list.size() - 1);
            return true;
        }
        return false;
    }

    public boolean remove(int val) {
        if (map.containsKey(val)) {
            int candidateIdx = map.get(val);
            swap(list, candidateIdx, list.size() - 1);
            map.put(list.get(candidateIdx), candidateIdx);
            map.remove(val);
            list.remove(list.size() - 1);
            return true;
        }
        return false;
    }

    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}

/**
 * 骚操作就是, 为了移除中间的某个element, 先让它和最后一个element交换位置, 然后移除最后一个element即可.
 * 这样就是O(1)的remove了. 但是这会破坏原来元素之间的相对位置, 本来是最后一个的element被移动到了中间某个
 * 位置. 当然这道题里面, 这不影响什么, 但是不要把这种操作用到那些在意元素间相对位置不变的情况.
 * 
 * 同样地第27行代码的位置很关键. 有一个edge case就是如果list中只剩一下一个元素, 而且我们刚好要移走它. 如果
 * 27行代码写的比较靠前, 比如插在24和25行之间, 那么25行就会抛出异常因为此时list中没有元素了. 因此我们要把这行
 * 代码放到25行之后.
 * 
 * 时间复杂度: 每一个method都是O(1) 因为这是题目规定.
 * 空间复杂度: O(n) n为list中元素最多的时候元素的个数.
 */