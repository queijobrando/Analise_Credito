package com.app.proposta.Proposta.App.infra.repository;

import com.app.proposta.Proposta.App.domain.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    List<Proposta> findAllByIntegradaIsFalse();

}
