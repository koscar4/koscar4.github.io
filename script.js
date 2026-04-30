/* ============================================
   PORTFOLIO OSCAR NGUEMA — Script v2
   ============================================ */

// ===== 1. DARK MODE =====
const themeToggle = document.getElementById('themeToggle');
const html = document.documentElement;

function setTheme(theme) {
    html.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);
    themeToggle.textContent = theme === 'dark' ? '☀️' : '🌙';
}

// Init theme
const savedTheme = localStorage.getItem('theme') ||
    (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
setTheme(savedTheme);

themeToggle.addEventListener('click', () => {
    setTheme(html.getAttribute('data-theme') === 'dark' ? 'light' : 'dark');
});

// ===== 2. NAVBAR SCROLL =====
const navbar = document.querySelector('.navbar');
window.addEventListener('scroll', () => {
    navbar.classList.toggle('scrolled', window.scrollY > 20);
}, { passive: true });

// ===== 3. MENU MOBILE =====
const menuToggle = document.getElementById('menuToggle');
const navMenu = document.getElementById('navMenu');

menuToggle.addEventListener('click', () => {
    const expanded = menuToggle.getAttribute('aria-expanded') === 'true';
    menuToggle.setAttribute('aria-expanded', String(!expanded));
    menuToggle.classList.toggle('active');
    navMenu.classList.toggle('active');
});

navMenu.querySelectorAll('a').forEach(link => {
    link.addEventListener('click', () => {
        menuToggle.classList.remove('active');
        navMenu.classList.remove('active');
        menuToggle.setAttribute('aria-expanded', 'false');
    });
});

document.addEventListener('click', (e) => {
    if (!e.target.closest('.nav-container')) {
        menuToggle.classList.remove('active');
        navMenu.classList.remove('active');
        menuToggle.setAttribute('aria-expanded', 'false');
    }
});

// ===== 4. ACTIVE NAV LINK =====
const sections = document.querySelectorAll('section[id]');
const navLinks = document.querySelectorAll('.nav-menu a');

function updateActiveLink() {
    const scrollPos = window.scrollY + 120;
    sections.forEach(section => {
        const top = section.offsetTop;
        const height = section.clientHeight;
        const id = section.getAttribute('id');
        if (scrollPos >= top && scrollPos < top + height) {
            navLinks.forEach(link => {
                link.classList.toggle('active', link.getAttribute('href') === `#${id}`);
            });
        }
    });
}
window.addEventListener('scroll', updateActiveLink, { passive: true });
updateActiveLink();

// ===== 5. SCROLL REVEAL =====
const revealEls = document.querySelectorAll('.reveal');
const revealObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('visible');
            revealObserver.unobserve(entry.target);
        }
    });
}, { threshold: 0.1, rootMargin: '0px 0px -60px 0px' });

revealEls.forEach(el => revealObserver.observe(el));

// ===== 6. SKILL IMAGES FALLBACK =====
document.querySelectorAll('.skill-icon img').forEach(img => {
    img.addEventListener('error', function () {
        this.style.display = 'none';
        const fallback = this.parentElement.querySelector('.icon-fallback');
        if (fallback) fallback.style.opacity = '1';
    });
});

// ===== 7. FILTRAGE PROJETS =====
const filterBtns = document.querySelectorAll('.filter-btn');
const projectCards = document.querySelectorAll('.project-card');

filterBtns.forEach(btn => {
    btn.addEventListener('click', () => {
        filterBtns.forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        const filter = btn.getAttribute('data-filter');
        projectCards.forEach(card => {
            const cat = card.getAttribute('data-category');
            const show = filter === 'all' || cat === filter;
            if (show) {
                card.classList.remove('hidden');
                requestAnimationFrame(() => {
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0)';
                });
            } else {
                card.style.opacity = '0';
                card.style.transform = 'translateY(16px)';
                setTimeout(() => card.classList.add('hidden'), 280);
            }
        });
    });
});

// Init transition on cards
projectCards.forEach(card => {
    card.style.transition = 'opacity .28s ease, transform .28s ease';
});

