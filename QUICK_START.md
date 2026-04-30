# ⚡ QUICK START - Démarrage Rapide en 5 Minutes

## 🎯 Objectif
Lancer votre portfolio en local et le voir fonctionner instantanément.

---

## 📦 Ce Que Vous Avez

```
✅ 3 fichiers essentiels : index.html, style.css, script.js
✅ Dossier assets avec images et CV
✅ 6 sections complètes : Hero, Parcours, Compétences, Projets, Veille, Contact
✅ Accessibilité WCAG 2.1 AA
✅ Responsive mobile-first
✅ Sans dépendances externes
```

---

## 🚀 Étape 1 : Lancer le Serveur (30 secondes)

### Option A : Live Server VS Code (RECOMMANDÉ ⭐)

```bash
1. Ouvrir VS Code
2. Aller dans Extensions (Ctrl+Shift+X)
3. Chercher "Live Server" par Ritwick Dey
4. Installer
5. Ouvrir index.html
6. Clic droit → "Open with Live Server"
7. ✅ Navigateur s'ouvre automatiquement sur http://localhost:5500
```

### Option B : XAMPP

```bash
1. Ouvrir XAMPP Control Panel
2. Cliquer "Start" sur Apache
3. Ouvrir navigateur → http://localhost/Portfolio/
4. ✅ Portfolio s'affiche
```

### Option C : Python 3

```bash
cd C:\xampp\htdocs\Portfolio
python -m http.server 8000
# Puis : http://localhost:8000
```

---

## ✨ Étape 2 : Vérifier Que Tout Fonctionne (1 minute)

**Checklist rapide** :

- [ ] Page charge sans erreur
- [ ] Navigation en haut sticky
- [ ] Clic sur "Voir mes projets" → scroll vers projets
- [ ] Clic sur filtre "Jeux" → projets filtrés
- [ ] Clic "Voir le projet" → modal s'ouvre
- [ ] Clic X sur modal → modal ferme
- [ ] Menu hamburger sur mobile (redimensionner fenêtre)
- [ ] Tous les textes lisibles

**Ouvrir Console** (F12) :
- [ ] Aucune erreur rouge
- [ ] Message "Portfolio Oscar NGUEMA" affiché

---

## ✏️ Étape 3 : Première Personnalisation (2 minutes)

### Changer le nom

**Fichier** : `index.html` ligne 60

```html
<!-- AVANT -->
<h1>Oscar NGUEMA</h1>

<!-- APRÈS -->
<h1>Votre Nom</h1>
```

**Sauvegarder** : Ctrl+S
**Navigateur** : F5 (rafraîchir) → Voilà!

### Changer le subtitle

**Fichier** : `index.html` ligne 61

```html
<p class="hero-subtitle">Développeur BTS SIO SLAM</p>
<!-- Remplacer par votre titre -->
```

### Changer email et téléphone

**Fichier** : `index.html` lignes ~395 et ~397

```html
<a href="mailto:oscar.nguema@email.com">oscar.nguema@email.com</a>
<a href="tel:+33612345678">+33 6 12 34 56 78</a>
```

---

## 🎨 Étape 4 : Changer les Couleurs (1 minute)

**Fichier** : `style.css` ligne 4

```css
:root {
    --primary: #6b63ff;        /* ← Couleur principale */
    --primary-dark: #5a52d0;
    --secondary: #ff6b6b;
    --accent: #ffd93d;
}
```

**Exemples** :
- Rose : `--primary: #ff1493;` `--primary-dark: #ff69b4;`
- Vert : `--primary: #10b981;` `--primary-dark: #059669;`
- Orange : `--primary: #ff8c00;` `--primary-dark: #ff7c00;`

**Effet immédiat** : Tous les boutons, liens et accents changent!

---

## 📸 Étape 5 : Remplacer une Image (1 minute)

### Image Hero

1. Préparez votre image (recommandé: 1200x600px)
2. Sauvegardez dans : `assets/img/hero-image.jpg`
3. Rafraîchir le navigateur
4. ✅ Nouvelle image s'affiche

### Images Projets

1. Préparez 6 images (400x250px recommandé)
2. Remplacez dans `assets/img/` :
   - `projet-snake.jpg`
   - `projet-shopify.jpg`
   - `projet-ecommerce.jpg`
   - `projet-client-lourd.jpg`
   - `projet-client-leger.jpg`
3. Rafraîchir
4. ✅ Nouvelles images visibles

---

## 🎯 Les 5 Points d'Entrée Clés

| Quoi ? | Où ? | Ligne | Exemple |
|--------|------|-------|---------|
| **Nom & titre** | index.html | 60-61 | `Oscar NGUEMA` |
| **Parcours** | index.html | 119-167 | Dates + écoles |
| **Compétences** | index.html | 196-242 | Langages, outils |
| **Projets** | index.html | 279-406 | + script.js 126-231 |
| **Contact** | index.html | 395-450 | Email, téléphone |

---

## ✅ Fichiers Créés

```
✅ index.html                  (411 lignes - HTML sémantique)
✅ style.css                   (960 lignes - CSS responsive)
✅ script.js                   (400 lignes - JavaScript vanilla)

✅ assets/cv.pdf               (CV)
✅ assets/img/hero-image.jpg   (Image hero)
✅ assets/img/projet-*.jpg     (5 images projets)

✅ README.md                   (Documentation complète)
✅ PERSONNALISATION.md         (Guide de customisation)
✅ STRUCTURE.md                (Architecture détaillée)
✅ QUICK_START.md              (Ce fichier)
```

