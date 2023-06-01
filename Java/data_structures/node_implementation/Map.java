package Java.data_structures.node_implementation;

import Java.data_structures.Pair;

/**
 * Java Implementation for HashMap Object
 * Uses the hashCode of the key to determine bucket placement.
 * Load factor is the ration of the number of elements in our data structure to the number of buckets available to add these elements.
 * There are always 16 buckets to start out, when the load factor increases past a certain threshold (0.75 default), The map
 * will automatically rehash itself after double the number of buckets that are available.
 * Init as -->  Map<K,V> map = new Map<>();  //default loadFactor 0.75
 *              Map<K,V> map = new Map<>(0.5);  //load factor of 0.5
 * Methods -->  void put(K key, V value);
 *              void clear();
 *              int size();
 *              boolean isEmpty();
 *              Object getOrDefault(K key, Object obj);
 *              V get(K key);
 *              boolean containsKey(K key);
 *              boolean containsValue(V value);
 *              V remove(K key);
 *              int count(V value);
 *              void putAll(Map<K,V> map);
 *              @Override String toString();
 * @param <K> Key class limiter
 * @param <V> Value class limiter
 * @author Nikhil Daehee Agarwal
 */
public class Map<K,V> {

    private static final double LOAD = 0.75;
    private static final int BUCKETS_CAPACITY = 16;
    private static final int HEAD = 0;
    private static final int END = 1;

    private Node<Integer> front;
    private Node<Integer> end;
    private int size;
    private Node<Pair<K,V>>[][] buckets;
    private double load;
    private double currLoad;

    /**
     * Default Constructor for Map object
     * Load facter is set to 0.75
     */
    public Map(){
        this.front = null;
        this.end = null;
        this.size = 0;
        this.buckets = (Node<Pair<K,V>>[][]) new Node[BUCKETS_CAPACITY][2];
        this.currLoad = 0.0;
        this.load = LOAD;
    }

    /**
     * Overload constructor for Map Object
     * Load factor set to given double value.
     * @param load given load facter
     */
    public Map(double load){
        this.front = null;
        this.end = null;
        this.size = 0;
        this.buckets = (Node<Pair<K,V>>[][]) new Node[BUCKETS_CAPACITY][2];
        this.currLoad = 0.0;
        this.load = load;
    }

    /**
     * Adds a given Key and Value Pair to our HashMap
     * @param key Key object
     * @param value Value Object
     */
    public void put(K key, V value){
        int codeIndex = genCodeIndex(key);
        addToHashCodes(codeIndex);
        addToBucket(codeIndex,key,value);
        currLoad = (double) size / buckets.length;
        if(currLoad > load){
            rehash();
        }
    }

    /**
     * Clears the Map Object
     */
    public void clear(){
        this.front = null;
        this.end = null;
        this.size = 0;
        this.buckets = (Node<Pair<K,V>>[][]) new Node[BUCKETS_CAPACITY][2];
        this.currLoad = 0.0;
    }

    /**
     * method to retrieve the size of the HashMap
     * Returns how many Key-Value pairings there are
     * @return int size
     */
    public int size(){
        return size;
    }

    /**
     * returns if the there are no key-value mappings in the map
     * @return true if size is 0, false otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * gets the value of a certain key, if the key does not exist in the map,
     * returns a default value that is provided
     * @param key Key to search for and return value of.
     * @param obj default value
     * @return Value Object or default Object
     */
    public Object getOrDefault(K key, Object obj){
        int codeIndex = genCodeIndex(key);
        Node<Pair<K,V>> start = buckets[codeIndex][HEAD];
        while(start!=null){
            Pair<K,V> p = start.val();
            if(p.first().equals(key)){
                return p.second();
            }
            start = start.next();
        }
        return obj;
    }

    /**
     * getter method for value of certain key.
     * @param key Key to search map for and return value of.
     * @return Value Object if Key found, null otherwise
     */
    public V get(K key){
        int codeIndex = genCodeIndex(key);
        Node<Pair<K,V>> start = buckets[codeIndex][HEAD];
        while(start!=null){
            Pair<K,V> p = start.val();
            if(p.first().equals(key)){
                return p.second();
            }
            start = start.next();
        }
        return null;
    }

    /**
     * Checks if Map contains a given Key
     * @param key Key to search for
     * @return true if found, false otherwise
     */
    public boolean containsKey(K key){
        int codeIndex = genCodeIndex(key);
        Node<Pair<K,V>> start = buckets[codeIndex][HEAD];
        while(start!=null){
            if(start.val().first().equals(key)){
                return true;
            }
            start = start.next();
        }
        return false;
    }

    /**
     * Checks to see if Map contains a given Value
     * @param value Value to search for
     * @return ture if found, false otherwise
     */
    public boolean containsValue(V value){
        Node<Integer> start = front;
        while(start!=null){
            Node<Pair<K,V>> begin = buckets[start.val()][HEAD];
            while(begin!=null){
                if(begin.val().second().equals(value)){
                    return true;
                }
                begin = begin.next();
            }
            start = start.next();
        }
        return false;
    }

    /**
     * removes a certain Key value pairing from the Map
     * @param key Key to remove alone with Value
     * @return Value object of the deleted Key if key was found, null otherwise (no removal)
     */
    public V remove(K key){
        int codeIndex = genCodeIndex(key);
        Node<Pair<K,V>> start = buckets[codeIndex][HEAD];
        if(start == null){
            return null;
        }
        if(start.val().first().equals(key)){
            V returnVal = start.val().second();
            buckets[codeIndex][HEAD] = buckets[codeIndex][HEAD].next();
            if(buckets[codeIndex][HEAD] == null){
                buckets[codeIndex][END] = null;
                removeFromCodeList(codeIndex);
            }
            size--;
            return returnVal;
        }
        while(start.next()!=null){
            if(start.next().val().first().equals(key)){
                V returnVal = start.next().val().second();
                start.next(start.next().next());
                size--;
                return returnVal;
            }
            start = start.next();
        }
        return null;
    }

