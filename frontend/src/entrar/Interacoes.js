import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function Interacoes() {
    const { id } = useParams();
    const [comentarios, setComentarios] = useState([]);
    const [curtidas, setCurtidas] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        async function carregarInteracoes() {
            try {
                setLoading(true);

                const [resComentarios, resCurtidas] = await Promise.all([
                    fetch(`http://localhost:8080/comentario/usuario/${id}`),
                    fetch(`http://localhost:8080/curtida/usuario/${id}`)
                ]);

                if (!resComentarios.ok || !resCurtidas.ok) {
                    throw new Error("Erro ao carregar interações");
                }

                const dataComentarios = await resComentarios.json();
                const dataCurtidas = await resCurtidas.json();

                setComentarios(dataComentarios);
                setCurtidas(dataCurtidas);

            } catch (err) {
                setErro(err.message);
            } finally {
                setLoading(false);
            }
        }

        carregarInteracoes();
    }, [id]);

    async function removerComentario(idComentario) {
        await fetch(`http://localhost:8080/comentario/delete/${idComentario}`, {
            method: "DELETE"
        });

        setComentarios(prev => prev.filter(c => c.id !== idComentario));
    }

    async function removerCurtida(idCurtida) {
        await fetch(`http://localhost:8080/curtida/delete/${idCurtida}`, {
            method: "DELETE"
        });

        setCurtidas(prev => prev.filter(c => c.id !== idCurtida));
    }

    if (loading) return <p>Carregando...</p>;
    if (erro) return <p>{erro}</p>;
    return (
    <div className="interacoes-page">
        <div className="interacoes-container">
        <h2>Minhas Interações</h2>

        <div className="interacoes-scroll">

            <div className="secao">
            <h3>Comentários</h3>

            {comentarios.length === 0 ? (
                <p className="mensagem-vazia">Nenhum comentário.</p>
            ) : (
                <ul className="lista">
                {comentarios.map((comentario) => (
                    <li key={comentario.id} className="item">
                    <span>{comentario.texto}</span>
                    <button
                        className="btn-remover"
                        onClick={() => removerComentario(comentario.id)}
                    >
                        Remover
                    </button>
                    </li>
                ))}
                </ul>
            )}
            </div>

            <div className="secao">
            <h3>Curtidas</h3>

            {curtidas.length === 0 ? (
                <p className="mensagem-vazia">Nenhuma curtida.</p>
            ) : (
                <ul className="lista">
                {curtidas.map((curtida) => (
                    <li key={curtida.id} className="item">
                    <span>
                        Curtida na publicação {curtida.publicacao?.id}
                    </span>
                    <button
                        className="btn-remover"
                        onClick={() => removerCurtida(curtida.id)}
                    >
                        Remover
                    </button>
                    </li>
                ))}
                </ul>
            )}
            </div>

        </div>
        </div>
    </div>
    );
}