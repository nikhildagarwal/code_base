package Java.data_structures.array_implementation;

public class Queue<T> {

    private static final int INIT_CAPACITY = 10;

    private T[] queue;
    private int end;
    private int capacity;
    private int front;

    public Queue(){
        this.capacity = INIT_CAPACITY;
        this.end = 0;
        this.queue = (T[]) new Object[this.capacity];
        this.front = 0;
    }

    public Queue(int capacity){
        if(capacity <= 0){
            throw new NegativeArraySizeException("capacity must be a positive value");
        }
        this.capacity = capacity;
        this.end = 0;
        this.queue = (T[]) new Object[this.capacity];
        this.front = 0;
    }

    public void push(T value){
        if(end == queue.length){
            grow();
        }
        queue[end] = value;
        end++;
    }

    public T peek(){
        if(this.isEmpty()){
            throw new NegativeArraySizeException("Queue is Empty");
        }
        return queue[front];
    }

    public T poll(){
        if(this.isEmpty()){
            throw new NegativeArraySizeException("Queue is Empty!");
        }
        T toReturn = queue[front];
        front++;
        if(front == capacity){
            shrink();
        }
        return toReturn;
    }

    public int size(){
        return end-front;
    }

    public boolean isEmpty(){
        return front==end;
    }

    public int capacityAvailable(){
        return queue.length-end;
    }

    @Override
    public int hashCode(){
        long sum =0;
        for(int i = front;i<end;i++){
            sum += (queue[i].hashCode());
            sum %= Integer.MAX_VALUE;
        }
        long fin = sum < 0 ? sum * (-1) : sum;
        return (int) fin;
    }

    @Override
    public boolean equals(Object obj){
        Queue<T> q = (Queue<T>) obj;
        if(this.size()!=q.size()){
            return false;
        }
        int startOfQ = q.getFront();
        T[] qArray = q.getQueue();
        for(int i = front;i<end;i++){
            if(!queue[i].equals(qArray[startOfQ])){
                return false;
            }
            startOfQ++;
        }
        return true;
    }

    @Override
    public String toString(){
        String ans = "[";
        for (int i = front; i < end; i++) {
            ans += queue[i];
            if (i != end - 1) {
                ans += ", ";
            }
        }
        return "<-out- "+ans + "] <-in-";
    }

    private T[] getQueue(){
        return queue;
    }

    private int getFront(){
        return front;
    }

    private int getEnd(){
        return end;
    }

    private int getCapacity(){
        return capacity;
    }

    private void shrink(){
        T[] newQ = (T[]) new Object[queue.length-capacity];
        int index = 0;
        for(int i = front;i<end;i++){
            newQ[index] = queue[i];
            index++;
        }
        front = 0;
        end = index;
        queue = newQ;
    }

    private void grow(){
        T[] newQ = (T[]) new Object[end + capacity];
        for(int i = front;i<end;i++){
            newQ[i] = queue[i];
        }
        this.queue = newQ;
    }

}
