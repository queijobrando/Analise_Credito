package com.app.proposta.Proposta.App.domain.service;

import com.app.proposta.Proposta.App.api.dto.PropostaDto;
import com.app.proposta.Proposta.App.api.dto.PropostaRespostaDto;
import com.app.proposta.Proposta.App.api.mapper.PropostaMapper;
import com.app.proposta.Proposta.App.domain.model.Proposta;
import com.app.proposta.Proposta.App.infra.repository.PropostaRepository;
import com.app.proposta.Proposta.App.infra.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final PropostaMapper propostaMapper;
    private final NotificacaoService notificacaoService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private String propostaExchange; // Exchange definida lá no application.yml

    // Metodo para enviar a mensagem para a exchange
    public void notificar(Proposta proposta){
        try {
            notificacaoService.notificar(proposta, propostaExchange);
        } catch (RuntimeException e){
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }

    @Transactional
    public PropostaRespostaDto criar(PropostaDto dto){
        Proposta proposta = propostaMapper.toEntity(dto);
        propostaRepository.save(proposta);
        PropostaRespostaDto respostaDto = propostaMapper.toDto(proposta);

        notificar(proposta); // manda para o exchange esse dto

        return respostaDto;
    }

    public PropostaRespostaDto getProposta(Long id){
        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Proposta não encontrada"));
        return propostaMapper.toDto(proposta);
    }

    public List<PropostaRespostaDto> listarPropostas(){
        return propostaRepository.findAll().stream().map(propostaMapper::toDto).toList();
    }

}
