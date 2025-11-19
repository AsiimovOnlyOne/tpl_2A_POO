package cell;

import gui.GUISimulator;
import gui.Simulable;
import java.awt.*;

/**
 * Classe de base abstraite pour tous les simulateurs de grilles.
 * Elle gère le cycle de vie de la simulation (Avancer, Redémarrer).
 */
public abstract class AbstractSimulator implements Simulable {

    // Le cœur du modèle.
    // AbstractGrid permet au polymorphisme de fonctionner : cette variable pourra contenir
    // aussi bien une GridConway qu'une GridImmigration.
    protected AbstractGrid grid;

    /**
     * Constructeur.
     * @param gui L'interface graphique qui pilotera ce simulateur.
     */
    public AbstractSimulator(GUISimulator gui) {
        // On lie ce simulateur à l'interface graphique.
        // Quand on cliquera sur les boutons du GUI, c'est ce simulateur qui répondra.
        gui.setSimulable(this);
    }

    /**
     * Méthode abstraite.
     * Chaque jeu a sa propre façon de dessiner (couleurs différentes, formes...).
     * Les classes filles (ex: ImmigrationSimulator) devront implémenter cette méthode.
     */
    protected abstract void draw();


    /**
     * Implémentation de l'interface Simulable.
     * Appelé par le GUI pour passer à l'étape suivante.
     */
    @Override
    public void next() {
        // 1. On met à jour le modèle (calcul mathématique)
        grid.next();
        // 2. On met à jour la vue (affichage)
        draw();
    }

    /**
     * Implémentation de l'interface Simulable.
     * Appelé par le GUI pour remettre à zéro.
     */
    @Override
    public void restart() {
        // 1. On remet le modèle à son état initial
        grid.reset();
        // 2. On redessine
        draw();
    }
}
