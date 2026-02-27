import { useState } from "react";
import {useParams } from "react-router-dom";

export default function Interagir() {
  const { id } = useParams();
  const [idPublicacao, setIdPublicacao] = useState("");
  const [tipoInteracao, setTipoInteracao] = useState("curtir");
  const [conteudoComentario, setConteudoComentario] = useState("");
  const [mensagem, setMensagem] = useState("");

  function Interagir(e) {
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
        publicacaoId: idPublicacao,
        usuarioId: id
      })
    })
    .then(() => setMensagem("Publicação curtida com sucesso."))
    .catch(() => setMensagem("Erro ao curtir."));
  }

  function comentar() {

    if (!conteudoComentario.trim()) {
      setMensagem("Digite um comentário.");
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
    .then(() => {
      setMensagem("Comentário enviado com sucesso.");
      setConteudoComentario("");
    })
    .catch(() => setMensagem("Erro ao comentar."));
  }

  return (
    <div style={styles.container}>
      <h2>Interagir com Publicação</h2>

      <form onSubmit={Interagir} style={styles.form}>

        <input
          type="number"
          placeholder="ID da publicação"
          value={idPublicacao}
          onChange={(e) => setIdPublicacao(e.target.value)}
          style={styles.input}
        />

        <select
          value={tipoInteracao}
          onChange={(e) => setTipoInteracao(e.target.value)}
          style={styles.input}
        >
          <option value="curtir">Curtir</option>
          <option value="comentar">Comentar</option>
        </select>

        {tipoInteracao === "comentar" && (
          <textarea
            placeholder="Digite seu comentário"
            value={conteudoComentario}
            onChange={(e) => setConteudoComentario(e.target.value)}
            style={styles.textarea}
          />
        )}

        <button type="submit" style={styles.button}>
          Enviar Interação
        </button>

      </form>

      {mensagem && <p style={styles.mensagem}>{mensagem}</p>}
    </div>
  );
}

const styles = {
  container: {
    maxWidth: "400px",
    margin: "0 auto",
    padding: "20px"
  },
  form: {
    display: "flex",
    flexDirection: "column",
    gap: "12px"
  },
  input: {
    padding: "8px",
    borderRadius: "8px",
    border: "1px solid #ccc"
  },
  textarea: {
    padding: "8px",
    borderRadius: "8px",
    border: "1px solid #ccc",
    minHeight: "80px"
  },
  button: {
    padding: "10px",
    borderRadius: "8px",
    border: "none",
    cursor: "pointer"
  },
  mensagem: {
    marginTop: "15px"
  }
};