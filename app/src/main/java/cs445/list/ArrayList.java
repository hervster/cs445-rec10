package cs445.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that implements the ADT list using a resizable array.
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author William C. Garrison III
 * @version 4.1
 */
public class ArrayList<E> implements ListInterface<E> {

    private E[] list;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            initialCapacity = DEFAULT_CAPACITY;
        }

        @SuppressWarnings("unchecked")
        E[] tempList = (E[])new Object[initialCapacity];
        list = tempList;
        size = 0;
    }

    public void add(E newEntry) {
        add(size, newEntry);
    }

    public void add(int newPosition, E newEntry) {
        if (newPosition >= 0 && newPosition <= size) {
            if (newPosition <= size - 1) {
                makeRoom(newPosition);
            }
            list[newPosition] = newEntry;
            size++;
            ensureCapacity();
        } else {
            throw new IndexOutOfBoundsException("Given position " + newPosition +
                    " for add's new entry is out of bounds.");
        }
    }

    public E remove(int position) {
        return remove(position, false);
    }

    /**
     * This is a helper method that allows me to print an error on regular calls
     * to remove, but not if called through the iterator.
     */
    private E remove(int position, boolean fromIterator) {
        if (!fromIterator) {
            System.err.print("Call to remove() detected! ");
            System.err.println("In this lab, you should use iterators instead.");
        }
        if (position >= 0 && position <= size - 1) {
            assert !isEmpty();
            E result = list[position];

            if (position < size - 1) {
                removeGap(position);
            }

            size--;
            return result;
        } else {
            throw new IndexOutOfBoundsException("Illegal position " + position +
                    " given to remove operation.");
        }
    }

    public void clear() {
        for (int index = 0; index < size; index++) {
            list[index] = null;
        }

        size = 0;
    }

    public E set(int position, E newEntry) {
        if (position >= 0 && position < size) {
            assert !isEmpty();
            E originalEntry = list[position];
            list[position] = newEntry;
            return originalEntry;
        } else {
            throw new IndexOutOfBoundsException("Illegal position " + position +
                    " given to replace operation.");
        }
    }

    public E get(int position) {
        System.err.print("Call to get() detected! ");
        System.err.println("In this lab, you should use iterators instead.");
        if (position >= 0 && position < size) {
            assert !isEmpty();
            return list[position];
        } else {
            throw new IndexOutOfBoundsException("Illegal position " + position +
                    " given to getEntry operation.");
        }
    }

    public E[] toArray() {
        @SuppressWarnings("unchecked")
        E[] result = (E[])new Object[size];

        for (int index = 0; index < size; index++) {
            result[index] = list[index];
        }

        return result;
    }

    public boolean contains(E entry) {
        return indexOf(entry) >= 0;
    }

    public int indexOf(E entry) {
        int result = -1;

        for (int index = 0; result < 0 && index < size; index++) {
            if ((entry == null && list[index] == null) ||
                    (entry != null && entry.equals(list[index]))) {
                result = index;
            }
        }

        return result;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        int capacity = list.length;
        if (size >= capacity) {
            int newCapacity = 2 * capacity;
            list = Arrays.copyOf(list, newCapacity);
        }
    }

    private void makeRoom(int newPosition) {
        assert (newPosition >= 0) && (newPosition < size);

        for (int index = size - 1; index >= newPosition; index--) {
            list[index + 1] = list[index];
        }
    }

    private void removeGap(int position) {
        assert (position >= 0) && (position < size);

        int removedIndex = position;
        int lastIndex = size;

        for (int index = removedIndex; index < lastIndex; index++) {
            list[index] = list[index + 1];
        }
    }

    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<E> {
        private int nextIndex;
        private boolean wasNextCalled;

        private ArrayListIterator() {
            nextIndex = 0;
            wasNextCalled = false;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public E next() {
            if (hasNext()) {
                wasNextCalled = true;
                E next = list[nextIndex];
                nextIndex++;
                return next;
            } else {
                throw new NoSuchElementException("No more elements to iterate");
            }
        }

        public void remove() {
            if (wasNextCalled) {
                // Call the containing object's remove method
                ArrayList.this.remove(nextIndex-1, true);
                nextIndex--;
                wasNextCalled = false;
            } else {
                throw new IllegalStateException("Cannot call remove now");
            }
        }
    }

}

