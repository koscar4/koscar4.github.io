from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import cm
from reportlab.lib import colors
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, HRFlowable
from reportlab.lib.enums import TA_CENTER, TA_LEFT, TA_JUSTIFY

OUTPUT = r"C:\xampp\htdocs\Castellane_AutoEcole\castellane_presentation.pdf"

doc = SimpleDocTemplate(
    OUTPUT,
    pagesize=A4,
    rightMargin=2*cm,
    leftMargin=2*cm,
    topMargin=2*cm,
    bottomMargin=2*cm,
)

styles = getSampleStyleSheet()

# Custom styles
title_style = ParagraphStyle(
    'CustomTitle',
    parent=styles['Title'],
    fontSize=22,
    textColor=colors.HexColor('#1a3a5c'),
    spaceAfter=6,
    alignment=TA_CENTER,
)
subtitle_style = ParagraphStyle(
    'Subtitle',
    parent=styles['Normal'],
    fontSize=11,
    textColor=colors.HexColor('#555555'),
    spaceAfter=20,
    alignment=TA_CENTER,
)
h1_style = ParagraphStyle(
    'H1',
    parent=styles['Heading1'],
    fontSize=14,
    textColor=colors.white,
    backColor=colors.HexColor('#1a3a5c'),
    spaceBefore=18,
    spaceAfter=8,
    leftIndent=-10,
    rightIndent=-10,
    borderPad=6,
)
h2_style = ParagraphStyle(
    'H2',
    parent=styles['Heading2'],
    fontSize=12,
    textColor=colors.HexColor('#1a3a5c'),
    spaceBefore=12,
    spaceAfter=4,
)
body_style = ParagraphStyle(
    'Body',
    parent=styles['Normal'],
    fontSize=10,
    leading=15,
    spaceAfter=6,
    alignment=TA_JUSTIFY,
)
code_style = ParagraphStyle(
    'Code',
    parent=styles['Code'],
    fontSize=9,
    backColor=colors.HexColor('#f4f4f4'),
    leftIndent=12,
    rightIndent=12,
    borderPad=4,
    spaceAfter=8,
)
warning_style = ParagraphStyle(
    'Warning',
    parent=styles['Normal'],
    fontSize=10,
    leading=15,
    backColor=colors.HexColor('#fff3cd'),
    borderColor=colors.HexColor('#ffc107'),
    borderWidth=1,
    borderPad=8,
    spaceAfter=8,
)

story = []

# ── TITRE ──────────────────────────────────────────────────────────────────
story.append(Spacer(1, 1*cm))
story.append(Paragraph("Castellane Auto École", title_style))
story.append(Paragraph("Présentation des grands axes du code", subtitle_style))
story.append(HRFlowable(width="100%", thickness=2, color=colors.HexColor('#1a3a5c')))
story.append(Spacer(1, 0.5*cm))

# ── 1. ARCHITECTURE MVC ────────────────────────────────────────────────────
story.append(Paragraph("1. Architecture MVC", h1_style))
story.append(Paragraph(
    "Le projet suit rigoureusement le patron de conception <b>Modèle – Vue – Contrôleur (MVC)</b>, "
    "découpé en trois packages distincts :",
    body_style
))

mvc_data = [
    ["Package", "Rôle", "Fichiers principaux"],
    ["controleur/", "Contrôleur + entités métier", "Castellane.java, Controleur.java, + POJO"],
    ["modele/", "Accès base de données (JDBC)", "BDD.java, Modele.java"],
    ["vue/", "Interface graphique (Swing)", "VueConnexion.java, VueGenerale.java, Panel*.java"],
]
mvc_table = Table(mvc_data, colWidths=[4.5*cm, 6*cm, 7*cm])
mvc_table.setStyle(TableStyle([
    ('BACKGROUND', (0,0), (-1,0), colors.HexColor('#1a3a5c')),
    ('TEXTCOLOR',  (0,0), (-1,0), colors.white),
    ('FONTNAME',   (0,0), (-1,0), 'Helvetica-Bold'),
    ('FONTSIZE',   (0,0), (-1,-1), 9),
    ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.HexColor('#eaf0f8'), colors.white]),
    ('GRID',       (0,0), (-1,-1), 0.5, colors.HexColor('#cccccc')),
    ('PADDING',    (0,0), (-1,-1), 6),
    ('VALIGN',     (0,0), (-1,-1), 'MIDDLE'),
]))
story.append(mvc_table)
story.append(Spacer(1, 0.3*cm))

