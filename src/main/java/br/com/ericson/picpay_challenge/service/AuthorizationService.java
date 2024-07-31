package br.com.ericson.picpay_challenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.ericson.picpay_challenge.dto.AuthorizationRequestDTO;
import br.com.ericson.picpay_challenge.dto.AuthorizationResponseDTO;

@Service
public class AuthorizationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String AUTH_URL = "https://util.devi.tools/api/v2/authorize";

    public AuthorizationResponseDTO checkAuthorization() {
        try {
            AuthorizationRequestDTO response = restTemplate.getForObject(AUTH_URL,
                    AuthorizationRequestDTO.class);
            if (response != null && response.getStatus() != null) {
                Boolean isAuthorized = response.getStatus().equals("success");
                String message = isAuthorized ? "Autorização bem-sucedida" : "Autorização falhou";
                logger.info("Autorização: {}", message);
                return new AuthorizationResponseDTO(isAuthorized, message);
            } else {
                logger.warn("Erro ao validar serviço, tente mais tarde.");
                return new AuthorizationResponseDTO(false, "Erro ao validar serviço, tente mais tarde.");
            }
        } catch (Exception e) {
            logger.error("Erro ao verificar autorização", e);
            return new AuthorizationResponseDTO(false, "Erro ao verificar autorização");
        }
    }
}
