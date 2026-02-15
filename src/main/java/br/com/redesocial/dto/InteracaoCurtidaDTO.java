package br.com.redesocial.dto;

public class InteracaoCurtidaDTO {

    private Long usuarioId;
    private Long publicacaoId;

        public InteracaoCurtidaDTO(Long usuarioId, Long publicacaoId){
            this.usuarioId = usuarioId;
            this.publicacaoId = publicacaoId;
        }

        public Long getUsuarioId(){
            return usuarioId;
        }

        public void setUsuarioId(Long usuarioId){
            this.usuarioId = usuarioId;
    }

    public Long getPublicacaoId(){
            return publicacaoId;
    }

    public void setPublicacaoId(Long publicacaoId){
            this.publicacaoId = publicacaoId;
    }
}
