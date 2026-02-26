import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";

export default function DeletarPerfil() {

    const { id } = useParams();
    const navigate = useNavigate();

    const [msg, setMsg] = useState("");
    const [msgColor, setMsgColor] = useState("");

    const handleDelete = async () => {

        const confirmar = window.confirm("Tem certeza que deseja deletar esse perfil?");
        if (!confirmar) return;

        try {
            const resposta = await fetch(`http://localhost:8080/perfil/delete/${id}`, {
                method: "DELETE"
            });

            if (!resposta.ok) {
                setMsg("Erro ao deletar perfil.");
                setMsgColor("red");
            } else {
                setMsg("Perfil deletado com sucesso!");
                setMsgColor("green");

                // Redireciona depois de 1.5s
                setTimeout(() => {
                    navigate("/");
                }, 1000);
            }

        } catch (erro) {
            setMsg("Erro ao conectar com a API.");
            setMsgColor("red");
        }
    };

    return (
        <div className="container">
            <div className="card">
                <h2>Deletar Perfil</h2>

                <button onClick={handleDelete} style={{ backgroundColor: "red" }}>
                    Confirmar Deleção
                </button>

                {msg && <p style={{ color: msgColor }}>{msg}</p>}
            </div>
        </div>
    );
}