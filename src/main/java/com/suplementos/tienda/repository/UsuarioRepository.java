package com.suplementos.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.suplementos.tienda.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
