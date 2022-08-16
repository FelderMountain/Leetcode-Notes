class TimeMap {
    private Map<Integer, Map<String, String>> store;

    public TimeMap() {
        store = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        if (!store.containsKey(timestamp)) {
            store.put(timestamp, new HashMap<>());
        }
        store.get(timestamp).put(key, value);
    }

    public String get(String key, int timestamp) {
        if (store.containsKey(timestamp) && store.get(timestamp).containsKey(key)) {
            return store.get(timestamp).get(key);
        }

        for (int i = timestamp - 1; i > 0; i--) {
            if (store.containsKey(i) && store.get(i).containsKey(key)) {
                return store.get(i).get(key);
            }
        }
        return "";
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */

/**
 * 我的第一版答案. 使用的是双层hashmap.
 * 
 * 这道题学习Treemap.
 * 
 * 时间复杂度: O(n) n就是某个timestamp的值给进去, 假如没找到, 就挨个往前找, 遍历一遍.
 * 空间复杂度: O(n) n是所有set的操作
 */

class TimeMap {
    Map<String, TreeMap<Integer, String>> map;

    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        map.putIfAbsent(key, new TreeMap<>());
        map.get(key).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key))
            return "";
        String ans = "";
        TreeMap<Integer, String> currentTreeMap = map.get(key);
        Integer floorKey = currentTreeMap.floorKey(timestamp);
        if (floorKey != null) {
            ans = currentTreeMap.get(floorKey);
        }
        return ans;

    }
}

/**
 * 就是红黑树. 每个node存的是键值对构成的entry, 按照key的大小排序(或者自定义排序方法, 使用comparator来说明排序方式).
 * 然后我们可以在时间复杂度为O(logn)的前提下取到某个给定的key对应的value. 比如那个Time Based Key-Value
 * Store那道题. 我想的是有一个hashmap, key是timestamp, value呢是一个hashmap, 这个inner
 * hashmap存的是某个timestamp下的string, string这样的key value pair.
 * 现在的问题就是如果我们想要得到某个string在某个timestamp下对应的value, 那该怎么办呢? 按照我的操作,
 * 就是先看有没有这个timestamp, 有的话再看在该timestamp下有没有该string作为key的KV pair. 没有的话,
 * 从timestamp开始向下遍历(timestamp – 1, timestamp -2…一直找到timestamp是1的情况,
 * 因为最小就是1)外部的map的timestamp, 去寻找每种timestamp的inner map是否包含该string作为key的KV pair.
 * 这样就会很慢.
 * 
 * 试着想一下另一种嵌套方式. 用String-String pair的key作为外层map的key, 外层map对应的value呢是一个hashmap,
 * inner hashmap则是timestamp和string-string pair中的value组成的pair.
 * 于是我们给定一个string-string key value pair以及timestamp, 我们首先看这个string key是否存在,
 * 不存在的话, 等于是在任何timestamp下都没有插入过该string-string pair. 如果存在, 我们找到这个key对应的hashmap,
 * 这个map中呢存的是各种timestamp下以该string key对应的value的值. 此时又有一个问题,
 * 如果刚好我们要的timestamp存在那还好, 直接访问到返回即可, 如果没有呢? 就要遍历整个inner
 * map来找到最接近现在timestamp值的那个entry(该entry中的timestamp最接近我们想要的timestamp, 这是题目的要求)了,
 * 因为这个hashmap的entry不是按照timestamp排好序的.
 * 
 * 此时TreeMap就派上用场了. TreeMap就能把给定的键值对按照键的大小(键的大小可以根据natural
 * ordering也可以根据我们提供的comparator)来排序并且可以在log(n)时间获得某个key对应的value. 于是如果inner
 * map存在我们想要的timestamp, 那么直接获取, 如果没有, 那么获取此时存有的最大的timestamp即可. 毕竟红黑树就是个BST,
 * 那么获取树中最大的timestamp也就是需要log(n)时间.
 * 
 * 除了可以使用TreeMap, 我们还有别的方法. 我们忽略了一个点, 就是这道题有说过timestamp的值是不会递减的.
 * 也就是后插入的timestamp不会比前面小. 也就是对于某个KV pair, 我们先插入K, V1(timestamp1), 后插入K,
 * V2(timestamp2), timestamp2是不会小于timestamp1的. 于是我们的inner map可以换成List.
 * list中存的还是timestamp-string pair, 按照题目的说话, timestamp只会越来越大不会变小,
 * 那么这个list就是sorted了, 因为是先来后到, 对于同一个string key后插入的就会在list的后面. 对于sorted list,
 * 想要查找某个值, binary search就自然而然被想到了. 先使用binary search找我们想要的timestamp, 如果有的话就返回它,
 * 没有的话返回list中最后一个element中的value即可.
 * 
 * 需要注意的是第63行的if是必要的, 因为get可以随便, 比如我插入个timestamp是10, 我取5, 如果没这个if, 直接null
 * pointer exception了. 不能直接返回currentTreeMap.get(floorKey), floorKey可能为null.
 * 
 * 时间复杂度: O(logn)
 * 空间复杂度: O(n) n是set的个数.
 * 
 */

