package Heap.AbstrHeap;

import Heap.AbstLIFOFIFO.AbstrLifo;
import Heap.AbstLIFOFIFO.IAbstrLIFOFIFO;
import Heap.Table.BinStromException;
import Heap.enun.eTypProhl;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrHeap<K extends Comparable<K>> implements IAbstrHeap<K> {

    private Arr<K>[] arr;
    private int size;

    public AbstrHeap() {
        arr = new Arr[10];
    }

    public static class Arr<K> {
        public K element;

        public Arr(K element) {
            this.element = element;
        }
    }

    @Override
    public void vybuduj() {
        for (int j = size / 2; j > 0; j--) {
            for (int i = 1; i < size; i *= 2) {
                int smallestChild;
                if (i * 2 <= size) {
                    if (i * 2 + 1 < size)
                        smallestChild = arr[i * 2].element.compareTo(arr[i * 2 + 1].element) < 0 ? i * 2 : i * 2 + 1;
                    else {
                        smallestChild = i * 2;

                    }

                } else {
                    smallestChild = 0;
                }

                while (smallestChild != 0 && arr[smallestChild].element.compareTo(arr[i].element) < 0) {
                    K pomoc = arr[smallestChild].element;
                    arr[smallestChild] = new Arr<>(arr[i].element);
                    arr[i] = new Arr<>(pomoc);
                }

            }
        }
    }

    @Override
    public void prebuduj(K[] array) {
        size=array.length;
        arr = new Arr[size+1];
        for (int i = 0; i < size; i++) {
            arr[i + 1] = new Arr<>(array[i]);
        }
        vybuduj();


    }

    @Override
    public void zrus() {
        this.arr = null;
        this.size = 0;


    }

    @Override
    public boolean jePrazdny() {
        return arr == null;
    }

    @Override
    public void vloz(K data) {
        if (jePrazdny()) {
            arr = new Arr[10];
            arr[1] = new Arr(data);
            size = 1;
        } else if (size == 0) {
            arr[1].element = data;
            size++;

        } else {
            if (size + 1 >= arr.length) {
                Arr<K>[] newArr = new Arr[arr.length * 2];
                if (arr.length - 1 >= 0) System.arraycopy(arr, 1, newArr, 1, arr.length - 1);
                arr = newArr;
            }
            arr[size + 1] = new Arr<>(data);
            size++;
            for (int i = size; i > 0; i = i / 2) {
                if (i / 2 > 0 && arr[i / 2].element.compareTo(arr[i].element) > 0) {
                    K pomoc = arr[i].element;
                    arr[i] = new Arr<>(arr[i / 2].element);
                    arr[i / 2] = new Arr<>(pomoc);


                }

            }
        }

    }


    @Override
    public K odeberMax() throws BinStromException {
        if (jePrazdny()) throw new BinStromException("Prazdny");
        K last = arr[1].element;
        if (size == 1) {
            zrus();
            return last;
        } else {
            arr[1] = new Arr<>(arr[size].element);
            arr[size] = null;
            size--;
            for (int i = 1; i < size; i *= 2) {
                int smallestChild;
                if (i * 2 <= size) {
                    if (i * 2 + 1 < size)
                        smallestChild = arr[i * 2].element.compareTo(arr[i * 2 + 1].element) < 0 ? i * 2 : i * 2 + 1;
                    else {
                        smallestChild = i * 2;

                    }

                } else {
                    smallestChild = 0;
                }

                if (smallestChild != 0 && arr[smallestChild].element.compareTo(arr[i].element) < 0) {
                    K pomoc = arr[smallestChild].element;
                    arr[smallestChild] = new Arr<>(arr[i].element);
                    arr[i] = new Arr<>(pomoc);
                    i = smallestChild;
                }

            }
        }


        return last;
    }

    @Override
    public K zpristupniMax() {
        return arr[1].element;
    }

    @Override
    public Iterator vytvorIterator(eTypProhl typ) {
        switch (typ) {
            case SIRKA -> {
                return new Iterator() {
                    int i = 0;

                    @Override
                    public boolean hasNext() {
                        if (i + 1 <= size) return true;
                        return false;
                    }

                    @Override
                    public K next() {
                        i++;
                        return arr[i].element;

                    }
                };

            }
            case HLOUBKA -> {

                IAbstrLIFOFIFO<Integer> zasobnik = new AbstrLifo<>();
                zasobnik.vloz(1);
                return new Iterator<>() {
                    int i = 1;

                    @Override
                    public boolean hasNext() {
                        return !zasobnik.jePrazdny();
                    }

                    @Override
                    public K next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        while (i * 2 <= size) {
                            i = i * 2;
                            zasobnik.vloz(i);
                        }
                        int elem = zasobnik.odeber();

                        if (elem * 2 + 1 <= size) {
                            i = elem * 2 + 1;
                            zasobnik.vloz(i);
                        }

                        return arr[elem].element;
                    }
                };

            }
        }
        return null;
    }
}
