package com.suplementos.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.suplementos.tienda.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}