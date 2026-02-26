package br.com.redesocial.dto;

public class ParticipaDTO {
    private  Long idPerfil;

    private String perfilNome;
    private String perfilEmail;

    public ParticipaDTO() {}

    public ParticipaDTO(Long idPerfil, Long idConversa) {
        this.idPerfil = idPerfil;
    }

    public Long getIdPerfil() {return idPerfil;}
    public void setIdPerfil(Long idPerfil) {this.idPerfil = idPerfil;}
    public String getPerfilNome() {return perfilNome;}
    public void setPerfilNome(String perfilNome) {this.perfilNome = perfilNome;}
    public String getPerfilEmail() {return perfilEmail;}
    public void setPerfilEmail(String perfilEmail) {this.perfilEmail = perfilEmail;}
}
