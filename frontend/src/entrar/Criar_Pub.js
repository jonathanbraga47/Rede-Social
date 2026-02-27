import { useState } from "react";
import { useParams } from "react-router-dom";

export default function Criar_Pub() {
  const { perfilId } = useParams();

  const [legenda, setLegenda] = useState("");
  const [urlMidia, setUrlMidia] = useState("");
  const [loading, setLoading] = useState(false);

  const [mensagem, setMensagem] = useState("");
  const [tipoMensagem, setTipoMensagem] = useState(""); // "sucesso" ou "erro"

  const criarPublicacao = async (e) => {
    e.preventDefault();

    setMensagem("");
    setTipoMensagem("");

    if (!legenda.trim()) {
      setMensagem("Digite uma legenda.");
      setTipoMensagem("erro");
      return;
    }

    setLoading(true);

    try {
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

      const publicacaoCriada = await responsePub.json();
      const idPublicacao = publicacaoCriada.id;

      if (urlMidia.trim()) {
        const responseMidia = await fetch("http://localhost:8080/arquivo-midia/create", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            idPublicacao: idPublicacao,
            urlMidia: "https://picsum.photos/id/" + urlMidia + "/800/600"
          })
        });

        if (!responseMidia.ok) {
          throw new Error("Erro ao salvar mídia");
        }
      }

      setMensagem("Publicação criada com sucesso!");
      setTipoMensagem("sucesso");

      setLegenda("");
      setUrlMidia("");

    } catch (error) {
      console.error(error);
      setMensagem("Erro ao publicar.");
      setTipoMensagem("erro");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Criar Publicação</h2>

      {/* ✅ Mensagem */}
      {mensagem && (
        <p
          style={{
            color: tipoMensagem === "sucesso" ? "green" : "red",
            fontWeight: "bold"
          }}
        >
          {mensagem}
        </p>
      )}

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