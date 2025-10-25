package com.suplementos.tienda.controller;

import com.suplementos.tienda.repository.ProductoRepository;
import com.suplementos.tienda.repository.CategoriaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public ProductoController(ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping("/productos")
    public String listarProductos(Model model, HttpSession session) {
        model.addAttribute("productos", productoRepo.findAll());
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "productos";
    }

    // --- Productos por categoría ---
    @GetMapping("/productos/categoria/{id}")
    public String productosPorCategoria(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("productos", productoRepo.findByCategoriaId(id));
        model.addAttribute("categorias", categoriaRepo.findAll());
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        return "productos"; // misma vista, solo filtrada
    }
}
