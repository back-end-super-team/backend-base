package backend.backendbase.enums;

public enum RateLimitType {
    API,        // Specific API endpoint
    USER,       // Per user
    IP,        // Per IP address
    FUNCTION,   // Service/database calls
}
