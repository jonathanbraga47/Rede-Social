import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./Conversa.css";

export default function Conversas() {
    const { idPerfil } = useParams();

    const [conversas, setConversas] = useState([]);
    const [novaMensagem, setNovaMensagem] = useState("");
    const [idConversaSelecionada, setIdConversaSelecionada] = useState(null);
    const [mensagens, setMensagens] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8080/participa/getAll/${idPerfil}`)
            .then(res => res.json())
            .then(data => {
                setConversas(data);
            })
            .catch(err => console.error("Erro ao buscar conversas:", err));
    }, [idPerfil]);
    console.log("idPerfil:", idPerfil);

    function selecionarConversa(id) {
        setIdConversaSelecionada(id);

        fetch(`http://localhost:8080/mensagem/getAll/${id}`)
            .then(res => res.json())
            .then(data => {
                setMensagens(data);
            })
            .catch(err => console.error("Erro ao buscar mensagens:", err));
    }

    function enviarMensagem() {
        if (!novaMensagem || !idConversaSelecionada) return;

        fetch("http://localhost:8080/mensagem/send", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                idConversa: idConversaSelecionada,
                perfil: idPerfil,
                conteudo: novaMensagem
            })
        })
            .then(res => res.json())
            .then(() => {
                setNovaMensagem("");
                alert("Mensagem enviada");
            })
            .catch(err => console.error("Erro ao enviar mensagem:", err));
    }
    return (
        <div className="conversas-container">
            <div className="lista-conversas">
                <h2>Conversas</h2>

                {conversas.length === 0 && <p>Você ainda não tem conversas.</p>}

                {conversas.map(conversa => (
                    <div
                        key={conversa.idConversa}
                        onClick={() => selecionarConversa(conversa.idConversa)}
                        className="conversa-item"
                    >
                        <strong>Conversa #{conversa.idConversa}</strong>

                        Participantes:
                        {conversa.participantes.map(p => (
                            <div key={p.idPerfil}>
                                {p.perfilNome}
                            </div>
                        ))}
                    </div>
                ))}
            </div>

            {idConversaSelecionada && (
                <div className="area-mensagem">

                    <h3>Mensagens</h3>

                    <div className="mensagens-lista">
                    {mensagens.length === 0 && <p>Nenhuma mensagem ainda.</p>}

                    {mensagens.map(msg => (
                        <div key={msg.idMensagem} style={{
                            border: "1px solid #ccc",
                            padding: "8px",
                            marginBottom: "5px"
                        }}>
                            <strong>{msg.perfilNome}</strong>
                            <p>{msg.conteudo}</p>
                            <small>{msg.dataHora}</small>
                        </div>
                    ))}
                    </div>

                    <div className="mensagem-input">
                        <textarea
                            value={novaMensagem}
                            onChange={(e) => setNovaMensagem(e.target.value)}
                            placeholder="Digite sua mensagem..."
                            rows="3"
                        />
                        <button onClick={enviarMensagem}>
                            Enviar
                        </button>
                    </div>
                </div>
            )}
        
        </div>
    );
}