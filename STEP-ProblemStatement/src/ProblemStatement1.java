import java.util.*;

public class ProblemStatement1 {

    static class TokenBucket {
        int tokens;
        long lastRefill;
        int maxTokens;

        TokenBucket(int max) {
            maxTokens = max;
            tokens = max;
            lastRefill = System.currentTimeMillis();
        }
    }

    static HashMap<String, TokenBucket> clients = new HashMap<>();

    public static void main(String[] args) {

        System.out.println(checkRateLimit("abc123"));
        System.out.println(checkRateLimit("abc123"));
        System.out.println(getRateLimitStatus("abc123"));
    }

    static String checkRateLimit(String id) {

        if (!clients.containsKey(id)) {
            clients.put(id, new TokenBucket(1000));
        }

        TokenBucket b = clients.get(id);

        long now = System.currentTimeMillis();

        if (now - b.lastRefill >= 3600000) {
            b.tokens = b.maxTokens;
            b.lastRefill = now;
        }

        if (b.tokens > 0) {
            b.tokens--;
            return "Allowed (" + b.tokens + " requests remaining)";
        } else {
            long retry = (3600000 - (now - b.lastRefill)) / 1000;
            return "Denied (0 requests remaining, retry after " + retry + "s)";
        }
    }

    static String getRateLimitStatus(String id) {

        if (!clients.containsKey(id)) {
            return "No data";
        }

        TokenBucket b = clients.get(id);

        int used = b.maxTokens - b.tokens;
        long reset = (b.lastRefill + 3600000) / 1000;

        return "{used: " + used + ", limit: " + b.maxTokens + ", reset: " + reset + "}";
    }
}