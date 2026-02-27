import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./KejomiTable.css"

function KejomiTable() {

  const [perfis, setPerfis] = useState([]);

   useEffect(() => {
    fetch("http://localhost:8080/perfil/getAll")
        .then((res) => res.json())
        .then((data) => {
            console.log("DATA:", data);
            setPerfis(data);
        })
        .catch((err) => console.error(err));
    }, []);
    
    console.log("Perfis:", perfis);

    return (
    <div className="table-card">

        <div className="table-header">
            <h2 className="table-title">Todos os Perfis</h2>
            <Link to="/perfil/create" className="add-button">
                + Cadastrar
            </Link>
            <h2 className="engajamento"></h2>
            <Link to="/viewEngajamento/engajamento" className="add-button">
                Vizualizar Views
            </Link>
        </div>

        <div className="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Ações</th>
                    </tr>
                </thead>

                <tbody>
                    {perfis.map((perfil) => (
                        <tr key={perfil.id}>
                            <td>{perfil.id}</td>
                            <td>{perfil.nome}</td>
                            <td>{perfil.email}</td>
                            <td>
                                <Link to={`/perfil/${perfil.id}`} className="action-button">
                                    Entrar
                                </Link>{" "}
                                <Link to={`/perfil/update/${perfil.id}`} className="action-button">
                                    Editar
                                </Link>{" "}
                                <Link to={`/perfil/delete/${perfil.id}`} className="delete-button">
                                    Excluir
                                </Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>

    </div>
);
    }

    export default KejomiTable;
