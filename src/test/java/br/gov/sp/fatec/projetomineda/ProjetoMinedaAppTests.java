package br.gov.sp.fatec.projetomineda;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.gov.sp.fatec.projetomineda.entity.Autorizacao;

import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.projetomineda.entity.Cliente;
import br.gov.sp.fatec.projetomineda.repository.AutorizacaoRepository;
import br.gov.sp.fatec.projetomineda.repository.ClienteRepository;
import br.gov.sp.fatec.projetomineda.repository.PedidoRepository;
import br.gov.sp.fatec.projetomineda.services.SegurancaService;

@SpringBootTest
@Transactional
@Rollback 
class ProjetoMinedaAppTests {

    @Autowired 
    private ClienteRepository clienteRepo;

    @Autowired 
    private AutorizacaoRepository autRepo;

    @Autowired 
    private PedidoRepository pedRepo;

    @Autowired
    private SegurancaService segService;

	@Test
	void contextLoads() {
    }

    @Test
    void testaInsercao(){
        Cliente cliente = new Cliente();
        cliente.setNome("Fernando");
        cliente.setEmail("fernando@gmail.com");
        cliente.setSenha("senha");
        cliente.setAutorizacoes(new HashSet<Autorizacao>());
        Autorizacao aut = new Autorizacao();        
        aut.setNome("USERUSER");
        autRepo.save(aut);
        cliente.getAutorizacoes().add(aut);
        clienteRepo.save(cliente);
        assertNotNull(cliente.getAutorizacoes().iterator().next().getId());
    }

    @Test
    void testaInsercaoAutorizacao(){
        Cliente cliente = new Cliente();
        cliente.setNome("Felipe");
        cliente.setEmail("felipe@gmail.com");
        cliente.setSenha("1234");
        clienteRepo.save(cliente);        
        Autorizacao aut = new Autorizacao();        
        aut.setNome("USERUSER33");
        aut.setCliente(new HashSet<Cliente>());
        aut.getCliente().add(cliente);
        autRepo.save(aut);      
        assertNotNull(aut.getCliente().iterator().next().getId());
    }

    @Test
    void testaAutorizacao(){
        Cliente cliente = clienteRepo.findById(1L).get();
        assertEquals("USER", cliente.getAutorizacoes().iterator().next().getNome());
    }

    @Test
    void testaBuscaClienteNomeContains(){
        List<Cliente> clientes = clienteRepo.findByNomeContainsIgnoreCase("F");
        assertFalse(clientes.isEmpty());       
    }

    @Test
    void testaBuscaClienteNome(){
        Cliente cliente = clienteRepo.findByNome("Jeff");
        assertNotNull(cliente);       
    }

    @Test
    void testaBuscaClienteNomeSenha(){
        Cliente cliente = clienteRepo.findByNomeAndSenha("Rafael", "S3nH4");
        assertNotNull(cliente);       
    }

    @Test
    void testaBuscaClienteNomeAutorizacao(){
        List<Cliente> clientes = clienteRepo.findByAutorizacoesNome("ADMIN");
        assertFalse(clientes.isEmpty());       
    }

    @Test
    void testaBuscaClienteNomeQuery(){
        Cliente cliente = clienteRepo.buscaClientePorNome("Caique");
        assertNotNull(cliente);       
    }

    @Test
    void testaBuscaClienteNomeSenhaQuery(){
        Cliente cliente = clienteRepo.buscaClientePorNomeESenha("Rafael", "S3nH4");
        assertNotNull(cliente);       
    }

    @Test
    void testaBuscaClienteNomeAutorizacaoQuery(){
        List<Cliente> clientes = clienteRepo.buscaPorNomeAutorizacao("ADMIN");
        assertFalse(clientes.isEmpty());       
    }

    @Test
    void testaServicoCriaCliente(){
        Cliente cliente = segService.criarCliente("Mineda", "mineda@gmail.com", "senha", "USER");
        assertNotNull(cliente);
    }

    @Test
    void testaBuscaClienteNomePedidoQuery(){
        Cliente cliente = clienteRepo.buscaPorNomePedido("000001");
        assertNotNull(cliente);         
    }
}