package br.gov.sp.fatec.projetomineda.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.projetomineda.entity.Autorizacao;
import br.gov.sp.fatec.projetomineda.entity.Pedido;
import br.gov.sp.fatec.projetomineda.entity.Cliente;
import br.gov.sp.fatec.projetomineda.repository.AutorizacaoRepository;
import br.gov.sp.fatec.projetomineda.repository.ClienteRepository;
import br.gov.sp.fatec.projetomineda.repository.PedidoRepository;
import br.gov.sp.fatec.projetomineda.exception.RegNotFoundException;

@Service("SegurancaService")
public class SegurancaServiceImpl implements SegurancaService {

    @Autowired
    private AutorizacaoRepository autRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    @Transactional
    public Cliente criarCliente(String nome, String email, String senha, String autorizacao) {
        Autorizacao aut = autRepo.findByNome(autorizacao);
        if (aut == null) {
            aut = new Autorizacao();
            aut.setNome(autorizacao);
            autRepo.save(aut);
        }
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSenha(senha);
        cliente.setAutorizacoes(new HashSet<Autorizacao>());
        cliente.getAutorizacoes().add(aut);
        clienteRepo.save(cliente);
        return cliente;

    }

    @Transactional
    public Pedido criarPedido(String desc, double price, String email) {
        Cliente cliente = clienteRepo.buscarClientePorEmail(email);
        Pedido pedido = new Pedido();
        if(cliente != null){
            
            pedido.setDesc(desc);
            pedido.setPrice(price);
            cliente.setPedidos(new HashSet<Pedido>());
            cliente.getPedidos().add(pedido);
            pedidoRepo.save(pedido);
            clienteRepo.save(cliente);   
            return pedido;      
        }        
        throw new RegNotFoundException("Cliente não encontrado");
    }
    
    @Override
    public List<Cliente> buscarTodosClientes(){
        return clienteRepo.findAll();
    }

    @Override
    public List<Autorizacao>  listarAutorizacoes(){
        return autRepo.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Long id){
        Optional<Cliente> clienteOp = clienteRepo.findById(id);
        if(clienteOp.isPresent()) {
            return clienteOp.get();            
        }
        throw new RegNotFoundException("Cliente não encontrado!");
    }

    @Override
    public Cliente buscarClientePorNome(String nome){
        Cliente cliente = clienteRepo.findByNome(nome);
        if(cliente != null) {
            return cliente;            
        }
        throw new RegNotFoundException("Cliente não encontrado!");
    }

    @Override
    public Autorizacao buscarAutorizacaoPorNome(String nome){
        Autorizacao autorizacao = autRepo.findByNome(nome);
        if(autorizacao != null){
            return autorizacao;
        }
        throw new RegNotFoundException("Autorizacao não encontrada!");
    }

    @Override
    public List<Pedido> listarPedidos(){
        return pedidoRepo.findAll();
    }


    @Override
    public Pedido buscarPedidoPorId(Long id){
        Pedido pedido = pedidoRepo.buscarPedidoPorId(id);
        if(pedido != null) {
            return pedido;           
        }
        throw new RegNotFoundException("Pedido não encontrado!");
    }

    @Override
    public Pedido buscarPedidoPorDescricao(String desc){
        Pedido pedido = pedidoRepo.buscarPedidoPorDescricao(desc);
        if(pedido != null){
            return pedido;
        }
        throw new RegNotFoundException("Pedido não encontrado!");
    }


}