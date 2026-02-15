package br.com.redesocial.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interacao_comentario")
public class InteracaoComentario extends Interacao{

    @Column(nullable = false)
    private String texto;




    //get e set
    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
}
