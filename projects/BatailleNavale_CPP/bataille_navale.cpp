// ============================================================
//  BATAILLE NAVALE - C++
//  - Grille 8x8, 3 types de bateaux (tailles 2, 3, 4)
//  - Un bateau coule des qu'il est touche (une case suffit)
//  - Mode Joueur vs Joueur  ou  Joueur vs Ordinateur
//  - Sauvegarde/reprise de partie (fichier binaire)
//  - Matrices pour stocker les grilles
//  Compilation : g++ -o bataille_navale bataille_navale.cpp
//  Dependances : conio.h, windows.h (Windows uniquement)
// ============================================================

#include <iostream>
#include <conio.h>
#include <windows.h>
#include <fstream>
#include <ctime>
#include <cstdlib>
#include <cstring>

using namespace std;

// ============================================================
//  Constantes
// ============================================================
const int  GRID    = 8;
const int  NSHIPS  = 3;           // un bateau de taille 2, 3, 4
const char* SAVE   = "bataille_save.dat";

// Valeurs des cellules
// 0 = eau   1 = bateau intact   2 = rate   3 = coule
const int WATER  = 0;
const int SHIP   = 1;
const int MISS   = 2;
const int SUNK   = 3;

// ============================================================
//  Structures (toutes POD pour serialisation binaire simple)
// ============================================================
struct Ship {
    int  size;      // 2, 3 ou 4
    int  r, c;      // ligne/colonne du premier segment
    bool horiz;     // orientation
    bool sunk;      // coule ?
};

struct Player {
    int  myGrid [GRID][GRID];  // ma flotte (vue complete)
    int  oppGrid[GRID][GRID];  // ce que je connais de l'adversaire
    Ship ships[NSHIPS];
    bool isComputer;
    char name[32];
};

struct GameState {
    Player p[2];
    int    turn;    // 0 ou 1
    bool   over;
    int    winner;  // -1 si pas encore fini
};

GameState G;

// ============================================================
//  Helpers console
// ============================================================
static void gotoxy(int x, int y) {
    COORD c = {(SHORT)x, (SHORT)y};
    SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), c);
}
static void color(int c) {
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), c);
}
static void clrscr() { system("cls"); }

// ============================================================
//  Utilitaires grille
// ============================================================
static void clearGrid(int g[GRID][GRID]) {
    for (int r = 0; r < GRID; r++)
        for (int c = 0; c < GRID; c++)
            g[r][c] = WATER;
}

static bool canPlace(const int g[GRID][GRID], int r, int c, int size, bool horiz) {
    for (int i = 0; i < size; i++) {
        int rr = r + (horiz ? 0 : i);
        int cc = c + (horiz ? i : 0);
        if (rr < 0 || rr >= GRID || cc < 0 || cc >= GRID) return false;
        if (g[rr][cc] != WATER) return false;
    }
    return true;
}

static void placeShip(int g[GRID][GRID], int r, int c, int size, bool horiz) {
    for (int i = 0; i < size; i++) {
        int rr = r + (horiz ? 0 : i);
        int cc = c + (horiz ? i : 0);
        g[rr][cc] = SHIP;
    }
}

// ============================================================
//  Placement aleatoire des bateaux
// ============================================================
static void randomPlaceShips(Player& p) {
    clearGrid(p.myGrid);
    const int sizes[NSHIPS] = {4, 3, 2};
    for (int s = 0; s < NSHIPS; s++) {
        p.ships[s].size = sizes[s];
        p.ships[s].sunk = false;
        int r, c; bool h;
        int tries = 0;
        do {
            r = rand() % GRID;
            c = rand() % GRID;
            h = rand() % 2;
            tries++;
        } while (!canPlace(p.myGrid, r, c, sizes[s], h) && tries < 1000);
        p.ships[s].r = r;
        p.ships[s].c = c;
        p.ships[s].horiz = h;
        placeShip(p.myGrid, r, c, sizes[s], h);
    }
}

