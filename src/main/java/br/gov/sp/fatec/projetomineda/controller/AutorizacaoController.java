package br.gov.sp.fatec.projetomineda.controller;
import br.gov.sp.fatec.projetomineda.entity.Autorizacao;
import br.gov.sp.fatec.projetomineda.services.SegurancaService;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/autorizacao")
@CrossOrigin

public class AutorizacaoController {

    @Autowired
    private SegurancaService segurancaService;

    @JsonView(View.ListarAutorizacoes.class)
    @GetMapping
    public List<Autorizacao> buscarAutorizacoes(){
        return segurancaService.listarAutorizacoes();
    }

    @JsonView(View.AutorizacaoResumo.class)
    @GetMapping(value = "/nome/{autorizacao}")
    public Autorizacao buscarAutorizacaoPorNome(@PathVariable("autorizacao") String nome){
        return segurancaService.buscarAutorizacaoPorNome(nome);
    }   
}