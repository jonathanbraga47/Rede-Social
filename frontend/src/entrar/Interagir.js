import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./Interagir.css";

export default function Interagir() {
  const { id } = useParams();
  const [feed, setFeed] = useState([]);
  const [loading, setLoading] = useState(true);
  const [mensagem, setMensagem] = useState("");

  useEffect(() => {
    async function buscarFeed() {
      try {
        const response = await fetch("http://localhost:8080/viewFeed/feed");

        if (!response.ok) {
          throw new Error("Erro ao buscar feed");
        }

        const data = await response.json();
        setFeed(data);
      } catch (error) {
        setMensagem("Erro ao carregar publicações.");
      } finally {
        setLoading(false);
      }
    }

    buscarFeed();
  }, []);

  function curtir(publicacaoId) {
    fetch("http://localhost:8080/curtida/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        usuarioId: id,
        publicacaoId: publicacaoId
      })
    })
      .then(res => {
        if (!res.ok) throw new Error();
        setMensagem("❤️ Curtida enviada!");
      })
      .catch(() => setMensagem("❌ Erro ao curtir."));
  }

  function comentar(publicacaoId, conteudo) {
    if (!conteudo.trim()) {
      setMensagem("Digite um comentário.");
      return;
    }

    fetch("http://localhost:8080/comentario/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        publicacaoId: publicacaoId,
        usuarioId: id,
        conteudo: conteudo
      })
    })
      .then(res => {
        if (!res.ok) throw new Error();
        setMensagem("💬 Comentário enviado!");
      })
      .catch(() => setMensagem("❌ Erro ao comentar."));
  }

  if (loading) return <p>Carregando publicações...</p>;

  return (
    <div className="interagir-page">
      <div className="interagir-container">
        <h2>Publicações</h2>

        <div className="interagir-feed">
          {feed.map((item, index) => {
            let comentarioTemp = "";

            return (
              <div key={index} className="interagir-card">
                <h3>{item.nomePerfil}</h3>

                <p className="legenda">{item.legenda}</p>

                {item.urlMidia && (
                  <img
                    src={item.urlMidia}
                    alt="midia"
                    className="feed-image"
                  />
                )}

                <div className="feed-info">
                  <span>❤️ {item.totalCurtidas}</span>
                  <span>💬 {item.totalComentarios}</span>
                  <span>📅 {item.dataHora}</span>
                </div>

                <div className="acoes">
                  <button
                    onClick={() => curtir(item.idPublicacao)}
                    className="btn-curtir"
                  >
                    Curtir
                  </button>

                  <textarea
                    placeholder="Escreva um comentário..."
                    onChange={(e) => (comentarioTemp = e.target.value)}
                    className="textarea-comentario"
                  />

                  <button
                    onClick={() => comentar(item.idPublicacao, comentarioTemp)}
                    className="btn-comentar"
                  >
                    Comentar
                  </button>
                </div>
              </div>
            );
          })}
        </div>

        {mensagem && <p className="mensagem">{mensagem}</p>}
      </div>
    </div>
  );
}