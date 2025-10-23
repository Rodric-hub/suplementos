package com.suplementos.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.suplementos.tienda.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {}