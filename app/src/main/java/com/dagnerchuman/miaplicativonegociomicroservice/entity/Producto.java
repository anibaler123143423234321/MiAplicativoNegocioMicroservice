package com.dagnerchuman.miaplicativonegociomicroservice.entity;

import java.io.Serializable;
import java.util.Objects;

public class Producto implements Serializable {

    private Long id;
    private String nombre;
    private Long categoriaId;
    private String picture;
    private Double precio;
    private String fechaCreacion;
    private Long negocioId;
    private Integer stock;

    public Producto(Long id, String nombre, Long categoriaId, String picture, Double precio, String fechaCreacion, Long negocioId) {
        this.id = id;
        this.nombre = nombre;
        this.categoriaId = categoriaId;
        this.picture = picture;
        this.precio = precio;
        this.fechaCreacion = fechaCreacion;
        this.negocioId = negocioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(Long negocioId) {
        this.negocioId = negocioId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoriaId=" + categoriaId +
                ", picture='" + picture + '\'' +
                ", precio=" + precio +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", negocioId=" + negocioId +
                ", stock=" + stock +
                '}';
    }
}
