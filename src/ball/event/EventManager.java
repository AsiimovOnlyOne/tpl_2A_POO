package ball.event;

import java.util.PriorityQueue;

public class EventManager {

    private long currentDate;
    // PriorityQueue trie automatiquement les événements selon leur date (grâce au compareTo)
    private final PriorityQueue<Event> eventQueue;

    public EventManager() {
        this.currentDate = 0;
        this.eventQueue = new PriorityQueue<>();
    }

    public void addEvent(Event e) {
        this.eventQueue.add(e);
    }

    public void next() {
        currentDate++;
        // On exécute tous les événements prévus pour cette date ou avant
        while (!eventQueue.isEmpty() && eventQueue.peek().getDate() <= currentDate) {
            Event e = eventQueue.poll();
            assert e != null;
            e.execute();
        }
    }

    public void reset() {
        this.currentDate = 0;
        this.eventQueue.clear();
    }

}
