package br.com.redesocial.dto;

import jakarta.validation.constraints.NotNull;

public class ConversaRequestDTO {
    @NotNull(message = "ID do perfil criado é orbiagtório")
    private Long idPerfilCriador;

    @NotNull(message = "ID do perfil participante é obrigatório")
    private Long idPerfilParticipante;

    public ConversaRequestDTO() {}

    public ConversaRequestDTO(Long idPerfilCriador, Long idPerfilParticipante) {
        this.idPerfilCriador = idPerfilCriador;
        this.idPerfilParticipante = idPerfilParticipante;
    }

    public Long getIdPerfilCriador() {return idPerfilCriador;}
    public void setIdPerfilCriador(Long idPerfilCriador) {this.idPerfilCriador = idPerfilCriador;}
    public Long getIdPerfilParticipante() {return idPerfilParticipante;}
    public void setIdPerfilParticipante(Long idPerfilParticipante) {this.idPerfilParticipante = idPerfilParticipante;}
}
