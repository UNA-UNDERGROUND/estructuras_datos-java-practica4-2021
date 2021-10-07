package com.una.estructuras;

import java.time.LocalDate;

public class Empleado {

    public Empleado() {
        this(-1, "", "", "", LocalDate.now(), 0F);
    }

    public Empleado(Integer id, String primerApellido, String segundoApellido, String nombre, LocalDate fecha,
            Float salario) {
        this.id = id;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.nombre = nombre;
        this.fecha = fecha;
        this.salario = salario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Float getSalario() {
        return salario;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    private Integer id;
    private String primerApellido;
    private String segundoApellido;
    private String nombre;
    private LocalDate fecha;
    private Float salario;
}
