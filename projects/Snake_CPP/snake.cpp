// ============================================================
//  JEU DU SERPENT - Snake C++
//  - 3 pommes generees a la fois
//  - Le serpent grossit apres avoir mange les 3 pommes
//  - Game over : collision mur ou queue
//  - Sauvegarde/reprise de partie (fichier binaire)
//  - Mode Ordinateur (2 vitesses)
//  Compilation : g++ -o snake snake.cpp
//  Dependances : conio.h, windows.h (Windows uniquement)
// ============================================================

#include <iostream>
#include <conio.h>
#include <windows.h>
#include <fstream>
#include <ctime>
#include <cstdlib>
#include <climits>

using namespace std;

// ---- Dimensions du terrain (bordures incluses) ----
const int W        = 42;
const int H        = 22;
const int NAPPLES  = 3;
const int MAXBODY  = 1000;
const int SP_SLOW  = 220;   // ms par tick (mode lent)
const int SP_FAST  = 90;    // ms par tick (mode rapide)
const char* SAVE   = "snake_save.dat";

// ---- Direction ----
enum Dir { NONE=0, LEFT, RIGHT, UP, DOWN };

// ---- Point 2D ----
struct Pt { int x, y; };

// ---- Pomme ----
struct Apple { int x, y; bool active; };

// ---- Etat complet du jeu (serialisable) ----
struct GameState {
    Pt    body[MAXBODY]; // corps du serpent
    int   len;           // longueur actuelle
    Apple apples[NAPPLES];
    int   applesLeft;    // pommes restantes dans la serie
    int   pendingGrowth; // segments a ajouter progressivement
    int   score;
    Dir   dir;
    bool  over;
    bool  computer;      // mode ordinateur ?
    bool  fast;          // vitesse rapide ?
};

GameState G;

// ============================================================
//  Helpers console (Windows)
// ============================================================
static void hideCursor() {
    HANDLE h = GetStdHandle(STD_OUTPUT_HANDLE);
    CONSOLE_CURSOR_INFO ci = {1, FALSE};
    SetConsoleCursorInfo(h, &ci);
}
static void gotoxy(int x, int y) {
    COORD c = {(SHORT)x, (SHORT)y};
    SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), c);
}
static void color(int c) {
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), c);
}

// ============================================================
//  Verifications terrain
// ============================================================
static bool onBody(int x, int y, int from = 0) {
    for (int i = from; i < G.len; i++)
        if (G.body[i].x == x && G.body[i].y == y) return true;
    return false;
}
static bool onApple(int x, int y) {
    for (int i = 0; i < NAPPLES; i++)
        if (G.apples[i].active && G.apples[i].x == x && G.apples[i].y == y)
            return true;
    return false;
}

// ============================================================
//  Generation des pommes
// ============================================================
static void spawnApples() {
    for (int i = 0; i < NAPPLES; i++) {
        G.apples[i].active = true;
        int ax, ay;
        int tries = 0;
        do {
            ax = 1 + rand() % (W - 2);
            ay = 1 + rand() % (H - 2);
            tries++;
        } while ((onBody(ax, ay) || onApple(ax, ay)) && tries < 500);
        G.apples[i].x = ax;
        G.apples[i].y = ay;
    }
    G.applesLeft = NAPPLES;
}

// ============================================================
//  Initialisation d'une nouvelle partie
// ============================================================
static void initGame(bool computer, bool fast) {
    G.len           = 3;
    G.body[0]       = {W / 2,     H / 2};
    G.body[1]       = {W / 2 - 1, H / 2};
    G.body[2]       = {W / 2 - 2, H / 2};
    G.score         = 0;
    G.dir           = RIGHT;
    G.over          = false;
    G.computer      = computer;
    G.fast          = fast;
    G.pendingGrowth = 0;
    spawnApples();
}

// ============================================================
//  Dessin
// ============================================================
static void drawBorder() {
    color(14); // jaune
    for (int x = 0; x < W; x++) {
        gotoxy(x, 0);  cout << '#';
        gotoxy(x, H);  cout << '#';
    }
    for (int y = 0; y <= H; y++) {
        gotoxy(0,     y); cout << '#';
        gotoxy(W - 1, y); cout << '#';
    }
    color(7);
}

