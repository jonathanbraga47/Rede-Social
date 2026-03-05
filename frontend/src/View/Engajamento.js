import { useEffect, useState } from "react";
import "./ViewFeed.css"

export default function Engajamento() {

    const [engajamentos, setEngajamentos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/viewEngajamento/engajamento")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro ao buscar dados");
                }
                return response.json();
            })
            .then(data => {
                setEngajamentos(data);
                setLoading(false);
            })
            .catch(error => {
                setErro(error.message);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <p>Carregando engajamento...</p>;
    }

    if (erro) {
        return <p>Erro: {erro}</p>;
    }

    return (
  <div className="feed-container">
    <h2>Mais Engajadas</h2>

    {engajamentos.length === 0 && (
      <p>Nenhuma publicação encontrada.</p>
    )}

    <div className="feed-grid">
      {engajamentos.map((item, index) => (
        <div key={index} className="feed-card">
          <h3>{item.autor}</h3>

          <p className="legenda">{item.legenda}</p>

          {item.urlMidia && (
            <img
              src={item.urlMidia}
              alt="midia"
              className="feed-image"
            />
          )}

          <div className="feed-info">
            <span> 🎯 {item.totalInteracoes}</span>
            <span>📅 {new Date(item.dataHora).toLocaleString("pt-BR")}</span>
          </div>
        </div>
      ))}
    </div>
  </div>
);
}