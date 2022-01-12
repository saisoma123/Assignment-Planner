//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P11 Assignment Planner
// Course: CS 300 Fall 2021
//
// Author: Surya Somayyajula
// Email: somayyajula@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: (name of your pair programming partner)
// Partner Email: (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: https://stackoverflow.com/questions/58895261/fail-in-percolating-down-a-min-
// binary-heap-under-a-specific-case-of-children, this resource helped with swapping in the
//////////////// percolateDown method
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based heap implementation of a priority queue containing Assignments. Guarantees the
 * min-heap invariant, so that the Assignment at the root should have the earliest due date, and
 * children always have a due date after or at the same time as their parent. The root of a
 * non-empty queue is always at index 0 of this array-heap.
 */
public class AssignmentQueue implements PriorityQueueADT<Assignment>, Iterable<Assignment> {
  private Assignment[] queue; // array min-heap of assignments representing this priority queue
  private int size; // size of this priority queue


  /**
   * Creates a new empty AssignmentQueue with the given capacity
   * 
   * @param capacity Capacity of this AssignmentQueue
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public AssignmentQueue(int capacity) {
    // TODO complete this implementation
    if (capacity <= 0) {
      throw new IllegalArgumentException("The capacity is not a positive integer");
    }
    queue = new Assignment[capacity];
  }

  /**
   * Checks whether this AssignmentQueue is empty
   * 
   * @return {@code true} if this AssignmentQueue is empty
   */
  @Override
  public boolean isEmpty() {
    // TODO complete this implementation
    return size == 0;
  }

  /**
   * Returns the size of this AssignmentQueue
   * 
   * @return the size of this AssignmentQueue
   */
  @Override
  public int size() {
    // TODO complete this implementation
    return size;
  }

  /**
   * Returns the capacity of this AssignmentQueue
   * 
   * @return the capacity of this AssignmentQueue
   */
  public int capacity() {
    // TODO complete this implementation
    // capacity is the length of the queue
    return queue.length;
  }


  /**
   * Removes all elements from this AssignmentQueue
   */
  @Override
  public void clear() {
    // TODO complete this implementation
    // Sets all the elements to null
    for (int i = 0; i < queue.length; i++) {
      queue[i] = null;
    }
    size = 0;
  }

  /**
   * Returns the Assignment at the root of this AssignmentQueue, i.e. the Assignment with the
   * earliest due date.
   * 
   * @return the Assignment in this AssignmentQueue with the earliest due date
   * @throws NoSuchElementException if this AssignmentQueue is empty
   */
  @Override
  public Assignment peek() {
    // TODO complete this implementation
    if (size == 0) {
      throw new NoSuchElementException("This queue is empty");
    }
    // Returns highest priority element, which is the first
    return queue[0];
  }


  /**
   * Adds the given Assignment to this AssignmentQueue at the correct position based on the min-heap
   * ordering. This queue should maintain the min-heap invariant, so that the Assignment at each
   * index has an earlier or equivalent due-date than the Assignments in its child nodes.
   * Assignments should be compared using the Assignment.compareTo() method.
   * 
   * @param e Assignment to add to this AssignmentQueue
   * @throws NullPointerException  if the given Assignment is null
   * @throws IllegalStateException with a descriptive error message if this AssignmentQueue is full
   */
  @Override
  public void enqueue(Assignment e) {

    // TODO complete this implementation
    if (e == null) {
      throw new NullPointerException("Assignment is null");
    }
    if (size == queue.length) {
      throw new IllegalStateException("The Assignment Queue is full");
    }
    // Sets e to the highest element in the queue, then keeps swapping using percolateUp
    queue[size] = e;
    size++;
    percolateUp(size - 1);
  }

  /**
   * Removes and returns the Assignment at the root of this AssignmentQueue, i.e. the Assignment
   * with the earliest due date.
   * 
   * @return the Assignment in this AssignmentQueue with the earliest due date
   * @throws NoSuchElementException with a descriptive error message if this AssignmentQueue is
   *                                empty
   */
  @Override
  public Assignment dequeue() {
    // TODO complete this implementation
    if (isEmpty()) {
      throw new NoSuchElementException("This queue is empty");
    }
    // Stores original first element
    Assignment ret = peek();
    // Sets lowest priority element to highest
    queue[0] = queue[size - 1];
    // Sets lowest to null
    queue[size - 1] = null;
    size--;
    // Percolates down to reorder the queue
    percolateDown(0);
    return ret;
  }