// ============================================================
//  Affichage d'une grille
//  offX, offY = position dans la console
//  reveal = montrer les bateaux (true = grille propre, false = grille ennemie)
// ============================================================
static void printGrid(const int g[GRID][GRID], bool reveal, int offX, int offY) {
    color(7);
    gotoxy(offX, offY);
    cout << "  A B C D E F G H";
    for (int r = 0; r < GRID; r++) {
        gotoxy(offX, offY + 1 + r);
        cout << (r + 1) << ' ';
        for (int c = 0; c < GRID; c++) {
            int cell = g[r][c];
            if (!reveal && cell == SHIP) cell = WATER; // cacher les bateaux adverses
            switch (cell) {
                case WATER: color(8);  cout << ". "; break; // gris - eau
                case SHIP:  color(10); cout << "# "; break; // vert - bateau
                case MISS:  color(9);  cout << "O "; break; // bleu - rate
                case SUNK:  color(12); cout << "X "; break; // rouge - coule
            }
            color(7);
        }
    }
}

// ============================================================
//  Affichage complet du plateau pour le joueur courant
// ============================================================
static void displayBoards() {
    int cp = G.turn;
    clrscr();
    color(11);
    gotoxy(0, 0); cout << "========== BATAILLE NAVALE ==========";
    color(7);
    gotoxy(0, 1); cout << "Tour de : "; color(14); cout << G.p[cp].name; color(7);

    // Grille gauche : ma flotte
    gotoxy(0, 3);  color(10); cout << G.p[cp].name; color(7); cout << " - Ma flotte    ";
    printGrid(G.p[cp].myGrid, true, 0, 4);

    // Grille droite : tirs sur l'adversaire
    gotoxy(22, 3); color(12); cout << "Mes tirs (adversaire)   "; color(7);
    printGrid(G.p[cp].oppGrid, false, 22, 4);

    // Etat des bateaux
    gotoxy(0, 13);
    cout << "Mes bateaux : ";
    for (int i = 0; i < NSHIPS; i++) {
        color(G.p[cp].ships[i].sunk ? 12 : 10);
        cout << "[" << G.p[cp].ships[i].size << "] ";
    }
    color(7);

    gotoxy(0, 14);
    cout << "Commandes : coordonnee (ex: C4)  |  P = Sauvegarder  |  Q = Quitter";
    gotoxy(0, 15);
    cout << "Legende : # bateau  O rate  X coule  . eau               ";
}

// ============================================================
//  Couler un bateau : marque toutes ses cellules comme SUNK
// ============================================================
static void sinkShipAt(Player& target, int r, int c) {
    for (int s = 0; s < NSHIPS; s++) {
        if (target.ships[s].sunk) continue;
        // Verifier si la case (r,c) appartient a ce bateau
        for (int i = 0; i < target.ships[s].size; i++) {
            int rr = target.ships[s].r + (target.ships[s].horiz ? 0 : i);
            int cc = target.ships[s].c + (target.ships[s].horiz ? i : 0);
            if (rr == r && cc == c) {
                // Ce bateau est touche -> coule entierement
                target.ships[s].sunk = true;
                for (int j = 0; j < target.ships[s].size; j++) {
                    int sr = target.ships[s].r + (target.ships[s].horiz ? 0 : j);
                    int sc = target.ships[s].c + (target.ships[s].horiz ? j : 0);
                    target.myGrid[sr][sc] = SUNK;
                }
                return;
            }
        }
    }
}

// ============================================================
//  Synchroniser oppGrid de l'attaquant apres coulage
// ============================================================
static void syncOppGrid(Player& attacker, const Player& target) {
    for (int s = 0; s < NSHIPS; s++) {
        if (!target.ships[s].sunk) continue;
        for (int i = 0; i < target.ships[s].size; i++) {
            int rr = target.ships[s].r + (target.ships[s].horiz ? 0 : i);
            int cc = target.ships[s].c + (target.ships[s].horiz ? i : 0);
            if (attacker.oppGrid[rr][cc] == WATER)
                attacker.oppGrid[rr][cc] = SUNK;
        }
    }
}

// ============================================================
//  Effectuer un tir
//  Retourne false si la case a deja ete tiree
// ============================================================
static bool fire(int r, int c) {
    int cp  = G.turn;
    int opp = 1 - cp;
    Player& attacker = G.p[cp];
    Player& target   = G.p[opp];

    if (attacker.oppGrid[r][c] != WATER) return false;

    if (target.myGrid[r][c] == SHIP) {
        // TOUCHE -> bateau coule immediatement
        sinkShipAt(target, r, c);
        syncOppGrid(attacker, target);

        gotoxy(0, 17); color(12);
        cout << "TOUCHE ! Le bateau coule !                ";
        color(7);

        // Victoire si tous coules
        bool allGone = true;
        for (int s = 0; s < NSHIPS; s++)
            if (!target.ships[s].sunk) { allGone = false; break; }
        if (allGone) { G.over = true; G.winner = cp; }

    } else {
        attacker.oppGrid[r][c] = MISS;
        gotoxy(0, 17); color(9);
        cout << "Rate !                                    ";
        color(7);
    }
    return true;
}

