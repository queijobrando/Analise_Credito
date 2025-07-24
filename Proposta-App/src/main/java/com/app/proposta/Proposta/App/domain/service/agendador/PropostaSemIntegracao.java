package com.app.proposta.Proposta.App.domain.service.agendador;

import com.app.proposta.Proposta.App.domain.model.Proposta;
import com.app.proposta.Proposta.App.domain.service.NotificacaoRabbitService;
import com.app.proposta.Proposta.App.infra.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;
    private final NotificacaoRabbitService notificacaoRabbitService;
    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    @Value("${rabbitmq.propostapendente.exchange}")
    private String propostaExchange; // Exchange definida lá no application.yml

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS) // a cada 10 segundos
    public void buscarPropostaSemintegracao(){
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoRabbitService.notificar(proposta, propostaExchange);
                atualizarProposta(proposta);
            } catch (RuntimeException e){
                logger.error(e.getMessage());
            }
        });
    }

    private void atualizarProposta(Proposta proposta){
        proposta.setIntegrada(true);
        propostaRepository.save(proposta);
    }

}
