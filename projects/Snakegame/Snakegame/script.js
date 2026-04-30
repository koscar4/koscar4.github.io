// Configuration du jeu
const GRID_SIZE = 20; // Taille de chaque cellule
const CANVAS_SIZE = 400;
const GRID_COUNT = CANVAS_SIZE / GRID_SIZE;

// Éléments du DOM
const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');
const scoreDisplay = document.getElementById('score');
const bestScoreDisplay = document.getElementById('bestScore');
const levelDisplay = document.getElementById('level');
const startBtn = document.getElementById('startBtn');
const pauseBtn = document.getElementById('pauseBtn');
const resetBtn = document.getElementById('resetBtn');

// État du jeu
let snake = [{ x: 10, y: 10 }];
let apple = { x: 15, y: 15 };
let direction = { x: 1, y: 0 };
let nextDirection = { x: 1, y: 0 };
let score = 0;
let bestScore = localStorage.getItem('snakeBestScore') || 0;
let gameRunning = false;
let gamePaused = false;
let gameSpeed = 100; // millisecondes entre chaque mouvement
let appleCount = 0; // Nombre de pommes mangées

// Initialisation
bestScoreDisplay.textContent = bestScore;

// Événements des boutons
startBtn.addEventListener('click', startGame);
pauseBtn.addEventListener('click', togglePause);
resetBtn.addEventListener('click', resetGame);

// Contrôles au clavier
document.addEventListener('keydown', handleKeyPress);

function handleKeyPress(e) {
    if (!gameRunning || gamePaused) return;

    // Flèches
    if (e.key === 'ArrowUp' && direction.y === 0) {
        nextDirection = { x: 0, y: -1 };
        e.preventDefault();
    }
    if (e.key === 'ArrowDown' && direction.y === 0) {
        nextDirection = { x: 0, y: 1 };
        e.preventDefault();
    }
    if (e.key === 'ArrowLeft' && direction.x === 0) {
        nextDirection = { x: -1, y: 0 };
        e.preventDefault();
    }
    if (e.key === 'ArrowRight' && direction.x === 0) {
        nextDirection = { x: 1, y: 0 };
        e.preventDefault();
    }

    // WASD
    if ((e.key === 'w' || e.key === 'W') && direction.y === 0) {
        nextDirection = { x: 0, y: -1 };
    }
    if ((e.key === 's' || e.key === 'S') && direction.y === 0) {
        nextDirection = { x: 0, y: 1 };
    }
    if ((e.key === 'a' || e.key === 'A') && direction.x === 0) {
        nextDirection = { x: -1, y: 0 };
    }
    if ((e.key === 'd' || e.key === 'D') && direction.x === 0) {
        nextDirection = { x: 1, y: 0 };
    }

    // Espace pour pause
    if (e.key === ' ') {
        togglePause();
        e.preventDefault();
    }
}

function startGame() {
    if (!gameRunning) {
        gameRunning = true;
        gamePaused = false;
        startBtn.disabled = true;
        pauseBtn.disabled = false;
        gameLoop();
    }
}

function togglePause() {
    if (!gameRunning) return;
    
    gamePaused = !gamePaused;
    pauseBtn.textContent = gamePaused ? 'Reprendre' : 'Pause';
    
    if (!gamePaused) {
        gameLoop();
    }
}

function resetGame() {
    snake = [{ x: 10, y: 10 }];
    direction = { x: 1, y: 0 };
    nextDirection = { x: 1, y: 0 };
    score = 0;
    appleCount = 0;
    gameSpeed = 100;
    gameRunning = false;
    gamePaused = false;
    
    scoreDisplay.textContent = score;
    levelDisplay.textContent = 1;
    startBtn.disabled = false;
    pauseBtn.disabled = true;
    pauseBtn.textContent = 'Pause';
    
    spawnApple();
    draw();
}

function spawnApple() {
    let newApple;
    let validPosition = false;

    while (!validPosition) {
        newApple = {
            x: Math.floor(Math.random() * GRID_COUNT),
            y: Math.floor(Math.random() * GRID_COUNT)
        };

        // Vérifier que la pomme n'apparaît pas sur le serpent
        validPosition = !snake.some(segment => segment.x === newApple.x && segment.y === newApple.y);
    }

    apple = newApple;
}

