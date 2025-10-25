package com.suplementos.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.suplementos.tienda.repository.ProductoRepository;
import com.suplementos.tienda.repository.CategoriaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public HomeController(ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        this.productoRepo = productoRepo;
        this.categoriaRepo = categoriaRepo;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        var productos = productoRepo.findTop8ByOrderByIdAsc(); // primeros 8 productos
        model.addAttribute("productos", productos);
        model.addAttribute("usuario", session.getAttribute("usuarioLogueado"));
        model.addAttribute("categorias", categoriaRepo.findAll()); // para el navbar
        return "index";
    }
}
