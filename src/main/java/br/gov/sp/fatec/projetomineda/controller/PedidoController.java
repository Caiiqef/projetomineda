package br.gov.sp.fatec.projetomineda.controller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import br.gov.sp.fatec.projetomineda.entity.Pedido;
import br.gov.sp.fatec.projetomineda.services.SegurancaService;

@RestController
@RequestMapping(value = "/pedido")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidoController {

    @Autowired
    private SegurancaService segurancaService;

    @JsonView(View.PedidoLista.class)
    @GetMapping
    public List<Pedido> listarPedidos(){
        return segurancaService.listarPedidos();
    }

    @JsonView(View.PedidoLista.class)
    @GetMapping(value = "/desc")
    public Pedido buscarPedidoPorDescricao(@RequestParam(value = "desc") String desc){
        return segurancaService.buscarPedidoPorDescricao(desc);
    }

    @JsonView(View.PedidoLista.class)
    @GetMapping(value = "/{id}")
    public Pedido buscarPedidoPorId(@PathVariable("id") Long id){
        return segurancaService.buscarPedidoPorId(id);
    }

    @JsonView(View.PedidoLista.class)
    @PostMapping("")
    public ResponseEntity<Pedido> cadastrarNovoPedido(@RequestBody Pedido pedido, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        pedido = segurancaService.criarPedido(pedido.getDesc(), pedido.getPrice(), "caiquefernandes@gmail.com");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponentsBuilder.path("/pedido/" + pedido.getId()).build().toUri());
        return new ResponseEntity<>(pedido, responseHeaders, HttpStatus.CREATED);
    }    

    @DeleteMapping("/{id}")
    public ResponseEntity<Pedido> deletePedido(@PathVariable Long id){
        Pedido deletePedido = segurancaService.deletePedido(id);
        return new ResponseEntity<Pedido>(deletePedido, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        pedido = segurancaService.atualizarValorPedido(pedido.getPrice(), id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponentsBuilder.path("/pedido/" + pedido.getId()).build().toUri());
        return new ResponseEntity<Pedido>(pedido, responseHeaders, HttpStatus.CREATED);
    }

}