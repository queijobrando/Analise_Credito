package com.app.proposta.Proposta.App.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="usuario")
@Table(name="usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String sobrenome;

    private String cpf;

    private String telefone;

    private Double renda;

    @OneToOne(mappedBy = "usuario")
    @JsonBackReference
    private Proposta proposta;

}
