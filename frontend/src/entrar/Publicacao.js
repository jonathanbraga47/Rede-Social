import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./Publicacao.css";

export default function Publicacoes() {
    const { id } = useParams();

    const [publicacoes, setPublicacoes] = useState([]);
    const [selecionadas, setSelecionadas] = useState([]);
    const [editandoId, setEditandoId] = useState(null);
    const [novaLegenda, setNovaLegenda] = useState("");

    const [erro, setErro] = useState(null);
    const [carregando, setCarregando] = useState(true);

    useEffect(() => {
        async function carregarPublicacoes() {
            try {
                const resposta = await fetch(`http://localhost:8080/perfil/get/${id}/publicacoes`);

                if (!resposta.ok) {
                    throw new Error("Erro ao buscar publicações");
                }

                const dados = await resposta.json();
                setPublicacoes(dados);
            } catch (error) {
                setErro(error.message);
            } finally {
                setCarregando(false);
            }
        }

        carregarPublicacoes();
    }, [id]);

    // ===============================
    // Selecionar / Desselecionar
    // ===============================
    function toggleSelecionada(pubId) {
        if (selecionadas.includes(pubId)) {
            setSelecionadas(selecionadas.filter(id => id !== pubId));
        } else {
            setSelecionadas([...selecionadas, pubId]);
        }
    }

    // ===============================
    // DELETAR
    // ===============================
    async function deletarPublicacoes() {
        if (selecionadas.length === 0) {
            alert("Selecione pelo menos uma publicação.");
            return;
        }

        if (!window.confirm("Tem certeza que deseja deletar?")) return;

        try {
            for (let pubId of selecionadas) {
                const resposta = await fetch(`http://localhost:8080/publicacao/delete/${pubId}`, {
                    method: "DELETE"
                });

                if (!resposta.ok) {
                    throw new Error(`Erro ao deletar publicação ${pubId}`);
                }
            }

            // Atualiza tela
            setPublicacoes(publicacoes.filter(pub => !selecionadas.includes(pub.id)));
            setSelecionadas([]);

        } catch (error) {
            alert(error.message);
        }
    }

    // ===============================
    // EDITAR
    // ===============================
    function iniciarEdicao(pub) {
        setEditandoId(pub.id);
        setNovaLegenda(pub.legenda);
    }

    async function salvarEdicao(pubId) {
        try {
            const resposta = await fetch(`http://localhost:8080/publicacao/update/${pubId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    legenda: novaLegenda
                })
            });

            if (!resposta.ok) {
                throw new Error("Erro ao atualizar publicação");
            }

            // Atualiza no estado
            setPublicacoes(publicacoes.map(pub =>
                pub.id === pubId ? { ...pub, legenda: novaLegenda } : pub
            ));

            setEditandoId(null);
            setNovaLegenda("");

        } catch (error) {
            alert(error.message);
        }
    }

    if (carregando) return <p>Carregando...</p>;
    if (erro) return <p>{erro}</p>;

    return (
        <div className="container-publicacoes">
            <h2>Publicações</h2>

            <button
                onClick={deletarPublicacoes}
                disabled={selecionadas.length === 0}
                style={{ marginBottom: "10px" }}
            >
                Deletar Selecionadas
            </button>

            <div className="tabela-container">
                <table>
                    <thead>
                        <tr>
                            <th></th>
                            <th>ID</th>
                            <th>Legenda</th>
                            <th>Data</th>
                            <th>Qtd Interações</th>
                            <th>Mídias</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {publicacoes.map((pub) => (
                            <tr key={pub.id}>
                                <td>
                                    <input
                                        type="checkbox"
                                        checked={selecionadas.includes(pub.id)}
                                        onChange={() => toggleSelecionada(pub.id)}
                                    />
                                    <strong> SELECIONAR</strong>
                                </td>

                                <td><strong>ID:</strong> {pub.id}</td>

                                <td>
                                    <strong>LEGENDA:</strong> 
                                    {editandoId === pub.id ? (
                                        <input
                                            type="text"
                                            value={novaLegenda}
                                            onChange={(e) => setNovaLegenda(e.target.value)}
                                            className="input-edicao"
                                        />
                                    ) : (
                                        pub.legenda
                                    )}
                                </td>

                                <td><strong>DATA:</strong> {new Date(pub.dataHora).toLocaleDateString()}</td>
                                <td><strong>INTERAÇÕES:</strong> {pub.interacoes ? pub.interacoes.length : 0}</td>

                                <td>
                                    <strong>MÍDIAS:</strong>
                                    <div className="midia-container">
                                        {pub.arquivos && pub.arquivos.map((arquivo, index) => (
                                            <img 
                                                key={index} 
                                                src={arquivo.urlMidia} 
                                                alt="Preview" 
                                                className="img-publicacao"
                                                onError={(e) => e.target.style.display = 'none'} // Esconde se a URL for inválida
                                            />
                                        ))}
                                    </div>
                                </td>

                                <td className="acoes-card">
                                    {editandoId === pub.id ? (
                                        <>
                                            <button onClick={() => salvarEdicao(pub.id)} className="btn-salvar">Salvar</button>
                                            <button onClick={() => setEditandoId(null)} className="btn-cancelar">Cancelar</button>
                                        </>
                                    ) : (
                                        <button onClick={() => iniciarEdicao(pub)} className="btn-editar">Editar</button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}