/************************************************************
 * JEU DU SERPENT (SNAKE) — Version C pure
 *
 * Regles :
 *   - 3 pommes generees a chaque round
 *   - Le serpent grandit (+3) apres avoir mange les 3 pommes
 *   - Fin si collision mur ou queue
 *   - Sauvegarde / reprise de partie (fichier binaire)
 *   - Mode Ordinateur avec 2 vitesses (lente / rapide)
 *
 * Compilation : gcc snake.c -o snake.exe
 * Plateforme  : Windows (conio.h, windows.h)
 ************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
#include <conio.h>
#include <windows.h>

/* =========================================================
   CONSTANTES
   ========================================================= */
#define W         42          /* largeur terrain (bordures incluses) */
#define H         22          /* hauteur terrain (bordures incluses) */
#define NAPPLES    3          /* nombre de pommes par round          */
#define MAXBODY  (W * H)     /* longueur max du serpent             */
#define SP_SLOW  220          /* ms / tick en mode lent              */
#define SP_FAST   90          /* ms / tick en mode rapide            */
#define SAVEFILE "snake_save.dat"

/* =========================================================
   TYPES
   ========================================================= */
typedef enum { NONE=0, LEFT, RIGHT, UP, DOWN } Dir;

typedef struct { int x, y; }          Pt;
typedef struct { int x, y, active; }  Apple;

typedef struct {
    Pt    body[MAXBODY];
    int   len;
    Apple apples[NAPPLES];
    int   applesLeft;      /* pommes restantes ce round  */
    int   pendingGrowth;   /* segments a ajouter         */
    int   score;
    Dir   dir;
    int   over;            /* 1 = partie terminee        */
    int   computer;        /* 1 = mode ordinateur        */
    int   fast;            /* 1 = vitesse rapide         */
} GameState;

/* =========================================================
   VARIABLE GLOBALE
   ========================================================= */
static GameState G;

/* =========================================================
   HELPERS CONSOLE (Windows)
   ========================================================= */
static void hideCursor(void) {
    CONSOLE_CURSOR_INFO ci = {1, FALSE};
    SetConsoleCursorInfo(GetStdHandle(STD_OUTPUT_HANDLE), &ci);
}

static void gotoxy(int x, int y) {
    COORD c;
    c.X = (SHORT)x;
    c.Y = (SHORT)y;
    SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), c);
}

static void setColor(int c) {
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), (WORD)c);
}

/* =========================================================
   VERIFICATION TERRAIN
   ========================================================= */
static int onBody(int x, int y, int from) {
    int i;
    for (i = from; i < G.len; i++)
        if (G.body[i].x == x && G.body[i].y == y) return 1;
    return 0;
}

static int onApple(int x, int y) {
    int i;
    for (i = 0; i < NAPPLES; i++)
        if (G.apples[i].active && G.apples[i].x == x && G.apples[i].y == y)
            return 1;
    return 0;
}

/* =========================================================
   GENERATION DES POMMES
   ========================================================= */
static void spawnApples(void) {
    int i, ax, ay, tries;
    for (i = 0; i < NAPPLES; i++) {
        G.apples[i].active = 1;
        tries = 0;
        do {
            ax = 1 + rand() % (W - 2);
            ay = 1 + rand() % (H - 2);
            tries++;
        } while ((onBody(ax, ay, 0) || onApple(ax, ay)) && tries < 500);
        G.apples[i].x = ax;
        G.apples[i].y = ay;
    }
    G.applesLeft = NAPPLES;
}

/* =========================================================
   INITIALISATION
   ========================================================= */
static void initGame(int computer, int fast) {
    memset(&G, 0, sizeof(G));
    G.len = 3;
    G.body[0].x = W / 2;     G.body[0].y = H / 2;
    G.body[1].x = W / 2 - 1; G.body[1].y = H / 2;
    G.body[2].x = W / 2 - 2; G.body[2].y = H / 2;
    G.score         = 0;
    G.dir           = RIGHT;
    G.over          = 0;
    G.computer      = computer;
    G.fast          = fast;
    G.pendingGrowth = 0;
    spawnApples();
}

/* =========================================================
   DESSIN
   ========================================================= */
static void drawBorder(void) {
    int x, y;
    setColor(14); /* jaune */
    for (x = 0; x < W; x++) {
        gotoxy(x, 0);  putchar('#');
        gotoxy(x, H);  putchar('#');
    }
    for (y = 0; y <= H; y++) {
        gotoxy(0,     y); putchar('#');
        gotoxy(W - 1, y); putchar('#');
    }
    setColor(7);
}

