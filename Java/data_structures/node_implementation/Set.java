package Java.data_structures.node_implementation;

/**
 * Java implementation of HashSet Object.
 * Uses HashCodes to determine which bucket to add elements too.
 * Change the number of buckets that set contains by changing the value of var DIVISOR.
 * The buckets are structured with a 2-D array of size (Integer.MAX_VALUE / DIVISOR) x 2, while the elements of a bucket are stored in a linkedList.
 * Let's say we have a bucket b. b[0] will contain the pointer to the head of the LinkedList, while b[1] will contain the pointer to the last element in the LinkedList.
 * This allows us to add elements to the back of the List without losing the pointer to the head of the List.
 * Init as -->  Set<T> set = new Set<>();
 *              Set<T> set = new Set<>(0.95);  // load factor of 0.95
 * Methods -->  int size();
 *              boolean add(T value);
 *              boolean remove(T value);
 *              boolean contains(T value);
 *              void clear();
 *              boolean isEmpty();
 *              T[] toArray();
 *              @Override String toString();
 * @param <T> Generic Object Limiter
 * @author Nikhil Daehee Agarwal
 */
public class Set<T> {


    private static final int BUCKETS_CAPACITY = 16;
    private static final double LOAD = 0.75;
    private static final int HEAD = 0;
    private static final int STREAM = 1;

    private Node<Integer> front;
    private Node<Integer> end;
    private Node<T>[][] buckets;
    private int size;
    private double currLoad;
    private double load;

    /**
     * Default constructor for set object
     */
    public Set(){
        this.front = null;
        this.end = null;
        this.buckets = (Node<T>[][]) new Node[BUCKETS_CAPACITY][2];
        this.size = 0;
        this.currLoad = 0.0;
        this.load = LOAD;
    }

    /**
     * Overload Constructor for Set with given load factor.
     * @param load double loadFactor. Ratio of elements in the set to buckets available in the set.
     */
    public Set(double load){
        this.front = null;
        this.end = null;
        this.buckets = (Node<T>[][]) new Node[BUCKETS_CAPACITY][2];
        this.size = 0;
        this.currLoad = 0.0;
        this.load = load;
    }

    /**
     * size of set (number of elements)
     * @return int of the number of elements in the set
     */
    public int size(){
        return size;
    }

    /**
     * Add a Generic Object to the set
     * @param value Object to add
     * @return true if object was not previously in the array thus object has been added, false otherwise
     */
    public boolean add(T value){
        int codeIndex = generateCodeIndex(value);
        addToCodesList(codeIndex);
        boolean added = addToBucket(codeIndex,value);
        if(added){
            size++;
            currLoad = (double) size / buckets.length;
            if(currLoad>load){
                rehash();
            }
        }
        return added;
    }

    /**
     * Removes a Generic Object from the set
     * @param value Object to remove
     * @return true if Object was found thus removed, false otherwise
     */
    public boolean remove(T value){
        int codeIndex = generateCodeIndex(value);
        Node<T> start = buckets[codeIndex][HEAD];
        if(start == null){
            return false;
        }
        if(start.val().equals(value)){
            buckets[codeIndex][HEAD] = buckets[codeIndex][HEAD].next();
            if(buckets[codeIndex][HEAD] ==null){
                buckets[codeIndex][STREAM] = null;
                removeFromCodeIndex(codeIndex);
            }
            size--;
            return true;
        }
        while(start.next()!=null){
            if(start.next().val().equals(value)){
                start.next(start.next().next());
                size--;
                return true;
            }
            start = start.next();
        }
        return false;
    }

    /**
     * Checks to see if the set contains a given element
     * @param value element to check for
     * @return true if found, false if not found
     */
    public boolean contains(T value){
        int codeIndex = generateCodeIndex(value);
        Node<T> start = buckets[codeIndex][HEAD];
        while(start!=null){
            if(start.val().equals(value)){
                return true;
            }
        }
        return false;
    }

    /**
     * returns if the number of elements in the set is 0 or not
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Resets our set to empty set Object
     */
    public void clear(){
        this.front = null;
        this.end = null;
        this.buckets = (Node<T>[][]) new Node[BUCKETS_CAPACITY][2];
        this.size = 0;
        this.currLoad = 0.0;
    }

