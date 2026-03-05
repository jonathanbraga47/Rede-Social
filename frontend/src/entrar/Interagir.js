import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./Interagir.css";

export default function Interagir() {
  const { id } = useParams();

  const [feed, setFeed] = useState([]);
  const [loading, setLoading] = useState(true);
  const [mensagem, setMensagem] = useState("");

  const [idSelecionado, setIdSelecionado] = useState("");
  const [comentario, setComentario] = useState("");

  useEffect(() => {
    async function buscarFeed() {
      try {
        const response = await fetch("http://localhost:8080/viewFeed/feed");

        if (!response.ok) throw new Error();

        const data = await response.json();
        setFeed(data);
      } catch {
        setMensagem("Erro ao carregar publicações.");
      } finally {
        setLoading(false);
      }
    }

    buscarFeed();
  }, []);

  function curtir() {
    if (!idSelecionado) {
      setMensagem("Digite o ID da publicação.");
      return;
    }

    fetch("http://localhost:8080/curtida/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        usuarioId: id,
        publicacaoId: idSelecionado
      })
    })
      .then(res => {
        if (!res.ok) throw new Error();
        setMensagem("❤️ Curtida enviada!");
      })
      .catch(() => setMensagem("❌ Erro ao curtir."));
  }

  function comentar() {
    if (!idSelecionado) {
      setMensagem("Digite o ID da publicação.");
      return;
    }

    if (!comentario.trim()) {
      setMensagem("Digite um comentário.");
      return;
    }

    fetch("http://localhost:8080/comentario/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        usuarioId: id,
        publicacaoId: idSelecionado,
        conteudo: comentario
      })
    })
      .then(res => {
        if (!res.ok) throw new Error();
        setMensagem("💬 Comentário enviado!");
        setComentario("");
      })
      .catch(() => setMensagem("❌ Erro ao comentar."));
  }

  if (loading) return <p>Carregando publicações...</p>;

  return (
    <div className="interagir-page">
      <div className="interagir-container">
        <h2>Publicações</h2>

        {/* Scroll com tabela */}
        <div className="interagir-feed">
          <table className="tabela-feed">
            <thead>
              <tr>
                <th>ID</th>
                <th>Perfil</th>
                <th>Legenda</th>
                <th>Curtidas</th>
                <th>Comentários</th>
                <th>Data</th>
              </tr>
            </thead>
            <tbody>
              {feed.map((item) => (
                <tr key={item.idPublicacao}>
                  <td>{item.idPublicacao}</td>
                  <td>{item.nomePerfil}</td>
                  <td>{item.legenda}</td>
                  <td>{item.totalCurtidas}</td>
                  <td>{item.totalComentarios}</td>
                  <td>{item.dataHora}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Área de interação (embaixo do scroll) */}
        <div className="acoes">
          <input
            type="number"
            placeholder="Digite o ID da publicação"
            value={idSelecionado}
            onChange={(e) => setIdSelecionado(e.target.value)}
            className="textarea-comentario"
          />

          <textarea
            placeholder="Escreva um comentário..."
            value={comentario}
            onChange={(e) => setComentario(e.target.value)}
            className="textarea-comentario"
          />

          <button onClick={curtir} className="btn-curtir">
            Curtir
          </button>

          <button onClick={comentar} className="btn-comentar">
            Comentar
          </button>
        </div>

        {mensagem && <p className="mensagem">{mensagem}</p>}
      </div>
    </div>
  );
}