// ===== 8. DONNÉES PROJETS =====
const projectsData = {
    snake: {
        title: 'Jeu Snake',
        image: 'assets/img/projet-snake_game.jpg',
        description: `
            <h3>Recréation du classique jeu Snake</h3>
            <p><strong>Contexte :</strong> Projet scolaire JavaScript pour pratiquer la programmation événementielle et le rendu graphique via l'API Canvas du navigateur.</p>
            <p><strong>Objectifs :</strong> Implémenter une boucle de jeu fluide, gérer les collisions, maintenir un score progressif et permettre la pause/reprise.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Déplacement du serpent au clavier (touches directionnelles)</li>
                <li>Système de score avec accélération progressive de la vitesse</li>
                <li>Détection des collisions (murs et corps du serpent)</li>
                <li>Pause, reprise et reset de la partie</li>
                <li>Interface responsive adaptée desktop et mobile</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Synchroniser la boucle de jeu avec <code>requestAnimationFrame</code> pour obtenir une animation fluide sans consommer trop de CPU.</p>
            <p><strong>Solution apportée :</strong> Utilisation d'un compteur de délai interne pour réguler la vitesse indépendamment du taux de rafraîchissement de l'écran.</p>
            <p><strong>Technologies :</strong> HTML5, CSS3, JavaScript vanilla, Canvas API</p>
            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B4 — Mode projet</p>`,
        demo: 'projects/Snakegame/Snakegame/index.html',
        github: 'https://github.com/koscar4/Snakegame'
    },
    morpion: {
        title: 'Morpion 2.0',
        image: 'assets/img/projet-morpion.svg',
        description: `
            <h3>Jeu de morpion interactif personnalisé</h3>
            <p><strong>Contexte :</strong> Projet web JavaScript avec une interface moderne permettant aux joueurs de choisir leurs symboles (texte ou images) avant de jouer.</p>
            <p><strong>Objectifs :</strong> Approfondir la manipulation du DOM, gérer un état de jeu cohérent et proposer une UX soignée avec aperçu des sélections.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Grille de morpion interactive 3×3</li>
                <li>Choix de symboles classiques (X/O) ou d'images personnalisées par joueur</li>
                <li>Aperçu en direct des sélections avant le lancement</li>
                <li>Détection automatique de victoire, match nul et remise à zéro rapide</li>
                <li>Interface responsive desktop et mobile</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Gérer dynamiquement l'état de chaque case (joueur 1, joueur 2, vide) tout en vérifiant les 8 combinaisons gagnantes à chaque coup.</p>
            <p><strong>Solution apportée :</strong> Représentation de la grille par un tableau JavaScript indexé avec vérification systématique après chaque clic.</p>
            <p><strong>Technologies :</strong> HTML5, CSS3, JavaScript vanilla</p>
            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B4 — Mode projet</p>`,
        demo: 'projects/Morpion2.0/index.html',
        github: 'https://github.com/koscar4/morpion2.0'
    },
    shopify: {
        title: 'Boutique Shopify',
        image: 'assets/img/Shopify-Logo-PNG.png',
        description: `
            <h3>Configuration e-commerce Shopify</h3>
            <p><strong>Contexte :</strong> Projet scolaire de création d'une boutique en ligne complète sur la plateforme Shopify, avec catalogue de produits et intégration du paiement.</p>
            <p><strong>Objectifs :</strong> Maîtriser la configuration d'une solution e-commerce clé-en-main, comprendre le langage de templating Liquid et optimiser l'expérience d'achat.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Création et configuration complète d'une boutique Shopify</li>
                <li>Gestion des produits, collections et catégories</li>
                <li>Pages produit avec images, descriptions et variantes</li>
                <li>Intégration du système de paiement natif Shopify</li>
                <li>Personnalisation du thème via le langage Liquid et CSS</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Comprendre la syntaxe Liquid (spécifique à Shopify) pour personnaliser les templates sans casser le thème existant.</p>
            <p><strong>Solution apportée :</strong> Lecture de la documentation Shopify et tests progressifs sur un thème de développement isolé.</p>
            <p><strong>Technologies :</strong> Shopify, Liquid, CSS, JavaScript</p>
            <p><strong>Compétences BTS :</strong> B3 — Présence en ligne · B4 — Mode projet</p>`,
        demo: null,
        github: null
    },
    ecommerce: {
        title: 'Site E-Enseignement (WordPress)',
        image: 'assets/img/projet-E-enseignement.png',
        description: `
            <h3>Plateforme d'enseignement en ligne</h3>
            <p><strong>Contexte :</strong> Projet scolaire en équipe pour développer une plateforme d'e-learning avec WordPress, hébergée sur IONOS. Maquettage Figma préalable.</p>
            <p><strong>Objectifs :</strong> Créer un site pédagogique fonctionnel, gérer le contenu via CMS, sécuriser l'accès et déployer sur un hébergeur réel.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Site WordPress complet avec gestion de contenu CMS</li>
                <li>Maquettes UI/UX réalisées sur Figma avant développement</li>
                <li>Personnalisation du thème et des pages</li>
                <li>Déploiement et configuration sur hébergeur IONOS</li>
                <li>Sécurisation de l'accès administrateur</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Coordonner le travail en équipe sur un même projet WordPress sans conflits de modifications simultanées.</p>
            <p><strong>Solution apportée :</strong> Attribution de rôles distincts (design, contenu, technique) et validation par le chef de projet avant chaque modification majeure.</p>
            <p><strong>Technologies :</strong> WordPress, CMS, Figma, IONOS</p>
            <p><strong>Compétences BTS :</strong> B3 — Présence en ligne · B4 — Mode projet · B5 — Mise à disposition service</p>`,
        demo: null,
        github: null
    },
    'auto-lourd': {
        title: 'Auto-école — Client Lourd (Java)',
        image: 'assets/img/projet-client-lourd.jpg',
        description: `
            <h3>Application desktop de gestion d'auto-école</h3>
            <p><strong>Contexte :</strong> Projet scolaire de développement d'une application Java avec interface graphique Swing pour gérer complètement une auto-école.</p>
            <p><strong>Objectifs :</strong> Maîtriser la programmation orientée objet en Java, concevoir une GUI avec Swing, connecter une base de données via JDBC et appliquer l'architecture MVC.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Gestion des élèves (inscription, suivi, résultats)</li>
                <li>Gestion des moniteurs et planning des leçons</li>
                <li>Suivi des paiements et facturation</li>
                <li>Génération de rapports et exports PDF</li>
                <li>Authentification avec rôles (admin, secrétaire, moniteur)</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Gérer la synchronisation entre l'interface Swing (thread EDT) et les requêtes base de données JDBC sans bloquer l'UI.</p>
            <p><strong>Solution apportée :</strong> Utilisation de <code>SwingWorker</code> pour exécuter les opérations BDD en arrière-plan et mettre à jour l'UI de façon thread-safe.</p>
            <p><strong>Technologies :</strong> Java, Swing (GUI), JDBC, Base de données, Eclipse</p>
            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B9 — Gestion des données · B4 — Mode projet</p>`,
        demo: null,
        github: 'https://github.com/koscar4/Projet_autoecole_client_lourd'
    },
    'auto-leger': {
        title: 'Auto-école — Client Léger (PHP/MySQL)',
        images: [
            'assets/img/autoecole-client-leger.png',
            'assets/img/autoecole-inscription.png',
            'assets/img/autoecole-lecons.png',
            'assets/img/autoecole-candidats.png'
        ],
        description: `
            <h3>Application web de gestion d'auto-école</h3>
            <p><strong>Contexte :</strong> Version web de la gestion d'auto-école, accessible via navigateur, développée en PHP/MySQL avec architecture MVC sous XAMPP.</p>
            <p><strong>Objectifs :</strong> Implémenter un CRUD complet, sécuriser les accès via sessions PHP, organiser le code en MVC et réaliser des requêtes SQL paramétrées.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Gestion des candidats, moniteurs et véhicules</li>
                <li>Planification et suivi des cours de conduite</li>
                <li>Opérations CRUD complètes sur toutes les entités</li>
                <li>Système de connexion avec sessions sécurisées</li>
                <li>Interface simple et navigation rapide</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Organiser correctement l'architecture MVC en PHP pur sans framework, notamment la séparation entre la logique métier et l'affichage.</p>
            <p><strong>Solution apportée :</strong> Mise en place d'un routeur simple basé sur le paramètre GET <code>page</code> avec inclusion des contrôleurs et vues correspondants.</p>
            <p><strong>Technologies :</strong> HTML5/CSS3, PHP, MySQL, PDO, Architecture MVC, XAMPP</p>
            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B9 — Gestion des données · B8 — Maintenance &amp; évolution</p>`,
        demo: 'http://localhost/projet_autoecole/',
        github: 'https://github.com/koscar4/Client_leger_autoecole'
    },
    glpi: {
        title: 'GLPI — Gestion de parc informatique',
        image: 'assets/img/projet-glpi.png',
        description: `
            <h3>Installation et configuration de GLPI</h3>
            <p><strong>Contexte :</strong> Projet scolaire d'administration système : installation d'un environnement LAMP complet sous Linux puis déploiement de GLPI pour simuler un service support IT.</p>
            <p><strong>Objectifs :</strong> Maîtriser l'administration Linux, déployer un stack LAMP, configurer GLPI et mettre en place un système de ticketing IT fonctionnel.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Installation Linux, Apache, PHP et MySQL depuis zéro</li>
                <li>Déploiement et configuration initiale de GLPI</li>
                <li>Gestion des utilisateurs, profils et droits d'accès</li>
                <li>Création et suivi des tickets d'assistance IT</li>
                <li>Inventaire du parc matériel et logiciel</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Configurer correctement les permissions Linux et les droits MySQL pour que GLPI puisse accéder à sa base de données.</p>
            <p><strong>Solution apportée :</strong> Lecture de la documentation GLPI et ajustement des droits via <code>chmod</code>/<code>chown</code> et configuration du fichier <code>config_db.php</code>.</p>
            <p><strong>Technologies :</strong> Linux, Apache, PHP, MySQL, GLPI</p>
            <p><strong>Compétences BTS :</strong> B1 — Patrimoine informatique · B2 — Réponse aux incidents · B5 — Mise à disposition service</p>`,
        demo: null,
        github: null
    },
    scolarite: {
        title: 'Application Scolarité',
        image: 'assets/img/projet-scolarite.png',
        description: `
            <h3>Gestion de scolarité en PHP/MySQL</h3>
            <p><strong>Contexte :</strong> Projet scolaire de développement d'une application web locale pour administrer un établissement scolaire : étudiants, classes, matières et professeurs.</p>
            <p><strong>Objectifs :</strong> Concevoir une base de données relationnelle, implémenter le CRUD complet, sécuriser l'authentification admin et organiser le code en MVC.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>CRUD complet : étudiants, classes, matières, professeurs</li>
                <li>Connexion administrateur sécurisée (sessions PHP)</li>
                <li>Affichage dynamique des données depuis la BDD</li>
                <li>Recherche et filtrage des étudiants</li>
                <li>Organisation MVC avec séparation vues/contrôleurs</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Concevoir un schéma de base de données cohérent avec les bonnes relations (classe → étudiants, matière → professeur) sans redondance.</p>
            <p><strong>Solution apportée :</strong> Modélisation préalable avec un diagramme entité-association, normalisation en 3NF et utilisation de clés étrangères avec contraintes d'intégrité.</p>
            <p><strong>Technologies :</strong> PHP, MySQL, PDO, XAMPP, HTML5/CSS3, Architecture MVC</p>
            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B9 — Gestion des données · B8 — Maintenance &amp; évolution</p>`,
        demo: 'projects/Scolarite/index.php',
        github: null
    },
    tictactoe: {
        title: 'Tic-Tac-Toe Java',
        image: 'assets/img/TicTacToeJava.png',
        description: `
            <h3>Jeu de morpion en Java avec interface Swing</h3>
            <p><strong>Contexte :</strong> Projet scolaire de développement d'une application desktop Java pour pratiquer la programmation orientée objet et la conception d'interfaces graphiques avec Swing.</p>
            <p><strong>Objectifs :</strong> Implémenter la logique complète d'un jeu de morpion, concevoir une interface graphique réactive et gérer les événements utilisateur.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Grille de jeu 3×3 interactive avec boutons Swing</li>
                <li>Alternance automatique des tours entre les deux joueurs</li>
                <li>Détection automatique de victoire (lignes, colonnes, diagonales) et de match nul</li>
                <li>Affichage du joueur gagnant et option de réinitialisation de la partie</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Implémenter proprement la détection des 8 combinaisons gagnantes et la gestion de l'état de la grille.</p>
            <p><strong>Solution apportée :</strong> Utilisation d'un tableau 2D pour représenter l'état de la grille et vérification systématique après chaque coup joué.</p>
            <p><strong>Technologies :</strong> Java, Swing, Programmation orientée objet</p>
            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B4 — Mode projet</p>`,
        demo: null,
        github: null
    },
    convertisseur: {
        title: 'Convertisseur Java',
        image: 'assets/img/Convertisseur.png',
        description: `
            <h3>Application de conversion d'unités en Java</h3>
            <p><strong>Contexte :</strong> Projet scolaire de développement d'une application desktop Java permettant de convertir différentes unités de mesure via une interface graphique Swing.</p>
            <p><strong>Objectifs :</strong> Maîtriser la conception d'interfaces Swing, gérer les événements de saisie et structurer le code en classes Java orientées objet.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Conversion de températures (Celsius, Fahrenheit, Kelvin)</li>
                <li>Conversion de distances, poids et autres unités courantes</li>
                <li>Mise à jour du résultat en temps réel à la saisie</li>
                <li>Interface graphique intuitive avec menus de sélection des unités</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Gérer la saisie en temps réel sans bloquer l'interface et valider les entrées utilisateur (nombres négatifs, caractères invalides).</p>
            <p><strong>Solution apportée :</strong> Utilisation d'un <code>DocumentListener</code> sur le champ de saisie et mise en place d'un bloc try/catch pour filtrer les entrées non numériques.</p>
            <p><strong>Technologies :</strong> Java, Swing, Programmation orientée objet</p>
            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B4 — Mode projet</p>`,
        demo: null,
        github: null
    },
    'stage-1': {
        title: 'Stage 1ère année — Délice Éternel Gabon',
        image: 'assets/img/.png',
        description: `
            <h3>Délice Éternel Gabon — Site e-commerce</h3>
            <p>
                <strong>Entreprise :</strong> UP2DATE-IT — 6 Allée Louise Bourgeois, Villejuif (94) &nbsp;|&nbsp;
                <strong>Responsable :</strong> M'VOU Aristide Wylfrand (Président)<br>
                <strong>Période :</strong> 24 novembre 2025 → 2 janvier 2026 &nbsp;|&nbsp; <strong>Durée :</strong> 5 semaines
            </p>
            <p><strong>Contexte :</strong> Développement d'un site e-commerce de vêtements africains, bijoux et masques dans le cadre de mon stage de 1ère année BTS SIO SLAM.</p>

            <h4 style="margin-top:1.2rem;">Réalisations</h4>
            <ul>
                <li><strong>Architecture modulaire Angular 12</strong> — AuthModule, CoreModule, FeaturesModule, WelcomeModule, ShoppingCartModule</li>
                <li><strong>Routing avec lazy loading</strong> sur 4 modules : tenues (<code>/dresses</code>), bijoux (<code>/earings</code>), masques (<code>/masks</code>), panier (<code>/shopping-cart</code>)</li>
                <li><strong>Gestion d'état NgRx Store</strong> — chargement automatique des 3 catégories produits au démarrage via Actions</li>
                <li><strong>Internationalisation multi-langue</strong> avec <code>@ngx-translate/core</code> (fichiers JSON dans <code>assets/i18n/</code>)</li>
                <li><strong>Authentification</strong> utilisateur via Firebase Auth + AngularFire</li>
                <li><strong>Intégration Firebase complète</strong> : Firestore (BDD), Storage (images), Functions (backend), Hosting (déploiement)</li>
                <li><strong>Optimisation des images</strong> produits via Firebase Storage</li>
                <li><strong>Notifications email automatiques</strong> avec SendGrid via Firebase Functions</li>
                <li><strong>Animations de transition</strong> entre pages (<code>routerTransition</code>)</li>
                <li><strong>Déploiement en production</strong> sur Firebase Hosting</li>
            </ul>

            <h4 style="margin-top:1.2rem;">Stack technique</h4>
            <p><strong>Frontend :</strong> Angular 12 · TypeScript · SCSS · Bootstrap 4 · Angular Material · RxJS · Font Awesome</p>
            <p><strong>Backend / Cloud :</strong> Firebase Auth · Firestore · Firebase Storage · Firebase Functions · Firebase Hosting · SendGrid</p>
            <p><strong>State / Outils :</strong> NgRx · @ngx-translate · jQuery</p>

            <p><strong>Compétences BTS :</strong> B1 — Patrimoine informatique · B4 — Mode projet · B6 — Développement professionnel</p>`,
        demo: 'https://delice-eternel-gabon.web.app',
        github: null
    },
    'stage-2': {
        title: 'Stage 2ème année — Système de paiement Stripe',
        image: 'assets/img/logostripe.png',
        description: `
            <h3>Implémentation d'un système de paiement en ligne via Stripe</h3>
            <p><strong>Durée :</strong> 5 semaines &nbsp;|&nbsp; <strong>Stack :</strong> Angular + Firebase + Stripe + SendGrid</p>

            <h4 style="margin-top:1.2rem;">Contexte &amp; Objectifs</h4>
            <p>Dans le cadre de mon stage de 2ème année BTS SIO SLAM, j'ai développé un <strong>module de paiement en ligne</strong> basé sur Stripe Payment Links, intégré à une plateforme de commande existante Angular + Firebase. L'objectif était de rendre le paiement simple, sécurisé et automatisé : génération des liens Stripe, envoi automatique par email, suivi des statuts en temps réel via webhooks.</p>

            <h4 style="margin-top:1.2rem;">Déroulement semaine par semaine</h4>
            <ul>
                <li><strong>S1 — Analyse :</strong> Prise en main de l'architecture Angular + Firebase + SendGrid. Étude du flux de commande, configuration Stripe en mode test.</li>
                <li><strong>S2 — Firebase Function :</strong> Création de <code>createOrderPaymentLink()</code> : calcul du montant, génération du lien Stripe, stockage Firestore, retour de l'URL.</li>
                <li><strong>S3 — Email &amp; Angular :</strong> Intégration SendGrid. Mise à jour du composant Angular avec bouton "Payer en ligne" et appel via <code>httpsCallable()</code>.</li>
                <li><strong>S4 — Webhook Stripe :</strong> Fonction <code>stripeWebhook()</code> pour écouter <code>payment_link.completed</code> et mettre à jour Firestore : <em>pending → paid</em>. Vérification signature Stripe.</li>
                <li><strong>S5 — Tests &amp; Documentation :</strong> Tests complets (paiements, erreurs, expirations). Rédaction de la documentation technique complète.</li>
            </ul>

            <h4 style="margin-top:1.2rem;">Difficultés rencontrées</h4>
            <ul>
                <li><strong>Webhooks Stripe en local :</strong> Les webhooks nécessitent une URL publique — impossible en développement local standard.</li>
                <li><strong>Sécurité de la signature :</strong> Valider correctement la signature Stripe pour éviter les faux événements malveillants.</li>
                <li><strong>Synchronisation Angular/Firebase :</strong> Mettre à jour l'UI Angular en temps réel quand le statut Firestore change après le webhook.</li>
            </ul>

            <h4 style="margin-top:1.2rem;">Solutions apportées</h4>
            <ul>
                <li>Utilisation de <strong>Stripe CLI</strong> (<code>stripe listen --forward-to</code>) pour tunneliser les webhooks en local.</li>
                <li>Vérification systématique avec <code>stripe.webhooks.constructEvent()</code> et la clé secrète webhook.</li>
                <li>Souscription à un <strong>Firestore snapshot listener</strong> dans le composant Angular pour une mise à jour réactive.</li>
            </ul>

            <h4 style="margin-top:1.2rem;">Résultat final</h4>
            <p>Module de paiement entièrement opérationnel et documenté : génération automatique des liens Stripe, envoi email, suivi des statuts en temps réel. Livré avec rapport de stage et documentation technique complète (architecture, modèle Firestore, procédure de mise en production).</p>

            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B8 — Maintenance &amp; évolution · B9 — Gestion des données · B5 — Documentation</p>
        `,
        demo: null,
        github: null
    },
    pokedex: {
        title: 'Pokédex Angular',
        image: 'assets/img/angular-pokedex-app.png',
        description: `
            <h3>Application Pokédex avec Angular</h3>
            <p><strong>Contexte :</strong> Projet de certification Udemy pour maîtriser Angular : création d'une application complète de gestion de Pokémon avec recherche, affichage détaillé, ajout, édition et suppression.</p>
            <p><strong>Objectifs :</strong> Maîtriser les composants standalone Angular, les signaux, les services HTTP, le routing protégé et les formulaires réactifs.</p>
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Liste de Pokémon avec barre de recherche filtrée en temps réel</li>
                <li>Fiche détaillée par Pokémon (stats, types, image)</li>
                <li>Ajout et édition via formulaires réactifs avec validation</li>
                <li>Suppression avec redirection automatique</li>
                <li>Authentification et routes protégées par guard</li>
                <li>Directive personnalisée de bordure colorée selon le type</li>
                <li>Double service : JSON Server (prod) et LocalStorage (dev)</li>
            </ul>
            <p><strong>Difficulté rencontrée :</strong> Gérer l'état asynchrone des données Pokémon avec les signaux Angular tout en maintenant la réactivité du filtre de recherche.</p>
            <p><strong>Solution apportée :</strong> Utilisation de <code>toSignal()</code> pour convertir les observables RxJS en signaux, combinée à <code>computed()</code> pour dériver le filtre automatiquement.</p>
            <p><strong>Technologies :</strong> Angular 21, TypeScript, RxJS, Bootstrap, JSON Server, Firebase</p>
            <p><strong>Compétences BTS :</strong> B7 — Développement applicatif · B4 — Mode projet</p>`,
        demo: 'http://localhost/PORTFOLIO_OSCAR_2026/projects/angular-pokedex-app/dist/angular-pokedex-app/browser/',
        github: 'https://github.com/koscar4/angular-pokedex-app'
    }
};