static void drawGame(void) {
    int x, y, i;
    /* Effacer zone interieure */
    for (y = 1; y < H; y++) {
        gotoxy(1, y);
        for (x = 1; x < W - 1; x++) putchar(' ');
    }
    /* Pommes */
    setColor(12); /* rouge */
    for (i = 0; i < NAPPLES; i++)
        if (G.apples[i].active) {
            gotoxy(G.apples[i].x, G.apples[i].y);
            putchar('*');
        }
    /* Corps */
    setColor(10); /* vert vif */
    for (i = 1; i < G.len; i++) {
        gotoxy(G.body[i].x, G.body[i].y);
        putchar('o');
    }
    /* Tete */
    setColor(11); /* cyan */
    gotoxy(G.body[0].x, G.body[0].y);
    putchar('@');
    /* HUD */
    setColor(7);
    gotoxy(0, H + 1);
    printf(" Score: %d  |  Pommes restantes: %d/%d  |  Longueur: %d  |  %s     ",
           G.score, G.applesLeft, NAPPLES, G.len,
           G.computer ? (G.fast ? "[ORDI RAPIDE]" : "[ORDI LENT]") : "[JOUEUR]");
    gotoxy(0, H + 2);
    if (!G.computer)
        printf(" ZQSD / Fleches = direction  |  P = Sauvegarder  |  Echap = Quitter    ");
    else
        printf(" P = Sauvegarder  |  Echap = Quitter                                   ");
}

/* =========================================================
   IA — DIRECTION GREEDY VERS LA POMME LA PLUS PROCHE
   ========================================================= */
static Dir aiDir(void) {
    int i, hx, hy, tx, ty, best, d, dx, dy, nx, ny;
    Dir pref[4], opp, chosen;
    hx = G.body[0].x; hy = G.body[0].y;
    tx = -1; ty = -1; best = W * H + 1;

    for (i = 0; i < NAPPLES; i++) {
        if (!G.apples[i].active) continue;
        d = abs(G.apples[i].x - hx) + abs(G.apples[i].y - hy);
        if (d < best) { best = d; tx = G.apples[i].x; ty = G.apples[i].y; }
    }
    if (tx < 0) return G.dir;

    /* Direction opposee interdite */
    opp = NONE;
    switch (G.dir) {
        case LEFT:  opp = RIGHT; break;
        case RIGHT: opp = LEFT;  break;
        case UP:    opp = DOWN;  break;
        case DOWN:  opp = UP;    break;
        default: break;
    }

    /* Ordre de preference selon l'axe dominant */
    dx = tx - hx; dy = ty - hy;
    if (abs(dx) >= abs(dy)) {
        pref[0] = (dx > 0) ? RIGHT : LEFT;
        pref[1] = (dy > 0) ? DOWN  : UP;
        pref[2] = (dy > 0) ? UP    : DOWN;
        pref[3] = (dx > 0) ? LEFT  : RIGHT;
    } else {
        pref[0] = (dy > 0) ? DOWN  : UP;
        pref[1] = (dx > 0) ? RIGHT : LEFT;
        pref[2] = (dx > 0) ? LEFT  : RIGHT;
        pref[3] = (dy > 0) ? UP    : DOWN;
    }

    for (i = 0; i < 4; i++) {
        chosen = pref[i];
        if (chosen == opp) continue;
        nx = hx; ny = hy;
        switch (chosen) {
            case LEFT:  nx--; break;
            case RIGHT: nx++; break;
            case UP:    ny--; break;
            case DOWN:  ny++; break;
            default: break;
        }
        if (nx <= 0 || nx >= W - 1 || ny <= 0 || ny >= H) continue;
        if (onBody(nx, ny, 1)) continue;
        return chosen;
    }
    return G.dir;
}

/* =========================================================
   MISE A JOUR DU JEU
   ========================================================= */
static void update(void) {
    int i;
    Pt head = G.body[0];

    switch (G.dir) {
        case LEFT:  head.x--; break;
        case RIGHT: head.x++; break;
        case UP:    head.y--; break;
        case DOWN:  head.y++; break;
        default: break;
    }

    /* Collision mur */
    if (head.x <= 0 || head.x >= W - 1 || head.y <= 0 || head.y >= H) {
        G.over = 1; return;
    }
    /* Collision queue */
    if (onBody(head.x, head.y, 1)) {
        G.over = 1; return;
    }

    /* Manger une pomme ? */
    int ate = 0;
    for (i = 0; i < NAPPLES; i++) {
        if (G.apples[i].active &&
            G.apples[i].x == head.x && G.apples[i].y == head.y) {
            G.apples[i].active = 0;
            G.applesLeft--;
            G.score += 10;
            ate = 1;
            break;
        }
    }

    /* Decaler le corps vers l'avant (faire de la place en tete) */
    if (G.len < MAXBODY - 1)
        for (i = G.len; i > 0; i--)
            G.body[i] = G.body[i - 1];
    G.body[0] = head;

    /* Croissance differee segment par segment */
    if (G.pendingGrowth > 0) {
        G.len++;
        G.pendingGrowth--;
    }

    /* Toutes les pommes mangees : le serpent grossit de 3 segments */
    if (ate && G.applesLeft == 0) {
        G.pendingGrowth += NAPPLES;
        G.score += 30; /* bonus de serie */
        spawnApples();
    }
}

