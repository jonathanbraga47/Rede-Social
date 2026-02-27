import { useState } from "react";
import { useParams } from "react-router-dom";

import "./ListarSegue.css";

export default function SeguirFormulario() {
    const { id } = useParams();
    const [idSeguido, setIdSeguido] = useState("");
    const [mensagem, setMensagem] = useState("");
    const [erro, setErro] = useState("");

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

            {mensagem && <p style={{color: "white"}}>{mensagem}</p>}
            {erro && <p style={{color: "red"}}>{erro}</p>}
        </div>
    );
}