# ── 2. POINT D'ENTRÉE ──────────────────────────────────────────────────────
story.append(Paragraph("2. Point d'entrée — Castellane.java", h1_style))
story.append(Paragraph(
    "<b>Castellane.java</b> contient le <b>main()</b> et orchestre le cycle de vie de l'application. "
    "Il instancie la fenêtre de connexion au démarrage, puis gère la transition vers la fenêtre principale "
    "une fois l'utilisateur authentifié.",
    body_style
))
story.append(Paragraph("Responsabilités :", h2_style))
entry_data = [
    ["Méthode", "Action"],
    ["main()", "Crée la VueConnexion au lancement"],
    ["rentreVisibleConnexion(boolean)", "Affiche / masque la fenêtre de connexion"],
    ["creeDetruireVueGenerale(boolean)", "Crée ou ferme la fenêtre principale"],
]
entry_table = Table(entry_data, colWidths=[8*cm, 9.5*cm])
entry_table.setStyle(TableStyle([
    ('BACKGROUND', (0,0), (-1,0), colors.HexColor('#2c6ea0')),
    ('TEXTCOLOR',  (0,0), (-1,0), colors.white),
    ('FONTNAME',   (0,0), (-1,0), 'Helvetica-Bold'),
    ('FONTSIZE',   (0,0), (-1,-1), 9),
    ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.HexColor('#eaf0f8'), colors.white]),
    ('GRID',       (0,0), (-1,-1), 0.5, colors.HexColor('#cccccc')),
    ('PADDING',    (0,0), (-1,-1), 6),
]))
story.append(entry_table)

# ── 3. COUCHE MODÈLE ───────────────────────────────────────────────────────
story.append(Paragraph("3. Couche Modèle", h1_style))

story.append(Paragraph("BDD.java — Connexion JDBC", h2_style))
story.append(Paragraph(
    "Classe utilitaire qui encapsule la connexion à MySQL via JDBC. "
    "Elle expose trois méthodes : <b>seConnecter()</b>, <b>seDeConnecter()</b> et <b>getMaConnexion()</b>. "
    "Le driver utilisé est <i>com.mysql.cj.jdbc.Driver</i> (bibliothèque <i>mysql-connector-j-9.6.0.jar</i>).",
    body_style
))

story.append(Paragraph("Modele.java — CRUD complet", h2_style))
story.append(Paragraph(
    "Contient toutes les requêtes SQL pour les 4 entités de l'application. "
    "Chaque entité dispose des opérations <b>INSERT</b>, <b>UPDATE</b>, <b>DELETE</b> et <b>SELECT</b>. "
    "Les requêtes paramétrées utilisent des <b>PreparedStatement</b> pour sécuriser les données.",
    body_style
))

modele_data = [
    ["Entité", "Opérations disponibles"],
    ["candidat", "insertCandidat, updateCandidat, deleteCandidat, selectAllCandidats(filtre)"],
    ["moniteur", "insertMoniteur, updateMoniteur, deleteMoniteur, selectAllMoniteurs()"],
    ["vehicule", "insertVehicule, updateVehicule, deleteVehicule, selectAllVehicules(filtre)"],
    ["lecon",    "insertLecon, updateLecon, deleteLecon, selectAllLecons(filtre)"],
    ["user",     "selectWhereUser(email, mdp)"],
]
modele_table = Table(modele_data, colWidths=[3.5*cm, 14*cm])
modele_table.setStyle(TableStyle([
    ('BACKGROUND', (0,0), (-1,0), colors.HexColor('#2c6ea0')),
    ('TEXTCOLOR',  (0,0), (-1,0), colors.white),
    ('FONTNAME',   (0,0), (-1,0), 'Helvetica-Bold'),
    ('FONTSIZE',   (0,0), (-1,-1), 9),
    ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.HexColor('#eaf0f8'), colors.white]),
    ('GRID',       (0,0), (-1,-1), 0.5, colors.HexColor('#cccccc')),
    ('PADDING',    (0,0), (-1,-1), 6),
]))
story.append(modele_table)
story.append(Spacer(1, 0.2*cm))
story.append(Paragraph(
    "Le Modèle expose également des méthodes de <b>statistiques</b> : "
    "countCandidats(), countMoniteurs(), countVehicules(), countLecons(), "
    "countCandidatsByPermis() et countLeconsByMoniteur().",
    body_style
))

