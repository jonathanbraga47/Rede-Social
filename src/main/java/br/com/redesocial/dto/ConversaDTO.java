package br.com.redesocial.dto;

import java.util.List;

public class ConversaDTO {
    private Long idConversa;
    private String tipoConversa;

    private List<ParticipaDTO> participantes;

    public ConversaDTO() {}
    public Long getIdConversa() {return idConversa;}
    public void setIdConversa(Long idConversa) {this.idConversa = idConversa;}
    public String getTipoConversa() {return tipoConversa;}
    public void setTipoConversa(String tipoConversa) {this.tipoConversa = tipoConversa;}
    public List<ParticipaDTO> getParticipantes() {return participantes;}
    public void setParticipantes(List<ParticipaDTO> participantes) {this.participantes = participantes;}
}
