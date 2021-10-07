package com.una.ejemplos;

import com.una.estructuras.ListaEmpleados;
import com.una.util.XMLParser;

public class Ejemplo14b {

    public static void main(String[] args) {
        final String rutaArchivo = "./empleados.xml";

        ListaEmpleados empleados = XMLParser.cargarLista(rutaArchivo);

        System.out.println(empleados.toStringXML());
    }

}