    /**
     * Returns the contents of our set as an Object array
     * @return T[] array
     */
    public T[] toArray(){
        T[] tarray = (T[]) new Object[size];
        int i = 0;
        Node<Integer> start = front;
        while(start!=null){
            Node<T> begin = buckets[start.val()][HEAD];
            while(begin!=null){
                tarray[i] = begin.val();
                i++;
                begin = begin.next();
            }
            start = start.next();
        }
        return tarray;
    }

    /**
     * Returns the set as a string to better visualize the data structure
     * @return String of set elements
     */
    @Override
    public String toString(){
        if(front == null){
            return "[]";
        }
        String ans = "[";
        Node<Integer> start = front;
        while(start.next()!=null){
            Node<T> clone = buckets[start.val()][HEAD];
            while(clone!=null){
                ans += (clone.val()+", ");
                clone = clone.next();
            }
            start = start.next();
        }
        Node<T> clone = buckets[start.val()][HEAD];
        while(clone.next()!=null){
            ans += (clone.val()+", ");
            clone = clone.next();
        }
        return ans + clone.val() + "]";
    }

    /**
     * removes node with value "code index" from LinkedList of codeIndex nodes
     * @param codeIndex int codeIndex
     */
    private void removeFromCodeIndex(int codeIndex){
        if(front.val()==codeIndex) {
            front = front.next();
            if(front==null){
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
     * adds T value to the LinkedList in the "codeIndex" bucket.
     * @param codeIndex specific bucket we want to add value to.
     * @param value value to add to set.
     * @return true if value was not found in bucket and was therefore added, false otherwise.
     */
    private boolean addToBucket(int codeIndex, T value){
        Node<T>[] pair = buckets[codeIndex];
        if(pair[HEAD]==null){
            pair[HEAD] = new Node<>(value);
            pair[STREAM] = pair[HEAD];
            return true;
        }else{
            if(!searchAndFound(pair[HEAD],value)){
                pair[STREAM].next(new Node<>(value));
                pair[STREAM] = pair[STREAM].next();
                return true;
            }
            return false;
        }
    }

    /**
     * Adds a Node<Integer> Object to our codeIndexes LinkedList.
     * Only adds the value to the LinkedList if a node with the same value is not found.
     * @param codeIndex codeIndex value to add.
     */
    private void addToCodesList(int codeIndex){
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
     * Searches a LinkedList with a head at (Node start) for a given (T value).
     * @param start head of LinkedList.
     * @param value value to search for.
     * @return true if element match found, false otherwise
     */
    private boolean searchAndFound(Node<T> start, T value){
        Node<T> clone = start;
        while(clone!=null){
            if(clone.val().equals(value)){
                return true;
            }
            clone = clone.next();
        }
        return false;
    }

    /**
     * Searches a LinkedList with a head at (Node<Integer> start) for a given (int value).
     * @param start head of LinkedList.
     * @param codeIndex int to search for.
     * @return true if int match found, false otherwise
     */
    private boolean searchAndFound(Node<Integer> start, int codeIndex){
        Node<Integer> clone = start;
        while(clone!=null){
            if(clone.val()==codeIndex){
                return true;
            }
            clone = clone.next();
        }
        return false;
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
     * IMPORTANT!! *
     * Generates codeIndex of T value.
     * Determines which bucket the element provided will be added to.
     * @param value T value to add.
     * @return int index value in buckets array.
     */
    private int generateCodeIndex(T value){
        int code = value.hashCode();
        code = code < 0 ? code * (-1) : code;
        return code % buckets.length;
    }

    /**
     * Rehashes our set and doubles its bucket capacity to take care for increasing load.
     */
    private void rehash(){
        Set<T> s = new Set<>(this.load);
        s.buckets = (Node<T>[][]) new Node[this.buckets.length * 2][2];
        Node<Integer> start = front;
        while(start!=null){
            Node<T> begin = buckets[start.val()][HEAD];
            while(begin!=null){
                s.add(begin.val());
                begin = begin.next();
            }
            start = start.next();
        }
        this.front = s.front;
        this.end = s.end;
        this.buckets = s.buckets;
        this.size = s.size;
        this.currLoad = s.currLoad;
    }

}