---

## 🐛 Petits Problèmes & Solutions

### ❌ Les styles ne s'appliquent pas
```
→ Videz le cache (Ctrl+Shift+Del)
→ Forcez rechargement (Ctrl+F5)
```

### ❌ Images ne s'affichent pas
```
→ Vérifiez le chemin : assets/img/nom.jpg
→ Vérifiez les permissions du fichier
→ Essayez avec http (pas file://)
```

### ❌ Menu mobile ne fonctionne pas
```
→ Ouvrez F12 → Console
→ Cherchez erreurs JavaScript
→ Vérifiez que script.js est chargé
```

### ❌ Modal de projet vide
```
→ Cherchez le projectKey dans script.js
→ Vérifiez les données dans projectsData
→ Ouvrez Console pour voir erreurs
```

---

## 📱 Tester le Responsive

**VS Code** :
1. Ouvrir DevTools (F12)
2. Cliquer sur icône mobile (Ctrl+Shift+M)
3. Choisir "iPhone 12 Pro" ou autre
4. Voir le site en mobile

**Résultat attendu** :
- ✅ Menu devient hamburger
- ✅ Sections en colonne unique
- ✅ Textes restent lisibles
- ✅ Boutons clickables

---

## 🔗 Liens à Mettre à Jour

```html
<!-- 1. Email -->
<a href="mailto:oscar.nguema@email.com">
→ Remplacer par votre email

<!-- 2. Téléphone -->
<a href="tel:+33612345678">
→ Remplacer par votre téléphone

<!-- 3. GitHub -->
<a href="https://github.com">
→ Remplacer par votre profil GitHub

<!-- 4. LinkedIn -->
<a href="https://linkedin.com">
→ Remplacer par votre profil LinkedIn
```

---

## 📊 Exemple de Modification Complète

### Ajouter un 6ème Projet

**1. HTML** (index.html ligne ~406) :
```html
<article class="project-card" data-category="Web">
    <div class="project-image">
        <img src="assets/img/mon-nouveau-projet.jpg" alt="Mon projet">
    </div>
    <div class="project-content">
        <span class="project-badge">Web</span>
        <h3>Mon Nouveau Projet</h3>
        <p class="project-description">Description...</p>
        <div class="project-tech">
            <span class="tech-badge">React</span>
            <span class="tech-badge">Node.js</span>
        </div>
        <div class="project-acquis">
            <p><strong>Acquis :</strong> Apprentissages...</p>
        </div>
        <button class="btn btn-small" data-project="mon-nouveau">Voir</button>
    </div>
</article>
```

**2. JavaScript** (script.js ligne ~231) :
```javascript
'mon-nouveau': {
    title: 'Mon Nouveau Projet',
    description: `
        <h3>Titre</h3>
        <p><strong>Contexte :</strong> ...</p>
        <p><strong>Technos :</strong> React, Node.js</p>
    `,
    demo: 'https://lien-demo.com',
    github: 'https://github.com/user/repo'
}
```

**3. Image** (assets/img/) :
- Créer/télécharger `mon-nouveau-projet.jpg`

**Voilà!** 6ème projet ajouté ✅

---

## 🎓 Apprendre en Faisant

**Explorez le code** :

1. Ouvrez `style.css`
2. Cherchez `--primary: #6b63ff;`
3. Changez en `#ff0000` (rouge)
4. Sauvegardez (Ctrl+S)
5. Rafraîchissez (F5)
6. 🎉 Tout devient rouge!

**Ça montre** :
- Les variables CSS et leur impact
- Le hot-reload avec Live Server
- La cascade CSS en action

---

## 🚀 Prochaines Étapes

**Une fois satisfait** :

1. **Remplacez toutes les images** avec vos vraies images
2. **Mettez à jour tous les textes** avec vos données
3. **Déployez** sur Netlify ou GitHub Pages (gratuit)
4. **Partagez** votre portfolio!

---

## 📚 Ressources

| Besoin | Lien |
|--------|------|
| Images gratuites | unsplash.com, pexels.com |
| Couleurs | coolors.co, colorhunt.co |
| Éditeur | VS Code, Sublime Text |
| Validation HTML | validator.w3.org |
| Tester accessibilité | webaim.org/resources/contrastchecker |

---

## ✨ Astuce Pro

**Cachez des sections** :
```css
/* Dans style.css, ajouter en bas : */

/* Cacher la section veille */
#veille {
    display: none;
}

/* Cacher le footer */
.footer {
    display: none;
}
```

---

## 🎉 Félicitations!

Vous avez maintenant un **portfolio professionnel complet** :
- ✅ Responsive
- ✅ Accessible
- ✅ Sans dépendances
- ✅ Moderne
- ✅ Personnalisable

**Prochain objectif** : Mettre en ligne et l'envoyer aux recruteurs!

---

**Durée totale** : ~5 minutes ⏱️
**Niveau** : Débutant à Intermédiaire ⭐
**Support** : README.md + PERSONNALISATION.md

Bonne chance! 🚀
