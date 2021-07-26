package cs445.rec10;

import java.util.Iterator;

public class IterableUtils {

    /**
     * Prints a collection of entries (of any type: T), using the traversal
     * behaviour of the Iterator pattern
     * @param collection  the collection to be printed
     */
    public static <T> void printAll(Iterable<T> collection) {
        // TODO: implement this method
        Iterator<T> iter = collection.iterator();

        while(iter.hasNext())
        {
            System.out.print(iter.next() + " ");
        }
        System.out.println();
    }

    /**
     * Removes all the enteries (of type: String) whose length is less than a
     * certain limit
     * @param collection  the collection to be printed
     * @param limit  the upper limit for the length of strings to be removed
     */
    public static void removeShortStrings(Iterable<String> collection, int limit) {
        // TODO: implement this method
        Iterator<String> iter = collection.iterator();

        while(iter.hasNext())
        {
            if (iter.next().length() < limit){iter.remove();}
        }

    }

    /**
     * Find the most repetitive entry (mode), in a collection of enteries (of
     * any type: T),
     * @param collection  the collection to be traversed
     * @return  the mode
     */
    public static <T> T findMode(Iterable<T> collection) {
        // TODO: implement this method
        T mode = null;
        int modeCount = 0;

        Iterator<T> outer = collection.iterator();

        while (outer.hasNext())
        {
            T curr = outer.next();
            int currCount = 0;

            Iterator<T> inner = collection.iterator();

            while(inner.hasNext())
            {
                if ( curr.equals( inner.next() ) ) { currCount++; }

                if (currCount > modeCount)
                {
                    mode = curr;
                    modeCount = currCount;
                }
            }
        }
        return mode;
    }

}

