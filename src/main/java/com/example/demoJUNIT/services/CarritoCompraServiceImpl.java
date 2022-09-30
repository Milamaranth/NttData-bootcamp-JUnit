package com.example.demoJUNIT.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demoJUNIT.dao.BaseDatos;
import com.example.demoJUNIT.model.Articulo;

public class CarritoCompraServiceImpl implements CarritoCompraService {

    private List<Articulo> cesta = new ArrayList<>();

    private BaseDatos baseDatos;

    public CarritoCompraServiceImpl(BaseDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    @Override
    public void limpiarCesta() {
        cesta.clear();

    }

    @Override
    public void addArticulo(Articulo articulo) {
        cesta.add(articulo);
    }

    @Override
    public Integer getNumArticulo() {
        Integer res = cesta.size();
        return res;
    }

    @Override
    public List<Articulo> getArticulos() {
        return cesta;
    }

    @Override
    public Articulo findArticuloById(Integer identificador){
        return baseDatos.findArticuloById(identificador);
    }

    @Override
    public List<Articulo> getArticulosBBDD() {
        baseDatos.iniciarBBDD();
        return baseDatos.getArticulos();
    }

    @Override
    public Double totalPrice() {
        Double precio = 0D;
        for (Articulo articulo : cesta) {
            precio += articulo.getPrecio();
        }
        return precio;
    }

    @Override
    public Double calculadorDescuento(Double precio, Double porcentajeDescuento) {
        return precio - precio * porcentajeDescuento * 0.01;
    }

    @Override
    public Double aplicarDescuento(int i, Double porcentajeDescuento) {
        Articulo articulo = baseDatos.findArticuloById(i);
        if(Optional.ofNullable(articulo).isPresent()){
            return calculadorDescuento(articulo.getPrecio(), 10D);
        }else{
            System.out.println("No se encuentra art por ID" + i);
        }
        return null;
    }

    @Override
    public void addAndRegisterArticulo(Articulo articulo) {
        baseDatos.insertarArticulo(articulo);
        addArticulo(articulo);
    }

}
