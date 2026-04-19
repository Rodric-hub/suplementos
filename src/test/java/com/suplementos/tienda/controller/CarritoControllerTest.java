package com.suplementos.tienda.controller;

import com.suplementos.tienda.model.Producto;
import com.suplementos.tienda.model.Usuario;
import com.suplementos.tienda.repository.CarritoRepository;
import com.suplementos.tienda.repository.ProductoRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(CarritoController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarritoRepository carritoRepo;

    @MockBean
    private ProductoRepository productoRepo;

    @Test
    void agregar_producto_no_logueado_redirige_login() throws Exception {

        mockMvc.perform(post("/carrito/agregar/1")
                .with(csrf())
                .flashAttr("msg", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?login=true"));
    }

    @Test
    void agregar_producto_existente_redirige_home() throws Exception {

        Usuario usuario = new Usuario();
        Producto producto = new Producto();

        Mockito.when(productoRepo.findById(1L)).thenReturn(Optional.of(producto));

        Mockito.when(carritoRepo.findByUsuarioAndProducto(usuario, producto))
                .thenReturn(null);

        mockMvc.perform(post("/carrito/agregar/1")
                .with(csrf())
                .sessionAttr("usuarioLogueado", usuario)
                .flashAttr("msg", "")) // 👈 clave
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void agregar_producto_no_existente_redirige_home() throws Exception {

        Usuario usuario = new Usuario();

        Mockito.when(productoRepo.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/carrito/agregar/1")
                .with(csrf())
                .sessionAttr("usuarioLogueado", usuario))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}