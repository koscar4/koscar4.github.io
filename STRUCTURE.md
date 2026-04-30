# 📂 Arborescence et Structure du Projet

## Arborescence Complète

```
Portfolio/
│
├── 📄 index.html                    # Page principale HTML
├── 🎨 style.css                     # Feuille de styles CSS
├── ⚙️ script.js                     # Logique JavaScript
│
├── 📚 README.md                     # Documentation principale
├── 🛠️ PERSONNALISATION.md           # Guide de customisation
└── 📋 STRUCTURE.md                  # Ce fichier
│
└── 📁 assets/                       # Dossier des ressources
    │
    ├── 📄 cv.pdf                    # CV téléchargeable
    │
    └── 📁 img/                      # Images du portfolio
        ├── hero-image.jpg           # Image hero (~1200x600px)
        ├── projet-snake.jpg         # Projet 1 (~400x250px)
        ├── projet-shopify.jpg       # Projet 2
        ├── projet-ecommerce.jpg     # Projet 3
        ├── projet-client-lourd.jpg  # Projet 4
        └── projet-client-leger.jpg  # Projet 5
```

---

## 📄 Fichiers Détaillés

### 1. `index.html` (411 lignes)

**Rôle** : Structure sémantique complète du portfolio

**Contient** :

| Section | Lignes | Éléments |
|---------|--------|----------|
| Head (META, TITLE, CSS) | 1-11 | SEO, Meta tags, Viewport |
| Navigation | 14-31 | Menu principal + Menu mobile |
| Hero Section | 34-53 | Titre, description, boutons CTA |
| Parcours Timeline | 57-107 | 4 éléments timeline |
| Compétences | 111-153 | 4 cartes de compétences |
| Projets | 157-277 | 5 cartes + filtres |
| Veille Tech | 281-309 | 4 cartes informationnelles |
| Contact | 313-339 | Infos + réseaux sociaux |
| Footer | 343-348 | Copyright |
| Modal | 351-364 | Popup détails projets |
| Script | 367 | Lien vers script.js |

**Points clés** :
- ✅ Structure sémantique HTML5
- ✅ ARIA labels pour accessibilité
- ✅ Meta tags SEO
- ✅ Loading="lazy" pour images
- ✅ Data attributes pour filtrage

---

### 2. `style.css` (960+ lignes)

**Rôle** : Tous les styles du site

**Sections** :

| Section | Lignes | Contenu |
|---------|--------|---------|
| Variables CSS | 1-50 | Couleurs, spacing, shadows |
| Reset Global | 52-100 | Styles de base |
| Navigation | 102-155 | Navbar, menu, responsive |
| Hero | 157-205 | Section d'accueil |
| Boutons | 207-235 | Styles btn + hover |
| Sections | 237-260 | Padding, h2, subtitles |
| Timeline | 262-315 | Parcours (ligne, markers) |
| Compétences | 317-365 | Cartes + listes de skills |
| Projets | 367-445 | Cards, images, badges |
| Veille | 447-480 | Cards informationnelles |
| Contact | 482-515 | Section contact + gradient |
| Footer | 517-530 | Footer styling |
| Modal | 532-575 | Popup + animations |
| Responsive (Tablet) | 577-680 | Media queries 768px |
| Responsive (Mobile) | 682-800 | Media queries 480px |
| Dark Mode | 802-830 | Prefers-color-scheme |
| Accessibility | 832-960 | Focus, reduced motion, contrast |

**Points clés** :
- ✅ 60+ variables CSS
- ✅ Mobile-first approach
- ✅ Flexbox + Grid
- ✅ Animations fluides
- ✅ Dark mode support
- ✅ Respects prefers-reduced-motion

---

### 3. `script.js` (400+ lignes)

**Rôle** : Interactivité et dynamique

**Fonctionnalités** :

