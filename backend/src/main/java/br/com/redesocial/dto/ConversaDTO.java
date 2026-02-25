package br.com.redesocial.dto;

import java.util.List;

public class ConversaDTO {
    private Long idConversa;

    private List<ParticipaDTO> participantes;
    private List<MensagemDTO> mensagens;

    public List<MensagemDTO> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<MensagemDTO> mensagens) {
        this.mensagens = mensagens;
    }

    public ConversaDTO() {}
    public Long getIdConversa() {return idConversa;}
    public void setIdConversa(Long idConversa) {this.idConversa = idConversa;}
    public List<ParticipaDTO> getParticipantes() {return participantes;}
    public void setParticipantes(List<ParticipaDTO> participantes) {this.participantes = participantes;}
}