// ============================================================
//  Verifier si une case a ete tiree
// ============================================================
static bool alreadyShot(int r, int c) {
    return G.p[G.turn].oppGrid[r][c] != WATER;
}

// ============================================================
//  IA Ordinateur (recherche + destruction)
// ============================================================
static void aiTurn() {
    Player& ai = G.p[G.turn];
    int tr = -1, tc = -1;

    // Phase destruction : chercher une case adjacente a un hit (SUNK)
    for (int r = 0; r < GRID && tr < 0; r++) {
        for (int c = 0; c < GRID && tr < 0; c++) {
            if (ai.oppGrid[r][c] != SUNK) continue;
            const int dr[] = {-1, 1, 0, 0};
            const int dc[] = { 0, 0,-1, 1};
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr >= 0 && nr < GRID && nc >= 0 && nc < GRID &&
                    ai.oppGrid[nr][nc] == WATER) {
                    tr = nr; tc = nc; break;
                }
            }
        }
    }

    // Phase recherche : case aleatoire non tiree
    if (tr < 0) {
        int tries = 0;
        do {
            tr = rand() % GRID;
            tc = rand() % GRID;
            tries++;
        } while (ai.oppGrid[tr][tc] != WATER && tries < 200);
    }

    gotoxy(0, 17); color(11);
    char col = 'A' + tc;
    cout << "L'ordinateur tire en " << col << (tr + 1) << "...      ";
    color(7);
    Sleep(900);
    fire(tr, tc);
}

// ============================================================
//  Parse "A3" -> r=2, c=0
// ============================================================
static bool parseCoord(const char* s, int& r, int& c) {
    if (strlen(s) < 2) return false;
    char col = toupper(s[0]);
    if (col < 'A' || col > 'H') return false;
    c = col - 'A';
    int row = 0;
    for (int i = 1; s[i] != '\0'; i++) {
        if (s[i] < '0' || s[i] > '9') return false;
        row = row * 10 + (s[i] - '0');
    }
    r = row - 1;
    return (r >= 0 && r < GRID);
}

// ============================================================
//  Placement manuel des bateaux
// ============================================================
static void manualPlace(Player& p) {
    clearGrid(p.myGrid);
    const int   sizes[NSHIPS] = {4, 3, 2};
    const char* names[NSHIPS] = {
        "Porte-avions (taille 4)",
        "Croiseur     (taille 3)",
        "Destroyer    (taille 2)"
    };

    for (int s = 0; s < NSHIPS; s++) {
        p.ships[s].size = sizes[s];
        p.ships[s].sunk = false;

        while (true) {
            clrscr();
            cout << "=== PLACEMENT - " << p.name << " ===" << endl << endl;
            printGrid(p.myGrid, true, 0, 2);
            cout << endl << endl;
            cout << "Placement : " << names[s] << endl;
            cout << "Coordonnee depart (ex: A3) : ";
            char coord[16]; cin >> coord;
            cout << "Direction  (H=horizontal / V=vertical) : ";
            char dir; cin >> dir;
            bool h = (toupper(dir) == 'H');
            int r, c;
            if (!parseCoord(coord, r, c)) { cout << "Coordonnee invalide !" << endl; Sleep(900); continue; }
            if (!canPlace(p.myGrid, r, c, sizes[s], h)) { cout << "Placement impossible !" << endl; Sleep(900); continue; }
            p.ships[s].r = r; p.ships[s].c = c; p.ships[s].horiz = h;
            placeShip(p.myGrid, r, c, sizes[s], h);
            break;
        }
    }
}

// ============================================================
//  Sauvegarde / Chargement (binaire, struct POD)
// ============================================================
static void saveGame() {
    ofstream f(SAVE, ios::binary);
    if (!f) {
        gotoxy(0, 18); color(12);
        cout << " Erreur : impossible de sauvegarder !   ";
        color(7); return;
    }
    f.write((char*)&G, sizeof(G));
    f.close();
    gotoxy(0, 18); color(10);
    cout << " Partie sauvegardee dans '" << SAVE << "'   ";
    color(7);
}

static bool loadGame() {
    ifstream f(SAVE, ios::binary);
    if (!f) return false;
    f.read((char*)&G, sizeof(G));
    f.close();
    return true;
}

