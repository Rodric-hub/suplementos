package com.suplementos.tienda.controller;

import com.suplementos.tienda.repository.ProductoRepository;
import com.suplementos.tienda.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoApiController {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public ProductoApiController(ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping
    public List<?> listar() {
        return productoRepo.findAll();
    }

    @GetMapping("/categoria/{id}")
    public List<?> porCategoria(@PathVariable Long id) {
        return productoRepo.findByCategoriaId(id);
    }
}
