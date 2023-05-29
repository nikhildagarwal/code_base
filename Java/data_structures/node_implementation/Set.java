package Java.data_structures.node_implementation;

public class Set<T> {

    private static final int DIVISOR = 256;
    private static final int BUCKETS_CAPACITY = Integer.MAX_VALUE / DIVISOR;
    private static final int HEAD = 0;
    private static final int STREAM = 1;

    private Node<Integer> front;
    private Node<Integer> end;
    private Node<T>[][] buckets;
    private int size;

    public Set(){
        this.front = null;
        this.end = null;
        this.buckets = (Node<T>[][]) new Node[BUCKETS_CAPACITY][2];
        this.size = 0;
    }

    public int size(){
        return size;
    }

    public boolean add(T value){
        int codeIndex = generateCodeIndex(value);
        addToCodesList(codeIndex);
        boolean added = addToBucket(codeIndex,value);
        if(added){
            size++;
        }
        return added;
    }

    public boolean remove(T value){
        int codeIndex = generateCodeIndex(value);
        Node<T> start = buckets[codeIndex][HEAD];
        if(start == null){
            return false;
        }
        if(start.val().equals(value)){
            buckets[codeIndex][HEAD] = buckets[codeIndex][HEAD].next();
            if(buckets[codeIndex][HEAD] ==null){
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

    private void removeFromCodeIndex(int codeIndex){
        if(front.val()==codeIndex) {
            front = front.next();
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

    private void print(Node<T> start){
        Node<T> clone = start;
        while(clone!=null){
            System.out.println(clone.val());
            clone = clone.next();
        }
    }

    private int generateCodeIndex(T value){
        int code = value.hashCode();
        code = code < 0 ? code * (-1) : code;
        return code / DIVISOR;
    }

    public static void main(String[] args){
        
    }

}
