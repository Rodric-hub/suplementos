package com.suplementos.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.suplementos.tienda.model.Producto;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findTop8ByOrderByIdAsc();  // <- agregado
    List<Producto> findByCategoriaId(Long categoriaId); // <- agregado
}
