package com.example.demoJUNIT.services;

import java.util.List;
import com.example.demoJUNIT.model.Articulo;

public interface CarritoCompraService {
    
    public void limpiarCesta();

    public void addArticulo(Articulo articulo);

    public Integer getNumArticulo();

    public List<Articulo> getArticulos();

    public Double totalPrice();

    public Double calculadorDescuento(Double precio, Double porcentajeDescuento);

    public List<Articulo> getArticulosBBDD();

    public Articulo findArticuloById(Integer identificador);

    public Double aplicarDescuento(int i, Double porcentajeDescuento);

    public void addAndRegisterArticulo(Articulo articulo);
}
