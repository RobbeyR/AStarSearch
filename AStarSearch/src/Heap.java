/*
 * @author Roberto Rodriguez
 * @date 10/23/2018
 */
public class Heap {

    private Node[] heap;
    private int size;

    //constructor to set up size and a min heap of nodes
    public Heap () {
        heap = new Node[225];
        size = 0;
    }

    //get size of heap
    public int getSize () {
        return size;
    }

    //add a node to heap
    public void add (Node c) {

        //Make sure the heap isn't full
        if (size + 2 > heap.length) {
            System.out.println ("The heap is full");
            return;
        }

        //increase the size
        size ++;

        //add new object to the next open position in the heap
        heap [size] = c;

        //create a variable to keep track of where our object is in the heap
        int index = size;

        //continue to compare the object to it's parents until it reaches the root
        while (index > 1) {

            //grab parent's index
            int parentIndex = index / 2;

            //compare object to it's parent to see if we need to swap it
            if (heap[index].getF () < heap[parentIndex].getF()) {

                //swap objects
                Node temporary = heap[index];
                heap[index] = heap[parentIndex];
                heap[parentIndex] = temporary;

                //update index to parent's index after swap
                index = parentIndex;
            } else {
                //parent value is smaller...no swap needed...break out
                break;
            }
        }
    }

    public Node[] getHeap() {
        return heap;
    }

    //remove node from heap
    public Node remove () {

        //make sure the heap isn't empty
        if (size == 0) {
            System.out.println ("The heap is already empty");
            return null;
        }

        //store temporary reference to root object, so we can we return it at the end
        Node temporary = heap [1];

        //move object in the last position to the root
        heap [1] = heap [size];
        heap [size] = null;
        size--;


        //store the index of the object we moved to the root
        int index = 1;

        //continue to compare index to its children as long as there are children
        while (index <= size / 2) {

            //store index and values of children
            int leftChildIndex = index * 2;
            int rightChildIndex = leftChildIndex + 1;

            int leftChildValue = heap [leftChildIndex].getF ();
            int rightChildValue = Integer.MAX_VALUE;

            //is there a right child
            if (rightChildIndex <= size) {
                rightChildValue = heap [rightChildIndex].getF ();
            }

            //determine the smaller of the two children
            int smallerValue;
            int smallerIndex;

            if (rightChildValue < leftChildValue) {
                smallerValue = rightChildValue;
                smallerIndex = rightChildIndex;
            } else {
                smallerValue = leftChildValue;
                smallerIndex = leftChildIndex;
            }

            //determine if a swap should be made with the parent and the smaller child
            if (heap[index].getF () > smallerValue) {

                //swap
                Node swap = heap [index];
                heap [index] = heap [smallerIndex];
                heap [smallerIndex] = swap;

                //update the index since we moved it to a child position
                index = smallerIndex;
            } else {
                //parent value is smaller, no swap needed, break out
                break;
            }

        }

        //return the original root
        return temporary;

    }


}
