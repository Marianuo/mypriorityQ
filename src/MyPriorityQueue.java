/**
 * @author  Names :
 * Marian Agamy
/

/**
 * MyPriorityQueue is a custom implementation of a priority queue that uses a binary heap to manage elements.
 * It supports standard queue operations such as adding, offering, polling, peeking, and removing elements,
 * The queue dynamically resizes its underlying array as needed and handles edge cases such as null inputs and empty states.
 * Additional methods include heapify for maintaining the heap structure and a custom sort method that sorts
 * an array using the priority queue.
 */
import java.util.*;

public class MyPriorityQueue<T extends Comparable<T>> implements Queue<T> {
    private static final int DEFAULT_CAPACITY = 10;  // Default initial capacity of the priority queue
    private T[] array;  // Array to store elements in the priority queue
    private int size;  // Number of elements currently in the priority queue

    /**
     * Constructor with specified initial capacity.
     * @param size The initial capacity of the priority queue.
     */
    public MyPriorityQueue(int size) {
        this.array = (T[]) new Comparable[size];  // Initialize the array with the given size (ChatGPT helped with this line)
        this.size = 0;  // Initialize the size to 0, indicating an empty queue
    }

    /**
     * Constructor with default initial capacity.
     * The initial capacity of the priority queue.
     */
    public MyPriorityQueue() {
        this.array = (T[]) new Comparable[DEFAULT_CAPACITY];  // Initialize the array with default capacity (Chatgpt helped with this line)
        this.size = 0;  // Initialize the size to 0, indicating an empty queue
    }
    /**
     * Adds an element to the priority queue.
     *
     * @param item The element to be added to the priority queue.
     * @return true if the element was successfully added.
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public boolean add(T item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null element to MyPriorityQueue");  // Null elements are not allowed
        }
        if (size == array.length) {
            resize();  // Resize the array if it's full
        }
        array[size] = item;  // Add the new item to the end of the array
        size++;  // Increment the size
        int current = size - 1;  // Index of the newly added element
        // Reheapify the heap (upward)
        while (current > 0 && array[current].compareTo(array[(current - 1) / 2]) > 0) {
            swap(current, (current - 1) / 2);  // Swap with parent if necessary
            current = (current - 1) / 2;  // Move up to the parent's index
        }
        return true;  // Indicate that the item was successfully added
    }

    @Override
    public boolean remove(Object o) {
        return false;  // Placeholder method.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;  // Placeholder method.
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;  // Placeholder method.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;  // Placeholder method.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;  // Placeholder method,
    }
    /**
     *  Clears the priority queue.
     * Removes all elements from the priority queue.
     */
    @Override
    public void clear() {
        Arrays.fill(array, null);  // Clear all elements in the array
        size = 0;  // Reset size to 0, indicating an empty queue
    }
    /**
     * Offers an element to the priority queue.
     *
     * @param item The element to be offered to the priority queue.
     * @return true if the element was successfully added, false if the element is null.
     */

