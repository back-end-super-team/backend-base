package backend.base.data.api;

public record ListResponse<T> (
        int totalRecords,
        T content
) {}
