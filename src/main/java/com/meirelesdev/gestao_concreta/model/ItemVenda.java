package com.meirelesdev.gestao_concreta.model;

public class ItemVenda {

    private int id;

    private Produto produto;

    private int quantidade;

    private double precoUnitario;

    private double subtotal;

    public ItemVenda() {
    }

    public ItemVenda(int id,
                     Produto produto,
                     int quantidade,
                     double precoUnitario,
                     double subtotal) {

        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    private void calcularSubtotal() {
        this.subtotal = this.quantidade * this.precoUnitario;
    }

}