// ===== 9. MODAL =====
const projectModal = document.getElementById('projectModal');
const modalTitle = document.getElementById('modalTitle');
const modalBody = document.getElementById('modalBody');
const modalClose = document.querySelector('.modal-close');
const projectDemoBtn = document.getElementById('projectDemoBtn');
const projectGithubBtn = document.getElementById('projectGithubBtn');

function openModal(projectKey) {
    const data = projectsData[projectKey];
    if (!data) return;

    modalTitle.textContent = data.title;

    // Carousel multi-images ou image unique
    const imgs = data.images || (data.image ? [data.image] : []);
    let carouselHTML = '';
    if (imgs.length > 1) {
        const dots = imgs.map((_, i) => `<button class="carousel-dot${i===0?' active':''}" data-idx="${i}" aria-label="Image ${i+1}"></button>`).join('');
        carouselHTML = `
        <div class="modal-carousel" data-current="0">
            <div class="carousel-track" style="display:flex;transition:transform .35s ease;">
                ${imgs.map(src => `<img class="carousel-slide" src="${src}" alt="${data.title}" loading="lazy">`).join('')}
            </div>
            <button class="carousel-btn carousel-prev" aria-label="Précédent">&#8249;</button>
            <button class="carousel-btn carousel-next" aria-label="Suivant">&#8250;</button>
            <div class="carousel-dots">${dots}</div>
        </div>`;
    } else if (imgs.length === 1) {
        carouselHTML = `<img class="modal-project-image" src="${imgs[0]}" alt="${data.title}" loading="lazy">`;
    }

    modalBody.innerHTML = carouselHTML + data.description;

    // Init carousel navigation
    const carousel = modalBody.querySelector('.modal-carousel');
    if (carousel) {
        const track = carousel.querySelector('.carousel-track');
        const allDots = carousel.querySelectorAll('.carousel-dot');
        let cur = 0;
        const go = (n) => {
            cur = (n + imgs.length) % imgs.length;
            track.style.transform = `translateX(-${cur * 100}%)`;
            allDots.forEach((d, i) => d.classList.toggle('active', i === cur));
        };
        carousel.querySelector('.carousel-prev').addEventListener('click', () => go(cur - 1));
        carousel.querySelector('.carousel-next').addEventListener('click', () => go(cur + 1));
        allDots.forEach(d => d.addEventListener('click', () => go(+d.dataset.idx)));
    }

    if (data.demo) {
        projectDemoBtn.href = data.demo;
        projectDemoBtn.style.display = 'inline-flex';
    } else {
        projectDemoBtn.style.display = 'none';
    }

    if (data.github) {
        projectGithubBtn.href = data.github;
        projectGithubBtn.style.display = 'inline-flex';
    } else {
        projectGithubBtn.style.display = 'none';
    }

    projectModal.classList.add('active');
    projectModal.setAttribute('aria-hidden', 'false');
    document.body.style.overflow = 'hidden';

    // Focus trap
    setTimeout(() => modalClose.focus(), 100);
}

