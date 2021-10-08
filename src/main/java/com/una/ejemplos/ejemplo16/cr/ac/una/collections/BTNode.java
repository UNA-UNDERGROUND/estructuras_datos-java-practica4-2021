package com.una.ejemplos.ejemplo16.cr.ac.una.collections;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import com.una.ejemplos.ejemplo16.cr.ac.una.view.drawing.Paintable;

/**
 *
 * (c) 2021
 *
 * @author Georges Alfaro S.
 * @version 1.0.0 2021-09-23
 */
public class BTNode<T extends Comparable<T>> implements Paintable {

    public BTNode(T info, BTNode<T> left, BTNode<T> right) {
        this(null, info, left, right);
    }

    public BTNode(BTNode<T> father, T info, BTNode<T> left, BTNode<T> right) {
        this.info = info;
        this.left = left;
        this.right = right;
        this.father = father;

        this.location = null;
        this.finalLocation = null;
    }

    public BTNode(BTNode<T> father, T info) {
        this(null, info, null, null);
    }

    public BTNode(T info) {
        this(null, info);
    }

    public int size() {
        return 1 + ((left != null) ? left.size() : 0) + ((right != null) ? right.size() : 0);
    }

    public int height() {
        return 1 + Math.max(((left != null) ? left.height() : 0), ((right != null) ? right.height() : 0));
    }

    public int width() {
        int w = 3;
        if ((left == null) && (right == null)) {
            w = 1;
        } else if ((left == null) || (right == null)) {
            w = 2;
        }
        return w;
    }

    public BTNode<T> find(T obj) {
        if (obj != null) {
            int r = obj.compareTo(getInfo());
            if (r == 0) {
                return this;
            } else if ((r < 0) && (left != null)) {
                return left.find(obj);
            } else if ((r > 0) && (right != null)) {
                return right.find(obj);
            } else {
                return null;
            }
        } else {
            throw new NullPointerException();
        }
    }

    public void add(T obj) {
        if (obj.compareTo(getInfo()) <= 0) {
            if (left == null) {
                left = new BTNode<T>(this, obj);
            } else {
                left.add(obj);
            }
        } else {
            if (right == null) {
                right = new BTNode<T>(this, obj);
            } else {
                right.add(obj);
            }
        }
    }

    public BTNode<T> deleteRec(BTNode<T> root, T value) {
        if (root == null) {
            return root;
        }
        if (value.compareTo(root.info) < 0) {
            root.left = deleteRec(root.left, value);
        } else if (value.compareTo(root.info) > 0) {
            root.right = deleteRec(root.right, value);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            root.info = minValue(root.right);
            root.right = deleteRec(root.right, root.info);
        }

        return root;
    }

    T minValue(BTNode<T> root) {
        T minv = root.info;
        while (root.left != null) {
            minv = root.left.info;
            root = root.left;
        }
        return minv;
    }

    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    public T getInfo() {
        return info;
    }

    public void setFather(BTNode<T> node) {
        father = node;
    }

    public void setLeft(BTNode<T> node) {
        left = node;
    }

    public void setRight(BTNode<T> node) {
        right = node;
    }

    public BTNode<T> getFather() {
        return father;
    }

    public BTNode<T> getLeft() {
        return left;
    }

    public BTNode<T> getRight() {
        return right;
    }

    public Point2D.Float getLocation() {
        return location;
    }

    public void setLocation(Point2D.Float location) {
        this.location = location;
    }

    public void setLocation(float x, float y) {
        if (location != null) {
            location.setLocation(x, y);
        } else {
            location = new Point2D.Float(x, y);
        }
    }

    public Point2D.Float getFinalLocation() {
        return finalLocation;
    }

    public void setFinalLocation(Point2D.Float finalLocation) {
        this.finalLocation = finalLocation;
    }

    @Override
    public String toString() {
        boolean hasChildren = (getLeft() != null) || (getRight() != null);
        StringBuilder r = new StringBuilder(hasChildren ? "{" : "");
        r.append(getInfo());
        if (hasChildren) {
            r.append("; [");
            r.append(String.format("%s, ", (left != null) ? left : "_"));
            r.append(String.format("%s", (right != null) ? right : "_"));
            r.append("]");
        }
        r.append(hasChildren ? "}" : "");
        return r.toString();
    }

    public String toString(int indent) {
        return toString(indent, 0);
    }

    private String toString(int indent, int level) {
        StringBuilder r = new StringBuilder();
        if (left != null) {
            r.append(left.toString(indent, level + 1));
        }
        r.append(String.format("%s%s%n", " ".repeat(indent).repeat(level), getInfo()));
        if (right != null) {
            r.append(right.toString(indent, level + 1));
        }
        return r.toString();
    }

