class RandomizedCollection {
    private Map<Integer, Set<Integer>> map;
    private List<Integer> list;
    private Random rand;

    public RandomizedCollection() {
        map = new HashMap<>();
        list = new ArrayList<>();
        rand = new Random();
    }

    public boolean insert(int val) {
        if (!map.containsKey(val))
            map.put(val, new LinkedHashSet<Integer>());
        map.get(val).add(list.size());
        list.add(val);
        return map.get(val).size() == 1;
    }

    public boolean remove(int val) {
        if (map.containsKey(val) && map.get(val).size() > 0) {
            int targetIdx = map.get(val).iterator().next();
            int lastIdx = list.size() - 1;
            list.set(targetIdx, list.get(lastIdx));
            map.get(val).remove(targetIdx);
            map.get(list.get(targetIdx)).add(targetIdx);
            map.get(list.get(targetIdx)).remove(list.size() - 1);
            list.remove(list.size() - 1);
            return true;
        }
        return false;
    }

    public int getRandom() {
        return list.get(rand.nextInt(list.size()));
    }
}

/**
 * 这道题的思路是这样的:
 * 根据380的思想, 我们本来是用map存数字和它的index构成的entry. 然后如果insert, 就直接insert;
 * remove的话先和最后一个元素换位置, 然后更新map中受到影响的元素的index(即将被移除的值和list中
 * 最后一个位置的值). 最后把list的最后一个元素移除掉.
 * 
 * 这里由于某个数字可以出现多次, 那么我们就要存某个数字以及该数字所在所有的index, 因此存的是数字和一个list构成的entry. insert还
 * 好说, 就直接在map里更新, 然后在list尾部添加. 如果要移除某个数字, 我们获得该数字在list中的某一个index, 然后和最后一个
 * 元素换位置, 然后更新要被移除的数字的list以及最后一个元素它的list. 这里问题就来了,
 * 我们更新最后一个元素list要做的事情是首先移除存所有数字list最后一个位置的
 * index, 然后再添加target(要被移除的数字)本来所在位置的index. 那么如果要移除某个值, 需要O(1), 自然想到了Set.
 * 那你可能会说, 既然是移除存所有数字的list中的最后一个元素. 那么该元素在map中对应的list最后一个不也应该是最靠后的
 * 那个. 如果我们光插入不移除是没毛病的, 但是一旦有移除, 那么该元素在map中对应的list的最后一个元素可能不是在整个存数字的
 * list中最靠后的, 也就是我们不能认为某个元素在map中对应的list中的元素是单调递增的.
 * 
 * 比如我们插入0, 0, 1, 1, 1
 * map中就会有: 0 : [0, 1]; 1 : [2, 3, 4]
 * 如果我们移除0, 假设移除第0个0, 那么移除后list的样子:
 * 1, 0, 1, 1
 * map的样子:
 * 0 : [1]; 1 : [2, 3, 0]
 * 此时我们发现1对应的list就变成不是单调递增了.
 * 
 * 这也就是我们为什么用set而不是list.
 * 
 * 接下来我们说为什么用LinkedHashSet. 我们为了获得一个set中的元素使用set.iterator().next(),
 * 但是hashset的implementation是比较坑爹的. 如果我们一开始插入很多元素, 让hashtable扩充的很大.
 * 然后又把元素移除了很多, 那么当我们想再获取一个元素的时候, iterator就会遍历很多没装东西的bucket,
 * 直到找到一个元素. 而LinkedHashSet用一个双向链表记录了插入的元素, 那么我们获取其中一个元素就会变得
 * 很快, 即使hashtable被扩张的很大.
 * 
 * 最后注意25, 26, 27这三行. 这三行的顺序不能换, 交换任意两行都会出问题.
 * 这是因为很有可能我们要移除的元素的index和list中最后一个元素的index相同.
 * 因此顺序就是先更新要被移除元素的set, 再更新list中最后一个元素对应的set(先增加, 再移除).
 * 最后移除list中最后一个元素.
 * 
 * 每一个method的时间复杂度都是O(1)
 * 
 */