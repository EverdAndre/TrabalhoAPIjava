package com.churrascaria.api.repository;

import com.churrascaria.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByProdutos(String produtos);
    List<Pedido> findByProdutosContainingIgnoreCase(String termo);
}
