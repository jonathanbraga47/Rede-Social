package br.com.redesocial.dto;

public class PerfilDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;

    public String getSenha() {return senha;}
    public String getEmail() {return email;}
    public String getNome() {return nome;}
    public Long getId() {return id;}
}