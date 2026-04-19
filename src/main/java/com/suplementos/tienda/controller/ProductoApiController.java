package com.suplementos.tienda.controller;

import com.suplementos.tienda.model.Producto;
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
    public List<Producto> listar() {
        return productoRepo.findAll();
    }

    @GetMapping("/categoria/{id}")
    public List<Producto> porCategoria(@PathVariable Long id) {
        return productoRepo.findByCategoriaId(id);
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoRepo.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto nuevo) {
        return productoRepo.findById(id)
                .map(p -> {
                    p.setNombre(nuevo.getNombre());
                    p.setPrecio(nuevo.getPrecio());
                    p.setCategoria(nuevo.getCategoria());
                    return productoRepo.save(p);
                })
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoRepo.deleteById(id);
    }
}
