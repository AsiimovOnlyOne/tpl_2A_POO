package boid;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Boids {

    private ArrayList<Point> initBoids;
    private ArrayList<Point> boids;
    private ArrayList<Point2D> initVelocities;
    private ArrayList<Point2D> velocities;

    public Boids() {
        this.boids = new ArrayList<>();
        this.velocities = new ArrayList<>();
    }

    public int size() {
        return this.boids.size();
    }

    public void generateBoids(int n, int width, int height, int radius){
        randomBoids(n, width,height,radius);
        setInit();
    }

    public void setInit() {
        this.initBoids = new ArrayList<>(this.boids);
        this.initVelocities = new ArrayList<>(this.velocities);
    }

    public void reset() {
        this.boids = new ArrayList<>(this.initBoids);
        this.velocities = new ArrayList<>(this.initVelocities);
    }

    public ArrayList<Point> getCoordBoids(){
        return new ArrayList<>(this.boids);
    }

    public ArrayList<Point2D> getSpeedBoids(){
        return new ArrayList<>(this.velocities);
    }

    public void addBoid(Point boid, Point2D velocity){
        this.boids.add(boid);
        this.velocities.add(velocity);
    }

    public void randomBoids(int n, int width, int height, int radius) {
        Random rand = new Random();
        this.boids.clear();
        this.velocities.clear();

        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(width - 2 * radius) + radius;
            int y = rand.nextInt(height - 2 * radius) + radius;
            // vitesse aléatoire entre -5 et 5 (évite 0)
            int dx = rand.nextInt(11) - 5;
            int dy = rand.nextInt(11) - 5;
            if (dx == 0) dx = 1;
            if (dy == 0) dy = 1;

            this.addBoid(new Point(x, y), new Point2D.Float((float)dx, (float)dy));
        }
    }

    public Point2D f(int i) {
        Point pi = boids.get(i);
        Point2D vi = velocities.get(i);

        //1 Boids try to fly towards the centre of mass of neighbouring boids
        float pcX = 0;
        float pcY = 0;
        //2 Boids try to keep a small distance away from other objects
        float cX = 0;
        float cY = 0;
        //3 Boids try to match velocity with near boids
        float pvX = 0;
        float pvY = 0;
        //4 Boids stays on the screen
        float vX = 0;
        float vY = 0;

        for (int j = 0; j < boids.size(); j++) {
            if (i != j) {
                Point pj = boids.get(j);
                Point2D vj = velocities.get(j);
                //1
                pcX += pj.x;
                pcY += pj.y;
                //2
                if (pi.distance(pj) < 20) {
                    cX -= pj.x - pi.x;
                    cY -= pj.y - pi.y;
                }
                //3
                pvX += vj.getX();
                pvY += vj.getY();
                //4
                if (pj.x < 200) vX = 100;
                if (pj.x > 800) vX = -100;
                if (pj.y < 200) vY = 100;
                if (pj.y > 800) vY = -100;
            }
        }
        pcX = (float)(pcX / (float)(boids.size() - 1.0) - (float)pi.x) / (float)100;
        pcY = (float)(pcY / (float)(boids.size() - 1.0) - (float)pi.y) / (float)100;
        pvX = (float)(pvX / (float)(boids.size() - 1.0) - vi.getX()) / (float)8;
        pvY = (float)(pvY / (float)(boids.size() - 1.0) - vi.getY()) / (float)8;

        return new Point2D.Float(pcX + cX + pvX + vX, pcY + cY + pvY + vY);   
    }

    /** Déplace les boids en suivant le pseudocode de Parker */
    public void translate(int width, int height, int radius) {
        for (int i = 0; i < boids.size(); i++) {
            Point2D acc = this.f(i);
            velocities.set(i, new Point2D.Float((float)(velocities.get(i).getX() + acc.getX()), (float)(velocities.get(i).getY() + acc.getY())));
            boids.set(i, new Point(boids.get(i).x + (int)(velocities.get(i).getX()), boids.get(i).y + (int)(velocities.get(i).getY())));
        }
    }


    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (Point boid : this.boids) {
            s.append("x : ");
            s.append(boid.x);

            s.append(" y : ");
            s.append(boid.y);

            s.append("\n");
        }
        return s.toString();
    }

}
