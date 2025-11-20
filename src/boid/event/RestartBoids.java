package boid.event;

import boid.model.Boids;
import gui.GUISimulator;


/**
 * Sous classe d'evenement de Boid qui reset la famille de boid
 */

public class RestartBoids extends EventBoids {

    public RestartBoids(int date, GUISimulator gui, Boids boids) {
        super(date, gui, boids);
    }

    /** on reset */
    @Override
    public void execute(){
        super.getBoids().reset();
    }
}
