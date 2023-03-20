package Heap.AbstLIFOFIFO;

import Heap.DoubleList.AbstrDoubleList;
import Heap.DoubleList.IAbstrDoubleList;
import Heap.DoubleList.LinkedListException;

import java.util.Iterator;

public class AbstrLifo<T> implements IAbstrLIFOFIFO<T>{
    IAbstrDoubleList<T> list;
    public AbstrLifo() {
        list=new AbstrDoubleList<>();
    }
    @Override
    public void zrus() {
        list.zrus();

    }

    @Override
    public boolean jePrazdny() {
        return list.jePrazdny();
    }

    @Override
    public void vloz(T data) {
        list.vlozPosledni(data);

    }

    @Override
    public T odeber()  {
        try {
            return list.odeberPosledni();
        } catch (LinkedListException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator vytvorIterator() {
        return list.iterator();
    }


}
