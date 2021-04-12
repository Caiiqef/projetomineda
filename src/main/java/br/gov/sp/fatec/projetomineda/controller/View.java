package br.gov.sp.fatec.projetomineda.controller;

public class View {
    public static class ClienteResumo{}
    public static class ClienteCompleto extends ClienteResumo{}

    public static class PedidoLista{}
    public static class AutorizacaoResumo {}
    
    public static class ListarAutorizacoes extends AutorizacaoResumo{}
}