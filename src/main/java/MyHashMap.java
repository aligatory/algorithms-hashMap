

import java.util.*;
import java.util.stream.Collectors;

public class MyHashMap{
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

    
    public int size() {
        return nodesCount;
    }

    
    public boolean isEmpty() {
        return nodesCount == 0;
    }

    
    public boolean containsKey(Object key) {
        KeyParams params = getKeyParams(key);
        return findNode(params.hash, table[params.index], key).isPresent();
    }

    
    public boolean containsValue(Object value) {
        return values()
                .stream()
                .filter(v -> v.hashCode() == value.hashCode())
                .anyMatch(v -> v.equals(value));
    }

    
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
            if (node.isEmpty()) {
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
        int length = oldArray.length;
        Node[] newArray = new Node[length * 2];
        for (Node node: getNodes()){
            int index = node.hash & (newArray.length - 1);
            Node n = newArray[index];
            node.setNext(null);
            if (n == null){
                newArray[index] = node;
            }else{
                while (n.next != null){
                    n=n.next;
                }
                n.next = node;
            }
        }
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

    
    public void putAll(Map<? extends String, ? extends String> m) {
        
    }

    
    public void clear() {
        
    }

    
    public Set<String> keySet() {
        return new HashSet<>();
    }

    
    public Collection<String> values() {
        return getNodes()
                .stream()
                .map(n->n.value).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Node> getNodes() {
        var res = new ArrayList<Node>();
        for (Node node : table) {
            if (node != null) {
                while (node != null) {
                    res.add(node);
                    node = node.next;
                }
            }
        }
        return res;
    }


    public Set<Map.Entry<String, String>> entrySet() {
        return new HashSet<>();
    }


    private int getNodeIndex(int hash) {
        return (hash & (table.length - 1));
    }

    static class Node{

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


        public String getKey() {
            return key;
        }

        
        public String getValue() {
            return value;
        }

        
        public String setValue(String value) {
            this.value = value;
            return value;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
