package br.com.redesocial.dto;

import java.time.LocalDateTime;

public class MensagemDTO {
    private Long idMensagem;
    private Long idConversa;
    private Long idPerfil;
    private LocalDateTime dataHora;
    private String conteudo;

    private String perfilNome;

    public MensagemDTO() {}

    public Long getIdMensagem() { return idMensagem; }
    public void setIdMensagem(Long idMensagem) { this.idMensagem = idMensagem; }
    public Long getIdConversa() { return idConversa; }
    public void setIdConversa(Long idConversa) { this.idConversa = idConversa; }
    public Long getIdPerfil() { return idPerfil; }
    public void setIdPerfil(Long idPerfil) { this.idPerfil = idPerfil; }
    public String getPerfilNome() { return perfilNome; }
    public void setPerfilNome(String perfilNome) { this.perfilNome = perfilNome; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

}
