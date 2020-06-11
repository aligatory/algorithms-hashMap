import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    public int capacity(){ return table.length;}
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
        KeyParams params = getKeyParams(key);
        return findNode(params.hash, table[params.index], key).isPresent();
    }

    @Override
    public boolean containsValue(Object value) {
        return values()
                .stream()
                .filter(v -> v.hashCode() == value.hashCode())
                .anyMatch(v -> v.equals(value));
    }

    @Override
    public String get(Object key) {
        KeyParams params = getKeyParams(key);
        Optional<Node> node = findNode(params.getHash(), table[params.getIndex()], params.getKey());
        return node.map(value -> value.value).orElse(null);
    }

    private KeyParams getKeyParams(Object key) {
        int hash = key.hashCode();
        int index = getNodeIndex(hash);
        return new KeyParams(hash, index, key);
    }

    private class KeyParams {
        public int getHash() {
            return hash;
        }

        public int getIndex() {
            return index;
        }

        public Object getKey() {
            return key;
        }

        public KeyParams(int hash, int index, Object key) {
            this.hash = hash;
            this.index = index;
            this.key = key;
        }

        private int hash;
        private int index;
        private Object key;
    }

    @Override
    public String put(String key, String value) {
        KeyParams params = getKeyParams(key);
        int index = params.index;
        int hash = params.hash;
        Node firstNode = table[index];
        if (firstNode == null) {
            table[index] = new Node(hash, key, value, null);
            nodesCount++;
        } else {
            Optional<Node> node = findNode(hash, firstNode, key);
            if (!node.isPresent()) {
                table[index] = new Node(hash, key, value, firstNode);
                nodesCount++;
            } else {
                node.get().setValue(value);
            }
        }
        if (nodesCount >= loadFactor * table.length){
            table = extendArray(table);
        }
        return value;

    }

    private Node[] extendArray(Node[] oldArray){
        Node[] newArray = new Node[oldArray.length * 2];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }

    private Optional<Node> findNode(final int hash, Node node, final Object key) {
        while (node != null) {
            if (node.hash == hash && node.key.equals(key)) {
                return Optional.of(node);
            }
            node = node.next;
        }
        return Optional.empty();
    }

    @Override
    public String remove(Object key) {
        KeyParams keyParams = getKeyParams(key);
        return removeNode(keyParams);
    }

    private String removeNode(KeyParams keyParams) {
        Node currentNode = table[keyParams.getIndex()];
        Node prevNode = null;
        String res;
        if (currentNode == null) {
            return null;
        }
        while (currentNode != null) {
            if (currentNode.key.equals(keyParams.key)) {
                res = currentNode.value;
                if (prevNode == null) {
                    if (currentNode.next == null) {
                        table[keyParams.getIndex()] = null;
                    } else {
                        table[keyParams.getIndex()] = currentNode.next;
                    }
                } else {
                    if (currentNode.next == null) {
                        prevNode.next = null;
                    } else {
                        prevNode.next = currentNode.next;
                    }
                }
                nodesCount--;
                return res;
            }
            prevNode = currentNode;
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new NotImplementedException();
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public Set<String> keySet() {
        throw new NotImplementedException();
    }

    @Override
    public Collection<String> values() {
        List<String> values = new ArrayList<>(nodesCount);
        for (Node node : table) {
            if (node != null) {
                while (node != null) {
                    values.add(node.value);
                    node = node.next;
                }
            }
        }
        return values;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        throw new NotImplementedException();
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
