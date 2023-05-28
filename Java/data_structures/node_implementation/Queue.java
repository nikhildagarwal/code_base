package Java.data_structures.node_implementation;

public class Queue<T> {

    private Node<T> front;
    private Node<T> end;
    private int size;

    public Queue(){
        this.front = null;
        this.end = null;
        this.size = 0;
    }

    public void push(T value){
        if(front==null){
            end = new Node<>(value);
            front = end;
        }else{
            Node<T> adder = new Node<>(value);
            end.next(adder);
            end = end.next();
        }
        size++;
    }

    public T peek(){
        if(front==null){
            throw new NullPointerException("Queue is empty!");
        }
        return front.val();
    }

    public T poll(){
        if(front==null){
            throw new NullPointerException("Queue is empty!");
        }
        T valToReturn = front.val();
        front = front.next();
        if(front == null){
            end = null;
        }
        size--;
        return valToReturn;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean contains(T value){
        Node<T> start = front;
        while(start!=null){
            if(start.val().equals(value)){
                return true;
            }
            start = start.next();
        }
        return false;
    }

}
