package com.una.ejemplos.ejemplo16.cr.ac.una.view.drawing;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * (c) 2019-2021
 *
 * @author Georges Alfaro S.
 * @version 1.2.0 2021-09-27
 */
public interface Paintable {

    public void paint(Graphics g);

    public void paint(Graphics g, Rectangle bounds);

}
