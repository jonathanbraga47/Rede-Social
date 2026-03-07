import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

export default function EditarPerfil() {

    const { id } = useParams();

    const [formData, setFormData] = useState({
        nome: "",
        email: "",
        senha: ""
    });

    const [originalData, setOriginalData] = useState({});
    const [msg, setMsg] = useState("");
    const [msgColor, setMsgColor] = useState("");
    const navigate = useNavigate();

    // Buscar dados ao carregar
    useEffect(() => {
    async function buscarPerfil() {
        try {
            const resposta = await fetch(`http://localhost:8080/perfil/get/${id}`);

            if (!resposta.ok) {
                throw new Error(`Erro ${resposta.status}`);
            }

            const dados = await resposta.json(); // 👈 declara aqui

            setFormData({
                nome: dados.nome || "",
                email: dados.email || "",
                senha: dados.senha || ""
            });

            setOriginalData({
                nome: dados.nome || "",
                email: dados.email || "",
                senha: dados.senha || ""
            });

        } catch (erro) {
            console.error("Erro real:", erro);
            setMsg("Erro ao buscar perfil");
            setMsgColor("red");
        }
    }

    buscarPerfil();
}, [id]);

    console.log("ID recebido:", id);

    const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        //nao permitir enviar sem alteracao
        if (
            formData.nome === originalData.nome &&
            formData.email === originalData.email &&
            formData.senha === originalData.senha
        ) {
            setMsg("Você não alterou nenhuma informação.");
            setMsgColor("orange");
            return;
        }

        try {
            const resposta = await fetch(`http://localhost:8080/perfil/update/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            });

            const texto = await resposta.text();

            if (!resposta.ok) {
                setMsg(texto);
                setMsgColor("red");
            } else {
                setMsg("Perfil atualizado com sucesso!");
                setMsgColor("green");
                setOriginalData(formData);

                setTimeout(() => {
                    navigate("/");
                }, 1000);
            }

        } catch (erro) {
            setMsg("Erro ao conectar com a API");
            setMsgColor("red");
        }
    };

    return (
        <div className="container">
            <div className="card">
                <h2>Editar Perfil</h2>

                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        name="nome"
                        placeholder="Nome"
                        value={formData.nome}
                        onChange={handleChange}
                    />

                    <input
                        type="email"
                        name="email"
                        placeholder="E-mail"
                        value={formData.email}
                        onChange={handleChange}
                    />

                    <input
                        type="password"
                        name="senha"
                        placeholder="Senha"
                        value={formData.senha}
                        onChange={handleChange}
                    />

                    <button type="submit">Salvar Alterações</button>
                </form>

                {msg && <p style={{ color: msgColor }}>{msg}</p>}
            </div>
        </div>
    );
}