import { useEffect, useState } from "react";
import "./ViewFeed.css";

export default function ViewFeed() {
  const [feed, setFeed] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState(null);

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
        setErro(error.message);
      } finally {
        setLoading(false);
      }
    }

    buscarFeed();
  }, []);

  if (loading) return <p className="status">Carregando feed...</p>;
  if (erro) return <p className="erro">{erro}</p>;

  return (
    <div className="feed-container">
      <h2>Feed</h2>

      {feed.length === 0 && <p>Nenhuma publicação encontrada.</p>}

      <div className="feed-grid">
        {feed.map((item, index) => (
          <div key={index} className="feed-card">
            <h3>{item.nome}</h3>

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
          </div>
        ))}
      </div>
    </div>
  );
}