static void drawGame() {
    // Effacer zone interieure
    for (int y = 1; y < H; y++) {
        gotoxy(1, y);
        for (int x = 1; x < W - 1; x++) cout << ' ';
    }
    // Pommes
    color(12);
    for (int i = 0; i < NAPPLES; i++)
        if (G.apples[i].active) { gotoxy(G.apples[i].x, G.apples[i].y); cout << '*'; }
    // Corps
    color(10);
    for (int i = 1; i < G.len; i++) { gotoxy(G.body[i].x, G.body[i].y); cout << 'o'; }
    // Tete
    color(11);
    gotoxy(G.body[0].x, G.body[0].y); cout << '@';
    // HUD
    color(7);
    gotoxy(0, H + 1);
    cout << " Score: " << G.score
         << "  Pommes restantes: " << G.applesLeft
         << "  Longueur: " << G.len
         << "  " << (G.computer ? (G.fast ? "[ORDI RAPIDE]" : "[ORDI LENT]") : "[JOUEUR]")
         << "     ";
    gotoxy(0, H + 2);
    if (!G.computer)
        cout << " ZQSD / Fleches = direction  |  P = Sauvegarder  |  Echap = Quitter    ";
    else
        cout << " P = Sauvegarder  |  Echap = Quitter                                   ";
}

// ============================================================
//  IA : trouve la pomme la plus proche et s'y dirige
// ============================================================
static Dir aiDir() {
    int hx = G.body[0].x, hy = G.body[0].y;
    int tx = -1, ty = -1, best = INT_MAX;
    for (int i = 0; i < NAPPLES; i++) {
        if (!G.apples[i].active) continue;
        int d = abs(G.apples[i].x - hx) + abs(G.apples[i].y - hy);
        if (d < best) { best = d; tx = G.apples[i].x; ty = G.apples[i].y; }
    }
    if (tx < 0) return G.dir;

    // Direction contraire interdite
    Dir opp = NONE;
    switch (G.dir) {
        case LEFT: opp = RIGHT; break; case RIGHT: opp = LEFT; break;
        case UP:   opp = DOWN;  break; case DOWN:  opp = UP;   break;
        default: break;
    }

    // Ordre de preference vers la cible
    int dx = tx - hx, dy = ty - hy;
    Dir pref[4];
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

    for (int i = 0; i < 4; i++) {
        if (pref[i] == opp) continue;
        int nx = hx, ny = hy;
        switch (pref[i]) {
            case LEFT: nx--; break; case RIGHT: nx++; break;
            case UP:   ny--; break; case DOWN:  ny++; break;
            default: break;
        }
        if (nx <= 0 || nx >= W - 1 || ny <= 0 || ny >= H) continue;
        if (onBody(nx, ny, 1)) continue;
        return pref[i];
    }
    return G.dir;
}

// ============================================================
//  Logique de deplacement
// ============================================================
static void update() {
    Pt head = G.body[0];
    switch (G.dir) {
        case LEFT:  head.x--; break;
        case RIGHT: head.x++; break;
        case UP:    head.y--; break;
        case DOWN:  head.y++; break;
        default: break;
    }

    // Collision mur
    if (head.x <= 0 || head.x >= W - 1 || head.y <= 0 || head.y >= H) {
        G.over = true; return;
    }
    // Collision queue
    if (onBody(head.x, head.y, 1)) {
        G.over = true; return;
    }

    // Verifier si on mange une pomme
    bool ate = false;
    for (int i = 0; i < NAPPLES; i++) {
        if (G.apples[i].active &&
            G.apples[i].x == head.x && G.apples[i].y == head.y) {
            G.apples[i].active = false;
            G.applesLeft--;
            G.score += 10;
            ate = true;
            break;
        }
    }

    // Decaler le corps vers l'avant
    // Faire de la place en tete
    if (G.len < MAXBODY - 1) {
        for (int i = G.len; i > 0; i--)
            G.body[i] = G.body[i - 1];
    }
    G.body[0] = head;

    // Croissance : si pendingGrowth > 0 on ne retire pas la queue
    if (G.pendingGrowth > 0) {
        G.len++;
        G.pendingGrowth--;
    }
    // Sinon mouvement normal (la queue suit la tete)
    // (le decalage ci-dessus suffit, len ne change pas)

    // Toutes les pommes mangees -> le serpent grossit de 3
    if (ate && G.applesLeft == 0) {
        G.pendingGrowth += NAPPLES; // croissance differee
        G.score += 30;              // bonus de serie
        spawnApples();
    }
}

