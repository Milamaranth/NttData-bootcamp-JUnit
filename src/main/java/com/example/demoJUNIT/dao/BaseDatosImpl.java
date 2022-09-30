package com.example.demoJUNIT.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demoJUNIT.model.Articulo;

public class BaseDatosImpl implements BaseDatos{
    
    private Map<Integer,Articulo> baseDatos = new HashMap<>();

    @Override
    public void iniciarBBDD() {
        baseDatos = new HashMap<>();
        baseDatos.put(1, new Articulo("Pantalón",20D));
    }

    @Override
    public List<Articulo> getArticulos(){
        return baseDatos.values().stream().toList();
    }

    @Override
    public Articulo findArticuloById(Integer id) {
        return baseDatos.get(id);
    }

    @Override
    public Articulo insertarArticulo(Articulo articulo) {
        int id = nuevoId();
        articulo.setId(id);
        baseDatos.put(nuevoId(), articulo);
        return articulo;
    }

    private int nuevoId() {
        Integer i = 1;
        while (baseDatos.containsKey(i)) {
            i = i+1;
        }
        return i;
    }
}
