package com.una.ejemplos.ejemplo16.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.una.ejemplos.ejemplo16.cr.ac.una.collections.BSTree;

/**
 *
 * (c) 2021
 *
 * @author Georges Alfaro S.
 * @version 1.0.0 2021-09-23
 */
public class VentanaAplicacion extends JFrame implements PropertyChangeListener {

    public VentanaAplicacion(BSTree<Integer> a, String title) throws HeadlessException {
        super(title);
        this.arbol = a;

        configurar();
    }

    private void configurar() {
        ajustarComponentes(getContentPane());
        setResizable(true);
        setSize(600, 600);
        setMinimumSize(new Dimension(480, 480));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void ajustarComponentes(Container c) {
        lblValor = new JLabel("Valor :");
        txtValor = new JTextField();
        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar");
        panelDatos = new JPanel();

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBtnAgregarClick();
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBtnEliminarClick();
            }
        });

        panelDatos.setLayout(new java.awt.GridLayout(1, 0));
        panelDatos.add(Box.createHorizontalStrut(1));
        panelDatos.add(lblValor);
        panelDatos.add(txtValor);
        panelDatos.add(btnAgregar);
        panelDatos.add(btnEliminar);
        panelDatos.add(Box.createHorizontalStrut(1));

        c.setLayout(new BorderLayout());
        c.add(BorderLayout.CENTER, panelPrincipal = new JPanel() {
            @Override
            public void paintComponent(Graphics bg) {
                super.paintComponent(bg);

                Graphics2D g = (Graphics2D) bg;
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                arbol.paint(g, getBounds());
            }
        });

        c.add(BorderLayout.SOUTH, panelDatos);
        panelPrincipal.setBackground(Color.LIGHT_GRAY);
    }

    public void init() {
        setVisible(true);
        arbol.addPropertyChangeListener(this);

        runner = new Thread(new Runnable() {
            @Override
            public void run() {
                while (runner == Thread.currentThread()) {
                    arbol.move();
                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException ignore) {
                    }
                }
            }
        });
        runner.start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        panelPrincipal.repaint();
    }

    private void onBtnAgregarClick() {
        try {
            Integer valor = Integer.parseInt(txtValor.getText());
            if(arbol.find(valor) == null){
                arbol.add(valor);
            }
            else{
                throw new RuntimeException("elemento duplicado");
            }
            propertyChange(null);
        } catch (NumberFormatException ex) {
            System.err.println("no se ingreso un numero valido");
        } catch (Exception e) {
            System.err.println("no se pudo agregar el elemento: " + e.getLocalizedMessage());
        }
        txtValor.setText("");
    }

    private void onBtnEliminarClick() {
        try {
            Integer valor = Integer.parseInt(txtValor.getText());
            arbol.delete(valor);
            propertyChange(null);
        } catch (NumberFormatException ex) {
            System.err.println("no se ingreso un numero valido");
        } catch (Exception e) {
            System.err.println("no se pudo elimnar el elemento: " + e.getLocalizedMessage());
        }
        txtValor.setText("");
    }

    private JPanel panelPrincipal;
    private Thread runner;
    private final BSTree<Integer> arbol;
    private JPanel panelDatos;
    private JLabel lblValor;
    private JTextField txtValor;
    private JButton btnAgregar;
    private JButton btnEliminar;
}
