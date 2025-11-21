package boid.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * sous classe de boid qui correspond aux boids proies
 */
public class BoidsPredateur extends Boids {

    public BoidsPredateur(){
        super();
        /** caractéristique de la famille prédateur à modifier */
        super.setlargeur(50); // largeur
        super.sethauteur(35); // hauteur
        super.setvMax(6.0); // vitesse max
        super.setcouleur(Color.red); // couleur
        super.setTaille(12); // nombre de boids dans la famille
    }

        /**
     * Calcul de l’accélération pour un boid i selon les règles de Parker
     * - Cohésion : pcX, pcY
     * - Séparation : cX, cY
     * - Alignement : pvX, pvY
     */
    public Point2D f(int i, Boids proies) {

        /** Parametre de la force exercée sur chaque boids à modifier */
        float coef_centre_masse_boids = 1/200f; // plus ce coef est élevé, plus la force liée au centre de masse de boids de la meme famille est élevée
        float coef_alignement = 1/8f; // plus ce coef est élevé, plus la force liée à l'alignement des boids de la meme famille est élevée
        float coef_centre_masse_proies = 1/800f; // plus ce coef est élevé, plus la force attractive liée au centre de masse des proies est élevée
        float faim = 1/10f; // plus ce coef est élevé, plus la force attiractive liée au proies repérées est élevée
        float dist_repere_proies = 2f; // plus ce coef est élevé, plus le boids repère les proies de loin


        ArrayList<Point> boids = super.getCoordBoids();
        ArrayList<Point2D> velocities = super.getSpeedBoids();

        Point pi = boids.get(i);
        Point2D vi = velocities.get(i);

        float pcX = 0, pcY = 0; // Cohésion
        float cX = 0, cY = 0;   // Séparation
        float pvX = 0, pvY = 0; // Alignement

        for (int j = 0; j < boids.size(); j++) {
            if (i != j) {
                Point pj = boids.get(j);
                Point2D vj = velocities.get(j);

                // 1) Cohésion (calcul de centre de masse)
                pcX += pj.x;
                pcY += pj.y;

                // 2) Séparation si trop proche
                if (pi.distance(pj) < super.gethauteur()+super.getlargeur()/4) {
                    cX -= pj.x - pi.x;
                    cY -= pj.y - pi.y;
                }

                // 3) Alignement, en prenant en compte toute la population
                pvX += (float) vj.getX();
                pvY += (float) vj.getY();
            }
        }

        // par rapport aux proies, les prédateurs sont attirés par le centre de masse des proies et sont d'avantage attiré si elles sont trop praches

        float prx = 0, pry = 0; // Cohésion des proies
        float crX = 0, crY = 0;   // attirance des proies

        for (int j = 0; j < proies.size(); j++) {
            Point pj = proies.getCoordBoids().get(j);
            // 1) Cohésion (calcul de centre de masse)
            prx += pj.x;
            pry += pj.y;

            // 2) attire si trop proche des proies
            if (pi.distance(pj) < (super.gethauteur()+super.getlargeur())*dist_repere_proies) {
                crX += pj.x - pi.x;
                crY += pj.y - pi.y;
            }
        }

        // Moyenne et ajustement pour cohésion et alignement, les coeffs peuvent etre adaptés en fonction de ce que l'on veut simuler
        if (boids.size() - 1 != 0){
            pcX = (pcX / (boids.size() - 1) - pi.x)*coef_centre_masse_boids;
            pcY = (pcY / (boids.size() - 1) - pi.y)*coef_centre_masse_boids;
        }
        pvX = (float) ((pvX / (boids.size() - 1) - vi.getX()))*coef_alignement;
        pvY = (float) ((pvY / (boids.size() - 1) - vi.getY()))*coef_alignement;

        if (proies.size() != 0){
            prx = (prx / (proies.size()) - pi.x)*coef_centre_masse_proies;
            pry = (pry / (proies.size()) - pi.y)*coef_centre_masse_proies;
        }
        return new Point2D.Float(pcX + cX*10 + pvX + prx + crX*faim, pcY + cY*10 + pvY + pry + crY*faim);
    }
}
