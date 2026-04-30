-- ============================================================
--   CASTELLANE Auto Ecole - Base de donnees
--   A importer dans phpMyAdmin ou via la commande :
--   mysql -u root -p < castellane_autoecole.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS castellane_autoecole
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE castellane_autoecole;

-- ------------------------------------------------------------
-- Table : user  (comptes de connexion)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
    iduser  INT          NOT NULL AUTO_INCREMENT,
    nom     VARCHAR(80)  NOT NULL,
    prenom  VARCHAR(80)  NOT NULL,
    email   VARCHAR(100) NOT NULL UNIQUE,
    mdp     VARCHAR(100) NOT NULL,
    PRIMARY KEY (iduser)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Compte par defaut  :  admin@castellane.fr / admin
INSERT INTO user (nom, prenom, email, mdp) VALUES
    ('Admin', 'Castellane', 'admin@castellane.fr', 'admin');

-- ------------------------------------------------------------
-- Table : candidat
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS candidat (
    idcandidat  INT          NOT NULL AUTO_INCREMENT,
    nom         VARCHAR(80)  NOT NULL,
    prenom      VARCHAR(80)  NOT NULL,
    email       VARCHAR(100),
    telephone   VARCHAR(20)  NOT NULL,
    adresse     VARCHAR(200),
    typepermis  VARCHAR(10)  NOT NULL,   -- ex : B, A, A2, AM...
    PRIMARY KEY (idcandidat)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exemples
INSERT INTO candidat (nom, prenom, email, telephone, adresse, typepermis) VALUES
    ('Dupont',   'Lucas',  'lucas.dupont@mail.fr',   '0612345678', '12 rue de la Paix, Marseille',  'B'),
    ('Martin',   'Emma',   'emma.martin@mail.fr',    '0687654321', '5 avenue Foch, Aix-en-Provence','B'),
    ('Bernard',  'Hugo',   'hugo.bernard@mail.fr',   '0698765432', '3 chemin des Oliviers, Toulon', 'A2');

-- ------------------------------------------------------------
-- Table : moniteur
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS moniteur (
    idmoniteur  INT          NOT NULL AUTO_INCREMENT,
    nom         VARCHAR(80)  NOT NULL,
    prenom      VARCHAR(80)  NOT NULL,
    email       VARCHAR(100),
    telephone   VARCHAR(20)  NOT NULL,
    PRIMARY KEY (idmoniteur)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exemples
INSERT INTO moniteur (nom, prenom, email, telephone) VALUES
    ('Castellane', 'Pierre', 'p.castellane@autoecole.fr', '0491234567'),
    ('Rossi',      'Marie',  'm.rossi@autoecole.fr',      '0499876543');

-- ------------------------------------------------------------
-- Table : vehicule
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS vehicule (
    idvehicule      INT         NOT NULL AUTO_INCREMENT,
    marque          VARCHAR(50) NOT NULL,
    modele          VARCHAR(50) NOT NULL,
    immatriculation VARCHAR(20) NOT NULL UNIQUE,
    PRIMARY KEY (idvehicule)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exemples
INSERT INTO vehicule (marque, modele, immatriculation) VALUES
    ('Renault', 'Clio V',    'AB-123-CD'),
    ('Peugeot', '208',       'EF-456-GH'),
    ('Citroen', 'C3',        'IJ-789-KL');

-- ------------------------------------------------------------
-- Table : lecon
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS lecon (
    idlecon     INT         NOT NULL AUTO_INCREMENT,
    datelecon   DATE        NOT NULL,
    heuredebut  TIME        NOT NULL,
    heurefin    TIME        NOT NULL,
    idcandidat  INT         NOT NULL,
    idmoniteur  INT         NOT NULL,
    PRIMARY KEY (idlecon),
    CONSTRAINT fk_lecon_candidat  FOREIGN KEY (idcandidat)  REFERENCES candidat (idcandidat)  ON DELETE CASCADE,
    CONSTRAINT fk_lecon_moniteur  FOREIGN KEY (idmoniteur)  REFERENCES moniteur (idmoniteur)  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Exemples (utilise les IDs inseres ci-dessus)
INSERT INTO lecon (datelecon, heuredebut, heurefin, idcandidat, idmoniteur) VALUES
    ('2026-04-07', '09:00', '10:00', 1, 1),
    ('2026-04-07', '10:00', '11:00', 2, 2),
    ('2026-04-08', '14:00', '15:00', 3, 1),
    ('2026-04-09', '11:00', '12:00', 1, 2);
