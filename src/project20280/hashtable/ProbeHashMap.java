package project20280.hashtable;

import project20280.interfaces.Entry;

import java.util.ArrayList;

public class ProbeHashMap<K, V> extends AbstractHashMap<K, V> {
    private MapEntry<K, V>[] table;
    private final MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap() {
        super();
    }

    /**
     * Creates a hash table with given capacity and prime factor 109345121.
     */
    public ProbeHashMap(int cap) {
        super(cap);
    }

    /**
     * Creates a hash table with the given capacity and prime factor.
     */
    public ProbeHashMap(int cap, int p) {
        super(cap, p);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void createTable() {
        table = new MapEntry[capacity];
    }

    /**
     * Returns index of slot containing key k, or -(a+1) where a is the index
     * of the first available slot, if k is not found.
     */
    int findSlot(int h, K k) {
        int avail = -1;
        int j = h;
        do {
            if (isAvailable(j)) {
                if (avail == -1) avail = j;        // first available slot noted
                if (table[j] == null) break;       // search cannot continue past null
            } else if (table[j].getKey().equals(k)) {
                return j;                          // key found
            }
            j = (j + 1) % capacity;               // linear probing to next slot
        } while (j != h);
        return -(avail + 1);                       // key not found; return available slot
    }

    /**
     * Returns true if slot j is available (null or DEFUNCT).
     */
    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    @Override
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;
        return table[j].getValue();
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0) {
            // key already exists, update value
            return table[j].setValue(v);
        }
        // insert at first available slot
        table[-(j + 1)] = new MapEntry<>(k, v);
        n++;
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;
        V old = table[j].getValue();
        table[j] = DEFUNCT;
        n--;
        return old;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> entries = new ArrayList<>();
        for (int j = 0; j < capacity; j++) {
            if (!isAvailable(j))
                entries.add(table[j]);
        }
        return entries;
    }

    public String toString() {
        return entrySet().toString();
    }

    public static void main(String[] args) {
        ProbeHashMap<Integer, String> m = new ProbeHashMap<>();
        m.put(1, "One");
        m.put(10, "Ten");
        m.put(11, "Eleven");
        m.put(20, "Twenty");

        System.out.println("m: " + m);

        m.remove(11);
        System.out.println("m: " + m);
    }
}