function gameLoop() {
    if (!gameRunning || gamePaused) return;

    // Appliquer la direction suivante
    direction = { ...nextDirection };

    // Calculer la nouvelle tête
    const head = { ...snake[0] };
    head.x += direction.x;
    head.y += direction.y;

    // Vérifier les collisions avec les murs
    if (head.x < 0 || head.x >= GRID_COUNT || head.y < 0 || head.y >= GRID_COUNT) {
        endGame();
        return;
    }

    // Vérifier la collision avec le corps du serpent
    if (snake.some(segment => segment.x === head.x && segment.y === head.y)) {
        endGame();
        return;
    }

    // Ajouter la nouvelle tête
    snake.unshift(head);

    // Vérifier si la pomme est mangée
    if (head.x === apple.x && head.y === apple.y) {
        score += 10;
        appleCount++;
        scoreDisplay.textContent = score;

        // Augmenter la difficulté tous les 5 pommes
        const level = Math.floor(appleCount / 5) + 1;
        levelDisplay.textContent = level;
        gameSpeed = Math.max(50, 100 - level * 10); // Accélération progressive

        spawnApple();
    } else {
        // Enlever la queue si pas de pomme mangée
        snake.pop();
    }

    draw();
    setTimeout(gameLoop, gameSpeed);
}

function draw() {
    // Effacer le canvas
    ctx.fillStyle = '#1a1a1a';
    ctx.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

    // Dessiner la grille (optionnel)
    ctx.strokeStyle = '#333';
    ctx.lineWidth = 0.5;
    for (let i = 0; i <= GRID_COUNT; i++) {
        ctx.beginPath();
        ctx.moveTo(i * GRID_SIZE, 0);
        ctx.lineTo(i * GRID_SIZE, CANVAS_SIZE);
        ctx.stroke();

        ctx.beginPath();
        ctx.moveTo(0, i * GRID_SIZE);
        ctx.lineTo(CANVAS_SIZE, i * GRID_SIZE);
        ctx.stroke();
    }

    // Dessiner le serpent
    snake.forEach((segment, index) => {
        if (index === 0) {
            // Tête du serpent en vert plus brillant
            ctx.fillStyle = '#00ff00';
            ctx.shadowColor = '#00ff00';
            ctx.shadowBlur = 10;
        } else {
            // Corps du serpent en vert plus foncé
            ctx.fillStyle = '#00cc00';
            ctx.shadowColor = 'rgba(0, 255, 0, 0.5)';
            ctx.shadowBlur = 5;
        }

        ctx.fillRect(
            segment.x * GRID_SIZE + 1,
            segment.y * GRID_SIZE + 1,
            GRID_SIZE - 2,
            GRID_SIZE - 2
        );
    });

    ctx.shadowColor = 'transparent';

    // Dessiner la pomme
    ctx.fillStyle = '#ff4444';
    ctx.shadowColor = '#ff4444';
    ctx.shadowBlur = 10;
    const appleCenterX = apple.x * GRID_SIZE + GRID_SIZE / 2;
    const appleCenterY = apple.y * GRID_SIZE + GRID_SIZE / 2;
    ctx.beginPath();
    ctx.arc(appleCenterX, appleCenterY, GRID_SIZE / 2 - 2, 0, Math.PI * 2);
    ctx.fill();
    ctx.shadowColor = 'transparent';

    // Dessiner la tige de la pomme
    ctx.strokeStyle = '#ff8800';
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.moveTo(appleCenterX, appleCenterY - GRID_SIZE / 2 + 2);
    ctx.lineTo(appleCenterX, appleCenterY - GRID_SIZE / 2 - 3);
    ctx.stroke();

    // Afficher "PAUSE" si en pause
    if (gamePaused) {
        ctx.fillStyle = 'rgba(0, 0, 0, 0.7)';
        ctx.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        ctx.fillStyle = '#fff';
        ctx.font = 'bold 40px Arial';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.fillText('PAUSE', CANVAS_SIZE / 2, CANVAS_SIZE / 2);
    }
}

function endGame() {
    gameRunning = false;
    gamePaused = false;
    startBtn.disabled = false;
    pauseBtn.disabled = true;
    pauseBtn.textContent = 'Pause';

    // Mettre à jour le meilleur score
    if (score > bestScore) {
        bestScore = score;
        localStorage.setItem('snakeBestScore', bestScore);
        bestScoreDisplay.textContent = bestScore;
    }

    // Afficher le message de game over avec le score final
    setTimeout(() => {
        const level = Math.floor(appleCount / 5) + 1;
        alert(`Jeu terminé!\n\nScore final: ${score}\nNiveau atteint: ${level}\nPommes mangées: ${appleCount}\nMeilleur score: ${bestScore}`);
    }, 100);

    draw();
}

// Initialisation du jeu
resetGame();
