package cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Grille implémentant le modèle de ségrégation de Schelling.
 * Dans ce modèle :
 * - État 0 = logement vacant
 * - État 1, 2, 3... = différents groupes de population
 * Une famille est "insatisfaite" si elle a trop de voisins différents d'elle et les familles insatisfaites déménagent vers des logements vacants.
 */
public class GridSchelling extends AbstractGrid {
    
    /** Nombre de groupes de population différents (sans compter les logements vacants) */
    private final int numberOfPopulationGroups;
    
    /** Seuil de tolérance : nombre maximum de voisins différents acceptés
     *  Si une famille a plus de 'tolerance' voisins différents, elle déménage */
    private final int toleranceThreshold;
    
    /** Générateur de nombres aléatoires pour l'initialisation et les déménagements */
    private final Random random;

    /**
     * Constructeur de la grille de Schelling.
     * size Taille de la grille (size x size)
     * numberOfPopulationGroups Nombre de groupes de population différents
     * toleranceThreshold Seuil de tolérance (nombre max de voisins différents)
     */
    public GridSchelling(int size, int numberOfPopulationGroups, int toleranceThreshold) {
        super(size); 
        this.numberOfPopulationGroups = numberOfPopulationGroups;
        this.toleranceThreshold = toleranceThreshold;
        this.random = new Random();
        
        initializePopulation();
    }

    /**
     * Initialise la population de manière aléatoire.
     */
    private void initializePopulation() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // 20% de chance d'avoir un logement vacant
                if (Math.random() < 0.2) {
                    cells[row][col].setState(0);  // Logement vacant
                } else {
                    // Assignation aléatoire d'un groupe de population (1 à numberOfPopulationGroups)
                    int populationGroup = 1 + random.nextInt(numberOfPopulationGroups);
                    cells[row][col].setState(populationGroup);
                }
            }
        }
        setInit(); // Sauvegarde l'état initial pour permettre un restart
    }

    @Override
    protected int nextCellState(int row, int col) {
        return cells[row][col].getState();
    }

    /**
     * Algorithme :
     * 1. Identifie tous les logements vacants
     * 2. Identifie toutes les familles insatisfaites
     * 3. Fait déménager les familles insatisfaites vers des logements vacants
     */
    @Override
    public void next() {
        // Liste des positions de tous les logements vacants
        List<int[]> vacantHomes = new ArrayList<>();
        
        // Liste des positions de toutes les familles insatisfaites
        List<int[]> unsatisfiedFamilies = new ArrayList<>();

        // Première passe : identification des logements vacants et familles insatisfaites
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int currentState = cells[row][col].getState();
                
                if (currentState == 0) {
                    // Logement vacant trouvé
                    vacantHomes.add(new int[]{row, col});
                } else if (isFamilyUnsatisfied(row, col, currentState)) {
                    // Famille insatisfaite trouvée
                    unsatisfiedFamilies.add(new int[]{row, col});
                }
            }
        }

        // Deuxième passe : déménagement des familles insatisfaites
        for (int[] currentHomePosition : unsatisfiedFamilies) {
            // S'il n'y a plus de logements vacants, on arrête
            if (vacantHomes.isEmpty()) {
                break;
            }
            
            // Choix aléatoire d'un logement vacant
            int randomIndex = random.nextInt(vacantHomes.size());
            int[] newHomePosition = vacantHomes.remove(randomIndex);
            
            // Récupération du groupe de la famille qui déménage
            int familyGroup = cells[currentHomePosition[0]][currentHomePosition[1]].getState();

            // Déménagement : la famille s'installe dans le nouveau logement
            cells[newHomePosition[0]][newHomePosition[1]].setState(familyGroup);
            
            // L'ancien logement devient vacant
            cells[currentHomePosition[0]][currentHomePosition[1]].setState(0);

            // L'ancien logement est maintenant vacant et disponible
            vacantHomes.add(currentHomePosition);
        }
    }

    /**
     * Détermine si une famille est insatisfaite de son voisinage.
     * Une famille est insatisfaite si elle a strictement plus de 'toleranceThreshold' voisins appartenant à un groupe différent du sien.
     */
    private boolean isFamilyUnsatisfied(int row, int col, int familyGroup) {
        int differentNeighborsCount = 0;
        
        // Parcours des 8 voisins
        for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
            for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {
                // On ne compte pas la cellule elle-même
                if (deltaRow == 0 && deltaCol == 0) {
                    continue;
                }
                
                // Calcul des coordonnées du voisin
                int neighborRow = (row + deltaRow + size) % size;
                int neighborCol = (col + deltaCol + size) % size;
                
                int neighborGroup = cells[neighborRow][neighborCol].getState();
                
                // On compte les voisins occupés et différents
                if (neighborGroup != 0 && neighborGroup != familyGroup) {
                    differentNeighborsCount++;
                }
            }
        }
        
        // La famille est insatisfaite si elle a trop de voisins différents
        return differentNeighborsCount > toleranceThreshold;
    }
}