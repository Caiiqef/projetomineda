package br.gov.sp.fatec.projetomineda.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

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

    @Autowired
    private PasswordEncoder passEncoder;

    @Transactional
    @PreAuthorize("isAuthenticated()")
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
        cliente.setSenha(passEncoder.encode(senha));
        cliente.setAutorizacoes(new HashSet<Autorizacao>());
        cliente.getAutorizacoes().add(aut);
        clienteRepo.save(cliente);
        return cliente;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Pedido criarPedido(String desc, double price, String email) {
        Cliente cliente = clienteRepo.buscarClientePorEmail(email);
        Pedido pedido = new Pedido();
        if(cliente != null){
            
            pedido.setDesc(desc);
            pedido.setPrice(price);
            cliente.setPedidos(new HashSet<Pedido>());
            cliente.getPedidos().add(pedido);
            pedido.setCliente(new HashSet<Cliente>());
            pedido.getCliente().add(cliente);
            pedidoRepo.save(pedido);
            clienteRepo.save(cliente);   
            return pedido;      
        }        
        throw new RegNotFoundException("Cliente não encontrado");
    }

    @PreAuthorize("isAuthenticated()")
    public Cliente atualizarCliente(String nome, String email, String senha, Long id){
        Cliente cliente = clienteRepo.buscarClientePorId(id);
        if(cliente != null){
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setSenha(senha);
            clienteRepo.save(cliente);
            return cliente;
        }
        throw new RegNotFoundException("Cliente não encontrado!");
    }

    @PreAuthorize("isAuthenticated()")
    public Pedido atualizarValorPedido(double price, Long id){
        Pedido pedido = pedidoRepo.buscarPedidoPorId(id);
        if (pedido != null) {
            pedido.setPrice(price);
            pedidoRepo.save(pedido);
            return pedido;            
        }
        throw new RegNotFoundException("Pedido não encontrado!");
    }
    
    @Override
    //@PreAuthorize("hasRole('ADMIN')")
    public List<Cliente> buscarTodosClientes(){
        return clienteRepo.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Autorizacao>  listarAutorizacoes(){
        return autRepo.findAll();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Cliente buscarClientePorId(Long id){
        Optional<Cliente> clienteOp = clienteRepo.findById(id);
        if(clienteOp.isPresent()) {
            return clienteOp.get();            
        }
        throw new RegNotFoundException("Cliente não encontrado!");
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Cliente buscarClientePorNome(String nome){
        Cliente cliente = clienteRepo.findByNome(nome);
        if(cliente != null) {
            return cliente;            
        }
        throw new RegNotFoundException("Cliente não encontrado!");
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Autorizacao buscarAutorizacaoPorNome(String nome){
        Autorizacao autorizacao = autRepo.findByNome(nome);
        if(autorizacao != null){
            return autorizacao;
        }
        throw new RegNotFoundException("Autorizacao não encontrada!");
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Pedido> listarPedidos(){
        return pedidoRepo.findAll();
    }


    @Override
    @PreAuthorize("isAuthenticated()")
    public Pedido buscarPedidoPorId(Long id){
        Pedido pedido = pedidoRepo.buscarPedidoPorId(id);
        if(pedido != null) {
            return pedido;           
        }
        throw new RegNotFoundException("Pedido não encontrado!");
    }

    

    @Override
    @PreAuthorize("isAuthenticated()")
    public Pedido buscarPedidoPorDescricao(String desc){
        Pedido pedido = pedidoRepo.buscarPedidoPorDescricao(desc);
        if(pedido != null){
            return pedido;
        }
        throw new RegNotFoundException("Pedido não encontrado!");
    }

    @Override
    public Pedido deletePedido(Long id){
        Optional<Pedido> pedido = pedidoRepo.findById(id);
        if(pedido.isPresent()){
            Pedido p = pedido.get();
            for (Cliente cliente : pedido.get().getCliente()) {
                p.removeCliente(cliente);
                clienteRepo.save(cliente);
            }
            pedidoRepo.save(p);

            pedidoRepo.deleteById(pedido.get().getId());
            return pedido.get();
        }
        throw new RegNotFoundException("Pedido inexistente");
    }

    public Cliente deleteCliente(Long id){
        Optional<Cliente> cliente = clienteRepo.findById(id);
        if(cliente.isPresent()){
            clienteRepo.deleteById(cliente.get().getId());
            return cliente.get();
        }
        throw new RegNotFoundException("Cliente inexistente");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Cliente cliente = clienteRepo.findByNome(username);
        if (cliente == null) {
            throw new UsernameNotFoundException("Usuário " + username + " não encontrado!");
        }
        return User.builder().username(username).password(cliente.getSenha())
                .authorities(cliente.getAutorizacoes().stream().map(Autorizacao::getNome).collect(Collectors.toList())
                        .toArray(new String[cliente.getAutorizacoes().size()]))
                .build();
    }

    // public Cliente atualizarCliente(Cliente cliente, Cliente novoCliente) {
    //     // cliente.setNome(novoCliente.getNome());
    //     cliente.setEmail(novoCliente.getEmail());
    //     // cliente.setSenha(novoCliente.getSenha());
    //     clienteRepo.save(cliente);
    //     return cliente;
    // }

    // @Transactional
    // public Cliente updatCliente(String nome, String email, String senha) {

    //     Cliente cliente = new Cliente();
    //     cliente.setNome(nome);
    //     cliente.setEmail(email);
    //     cliente.setSenha(senha);
    //     clienteRepo.save(cliente);
    //     return cliente;
    // }  
}