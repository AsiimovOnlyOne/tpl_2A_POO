package cell.event;

/**
 * Classe abstraite représentant un événement planifiable dans le temps.
 * Elle implémente Comparable pour être triée chronologiquement dans la file d'attente.
 */
public abstract class Event implements Comparable<Event> {

    private final long date; // Date d'exécution de l'événement (ex: numéro du "tick")

    public Event(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    /**
     * Méthode à implémenter par les sous-classes (ex: GridEvent, BallMoveEvent).
     * Contient l'action à effectuer (ex: faire avancer la grille).
     */
    public abstract void execute();

    /**
     * Comparaison pour la PriorityQueue : les événements avec la plus petite date passent en premier.
     */
    @Override
    public int compareTo(Event other) {
        return Long.compare(this.date, other.date);
    }
}