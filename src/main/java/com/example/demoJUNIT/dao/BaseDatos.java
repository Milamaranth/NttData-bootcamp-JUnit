package com.example.demoJUNIT.dao;

import java.util.List;

import com.example.demoJUNIT.model.Articulo;

public interface BaseDatos {
    
    public void iniciarBBDD();

    public List<Articulo> getArticulos();

    public Articulo findArticuloById(Integer any);

    public Articulo insertarArticulo(Articulo articulo);
}
