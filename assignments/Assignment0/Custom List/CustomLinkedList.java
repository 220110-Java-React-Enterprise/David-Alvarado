import java.util.Iterator;


/**
 * Simple doubly linkedlist implementation, extending custom list interface.
 * Also implements Iterable interface. (commented out)
 * @param <T>
 */
public class CustomLinkedList<T> implements CustomListInterface<T>, Iterable<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;


    /**
     * Adds an object to the end of the linked list
     * @param t object to be added to the list
     */
    @Override
    public void add(T t) {
        Node<T> newNode = new Node<T>(t);
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    /**
     * Adds an object to the linked list at the specified index, splicing into place
     * and effectively shifting all further objects
     * @param index position to add object
     * @param t object to be added
     */
    @Override
    public void add(T t, int index) {
        //Implement this method
        if (index < 0){
            System.out.println("Unable to add a node at a negative index.");
        }else {
            if ((size < index) || (size == 0)) {
                System.out.println("You've attempted to add a node to a non existent list or outside the boundaries of your current list. ");
                System.out.println("If the list is non existent (size = 0) it will be the first node in your list.");
                System.out.println("If you added an index which is higher than the size of your list, it will be appended to the end of the list.");
                add(t);
            } else {
                Node<T> currentNode;

                if (index > (size/2)) {
                    currentNode = tail;
                    for (int i = size-1; index < i; i--) {
                        currentNode = currentNode.prev;
                    }
                }
                else {
                    currentNode = head;
                    for (int i = 0; i < index; i++) {
                        currentNode = currentNode.next;
                    }

                    }
                Node<T> newNode = new Node<>(t);
                Node<T> previousNode = currentNode.prev;

                newNode.next = currentNode;
                currentNode.prev = newNode;
                newNode.prev = previousNode;
                previousNode.next = newNode;

                size++;

                }
            }
        }

    /**
     * gets the object found at provided index position
     * @param index location of the object to get
     * @return object found at index position
     */
    @Override
    public T get(int index) {
        //Implement this method
        Node<T> currentNode;

        if (index > (size/2)) {
            currentNode = tail;
            for (int i = size-1; index < i; i--) {
                currentNode = currentNode.prev;
            }
        }
        else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }

        }

        return currentNode.obj;
    }

    /**
     * Clears the linked list by setting head and tail to null.
     *
     */
    @Override
    public void clear() {
        //Implement this method
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Searches linked list for an object using Object.equals() to compare
     * returns the index of the first matching object found. -1 if not found.
     * @param t the object to match
     * @return index of the first matching object found. -1 if not found
     */
    @Override
    public int contains(T t) {
        //Implement this method
        int index = 0;
        boolean found = false;

        if (size == 0) {
            System.out.println("Size of list is 0, there is nothing to return");
        } else {
            Node<T> currentNode = head;

            while (true) {

                if (currentNode.obj.equals(t)) {
                    found = true;
                    break;
                } else {
                    if (currentNode.next == null){
                        break;
                    }else {
                        currentNode = currentNode.next;
                        index++;
                    }
                }
            }
        }
        if (found) {
            return index;
        } else {
            return -1;
        }
    }

    /**
     * removes an object from linked list and splices the two resulting separate lists
     * together.
     * @param index the location of the object to be removed.
     */
    @Override
    public void remove(int index) {
        //Implement this method
        Node<T> currentNode;

        if (index > (size/2)) {
            currentNode = tail;
            for (int i = size-1; index < i; i--) {
                currentNode = currentNode.prev;
            }
        }
        else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }

        Node<T> previousNode = currentNode.prev;
        Node<T> nextNode = currentNode.next;

        previousNode.next = nextNode;
        nextNode.prev = previousNode;

        size--;

        currentNode.next = null;
        currentNode.prev = null;
        currentNode.obj = null;


    }


    /**
     * returns the size of the linked list
     * @return size of linked list
     */
    @Override
    public int size() {
        //Implement this method
        return size;
    }

    public void printList(){
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            System.out.println(currentNode.obj);
            currentNode = currentNode.next;
        }

    }
    /**
     * iterator implementation
     * @return returns an iterator object to traverse the linked list
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> cursor = head;

            /**
             * checks if the linked list has another node, testing if the cursor points to a node
             * or if it is null
             * @return true if the cursor points to a node, false if the cursor node reference is null
             */
            @Override
            public boolean hasNext() {
                if (cursor == null){
                    return false;
                }
                return true;
            }

            /**
             * returns the node the cursor points to, then advances the cursor to the next node
             * @return the object at the location of the cursor
             */
            @Override
            public T next() {
                T t = cursor.obj;
                cursor = cursor.next;
                return t;
            }
        };
    }


    /**
     * Private node class contains a reference to object of list type, a reference to the next node, and
     * to the previous node.
     * @param <T>
     */
    private class Node<T> {
        Node<T> next;
        Node<T> prev;
        T obj;

        /**
         * empty constructor creates an empty node
         */
        Node() {

        }

        /**
         * creates a node and stores an object by reference
         * @param t the stored object
         */
        Node(T t) {
            obj = t;
        }

        /**
         * creates a node which stores an object by reference and has a reference to another node
         * @param t object to be stored
         * @param next next node in list
         */
        Node(T t, Node<T> next) {
            this(t);
            this.next = next;
        }

        /**
         * creates a node which stores an object by reference and has refrences to two nodes,
         * previous and next in the list
         * @param t the object to be stored
         * @param next reference to next node in list
         * @param prev reference to previous node in list
         */
        Node(T t, Node<T> next, Node<T> prev) {
            this(t, next);
            this.prev = prev;
        }
    }


    /**
     * You can use this method to test the list, but it may be a good idea to add more tests
     * to make sure everything works properly. This test will print all the strings given in
     * the parameter list.
     * @param greeting - a string with a greeting message - first item added to list
     * @param goodbye - a string with a closing message - last item added to list
     * @param args - variable arguments, any number of strings which will be added to list
     */
    public void testMethod(String greeting, String goodbye, String... args) {
        System.out.println(greeting);
        for(int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        System.out.println(goodbye);
    }

    /**
     * Another method that can be used to test the list, give any number of ints in the parameter list
     * and it will return the sum of those integers.
     * @param nums - variable arguments - any number of integer values
     * @return - the sum of the integers given in the parameter list
     */
    public int testSum(Integer... nums) {
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        return sum;
    }


}
