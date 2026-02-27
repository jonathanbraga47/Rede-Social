import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./ViewHistMsg.css";

export default function ViewHistoricoMensagens() {
  const { id } = useParams(); // pega o id da URL
  const [mensagens, setMensagens] = useState([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState(null);

  useEffect(() => {
    async function buscarHistorico() {
      if (!id) return;

      setLoading(true);
      setErro(null);

      try {
        const response = await fetch(
          `http://localhost:8080/historicoMensagem/historico/${id}`
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

    buscarHistorico();
  }, [id]);

  return (
    <div className="historico-container">
      <h2>Histórico de Mensagens</h2>

      {loading && <p>Carregando...</p>}
      {erro && <p className="erro">{erro}</p>}

      {!loading && mensagens.length === 0 && (
        <p>Nenhuma mensagem encontrada.</p>
      )}

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
            {mensagens.map((msg) => (
              <tr key={msg.id}>
                <td>{msg.idConversa}</td>
                <td>{msg.nomeRemetente}</td>
                <td>{msg.conteudo}</td>
                <td>{msg.dataHora}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}