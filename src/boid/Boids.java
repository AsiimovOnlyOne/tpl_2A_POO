package boid;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe abstraite Boids (factorisation du code car plusieur type de boids): simulation de boids selon les règles classiques
 * - Cohésion : se rapprocher du centre de masse des voisins
 * - Séparation : éviter les collisions
 * - Alignement : suivre la vitesse moyenne des voisins
 *
 * On a inclu les options suivantes :
 * - les boids qui sortent d'un côté réapparaissent de l'autre
 * - Limitation de vitesse maximale
 */
public abstract class Boids {

    // Positions initiales pour reset
    private ArrayList<Point> initBoids;
    private ArrayList<Point2D> initVelocities;

    // Positions et vitesses actuelles
    private ArrayList<Point> boids;
    private ArrayList<Point2D> velocities;

    // largeur et hauteur de la famille de boids
    private double largeur;
    private double hauteur;

    private float vMax; // Vitesse maximale

    private Color couleur; // couleur de la famille

    private int taille;

    protected double getlargeur(){
        return this.largeur;
    }

    protected double gethauteur(){
        return this.hauteur;
    }

    protected double getvMax(){
        return this.vMax;
    }

    protected Color getCouleur(){
        return this.couleur;
    }

    protected int getTaille(){
        return this.taille;
    }


    protected void setlargeur(double largeur){
        this.largeur = largeur;
    }
    protected void sethauteur(double hauteur){
        this.hauteur = hauteur;
    }
    protected void setvMax(double vMax){
        this.vMax = (float) vMax;
    }
    protected void setcouleur(Color couleur){
        this.couleur = couleur;
    }
    protected void setTaille(int taille){
        this.taille = taille;
    }

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
    public void generateBoids(int width, int height){
        randomBoids(width,height);
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
    public void randomBoids(int width, int height) {
        Random rand = new Random();
        boids.clear();
        velocities.clear();

        for (int i = 0; i < taille; i++) {
            int x = (int) (rand.nextInt((int) (width - largeur)) + hauteur);
            int y = (int) (rand.nextInt((int) (height - largeur)) + hauteur);

            // Vitesse aléatoire entre -0.25*vMax et 0.25*vMax, pas vitesse initiale nulle
            int dx = (int) (rand.nextInt(11) - 0.25*vMax);
            int dy = (int) (rand.nextInt(11) - 0.25*vMax);
            if (dx == 0) dx = 1;
            if (dy == 0) dy = 1;

            this.addBoid(new Point(x, y), new Point2D.Float(dx, dy));
        }
    }

    /**
     * Calcul de l’accélération pour un boid i selon les règles de Parker
     */
    protected abstract Point2D f(int i, Boids b);

    /**
     * Déplace les boids selon les règles de Parker + wrap-around + limite de vitesse
     * @param width largeur de l’écran
     * @param height hauteur de l’écran
     * @param b l'autre population de boids
     */
    public void translate(int width, int height, Boids b) {


        for (int i = 0; i < boids.size(); i++) {

            // 1) Calcul de l’accélération
            Point2D acc = this.f(i, b);

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

            // 5) si un boid sort de l'écran, il reapparait de l'autre
            if (newX > width - hauteur) newX = (int) hauteur;
            if (newX < hauteur) newX = (int) (width - hauteur);
            if (newY > height - hauteur) newY = (int) hauteur;
            if (newY < hauteur) newY = (int) (height - hauteur);

            // 6) Mise à jour finale
            boids.set(i, new Point(newX, newY));
        }
    }

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