package com.app.proposta.Proposta.App.api.dto;

public record PropostaRespostaDto(
        Long id,
        String nome,
        String sobrenome,
        String cpf,
        String telefone,
        Double renda,
        String valorSolicitadoFmt,
        int prazoPagamento
) {
}
