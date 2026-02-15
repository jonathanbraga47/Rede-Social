package br.com.redesocial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PublicacaoDTO {

    @NotNull(message = "O Id ")
    private Long perfilId;
    private String legenda;

    public PublicacaoDTO() {

    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

}
