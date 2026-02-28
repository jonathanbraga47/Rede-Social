import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import "./EntrarPerfil.css";

export default function EntrarPerfil() {
    const { id } = useParams();

    const [perfil, setPerfil] = useState(null);
    const [seguidores, setSeguidores] = useState([]);
    const [seguindo, setSeguindo] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
    async function carregarDados() {
        try {
            setLoading(true);
            setErro(null);

            const [perfilRes, seguidoresRes, seguindoRes] = await Promise.all([
                fetch(`http://localhost:8080/perfil/get/${id}`),
                fetch(`http://localhost:8080/segue/seguidores/${id}`),
                fetch(`http://localhost:8080/segue/seguindo/${id}`)
            ]);

            if (!perfilRes.ok || !seguidoresRes.ok || !seguindoRes.ok) {
                throw new Error("Erro ao buscar dados");
            }

            const perfilData = await perfilRes.json();
            const seguidoresData = await seguidoresRes.json();
            const seguindoData = await seguindoRes.json();

            setPerfil(perfilData);
            setSeguidores(seguidoresData);
            setSeguindo(seguindoData);

        } catch (error) {
            console.error(error);
            setErro("Erro ao carregar perfil.");
        } finally {
            setLoading(false);
        }
    }

    carregarDados();
    }, [id]);

    if (loading) {
        return <h2>Carregando...</h2>;
    }

    if (erro) {
        return <h2>{erro}</h2>;
    }

    if (!perfil) {
        return <h2>Perfil não encontrado.</h2>;
    }

    return (
        <div className="perfil-container">
            <div className="perfil-card">
                <h2>{perfil.nome}</h2>

                {/* Aqui você pode colocar contadores vindos do backend */}
                <div className="perfil-info">
                    <p>Seguidores: {seguidores?.length || 0}</p>
                    <p>Seguindo: {seguindo?.length || 0}</p>
                   
                </div>
            </div>

            <div className="stats">
                <Link to={`/get/${id}/publicacoes`}>
                    Publicações
                </Link>{" "}
                <Link to={`/segue/seguindo/${id}`}>
                    Seguindo
                </Link>{" "}
                <Link to={`/segue/seguidores/${id}`}>
                    Seguidores
                </Link>{" "}
                <Link to={`/conversas/${id}`}>
                    Conversas
                </Link>{" "}
                <Link to={`/publicacao/create/${id}`}>
                    publicar
                </Link>{" "}
                <Link to={`/seguir/${id}`}>
                    Seguir
                </Link>{" "}
                <Link to={`/interagir/${id}`}>
                    Interagir
                </Link>{" "}
            </div>
        </div>
    );
}