    void move() {
        if (location != null) {
            setLocation(location.x + dT * (finalLocation.x - location.x),
                    location.y + dT * (finalLocation.y - location.y));

            if (left != null) {
                left.move();
            }
            if (right != null) {
                right.move();
            }
        } else {
            System.err.printf("(no current location: %s)%n", getInfo());
        }
    }

    void updateLocation(Rectangle r, int rh) {

        // Calcula el ancho proporcional de cada subárbol.
        //
        float wl = (left != null) ? left.width() : 0f;
        float wr = (right != null) ? right.width() : 0f;

        // Si no existe un subárbol, se asume un espacio relativo
        // para dejar libre en el lado que corresponda.
        //
        final float M = 0.15f;

        // Inicialmente, se coloca el nodo centrado en el
        // espacio del rectángulo circunscrito.
        //
        float x = r.x + r.width * 0.5f;

        if ((wl != 0f) && (wr != 0f)) {
            x = r.x + r.width * wl / (wl + wr);
        } else if ((wl == 0f) && (wr != 0f)) {
            x = r.x + r.width * M;
        } else if ((wl != 0f) && (wr == 0f)) {
            x = r.x + r.width * (1f - M);
        }

        float y = r.y + rh / 2f;

        finalLocation = new Point2D.Float(x, y);

        if (location == null) {
            location = new Point2D.Float(x, y);
        }
        // System.out.printf("%2s @ (%3.1f, %3.1f) -> (%3.1f, %3.1f)%n",
        // getInfo(),
        // location.x, location.y,
        // finalLocation.x, finalLocation.y);

        // Calcula el rectángulo contenedor de cada subárbol.
        // El nodo actual se encuentra sobre la división vertical de
        // ambas partes.
        //
        if (left != null) {
            left.updateLocation(new Rectangle(r.x, r.y + rh, (int) (x - r.x), r.height - rh), rh);
        }
        if (right != null) {
            right.updateLocation(new Rectangle((int) (x), r.y + rh, (int) (r.width - (x - r.x)), r.height - rh), rh);
        }
    }

    void paintEdges(Graphics bg) {
        Graphics2D g = (Graphics2D) bg;

        if (location != null) {
            g.setColor(Color.BLACK);
            g.setStroke(SIMPLE_STROKE);
            if ((left != null) && (left.location != null)) {
                g.drawLine((int) location.x, (int) location.y, (int) left.location.x, (int) left.location.y);
                left.paintEdges(g);
            }
            if ((right != null) && (right.location != null)) {
                g.drawLine((int) location.x, (int) location.y, (int) right.location.x, (int) right.location.y);
                right.paintEdges(g);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        throw new IllegalArgumentException();
    }

    @Override
    public void paint(Graphics bg, Rectangle bounds) {
        Graphics2D g = (Graphics2D) bg;

        if (location != null) {
            int x = (int) location.x;
            int y = (int) location.y;

            g.setColor(SHADOW_COLOR);
            g.fillOval(x - NR + SHD_OFF, y - NR + SHD_OFF, 2 * NR, 2 * NR);
            g.setColor((isLeaf()) ? BKGND_COLOR_B : BKGND_COLOR);
            g.fillOval(x - NR, y - NR, 2 * NR, 2 * NR);
            g.setColor(Color.BLACK);
            g.setStroke(SIMPLE_STROKE);
            g.drawOval(x - NR, y - NR, 2 * NR, 2 * NR);

            g.setColor(Color.BLACK);
            g.setFont(BASE_FONT);
            FontMetrics fm = g.getFontMetrics();
            String txt = getInfo().toString();
            g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getAscent() / 2);

            if (left != null) {
                left.paint(bg, bounds);
            }
            if (right != null) {
                right.paint(bg, bounds);
            }
        }
    }

    private static final Color BKGND_COLOR = Color.WHITE;
    private static final Color BKGND_COLOR_B = new Color(0.9f, 0.8f, 0.6f);
    private static final Color SHADOW_COLOR = new Color(0.2f, 0.2f, 0.2f, 0.5f);
    private static final Stroke SIMPLE_STROKE = new BasicStroke(1.0f);
    private static final float[] DASHES = { 4f, 4f };
    private static final Stroke GUIDE_STROKE_A = new BasicStroke(1f);
    private static final Stroke GUIDE_STROKE_B = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f,
            DASHES, 0f);
    private static final Font BASE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

    public static final int SHD_OFF = 3; // shadow offset
    public static final int NR = 14; // node radius

    private T info;
    private BTNode<T> left;
    private BTNode<T> right;
    private BTNode<T> father;

    Point2D.Float location;
    private Point2D.Float finalLocation;
    private final float dT = 0.035f;
}
