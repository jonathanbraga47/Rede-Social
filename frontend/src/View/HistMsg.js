import { useState } from "react";
import "./ViewHistoricoMensagens.css";

export default function ViewHistoricoMensagens() {
  const [idConversa, setIdConversa] = useState("");
  const [mensagens, setMensagens] = useState([]);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState(null);

  async function buscarHistorico() {
    if (!idConversa) return;

    setLoading(true);
    setErro(null);

    try {
      const response = await fetch(
        `http://localhost:8080/historicoMensagem/historico/${idConversa}`
      );

      if (!response.ok) {
        throw new Error("Erro ao buscar histórico");
      }

      const data = await response.json();
      setMensagens(data);
    } catch (error) {
      setErro(error.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="historico-container">
      <h2>Histórico de Mensagens</h2>

      <div className="busca-box">
        <input
          type="number"
          placeholder="Digite o ID da conversa"
          value={idConversa}
          onChange={(e) => setIdConversa(e.target.value)}
        />
        <button onClick={buscarHistorico}>
          Buscar
        </button>
      </div>

      {loading && <p>Carregando...</p>}
      {erro && <p className="erro">{erro}</p>}

      {mensagens.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>ID Conversa</th>
              <th>Remetente</th>
              <th>Mensagem</th>
              <th>Data</th>
            </tr>
          </thead>
          <tbody>
            {mensagens.map((msg, index) => (
              <tr key={index}>
                <td>{msg.idConversa}</td>
                <td>{msg.nomeRemetente}</td>
                <td>{msg.conteudo}</td>
                <td>{msg.dataHora}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {!loading && mensagens.length === 0 && idConversa && (
        <p>Nenhuma mensagem encontrada.</p>
      )}
    </div>
  );
}