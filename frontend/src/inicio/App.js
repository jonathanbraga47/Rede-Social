import {BrowserRouter, Routes, Route} from 'react-router-dom';
import './App.css';
import KejomiTable from './KejomiTable';
import CriarPerfil from '../perfil/CriarPerfil';
import EditarPerfil from '../perfil/EditarPerfil';
import EntrarPerfil from '../perfil/EntrarPerfil';
import DeletarPerfil from '../perfil/DeletarPerfil';
import Publicacoes from '../entrar/Publicacao';
import Conversas from '../entrar/Conversa';
import Seguindo from '../entrar/Seguindo';
import Seguidores from '../entrar/Seguidores';
import SeguirFormulario from '../entrar/Seguir';
import Engajamento from '../View/Engajamento';
import Criar_Pub from '../entrar/Criar_Pub';
import Interagir from '../entrar/Interagir';
import ViewsTable from '../View/ViewTable';
import ViewHistoricoMensagens from '../View/ViewHistMsg';
import ViewFeed from '../View/ViewFeed';
import Relatorios from './Relatorios';
function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<KejomiTable/>} />
          <Route path="/perfil/create" element={<CriarPerfil/>} />
          <Route path="/viewEngajamento/engajamento" element={<Engajamento />} />
          <Route path="/perfil/update/:id" element={<EditarPerfil/>} />
          <Route path="/perfil/delete/:id" element={<DeletarPerfil/>} />
          <Route path="/perfil/:id" element={<EntrarPerfil />} />
          <Route path="/get/:id/publicacoes" element={<Publicacoes />} />
          <Route path="/segue/seguidores/:id" element={<Seguidores />} />
          <Route path="/segue/seguindo/:id" element={<Seguindo/>} />
          <Route path="/conversas/:idPerfil" element={<Conversas />} />
          <Route path="/seguir/:id" element={<SeguirFormulario />} />
          <Route path="/publicacao/create/:perfilId" element={<Criar_Pub />} />
          <Route path="/interagir/:id" element={<Interagir/>} />
          <Route path="/views" element={<ViewsTable/>} />
          <Route path="/viewFeed/feed" element={<ViewFeed/>} />
          <Route path="/historicoMensagem/historico/:id" element={<ViewHistoricoMensagens />} />
          <Route path="/relatorios" element={<Relatorios />} />  {/* NOVA ROTA */}
        </Routes>
      </BrowserRouter>
  );
}

export default App;