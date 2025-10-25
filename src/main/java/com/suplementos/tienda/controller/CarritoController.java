package com.suplementos.tienda.controller;

import com.suplementos.tienda.model.Carrito;
import com.suplementos.tienda.model.Producto;
import com.suplementos.tienda.model.Usuario;
import com.suplementos.tienda.repository.CarritoRepository;
import com.suplementos.tienda.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoRepository carritoRepo;
    private final ProductoRepository productoRepo;

    public CarritoController(CarritoRepository carritoRepo, ProductoRepository productoRepo) {
        this.carritoRepo = carritoRepo;
        this.productoRepo = productoRepo;
    }

    // POST para añadir producto
    @PostMapping("/agregar/{id}")
    public String agregar(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/?login=true"; // Usuario no logueado
        }

        Producto p = productoRepo.findById(id).orElse(null);
        if (p == null) {
            ra.addFlashAttribute("msgError", "Producto no encontrado ❌");
            return "redirect:/";
        }

        // Verificar si ya existe el producto en el carrito del usuario
        Carrito existente = carritoRepo.findByUsuarioAndProducto(usuario, p);

        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + 1); // Aumenta cantidad
            carritoRepo.save(existente);
        } else {
            Carrito c = new Carrito();
            c.setUsuario(usuario);
            c.setProducto(p);
            c.setCantidad(1);
            carritoRepo.save(c);
        }

        ra.addFlashAttribute("msgExito", "Producto añadido al carrito ✅");
        return "redirect:/";
    }

    // Evita error con GET
    @GetMapping("/agregar/{id}")
    public String agregarGet() {
        return "redirect:/";
    }

    // Ver carrito
    @GetMapping
    public String verCarrito(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/?login=true";
        }
        List<Carrito> lista = carritoRepo.findByUsuario(usuario);
        model.addAttribute("carrito", lista);
        return "carrito";
    }

    // Eliminar del carrito
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        carritoRepo.deleteById(id);
        return "redirect:/carrito";
    }
}
