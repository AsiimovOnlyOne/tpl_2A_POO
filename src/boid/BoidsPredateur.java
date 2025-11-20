package boid;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * sous classe de boid qui correspond aux boids proies
 */
public class BoidsPredateur extends Boids{

    public BoidsPredateur(){
        super();
        /** caractéristique de la famille prédateur à modifier*/
        super.setlargeur(50); // largeur de la famille
        super.sethauteur(35); // hauteur de la famille
        super.setvMax(6f); // vitesse max de la famille
        super.setcouleur(Color.red); // couleur de la famille
        super.setTaille(12); // nomble de bois dans la famille
    }

        /**
     * Calcul de l’accélération pour un boid i selon les règles de Parker
     * - Cohésion : pcX, pcY
     * - Séparation : cX, cY
     * - Alignement : pvX, pvY
     */
    public Point2D f(int i, Boids proies) {
        /**
         * coefficients a modifier
         * ils interviennent dans le calcul de la force
         */
        float coh = 2000f; // plus ce coef est faible plus la force liée au centre de masse de la famille de boid est élevée
        float align = 8f; // plus ce coeff est faible plus la force lié à l'alignement est élevée
        float coh_proies = 100f; // plus ce coeff est faible plus la force repulsive lié au centre de masse des prédateur est élevée
        float dist_attir = (float) 2; // plus ce coeff est élevé plus le boid va repérer de loin les proies
        float faim = (float) 0.01; // plus ce coeff est élevé plus les prédateur seront attirés par les proies un fois repérés

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
                if (pi.distance(pj) < super.gethauteur()+super.getlargeur()/4){
                    cX -= pj.x - pi.x;
                    cY -= pj.y - pi.y;
                }

                // 3) Alignement, en prenant en compte toute la population
                pvX += vj.getX();
                pvY += vj.getY();
            }
        }

        /** par rapport aux proies, les prédateurs sont attirés par le centre de masse des proies et sont d'avantage attiré si elles sont trop praches */

        float prx = 0, pry = 0; // Cohésion des proies
        float crX = 0, crY = 0;   // attirance des proies

        for (int j = 0; j < proies.size(); j++) {
            Point pj = proies.getCoordBoids().get(j);
            // 1) Cohésion (calcul de centre de masse)
            prx += pj.x;
            pry += pj.y;

            // 2) attire si trop proche des proies
            if (pi.distance(pj) < (super.gethauteur()+super.getlargeur())*dist_attir) {
                crX += pj.x - pi.x;
                crY += pj.y - pi.y;
            }
        }

        // Moyenne et ajustement pour cohésion et alignement, les coeffs peuvent etre adaptés en fonction de ce que l'on veut simuler
        pcX = (pcX / (boids.size() - 1) - pi.x) / coh;
        pcY = (pcY / (boids.size() - 1) - pi.y) / coh;
        pvX = (float) ((pvX / (boids.size() - 1) - vi.getX()) / align);
        pvY = (float) ((pvY / (boids.size() - 1) - vi.getY()) / align);
        if (proies.size() != 0){
            prx = (pcX / (proies.size())) / coh_proies;
            pry = (pcY / (proies.size())) / coh_proies;
        }
        return new Point2D.Float(pcX + cX*10 + pvX + prx + crX*faim, pcY + cY*10 + pvY + pry + crY*faim);
    }
}
