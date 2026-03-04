import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function Seguindo() {
    const { id } = useParams();
    const [seguindo, setSeguindo] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/segue/seguindo/${id}`)
            .then(res => {
                if (!res.ok) throw new Error("Erro ao buscar perfis seguidos");
                return res.json();
            })
            .then(data => {
                setSeguindo(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setErro("Não foi possível carregar lista.");
                setLoading(false);
            });
    }, [id]);

    const deixarDeSeguir = async (idSeguido) => {
        try {
            const res = await fetch(
                `http://localhost:8080/segue/${id}/${idSeguido}`,
                { method: "DELETE" }
            );

            if (!res.ok) throw new Error("Erro ao deixar de seguir");

            // Atualiza o estado removendo da lista (UX lisinha)
            setSeguindo(prev =>
                prev.filter(perfil => perfil.id !== idSeguido)
            );

        } catch (err) {
            console.error(err);
            alert("Não foi possível deixar de seguir.");
        }
    };

    if (loading) return <h2>Carregando...</h2>;
    if (erro) return <h2>{erro}</h2>;

    return (
        <div className="lista-container">
            <h2>Seguindo</h2>

            {seguindo.length === 0 ? (
                <p>Este perfil ainda não segue ninguém.</p>
            ) : (
                seguindo.map(perfil => (
                    <div key={perfil.id} className="perfil-item">
                        <p>{perfil.seguidoNome}</p>

                        <button
                            onClick={() => deixarDeSeguir(perfil.id)}
                            className="btn-deixar-seguir"
                        >
                            Deixar de seguir
                        </button>
                    </div>
                ))
            )}
        </div>
    );
}