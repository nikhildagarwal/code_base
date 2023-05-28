package Java.data_structures.array_implementation;

import java.util.Iterator;

/**
 * Java Implementation for Dynamic List.
 * Space grows and shrinks as needed.
 * Init as -->  List<T> myList = new List<>(); //default capacity is var INIT_CAPACITY
 *              List<T> myList = new List<>(4); //set init capacity to 4
 * Methods -->  void add(T value)
 *              void remove(int index)
 *              boolean removeAll(T value)
 *              void set(int index, T value)
 *              T get(int index)
 *              List<Integer> allPositionsOf(T value)
 *              List<T> subList(int index1, int index2)
 *              T[] toArray()
 *              int capacityAvailable()
 *              boolean contains(T value)
 *              int size()
 *              boolean isEmpty()
 *              void clear()
 *              void reverse()
 *              @Override boolean equals()
 *              @Override int hashCode()
 *              @Override String toString()
 * @param <T> Generic Object
 * @author Nikhil Daehee Agarwal
 */
public class List<T> implements Iterable<T> {

    private static final int INIT_CAPACITY = 100;

    private int capacity;
    private T[] list;
    private int size;

    /**
     * Creates List Object with default capacity 10
     */
    public List() {
        this.capacity = INIT_CAPACITY;
        this.size = 0;
        this.list = (T[]) new Object[capacity];
    }

    /**
     * Creates List Object with specified capacity
     * @param capacity user specified capacity (int)
     */
    public List(int capacity) {
        if(capacity <= 0){
            throw new NegativeArraySizeException("capacity must be a positive value");
        }
        this.capacity = capacity;
        this.size = 0;
        this.list = (T[]) new Object[capacity];
    }

    /**
     * append value to the back of the List
     * @param value generic value we want to add
     */
    public void add(T value) {
        if (size == list.length) {
            grow();
        }
        list[size] = value;
        size++;
    }

