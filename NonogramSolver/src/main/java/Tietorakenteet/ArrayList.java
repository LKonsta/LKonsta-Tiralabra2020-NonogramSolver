package Tietorakenteet;

import java.util.Iterator;



public class ArrayList<T> implements Iterable<T> {
    
    
    T[] array;
    int index;
    int koko;

    public ArrayList() {
        array = (T[]) new Object[10];
        
        index = 0;
        koko = 10;
    }
    
    public void add(T a) {
        
        if (koko <= index) {
            T[] valiArray = (T[]) new Object[koko * 2];
            
            for (int i = 0;i < array.length; i++) {
                valiArray[i] = array[i];
            }
            
            array = valiArray;
            koko *= 2;
            
        }
        array[index] = a;
        index++;
    }
    
    public void addAll(T a[]) {
        
        if (a != null) {
            for (int i = 0; i < a.length; i++) {
                this.add(a[i]);
            }
        }
    }
    
    public T get(int i) {
        if (i < index && i >= 0) {
            return array[i];
        }
        return null;
    }
    
    public void set(int i, T a) {
        if (i < index && i >= 0) {
            array[i] = a;
        }
    }
    
    public int size() {
        int valikoko = 0;
        for (int i = 0; i < koko; i++) {
            if (array[i] != null) {
                valikoko++;
            }
        }
        
        return valikoko;
    }
    
    public boolean isEmpty() {
        return (index == 0);
    }
    

    public boolean sama(ArrayList<T> a) {
        boolean vali = true;
        for (int i = 0; i < this.array.length; i++) {
            if (!(this.array[i] == (a.get(i)))) {
                vali = false;
            }
        }
        return vali;
    }
    
    public void copy(ArrayList<T> list) {
        T[] valiArray = (T[]) new Object[list.getKoko()];
            
        for (int i = 0;i < array.length; i++) {
            valiArray[i] = list.get(i);
        }
           
        array = valiArray;
        koko = list.getKoko();
        index = list.size();
    }
    
    public void flip() {
        int size = this.size();

        T[] valiArray = (T[]) new Object[this.koko];

        for (int i = 0;i < size; i++) {
            T vali = array[i];
            valiArray[i] = array[size-1-i];
            valiArray[size-1-i] = vali;
        }
            
        array = valiArray;
    }
    
    public boolean contains(T i) {
        boolean totta = false;
        for (int j = 0; j < index; j++) {
            if (array[j] == i) {
                totta = true;
            }
        }
        return totta;
    }
    
    @Override
    public String toString() {
        String palaute = "(";
        for (int i = 0; i < this.size(); i++) {
            palaute = palaute + array[i];
            if (i+1 != this.size()) {
                palaute = palaute + ", ";
            }
        }
        palaute = palaute + ")";
        return palaute;
    }

    private int getKoko() {
        return koko;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < index;
            }

            @Override
            public T next() {
                return array[i++];
            }

        };
        return it;
    }
    

    
}
