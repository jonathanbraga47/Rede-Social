package br.com.redesocial.model;

import jakarta.persistence.*;

@Entity
@Table(name = "interacao_curtida")
@PrimaryKeyJoinColumn(name = "id_interacao")
public class InteracaoCurtida extends Interacao{
}
