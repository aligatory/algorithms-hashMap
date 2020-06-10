import jdk.internal.jline.internal.Nullable;

import java.util.*;

public class MyHashMap implements Map<String, String> {
    private int nodesCount;
    private Node[] table;
    private double loadFactor = .75;

    public MyHashMap(int initialCapacity) {
        table = new Node[initialCapacity];
    }

    public MyHashMap(int initialCapacity, int loadFactor) {
        table = new Node[initialCapacity];
        this.loadFactor = loadFactor;
    }

    public MyHashMap() {
        table = new Node[16];
    }

    @Override
    public int size() {
        return nodesCount;
    }

    @Override
    public boolean isEmpty() {
        return nodesCount == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hash = key.hashCode();
        int index = getNodeIndex(hash);
        return findSimilarNode(hash, table[index], key).isPresent();
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public String get(Object key) {
        return null;
    }

    @Override
    public String put(String key, String value) {
        int hash = key.hashCode();
        int index = getNodeIndex(hash);
        Node firstNode = table[index];
        if (firstNode == null) {
            table[index] = new Node(hash, key, value, null);
            nodesCount++;
        } else {
            Optional<Node> node = findSimilarNode(hash, firstNode, key);
            if (!node.isPresent()) {
                table[index] = new Node(hash, key, value, firstNode);
                nodesCount++;
            } else {
                node.get().setValue(value);
            }
        }

        return value;

    }

    private Optional<Node> findSimilarNode(final int hash, @Nullable Node node, final Object key) {
        if (node == null) return Optional.empty();
        while (node.next != null) {
            if (node.hash == hash && node.key.equals(key)) {
                return Optional.of(node);
            }
            node = node.next;
        }
        return Optional.empty();
    }

    @Override
    public String remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        List<String> values = new ArrayList<>(nodesCount);
        // TODO Node iterator
        return null;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return null;
    }


    private int getNodeIndex(int hash) {
        return (hash & (table.length - 1));
    }

    static class Node implements Entry<String, String> {

        final int hash;
        final String key;
        String value;
        Node next;

        Node(int hash, String key, String value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }


        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String setValue(String value) {
            this.value = value;
            return value;
        }
    }
}
