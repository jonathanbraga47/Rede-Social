import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

import "./ListarSegue.css";

export default function Seguidores() {
    const { id } = useParams();
    const [seguidores, setSeguidores] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/segue/seguidores/${id}`)
            .then(res => {
                if (!res.ok) throw new Error("Erro ao buscar seguidores");
                return res.json();
            })
            .then(data => {
                setSeguidores(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setErro("Não foi possível carregar seguidores.");
                setLoading(false);
            });
    }, [id]);
    
const removerSeguidor = async (idSeguidor) => {
    try {
        const response = await fetch(
            `http://localhost:8080/segue/deixar-de-seguir/${idSeguidor}/${id}`,
            {
                method: "DELETE"
            }
        );

        if (!response.ok) {
            throw new Error("Erro ao remover seguidor");
        }

        // remove da lista sem reload
        setSeguidores(prev =>
            prev.filter(seguidor => seguidor.idPerfilSeguidor !== idSeguidor)
        );

    } catch (error) {
        console.error(error);
        alert("Não foi possível remover o seguidor.");
    }
};
    if (loading) return <h2>Carregando seguidores...</h2>;
    if (erro) return <h2>{erro}</h2>;

    return (
        <div className="lista-container">
            <h2>Seguidores</h2>

            {seguidores.length === 0 ? (
                <p>Este perfil ainda não possui seguidores.</p>
            ) : (
                seguidores.map(seguidor => (
                <div key={seguidor.idPerfilSeguidor} className="perfil-item">
                    <p>{seguidor.seguidorNome}</p>

                    <button
                        onClick={() => removerSeguidor(seguidor.idPerfilSeguidor)}
                        className="btn-remover"
                    >
                        Remover
                    </button>
                </div>
            ))
            )}
        </div>
    );
}

