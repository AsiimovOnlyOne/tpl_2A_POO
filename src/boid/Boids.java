package boid;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe Boids : simulation de boids selon les règles classiques
 * - Cohésion : se rapprocher du centre de masse des voisins
 * - Séparation : éviter les collisions
 * - Alignement : suivre la vitesse moyenne des voisins
 *
 * Cette version inclut :
 * - Wrap-around : les boids qui sortent d'un côté réapparaissent de l'autre
 * - Limitation de vitesse maximale pour éviter les mouvements explosifs
 */
public class Boids {

    // Positions initiales pour reset
    private ArrayList<Point> initBoids;
    private ArrayList<Point2D> initVelocities;

    // Positions et vitesses actuelles
    private ArrayList<Point> boids;
    private ArrayList<Point2D> velocities;

    /** Constructeur : initialise les listes vides */
    public Boids() {
        this.boids = new ArrayList<>();
        this.velocities = new ArrayList<>();
    }

    /** Retourne le nombre de boids actuel */
    public int size() {
        return this.boids.size();
    }

    /** Génère n boids aléatoirement dans l’espace défini par width/height et radius */
    public void generateBoids(int n, int width, int height, int radius){
        randomBoids(n, width,height,radius);
        setInit(); // Sauvegarde l’état initial pour reset
    }

    /** Sauvegarde la position et vitesse initiales */
    public void setInit() {
        this.initBoids = new ArrayList<>(this.boids);
        this.initVelocities = new ArrayList<>(this.velocities);
    }

    /** Reset à l’état initial */
    public void reset() {
        this.boids = new ArrayList<>(this.initBoids);
        this.velocities = new ArrayList<>(this.initVelocities);
    }

    /** Retourne une copie des positions */
    public ArrayList<Point> getCoordBoids(){
        return new ArrayList<>(this.boids);
    }

    /** Retourne une copie des vitesses */
    public ArrayList<Point2D> getSpeedBoids(){
        return new ArrayList<>(this.velocities);
    }

    /** Ajoute un boid avec position et vitesse donnée */
    public void addBoid(Point boid, Point2D velocity){
        this.boids.add(boid);
        this.velocities.add(velocity);
    }

    /** Génère n boids aléatoires avec positions et vitesses initiales */
    public void randomBoids(int n, int width, int height, int radius) {
        Random rand = new Random();
        this.boids.clear();
        this.velocities.clear();

        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(width - 2 * radius) + radius;
            int y = rand.nextInt(height - 2 * radius) + radius;

            // Vitesse aléatoire entre -5 et 5, pas vitesse initiale nulle
            int dx = rand.nextInt(11) - 5;
            int dy = rand.nextInt(11) - 5;
            if (dx == 0) dx = 1;
            if (dy == 0) dy = 1;

            this.addBoid(new Point(x, y), new Point2D.Float(dx, dy));
        }
    }

    /**
     * Calcul de l’accélération pour un boid i selon les règles de Parker
     * - Cohésion : pcX, pcY
     * - Séparation : cX, cY
     * - Alignement : pvX, pvY
     */
    public Point2D f(int i) {
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
                if (pi.distance(pj) < 20) {
                    cX -= pj.x - pi.x;
                    cY -= pj.y - pi.y;
                }

                // 3) Alignement, en prenant en compte toute la population
                pvX += vj.getX();
                pvY += vj.getY();
            }
        }

        // Moyenne et ajustement pour cohésion et alignement, /100f et /8f corresponde aux coef des composantes de chaque forces
        pcX = (pcX / (boids.size() - 1) - pi.x) / 100f;
        pcY = (pcY / (boids.size() - 1) - pi.y) / 100f;
        pvX = (float) ((pvX / (boids.size() - 1) - vi.getX()) / 8f);
        pvY = (float) ((pvY / (boids.size() - 1) - vi.getY()) / 8f);

        return new Point2D.Float(pcX + cX + pvX, pcY + cY + pvY);
    }

    /**
     * Déplace les boids selon les règles de Parker + wrap-around + limite de vitesse
     * @param width largeur de l’écran
     * @param height hauteur de l’écran
     * @param radius rayon pour éviter la sortie exacte de l’écran
     */
    public void translate(int width, int height, int radius) {

        float vMax = 8f; // Vitesse maximale

        for (int i = 0; i < boids.size(); i++) {

            // 1) Calcul de l’accélération
            Point2D acc = this.f(i);

            // 2) Mise à jour de la vitesse
            float newVx = (float) velocities.get(i).getX() + (float) acc.getX();
            float newVy = (float) velocities.get(i).getY() + (float) acc.getY();

            // 3) Limitation de vitesse
            float speed = (float) Math.sqrt(newVx * newVx + newVy * newVy);
            if (speed > vMax) {
                newVx = (newVx / speed) * vMax;
                newVy = (newVy / speed) * vMax;
            }

            velocities.set(i, new Point2D.Float(newVx, newVy));

            // 4) Mise à jour de la position
            int newX = boids.get(i).x + (int) newVx;
            int newY = boids.get(i).y + (int) newVy;

            // 5) Wrap-around
            if (newX > width - radius) newX = radius;
            if (newX < radius) newX = width - radius;
            if (newY > height - radius) newY = radius;
            if (newY < radius) newY = height - radius;

            // 6) Mise à jour finale
            boids.set(i, new Point(newX, newY));
        }
    }

    /** Représentation textuelle des positions des boids */
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Point boid : this.boids) {
            s.append("x : ").append(boid.x)
             .append(" y : ").append(boid.y)
             .append("\n");
        }
        return s.toString();
    }
}