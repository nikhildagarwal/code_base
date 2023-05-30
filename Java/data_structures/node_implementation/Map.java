package Java.data_structures.node_implementation;

import Java.data_structures.Pair;

public class Map<K,V> {

    private static final int DIVISOR = 256;
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
        Map<Integer,String> m = new Map<>();
        m.put(3,"jo");
        m.put(264,"no");
        m.put(5,"jpdl");
        System.out.println(m);
        m.put(93492,"Nki");
        System.out.println(m);
        m.put(7,"Nki");
        System.out.println(m);
        m.put(7,"Hope Loves me");
        System.out.println(m);
    }

}
