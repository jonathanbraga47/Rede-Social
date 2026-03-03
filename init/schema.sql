CREATE TABLE IF NOT EXISTS perfil (
                                      id_perfil INT AUTO_INCREMENT PRIMARY KEY,
                                      email VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(20) NOT NULL,
    senha VARCHAR(20) NOT NULL
    );

CREATE TABLE IF NOT EXISTS conversa (
                                        id_conversa INT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS segue (
                                     user_seguidor INT,
                                     user_seguido INT,
                                     PRIMARY KEY (user_seguidor, user_seguido),
    FOREIGN KEY (user_seguidor) REFERENCES perfil(id_perfil) ON DELETE CASCADE,
    FOREIGN KEY (user_seguido) REFERENCES perfil(id_perfil) ON DELETE CASCADE,
    CONSTRAINT chk_nao_seguir_si_mesmo CHECK (user_seguidor <> user_seguido)
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
    pu.id_publicacao,
    pe.nome,
    pu.legenda,
    pu.data_hora,
    am.url_midia,
    (SELECT COUNT(*)
     FROM interacao_curtida ic
              JOIN interacao i ON ic.id_interacao = i.id_interacao
     WHERE i.id_publicacao = pu.id_publicacao) AS total_curtidas,
    (SELECT COUNT(*)
     FROM interacao_comentario ico
              JOIN interacao i ON ico.id_interacao = i.id_interacao
     WHERE i.id_publicacao = pu.id_publicacao) AS total_comentarios
FROM publicacao pu
         JOIN perfil pe ON pu.id_perfil = pe.id_perfil
         LEFT JOIN arquivo_midia am ON pu.id_publicacao = am.id_publicacao;

CREATE OR REPLACE VIEW view_engajamento AS
SELECT
    pu.id_publicacao,
    pe.nome AS autor,
    am.url_midia,
    pu.legenda,
    pu.data_hora,
    (SELECT COUNT(*) FROM interacao i WHERE i.id_publicacao = pu.id_publicacao) AS total_interacoes
FROM publicacao pu
         JOIN perfil pe ON pu.id_perfil = pe.id_perfil
         LEFT JOIN arquivo_midia am ON pu.id_publicacao = am.id_publicacao
WHERE (
          SELECT COUNT(*)
          FROM interacao i
          WHERE i.id_publicacao = pu.id_publicacao
      ) > 3
ORDER BY total_interacoes DESC;

CREATE OR REPLACE VIEW view_historico_mensagens AS
SELECT
    m.id_mensagem,
    c.id_conversa,
    pe.nome,
    m.conteudo,
    m.data_hora
FROM mensagem m
         JOIN conversa c ON m.id_conversa = c.id_conversa
         JOIN perfil pe ON m.id_perfil = pe.id_perfil
ORDER BY m.data_hora ASC;


DELIMITER $$

CREATE TRIGGER trg_curtida_unica
BEFORE INSERT ON interacao
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1
        FROM interacao i
        JOIN interacao_curtida ic 
            ON i.id_interacao = ic.id_interacao
        WHERE i.id_perfil = NEW.id_perfil
        AND i.id_publicacao = NEW.id_publicacao
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Você já curtiu essa publicacão.';
    END IF;
END$$

DELIMITER ;