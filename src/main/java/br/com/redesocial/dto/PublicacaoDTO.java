package br.com.redesocial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PublicacaoDTO {

    private Long id;
    private Long perfilId;
    private LocalDateTime dataHora;
    private String legenda;







    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getPerfilId() {return perfilId;}

    public void setPerfilId(Long perfilId) {this.perfilId = perfilId;}

    public LocalDateTime getDataHora() {return dataHora;}

    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}

    public String getLegenda() {return legenda;}

    public void setLegenda(String legenda) {this.legenda = legenda;}
}