class TimeMap {
    static class Data {
        int timestamp;
        String value;

        Data(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    Map<String, List<Data>> map;

    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(new Data(timestamp, value));
    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key))
            return "";
        List<Data> list = map.get(key);
        return binarySearch(list, timestamp);
    }

    private String binarySearch(List<Data> list, int timestamp) {
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (list.get(middle).timestamp == timestamp) {
                return list.get(middle).value;
            } else if (list.get(middle).timestamp < timestamp) {
                if (list.get(middle + 1).timestamp > timestamp)
                    return list.get(middle).value;
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return list.get(left).timestamp <= timestamp ? list.get(left).value : "";
    }}

    /**
     * binary search的方法.
     * 
     * 这里比较特别的是如果找不到某个timestamp, 我们就要返回比这个timestamp小的那些entry中timestamp最大的
     * 那个entry.
     * 
     * 首先我们要讨论binary search最后结束时会是什么情况. 当while条件是left <= right时.
     * 1. left == right, 要么left移动, 要么right移动. 根据此时left和right指向的这个数字和target的关系. 然后结束.
     * 2. left != right, 此时left挨着right. 如果left移动, 那么还会继续走到left == right的情况,
     * 如果right移动, 那么直接结束.
     * 这样看就很乱.
     * 
     * 那么我们试着看看left < right的循环条件.
     * 首先就是left和right挨着, 那么如果target比较大, 就要移动left, 但是如果此时right也不是target, 那么此时的left就是
     * 答案(比target小的所有数字中最大的), 因此我们在移动left前先判断一下middle + 1的值. 这也就是第149行的来历.
     * 另一种是left == right跳出循环, 此时我们只需要看此时指向的值是否小于或者等于target, 如果是就返回, 如果不是那说明target
     * 太小了, 没有比target还小的值了, 于是返回“”.
     * 最后一种是right超过left跳出循环, 此时是left和right挨着, right = middle - 1导致.
     * 此时的话是target比middle指向的小. 此时left就是就是最接近middle的值了.
     * 
     * 上面这一堆讨论很难记. 还有一个更好的方法:
     */
    String answer = "";
    List<Pair> values = map.get(key);
    int left = 0;
    int right = values.size() - 1;

    while(left<=right)
    {
        int middle = left + (right - left) / 2;
        if (list.get(middle).timestamp == timestamp) {
            return list.get(middle).value;
        } else if (list.get(middle).timestamp < timestamp) {
            result = middle;
            left = middle + 1;
        } else {
            right = middle - 1;
        }
    }
/**
 * 这里就是在middle指向的数字小于target的时候, 我们记录此时middle指向的值.
 * 这样随着区间的缩小, result记录的值始终小于target. 一直到最后left > right
 * 的时候, 循环结束的前一步可能是:
 * left和right挨着, right = middle - 1. 此时是middle比target大, 使得right左移.
 * 此时result是不更新的没毛病.
 * 
 * left和right重合, 如果left移动说明middle比target小, 那么要更新一下result的值.
 * 如果right移动, 说明middle比target大, 不更新result, 没毛病.
 * 
 * 因此是每次只要出现middle比target小的情况, 我们都记录下来. 这样会越来越逼近target.
 * 
 * 这个模板要记下来.
 */