function closeModal() {
    projectModal.classList.remove('active');
    projectModal.setAttribute('aria-hidden', 'true');
    document.body.style.overflow = '';
}

document.querySelectorAll('[data-project]').forEach(btn => {
    btn.addEventListener('click', () => openModal(btn.getAttribute('data-project')));
});

modalClose.addEventListener('click', closeModal);
projectModal.addEventListener('click', (e) => { if (e.target === projectModal) closeModal(); });
document.addEventListener('keydown', (e) => { if (e.key === 'Escape') closeModal(); });

// ===== 10. SCROLL TO TOP =====
const scrollTopBtn = document.getElementById('scrollTop');

window.addEventListener('scroll', () => {
    scrollTopBtn.classList.toggle('visible', window.scrollY > 400);
}, { passive: true });

scrollTopBtn.addEventListener('click', () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
});

// ===== 11. CONTACT FORM =====
const contactForm = document.getElementById('contactForm');
const formStatus = document.getElementById('formStatus');

contactForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const name    = document.getElementById('contactName').value.trim();
    const email   = document.getElementById('contactEmail').value.trim();
    const subject = document.getElementById('contactSubject').value.trim();
    const message = document.getElementById('contactMessage').value.trim();

    const body   = `De : ${name} (${email})\n\n${message}`;
    const mailto = `mailto:oscarkevine004@gmail.com?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;

    window.location.href = mailto;

    formStatus.className = 'form-status success';
    formStatus.style.display = 'block';
    formStatus.textContent = '✅ Votre client mail va s\'ouvrir avec le message pré-rempli.';
    contactForm.reset();

    setTimeout(() => {
        formStatus.className = 'form-status';
        formStatus.style.display = 'none';
    }, 6000);
});

// ===== 12. SMOOTH SCROLL =====
document.querySelectorAll('a[href^="#"]').forEach(link => {
    link.addEventListener('click', (e) => {
        const href = link.getAttribute('href');
        if (href !== '#') {
            e.preventDefault();
            const target = document.querySelector(href);
            if (target) target.scrollIntoView({ behavior: 'smooth' });
        }
    });
});

// ===== 13. CONSOLE =====
console.log('%cPortfolio Oscar NGUEMA v2', 'color:#5B5FEF;font-size:18px;font-weight:bold;');
console.log('%cBTS SIO SLAM · Développeur Web', 'color:#26DE81;font-size:13px;');
