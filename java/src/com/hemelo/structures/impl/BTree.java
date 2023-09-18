package com.hemelo.structures.impl;

import com.hemelo.structures.ITree;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 *
 */
public class BTree<T extends Comparable<T>>  implements ITree<T> {


    private Node<T> root;
    private int minKeys, minChildren, maxKeys, maxChildren, size;

    public BTree() {
       this(1);
    }

    public BTree(int order) {
        this.root = null;
        this.size = 0;
        this.minKeys = order;
        this.maxKeys = 2 * this.minKeys;
        this.minChildren = this.minKeys + 1;
        this.maxChildren = this.maxKeys + 1;
    }

    @Override
    public boolean add(T value) {
        if (this.root == null) {
            this.root = new Node<T>(null, this.maxKeys, this.maxChildren);
            this.size++;
            return true;
        }

        Node<T> node = root;

        while (node != null) {

            if (!node.hasChildren()) {
                node.addKey(value);

                if (node.getKeysSize() <= this.maxKeys) break;

                this.split(node);
                break;
            }

            T lesser = node.getKey(0);

            if (value.compareTo(lesser) <= 0) {
                node = node.getChild(0);
                continue;
            }

            T greater = node.getKey( node.getKeysSize() - 1);

            if (value.compareTo(greater) > 0) {
                node = node.getChild(node.getKeysSize());
                continue;
            }

            for (int i = 0; i < node.getKeysSize(); i++) {
                T previous = node.getKey(i);
                T next = node.getKey(i + 1);

                if (value.compareTo(previous) > 0 && value.compareTo(next) <= 0) {
                    node = node.getChild(i + 1);
                    break;
                }
            }
        }

        this.size++;
        return true;
    }

    @Override
    public boolean contains(T value) {
        return this.findNode(value) != null;
    }

    @Override
    public boolean validate() {
        if (this.root == null) return true;
        return validateNode(this.root);
    }

    @Override
    public T remove(T value) {
        return null;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    private Node<T> findNode(final T value) {
        Node<T> node = this.root;

        while (node != null) {

            T lesser = node.getKey(0);

            if (value.compareTo(lesser) < 0) {
                node = (node.getChildrenSize() > 0) ? node.getChild(0) : null;
                continue;
            }

            int last = node.getKeysSize() - 1;
            T greater = node.getKey(last);

            if (value.compareTo(greater) > 0) {
                node = (node.getChildrenSize() > node.getKeysSize()) ? node.getChild(node.getKeysSize()) : null;
                continue;
            }

            for (int i = 0; i < node.getKeysSize(); i++) {
                T currentValue = node.getKey(i);

                if (currentValue.compareTo(value) == 0) return node;

                int next = (i + 1);
                if (next > last) continue;

                T nextValue = node.getKey(i + 1);

                if ((currentValue.compareTo(value) < 0) && nextValue.compareTo(value) > 0) {

                    if (next < node.getChildrenSize()) {
                        node = node.getChild(next);
                        break;
                    }

                    return null;
                }
            }
        }

        return null;
    }

    private void split(Node<T> node) {
        int medianIdx = node.getKeysSize() / 2;
        T medianValue  = node.getKey(medianIdx);

        Node<T> leftSide = new Node<>(null, this.maxKeys, this.maxChildren);

        for (int i = 0; i < medianIdx; i++) {
            leftSide.addKey(node.getKey(i));
        }

        if (node.hasChildren()) {
            for (int i = 0; i <= medianIdx; i++) {
                Node<T> c = node.getChild(i);
                leftSide.addChild(c);
            }
        }

        Node<T> rightSide = new Node<>(null, this.maxKeys, this.maxChildren);

        for (int j = medianIdx + 1; j < node.getKeysSize(); j++) {
            rightSide.addKey(node.getKey(j));
        }

        if (node.hasChildren()) {
            for (int j = medianIdx + 1; j < node.getChildrenSize(); j++) {
                Node<T> c = node.getChild(j);
                rightSide.addChild(c);
            }
        }

        if (node.hasParent()) {
            Node<T> parent =  node.getParent();
            parent.addKey(medianValue);
            parent.removeChild(node);
            parent.addChild(leftSide);
            parent.addChild(rightSide);

            if (parent.getKeysSize() > this.maxKeys) split(parent);
            return;
        }

        Node<T> root = new Node<T>(null, this.maxKeys, this.maxChildren);
        root.addKey(medianValue);
        node.setParent(root);
        this.root = root;
        node = root;
        node.addChild(leftSide);
        node.addChild(rightSide);
    }

    private boolean validateNode(Node<T> node) {

        if (node.getKeysSize() > 1) {
            for (int i = 1; i < node.getKeysSize(); i++) {

            }
        }
    }

    private static class Node<T extends  Comparable<T>> {

        private T[] keys;
        private Node<T> parent;
        private Node<T>[] children;
        private int keysSize;
        private int childrenSize;

        private Comparator<Node<T>> comparator = new Comparator<Node<T>>() {
            @Override
            public int compare(Node<T> o1, Node<T> o2) {
                return o1.getKey(0).compareTo(o2.getKey(0));
            }
        };

        private Node(Node<T> parent, int maxKeySize, int maxChildrenSize) {
            this.keys = (T[]) new Comparable[maxKeySize + 1];
            this.children = new Node[maxChildrenSize + 1];
            this.parent = parent;
            this.keysSize = 0;
            this.childrenSize = 0;
        }

        private void addChild(Node<T> child) {
            child.setParent(this);
            this.children[this.childrenSize++] = child;
            Arrays.sort(this.children, 0, this.childrenSize, comparator);
        }

        private boolean removeChild(Node<T> child) {

            if (!this.hasChildren()) return false;
            boolean found = false;

            for (int i = 0; i < this.childrenSize; i++) {
                if (this.children[i].equals(child)) {
                    found = true;
                } else {
                    this.children[i - 1] = this.children[i];
                }
            }

            if (!found)  return false;

            this.children[--this.childrenSize] = null;
            return true;
        }

        private Node<T> removeChild(int index) {
            if (index >= this.childrenSize) return null;

            Node<T> value = this.children[index];
            this.children[index] = null;

            for (int i = index + 1; i < this.childrenSize; i++) {
                this.children[i - 1] = this.children[i];
            }

            this.children[--this.childrenSize] = null;
            return value;
        }

        private void addKey(T value) {
            this.keys[this.keysSize++] = value;
            Arrays.sort(this.keys, 0, this.keysSize);
        }

        private T getKey(int index) {
            return this.keys[index];
        }

        private int getKeysSize() {
            return this.keysSize;
        }

        private int getChildrenSize() {
            return this.childrenSize;
        }

        private boolean hasChildren() {
            return this.getChildrenSize() > 0;
        }

        private boolean hasParent() {
            return this.parent != null;
        }

        private Node<T> getParent() {
            return this.parent;
        }

        private void setParent(Node<T> node) {
            this.parent = node;
        }

        private Node<T> getChild(int index) {
            if (index >= this.childrenSize) return null;
            return this.children[index];
        }

        private int indexOf(T value) {

            for (int i = 0; i < this.keysSize; i++) {
                if (this.keys[i].equals(value))
                    return i;
            }

            return -1;
        }
    }

    @Override
    public Collection<T> toCollection() {
        return null;
    }
}