  /**
   * Recursive implementation of percolateDown() method. Restores the min-heap invariant of a given
   * subtree by percolating its root down the tree. If the element at the given index does not
   * violate the min-heap invariant (it is due before its children), then this method does not
   * modify the heap. Otherwise, if there is a heap violation, then swap the element with the
   * correct child and continue percolating the element down the heap.
   * 
   * @param i index of the element in the heap to percolate downwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  protected void percolateDown(int i) {
    // TODO provide the worst-case runtime complexity of this method assuming that the problem size
    // N is the size of this queue
    // Time complexity: O(log(n))
    // TODO complete this implementation. This method MUST be implemented recursively
    // Gets the left and right child index
    int minindex = i;
    int leftchild = i * 2 + 1;
    int rightchild = i * 2 + 2;
    // Trys to find index with lowest priority
    if (leftchild < size && queue[leftchild].compareTo(queue[minindex]) < 0) {
      minindex = leftchild;
    }
    if (rightchild < size && queue[rightchild].compareTo(queue[minindex]) < 0) {
      minindex = rightchild;
    }
    // Swaps child and parent, and recurses again
    if (minindex != i) {
      Assignment temp = queue[i];
      queue[i] = queue[minindex];
      queue[minindex] = temp;
      percolateDown(minindex);
    }
  }

  /**
   * Recursive implementation of percolateUp() method. Restores the min-heap invariant of the tree
   * by percolating a leaf up the tree. If the element at the given index does not violate the
   * min-heap invariant (it occurs after its parent), then this method does not modify the heap.
   * Otherwise, if there is a heap violation, swap the element with its parent and continue
   * percolating the element up the heap.
   * 
   * @param i index of the element in the heap to percolate upwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  protected void percolateUp(int i) {
    // TODO provide the worst-case runtime complexity of this method assuming that the problem size
    // N is the size of this queue
    // Time complexity: O(log(n))

    // TODO complete this implementation. This method MUST be implemented recursively
    // If the child node is less than the parent node, swap the nodes and call the method on the
    // newly swapped parent node index
    int parentindex = (i - 1) / 2;
    if (queue[i].compareTo(queue[parentindex]) < 0) {
      Assignment temp = queue[i];
      queue[i] = queue[parentindex];
      queue[parentindex] = temp;
      percolateUp(parentindex);
    }


  }

  /**
   * Returns a deep copy of this AssignmentQueue containing all of its elements in the same order.
   * This method does not return the deepest copy, meaning that you do not need to duplicate
   * assignments. Only the instance of the heap (including the array and its size) will be
   * duplicated.
   * 
   * @return a deep copy of this AssignmentQueue. The returned new assignment queue has the same
   *         length and size as this queue.
   */
  public AssignmentQueue deepCopy() {
    // TODO complete this implementation
    // Makes a shallow copy of the AssignmentQueue, and deep copies the array and size
    AssignmentQueue deepcopy = this;
    return deepcopy;
  }

  /**
   * Returns a String representing this AssignmentQueue, where each element (assignment) of the
   * queue is listed on a separate line, in order from earliest to latest.
   * 
   * @see Assignment#toString()
   * @see AssignmentIterator
   * @return a String representing this AssignmentQueue
   */
  public String toString() {
    StringBuilder val = new StringBuilder();

    for (Assignment a : this) {
      val.append(a).append("\n");
    }

    return val.toString();
  }

  /**
   * Returns an Iterator for this AssignmentQueue which proceeds from the earliest to the latest
   * Assignment in the queue.
   * 
   * @see AssignmentIterator
   * @return an Iterator for this AssignmentQueue
   */
  @Override
  public Iterator<Assignment> iterator() {
    return new AssignmentIterator(this);
  }
}
