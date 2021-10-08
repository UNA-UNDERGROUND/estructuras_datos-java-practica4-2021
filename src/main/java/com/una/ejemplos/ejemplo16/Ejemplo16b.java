package com.una.ejemplos.ejemplo16;

import java.util.Arrays;
// import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.una.ejemplos.ejemplo16.cr.ac.una.collections.BSTree;
import com.una.ejemplos.ejemplo16.vista.VentanaAplicacion;

public class Ejemplo16b {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | UnsupportedLookAndFeelException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        new Ejemplo16b().init();
    }

    public void init() {
        mostrarDatos(a);

        Integer[] v = {
                // 16,
                // 24, 20, 18, 17, 19, 22, 21, 23, 28, 26, 25, 27, 30, 29, 31,
                // 8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15
            };
        List<Integer> d = Arrays.asList(v);

        // Collections.shuffle(d);
        // Collections.sort(d);
        // Collections.sort(d, Collections.reverseOrder());
        mostrarInterfaz();

        for (Integer e : d) {
            a.add(e);
            // mostrarDatos(a);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }
        }

        System.out.println(a.toString(4));
    }

    public void mostrarInterfaz() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaAplicacion(a, "Ejemplo 16b").init();
            }
        });
    }

    private static void mostrarDatos(BSTree<Integer> a) {
        System.out.printf("a: %s%na.k(): %d, a.h(): %d%n%n", a, a.size(), a.height());
    }

    private BSTree<Integer> a = new BSTree<>();
}
