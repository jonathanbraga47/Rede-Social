import { useState } from "react";
import { useParams } from "react-router-dom";


export default function Criar_Pub() {
    const { perfilId } = useParams();

    const [legenda, setLegenda] = useState("");
    const [urlMidia, setUrlMidia] = useState("");
    const [loading, setLoading] = useState(false);

    const criarPublicacao = async (e) => {
        e.preventDefault();

    if (!legenda.trim()) {
      alert("Digite uma legenda.");
      return;
    }

    setLoading(true);
   
    try {
      // 1️⃣ Criar Publicação
      const responsePub = await fetch("http://localhost:8080/publicacao/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          perfilId: perfilId,
          legenda: legenda
        })
      });

      if (!responsePub.ok) {
        throw new Error("Erro ao criar publicação");
      }
      console.log("perfilId:", perfilId);

      const publicacaoCriada = await responsePub.json();
      const idPublicacao = publicacaoCriada.id;

      // 2️⃣ Criar ArquivoMidia (se tiver url)
      
      if (urlMidia.trim()) {
        const responseMidia = await fetch("http://localhost:8080/arquivo-midia/create", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            idPublicacao: idPublicacao,
            urlMidia: "https://picsum.photos/id/"+urlMidia+"/800/600"
          })
        });

        if (!responseMidia.ok) {
          throw new Error("Erro ao salvar mídia");
        }
      }

      alert("Publicação criada com mídia 🚀");
      setLegenda("");
      setUrlMidia("");

    } catch (error) {
      console.error(error);
      alert("Erro ao publicar.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Criar Publicação</h2>

      <form onSubmit={criarPublicacao}>

        <textarea
          placeholder="Legenda"
          value={legenda}
          onChange={(e) => setLegenda(e.target.value)}
          style={{ width: "100%", height: "100px" }}
        />

        <br /><br />

        <input
          type="text"
          placeholder="URL da mídia"
          value={urlMidia}
          onChange={(e) => setUrlMidia(e.target.value)}
          style={{ width: "100%" }}
        />

        <br /><br />

        <button type="submit" disabled={loading}>
          {loading ? "Publicando..." : "Publicar"}
        </button>

      </form>
    </div>
  );
}