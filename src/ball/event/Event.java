package ball.event;

public abstract class Event implements Comparable<Event> {

    private final long date; // La "date" ou le "temps" auquel l'événement se produit

    public Event(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    /**
     * Action à effectuer lorsque l'événement se déclenche.
     */
    public abstract void execute();

    /**
     * Permet de trier les événements par date (du plus proche au plus lointain).
     */
    @Override
    public int compareTo(Event other) {
        return Long.compare(this.date, other.date);
    }
}