/* =========================================================
   SAUVEGARDE / CHARGEMENT
   ========================================================= */
static void saveGame(void) {
    FILE *f = fopen(SAVEFILE, "wb");
    if (!f) {
        gotoxy(0, H + 3); setColor(12);
        printf(" Erreur : impossible de sauvegarder !   ");
        setColor(7); return;
    }
    fwrite(&G, sizeof(GameState), 1, f);
    fclose(f);
    gotoxy(0, H + 3); setColor(10);
    printf(" Partie sauvegardee dans '%s'   ", SAVEFILE);
    setColor(7);
}

static int loadGame(void) {
    FILE *f = fopen(SAVEFILE, "rb");
    if (!f) return 0;
    fread(&G, sizeof(GameState), 1, f);
    fclose(f);
    G.over = 0;
    return 1;
}

/* =========================================================
   TRAITEMENT DES TOUCHES (MODE JOUEUR)
   ========================================================= */
static void handleInput(void) {
    int ch;
    if (!_kbhit()) return;
    ch = _getch();
    if (ch == 0 || ch == 224) {
        ch = _getch();
        switch (ch) {
            case 75: if (G.dir != RIGHT) G.dir = LEFT;  break; /* fleche gauche */
            case 77: if (G.dir != LEFT)  G.dir = RIGHT; break; /* fleche droite */
            case 72: if (G.dir != DOWN)  G.dir = UP;    break; /* fleche haut   */
            case 80: if (G.dir != UP)    G.dir = DOWN;  break; /* fleche bas    */
        }
    } else {
        switch (tolower(ch)) {
            case 'q': if (G.dir != RIGHT) G.dir = LEFT;  break;
            case 'd': if (G.dir != LEFT)  G.dir = RIGHT; break;
            case 'z': if (G.dir != DOWN)  G.dir = UP;    break;
            case 's': if (G.dir != UP)    G.dir = DOWN;  break;
            case 'p': saveGame(); break;
            case 27:  G.over = 1; break; /* Echap */
        }
    }
}

/* =========================================================
   MENU PRINCIPAL
   ========================================================= */
static void showMenu(void) {
    system("cls");
    setColor(11);
    printf("\n");
    printf("  +----------------------------------+\n");
    printf("  |    JEU DU SERPENT — SNAKE (C)    |\n");
    printf("  +----------------------------------+\n");
    setColor(14);
    printf("  |  1. Joueur   - Vitesse normale   |\n");
    printf("  |  2. Joueur   - Vitesse rapide    |\n");
    printf("  |  3. Ordi     - Vitesse lente     |\n");
    printf("  |  4. Ordi     - Vitesse rapide    |\n");
    printf("  |  5. Charger une sauvegarde       |\n");
    printf("  |  6. Quitter                      |\n");
    setColor(11);
    printf("  +----------------------------------+\n\n");
    setColor(7);
    printf("  Votre choix : ");
}

/* =========================================================
   MAIN
   ========================================================= */
int main(void) {
    int c, running;

    srand((unsigned)time(NULL));
    hideCursor();
    SetConsoleTitle("Snake — Jeu du Serpent (C)");
    system("mode con: cols=80 lines=30");

    running = 1;
    while (running) {
        showMenu();
        c = _getch();
        printf("%c\n", c);

        switch (c) {
            case '1': initGame(0, 0); break;
            case '2': initGame(0, 1); break;
            case '3': initGame(1, 0); break;
            case '4': initGame(1, 1); break;
            case '5':
                if (!loadGame()) {
                    setColor(12);
                    printf("\n  Aucune sauvegarde trouvee (%s).\n", SAVEFILE);
                    setColor(7);
                    printf("  Appuyez sur une touche...\n");
                    _getch();
                    continue;
                }
                break;
            case '6':
            default:
                running = 0;
                continue;
        }

        /* Boucle de jeu */
        system("cls");
        drawBorder();
        drawGame();

        while (!G.over) {
            if (G.computer) {
                G.dir = aiDir();
                if (_kbhit()) {
                    int k = _getch();
                    if (tolower(k) == 'p') saveGame();
                    if (k == 27) break;
                }
            } else {
                handleInput();
                if (G.over) break;
            }
            update();
            if (!G.over) drawGame();
            Sleep(G.fast ? SP_FAST : SP_SLOW);
        }

        /* Ecran de fin */
        setColor(12);
        gotoxy(W / 2 - 7, H / 2);
        printf("** GAME OVER **");
        setColor(15);
        gotoxy(W / 2 - 9, H / 2 + 1);
        printf("Score final : %d", G.score);
        setColor(7);
        gotoxy(0, H + 3);
        printf(" Appuyez sur une touche pour revenir au menu...\n");
        _getch();
    }

    system("cls");
    setColor(11);
    printf("\n  Merci d'avoir joue ! Au revoir.\n\n");
    setColor(7);
    return 0;
}
