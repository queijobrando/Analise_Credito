package com.app.proposta.Proposta.App.api.dto;

public record PropostaDto(
        String nome,
        String sobrenome,
        String cpf,
        String telefone,
        Double renda,
        Double valorSolicitado,
        int prazoPagamento
) {

}
