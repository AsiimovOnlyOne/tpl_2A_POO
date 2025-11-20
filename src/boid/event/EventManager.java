package boid.event;

import java.util.PriorityQueue;

    public class EventManager {
        final PriorityQueue<Event> eventQueue;

        public EventManager() {
            this.eventQueue = new PriorityQueue<>();
        }

        public void addEvent(Event e) {
            eventQueue.add(e);
        }

        public void next() {
            if (eventQueue.isEmpty()) return;

            // Récupère l'événement le plus proche dans le temps
            Event e = eventQueue.poll();

            // Exécute l'événement
            e.execute();
        }

    }


