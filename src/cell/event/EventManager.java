package cell.event;

import java.util.PriorityQueue;

public class EventManager {

    private long currentDate; // Temps actuel de la simulation
    private final PriorityQueue<Event> eventQueue;

    public EventManager() {
        this.currentDate = 0;
        this.eventQueue = new PriorityQueue<>();
    }

    /**
     * Ajoute un événement futur à la file d'attente.
     */
    public void addEvent(Event e) {
        this.eventQueue.add(e);
    }

    /**
     * Fait avancer le temps d'un pas et exécute tous les événements en retard ou à l'heure.
     */
    public void next() {
        currentDate++;
        // Tant qu'il y a des événements et que leur date est arrivée ou passée
        while (!eventQueue.isEmpty() && eventQueue.peek().getDate() <= currentDate) {
            Event e = eventQueue.poll(); // On retire l'événement de la file
            assert e != null;
            e.execute();                 // On l'exécute
        }
    }

    /**
     * Réinitialise le temps et vide la file (utile pour le bouton Restart).
     */
    public void reset() {
        this.currentDate = 0;
        this.eventQueue.clear();
    }

}
