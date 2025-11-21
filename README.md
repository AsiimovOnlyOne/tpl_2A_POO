# TPL 2A POO

Présent dans chaque package:

**Architecture Événementielle** : Utilisation d'un *EventManager* pour découpler le temps de la logique.
*Event.java* : 
définit une structure générique d’événement, représente toute action programmée 
*EventManager.java* : 
exécute les événements dans l’ordre chronologique
##**BALL**
	-> Objectif du package **ball** : Simulation basique de particules.
*Balls.java* : Gère une liste de balles, leurs positions et leurs rebonds sur les murs.
*BallsSimulator.java* : Le contrôleur qui lie le modèle Balls à l'interface graphique et au gestionnaire d'événements.

**/event**
BallsMoveEvent.java : L'événement discret responsable du déplacement des balles à chaque pas de temps.



##**CELL**
-> Objectif du package **cell**: fournir une architecture générique pour simuler des automates cellulaires, dont les modèles de ségrégation sont variés. 

*Cell.java*: Représente une cellule unique dans la grille. Elle ne connaît pas sa position, seulement son état courant.

**/grid**
*AbstractGrid.java*: factorise le code des grid
*GridConway.java* et *GridImmigration.java*: 
Implémentation concrète des automates de Conway et du jeu de l’immigration, ces programmes calculent les nouveaux états en suivant les règles de leur automate.
*GridSchelling.java*: implémentation concrète de l’automate de Schelling. Néanmoins, Schelling ne suit pas un automate cellulaire classique donc contrairement à Conway/Immigration, la transition n’est pas une simple règle locale d’où la redéfinition de next().

**/simulator**
*AbstractSimulator.java*: factorise le code des simulator
*ConwaySimulator.java* et *ImmigrationSimulator.java* et *SchellingSimulator.java*: 
Facilite la gestion des simulateurs graphiques à l’image des autres simulateurs aussi implémenter. 

**/event**
*GridEvent.java* : Événement périodique qui déclenche la méthode next() d'une grille.

##**BOID**
	-> Objectif du package **boid** : simuler le déplacement d’agents en essaim, puis de comportement de cohabitation

*BoidsSimulator.java* :
implémente la classe Simulable ce qui permet la réalisation de l’interface
*EllipseOrientee.java* : 
classe qui implémente GraphicalElement ce qui permet de dessiner dans GUI des ellipses dans toutes les positions, c’est avec cette classe qu’on dessine nos boids dans DrawBoids

**/event**
*EventBoids.java* : 
sous classe de Event pour une famille de boid pour factoriser le code pour les événements qui manipulent des boids 
*RestartBoids.java* : 
sous classe de EventBoids qui reset la famille de boid
*TranslateBoids.java* : 
sous classe de EventBoids qui translate les boids en prenant compte les interactions avec l’autre famille de boid
*DrawBoids.java*: 
sous classe de EventBoids qui dessine la famille de boid

**/model**
*Boids.java* :
défini la classe abstraite Boids, on a une factorisation du code car il y a plusieurs types de Boids (Prédateur et Proies)
*BoidsPredateurs.java*  et *BoidsProies.java* : 
sous classes de Boids qui implémentent différemment la fonction f qui définit l'accélération des boids, et donc leur comportement