package com.suplementos.tienda.controller;

import jakarta.servlet.http.HttpSession;
import com.suplementos.tienda.model.Usuario;
import com.suplementos.tienda.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;

    public UsuarioController(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @PostMapping("/registro")
public String registrarUsuario(@ModelAttribute Usuario usuario, HttpSession session) {
    usuarioRepo.save(usuario);
    session.setAttribute("usuarioLogueado", usuario); // Iniciar sesión automáticamente
    return "redirect:/";
}

    @PostMapping("/login")
public String loginUsuario(@RequestParam String email, 
     @RequestParam String contrasena,
     HttpSession session) {
    Usuario u = usuarioRepo.findAll().stream()
            .filter(x -> x.getEmail().equals(email) && x.getContrasena().equals(contrasena))
            .findFirst().orElse(null);
    if (u != null) {
        session.setAttribute("usuarioLogueado", u);
        return "redirect:/";
    } else {
        return "redirect:/?error=true";
    }
}
@GetMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate();  
    return "redirect:/";
}
}