    @Override
    public boolean offer(T item) {
        if (item == null) {
            return false;  // Null elements are not allowed, return false
        }
        if (size == array.length) {
            resize();  // Resize the array if it's full
        }
        array[size] = item;  // Add the new item to the end of the array
        size++;  // update the size
        int current = size - 1;  // Index of the newly added element
        // Reheapify the heap (upward)
        while (current > 0 && array[current].compareTo(array[(current - 1) / 2]) > 0) {
            swap(current, (current - 1) / 2);  // Swap with parent if necessary
            current = (current - 1) / 2;  // Move up to the parent's index
        }
        return true;  // Indicate that the item was successfully added
    }
    /**
     * Removes and returns the top element from the priority queue.
     *
     * @return The top element from the priority queue.
     * @throws NoSuchElementException if the priority queue is empty.
     */
    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();  // Throw exception if queue is empty
        }
        T item = array[0];  // The top element to be removed
        array[0] = array[size - 1];  // Replace the top with the last element
        array[size - 1] = null;  // Remove the last element
        size--;  // update size
        heapify(0);  // Reheapify the heap (downward)
        return item;  // Return the removed element
    }
    /**
     * Retrieves and removes the top element from the priority queue, or returns null if the queue is empty.
     *
     * @return The top element from the priority queue, or null if the queue is empty.
     */

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;  // Return null if the queue is empty
        }
        T item = array[0];  // The top element to be removed
        array[0] = array[size - 1];  // Replace the top with the last element
        array[size - 1] = null;  // Remove the last element
        size--;  // update size
        heapify(0);  // Reheapify the heap (downward)
        return item;  // Return the removed element
    }
    /**
     * Retrieves, but does not remove, the top element of the priority queue.
     *
     * @return The top element of the priority queue.
     * @throws NoSuchElementException if the priority queue is empty.
     */
    @Override
    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException();  // Throw exception if queue is empty
        }
        return array[0];  // Return the top element without removing it
    }
    /**
     * Retrieves, but does not remove, the top element of the priority queue, or returns null if the queue is empty.
     *
     * @return The top element of the priority queue, or null if the queue is empty.
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;  // Return null if the queue is empty
        }
        return array[0];  // Return the top element without removing it
    }
    /**
     * Returns the number of elements in the priority queue.
     *
     * @return The number of elements in the priority queue.
     */

    @Override
    public int size() {
        return size;  // Return the current size of the queue
    }
    /**
     * Checks if the priority queue is empty.
     *
     * @return true if the priority queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;  // Return true if the queue is empty
    }

    /**
     * Checks if the priority queue contains the specified element.
     *
     * @param o The element whose presence in this queue is to be tested.
     * @return true if the priority queue contains the specified element.
     */
    @Override
    public boolean contains(Object o) {
        // Check if the queue contains the given element
        for (int i = 0; i < size; i++) {
            if (this.array[i] == o) {
                return true;  // Return true if found
            }
        }
        return false;  // Return false if not found
    }

    @Override
    public Iterator<T> iterator() {
        return null;  // Placeholder method, needs implementation
    }

    /**
     * Returns an array containing all the elements in the priority queue.
     *
     * @return An array containing all elements in the priority queue.
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);  // Return a copy of the array up to the current size
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;  // Placeholder method, needs implementation
    }

    /**
     * Swaps two elements in the priority queue.
     *
     * @param i The index of the first element to be swapped.
     * @param j The index of the second element to be swapped.
     */
    private void swap(int i, int j) {
        T temp = array[i];  // Temporarily store the value at index i
        array[i] = array[j];  // Move value from index j to index i
        array[j] = temp;  // Move the temporary value to index j
    }

    /**
     * Resizes the array when it is full.
     * Doubles the size of the internal array to accommodate more elements.
     */
    private void resize() {
        this.array = Arrays.copyOf(this.array, this.array.length * 2);  // Double the size of the array
    }

    /**
     * Reheapifies the heap (downward) from a specified index.
     * this exact method was taught in class by Dr.Boaz Miller.
     *
     * @param i The index from which to start reheapifying.
     */
    private void heapify(int i) {
        int maxIndex = i;  // Start with the current index
        int left = i * 2 + 1;  // Index of the left child
        int right = i * 2 + 2;  // Index of the right child

        // Compare left child with current max
        if (left < size && array[left].compareTo(array[maxIndex]) > 0) {
            maxIndex = left;  // Update max index if left child is greater
        }
        // Compare right child with current max
        if (right < size && array[right].compareTo(array[maxIndex]) > 0) {
            maxIndex = right;  // Update max index if right child is greater
        }

        // If the current index is not the largest, swap and heapify the affected subtree
        if (maxIndex != i) {
            swap(maxIndex, i);  // Swap with the largest child
            heapify(maxIndex);  // Recursively heapify the subtree
        }
    }

    /**
     * Sorts the given array using the priority queue.
     *
     * @param arr The array to be sorted.
     */
    public void sort(T[] arr) {
        MyPriorityQueue<T> pq = new MyPriorityQueue<>(arr.length);  // Create a new priority queue with the size of the array
        for (T item : arr) {
            pq.add(item);  // Add all elements to the priority queue
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = pq.remove();  // Remove elements from the queue to get them in sorted order
        }
    }
    /**
     * Returns a string representation of the priority queue.
     *
     * @return A string representation of the priority queue according to in which order they'd be removed.
     */
    @Override
    public String toString() {
        T[] reverse = (T[]) Arrays.copyOf(array, size);  // Create a copy of the array
        Arrays.sort(reverse);  // Sort the copied array
        Object[] sorted = new Object[reverse.length];  // Create an array to store the reversed elements
        for (int i = 0; i < reverse.length; i++) {
            sorted[sorted.length - i - 1] = reverse[i];  // Reverse the order of the sorted array
        }
        return Arrays.toString(sorted);  // Return the reversed array as a string
    }
}