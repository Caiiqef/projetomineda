package br.gov.sp.fatec.projetomineda.controller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.projetomineda.entity.Pedido;
import br.gov.sp.fatec.projetomineda.services.SegurancaService;

@RestController
@RequestMapping(value = "/pedido")
@CrossOrigin
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

    @PostMapping
    public Pedido cadastrarNovoPedido(@RequestBody Pedido pedido) throws Exception {
        return segurancaService.criarPedido(pedido.getDesc(), pedido.getPrice(), "caiquefernandes@gmail.com");
    }
   
    
}