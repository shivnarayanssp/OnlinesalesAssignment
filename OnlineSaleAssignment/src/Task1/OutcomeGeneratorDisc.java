package Task1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OutcomeGeneratorDisc {
    private Map<Integer, Integer> probabilities;

    public OutcomeGeneratorDisc(Map<Integer, Integer> probabilities) {
        this.probabilities = probabilities;
    }

    public int generateOutcome() {
        int totalProb = 0;
        for (int probability : probabilities.values()) {
            totalProb += probability;
        }

        int randNum = new Random().nextInt(totalProb);
        int cumulativeProb = 0;

        for (Map.Entry<Integer, Integer> entry : probabilities.entrySet()) {
            cumulativeProb += entry.getValue();
            if (randNum < cumulativeProb) {
                return entry.getKey();
            }
        }

        return -1; // Return -1 if no outcome is found
    }

    public static void main(String[] args) {
        Map<Integer, Integer> probabilities = new HashMap<>();
        probabilities.put(1, 10);
        probabilities.put(2, 30);
        probabilities.put(3, 15);
        probabilities.put(4, 15);
        probabilities.put(5, 30);
        probabilities.put(6, 0);

        OutcomeGeneratorDisc outcomeGenerator = new OutcomeGeneratorDisc(probabilities);

        int numOccurrences = 1000;

        // Counters for each outcome
        Map<Integer, Integer> outcomeCounts = new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            outcomeCounts.put(i, 0);
        }

        for (int i = 0; i < numOccurrences; i++) {
            int result = outcomeGenerator.generateOutcome();
            if (result != -1) {
                outcomeCounts.put(result, outcomeCounts.get(result) + 1);
            }
        }

        System.out.println("On triggering the event " + numOccurrences + " times:");

        // Display outcome counts
        for (Map.Entry<Integer, Integer> entry : outcomeCounts.entrySet()) {
            System.out.println("Outcome " + entry.getKey() + " appeared " + entry.getValue() + " times");
        }
    }
}
