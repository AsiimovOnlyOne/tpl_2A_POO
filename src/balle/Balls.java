package balle;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Balls {

    private ArrayList<Point> initBalls;
    private ArrayList<Point> balls;
    private final ArrayList<Point> velocities;

    public Balls() {
        this.balls = new ArrayList<>();
        this.velocities = new ArrayList<>();
    }

    public void generateBalls(int n, int width, int height, int radius){
        randomBalls(n, width,height,radius);
        setInit();
    }

    public void setInit() {
        this.initBalls = new ArrayList<>(this.balls);
    }

    public void reInit() {
        this.balls = new ArrayList<>(this.initBalls);
    }

    public ArrayList<Point> getCoordBalls(){
        return new ArrayList<>(this.balls);
    }

    public void addBall(Point ball){
        this.balls.add(ball);
    }

    public void randomBalls(int n, int width, int height, int radius) {
        Random rand = new Random();
        this.balls.clear();
        this.velocities.clear();

        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(width - 2 * radius) + radius;
            int y = rand.nextInt(height - 2 * radius) + radius;
            this.addBall(new Point(x, y));

            // vitesse aléatoire entre -5 et 5 (évite 0)
            int dx = rand.nextInt(11) - 5;
            int dy = rand.nextInt(11) - 5;
            if (dx == 0) dx = 1;
            if (dy == 0) dy = 1;
            velocities.add(new Point(dx, dy));
        }
    }

    /** Déplace les balles et rebondit sur les bords */
    public void translate(int width, int height, int radius) {
        for (int i = 0; i < balls.size(); i++) {
            Point p = balls.get(i);
            Point v = velocities.get(i);

            // mise à jour position
            int newX = p.x + v.x;
            int newY = p.y + v.y;

            // rebonds sur les bords
            if (newX < radius || newX > width - radius) {
                v.x = -v.x;
                newX = p.x + v.x;
            }
            if (newY < radius || newY > height - radius) {
                v.y = -v.y;
                newY = p.y + v.y;
            }

            balls.set(i, new Point(newX, newY));
            velocities.set(i, v);
        }
    }


    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Point ball : this.balls) {
            s.append("x : ");
            s.append(ball.x);

            s.append(" y : ");
            s.append(ball.y);

            s.append("\n");
        }
        return s.toString();
    }

}
