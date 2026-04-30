# 📋 Guide de Personnalisation - Portfolio Oscar NGUEMA

## 🎯 Sections à Personnaliser

### 1️⃣ IDENTITÉ PERSONNELLE

#### Modifier le nom et le titre hero
**Fichier** : `index.html` (lignes ~60-65)

```html
<!-- AVANT -->
<h1>Oscar NGUEMA</h1>
<p class="hero-subtitle">Développeur BTS SIO SLAM</p>
<p class="hero-description">
    Passionné par le développement web et les technologies modernes.
    Créateur de solutions numériques performantes et accessibles.
</p>

<!-- APRÈS (exemple) -->
<h1>Votre Nom</h1>
<p class="hero-subtitle">Votre Titre Professionnel</p>
<p class="hero-description">
    Votre description personnelle...
</p>
```

---

### 2️⃣ PARCOURS / TIMELINE

**Fichier** : `index.html` (lignes ~119-167)

Chaque élément de la timeline a cette structure :

```html
<article class="timeline-item">
    <div class="timeline-marker"></div>
    <div class="timeline-content">
        <time dateTime="2018-09/2019-06">2018 - 2019</time>
        <h3>Baccalauréat général</h3>
        <p class="institution">Lycée René Descartes — Gabon</p>
        <p>Formation générale avec un intérêt marqué pour les sciences et les technologies.</p>
    </div>
</article>
```

**À modifier** :
- `dateTime` : Date formatée
- `<time>` : Années affichées
- `<h3>` : Titre du diplôme/formation
- `.institution` : Établissement
- Texte descriptif

---

### 3️⃣ COMPÉTENCES

**Fichier** : `index.html` (lignes ~196-242)

Chaque carte de compétence :

```html
<article class="competence-card">
    <div class="competence-icon">🎨</div>
    <h3>Frontend</h3>
    <ul class="skills-list">
        <li>HTML5 / CSS3</li>
        <li>JavaScript ES6+</li>
        <li>Responsive Design</li>
        <li>Accessibilité Web</li>
    </ul>
</article>
```

**À modifier** :
- `.competence-icon` : Emoji (🎨, ⚙️, 💻, 🛠️, etc.)
- `<h3>` : Catégorie de compétence
- `<li>` : Chaque compétence individuelle

**Ajouter une nouvelle catégorie** :
```html
<article class="competence-card">
    <div class="competence-icon">🚀</div>
    <h3>DevOps</h3>
    <ul class="skills-list">
        <li>Docker</li>
        <li>Kubernetes</li>
        <li>CI/CD</li>
    </ul>
</article>
```

---

### 4️⃣ PROJETS

#### A. Ajouter un projet en HTML
**Fichier** : `index.html` (lignes ~279-406)

```html
<article class="project-card" data-category="Web">
    <div class="project-image">
        <img src="assets/img/mon-projet.jpg" alt="Description du projet" loading="lazy">
    </div>
    <div class="project-content">
        <span class="project-badge">Web</span>
        <h3>Mon Nouveau Projet</h3>
        <p class="project-description">
            Description courte du projet...
        </p>
        <div class="project-tech">
            <span class="tech-badge">Tech 1</span>
            <span class="tech-badge">Tech 2</span>
        </div>
        <div class="project-acquis">
            <p><strong>Acquis :</strong> Compétences apprises...</p>
        </div>
        <button class="btn btn-small" data-project="mon-projet" aria-label="Voir les détails">
            Voir le projet
        </button>
    </div>
</article>
```

**Important** :
- `data-category` : Doit correspondre aux filtres (Jeux, Web, E-commerce, Applications)
- `data-project` : ID unique pour la modal
- Créer l'image : `assets/img/mon-projet.jpg`

#### B. Ajouter les détails dans la modal
**Fichier** : `script.js` (lignes ~126-231)