| # | Fonction | Lignes | Description |
|---|----------|--------|-------------|
| 1 | Menu Mobile | 10-35 | Toggle menu hamburger |
| 2 | Active Link | 37-55 | Surligne lien de la section active |
| 3 | Filtrage Projets | 57-100 | Filtres avec animation |
| 4 | Données Projets | 126-231 | Objets avec détails complets |
| 5 | Modal Projets | 233-280 | Affichage/fermeture popup |
| 6 | Scroll to Top | 282-320 | Bouton flottant + animation |
| 7 | Smooth Scroll | 322-335 | Navigation fluide (#anchors) |
| 8 | Lazy Loading | 337-355 | Chargement images à la demande |
| 9 | Animation Scroll | 357-380 | Intersection Observer |
| 10 | Année Courante | 382-385 | Affiche 2026 en footer |
| 11 | Active Link Nav | 387-410 | Synchronise nav avec scroll |
| 12 | Keyboard Nav | 412-420 | Gestion clavier (Escape) |
| 13 | Console Message | 422-425 | Message de bienvenue |

**Points clés** :
- ✅ Zéro dépendance (vanilla JS)
- ✅ Accessible (clavier + lecteur écran)
- ✅ Performant (Intersection Observer)
- ✅ Responsive
- ✅ Animations fluides

---

### 4. `assets/cv.pdf`

**Rôle** : CV téléchargeable

**Format** : PDF standard
**Taille** : ~200KB recommandé
**À remplacer** : Avec votre vrai CV

---

### 5. `assets/img/` (6 fichiers)

| Fichier | Dimensions | Utilisation | Format |
|---------|-----------|------------|--------|
| `hero-image.jpg` | 1200x600px | Section hero | JPEG/SVG |
| `projet-snake.jpg` | 400x250px | Projet Snake | JPEG/SVG |
| `projet-shopify.jpg` | 400x250px | Boutique Shopify | JPEG/SVG |
| `projet-ecommerce.jpg` | 400x250px | E-commerce | JPEG/SVG |
| `projet-client-lourd.jpg` | 400x250px | Client Lourd | JPEG/SVG |
| `projet-client-leger.jpg` | 400x250px | Client Léger | JPEG/SVG |

**Actuellement** : Images SVG placeholders
**À remplacer** : Avec vraies images

---

## 🎯 Points d'Entrée Importants

### Pour modifier le CONTENU
1. **Nom & description** → `index.html` ligne ~60
2. **Parcours** → `index.html` ligne ~119-167
3. **Compétences** → `index.html` ligne ~196-242
4. **Projets** → `index.html` ligne ~279-406
5. **Détails projets** → `script.js` ligne ~126-231

### Pour modifier le DESIGN
1. **Couleurs** → `style.css` ligne ~4-10
2. **Fonts** → `style.css` ligne ~54
3. **Espacements** → `style.css` ligne ~20-30
4. **Responsive** → `style.css` ligne ~577-800

### Pour modifier l'INTERACTIVITÉ
1. **Menu mobile** → `script.js` ligne ~10-35
2. **Filtrage projets** → `script.js` ligne ~57-100
3. **Modal** → `script.js` ligne ~233-280

---

## 📊 Statistiques du Projet

| Métrique | Valeur |
|----------|--------|
| Lignes HTML | 411 |
| Lignes CSS | 960+ |
| Lignes JavaScript | 400+ |
| Fichiers images | 6 |
| Variables CSS | 60+ |
| Media queries | 3 |
| Sections principales | 6 |
| Projets | 5 |
| Points d'ancrage (#) | 7 |
| Fonctions JS | 13 |

---

## 🔄 Flux de Chargement

```
┌─────────────────────────────────────────────────────┐
│  1. Navigateur charge index.html                     │
│                                                     │
│  2. HTML parse + Crée le DOM                        │
│      ├─ Charge style.css (rendu bloquant)          │
│      └─ Charge script.js (exécution)               │
│                                                     │
│  3. Images chargées avec loading="lazy"             │
│      ├─ Hero image (priorité haute)                │
│      └─ Images projets (à la demande)              │
│                                                     │
│  4. JavaScript initialise :                         │
│      ├─ Event listeners                            │
│      ├─ Intersection Observer                      │
│      └─ Navigation active                          │
│                                                     │
│  5. Rendu final + Prêt pour interaction            │
└─────────────────────────────────────────────────────┘
```

---

## 🎨 Hiérarchie CSS

```
:root (Variables globales)
├── * (Reset)
├── html, body (Base)
├── Heading (h1-h6)
├── Links (a)
├── .container (Layout)
├── .navbar (Navigation)
├── .hero (Accueil)
├── .btn (Boutons)
├── section (Sections)
├── .parcours + .timeline (Timeline)
├── .competences + .competence-card
├── .projets + .project-card + .modal
├── .veille + .veille-card
├── .contact + .footer
└── Media Queries
    ├── @media (max-width: 768px)
    └── @media (max-width: 480px)
```

---

## 🎯 Ordre de Chargement Optimal

**Pour performance maximale** :

1. **Images critiques en premier** : hero-image.jpg
2. **CSS sans dépendances** : Variables d'abord
3. **HTML sémantique** : Pas de divs inutiles
4. **JavaScript asynchrone** : Chargement différé
5. **Images en lazy-load** : Pour le reste

---

## 🔐 Fichiers Sensibles à Protéger

| Fichier | Raison | Action |
|---------|--------|--------|
| `assets/cv.pdf` | Données personnelles | ✅ À jour |
| Email dans HTML | Anti-spam | ⚠️ Format lisible |
| GitHub links | Profil personnel | ✅ Public OK |

---

## 📋 Checklist d'Installation

- [ ] Créer dossier `Portfolio/`
- [ ] Copier `index.html`
- [ ] Copier `style.css`
- [ ] Copier `script.js`
- [ ] Créer dossier `assets/`
- [ ] Créer dossier `assets/img/`
- [ ] Ajouter 6 images dans `assets/img/`
- [ ] Ajouter `cv.pdf` dans `assets/`
- [ ] Lancer avec Live Server
- [ ] Tester sur mobile
- [ ] Vérifier tous les liens

---

## 🚀 Déploiement

**Fichiers à déployer** :
```
Portfolio/
├── index.html ✅
├── style.css ✅
├── script.js ✅
├── README.md (optionnel)
└── assets/
    ├── cv.pdf ✅
    └── img/ (6 images) ✅
```

**Pas à déployer** :
- ❌ `index_old.html`
- ❌ `style_old.css`
- ❌ `script_old.js`
- ❌ `.git/`
- ❌ `.vscode/`

---

**Version** : 1.0
**Dernière mise à jour** : Février 2026
