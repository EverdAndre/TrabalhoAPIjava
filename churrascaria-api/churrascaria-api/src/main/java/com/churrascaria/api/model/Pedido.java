package com.churrascaria.api.model;

import jakarta.persistence.*;

@Entity
    @Table(name="pedido")

    public class Pedido {
        @Id

        @Column(name="id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="produtos")
        private String produtos;

        @Column (name="quantidade")
        private double quantidade;

        @Column (name="valor")
        private double valor;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getProdutos() {
            return produtos;
        }

        public void setProdutos(String produtos) {
            this.produtos = produtos;
        }

        public double getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(double quantidade) {
            this.quantidade = quantidade;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
        @Override
        public String toString() {
            return "Pedido{" +
                    "id=" + id +
                    ", produtos='" + produtos + '\'' +
                    ", quantidade='" + quantidade + '\'' +
                    ", valor=" + valor +
                    '}';


        }


    }