// ============================================================
//  Ecran de transition entre les deux joueurs (2J)
// ============================================================
static void transitionScreen(int nextPlayer) {
    clrscr();
    color(11);
    cout << endl << endl;
    cout << "  ============================================" << endl;
    cout << "   Au tour de : " << G.p[nextPlayer].name       << endl;
    cout << "   Passez la main et appuyez sur une touche..." << endl;
    cout << "  ============================================" << endl;
    color(7);
    _getch();
}

// ============================================================
//  Main
// ============================================================
int main() {
    srand((unsigned)time(nullptr));
    clrscr();

    cout << "+-----------------------------------+" << endl;
    cout << "|         BATAILLE NAVALE           |" << endl;
    cout << "+-----------------------------------+" << endl;
    cout << "| 1. Joueur vs Ordinateur           |" << endl;
    cout << "| 2. Joueur vs Joueur               |" << endl;
    cout << "| 3. Charger une sauvegarde         |" << endl;
    cout << "+-----------------------------------+" << endl;
    cout << "Votre choix : ";

    char choice; cin >> choice;

    if (choice == '3') {
        if (!loadGame()) {
            cout << "\nAucune sauvegarde trouvee (" << SAVE << ")." << endl;
            system("pause"); return 0;
        }
    } else {
        // Initialiser
        G.over   = false;
        G.winner = -1;
        G.turn   = 0;

        strncpy(G.p[0].name, "Joueur 1", 31);
        G.p[0].isComputer = false;
        clearGrid(G.p[0].oppGrid);

        if (choice == '2') {
            strncpy(G.p[1].name, "Joueur 2", 31);
            G.p[1].isComputer = false;
        } else {
            strncpy(G.p[1].name, "Ordinateur", 31);
            G.p[1].isComputer = true;
        }
        clearGrid(G.p[1].oppGrid);

        // Placement Joueur 1
        cout << "\nJoueur 1, placement manuel ? (O/N) : ";
        char m; cin >> m;
        if (toupper(m) == 'O') manualPlace(G.p[0]);
        else                   randomPlaceShips(G.p[0]);

        // Placement Joueur 2 / Ordinateur
        if (!G.p[1].isComputer) {
            cout << "\nJoueur 2, placement manuel ? (O/N) : ";
            cin >> m;
            if (toupper(m) == 'O') manualPlace(G.p[1]);
            else                   randomPlaceShips(G.p[1]);
        } else {
            randomPlaceShips(G.p[1]);
        }
    }

    // ---- Boucle de jeu ----
    while (!G.over) {
        int cp = G.turn;
        displayBoards();

        if (G.p[cp].isComputer) {
            aiTurn();
        } else {
            // Saisie du joueur
            bool validShot = false;
            while (!validShot && !G.over) {
                gotoxy(0, 16);
                cout << "Entrez coordonnee (ex: B4) : ";
                char input[32]; cin >> input;

                if (input[0] == 'q' || input[0] == 'Q') { G.over = true; break; }
                if (input[0] == 'p' || input[0] == 'P') { saveGame(); continue; }

                int r, c;
                if (!parseCoord(input, r, c)) {
                    gotoxy(0, 17); color(12);
                    cout << "Coordonnee invalide ! (ex: A1..H8)    ";
                    color(7); Sleep(900); continue;
                }
                if (alreadyShot(r, c)) {
                    gotoxy(0, 17); color(12);
                    cout << "Case deja jouee, choisissez une autre !";
                    color(7); Sleep(900); continue;
                }
                validShot = fire(r, c);
            }
        }

        if (!G.over) {
            Sleep(700);
            int next = 1 - G.turn;
            // Transition ecran entre deux joueurs humains
            if (!G.p[G.turn].isComputer && !G.p[next].isComputer)
                transitionScreen(next);
            G.turn = next;
        }
    }

    // ---- Ecran de fin ----
    clrscr();
    cout << endl << endl;
    color(11); cout << "  =============================" << endl;
    color(15); cout << "       FIN DE PARTIE          " << endl;
    color(11); cout << "  =============================" << endl;
    if (G.winner >= 0) {
        color(10);
        cout << endl << "  " << G.p[G.winner].name << " remporte la bataille navale !" << endl;
    } else {
        color(14);
        cout << endl << "  Partie abandonnee." << endl;
    }
    color(7);
    cout << endl << "  Appuyez sur une touche pour quitter..." << endl;
    _getch();
    return 0;
}
