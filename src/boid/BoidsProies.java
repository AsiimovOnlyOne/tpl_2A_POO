package boid;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * sous classe de boid qui correspond aux boids proies
 */
public class BoidsProies extends Boids{

    public BoidsProies(){
        super();
        /** caractéristique de la famille proies à modifier */
        super.setlargeur(30); // largeur de la famille conseil 30
        super.sethauteur(5); // hauteur de la famille conseil 5
        super.setvMax(6f); // le double de la vitesse max de la famille conseil 6f
        super.setcouleur(Color.blue); // couleur de la famille conseil blue
        super.setTaille(120); // nombre de boids dans la famille conseil 120
    }

    /**
     * Calcul de l’accélération pour un boid i selon les règles de Parker
     * - Cohésion : pcX, pcY
     * - Séparation : cX, cY
     * - Alignement : pvX, pvY
     */
    public Point2D f(int i, Boids pred) {
        /**
         * coefficients a modifier
         * ils interviennent dans le calcul de la force
         */
        float coh = 800f; // plus ce coeff est faible plus la force liée au centre de masse de la famille de boid est élevée conseil 800f
        float align = 3f; // plus ce coeff est faible plus la force lié à l'alignement est élevée conseil 3f
        float coh_pred = 30f; // plus ce coeff est faible plus la force repulsive lié au centre de masse des prédateur est élevée conseil 30f
        float dist_repuls = (float) 2; // plus ce coeff est élevé plus le boid va repérer de loin les proies conseil 2
        float peur = (float) 0.1; // plus ce coeff est élevé plus les proies éviteront les prédateurs une fois repéré conseil 0.1

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
                if (pi.distance(pj) < (super.gethauteur()+super.getlargeur())/3) {
                    cX -= pj.x - pi.x;
                    cY -= pj.y - pi.y;
                }

                // 3) Alignement, en prenant en compte toute la population
                pvX += vj.getX();
                pvY += vj.getY();
            }
        }

        /** par rapport aux predateur, les proies fuient le centre de masse des prédateurs et se sépare des prédateurs s ils sont trop pres*/

        float prx = 0, pry = 0; // fuient le centre de masse des pred
        float crX = 0, crY = 0;   // Séparation des pred

        for (int j = 0; j < pred.size(); j++) {
            Point pj = pred.getCoordBoids().get(j);
            // 1) Cohésion (calcul de centre de masse)
            prx += pj.x;
            pry += pj.y;

            // 2) Séparation si trop proche
            if (pi.distance(pj) < (super.gethauteur()+super.getlargeur())*dist_repuls){
                crX -= pj.x - pi.x;
                crY -= pj.y - pi.y;
            }
        }

        // Moyenne et ajustement pour cohésion et alignement, les coeffs peuvent etre adaptés en fonction de ce que l'on veut simuler
        if ((boids.size() - 1) != 0) {
            pcX = (pcX / (boids.size() - 1) - pi.x) / coh;
            pcY = (pcY / (boids.size() - 1) - pi.y) / coh;
            pvX = (float) ((pvX / (boids.size() - 1) - vi.getX()) / align);
            pvY = (float) ((pvY / (boids.size() - 1) - vi.getY()) / align);
        }
        if(pred.size() != 0){
            prx = (pcX / (pred.size())) / coh_pred;
            pry = (pcY / (pred.size())) / coh_pred;
        }
        return new Point2D.Float(pcX + cX + pvX - prx + crX*peur, pcY + cY + pvY - pry + crY*peur);
    }
}
