import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) return false;
        Node<T> node = find((T) o);
        Node<T> parentNode = findParentNode(node);
        if (node.right == null && node.left == null) {
            if (parentNode == null) root = null;
            else {
                if (node == parentNode.left) parentNode.left = null;
                else parentNode.right = null;
            }
        } else if (node.left == null) {
            if (parentNode == null) root = node.right;
            else {
                if (node == parentNode.left) parentNode.left = node.right;
                else parentNode.right = node.right;
            }
        } else if (node.right == null) {
            if (parentNode == null) root = node.left;
            else {
                if (node == parentNode.left) parentNode.left = node.left;
                else parentNode.right = node.left;
            }
        } else {
            Node<T> minNode = node.right;
            Node<T> parentOfMinNode = node;
            while (minNode.left != null) {
                parentOfMinNode = minNode;
                minNode = minNode.left;
            }
            if (minNode != node.right) minNode.right = node.right;
            minNode.left = node.left;
            parentOfMinNode.left = null;
            if (parentNode == null) root = minNode;
            else {
                if (node == parentNode.left) parentNode.left = minNode;
                else parentNode.right = minNode;
            }
        }
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    private Node<T> findParentNode(Node<T> node) {
        if (node == null || node == root) return null;
        Node<T> parentNode = root;
        while (node != parentNode.right && node != parentNode.left) {
            int comparison = node.value.compareTo(parentNode.value);
            if (comparison == 0) return parentNode;
            else if (comparison < 0) parentNode = parentNode.left;
            else parentNode = parentNode.right;
        }
        return parentNode;
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        private Stack<Node<T>> stack = new Stack<>();

        private BinaryTreeIterator() {
            Node<T> node = root;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        private Node<T> findNext() {
            if (stack.empty()) return null;
            Node<T> node = stack.pop();
            if (node.right != null) {
                Node<T> node1 = node.right;
                while (node1 != null) {
                    stack.push(node1);
                    node1 = node1.left;
                }
            }
            return node;
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}