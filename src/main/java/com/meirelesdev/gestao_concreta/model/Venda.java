package com.meirelesdev.gestao_concreta.model;

import java.time.LocalDate;

public class Venda {

    private int id;

    private LocalDate dataVenda;

    private String formaPagamento;

    private double valorTotal;

    public Venda() {
    }

    public Venda(int id,
                 LocalDate dataVenda,
                 String formaPagamento,
                 double valorTotal) {

        this.id = id;
        this.dataVenda = dataVenda;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

}