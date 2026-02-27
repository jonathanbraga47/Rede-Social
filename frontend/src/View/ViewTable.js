import { useState } from "react";
import { Link } from "react-router-dom";
import "./ViewTable.css";

export default function ViewsTable() {
  const [idConversa, setIdConversa] = useState("");

  return (
    <div className="views-container">
      <h2>Visualização das Views</h2>

      <table className="views-table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Descrição</th>
            <th>Ação</th>
          </tr>
        </thead>
        <tbody>

          {/* VIEW FEED */}
          <tr>
            <td>View Feed</td>
            <td>Lista todas as publicações do feed</td>
            <td>
              <Link 
                to="/viewFeed/feed"
                className="btn-view"
              >
                Feed
              </Link>
            </td>
          </tr>

          {/* VIEW ENGAJAMENTO */}
          <tr>
            <td>View Engajamento</td>
            <td>Mostra dados de engajamento das publicações</td>
            <td>
              <Link 
                to="/viewEngajamento/engajamento"
                className="btn-view"
              >
                Engajamento
              </Link>
            </td>
          </tr>

          {/* VIEW HISTÓRICO */}
          <tr>
            <td>Histórico de Mensagens</td>
            <td>Busca histórico por ID da conversa</td>
            <td>
              <div className="historico-box">
                <input
                  type="number"
                  placeholder="ID"
                  value={idConversa}
                  onChange={(e) => setIdConversa(e.target.value)}
                />

                {idConversa && (
                  <Link
                    to={`/historicoMensagem/historico/${idConversa}`}
                    className="btn-view"
                  >
                    Abrir
                  </Link>
                )}
              </div>
            </td>
          </tr>

        </tbody>
      </table>
    </div>
  );
}