package br.gov.sp.fatec.projetomineda.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.projetomineda.entity.Autorizacao;
import br.gov.sp.fatec.projetomineda.entity.Pedido;
import br.gov.sp.fatec.projetomineda.entity.Cliente;
import br.gov.sp.fatec.projetomineda.repository.AutorizacaoRepository;
import br.gov.sp.fatec.projetomineda.repository.ClienteRepository;
import br.gov.sp.fatec.projetomineda.repository.PedidoRepository;

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
        //HashSet não permite ter elementos iguais.
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
        
        else{
            return null;
        }        
    }    
}