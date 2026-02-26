package br.com.redesocial.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ParticipaId implements Serializable {
    @Column(name = "id_perfil")
    private Long idPerfil;

    @Column(name = "id_conversa")
    private Long idConversa;


    public ParticipaId() {
    }

    public ParticipaId(Long idPerfil, Long idConversa) {
        this.idPerfil = idPerfil;
        this.idConversa = idConversa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipaId that = (ParticipaId) o;
        return Objects.equals(idPerfil, that.idPerfil) && Objects.equals(idConversa, that.idConversa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerfil, idConversa);
    }

    public Long getIdPerfil() {return idPerfil;}
    public void setIdPerfil(Long idPerfil) {this.idPerfil = idPerfil;}
    public Long getIdConversa() {return idConversa;}
    public void setIdConversa(Long idConversa) {this.idConversa = idConversa;}

}