package boid;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class BoidsPredateur extends Boids{

    public BoidsPredateur(){
        super();
        /** caractéristique de la famille prédateur */
        super.setlargeur(50);
        super.sethauteur(35);
        super.setvMax(6f);
        super.setcouleur(Color.red);
        super.setTaille(12);
    }

        /**
     * Calcul de l’accélération pour un boid i selon les règles de Parker
     * - Cohésion : pcX, pcY
     * - Séparation : cX, cY
     * - Alignement : pvX, pvY
     */
    public Point2D f(int i, Boids proies) {

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
                pvX += vj.getX();
                pvY += vj.getY();
            }
        }

        /** par rapport aux proies, les prédateurs sont attirés par le centre de masse des proies */

        float prx = 0, pry = 0; // Cohésion des proies
        float crX = 0, crY = 0;   // attirance des proies

        for (int j = 0; j < proies.size(); j++) {
            Point pj = proies.getCoordBoids().get(j);
            // 1) Cohésion (calcul de centre de masse)
            prx += pj.x;
            pry += pj.y;

            // 2) attire si trop proche des proies
            if (pi.distance(pj) < super.gethauteur()+super.getlargeur()*3) {
                crX += pj.x - pi.x;
                crY += pj.y - pi.y;
            }
        }

        // Moyenne et ajustement pour cohésion et alignement, /100f et /8f corresponde aux coef des composantes de chaque forces
        pcX = (pcX / (boids.size() - 1) - pi.x) / 800f;
        pcY = (pcY / (boids.size() - 1) - pi.y) / 800f;
        pvX = (float) ((pvX / (boids.size() - 1) - vi.getX()) / 8f);
        pvY = (float) ((pvY / (boids.size() - 1) - vi.getY()) / 8f);
        prx = (pcX / (proies.size())) / 100f;
        pry = (pcY / (proies.size())) / 100f;
        return new Point2D.Float(pcX + cX + pvX + prx + crX/10, pcY + cY + pvY + pry + crY/10);
    }
}
