package backend.base.data.api;

public record PageResponse<T> (
        long totalRecords,
        int totalPage,
        int page,
        int size,
        T content
) {}
