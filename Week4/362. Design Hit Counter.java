class HitCounter {
    Queue<Integer> q = new LinkedList<>();

    public HitCounter() {

    }

    public void hit(int timestamp) {
        q.offer(timestamp);
    }

    public int getHits(int timestamp) {
        while (!q.isEmpty()) {
            int difference = timestamp - q.peek();
            if (difference >= 300) {
                q.poll();
            } else {
                break;
            }
        }
        return q.size();
    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */

/**
 * 这个就是用Queue. 因为timestamp始终是递增的, 因此hit的时候, 我们直接把timestamp推进queue中
 * queue中的timestamp从左到右就是递增的.如果要getHits, 我们把给定的(timestamp - 300,
 * timestamp]之外的数据pop出去, 当前给定的timestamp是最大的, 也就是大于等于queue中结尾的timestamp, 因此我们只需
 * 把开头的不在该范围内的部分pop出去即可. 剩下来的元素的个数就是hits.
 * 
 * hit:
 * 时间复杂度: O(1)
 * 
 * getHits:
 * 时间复杂度: O(n) 可能要把queue中所有的元素都pop出去.
 * 
 * 整体空间复杂度是: O(n) 因为用queue来存各个hit的timestamp.
 */

class HitCounter {
    class Pair {
        int count;
        int timestamp;

        Pair(int count, int timestamp) {
            this.count = count;
            this.timestamp = timestamp;
        }
    }

    Deque<Pair> q = new LinkedList<>();
    int total = 0;

    public HitCounter() {

    }

    public void hit(int timestamp) {
        if (!q.isEmpty() && q.getLast().timestamp == timestamp) {
            q.getLast().count += 1;
        } else {
            q.offerLast(new Pair(1, timestamp));
        }
        total += 1;
    }

    public int getHits(int timestamp) {
        while (!q.isEmpty()) {
            int difference = timestamp - q.getFirst().timestamp;
            if (difference >= 300) {
                total -= q.getFirst().count;
                q.pollFirst();
            } else {
                break;
            }
        }
        return total;
    }
}
/**
 * 这种写法时当某个timestamp可能出现多次hits的时候, 我们用这种写法. 因为如果按照原来的写法, 我们就要push
 * 很多重复的timestamp. 而现在我们可以把这些重复的timestamp统一合并, 用一个pair来表示, pair包含timestamp
 * 以及在这个timestamp中的hits的个数. 因为timestamp是递增的, 于是当有hit的时候, 我们看queue结尾的pair是否
 * 有和当前的timestamp相同的timestamp, 有的话就把这个pair的count + 1, 否则我们新push进一个pair.
 * 
 * getHits也是把范围外的pair给pop出去, 但是剩下pair的个数并不是hits的个数, 因为pair中的count很可能大于1. 于是
 * 我们再维护一个total, 来告诉我们当前的hits总数. 这样就出来了.
 * 
 * 时间复杂度和空间复杂度一样.
 */