# ── 4. COUCHE CONTRÔLEUR ───────────────────────────────────────────────────
story.append(Paragraph("4. Couche Contrôleur", h1_style))

story.append(Paragraph("Controleur.java — Façade statique", h2_style))
story.append(Paragraph(
    "<b>Controleur.java</b> est une façade entièrement statique qui sert d'intermédiaire entre la Vue et le Modèle. "
    "La Vue ne communique <u>jamais directement</u> avec le Modèle : tous les appels passent par le Contrôleur. "
    "Chaque méthode du Contrôleur délègue simplement l'appel à la méthode correspondante du Modèle.",
    body_style
))

story.append(Paragraph("Entités métier (POJO)", h2_style))
story.append(Paragraph(
    "Les classes du package <i>controleur/</i> représentent les objets métier. "
    "Ce sont de simples classes Java avec attributs, constructeur et getters/setters :",
    body_style
))

pojo_data = [
    ["Classe", "Attributs principaux"],
    ["Candidat",  "idcandidat, nom, prenom, email, telephone, adresse, typepermis"],
    ["Moniteur",  "idmoniteur, nom, prenom, email, telephone"],
    ["Vehicule",  "idvehicule, marque, modele, immatriculation"],
    ["Lecon",     "idlecon, datelecon, heuredebut, heurefin, idcandidat, idmoniteur"],
    ["User",      "iduser, nom, prenom, email, mdp"],
]
pojo_table = Table(pojo_data, colWidths=[3.5*cm, 14*cm])
pojo_table.setStyle(TableStyle([
    ('BACKGROUND', (0,0), (-1,0), colors.HexColor('#2c6ea0')),
    ('TEXTCOLOR',  (0,0), (-1,0), colors.white),
    ('FONTNAME',   (0,0), (-1,0), 'Helvetica-Bold'),
    ('FONTSIZE',   (0,0), (-1,-1), 9),
    ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.HexColor('#eaf0f8'), colors.white]),
    ('GRID',       (0,0), (-1,-1), 0.5, colors.HexColor('#cccccc')),
    ('PADDING',    (0,0), (-1,-1), 6),
]))
story.append(pojo_table)

# ── 5. COUCHE VUE ──────────────────────────────────────────────────────────
story.append(Paragraph("5. Couche Vue (Swing)", h1_style))

story.append(Paragraph("VueConnexion.java", h2_style))
story.append(Paragraph(
    "Fenêtre de login (<b>JFrame</b>). Contient un formulaire email + mot de passe. "
    "Appelle <i>Controleur.selectWhereUser()</i> pour authentifier. "
    "En cas de succès, masque la fenêtre de connexion et ouvre la <i>VueGenerale</i>. "
    "Supporte la validation par la touche <b>Entrée</b> (KeyListener).",
    body_style
))

story.append(Paragraph("VueGenerale.java", h2_style))
story.append(Paragraph(
    "Fenêtre principale (1000x550 px). Affiche une <b>barre de navigation</b> avec 6 boutons "
    "et un système de panels : un seul panel est visible à la fois selon l'onglet sélectionné. "
    "Le bouton <i>Quitter</i> retourne à la fenêtre de connexion.",
    body_style
))

story.append(Paragraph("Les 5 panels de gestion", h2_style))
panels_data = [
    ["Panel", "Fonctionnalités"],
    ["PanelCandidats",    "CRUD candidats + champ de recherche (filtre multi-champs)"],
    ["PanelMoniteurs",    "CRUD moniteurs"],
    ["PanelVehicules",    "CRUD vehicules + champ de recherche"],
    ["PanelLecons",       "CRUD lecons + champ de recherche par date"],
    ["PanelStatistiques", "Affiche les compteurs globaux et tableaux de répartition"],
]
panels_table = Table(panels_data, colWidths=[5*cm, 12.5*cm])
panels_table.setStyle(TableStyle([
    ('BACKGROUND', (0,0), (-1,0), colors.HexColor('#2c6ea0')),
    ('TEXTCOLOR',  (0,0), (-1,0), colors.white),
    ('FONTNAME',   (0,0), (-1,0), 'Helvetica-Bold'),
    ('FONTSIZE',   (0,0), (-1,-1), 9),
    ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.HexColor('#eaf0f8'), colors.white]),
    ('GRID',       (0,0), (-1,-1), 0.5, colors.HexColor('#cccccc')),
    ('PADDING',    (0,0), (-1,-1), 6),
]))
story.append(panels_table)

