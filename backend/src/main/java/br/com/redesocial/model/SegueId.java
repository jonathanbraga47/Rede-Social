package br.com.redesocial.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SegueId implements Serializable {
    @Column(name = "id_perfil_seguidor")
    private Long idPerfilSeguidor;

    @Column(name = "id_perfil_seguido")
    private Long idPerfilSeguido;

    public SegueId() {
    }

    public SegueId(Long idPerfilSeguidor, Long idPerfilSeguido) {
        this.idPerfilSeguidor = idPerfilSeguidor;
        this.idPerfilSeguido = idPerfilSeguido;
    }

    //override por ser chave composta
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegueId segueId = (SegueId) o;
        return Objects.equals(idPerfilSeguidor, segueId.idPerfilSeguidor) &&
                Objects.equals(idPerfilSeguido, segueId.idPerfilSeguido);
    }

    @Override
    public int hashCode(){
        return Objects.hash(idPerfilSeguidor, idPerfilSeguido);
    }

    public Long getIdPerfilSeguidor() {return idPerfilSeguidor;}
    public void setIdPerfilSeguidor(Long idPerfilSeguidor) {this.idPerfilSeguidor = idPerfilSeguidor;}
    public Long getIdPerfilSeguido() {return idPerfilSeguido;}
    public void setIdPerfilSeguido(Long idPerfilSeguido) {this.idPerfilSeguido = idPerfilSeguido;}
}
