package br.gov.sp.fatec.projetomineda.services;

import br.gov.sp.fatec.projetomineda.entity.Cliente;
import br.gov.sp.fatec.projetomineda.entity.Pedido;

public interface SegurancaService {

    public Cliente criarCliente(String nome, String email, String senha, String autorizacao);
    public Pedido criarPedido(String desc, double price, String email);
}
