package cell;

import gui.GUISimulator;
import gui.Simulable;
import java.awt.*;

public abstract class AbstractSimulator implements Simulable {

    protected AbstractGrid grid; // Modification : protected GridConway grid;

    public AbstractSimulator(GUISimulator gui) {

        gui.setSimulable(this);
    }

    protected abstract void draw();


    @Override
    public void next() {
        grid.next();
        draw();
    }

    @Override
    public void restart() {
        grid.reset();
        draw();
    }
}
