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


CREATE OR REPLACE VIEW view_ranking_usuarios AS
SELECT
    pe.id_perfil,
    pe.nome,
    pe.email,

    COUNT(DISTINCT pu.id_publicacao) AS total_publicacoes,

    COUNT(DISTINCT i.id_interacao) AS total_interacoes_feitas,

    (SELECT COUNT(*)
     FROM interacao i2
              JOIN interacao_curtida ic ON i2.id_interacao = ic.id_interacao
     WHERE i2.id_publicacao IN (
         SELECT id_publicacao FROM publicacao WHERE id_perfil = pe.id_perfil
     )) AS curtidas_recebidas,

    (SELECT COUNT(*)
     FROM interacao i3
              JOIN interacao_comentario ico ON i3.id_interacao = ico.id_interacao
     WHERE i3.id_publicacao IN (
         SELECT id_publicacao FROM publicacao WHERE id_perfil = pe.id_perfil
     )) AS comentarios_recebidos,

    (SELECT COUNT(*)
     FROM segue s
     WHERE s.user_seguido = pe.id_perfil) AS total_seguidores,

    (SELECT COUNT(*)
     FROM segue s
     WHERE s.user_seguidor = pe.id_perfil) AS total_seguindo,

    (
        COUNT(DISTINCT pu.id_publicacao) * 2 +
        COUNT(DISTINCT i.id_interacao) * 1 +
        (SELECT COUNT(*) FROM interacao i2 WHERE i2.id_publicacao IN (
            SELECT id_publicacao FROM publicacao WHERE id_perfil = pe.id_perfil
        )) * 3 +
        (SELECT COUNT(*) FROM segue s WHERE s.user_seguido = pe.id_perfil) * 2
        ) AS score_engajamento

FROM perfil pe
         LEFT JOIN publicacao pu ON pe.id_perfil = pu.id_perfil
         LEFT JOIN interacao i ON pe.id_perfil = i.id_perfil
GROUP BY pe.id_perfil, pe.nome, pe.email
ORDER BY score_engajamento DESC;


CREATE OR REPLACE VIEW view_perfis_inatividade AS
SELECT
    pe.id_perfil,
    pe.nome,
    pe.email,

    (SELECT COUNT(*)
     FROM publicacao pu
     WHERE pu.id_perfil = pe.id_perfil) AS total_publicacoes,

    (SELECT COUNT(*)
     FROM interacao i
     WHERE i.id_perfil = pe.id_perfil) AS total_interacoes,

    (SELECT COUNT(*)
     FROM segue s
     WHERE s.user_seguidor = pe.id_perfil) AS total_seguindo,

    (SELECT COUNT(*)
     FROM segue s
     WHERE s.user_seguido = pe.id_perfil) AS total_seguidores,

    CASE
        WHEN (SELECT COUNT(*) FROM publicacao WHERE id_perfil = pe.id_perfil) = 0
            AND (SELECT COUNT(*) FROM interacao WHERE id_perfil = pe.id_perfil) = 0
            AND (SELECT COUNT(*) FROM segue WHERE user_seguidor = pe.id_perfil) = 0
            THEN 'Totalmente Inativo'

        WHEN (SELECT COUNT(*) FROM publicacao WHERE id_perfil = pe.id_perfil) = 0
            AND (SELECT COUNT(*) FROM interacao WHERE id_perfil = pe.id_perfil) = 0
            THEN 'Nunca Publicou nem Interagiu'

        WHEN (SELECT COUNT(*) FROM publicacao WHERE id_perfil = pe.id_perfil) = 0
            THEN 'Nunca Publicou'

        WHEN (SELECT COUNT(*) FROM interacao WHERE id_perfil = pe.id_perfil) = 0
            THEN 'Nunca Interagiu'

        WHEN (SELECT COUNT(*) FROM segue WHERE user_seguidor = pe.id_perfil) = 0
            THEN 'Nao Segue Ninguem'

        ELSE 'Ativo'
        END AS status_atividade

FROM perfil pe
HAVING status_atividade <> 'Ativo'
ORDER BY
    CASE status_atividade
        WHEN 'Totalmente Inativo' THEN 1
        WHEN 'Nunca Publicou nem Interagiu' THEN 2
        WHEN 'Nunca Publicou' THEN 3
        WHEN 'Nunca Interagiu' THEN 4
        WHEN 'Nao Segue Ninguem' THEN 5
        END;


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


DELIMITER $$

CREATE TRIGGER trg_perfil_campos_obrigatorios
BEFORE INSERT ON perfil
FOR EACH ROW
BEGIN
    IF NEW.email IS NULL OR TRIM(NEW.email) = '' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Email é obrigatório.';
    END IF;

    IF NEW.nome IS NULL OR TRIM(NEW.nome) = '' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Nome é obrigatório.';
    END IF;

    IF NEW.senha IS NULL OR TRIM(NEW.senha) = '' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Senha é obrigatória.';
    END IF;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER trg_valida_senha_forte
BEFORE INSERT ON perfil
FOR EACH ROW
BEGIN
    IF NEW.senha IS NULL OR TRIM(NEW.senha) = '' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Senha é obrigatória.';
    END IF;

    IF CHAR_LENGTH(NEW.senha) < 5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A senha deve ter pelo menos 5 caracteres.';
    END IF;

    IF NEW.senha NOT REGEXP '[A-Za-z]' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A senha deve conter pelo menos uma letra.';
    END IF;


    IF NOT (
        NEW.senha REGEXP '[0-9]' OR 
        NEW.senha REGEXP '[!@#$%&*_]'
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A senha deve conter pelo menos um número ou símbolo (!@#$%&*_)';
    END IF;

END$$

DELIMITER ;