# ── 6. BASE DE DONNÉES ─────────────────────────────────────────────────────
story.append(Paragraph("6. Base de données", h1_style))
story.append(Paragraph(
    "Le schéma SQL est fourni dans <b>src/sql/castellane_autoecole.sql</b>. "
    "La connexion est configurée dans <i>Modele.java</i> (ligne 20) avec les paramètres suivants :",
    body_style
))

db_data = [
    ["Paramètre", "Valeur"],
    ["Utilisateur MySQL", "root"],
    ["Mot de passe",      "root"],
    ["Base de données",   "castellane_autoecole"],
    ["Serveur",           "localhost:3306"],
    ["Driver JDBC",       "com.mysql.cj.jdbc.Driver (mysql-connector-j-9.6.0.jar)"],
]
db_table = Table(db_data, colWidths=[5*cm, 12.5*cm])
db_table.setStyle(TableStyle([
    ('BACKGROUND', (0,0), (-1,0), colors.HexColor('#2c6ea0')),
    ('TEXTCOLOR',  (0,0), (-1,0), colors.white),
    ('FONTNAME',   (0,0), (-1,0), 'Helvetica-Bold'),
    ('FONTSIZE',   (0,0), (-1,-1), 9),
    ('ROWBACKGROUNDS', (0,1), (-1,-1), [colors.HexColor('#eaf0f8'), colors.white]),
    ('GRID',       (0,0), (-1,-1), 0.5, colors.HexColor('#cccccc')),
    ('PADDING',    (0,0), (-1,-1), 6),
]))
story.append(db_table)
story.append(Spacer(1, 0.2*cm))
story.append(Paragraph(
    "Les 5 tables de la base correspondent directement aux 5 entités métier : "
    "<b>user</b>, <b>candidat</b>, <b>moniteur</b>, <b>vehicule</b>, <b>lecon</b>.",
    body_style
))

# ── 7. POINT D'ATTENTION ───────────────────────────────────────────────────
story.append(Paragraph("7. Point d'attention — Faille SQL Injection", h1_style))
story.append(Paragraph(
    "<b>Attention :</b> Dans les méthodes <i>selectAllCandidats()</i> et <i>selectAllVehicules()</i> "
    "de <b>Modele.java</b>, le paramètre de filtre est injecté par <b>concaténation de chaîne</b> "
    "directement dans la requête SQL. Cela expose l'application à une faille d'<b>injection SQL</b>.",
    warning_style
))
story.append(Spacer(1, 0.2*cm))
story.append(Paragraph("Code actuel (vulnérable) :", h2_style))
story.append(Paragraph(
    'req = "SELECT * FROM candidat WHERE nom LIKE \'%" + filtre + "%\'"',
    code_style
))
story.append(Paragraph("Correction recommandée :", h2_style))
story.append(Paragraph(
    'req = "SELECT * FROM candidat WHERE nom LIKE ?";<br/>'
    'ps.setString(1, "%" + filtre + "%");',
    code_style
))
story.append(Paragraph(
    "Les autres méthodes (INSERT, UPDATE, DELETE) utilisent correctement les <b>PreparedStatement</b> "
    "et ne sont pas concernées par cette faille.",
    body_style
))

# ── PIED DE PAGE ───────────────────────────────────────────────────────────
story.append(Spacer(1, 1*cm))
story.append(HRFlowable(width="100%", thickness=1, color=colors.HexColor('#cccccc')))
story.append(Spacer(1, 0.2*cm))
story.append(Paragraph(
    "Document généré automatiquement — Castellane Auto École — Projet Java Swing / MySQL",
    ParagraphStyle('Footer', parent=styles['Normal'], fontSize=8,
                   textColor=colors.HexColor('#888888'), alignment=TA_CENTER)
))

doc.build(story)
print(f"PDF généré : {OUTPUT}")
