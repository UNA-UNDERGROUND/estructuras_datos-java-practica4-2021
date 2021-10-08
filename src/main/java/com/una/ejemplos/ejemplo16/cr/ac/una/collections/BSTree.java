package com.una.ejemplos.ejemplo16.cr.ac.una.collections;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.una.ejemplos.ejemplo16.cr.ac.una.view.drawing.Paintable;

/**
 *
 * (c) 2021
 *
 * @author Georges Alfaro S.
 * @version 1.0.0 2021-09-23
 */
public class BSTree<T extends Comparable<T>> implements Paintable {

    public BSTree() {
        this.notifier = new PropertyChangeSupport(this);
        this.root = null;
    }

    public int size() {
        return (root == null) ? 0 : root.size();
    }

    public int height() {
        return (root == null) ? 0 : root.height();
    }

    public BTNode<T> find(T obj) {
        return (root == null) ? null : root.find(obj);
    }

    public void add(T obj) {
        if (obj != null) {
            if (root == null) {
                root = new BTNode<T>(obj);
            } else {
                root.add(obj);
            }
            notifyListeners(obj.toString(), obj);
        } else {
            throw new NullPointerException();
        }
    }

    public void delete(T obj) {
        if (root != null) {
            boolean isRoot = root.getInfo().equals(obj);
            if (isRoot) {
                if (root.getLeft() == null) {
                    root = root.getRight();
                }
                if (root.getRight() == null) {
                    root = root.getLeft();
                } else {
                    BTNode<T> node = root.deleteRec(root, obj);
                    if (node == null && !isRoot) {
                        throw new RuntimeException("no existe el elemento {" + obj + "}");
                    }
                }
            }
        }
        throw new RuntimeException("no hay nodos");
    }

    @Override
    public String toString() {
        return (root == null) ? "{}" : root.toString();
    }

    public String toString(int n) {
        return (root == null) ? "(null)" : root.toString(n);
    }

    public void move() {
        if (root != null) {
            root.move();
        }
        notifyListeners(null, null);
    }

    @Override
    public void paint(Graphics g) {
        throw new IllegalArgumentException();
    }

    @Override
    public void paint(Graphics g, Rectangle bounds) {
        g.setColor(Color.BLACK);
        g.setFont(BASE_FONT);
        g.drawString(String.format("%8s %2d", "size:", size()), 16, 24);
        g.drawString(String.format("%8s %2d", "height:", height()), 16, 40);
        g.setColor(Color.GRAY);

        if (root != null) {
            bounds.grow(-BTNode.NR, 0);
            root.updateLocation(bounds, bounds.height / root.height());
            root.paintEdges(g);
            root.paint(g, bounds);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener obs) {
        notifier.addPropertyChangeListener(obs);
    }

    public void removePropertyChangeListener(PropertyChangeListener obs) {
        notifier.removePropertyChangeListener(obs);
    }

    public void notifyListeners(String property, Object evt) {
        notifier.firePropertyChange(property, null, evt);
    }

    private static final Font BASE_FONT = new Font("Inconsolata", Font.PLAIN, 16);
    private final PropertyChangeSupport notifier;
    private BTNode<T> root;
}
