# Makefile — Jeu du Serpent (Snake)
# Prérequis : MinGW / GCC installé (Windows)
# Usage     : make          → compile snake.c
#             make cpp      → compile snake.cpp
#             make clean    → supprime les exécutables

CC      = gcc
CXX     = g++
CFLAGS  = -Wall -Wextra -std=c99 -O2
CXXFLAGS= -Wall -Wextra -std=c++17 -O2

all: snake.exe

snake.exe: snake.c
	$(CC) $(CFLAGS) snake.c -o snake.exe

cpp: snake_cpp.exe

snake_cpp.exe: snake.cpp
	$(CXX) $(CXXFLAGS) snake.cpp -o snake_cpp.exe

clean:
	del /Q snake.exe snake_cpp.exe 2>nul || true

.PHONY: all cpp clean
