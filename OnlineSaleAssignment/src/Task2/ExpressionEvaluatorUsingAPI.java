package Task2;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpressionEvaluatorUsingAPI {
    private static final int MAX_CONCURRENT_REQUESTS = 50; // Max concurrent requests allowed by the API
    private static final int EXPRESSIONS_PER_SECOND = 500; // Expected expressions to evaluate per second
    private static final int RATE_LIMIT = 50; // API rate limit (requests per second)
    private static final int REFILL_INTERVAL = 1000 / RATE_LIMIT; // Time interval to refill tokens (in milliseconds)

    private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_REQUESTS);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final AtomicInteger tokens = new AtomicInteger(MAX_CONCURRENT_REQUESTS);

    // Method to evaluate expressions using the API
    public void evaluateExpressions(List<String> expressions) {
        for (String expression : expressions) {
            CompletableFuture.runAsync(() -> {
                try {
                    semaphore.acquire(); // Acquire permit to send the request
                    // Call API to evaluate expression here
                    // Handle API response and display result on console
                    System.out.println("Evaluated: " + expression + " -> Result: [API Response]");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release(); // Release permit after API call
                }
            });
        }
    }

    // Token refilling at a fixed rate
    private void refillTokens() {
        scheduler.scheduleAtFixedRate(() -> {
            int currentTokens = tokens.get();
            if (currentTokens < MAX_CONCURRENT_REQUESTS) {
                tokens.compareAndSet(currentTokens, MAX_CONCURRENT_REQUESTS);
            }
        }, 0, REFILL_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        ExpressionEvaluatorUsingAPI evaluator = new ExpressionEvaluatorUsingAPI();
        evaluator.refillTokens(); // Start token refill mechanism

        // Example expressions to evaluate
        List<String> expressions = List.of("2 * 4 * 4",
                "5 / (7 - 5)", "sqrt(16)", "sqrt(-3^2 - 4^2)");

        // Divide expressions into chunks to manage the rate limit
        for (int i = 0; i < expressions.size(); i += EXPRESSIONS_PER_SECOND / RATE_LIMIT) {
            List<String> subList = expressions.subList(i, Math.min(i + EXPRESSIONS_PER_SECOND / RATE_LIMIT, expressions.size()));
            evaluator.evaluateExpressions(subList);
            try {
                Thread.sleep(1000 / RATE_LIMIT); // Delay to comply with the rate limit
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