// ============================================================
//  Sauvegarde / Chargement
// ============================================================
static void saveGame() {
    ofstream f(SAVE, ios::binary);
    if (!f) {
        gotoxy(0, H + 3); color(12);
        cout << " Erreur : impossible de sauvegarder !   ";
        color(7); return;
    }
    f.write((char*)&G, sizeof(G));
    f.close();
    gotoxy(0, H + 3); color(10);
    cout << " Partie sauvegardee dans '" << SAVE << "'   ";
    color(7);
}

static bool loadGame() {
    ifstream f(SAVE, ios::binary);
    if (!f) return false;
    f.read((char*)&G, sizeof(G));
    f.close();
    G.over = false;
    return true;
}

// ============================================================
//  Traitement des touches (joueur)
// ============================================================
static void handleInput() {
    if (!_kbhit()) return;
    int ch = _getch();
    if (ch == 0 || ch == 224) {
        // Touches speciales (fleches)
        ch = _getch();
        switch (ch) {
            case 75: if (G.dir != RIGHT) G.dir = LEFT;  break; // fleche gauche
            case 77: if (G.dir != LEFT)  G.dir = RIGHT; break; // fleche droite
            case 72: if (G.dir != DOWN)  G.dir = UP;    break; // fleche haut
            case 80: if (G.dir != UP)    G.dir = DOWN;  break; // fleche bas
        }
    } else {
        switch (tolower(ch)) {
            case 'q': if (G.dir != RIGHT) G.dir = LEFT;  break;
            case 'd': if (G.dir != LEFT)  G.dir = RIGHT; break;
            case 'z': if (G.dir != DOWN)  G.dir = UP;    break;
            case 's': if (G.dir != UP)    G.dir = DOWN;  break;
            case 'p': saveGame(); break;
            case 27:  G.over = true; break; // Echap
        }
    }
}

// ============================================================
//  Main
// ============================================================
int main() {
    srand((unsigned)time(nullptr));
    system("cls");
    hideCursor();

    cout << "+-------------------------------+" << endl;
    cout << "|       JEU DU SERPENT          |" << endl;
    cout << "+-------------------------------+" << endl;
    cout << "| 1. Joueur  - Vitesse lente    |" << endl;
    cout << "| 2. Joueur  - Vitesse rapide   |" << endl;
    cout << "| 3. Ordi    - Vitesse lente    |" << endl;
    cout << "| 4. Ordi    - Vitesse rapide   |" << endl;
    cout << "| 5. Charger une sauvegarde     |" << endl;
    cout << "+-------------------------------+" << endl;
    cout << "Votre choix : ";

    char ch = _getch();
    cout << ch << endl;

    switch (ch) {
        case '1': initGame(false, false); break;
        case '2': initGame(false, true);  break;
        case '3': initGame(true,  false); break;
        case '4': initGame(true,  true);  break;
        case '5':
            if (!loadGame()) {
                cout << "\nAucune sauvegarde trouvee (" << SAVE << ")." << endl;
                cout << "Appuyez sur une touche..." << endl;
                _getch(); return 0;
            }
            break;
        default:
            initGame(false, false);
    }

    system("cls");
    drawBorder();
    drawGame();

    // ---- Boucle principale ----
    while (!G.over) {
        if (G.computer) {
            G.dir = aiDir();
            // Permet de sauvegarder ou quitter meme en mode ordi
            if (_kbhit()) {
                int c = _getch();
                if (c == 'p' || c == 'P') saveGame();
                if (c == 27) break;
            }
        } else {
            handleInput();
            if (G.over) break;
        }

        update();

        if (!G.over) drawGame();

        Sleep(G.fast ? SP_FAST : SP_SLOW);
    }

    // ---- Ecran de fin ----
    gotoxy(W / 2 - 6, H / 2);
    color(12); cout << "** GAME OVER **";
    gotoxy(W / 2 - 9, H / 2 + 1);
    color(15); cout << "Score final : " << G.score;
    color(7);
    gotoxy(0, H + 3);
    cout << " Appuyez sur une touche pour quitter..." << endl;
    _getch();
    return 0;
}
