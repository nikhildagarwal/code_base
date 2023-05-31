package Java.data_structures.node_implementation;

import Java.data_structures.Pair;

import java.util.GregorianCalendar;

public class Map<K,V> {

    private static final int DIVISOR = 512;
    private static final int BUCKETS_CAPACITY = Integer.MAX_VALUE / DIVISOR;
    private static final int HEAD = 0;
    private static final int END = 1;

    private Node<Integer> front;
    private Node<Integer> end;
    private int size;
    private Node<Pair<K,V>>[][] buckets;

    public Map(){
        this.front = null;
        this.end = null;
        this.size = 0;
        this.buckets = (Node<Pair<K,V>>[][]) new Node[BUCKETS_CAPACITY][2];
    }

    public void put(K key, V value){
        int codeIndex = genCodeIndex(key);
        addToHashCodes(codeIndex);
        addToBucket(codeIndex,key,value);
    }

    public void clear(){
        this.front = null;
        this.end = null;
        this.size = 0;
        this.buckets = (Node<Pair<K,V>>[][]) new Node[BUCKETS_CAPACITY][2];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

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

    private int genCodeIndex(K key){
        int code = key.hashCode();
        code = code < 0 ? code * (-1) : code;
        return code / DIVISOR;
    }

    public static void main(String[] args){
        Map<String,String> m = new Map<>();
        m.put("Nikhil","Rutgers University");
        m.put("Hope","Rutgers University");
        m.put("Jason","Maryland");
        m.put("Byron","NJIT");
        System.out.println(m);
        System.out.println("m size: "+m.size());
        Map<String,String> update = new Map<>();
        update.put("Aman","Rutgers University");
        update.put("Jason","Rutgers University");
        update.put("Byron","Rutgers University");
        update.put("Navin","BRHS");
        System.out.println(update);
        System.out.println("update size: "+update.size());
        m.putAll(update);
        System.out.println(m);
        System.out.println("m size: "+m.size());
        System.out.println(m.count("Rutgers University"));
    }

}
