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
    
    public void addFirst(T a) {
        if (koko <= index) {
            T[] valiArray = (T[]) new Object[koko * 2];
            
            valiArray[0] = a;
            for (int i = 0; i < array.length; i++) {
                valiArray[i+1] = array[i];
            }

            array = valiArray;
            koko *= 2;

        }
        else {
            T[] valiArray = (T[]) new Object[koko+1];

            valiArray[0] = a;
            for (int i = 0; i < array.length; i++) {
                valiArray[i + 1] = array[i];
            }

            array = valiArray;
            
            index++;
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

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>()  {
            private int i = 0;
            
            @Override
            public boolean hasNext()   {
                return i < index;
            }
            
            @Override
            public T next()   {
                return array[i++];
            }
            
        };
        return it;
    }


}
