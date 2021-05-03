package br.gov.sp.fatec.projetomineda.services;

import java.util.List;

import br.gov.sp.fatec.projetomineda.entity.Autorizacao;
import br.gov.sp.fatec.projetomineda.entity.Cliente;
import br.gov.sp.fatec.projetomineda.entity.Pedido;

public interface SegurancaService {

    public Cliente criarCliente(String nome, String email, String senha, String autorizacao);
    public Pedido criarPedido(String desc, double price, String email);
    
    public List<Cliente> buscarTodosClientes();
    public Cliente buscarClientePorId(Long id);
    public Cliente buscarClientePorNome(String nome);
    public Cliente deleteCliente(Long id);
    // public Cliente updateCliente(String nome);

    public Autorizacao buscarAutorizacaoPorNome(String nome);
    public List<Autorizacao> listarAutorizacoes();

    public List<Pedido> listarPedidos();
    public Pedido buscarPedidoPorId(Long id);
    public Pedido buscarPedidoPorDescricao(String desc);
    public Pedido atualizarValorPedido(double price, Long id);
    public Pedido deletePedido(Long id);
}
