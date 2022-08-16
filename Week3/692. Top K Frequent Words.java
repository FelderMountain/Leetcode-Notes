class Solution {
    static class Node {
        String word;
        int count;

        Node(String word, int count) {
            this.word = word;
            this.count = count;
        }
    }

    static class MyComparator implements Comparator<Node> {
        public int compare(Node nodeOne, Node nodeTwo) {
            int freqOne = nodeOne.count, freqTwo = nodeTwo.count;
            if (freqOne != freqTwo) {
                return freqTwo - freqOne;
            }
            return nodeOne.word.compareTo(nodeTwo.word);
        }

        public int compare(Node one, Node two) {
            return two.word.compareTo(one.word);
        }
    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Node> map = new HashMap<>();
        for (String word : words) {
            map.putIfAbsent(word, new Node(word, 0));
            map.get(word).count += 1;
        }

        Node[] nodeArray = new Node[map.size()];
        int pos = 0;
        for (Node node : map.values()) {
            nodeArray[pos] = node;
            pos += 1;
        }

        quickSelect(nodeArray, 0, nodeArray.length - 1, k);
        List<Node> candidates = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            candidates.add(nodeArray[i]);
        }
        Collections.sort(candidates, new MyComparator());
        List<String> ans = new ArrayList<>();
        for (Node node : candidates) {
            ans.add(node.word);
        }
        return ans;
    }

    public void quickSelect(Node[] nodeArray, int low, int up, int k) {
        int pivot = low, left = low + 1, right = up;
        MyComparator comparator = new MyComparator();
        while (left <= right) {
            if (comparator.compare(nodeArray[left], nodeArray[pivot]) > 0 &&
                    comparator.compare(nodeArray[right], nodeArray[pivot]) < 0) {
                swap(nodeArray, left, right);
                left += 1;
                right -= 1;
                continue;
            }
            if (comparator.compare(nodeArray[left], nodeArray[pivot]) <= 0) {
                left += 1;
            }
            if (comparator.compare(nodeArray[right], nodeArray[pivot]) >= 0) {
                right -= 1;
            }
        }
        swap(nodeArray, pivot, right);
        if (right == k - 1) {
            return;
        } else if (right < k - 1) {
            quickSelect(nodeArray, right + 1, up, k);
        } else {
            quickSelect(nodeArray, low, right - 1, k);
        }
    }

    private void swap(Node[] nodeArray, int i, int j) {
        Node temp = nodeArray[i];
        nodeArray[i] = nodeArray[j];
        nodeArray[j] = temp;
    }
}

/**
 * Quick Select的解法, 唯一要注意的就是我们要自己写一个comparator. comparator认为frequency大的, 或者
 * frequency相等, lexicographial order靠前的是小的, 应该往前排.
 * 
 * 时间复杂度: quick select就是O(log(n)), 然后再遍历前k个是O(k), 然后再把这k个sort一下就是
 * O(klogk), 再遍历得到最终的答案是O(k), 因此加一起是O(klogk + 2k + logn)最后就是O(klogk)
 * 
 * 空间复杂度: O(logn + k) quick select需要用栈, 最终的答案也需要长度为k的list.
 */

class Solution {
    public List<String> topKFrequent(String[] words, int k) {

        List<String> result = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (map.containsKey(words[i]))
                map.put(words[i], map.get(words[i]) + 1);
            else
                map.put(words[i], 1);
        }

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> a.getValue() == b.getValue() ? b.getKey().compareTo(a.getKey())
                        : a.getValue() - b.getValue());

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pq.offer(entry);
            if (pq.size() > k)
                pq.poll();
        }

        while (!pq.isEmpty())
            result.add(0, pq.poll().getKey());

        return result;
    }
}
/**
 * 使用priority queue.
 */