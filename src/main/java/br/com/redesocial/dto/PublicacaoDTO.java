package br.com.redesocial.dto;

import br.com.redesocial.model.Interacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class PublicacaoDTO {

    private Long id;
    private Long perfilId;
    private LocalDateTime dataHora;
    private String legenda;
    private List<Interacao> interacoes;

    public List<Interacao> getInteracoes() {
        return interacoes;
    }

    public void setInteracoes(List<Interacao> interacoes) {
        this.interacoes = interacoes;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getPerfilId() {return perfilId;}

    public void setPerfilId(Long perfilId) {this.perfilId = perfilId;}

    public LocalDateTime getDataHora() {return dataHora;}

    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}

    public String getLegenda() {return legenda;}

    public void setLegenda(String legenda) {this.legenda = legenda;}
}
