import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import "./ListarSegue.css";
import "./Seguir.css";

export default function SeguirFormulario() {
    const { id } = useParams();
    const [idSeguido, setIdSeguido] = useState("");
    const [mensagem, setMensagem] = useState("");
    const [erro, setErro] = useState("");
    const [perfis, setPerfis] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function carregarPerfis() {
            try {
                // Busca todos os perfis
                const resPerfis = await fetch("http://localhost:8080/perfil");
                const todosPerfis = await resPerfis.json();

                // Busca quem o usuário já segue
                const resSeguindo = await fetch(`http://localhost:8080/segue/seguindo/${id}`);
                const seguindo = await resSeguindo.json();

                const idsSeguindo = seguindo.map(s => s.idSeguido);

                // Filtra:
                const filtrados = todosPerfis.filter(p =>
                    p.id != id && !idsSeguindo.includes(p.id)
                );

                setPerfis(filtrados);
            } catch (err) {
                console.error(err);
                setErro("Erro ao carregar perfis.");
            } finally {
                setLoading(false);
            }
        }

        carregarPerfis();
    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();

        setMensagem("");
        setErro("");

        if (!id || !idSeguido) {
            setErro("Preencha todos os campos.");
            return;
        }

        fetch(`http://localhost:8080/segue/seguir/${id}/${idSeguido}`, {
            method: "POST"
        })
        .then(res => {
            if (!res.ok) {
                throw new Error("Erro ao seguir perfil.");
            }
            return res.json();
        })
        .then(() => {
            setMensagem("Perfil seguido com sucesso!");
            setIdSeguido("");

            // Remove da lista após seguir
            setPerfis(perfis.filter(p => p.id != idSeguido));
        })
        .catch(err => {
            console.error(err);
            setErro("Não foi possível seguir o perfil.");
        });
    };

    return (
        <div className="form-container">
            <h2>Seguir Perfil</h2>

            <form onSubmit={handleSubmit}>
                <div>
                    <label>ID do perfil que deseja seguir:</label>
                    <input
                        type="number"
                        value={idSeguido}
                        onChange={(e) => setIdSeguido(e.target.value)}
                        placeholder="Digite o ID do perfil"
                    />
                </div>

                <button type="submit">Seguir</button>
            </form>

            {mensagem && <p className="mensagem-sucesso">{mensagem}</p>}
            {erro && <p className="mensagem-erro">{erro}</p>}

            <h3>Perfis disponíveis para seguir</h3>

            {loading ? (
                <p>Carregando...</p>
            ) : (
                <div className="lista-perfis-scroll">
                    {perfis.map(perfil => (
                        <div key={perfil.id} className="card-perfil">
                            <p><strong>ID:</strong> {perfil.id}</p>
                            <p><strong>Nome:</strong> {perfil.nome}</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}