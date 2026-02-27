import { useEffect, useState } from "react";
import "./ViewsTable.css";

export default function ViewsTable() {
  const [feed, setFeed] = useState([]);
  const [engajamento, setEngajamento] = useState([]);
  const [idConversa, setIdConversa] = useState("");
  const [historico, setHistorico] = useState([]);

  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState(null);

  const baseUrl = "http://localhost:8080";

  useEffect(() => {
    async function carregarViews() {
      try {
        setLoading(true);
        setErro(null);

        const [feedRes, engajamentoRes] = await Promise.all([
          fetch(`${baseUrl}/viewFeed/feed`),
          fetch(`${baseUrl}/viewEngajamento/engajamento`)
        ]);

        if (!feedRes.ok || !engajamentoRes.ok) {
          throw new Error("Erro ao buscar views");
        }

        const feedData = await feedRes.json();
        const engajamentoData = await engajamentoRes.json();

        setFeed(feedData);
        setEngajamento(engajamentoData);

      } catch (error) {
        console.error(error);
        setErro("Erro ao carregar as views.");
      } finally {
        setLoading(false);
      }
    }

    carregarViews();
  }, []);

  async function buscarHistorico() {
    try {
      if (!idConversa) return;

      setLoading(true);
      setErro(null);

      const res = await fetch(
        `${baseUrl}/historicoMensagem/historico/${idConversa}`
      );

      if (!res.ok) {
        throw new Error("Erro ao buscar histórico");
      }

      const data = await res.json();
      setHistorico(data);

    } catch (error) {
      console.error(error);
      setErro("Erro ao carregar histórico.");
    } finally {
      setLoading(false);
    }
  }

  if (loading) {
    return <h2>Carregando...</h2>;
  }

  if (erro) {
    return <h2>{erro}</h2>;
  }

  return (
    <div className="views-container">
      <h2>Visualização das Views</h2>

      <table className="views-table">
        <thead>
          <tr>
            <th>View</th>
            <th>Quantidade de Registros</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>View Feed</td>
            <td>{feed?.length || 0}</td>
          </tr>

          <tr>
            <td>View Engajamento</td>
            <td>{engajamento?.length || 0}</td>
          </tr>

          <tr>
            <td>Histórico de Mensagens</td>
            <td>
              <div className="historico-box">
                <input
                  type="number"
                  placeholder="ID da conversa"
                  value={idConversa}
                  onChange={(e) => setIdConversa(e.target.value)}
                />
                <button
                  onClick={buscarHistorico}
                  disabled={!idConversa}
                  className="btn-view"
                >
                  Buscar
                </button>
                {historico.length > 0 && (
                  <p>{historico.length} mensagens</p>
                )}
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}