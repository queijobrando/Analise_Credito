package com.app.proposta.Proposta.App.infra.repository;

import com.app.proposta.Proposta.App.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
