import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {
    @Test
    void remove() {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        binaryTree.add(10);
        binaryTree.add(12);
        binaryTree.add(11);
        binaryTree.add(4);
        binaryTree.add(16);
        binaryTree.add(7);
        binaryTree.add(2);
        binaryTree.add(15);
        binaryTree.add(5);

        assertTrue(binaryTree.remove(16));
        assertFalse(binaryTree.contains(16));
        assertTrue(binaryTree.checkInvariant());

        assertTrue(binaryTree.remove(10));
        assertFalse(binaryTree.contains(10));
        assertTrue(binaryTree.checkInvariant());

        assertTrue(binaryTree.remove(4));
        assertFalse(binaryTree.contains(4));
        assertTrue(binaryTree.checkInvariant());

        assertTrue(binaryTree.remove(11));
        assertFalse(binaryTree.contains(11));
        assertTrue(binaryTree.checkInvariant());

        assertTrue(binaryTree.remove(15));
        assertFalse(binaryTree.contains(15));
        assertTrue(binaryTree.checkInvariant());

        assertTrue(binaryTree.remove(5));
        assertFalse(binaryTree.contains(5));
        assertTrue(binaryTree.checkInvariant());

        assertTrue(binaryTree.remove(2));
        assertFalse(binaryTree.contains(2));
        assertTrue(binaryTree.checkInvariant());

        assertTrue(binaryTree.remove(7));
        assertFalse(binaryTree.contains(7));
        assertTrue(binaryTree.checkInvariant());

        assertTrue(binaryTree.remove(12));
        assertFalse(binaryTree.contains(12));
        assertTrue(binaryTree.checkInvariant());
    }

    @Test
    void next() {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        binaryTree.add(10);
        binaryTree.add(12);
        binaryTree.add(11);
        binaryTree.add(4);
        binaryTree.add(16);
        binaryTree.add(7);
        binaryTree.add(2);
        binaryTree.add(15);
        binaryTree.add(5);

        Iterator<Integer> iterator = binaryTree.iterator();

        assertEquals(2, (int) iterator.next());
        assertEquals(4, (int) iterator.next());
        assertEquals(5, (int) iterator.next());
        assertEquals(7, (int) iterator.next());
        assertEquals(10, (int) iterator.next());
        assertEquals(11, (int) iterator.next());
        assertEquals(12, (int) iterator.next());
        assertEquals(15, (int) iterator.next());
        assertEquals(16, (int) iterator.next());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}