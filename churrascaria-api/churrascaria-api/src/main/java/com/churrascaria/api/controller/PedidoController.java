package com.churrascaria.api.controller;
import com.churrascaria.api.model.Pedido;
import com.churrascaria.api.repository.PedidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/pedido")

public class PedidoController {
    private PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository){this.pedidoRepository = pedidoRepository; }

    @GetMapping
    public List<Pedido> listarOuBuscar(@RequestParam(name = "produto", required = false) String termo) {
        if (termo == null || termo.isBlank()) {
            return pedidoRepository.findAll();
        }
        return pedidoRepository.findByProdutosContainingIgnoreCase(termo);
    }


    @GetMapping(params = "id")
    public ResponseEntity<Pedido> obterPorIdParam(@RequestParam Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> salvar(@Valid @RequestBody Pedido pedido) {
        pedido.setId(null);
        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity
                .created(URI.create("/pedido/" + salvo.getId())) // 201 + Location
                .body(salvo);
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<Void> deletarPorQuery(@RequestParam Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id,
                                            @Valid @RequestBody Pedido pedido) {
        return pedidoRepository.findById(id)
                .map(existente -> {
                    existente.setProdutos(pedido.getProdutos());
                    existente.setQuantidade(pedido.getQuantidade());
                    existente.setValor(pedido.getValor());
                    Pedido atualizado = pedidoRepository.save(existente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());

    }
    @PatchMapping("/{id}")
    public ResponseEntity<Pedido> atualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        return pedidoRepository.findById(id)
                .map(existente -> {
                    if (updates.containsKey("produtos")) {
                        existente.setProdutos((String) updates.get("produtos"));
                    }
                    if (updates.containsKey("quantidade")) {
                        existente.setQuantidade(((Number) updates.get("quantidade")).doubleValue());
                    }
                    if (updates.containsKey("valor")) {
                        existente.setValor(((Number) updates.get("valor")).doubleValue());
                    }
                    Pedido atualizado = pedidoRepository.save(existente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
