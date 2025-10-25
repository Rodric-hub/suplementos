package com.suplementos.tienda.controller;

import jakarta.servlet.http.HttpSession;
import com.suplementos.tienda.model.Usuario;
import com.suplementos.tienda.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login-manual")
    public String loginUsuario(@RequestParam String email,
                               @RequestParam String contrasena,
                               HttpSession session,
                               Model model) {

        Usuario u = usuarioRepo.findAll().stream()
                .filter(x -> x.getEmail().equals(email)
                        && passwordEncoder.matches(contrasena, x.getContrasena()))
                .findFirst().orElse(null);

        if (u != null) {
            session.setAttribute("usuarioLogueado", u);
            return "redirect:/";
        } else {
            model.addAttribute("loginError", true);
            return "index";
        }
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, HttpSession session) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioRepo.save(usuario);
        session.setAttribute("usuarioLogueado", usuario);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