    /**
     * Remove value from the list and resize the list as necessary
     * @param index index of element we want to remove
     */
    public void remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (size - 1 + capacity <= list.length) {
            shrink(index);
        } else {
            size--;
            for (int i = index; i < size; i++) {
                list[i] = list[i + 1];
            }
        }
    }

    /**
     * Remove all instances of a value from the list.
     * @param value generic object we want to remove.
     * @return true if the list changed (meaning we found an element that matched
     *         and removed it), false otherwise
     */
    public boolean removeAll(T value) {
        T[] newArray = (T[]) new Object[list.length];
        int index = 0;
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (!list[i].equals(value)) {
                newArray[index] = list[i];
                index++;
            } else {
                count++;
            }
        }
        if (count == 0) {
            return false;
        }
        size -= count;
        this.list = newArray;
        resize();
        return true;
    }

    /**
     * Change value at a specific index
     * @param index index of element that we want to change.
     * @param value generic value we want to change to.
     */
    public void set(int index, T value) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        list[index] = value;
    }

    /**
     * get value of element at a specific index in our list
     * @param index int index
     * @return generic value at specified index
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return list[index];
    }

    /**
     * return List object of all the index values where a specified value is found.
     * @param value generic value we want to find.
     * @return List object holding integer values of indexes where element was
     *         found.
     */
    public List<Integer> allPositionsOf(T value) {
        List<Integer> pos = new List<>();
        for (int i = 0; i < size; i++) {
            if (list[i].equals(value)) {
                pos.add(i);
            }
        }
        return pos;
    }

    /**
     * Method to generate a sublist of the List object
     * @param index1 left index bound
     * @param index2 right index bound
     * @return new List object containing only values from index1 to index2
     */
    public List<T> subList(int index1, int index2) {
        if (index2 == index1) {
            return new List<T>();
        }
        if (index2 < index1) {
            throw new IndexOutOfBoundsException("Right index cannot be less than the Left index");
        }
        if (index2 >= size || index2 < 0 || index1 >= size || index1 < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        List<T> newA = new List<>();
        for (int i = index1; i <= index2; i++) {
            newA.add(list[i]);
        }
        return newA;
    }

    /**
     * method to convert List object to an array
     * @return generic value array containing all elements from array
     */
    public T[] toArray() {
        T[] change = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            change[i] = list[i];
        }
        return change;
    }

    /**
     * method to relay the value of the capacity that is still available.
     * Just because there is more capacity available in the backend, does not mean
     * the user can access indexes that are higher than the size of the List
     * object.
     * Only to let the developer now how much space is available before we manually
     * grow the data structure.
     * @return int of capacity available (for developer purposes)
     */
    public int capacityAvailable() {
        return list.length - size;
    }

    /**
     * Checks to see if the List object contains an specified generic value
     * @param value generic value we are searching for.
     * @return true if found, false otherwise.
     */
    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (value.equals(list[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to get size of List
     * @return int size
     */
    public int size() {
        return size;
    }

    /**
     * method to check if List Object is empty
     * @return true if List size is 0, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list of all elements
     * resets the list as if we called the constructor.
     */
    public void clear() {
        this.size = 0;
        this.list = (T[]) new Object[capacity];
    }

    /**
     * Reverses the list in place, no extra space.
     */
    public void reverse() {
        int pR = size - 1;
        for (int i = 0; i < size / 2; i++) {
            T temp = list[i];
            list[i] = list[pR];
            list[pR] = temp;
            pR--;
        }
    }

    /**
     * Appends two List objects
     * @param l List object that we want to connect
     */
    public void append(List<T> l) {
        int newsize = size + l.size();
        int div = newsize / capacity;
        T[] newA = (T[]) new Object[(div + 1) * capacity];
        for (int i = 0; i < size; i++) {
            newA[i] = list[i];
        }
        for (int i = size; i < newsize; i++) {
            newA[i] = l.get(i - size);
        }
        this.size = newsize;
        this.list = newA;
    }

    /**
     * Implementation of Java.util iterator for advanced for loop use.
     * @return Iterator Object for use in advanced for loops.
     */
    @Override
    public Iterator<T> iterator() {
        return new selfIterator();
    }

    /**
     * private class selfIterator containing override methods for hasNext() and next().
     */
    private class selfIterator implements Iterator<T> {
        private int index;

        /**
         * return true if index is less than size, meaning that we are still withing the
         * "bounds" of the List object
         * @return true if we still have elements to loop through
         */
        @Override
        public boolean hasNext() {
            return index < size;
        }

        /**
         * method to return values to the iterator.
         * return generic value only if hasNext is true.
         * @return Generic object
         */
        @Override
        public T next() {
            if (hasNext()) {
                return list[index++];
            }
            return null;
        }
    }

    /**
     * Override method for equals in Java.Object class.
     * checks if two List objects are equal to each other.
     * @param obj List Object we want to check
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        List<T> myList = (List<T>) obj;
        if (myList.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (myList.get(i) != list[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Coverts List Object to a printable string.
     * Easier visualization.
     * @return String of format --> [ val1, val2, val3, ... , valn ]
     */
    @Override
    public String toString() {
        String ans = "[";
        for (int i = 0; i < size; i++) {
            ans += list[i];
            if (i != size - 1) {
                ans += ", ";
            }
        }
        return ans + "]";
    }

    /**
     * Overrides Object class hashCode method
     * @return hashCode of list by summing all hashcode values of contents and then taking care of overflow.
     */
    @Override
    public int hashCode(){
        long sum = 0;
        for(int i = 0;i<size;i++){
            sum += list[i].hashCode();
            sum %= Integer.MAX_VALUE;
        }
        long fin = sum % Integer.MAX_VALUE;
        fin = fin < 0 ? fin * (-1) : fin;
        return (int) fin;
    }

    /**
     * private helper method to grow dataStructure when elements exceeds the current
     * available capacity.
     */
    private void grow() {
        T[] newT = (T[]) new Object[size + capacity];
        int index = 0;
        for (int i = 0; i < size; i++) {
            newT[index] = list[i];
            index++;
        }
        this.list = newT;
    }

    /**
     * removes elements from list while resizin the data Structure down.
     */
    private void shrink(int index) {
        T[] newA = (T[]) new Object[list.length - capacity];
        int ind = 0;
        for (int i = 0; i < size; i++) {
            if (i != index) {
                newA[ind] = list[i];
                ind++;
            }
        }
        this.list = newA;
        size--;
    }

    /**
     * getter method for generic array
     * @return generic array
     */
    private T[] getList() {
        return list;
    }

    /**
     * getter method for capacity value
     * @return int capacity
     */
    private int getCapacity() {
        return capacity;
    }

    /**
     * resize the dataStructure and get rid of all extra space.
     */
    private void resize() {
        int diff = list.length - size;
        int div = diff / capacity;
        if (div == 0) {
            return;
        }
        T[] newA = (T[]) new Object[list.length - (capacity * div)];
        for (int i = 0; i < size; i++) {
            newA[i] = list[i];
        }
        this.list = newA;
    }
}