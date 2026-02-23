CREATE TABLE IF NOT EXISTS perfil (
                                      id_perfil INT AUTO_INCREMENT PRIMARY KEY,
                                      email VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(20) NOT NULL,
    senha VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS segue (
                                     user_seguidor INT,
                                     user_seguido INT,
                                     PRIMARY KEY (user_seguidor, user_seguido),
    FOREIGN KEY (user_seguidor) REFERENCES perfil(id_perfil) ON DELETE CASCADE,
    FOREIGN KEY (user_seguido) REFERENCES perfil(id_perfil) ON DELETE CASCADE,
    CONSTRAINT chk_nao_seguir_si_mesmo CHECK (user_seguidor <> user_seguido)
    );

CREATE TABLE IF NOT EXISTS conversa (
                                        id_conversa INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS participa (
                                         id_perfil INT,
                                         id_conversa INT,
                                         PRIMARY KEY (id_perfil, id_conversa),
    FOREIGN KEY (id_perfil) REFERENCES perfil(id_perfil) ON DELETE CASCADE,
    FOREIGN KEY (id_conversa) REFERENCES conversa(id_conversa) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS mensagem (
                                        id_mensagem INT AUTO_INCREMENT PRIMARY KEY,
                                        id_conversa INT NOT NULL,
                                        id_perfil INT NOT NULL,
                                        data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                        conteudo TEXT,
                                        FOREIGN KEY (id_conversa) REFERENCES conversa(id_conversa) ON DELETE CASCADE,
    FOREIGN KEY (id_perfil) REFERENCES perfil(id_perfil) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS publicacao (
                                          id_publicacao INT AUTO_INCREMENT PRIMARY KEY,
                                          id_perfil INT NOT NULL,
                                          data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                          legenda TEXT,
                                          FOREIGN KEY (id_perfil) REFERENCES perfil(id_perfil) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS interacao (
                                         id_interacao INT AUTO_INCREMENT PRIMARY KEY,
                                         id_perfil INT NOT NULL,
                                         id_publicacao INT NOT NULL,
                                         data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (id_perfil) REFERENCES perfil(id_perfil) ON DELETE CASCADE,
    FOREIGN KEY (id_publicacao) REFERENCES publicacao(id_publicacao) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS interacao_comentario (
                                                    id_interacao INT NOT NULL PRIMARY KEY,
                                                    texto TEXT,
                                                    FOREIGN KEY (id_interacao) REFERENCES interacao(id_interacao) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS interacao_curtida (
                                                 id_interacao INT NOT NULL PRIMARY KEY,
                                                 FOREIGN KEY (id_interacao) REFERENCES interacao(id_interacao) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS arquivo_midia (
                                             id_arquivo INT AUTO_INCREMENT PRIMARY KEY,
                                             id_publicacao INT NOT NULL,
                                             url_midia VARCHAR(255),
    FOREIGN KEY (id_publicacao) REFERENCES publicacao(id_publicacao) ON DELETE CASCADE
    );

CREATE OR REPLACE VIEW view_feed AS
SELECT
    pe.Nome,
    pu.Legenda,
    pu.Data_Hora,
    am.Url_Midia,
    (SELECT COUNT(*) FROM INTERACAO_CURTIDA ic
                              JOIN INTERACAO i ON ic.ID_Interacao = i.ID_Interacao
     WHERE i.ID_Publicacao = pu.ID_Publicacao) AS Total_Curtidas,
    (SELECT COUNT(*) FROM INTERACAO_COMENTARIO ico
                              JOIN INTERACAO i ON ico.ID_Interacao = i.ID_Interacao
     WHERE i.ID_Publicacao = pu.ID_Publicacao) AS Total_Comentarios
FROM PUBLICACAO pu
         JOIN PERFIL pe ON pu.ID_Perfil = pe.ID_Perfil
         LEFT JOIN ARQUIVO_MIDIA am ON pu.ID_Publicacao = am.ID_Publicacao;

--
CREATE OR REPLACE VIEW view_engajamento AS
SELECT
    pu.Id_Publicacao,
    pe.Nome AS Autor,
    am.Url_Midia,
    pu.Legenda,
    pu.Data_Hora,
    (SELECT COUNT(*) FROM INTERACAO i WHERE i.ID_Publicacao = pu.ID_Publicacao) AS Total_Interacoes
FROM PUBLICACAO pu
         JOIN PERFIL pe ON pu.ID_Perfil = pe.ID_Perfil
         LEFT JOIN ARQUIVO_MIDIA am ON pu.ID_Publicacao = am.ID_Publicacao
WHERE (
          SELECT COUNT(*)
          FROM INTERACAO i
          WHERE i.ID_Publicacao = pu.ID_Publicacao
      ) > 3
ORDER BY Total_Interacoes DESC;

CREATE OR REPLACE VIEW view_historico_mensagens AS
SELECT
    c.ID_Conversa,
    pe.Nome,
    m.Conteudo,
    m.Data_Hora
FROM MENSAGEM m
         JOIN CONVERSA c ON m.ID_Conversa = c.ID_Conversa
         JOIN PERFIL pe ON m.ID_Perfil = pe.ID_Perfil
ORDER BY m.Data_Hora ASC;