```javascript
const projectsData = {
    'mon-projet': {
        title: 'Titre du Projet',
        description: `
            <h3>Titre</h3>
            <p><strong>Contexte :</strong> ...</p>
            
            <p><strong>Fonctionnalités :</strong></p>
            <ul>
                <li>Feature 1</li>
                <li>Feature 2</li>
            </ul>
            
            <p><strong>Technologies utilisées :</strong> Tech 1, Tech 2</p>
            
            <p><strong>Acquis :</strong> Compétences apprises</p>
        `,
        demo: 'https://lien-demo.com',
        github: 'https://github.com/user/projet'
    }
};
```

#### C. Ajouter/Modifier les catégories de filtre
**Fichier** : `index.html` (lignes ~260-270)

```html
<div class="filter-buttons" role="group" aria-label="Filtrer les projets">
    <button class="filter-btn active" data-filter="tous">Tous</button>
    <button class="filter-btn" data-filter="MaNouvelleCat">Ma Catégorie</button>
    <!-- Ajouter d'autres boutons -->
</div>
```

---

### 5️⃣ VEILLE TECHNOLOGIQUE

**Fichier** : `index.html` (lignes ~430-460)

```html
<article class="veille-card">
    <div class="veille-icon">⚡</div>
    <h3>Web Performance</h3>
    <p>Optimisation de la performance web, Core Web Vitals et techniques de chargement rapide.</p>
    <a href="#" class="veille-link">Lire plus →</a>
</article>
```

**À modifier** :
- `.veille-icon` : Emoji pertinent
- `<h3>` : Titre du sujet
- `<p>` : Description
- `href="#"` : Remplacer par lien réel (blog article, ressource, etc.)

---

### 6️⃣ CONTACT

**Fichier** : `index.html` (lignes ~469-489)

```html
<li>
    <strong>Email :</strong>
    <a href="mailto:oscar.nguema@email.com">oscar.nguema@email.com</a>
</li>
<li>
    <strong>Téléphone :</strong>
    <a href="tel:+33612345678">+33 6 12 34 56 78</a>
</li>
```

**À modifier** :
- Email : Remplacer par votre email
- Téléphone : Remplacer par votre numéro
- GitHub URL : Ligne ~454
- LinkedIn URL : Ligne ~459

---

## 🎨 PERSONNALISATION DU DESIGN

### Modifier les Couleurs
**Fichier** : `style.css` (lignes ~4-10)

```css
:root {
    /* Couleurs */
    --primary: #6b63ff;           /* Couleur principale - Modifiez ici */
    --primary-dark: #5a52d0;      /* Couleur principale foncée */
    --secondary: #ff6b6b;         /* Couleur secondaire */
    --accent: #ffd93d;            /* Couleur d'accent */
    /* ... */
}
```

**Exemples de couleurs** :
- Bleu : `#0066cc`, `#0056b3`
- Rose : `#ff1493`, `#ff69b4`
- Vert : `#10b981`, `#059669`
- Orange : `#ff8c00`, `#ff7c00`

### Modifier les Polices
**Fichier** : `style.css` (ligne ~54)

```css
body {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Helvetica Neue', Arial, sans-serif;
}
```

Remplacer par :
```css
body {
    font-family: 'Poppins', 'Segoe UI', sans-serif;
}
```

Puis ajouter en `<head>` du `index.html` :
```html
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
```

### Modifier les Espacements
**Fichier** : `style.css` (lignes ~20-30)

```css
:root {
    --spacing-xs: 0.25rem;
    --spacing-sm: 0.5rem;
    --spacing-md: 1rem;
    --spacing-lg: 1.5rem;
    --spacing-xl: 2rem;
    --spacing-2xl: 3rem;
    --spacing-3xl: 4rem;
}
```

### Modifier les Ombres
**Fichier** : `style.css` (lignes ~36-39)

```css
--shadow-sm: 0 1px 2px 0 rgb(0 0 0 / 0.05);
--shadow-md: 0 4px 6px -1px rgb(0 0 0 / 0.1);
--shadow-lg: 0 10px 15px -3px rgb(0 0 0 / 0.1);
```

---

## 📸 IMAGES

