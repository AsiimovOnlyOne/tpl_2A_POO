package ball.event;

import ball.Balls;

public class BallsMoveEvent extends Event {

    private final Balls balls;
    private final EventManager manager;
    // Paramètres nécessaires pour la méthode translate de Balls
    private final int width;
    private final int height;
    private final int radius;

    public BallsMoveEvent(long date, Balls balls, EventManager manager, int width, int height, int radius) {
        super(date);
        this.balls = balls;
        this.manager = manager;
        this.width = width;
        this.height = height;
        this.radius = radius;
    }

    @Override
    public void execute() {
        // 1. Effectuer l'action (déplacer les balles)
        balls.translate(width, height, radius);

        // 2. Reprogrammer l'événement pour le "tick" suivant (date + 1)
        // C'est ce qui crée la boucle d'animation infinie
        manager.addEvent(new BallsMoveEvent(this.getDate() + 1, balls, manager, width, height, radius));
    }
}
