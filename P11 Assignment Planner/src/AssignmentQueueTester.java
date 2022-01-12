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
 * Tester class for the AssignmentQueue implementation of PriorityQueueADT
 */
public class AssignmentQueueTester {
  /**
   * Tests the functionality of the constructor for AssignmentQueue Should implement at least the
   * following tests:
   *
   * - Calling the AssignmentQueue with an invalid capacity should throw an IllegalArgumentException
   * - Calling the AssignmentQueue with a valid capacity should not throw any errors, and should
   * result in a new AssignmentQueue which is empty, and has size 0
   *
   * @return true if the constructor of AssignmentQueue functions properly
   * @see AssignmentQueue#AssignmentQueue(int)
   */
  public static boolean testConstructor() {
    // TODO complete the implementation of this tester method
    // Checks if correct exceptions are thrown
    try {
      try {
        AssignmentQueue invalidqueue = new AssignmentQueue(-1);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
        System.out.println("This is expected");
      }
      AssignmentQueue validqueue = new AssignmentQueue(10);
      if (!validqueue.isEmpty()) {
        return false;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Tests the functionality of the enqueue() and peek() methods Should implement at least the
   * following tests:
   *
   * - Calling peek on an empty queue should throw a NoSuchElementException - Calling enqueue on a
   * queue which is empty should add the Assignment, and increment the size of the queue - Calling
   * enqueue on a non-empty queue should add the Assignment at the proper position, and increment
   * the size of the queue. Try add at least 5 assignments - Calling peek on a non-empty queue
   * should always return the Assignment with the earliest due date - Calling enqueue on a full
   * queue should throw an IllegalStateException - Calling enqueue with a null Assignment should
   * throw a NullPointerException
   *
   * @return true if AssignmentQueue.enqueue() and AssignmentQueue.peek() function properly
   */
  public static boolean testEnqueue() {
    // TODO complete the implementation of this tester method
    try {

      AssignmentQueue queue = new AssignmentQueue(5);
      try {
        queue.peek();
      } catch (NoSuchElementException e) {
        e.printStackTrace();
        System.out.println("This is expected");
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
      // Checks if the first element is correct and the size is correct after enqueueing
      Assignment p11 = new Assignment("P11", 12, 13, 12);
      queue.enqueue(p11);
      if (queue.peek() != p11 && queue.size() != 1) {
        return false;
      }
      Assignment p09 = new Assignment("P09", 11, 26, 12);
      queue.enqueue(p09);
      if (queue.peek() != p09 && queue.size() != 2) {
        return false;
      }
      Assignment p10 = new Assignment("P10", 12, 6, 12);
      queue.enqueue(p10);
      if (queue.peek() != p09 && queue.size() != 3) {
        return false;
      }
      Assignment p08 = new Assignment("P08", 11, 19, 12);
      queue.enqueue(p08);
      if (queue.peek() != p08 && queue.size() != 4) {
        return false;
      }
      Assignment finexam = new Assignment("finexam", 12, 20, 12);
      queue.enqueue(finexam);
      if (queue.peek() != p08 && queue.size() != 5) {
        return false;
      }
      // Iterates through queue and checks if the elements are in the correct order
      Iterator<Assignment> iterator = queue.iterator();
      if (iterator.next() != p08 || iterator.next() != p09 || iterator.next() != p10
          || iterator.next() != p11 || iterator.next() != finexam) {
        return false;
      }
      // Checks if the correct exceptions are thrown
      try {
        queue.enqueue(p10);
      } catch (IllegalStateException e) {
        e.printStackTrace();
        System.out.println("This is expected");
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
      try {
        queue.enqueue(null);
      } catch (NullPointerException e) {
        e.printStackTrace();
        System.out.println("This is expected");
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Tests the functionality of dequeue() and peek() methods. The peek() method must return without
   * removing the assignment with the highest priority in the queue. The dequeue() method must
   * remove, and return the assignment with the highest priority in the queue. The size must be
   * decremented by one, each time the dequeue() method is successfully called. Try to check the
   * edge cases (calling peek and dequeue on an empty queue, and calling dequeue on a queue of size
   * one). For normal cases, try to consider dequeuing from a queue whose size is at least 6. Try to
   * consider cases where percolate-down recurses on left and right.
   * 
   * @return true if AssignmentQueue.dequeue() and AssignmentQueue.peek() function properly
   */
  public static boolean testDequeuePeek() {
    // TODO complete the implementation of this tester method
    try {
      AssignmentQueue queue = new AssignmentQueue(6);
      Assignment p09 = new Assignment("P09", 11, 26, 12);
      // Checks if peek works, and checks if P09 is removed correctly and that the size is 0
      queue.enqueue(p09);
      if (queue.peek() != p09 && queue.size() != 1) {
        return false;
      }
      if (queue.dequeue() != p09) {
        return false;
      }
      if (queue.size() != 0) {
        return false;
      }
      // Adds six new elements
      Assignment finexam = new Assignment("Final Exam", 12, 20, 12);
      queue.enqueue(finexam);
      Assignment p11 = new Assignment("P11", 12, 13, 12);
      queue.enqueue(p11);
      queue.enqueue(p09);
      Assignment p10 = new Assignment("P10", 12, 6, 12);
      queue.enqueue(p10);
      Assignment p08 = new Assignment("P08", 11, 19, 12);
      queue.enqueue(p08);
      Assignment p07 = new Assignment("P07", 11, 19, 9);
      queue.enqueue(p07);
      // Checks if dequeue and peek are correct, and that the size is decrementing correctly
      if (queue.dequeue() != p07 || queue.size() != 5 || queue.peek() != p08) {
        return false;
      }
      if (queue.dequeue() != p08 || queue.size() != 4 || queue.peek() != p09) {
        return false;
      }
      if (queue.dequeue() != p09 || queue.size() != 3 || queue.peek() != p10) {
        return false;
      }
      if (queue.dequeue() != p10 || queue.size() != 2 || queue.peek() != p11) {
        return false;
      }
      if (queue.dequeue() != p11 || queue.size() != 1 || queue.peek() != finexam) {
        return false;
      }
      if (queue.dequeue() != finexam || queue.size() != 0) {
        return false;
      }
      // Sees if the correct exceptions are thrown
      try {
        queue.peek();
      } catch (NoSuchElementException e) {
        e.printStackTrace();
        System.out.println("This is expected");
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
      try {
        queue.dequeue();
      } catch (NoSuchElementException e) {
        e.printStackTrace();
        System.out.println("This is expected");
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Tests the functionality of the clear() method Should implement at least the following
   * scenarios: - clear can be called on an empty queue with no errors - clear can be called on a
   * non-empty queue with no errors - After calling clear, the queue should contain no Assignments
   *
   * @return true if AssignmentQueue.clear() functions properly
   */
  public static boolean testClear() {
    // TODO complete the implementation of this tester method
    try {
      AssignmentQueue empty = new AssignmentQueue(10);
      empty.clear();
      if (!empty.isEmpty()) {
        return false;
      }
      // Checks if queue is empty after clearing
      AssignmentQueue nonempty = new AssignmentQueue(10);
      nonempty.enqueue(new Assignment("P11", 12, 13, 12));
      nonempty.enqueue(new Assignment("P10", 12, 6, 12));
      nonempty.enqueue(new Assignment("P09", 11, 26, 12));
      nonempty.clear();
      if (!nonempty.isEmpty()) {
        return false;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Tests all the methods of the AssignmentQueue class
   * 
   */
  public static boolean runAllTests() {
    // TODO complete the implementation of this tester method
    if (testConstructor() == true && testEnqueue() == true && testDequeuePeek() == true
        && testClear() == true) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Main method
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println(runAllTests());
  }
}
