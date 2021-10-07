package com.una.estructuras;

import java.util.ArrayList;
import java.util.List;

import com.una.util.XMLParser;

public class EmpleadoDAO {

    public EmpleadoDAO() {
        empleados = new ListaEmpleados();
        rutaArchivo = "";
    }

    public EmpleadoDAO(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        cargar();
    }

    // CRUD
    // Create
    public boolean agregarEmpleado(Empleado empleado) {
        return empleados.agregarEmpleado(empleado);
    }

    // Recover
    public Empleado recuperarEmpleado(Integer idEmpleado) {
        int pos = posicionEmpleado(idEmpleado);
        return pos != -1 ? empleados.recuperarEmpleado(pos) : null;
    }

    public List<Empleado> recuperarEmpleados() {
        List<Empleado> resultado = new ArrayList<>(empleados.getSize());
        for (int i = 0; i < empleados.getSize(); i++) {
            resultado.add(empleados.recuperarEmpleado(i));
        }
        return resultado;
    }

    // Update

    public boolean actualizarEmpleado(Integer idEmpleado, Empleado empleado) {
        Empleado actual = recuperarEmpleado(idEmpleado);
        if (actual != null) {
            actual.setId(empleado.getId());
            actual.setPrimerApellido(empleado.getPrimerApellido());
            actual.setSegundoApellido(empleado.getSegundoApellido());
            actual.setNombre(empleado.getNombre());
            actual.setFecha(empleado.getFecha());
            actual.setSalario(empleado.getSalario());
        }
        return false;
    }

    // Delete

    public boolean removerEmpleado(Integer idEmpleado) {
        int pos = posicionEmpleado(idEmpleado);
        if (pos != -1) {
            empleados.removerEmpleado(pos);
        }
        return false;
    }

    // implementacion
    int posicionEmpleado(Integer idEmpleado) {
        for (int i = 0; i < empleados.getSize(); i++) {
            Empleado actual = empleados.recuperarEmpleado(i);
            if (actual.getId() == idEmpleado) {
                return i;
            }
        }
        return -1;
    }

    // persistencia
    public boolean cargar() {
        if (rutaArchivo.isEmpty()) {
            return false;
        }
        ListaEmpleados val = XMLParser.cargarLista(rutaArchivo);
        if (val != null) {
            empleados = val;
        }
        return val != null;
    }

    public boolean guardar() {
        if (rutaArchivo.isEmpty()) {
            return false;
        }
        return XMLParser.guardarLista(rutaArchivo, empleados);
    }

    private String rutaArchivo;
    private ListaEmpleados empleados;
}
