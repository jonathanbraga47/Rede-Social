import { useState } from "react";
import { useNavigate } from "react-router-dom"
import "./CriarPerfil.css";

export default function CriarPerfil() {
    
    const [formData, setFormData] = useState({
        email: "",
        nome: "",
        senha: "",
    });

    const [msg, setMsg] = useState("");
    const [msgColor, setMsgColor] = useState("");
    const navigate = useNavigate();
    const handleChange = (e) => {
        const { name, value } = e.target;

        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const resposta = await fetch("http://localhost:8080/perfil/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            });

            const resultado = await resposta.json();

            if (!resposta.ok) {
                setMsg("Erro ao salvar");
                setMsgColor("red");
            } else {
                setMsg("Perfil criado com sucesso!");
                setMsgColor("green");

                setFormData({
                    email: "",
                    nome: "",
                    senha: "",
                });
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
            <h2>Criar Perfil</h2>

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
                    placeholder="Email"
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

                <button type="submit">Salvar</button>
            </form>

            {msg && <p style={{ color: msgColor }}>{msg}</p>}
        </div>
    );
}
