package br.com.redesocial.dto;

import java.util.List;

public class ConversaDTO {
    private Long idConversa;
    private Long idPerfil;
    private String tipoConversa;

    private String perfilNome;
    private String perfilEmail;
    private List<ParticipaDTO> participantes;

    public ConversaDTO() {}
    public Long getIdConversa() {return idConversa;}
    public void setIdConversa(Long idConversa) {this.idConversa = idConversa;}
    public Long getIdPerfil() {return idPerfil;}
    public void setIdPerfil(Long idPerfil) {this.idPerfil = idPerfil;}
    public String getPerfilNome() {return perfilNome;}
    public void setPerfilNome(String perfilNome) {this.perfilNome = perfilNome;}
    public String getPerfilEmail() {return perfilEmail;}
    public void setPerfilEmail(String perfilEmail) {this.perfilEmail = perfilEmail;}
    public String getTipoConversa() {return tipoConversa;}
    public void setTipoConversa(String tipoConversa) {this.tipoConversa = tipoConversa;}
    public List<ParticipaDTO> getParticipantes() {return participantes;}
    public void setParticipantes(List<ParticipaDTO> participantes) {this.participantes = participantes;}
}
