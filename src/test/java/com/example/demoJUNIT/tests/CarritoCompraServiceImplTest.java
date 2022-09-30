package com.example.demoJUNIT.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CustomMatcher;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.example.demoJUNIT.dao.BaseDatos;
import com.example.demoJUNIT.model.Articulo;
import com.example.demoJUNIT.services.CarritoCompraServiceImpl;

public class CarritoCompraServiceImplTest {

    private CarritoCompraServiceImpl carrito;
    private BaseDatos baseDatos;

    @BeforeEach
    public void init() {
        baseDatos = mock(BaseDatos.class);
        carrito = new CarritoCompraServiceImpl(baseDatos);
    }

    @Test
    public void testAddArticulo() {
        assertTrue(carrito.getArticulos().isEmpty());
        carrito.addArticulo(new Articulo("Pantalón", 10D));
        assertFalse(carrito.getArticulos().isEmpty());
    }

    @Test
    public void testGetNumArticulo() {
        carrito.addArticulo(new Articulo("Pantalón", 10D));
        carrito.addArticulo(new Articulo("Pantalón", 10D));
        Integer res = carrito.getNumArticulo();
        assertEquals(2, res);
    }

    @Test
    public void testGetArticulo() {
        carrito.addArticulo(new Articulo("Pantalón", 10D));
        carrito.addArticulo(new Articulo("Camisa", 10D));
        List<Articulo> res = carrito.getArticulos();

        Matcher<List<Articulo>> listMatcher = new CustomMatcher<List<Articulo>>("pantalón y camisa") {
            @Override
            public boolean matches(Object argument){
                if(argument == null || !List.class.isInstance(argument)){
                    return false;
                }
                boolean equals = true;
                List<Articulo> lista = (List<Articulo>) argument;
                equals &= lista.get(0).getNombre().equals("Pantalón");
                equals &= lista.get(0).getPrecio().equals(10D);
                equals &= lista.get(1).getNombre().equals("Camisa");
                equals &= lista.get(1).getPrecio().equals(10D);

                return equals;
            }
        };
        assertThat(res, listMatcher);
        assertEquals(2, res.size());
        assertEquals("Pantalón", res.get(0).getNombre());

    }

    @Test
    public void testGetArticulosBBDD() {
        List<Articulo> lista = new ArrayList<>();
        lista.add(new Articulo("Pantalón", 20D));
        lista.add(new Articulo("Camisa", 20D));
        when(baseDatos.getArticulos()).thenReturn(lista);
        List<Articulo> listado = carrito.getArticulosBBDD();
        assertEquals(2, listado.size());
    }

    @Test
    public void testTotalPrice() {
        carrito.addArticulo(new Articulo("Pantalón", 10D));
        carrito.addArticulo(new Articulo("Camisa", 10D));
        Double res = carrito.totalPrice();
        assertEquals(20D, res);
    }

    @Test
    public void testCalculadorDescuento() {
        carrito.addArticulo(new Articulo("Pantalón", 10D));
        carrito.addArticulo(new Articulo("Camisa", 10D));
        Double res = carrito.calculadorDescuento(20D, 10D);
        assertEquals(18D, res);
    }

    @Test
    public void testAplicarDescuento() {
        Articulo articulo = new Articulo("Pantalón", 10D);

        when(baseDatos.findArticuloById(1)).thenReturn(articulo);
        Double res = carrito.aplicarDescuento(1, 10D);
        assertEquals(9.0, res);
        verify(baseDatos, times(1)).findArticuloById(any(Integer.class));
    }

    @Test
    public void testInsertarArticulo() {
        Articulo articulo = new Articulo("Pantalón", 10D);
        Articulo articulo2 = new Articulo("Vestido", 20D);
        final int id = 9;

        when(baseDatos.insertarArticulo(articulo)).thenAnswer(invocation -> {
            Articulo art = invocation.getArgument(0);
            art.setId(id);
            return art;
        });
        carrito.addAndRegisterArticulo(articulo);
        assertEquals(id, articulo.getId());
        assertThat(carrito.getArticulos(), hasItem(articulo));
        assertThat(carrito.getArticulos(), not(hasItem(articulo2)));
        verify(baseDatos, times(1)).insertarArticulo(any(Articulo.class));



    }
    
}
