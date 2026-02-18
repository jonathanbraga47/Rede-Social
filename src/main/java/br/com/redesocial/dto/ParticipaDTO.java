package br.com.redesocial.dto;

public class ParticipaDTO {
    private  Long idPerfil;
    private Long idConversa;

    private String perfilNome;
    private String perfilEmail;

    public ParticipaDTO() {}

    public ParticipaDTO(Long idPerfil, Long idConversa) {
        this.idPerfil = idPerfil;
        this.idConversa = idConversa;
    }

    public Long getIdPerfil() {return idPerfil;}
    public void setIdPerfil(Long idPerfil) {this.idPerfil = idPerfil;}
    public Long getIdConversa() {return idConversa;}
    public void setIdConversa(Long idConversa) {this.idConversa = idConversa;}
    public String getPerfilNome() {return perfilNome;}
    public void setPerfilNome(String perfilNome) {this.perfilNome = perfilNome;}
    public String getPerfilEmail() {return perfilEmail;}
    public void setPerfilEmail(String perfilEmail) {this.perfilEmail = perfilEmail;}
}
