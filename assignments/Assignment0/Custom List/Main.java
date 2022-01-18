import java.util.Iterator;

public class Main {
    public static void main(String[] args){
        // Linked list test. It initially adds 9 numbers to list in order.
        CustomLinkedList<Integer> myLinkedList = new CustomLinkedList<Integer>();

        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        myLinkedList.add(4);
        myLinkedList.add(6);
        myLinkedList.add(7);
        myLinkedList.add(8);
        myLinkedList.add(9);
        myLinkedList.add(10);
        Iterator<Integer> it = myLinkedList.iterator();

        //Prints size of list and all nodes in list.
        System.out.println("Size before inserting 5 at index 4: " + myLinkedList.size());
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }
        // adds missing integer in list at the correct index (5,4) [value, index]
        // Prints new size and all nodes in list.
        System.out.println("\n");
        myLinkedList.add(5,4);

        System.out.println("Size after inserting 5 at index 4: " + myLinkedList.size());

        it = myLinkedList.iterator();
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }
        // Removes a node at index 6.
        //Prints new size and all nodes in list.
        System.out.println("\n");
        myLinkedList.remove(6);

        System.out.println("size after removing item from index 6: " + myLinkedList.size());

        it = myLinkedList.iterator();
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }

        //Clears entire list.
        // Prints new list size and attempts to print list.
        System.out.println("\n");
        myLinkedList.clear();
        System.out.println("size after clearing list: " + myLinkedList.size());
        it = myLinkedList.iterator();
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }

    }
}
