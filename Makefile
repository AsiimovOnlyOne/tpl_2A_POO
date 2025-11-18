# Example de makefile pour compiler le squelette de code distribué
# Vous pouvez compléter ce makefile, mais étant donnée la taille du projet, 
# il est FORTEMENT recommandé d'utiliser un IDE!

# Organisation:
#  1) Les sources (*.java) se trouvent dans le répertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) sont générés dans le répertoire bin
#     La hiérarchie des sources (par package) est conservée.
#
#  3) Une librairie gui.jar est distribuée pour l'interface grapique. 
#     Elle se trouve dans le sous-répertoire lib.
#
# Compilation:
#  Options de javac:
#   -d : répertoire dans lequel sont générés les .class compilés
#   -sourcepath : répertoire dans lequel sont cherchés les .java
#   -classpath : répertoire dans lequel sont cherchées les classes compilées (.class et .jar)


# Répertoires
SRC=src
BIN=bin
LIB=lib/gui.jar

# Paramètre SIM
SIM ?= boid.BoidsSimulator

# Sélection des sources selon la simulation
ifeq ($(SIM), TestInvader)
SOURCES = $(SRC)/TestInvader.java
else ifeq ($(SIM), boid.BoidsSimulator)
SOURCES = $(shell find $(SRC)/boid -name "*.java")
else
$(error La classe SIM "$(SIM)" n'est pas reconnue. Utilise TestInvader ou boids.BoidsSimulator.)
endif

all: run

compile:
	javac -d $(BIN) -classpath $(LIB) $(SOURCES)

run: compile
	java -classpath $(BIN):$(LIB) $(SIM)

clean:
	rm -rf $(BIN)