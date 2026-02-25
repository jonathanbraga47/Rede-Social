package br.com.redesocial.dto;

public class SegueDTO {
    private Long idPerfilSeguidor;
    private Long idPerfilSeguido;

    private String seguidorNome;
    private String seguidorEmail;
    private String seguidoNome;
    private String seguidoEmail;

    public Long getIdPerfilSeguidor() {return idPerfilSeguidor;}
    public void setIdPerfilSeguidor(Long idPerfilSeguidor) {this.idPerfilSeguidor = idPerfilSeguidor;}
    public Long getIdPerfilSeguido() {return idPerfilSeguido;}
    public void setIdPerfilSeguido(Long idPerfilSeguido) {this.idPerfilSeguido = idPerfilSeguido;}
    public String getSeguidorNome() {return seguidorNome;}
    public void setSeguidorNome(String seguidorNome) {this.seguidorNome = seguidorNome;}
    public String getSeguidorEmail() {return seguidorEmail;}
    public void setSeguidorEmail(String seguidorEmail) {this.seguidorEmail = seguidorEmail;}
    public String getSeguidoNome() {return seguidoNome;}
    public void setSeguidoNome(String seguidoNome) {this.seguidoNome = seguidoNome;}
    public String getSeguidoEmail() {return seguidoEmail;}
    public void setSeguidoEmail(String seguidoEmail) {this.seguidoEmail = seguidoEmail;}
}
