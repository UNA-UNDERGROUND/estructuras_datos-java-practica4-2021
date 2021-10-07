package com.una.estructuras;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ListaEmpleados {

    public ListaEmpleados() {
        listaEmpleados = new ArrayList<>();
    }

    public boolean agregarEmpleado(Empleado empleado) {
        return listaEmpleados.add(empleado);
    }

    public Empleado recuperarEmpleado(int pos) {
        try {
            return listaEmpleados.get(pos);
        } catch (Exception e) {
            return null;
        }
    }

    public int getSize() {
        return listaEmpleados.size();
    }

    public boolean removerEmpleado(Empleado empleado) {
        return listaEmpleados.remove(empleado);
    }

    public boolean removerEmpleado(int pos) {
        try {
            listaEmpleados.remove(pos);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private List<Empleado> listaEmpleados;

    public String toStringXML() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder str = new StringBuilder();
        str.append("<lista_empleados>");
        for (Empleado empleado : listaEmpleados) {
            str.append("<empleado>");
            str.append("<id>");
            str.append(empleado.getId());
            str.append("</id>");
            str.append("<primer_apellido>");
            str.append(empleado.getPrimerApellido());
            str.append("</primer_apellido>");
            str.append("<segundo_apellido>");
            str.append(empleado.getSegundoApellido());
            str.append("</segundo_apellido>");
            str.append("<nombre>");
            str.append(empleado.getNombre());
            str.append("</nombre>");
            str.append("<nacimiento>");
            String fecha = empleado.getFecha().format(formatter);
            str.append(fecha);
            str.append("</nacimiento>");
            str.append("<salario_base>");
            str.append(empleado.getSalario());
            str.append("</salario_base>");
            str.append("</empleado>");
        }
        str.append("</lista_empleados>");
        return str.toString();
    }
}
