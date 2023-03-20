package Heap.AbstrHeap;

import Heap.Table.BinStromException;
import Heap.enun.eTypProhl;

import java.util.Iterator;

public interface IAbstrHeap<K extends Comparable<K>> {
    void vybuduj();
    void prebuduj(K[] arr);
    void zrus();
    boolean jePrazdny();
    void vloz(K data);
    K odeberMax() throws BinStromException;
    K zpristupniMax();
    Iterator vytvorIterator(eTypProhl typ);
}
