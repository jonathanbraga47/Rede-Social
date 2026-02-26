import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./Publicacao.css";

export default function Publicacoes() {
    const { id } = useParams();
    const [publicacoes, setPublicacoes] = useState([]);
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

    if (carregando) return <p>Carregando...</p>;
    if (erro) return <p>{erro}</p>;

    return (
        <div className="container-publicacoes">
            <h2>Publicações</h2>

            <div className="tabela-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Legenda</th>
                            <th>Data</th>
                            <th>Qtd Interações</th>
                            <th>Mídias:</th>
                        </tr>
                    </thead>
                    <tbody>
                        {publicacoes.map((pub) => (
                            <tr key={pub.id}>
                                <td>{pub.id}</td>
                                <td>{pub.legenda}</td>
                                <td>{pub.dataHora}</td>
                                <td>{pub.interacoes ? pub.interacoes.length : 0}</td>
                                <div>
                                
                                {pub.arquivos.map((arquivo) => (
                                    <td>{arquivo.urlMidia}</td>
                                ))}
                            </div>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}