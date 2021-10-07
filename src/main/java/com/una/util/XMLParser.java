package com.una.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.una.estructuras.Empleado;
import com.una.estructuras.ListaEmpleados;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
    private static ListaEmpleados cargarDatosXMl(Document doc) {
        ListaEmpleados lista = null;
        if (doc.getFirstChild().getNodeName() == "lista_empleados") {
            lista = new ListaEmpleados();
            NodeList listaNodos = doc.getFirstChild().getChildNodes();
            for (int i = 0; i < listaNodos.getLength(); i++) {
                Node actual = listaNodos.item(i);
                if (actual.getNodeName() == "empleado") {
                    Empleado empleado = cargarEmpleadoXML(actual);
                    if (empleado != null) {
                        lista.agregarEmpleado(empleado);
                    }
                }
            }

        }
        return lista;
    }

    private static Empleado cargarEmpleadoXML(Node nodoEmpleado) {
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            NodeList elementos = nodoEmpleado.getChildNodes();
            Integer id = null;
            String primerApellido = null;
            String segundoApellido = null;
            String nombre = null;
            LocalDate fecha = null;
            Float salarioBase = null;
            for (int i = 0; i < elementos.getLength(); i++) {
                Node actual = elementos.item(i);
                if (actual.getNodeType() == Node.ELEMENT_NODE) {
                    switch (actual.getNodeName()) {
                        case "id":
                            id = Integer.valueOf(actual.getTextContent());
                            break;
                        case "primer_apellido":
                            primerApellido = actual.getTextContent();
                            break;
                        case "segundo_apellido":
                            segundoApellido = actual.getTextContent();
                            break;
                        case "nombre":
                            nombre = actual.getTextContent();
                            break;
                        case "nacimiento":
                            fecha = LocalDate.parse(actual.getTextContent(), formato);
                            break;
                        case "salario_base":
                            salarioBase = Float.valueOf(actual.getTextContent());
                            break;
                        default:
                            break;
                    }
                }
            }
            if (id == null || primerApellido == null || segundoApellido == null || nombre == null || fecha == null
                    || salarioBase == null) {
                return null;
            }
            return new Empleado(id, primerApellido, segundoApellido, nombre, fecha, salarioBase);
        } catch (Exception e) {
            return null;
        }

    }

    private static Document cargarXML(String rutaArchivo) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(rutaArchivo));
            return doc;
        } catch (Exception ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
        return null;
    }

    public static ListaEmpleados cargarLista(String rutaArchivo) {
        return cargarDatosXMl(cargarXML(rutaArchivo));
    }

    public static boolean guardarLista(String rutaArchivo, ListaEmpleados lista){
        try (Writer salida = new PrintWriter(new FileOutputStream(rutaArchivo))) {
            salida.write(lista.toStringXML());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
