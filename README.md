# Portfolio Oscar NGUEMA - BTS SIO SLAM

## 📋 Description

Portfolio professionnel responsive développé en **HTML5 / CSS3 / JavaScript vanilla** (sans frameworks).

Inspiré par la structure de [monportfolioadel.com/mes-projets](https://monportfolioadel.com/mes-projets), ce portfolio présente :
- Navigation fluide et sticky
- Section hero impactante
- Timeline verticale du parcours
- Cartes de compétences par catégorie
- Galerie de projets avec filtres et modal
- Section veille technologique
- Formulaire de contact accessible
- Design responsive (mobile-first)
- Accessibilité WCAG 2.1

---

## 📁 Structure du Projet

```
Portfolio/
├── index.html              # Page principale - Structure HTML sémantique
├── style.css               # Feuille de styles avec variables CSS
├── script.js               # Interactivité (filtres, modal, navigation)
├── assets/
│   ├── cv.pdf              # CV téléchargeable
│   └── img/
│       ├── hero-image.jpg          # Image hero
│       ├── projet-snake.jpg        # Projet Snake
│       ├── projet-shopify.jpg      # Boutique Shopify
│       ├── projet-ecommerce.jpg    # E-commerce custom
│       ├── projet-client-lourd.jpg # Auto-école client lourd
│       └── projet-client-leger.jpg # Auto-école client léger
└── README.md               # Ce fichier
```

---

## 🚀 Comment Lancer le Portfolio

### Option 1 : Live Server (VS Code) - ⭐ RECOMMANDÉ

1. **Installer l'extension Live Server** :
   - Ouvrez VS Code
   - Allez dans Extensions (Ctrl+Shift+X)
   - Cherchez "Live Server" de Ritwick Dey
   - Cliquez sur Installer

2. **Lancer le serveur** :
   - Ouvrez le fichier `index.html`
   - Clic droit → "Open with Live Server"
   - Votre navigateur s'ouvrira automatiquement sur `http://localhost:5500`

### Option 2 : XAMPP (Serveur Local)

1. **Démarrer XAMPP** :
   - Ouvrez le panneau de contrôle XAMPP
   - Cliquez sur "Start" pour Apache

2. **Accéder au portfolio** :
   - Ouvrez votre navigateur
   - Allez à : `http://localhost/Portfolio/`

### Option 3 : Serveur Python (Python 3)

```bash
# Naviguer dans le dossier du projet
cd C:\xampp\htdocs\Portfolio

# Lancer un serveur Python
python -m http.server 8000

# Accéder à http://localhost:8000
```

### Option 4 : Node.js avec http-server

```bash
# Installer http-server globalement (une seule fois)
npm install -g http-server

# Naviguer dans le dossier et lancer
cd C:\xampp\htdocs\Portfolio
http-server

# Accéder à http://localhost:8080
```

---

## ✨ Fonctionnalités

### 🎨 Design & UX
- ✅ **Responsive** : Mobile-first, tablette et desktop
- ✅ **Variables CSS** : Couleurs, espacements, ombres centralisées
- ✅ **Gradients** : Beaux dégradés linéaires
- ✅ **Animations** : Transitions fluides et animations au scroll
- ✅ **Icônes** : Emojis pour une touche visuelle

### ♿ Accessibilité
- ✅ **Navigation au clavier** : Tab, Entrée, Échap
- ✅ **ARIA labels** : Descriptions pour lecteurs d'écran
- ✅ **Contraste élevé** : Ratio conforme WCAG AA
- ✅ **Texte alt** : Tous les images ont des descriptions
- ✅ **Focus visible** : Outline clairs sur les éléments focusables
- ✅ **Prefers reduced motion** : Support des animations réduites
- ✅ **Mode sombre** : Support `prefers-color-scheme: dark`

### 🔧 Interactivité
- ✅ **Menu mobile** : Hamburger menu responsive
- ✅ **Filtrage des projets** : Boutons de catégories avec animation
- ✅ **Modal de projets** : Popup pour voir les détails avec lien GitHub/démo
- ✅ **Smooth scroll** : Navigation fluide vers les sections
- ✅ **Scroll to top** : Bouton flottant pour remonter
- ✅ **Lazy loading** : Images chargées à la demande
- ✅ **Active link** : Surligne le lien de la section active

### 📱 Sections

#### 1. **Hero** (Accueil)
- Titre principal "Oscar NGUEMA"
- Subtitle "Développeur BTS SIO SLAM"
- Description courte
- Deux boutons d'action : "Voir mes projets" et "Me contacter"
- Image/illustration responsive

#### 2. **Parcours** (Timeline)
- 2018-2019 : Baccalauréat général - Lycée René Descartes
- 2020-2021 : BTS SIO 1ère année - ITIC Paris
- 2025-2026 : BTS SIO 2ème année (SLAM) - IRIS Paris
- 2026+ : Objectifs futurs

#### 3. **Compétences**
- Frontend (HTML5, CSS3, JavaScript, Responsive)
- Backend (PHP, SQL, MVC, APIs)
- Langages (Java, C++, JavaScript, PHP)
- Outils (WordPress, Shopify, Git, VS Code)

#### 4. **Projets**
- **Filtres** : Tous, Jeux, Web, E-commerce, Applications
- **5 Projets** :
  1. Snake (Jeu JavaScript)
  2. Boutique Shopify (E-commerce)
  3. Site E-commerce Custom (Fullstack)
  4. Auto-école Client Lourd (Java)
  5. Auto-école Client Léger (Web)

- **Modal** : Cliquer sur "Voir le projet" affiche les détails avec :
  - Description complète
  - Fonctionnalités
  - Technologies utilisées
  - Acquis du projet
  - Liens vers GitHub et démo

#### 5. **Veille Technologique**
- Web Performance (Core Web Vitals)
- Sécurité Web (OWASP)
- Accessibilité (WCAG)
- Frontend Moderne (Frameworks, PWA)

#### 6. **Contact**
- Email (oscar.nguema@email.com)
- Téléphone (+33 6 12 34 56 78)
- Liens GitHub, LinkedIn
- Téléchargement du CV (PDF)

---

## 🎯 Données Personnalisables

Pour adapter le portfolio à vos besoins, modifiez ces zones :

### Dans `index.html` :
- **Nom & titre** : Ligne ~60-62
- **Description** : Ligne ~63-65
- **Email** : Ligne ~395 et ~442
- **Téléphone** : Ligne ~397 et ~445
- **GitHub/LinkedIn** : Ligne ~449-450
- **Parcours** : Lignes ~119-167
- **Compétences** : Lignes ~196-242
- **Projets** : Lignes ~279-406
- **Veille** : Lignes ~430-460

### Dans `script.js` :
- **Détails des projets** : Ligne ~126-231 (objet `projectsData`)
- **Liens GitHub & démo** : Modifiez les URLs dans chaque projet

### Dans `style.css` :
- **Couleurs primaires** : Lignes ~4-10 (variables CSS)
- **Fonts** : Ligne ~54 (système de fonts)
- **Espacements** : Lignes ~20-30

---

## 🛠️ Technologies

### Frontend
- **HTML5** : Structure sémantique
- **CSS3** : Flexbox, Grid, Variables CSS, Media Queries
- **JavaScript ES6+** : Vanilla JS (zéro dépendance)

### Accessible à Tous
- **WCAG 2.1** : Niveaux AA conformes
- **ARIA** : Labels et roles pour lecteurs d'écran
- **SEO** : Meta tags et structure sémantique

---

## 📱 Points de Rupture (Breakpoints)

- **Desktop** : 1200px+
- **Tablette** : 768px - 1199px
- **Mobile** : < 480px

---

## 🎨 Système de Couleurs

| Couleur | Variable | Utilisation |
|---------|----------|-------------|
| Bleu Primaire | `--primary` | Boutons, liens, accents |
| Bleu Sombre | `--primary-dark` | Hover, backgrounds |
| Rouge Secondaire | `--secondary` | Badges, accents |
| Jaune Accent | `--accent` | Highlights, éléments importants |
| Blanc | `--white` | Texte léger, backgrounds |
| Gris | `--gray-*` | Texte neutre, bordures |

---

## 📄 Fichiers à Remplacer

Pour personnaliser :

1. **Images** : Remplacez les fichiers `.jpg` dans `assets/img/`
2. **CV** : Replacez le PDF dans `assets/cv.pdf`
3. **Lien GitHub** : Modifiez les URLs dans `script.js`

---

## 🐛 Troubleshooting

**Q: Les images ne s'affichent pas**
→ Vérifiez que le serveur est lancé et que les chemins dans `assets/img/` sont corrects

**Q: La modal ne s'ouvre pas**
→ Ouvrez la console (F12) et vérifiez qu'il n'y a pas d'erreurs JavaScript

**Q: Le menu mobile ne fonctionne pas**
→ Vérifiez que `script.js` est bien lié dans l'HTML et qu'il n'y a pas d'erreurs

**Q: Les styles ne s'appliquent pas**
→ Videz le cache du navigateur (Ctrl+Shift+Del ou Cmd+Shift+Del)

---

## 📚 Ressources Utilisées

- Inspiration : [monportfolioadel.com](https://monportfolioadel.com)
- CSS Variables : [MDN CSS Custom Properties](https://developer.mozilla.org/en-US/docs/Web/CSS/--*)
- Accessibilité : [WAI-ARIA Practices](https://www.w3.org/WAI/ARIA/apg/)
- Responsive Design : [Mobile First Approach](https://www.w3.org/MobileWeb/)

---

## 📝 Licence

Ce portfolio est à usage personnel. Libre d'adaptation.

---

## 👨‍💻 Auteur

**Oscar NGUEMA**
- Développeur BTS SIO SLAM
- Email : oscar.nguema@email.com
- GitHub : [github.com/oscarnguema](https://github.com)
- LinkedIn : [linkedin.com/in/oscarnguema](https://linkedin.com)

---

**Version** : 1.0
**Dernière mise à jour** : Février 2026
**Navigateurs supportés** : Chrome, Firefox, Safari, Edge (versions récentes)
