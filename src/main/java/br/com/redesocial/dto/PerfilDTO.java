package br.com.redesocial.dto;

import br.com.redesocial.enums.TipoPerfil;

public class PerfilDTO {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private Integer tipoPrivacidade;

    public PerfilDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getTipoPrivacidade() {
        return tipoPrivacidade;
    }

    public void setTipoPrivacidade(Integer tipoPrivacidade) {
        this.tipoPrivacidade = tipoPrivacidade;
    }
}