    /**
     * gets number of occurrences of a certain specified value in the Map
     * @param value value to count
     * @return int number of value occurrences
     */
    public int count(V value){
        int ans =0;
        Node<Integer> start = front;
        while(start!=null){
            Node<Pair<K,V>> begin = buckets[start.val()][HEAD];
            while(begin!=null){
                if(begin.val().second().equals(value)){
                    ans++;
                }
                begin = begin.next();
            }
            start = start.next();
        }
        return ans;
    }

    /**
     * Adds all the Key-Value pairings in a given Map to this Map.
     * @param toAdd Map to traverse for pairs.
     */
    public void putAll(Map<K,V> toAdd){
        Node<Integer> start = toAdd.front;
        while(start!=null){
            Node<Pair<K,V>> begin = toAdd.buckets[start.val()][HEAD];
            while(begin!=null){
                this.put(begin.val().first(),begin.val().second());
                begin = begin.next();
            }
            start = start.next();
        }
    }

    /**
     * Override Java Object toString() method.
     * Provides better visualization of HashMap
     * @return String of all Key-Value pairings
     */
    @Override
    public String toString(){
        String ans = "Map\n";
        Node<Integer> start = front;
        while(start!=null) {
            Node<Pair<K, V>> begin = buckets[start.val()][HEAD];
            while (begin != null) {
                ans += begin.val().toString() + "\n";
                begin = begin.next();
            }
            start = start.next();
        }
        return ans + "----------";
    }

    /**
     * removes node with value "code index" from LinkedList of codeIndex nodes
     * @param codeIndex int codeIndex node value
     */
    private void removeFromCodeList(int codeIndex){
        if(front.val()==codeIndex){
            front = front.next();
            if(front == null){
                end = null;
            }
            return;
        }
        Node<Integer> start = front;
        while(start.next()!=null){
            if(start.next().val()==codeIndex){
                start.next(start.next().next());
                return;
            }
            start = start.next();
        }
    }

    /**
     * Adds Node Object with value Pair<K,V> to linkedList at a specific index in our buckets array.
     * @param codeIndex index of array
     * @param key Key Object
     * @param value Value Object
     */
    private void addToBucket(int codeIndex, K key, V value){
        Node<Pair<K,V>>[] thisList = buckets[codeIndex];
        if(thisList[HEAD] == null){
            thisList[HEAD] = new Node<>(new Pair<>(key,value));
            size++;
            thisList[END] = thisList[HEAD];
        }else{
            Node<Pair<K,V>> start = thisList[HEAD];
            while(start!=null){
                if(start.val().first().equals(key)){
                    start.val().setSecond(value);
                    return;
                }
                start = start.next();
            }
            thisList[END].next(new Node<>(new Pair<>(key,value)));
            size++;
            thisList[END] = thisList[END].next();
        }
    }

    /**
     * Searches a given LinkedList of Integers for a specified value "value".
     * Checks to see if this value exists.
     * @param start Head of LinkedList
     * @param value value to search for.
     * @return true if value found, false otherwise.
     */
    private boolean searchAndFound(Node<Integer> start, int value){
        Node<Integer> clone = start;
        while(clone!=null){
            if(clone.val()==value){
                return true;
            }
            clone = clone.next();
        }
        return false;
    }

    /**
     * Adds generated indexValue to linkedList of active Index values.
     * This allows us to traverse our whole map more efficiently by only acessing buckets with values in them.
     * If a bucket has no values, it is considered inactive and the index value for that bucket will not be found
     * in this LinkedList
     * @param codeIndex index corresponding to buckets array
     */
    private void addToHashCodes(int codeIndex){
        if(front == null){
            front = new Node<>(codeIndex);
            end = front;
        }else{
            if(!searchAndFound(front,codeIndex)){
                end.next(new Node<>(codeIndex));
                end = end.next();
            }
        }
    }

    /**
     * Prints LinkedList to console.
     * @param start head of LinkedList
     */
    private void print(Node start){
        Node clone = start;
        while(clone!=null){
            System.out.println(clone.val());
            clone = clone.next();
        }
    }

    /**
     * Generates the index of the bucket for the Key
     * @param key Key value
     * @return int index corresponding to buckets array
     */
    private int genCodeIndex(K key){
        int code = key.hashCode();
        code = code < 0 ? code * (-1) : code;
        return code % buckets.length;
    }

    /**
     * When the load factor of our map is greater than the specified max Load of the Map Object,
     * The map will double the number of buckets that are available and the map will rehash.
     */
    private void rehash(){
        Map<K,V> map = new Map<>(load);
        map.buckets = (Node<Pair<K,V>>[][]) new Node[buckets.length * 2][2];
        Node<Integer> start = front;
        while(start!=null){
            Node<Pair<K,V>> begin = buckets[start.val()][HEAD];
            while(begin!=null){
                map.put(begin.val().first(),begin.val().second());
                begin = begin.next();
            }
            start = start.next();
        }
        this.front = map.front;
        this.end = map.end;
        this.buckets = map.buckets;
        this.size = map.size;
        this.currLoad = map.currLoad;
    }

}