### Remplacer l'image Hero
1. Préparez votre image : **1200x600px minimum**
2. Sauvegardez-la dans : `assets/img/hero-image.jpg`
3. Ou changez le lien dans `index.html` ligne ~76

### Remplacer les images des projets
1. Préparez 5 images : **400x250px**
2. Sauvegardez-les dans `assets/img/` :
   - `projet-snake.jpg`
   - `projet-shopify.jpg`
   - `projet-ecommerce.jpg`
   - `projet-client-lourd.jpg`
   - `projet-client-leger.jpg`

### Outils gratuits pour créer des images
- **Figma** : figma.com (gratuit)
- **Canva** : canva.com
- **Unsplash** : unsplash.com (photos libres)
- **Pexels** : pexels.com (photos libres)

---

## 📄 REMPLACER LE CV

1. Créez votre CV en PDF
2. Sauvegardez-le dans : `assets/cv.pdf`
3. Le lien est déjà présent dans le contact

---

## 🔗 LIENS À METTRE À JOUR

**Fichier** : `index.html` + `script.js`

| Élément | Fichier | Ligne | À faire |
|---------|---------|-------|---------|
| Email | index.html | 442, 395 | `mailto:` |
| Téléphone | index.html | 445, 397 | `tel:` |
| GitHub | index.html | 454 | URL profil GitHub |
| LinkedIn | index.html | 459 | URL profil LinkedIn |
| Démos projets | script.js | ~150-230 | URL démos |
| Repos GitHub | script.js | ~150-230 | URL GitHub projets |

---

## ♿ ACCESSIBILITÉ

### Textes alt pour images
```html
<img src="assets/img/hero-image.jpg" alt="Portrait d'Oscar NGUEMA" loading="lazy">
```

**Bon alt** : Descriptif, concis, pertinent
**Mauvais alt** : "image", "photo", ou vide

### Labels pour formulaires (optionnel si vous en ajoutez)
```html
<label for="name">Votre nom :</label>
<input type="text" id="name">
```

### Contraste des couleurs
Vérifiez avec : [contrastchecker.com](https://webaim.org/resources/contrastchecker/)

---

## ✅ CHECKLIST DE PERSONNALISATION

- [ ] Modifier le nom et titre hero
- [ ] Mettre à jour le parcours
- [ ] Ajuster les compétences
- [ ] Modifier/ajouter les projets
- [ ] Ajouter les détails des projets dans la modal
- [ ] Mettre à jour les liens GitHub et démo
- [ ] Remplacer les images
- [ ] Vérifier le CV (PDF)
- [ ] Mettre à jour email et téléphone
- [ ] Ajouter vos URLs GitHub/LinkedIn
- [ ] Adapter les couleurs si souhaité
- [ ] Tester sur mobile
- [ ] Vérifier l'accessibilité
- [ ] Faire un test complet

---

## 🐛 TIPS & TRICKS

### Camoufler une section
```css
section#veille {
    display: none;
}
```

### Changer rapidement les couleurs
Ouvrez la console du navigateur (F12) et modifiez directement :
```javascript
document.documentElement.style.setProperty('--primary', '#ff0000');
```

### Tester l'accessibilité
- Appuyez sur `Tab` pour naviguer au clavier
- Ouvrez la console avec F12 → Lightho use (Chrome)
- Utilisez un lecteur d'écran (NVDA gratuit)

### Faire un screenshot du site
- Firefox : Clic droit → Prendre une capture
- Chrome : Clic droit → Inspecter → ⋮ → Capture d'écran

---

## 🚀 PROCHAINES ÉTAPES AVANCÉES

1. **Ajouter un formulaire de contact** (PHP backend)
2. **Blog section** (Articles de veille)
3. **Animations avancées** (GSAP, Framer Motion)
4. **Dark mode toggle** (JavaScript)
5. **Multi-langue** (i18n)
6. **Déploiement** (Netlify, Vercel, GitHub Pages)

---

**Version** : 1.0
**Dernier update** : Février 2026
