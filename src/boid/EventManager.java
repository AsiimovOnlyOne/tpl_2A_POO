package boid;

import java.util.PriorityQueue;

    public class EventManager {
        private int currentDate;
        private PriorityQueue<Event> eventQueue;

        public EventManager() {
            this.currentDate = 0;
            this.eventQueue = new PriorityQueue<>();
        }

        public void addEvent(Event e) {
            eventQueue.add(e);
        }

        public boolean isFinished() {
            return eventQueue.isEmpty();
        }

        public void next() {
            if (eventQueue.isEmpty()) return;

            // Récupère l'événement le plus proche dans le temps
            Event e = eventQueue.poll();
            // Avance la date courante
            currentDate = e.getDate();
            // Exécute l'événement
            e.execute();
        }

        public int getCurrentDate() {
            return currentDate;
        }
    }


