import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHashMap implements Map<String, String> {

    private Node[] nodes;
    private double loadFactor = .75;

    public MyHashMap(int initialCapacity) {
        nodes = new Node[initialCapacity];
    }

    public MyHashMap(int initialCapacity, int loadFactor) {
        nodes = new Node[initialCapacity];
        this.loadFactor = loadFactor;
    }

    public MyHashMap() {
        nodes = new Node[16];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
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
        return null;
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
        return null;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return null;
    }

    class Node {
    }
}
