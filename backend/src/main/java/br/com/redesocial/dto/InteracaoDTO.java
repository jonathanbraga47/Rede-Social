package br.com.redesocial.dto;

import java.time.LocalDateTime;

public class InteracaoDTO {

    private Long id;
    private String tipo;
    private LocalDateTime dataHora;
    private Long perfilId;

    public InteracaoDTO() {}

    public InteracaoDTO(Long id, String tipo, LocalDateTime dataHora, Long perfilId) {
        this.id = id;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.perfilId = perfilId;
    }

    public Long getId() { return id; }
    public String getTipo() { return tipo; }
    public LocalDateTime getDataHora() { return dataHora; }
    public Long getPerfilId() { return perfilId; }
}

