package boid;

public abstract class Event implements Comparable<Event>{
    protected int date; // date ou timestamp de l'événement

    public Event(int date) {
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    // Méthode exécutée à la date de l'événement
    public abstract void execute();

    // Pour trier dans une PriorityQueue
    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.date, other.date);
    }
}

