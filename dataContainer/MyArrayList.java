package dataContainer;

import java.util.AbstractList;
import java.util.Comparator;

public class MyArrayList<E> extends AbstractList<E>{
	public static final int DEFAULT_CAPACITY = 20;
    private int capacity;
    private int size = 0;
    private Object[] helper;
    public int length;

    private Object[] array = new Object[DEFAULT_CAPACITY];

    public MyArrayList(int capacity) {
        array = new Object[capacity];
        this.capacity = capacity;
    }
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean add(E e) {
        if(size >= capacity) {
            grow(1.5f);
        }
        array[size] = e;
        size++;
        return true;
    }

    public boolean add(E e, Comparator<E> comp) {
        add(e);
        doMergeSort(comp);
        return true;
    }
// public boolean sort(Comparator<E> comp) {
//       doMergeSort(comp);
//        return true;
//    }

    @Override
    public E remove(int index) {
        E e = (E) array[index];
        for(int i=index; i<size-1; i++) {
            array[i] = array[i + 1];
        }
        return e;
    }
    private void doMergeSort(Comparator<E> comp) {
        if(size < 2) {
            return;
        }
        this.helper = new Object[size];
        mergeSort(0, size-1, comp);
    }

    private void mergeSort(int low, int high, Comparator<E> comp) {
        if(low < high) {
            int mid = (low + high) / 2;
            mergeSort(low, mid, comp);
            mergeSort(mid+1, high, comp);
            merge(low, mid, high, comp);
        }
    }

    //expands from course slides
    private void merge(int low, int middle,  int high, Comparator<E> comp) {

        for(int i = low ; i <= high; i++) {
            helper[i] = array[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while(i <= middle && j <= high) {
            if(comp.compare((E) helper[i], (E) helper[j]) <= 0) {
                array[k] = helper[i];
                i++;
            } else {
                array[k] = helper[j];
                j++;
            }
            k++;
        }

        while(i <= middle) {
            array[k] = helper[i];
            k++;
            i++;
        }

        while(j <= high) {
            array[k] = helper[j];
            k++;
            j++;
        }
    }

    private void grow(float rate) {
        capacity = (int) (rate * capacity);
        Object[] arr = new Object[capacity];
        for(int i=0; i< array.length; i++) {
            arr[i] = array[i];
        }
        array = arr;
    }

    @Override
	public E get(int index) {
        if(index >= size) {
            return null;
        }
        return (E) array[index];
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

}

