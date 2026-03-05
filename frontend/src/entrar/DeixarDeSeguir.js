const deixarDeSeguir = async (idPerfilSeguido) => {
    try {
        const response = await fetch(
            `http://localhost:8080/segue/deixar-de-seguir/${id}/${idPerfilSeguido}`,
            {
                method: "DELETE"
            }
        );

        if (!response.ok) {
            throw new Error("Erro ao deixar de seguir");
        }

        // remove da lista sem recarregar a página
        setSeguindo(prev =>
            prev.filter(perfil => perfil.idPerfilSeguido !== idPerfilSeguido)
        );

    } catch (error) {
        console.error(error);
        alert("Não foi possível deixar de seguir.");
    }
};