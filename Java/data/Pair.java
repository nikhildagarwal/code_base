package Java.data;

public class Pair<A, B> {

    private A value1;
    private B value2;

    public Pair() {
        this.value1 = null;
        this.value2 = null;
    }

    public Pair(A a, B b) {
        this.value1 = a;
        this.value2 = b;
    }

    public Pair(Pair<A,B> p){
        this.value1 = p.getFirst();
        this.value2 = p.getSecond();
    }

    public A getFirst() {
        return value1;
    }

    public B getSecond() {
        return value2;
    }

    public void setFirst(A a) {
        this.value1 = a;
    }

    public void setSecond(B b) {
        this.value2 = b;
    }

    public void set(A a, B b) {
        this.value1 = a;
        this.value2 = b;
    }

    @Override
    public boolean equals(Object obj) {
        Pair<A, B> newPair = (Pair<A, B>) obj;
        if (newPair.getFirst().equals(value1) && newPair.getSecond().equals(value2)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String ans = "<";
        ans += value1;
        ans += ": ";
        ans += value2;
        return ans + ">";
    }

    public static void main(String[] args) {
        Pair<Integer, String> myPair = new Pair<>();
        System.out.println(myPair);
    }
}
