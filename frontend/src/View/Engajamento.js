import { useEffect, useState } from "react";
import "./Engajamento.css"

export default function Engajamento() {

    const [engajamentos, setEngajamentos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/viewEngajamento/engajamento")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro ao buscar dados");
                }
                return response.json();
            })
            .then(data => {
                setEngajamentos(data);
                setLoading(false);
            })
            .catch(error => {
                setErro(error.message);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <p>Carregando engajamento...</p>;
    }

    if (erro) {
        return <p>Erro: {erro}</p>;
    }

    return (
        <div className="engajamento-page">
            <div className="engajamento-container">

                <div className="engajamento-header">
                    <h2 className="engajamento-title">Mais Engajadas</h2>
                </div>

                <div className="engajamento-table-wrapper">
                    <table className="engajamento-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Autor</th>
                                <th>Legenda</th>
                                <th>Data</th>
                                <th>Interações</th>
                                <th>Url Mídia</th>
                            </tr>
                        </thead>
                        <tbody>
                            {engajamentos.map(item => (
                                <tr key={item.idPublicacao}>
                                    <td>{item.idPublicacao}</td>
                                    <td>{item.autor}</td>
                                    <td>{item.legenda}</td>
                                    <td>{new Date(item.dataHora).toLocaleString()}</td>
                                    <td>{item.interacoes}</td>
                                    <td>{item.urlMidia}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    );
}