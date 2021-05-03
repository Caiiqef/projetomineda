package br.gov.sp.fatec.projetomineda.controller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.sp.fatec.projetomineda.entity.Cliente;
import br.gov.sp.fatec.projetomineda.services.SegurancaService;

@RestController
@RequestMapping(value = "/cliente")
@CrossOrigin
public class ClienteController {
    
    @Autowired
    private SegurancaService segurancaService;

    @JsonView(View.ClienteResumo.class)
    @GetMapping
    public List<Cliente> buscarClientes(){
        return segurancaService.buscarTodosClientes();
    }

    @JsonView(View.ClienteCompleto.class)
    @GetMapping(value = "/{id}")
    public Cliente buscarClientePorId(@PathVariable("id") Long id){
        return segurancaService.buscarClientePorId(id);
    }

    @JsonView(View.ClienteResumo.class)
    @GetMapping(value = "/nome")
    public Cliente buscarClientePorNome(@RequestParam(value = "nome") String nome){
        return segurancaService.buscarClientePorNome(nome);
    }

    @PostMapping("")
    public ResponseEntity<Cliente> cadastrarNovoCliente(@RequestBody Cliente cliente,
        UriComponentsBuilder uriComponentsBuilder){
            cliente = segurancaService.criarCliente(cliente.getNome(), cliente.getEmail(), cliente.getSenha(), "ROLE_USER");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(
                uriComponentsBuilder.path("/cliente/" + cliente.getId()).build().toUri());                
        return new ResponseEntity<Cliente>(cliente, responseHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable Long id){
        Cliente deleteCliente = segurancaService.deleteCliente(id);
        return new ResponseEntity<Cliente>(deleteCliente, HttpStatus.OK);
    }

    // @PutMapping(path = "/{id}")
    // public ResponseEntity<Cliente> updateCliente(@PathVariable String nome, 
    //     @RequestBody Cliente cliente, UriComponentsBuilder uriComponentsBuilder)
    //     throws Exception{
    //         cliente = segurancaService.updateCliente(cliente.getNome());
    //         HttpHeaders responHeaders = new HttpHeaders();
    //         responHeaders.setLocation(uriComponentsBuilder.path(
    //             "/cliente/" + cliente.getNome()).build().toUri());
    //         return new ResponseEntity<Cliente>(cliente, responHeaders, HttpStatus.CREATED);
    //     }
}