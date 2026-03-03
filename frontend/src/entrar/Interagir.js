import { useState } from "react";
import { useParams } from "react-router-dom";
import "./Interagir.css";

export default function Interagir() {
  const { id } = useParams();
  const [idPublicacao, setIdPublicacao] = useState("");
  const [tipoInteracao, setTipoInteracao] = useState("curtir");
  const [conteudoComentario, setConteudoComentario] = useState("");
  const [mensagem, setMensagem] = useState("");

  function handleSubmit(e) {
    e.preventDefault();

    if (!idPublicacao.trim()) {
      setMensagem("Digite o ID da publicação.");
      return;
    }

    if (tipoInteracao === "curtir") {
      curtir();
    } else {
      comentar();
    }
  }

  
  function curtir() {
    fetch("http://localhost:8080/curtida/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        usuarioId: id,
        publicacaoId: idPublicacao
      })
    })
    .then(res => {
      if (!res.ok) {
        throw new Error("Erro no servidor");
      }
      return res.text();
    })
    .then(() => setMensagem("❤️ Publicação curtida com sucesso!"))
    .catch(err => {
      console.error(err);
      setMensagem("❌ Erro ao curtir.");
    });
  }

  function comentar() {
    if (!conteudoComentario.trim()) {
      setMensagem("Digite um comentário!");
      return;
    }

    fetch("http://localhost:8080/comentario/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        publicacaoId: idPublicacao,
        usuarioId: id,
        conteudo: conteudoComentario
      })
    })
      .then(res => {
      if (!res.ok) {
        throw new Error("Erro no servidor");
      }
      return res.text();
    })
    .then(() => setMensagem("💬Comentário enviado com sucesso!"))
    .catch(err => {
      console.error(err);
      setMensagem("❌ Erro ao comentar.");
    });
      
  }

  return (
    <div className="interagir-page">
      <div className="interagir-card">
        <h2>Interagir com Publicação</h2>

        <form onSubmit={handleSubmit} className="interagir-form">
          <input
            type="number"
            placeholder="ID da publicação"
            value={idPublicacao}
            onChange={(e) => setIdPublicacao(e.target.value)}
            className="interagir-input"
          />

          <select
            value={tipoInteracao}
            onChange={(e) => setTipoInteracao(e.target.value)}
            className="interagir-select"
          >
            <option value="curtir">Curtir</option>
            <option value="comentar">Comentar</option>
          </select>

          {tipoInteracao === "comentar" && (
            <textarea
              placeholder="Digite seu comentário"
              value={conteudoComentario}
              onChange={(e) => setConteudoComentario(e.target.value)}
              className="interagir-textarea"
            />
          )}

          <button type="submit" className="interagir-button">
            Enviar Interação
          </button>
        </form>

        {mensagem && (
          <p className="interagir-mensagem">{mensagem}</p>
        )}
      </div>
    </div>
  );
}