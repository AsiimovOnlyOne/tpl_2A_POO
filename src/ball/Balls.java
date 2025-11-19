package ball;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Gestionnaire d'un ensemble de balles pour une simulation 2D.
 */
public class Balls {

    // Stocke la position actuelle des balles (x, y)
    private ArrayList<Point> balls;

    // Stocke la vitesse actuelle des balles (vecteur dx, dy)
    private final ArrayList<Point> velocities;

    // Sauvegarde des positions initiales pour la fonction reset()
    private ArrayList<Point> initBalls;

    public Balls() {
        this.balls = new ArrayList<>();
        this.velocities = new ArrayList<>();
    }

    /**
     * Initialise n balles aléatoires et sauvegarde l'état initial.
     */
    public void generateBalls(int n, int width, int height, int radius){
        randomBalls(n, width, height, radius);
        setInit(); // Sauvegarde immédiate après génération
    }

    /**
     * Crée une "copie de sauvegarde" des positions actuelles.
     */
    public void setInit() {
        this.initBalls = new ArrayList<>(this.balls);
        // BUG POTENTIEL : Tu ne sauvegardes pas les vitesses initiales (initVelocities).
        // Lors d'un reset, les balles reprendront leur place, mais garderont leur direction actuelle.
    }

    /**
     * Restaure les balles à leurs positions sauvegardées.
     */
    public void reset() {
        this.balls = new ArrayList<>(this.initBalls);
        // Ici, les vitesses ne sont pas remises à zéro ou à l'état initial.
    }

    /**
     * Retourne une copie de la liste des balles pour l'affichage (évite la modification externe de la liste principale).
     */
    public ArrayList<Point> getCoordBalls(){
        return new ArrayList<>(this.balls);
    }

    public void addBall(Point ball){
        this.balls.add(ball);
    }

    /**
     * Génère n balles avec des positions et vitesses aléatoires.
     */
    public void randomBalls(int n, int width, int height, int radius) {
        Random rand = new Random();
        this.balls.clear();
        this.velocities.clear();

        for (int i = 0; i < n; i++) {
            // Génération en s'assurant que la balle est entièrement dans le cadre (marge = radius)
            int x = rand.nextInt(width - 2 * radius) + radius;
            int y = rand.nextInt(height - 2 * radius) + radius;
            this.addBall(new Point(x, y));

            // Vitesse aléatoire entre -5 et 5
            int dx = rand.nextInt(11) - 5;
            int dy = rand.nextInt(11) - 5;

            // Évite que la balle soit immobile
            if (dx == 0) dx = 1;
            if (dy == 0) dy = 1;
            velocities.add(new Point(dx, dy));
        }
    }

    /** * Moteur physique : Déplace les balles et gère les collisions avec les murs.
     */
    public void translate(int width, int height, int radius) {
        for (int i = 0; i < balls.size(); i++) {
            Point p = balls.get(i);
            Point v = velocities.get(i); // Récupération de la vitesse correspondante

            // Calcul théorique de la nouvelle position
            int newX = p.x + v.x;
            int newY = p.y + v.y;

            // --- Gestion des rebonds (Collision Murs) ---

            // Mur Gauche OU Mur Droit
            if (newX < radius || newX > width - radius) {
                v.x = -v.x; // Inversion vitesse horizontale
                newX = p.x + v.x; // On force le mouvement dans la nouvelle direction pour ne pas "coller" au mur
            }

            // Mur Haut OU Mur Bas
            if (newY < radius || newY > height - radius) {
                v.y = -v.y; // Inversion vitesse verticale
                newY = p.y + v.y;
            }

            // Mise à jour des listes
            // Important : On crée un NOUVEAU point, on ne modifie pas l'ancien (p.x = ...)
            // Cela protège 'initBalls' car 'initBalls' pointe toujours vers les vieux objets Points.
            balls.set(i, new Point(newX, newY));
            velocities.set(i, v);
        }
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Point ball : this.balls) {
            s.append("x : ").append(ball.x).append(" y : ").append(ball.y).append("\n");
        }
        return s.toString();
    }
}