package Task1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OutcomeGeneratorCoin {
    private Map<String, Integer> outcomes;

    public OutcomeGeneratorCoin(Map<String, Integer> outcomes) {
        this.outcomes = outcomes;
    }

    public String generateOutcome() {
        int totalProb = 0;
        for (int probability : outcomes.values()) {
            totalProb += probability;
        }

        int randNum = new Random().nextInt(totalProb);
        int cumulativeProb = 0;

        for (Map.Entry<String, Integer> entry : outcomes.entrySet()) {
            cumulativeProb += entry.getValue();
            if (randNum < cumulativeProb) {
                return entry.getKey();
            }
        }

        return null; // Return null if no outcome is found
    }

    public static void main(String[] args) {
        Map<String, Integer> outcomes = new HashMap<>();
        outcomes.put("Head", 35);
        outcomes.put("Tail", 65);

        OutcomeGeneratorCoin outcomeGenerator = new OutcomeGeneratorCoin(outcomes);

        // Counters for outcomes
        int headCount = 0;
        int tailCount = 0;

        int numOccurrences = 1000;
        for (int i = 0; i < numOccurrences; i++) {
            String result = outcomeGenerator.generateOutcome();
            if (result != null) {
                if (result.equals("Head")) {
                    headCount++;
                } else if (result.equals("Tail")) {
                    tailCount++;
                }
            }
        }

        System.out.println("On triggering the event " + numOccurrences + " times:");
        System.out.println("Head appeared " + headCount + " times");
        System.out.println("Tail appeared " + tailCount + " times");
    }
}
