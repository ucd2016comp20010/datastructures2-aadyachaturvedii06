package project20280.tree;

import project20280.interfaces.Position;
import project20280.interfaces.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;


/**
 * An abstract base class providing some functionality of the Tree interface.
 * <p>
 * The following three methods remain abstract, and must be
 * implemented by a concrete subclass: root, parent, children. Other
 * methods implemented in this class may be overridden to provide a
 * more direct and efficient implementation.
 */
public abstract class AbstractTree<E> implements Tree<E> {

    @Override
    public boolean isInternal(Position<E> p) {
        // TODO
        return numChildren(p) > 0;
    }

    @Override
    public boolean isExternal(Position<E> p) {
        // TODO
        return numChildren(p) == 0;
    }

    @Override
    public boolean isRoot(Position<E> p) {
        // TODO
        return p == root();
    }

    @Override
    public int numChildren(Position<E> p) {
        // TODO
        int count = 0;
        for (Position<E> c : children(p))
            count++;
        return count;
    }

    @Override
    public int size() {
        int count = 0;
        for (Position p : positions()) count++;
        return count;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    //---------- support for computing depth of nodes and height of (sub)trees ----------

    public int depth(Position<E> p) throws IllegalArgumentException {
        // TODO
        if (isRoot(p))
            return 0;
        else
            return 1 + depth(parent(p));
    }

    private int heightBad() {
        int h = 0;
        for (Position<E> p : positions())
            if (isExternal(p))
                h = Math.max(h, depth(p));
        return h;
    }

    public int height_recursive(Position<E> p) {
        // TODO
        int h = 0;
        for (Position<E> c : children(p))
            h = Math.max(h, height_recursive(c));
        return h + (isExternal(p) ? 0 : 1);
    }

    public int height() throws IllegalArgumentException {
        return height_recursive(root());
    }

    //---------- support for various iterations of a tree ----------

    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = positions().iterator();

        public boolean hasNext() {
            return posIterator.hasNext();
        }

        public E next() {
            return posIterator.next().getElement();
        }

        public void remove() {
            posIterator.remove();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        return preorder();
    }

    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        // TODO
        snapshot.add(p);
        for (Position<E> c : children(p))
            preorderSubtree(c, snapshot);
    }

    public Iterable<Position<E>> preorder() {
        // TODO
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            preorderSubtree(root(), snapshot);
        return snapshot;
    }

    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        // TODO
        for (Position<E> c : children(p))
            postorderSubtree(c, snapshot);
        snapshot.add(p);
    }

    public Iterable<Position<E>> postorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty())
            postorderSubtree(root(), snapshot);
        return snapshot;
    }

    public Iterable<Position<E>> breadthfirst() {
        // TODO
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            Queue<Position<E>> fringe = new LinkedList<>();
            fringe.add(root());
            while (!fringe.isEmpty()) {
                Position<E> p = fringe.remove();
                snapshot.add(p);
                for (Position<E> c : children(p))
                    fringe.add(c);
            }
        }
        return snapshot;
    }
}