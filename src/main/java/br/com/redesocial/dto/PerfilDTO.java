package br.com.redesocial.dto;

import br.com.redesocial.enums.TipoPerfil;

public class PerfilDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Integer tipoPrivacidade;

    public Integer getTipoPrivacidade() {return tipoPrivacidade;}
    public String getSenha() {return senha;}
    public String getEmail() {return email;}
    public String getNome() {return nome;}
    public Long getId() {return id;}
}