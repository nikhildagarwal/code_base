package Java;

import Java.data.Pair;
import Java.data_structures.List;

public class Test {

    public static void main(String[] args){
        Pair<Pair<Integer,String>,Pair<List<Integer>,Boolean>> p1 = new Pair<>();
        p1.setFirst(new Pair<>(6,"sup"));
        List<Integer> l1 = new List<>();
        l1.add(6);
        l1.add(2);
        l1.add(3);
        l1.add(2);
        l1.add(7);
        p1.setSecond(new Pair<>(l1,false));
        Pair<Pair<Integer,String>,Pair<List<Integer>,Boolean>> p2 = new Pair<>(p1);
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p2.equals(p1));
        p2.setFirst(new Pair<>(6,"hi"));
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p2.equals(p1));
    }

}
