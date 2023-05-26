package Java;
import java.util.Iterator;

/**
 * Java Implementation for List.
 * Init as -->  List<T> myList = new List<>();     //default capacity is 10
 *              List<T> myList = new List<>(4);    //set init capacity to 4
 * Methods -->  void        add(T value) 
 *              void        remove(int index)
 *              boolean     removeAll(T value)
 *              void        set(int index, T value)
 *              T           get(int index)
 *              List<Integer> allPositionsOf(T value)
 *              List<T>     subList(int index1, int index2)
 *              T[]         toArray()
 *              int         capacityAvailable()
 *              boolean     contains(T value)
 *              int         size()
 *              boolean     isEmpty()
 *              void        clear()
 *              void        reverse()
 *              @Override boolean   equals()
 *              @Override String    toString()
 */

public class List<T> implements Iterable<T> {

    private int capacity;
    private T[] list;
    private int size;

    public List(){
        this.capacity = 10;
        this.size = 0;
        this.list = (T[]) new Object[capacity];
    }

    public List(int capacity){
        this.capacity = capacity;
        this.size = 0;
        this.list = (T[]) new Object[capacity];
    }

    public void add(T value){
        if(size == list.length){
            grow();
        }
        list[size] = value;
        size++;
    }

    public void remove(int index){
        if(index>=size || index < 0){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if(size - 1 + capacity <= list.length){
            shrink(index);
        }else{
            size --;
            for(int i = index;i<size;i++){
                list[i] = list[i+1];
            }
        }
    }

    public boolean removeAll(T value){
        List<Integer> pos = this.allPositionsOf(value);
        int l = pos.size();
        if(l == 0){
            return false;
        }
        int increment = 0;
        for(int i = 0;i<l;i++){
            this.remove(pos.get(i)-increment);
            increment++;
        }
        return true;
    }

    public void set(int index, T value){
        if(index >= size || index<0){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        list[index] = value;
    }

    public T get(int index){
        if(index >= size || index<0){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return list[index];
    }

    public List<Integer> allPositionsOf(T value){
        List<Integer> pos = new List<>();
        for(int i = 0;i<size;i++){
            if(list[i].equals(value)){
                pos.add(i);
            }
        }
        return pos;
    }

    public List<T> subList(int index1, int index2){
        if(index2 == index1){
            return new List<T>();
        }
        if(index2<index1){
            throw new IndexOutOfBoundsException("Right index cannot be less than the Left index");
        }
        if(index2 >= size || index2 < 0 || index1 >= size || index1 < 0){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        List<T> newA = new List<>();
        for(int i = index1;i<=index2;i++){
            newA.add(list[i]);
        }
        return newA;
    }

    public T[] toArray(){
        T[] change = (T[]) new Object[size];
        for(int i = 0;i<size;i++){
            change[i] = list[i];
        }
        return change;
    }

    public int capacityAvailable(){
        return size % capacity;
    }

    public boolean contains(T value){
        for(T curr:list){
            if(value.equals(curr)){
                return true;
            }
        }
        return false;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        this.size = 0;
        this.list = (T[]) new Object[capacity];
    }

    public void reverse(){
        int pR = size-1;
        for(int i = 0;i<size/2;i++){
            T temp = list[i];
            list[i] = list[pR];
            list[pR] = temp;
            pR--;
        }
    }

    @Override
    public Iterator<T> iterator(){
        return new selfIterator();
    }

    private class selfIterator implements Iterator<T>{
        private int index;

        @Override
        public boolean hasNext(){
            return index<size;
        }

        @Override
        public T next(){
            if(hasNext()){
                return list[index++];
            }
            return null;
        }
    }

    @Override
    public boolean equals(Object obj){
        List<T> myList = (List<T>) obj;
        if(myList.size()!=size){
            return false;
        }
        for(int i = 0;i<size;i++){
            if(myList.get(i) != list[i]){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        String ans = "[";
        for(int i = 0;i<size;i++){
            ans += list[i];
            if(i!=size-1){
                ans += ", ";
            }
        }
        return ans + "]";
    }

    private void grow(){
        T[] newT = (T[]) new Object[size+capacity];
        int index = 0;
        for(T curr:list){
            newT[index] = curr;
            index++;
        }
        this.list = newT;
    }

    private void shrink(int index){
        T[] newA = (T[]) new Object[list.length-capacity];
        int ind = 0;
        for(int i = 0;i<size;i++){
            if(i != index){
                newA[ind] = list[i];
                ind++;
            }
        }
        this.list = newA;
        size--;
    }
}