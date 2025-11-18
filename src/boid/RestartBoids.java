package boid;

import gui.GUISimulator;

public class RestartBoids extends EventBoids{


    public RestartBoids(int date, GUISimulator gui, Boids boids) {
        super(date, gui, boids);
    }

    /** on reset et on dessine */
    @Override
    public void execute(){
        super.getBoids().reset();
    }
}
