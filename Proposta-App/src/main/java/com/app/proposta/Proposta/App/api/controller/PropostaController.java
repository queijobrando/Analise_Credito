package com.app.proposta.Proposta.App.api.controller;

import com.app.proposta.Proposta.App.api.dto.PropostaDto;
import com.app.proposta.Proposta.App.api.dto.PropostaRespostaDto;
import com.app.proposta.Proposta.App.domain.service.PropostaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("proposta")
@AllArgsConstructor
public class PropostaController implements GenericController {

    private final PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaRespostaDto> criar(@RequestBody PropostaDto dto){
        PropostaRespostaDto proposta = propostaService.criar(dto);

        URI location = generateHeaderLocation(proposta.id());

        return ResponseEntity.created(location).body(proposta);
    }

    @GetMapping("{id}")
    public ResponseEntity<PropostaRespostaDto> exibirProposta(@PathVariable Long id){
        return ResponseEntity.ok(propostaService.getProposta(id));
    }

    @GetMapping
    public ResponseEntity<List<PropostaRespostaDto>> listarTodas(){
        return ResponseEntity.ok(propostaService.listarPropostas());
    }

}
