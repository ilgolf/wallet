package me.golf.core.common

data class PageResponse<T>(
    val data: List<T>,
    val totalElements: Long,
) {
    fun <R> map(transform: (T) -> R): PageResponse<R> {
        return PageResponse(
            data = data.map(transform),
            totalElements = totalElements,
        )
    }

    companion object {
        fun <T> empty(): PageResponse<T> {
            return PageResponse(
                data = emptyList(),
                totalElements = 0,
            )
        }
        
        fun<T> create(data: List<T>, totalElements: Long): PageResponse<T> {
            return PageResponse(
                data = data,
                totalElements = totalElements,
            )
        }
    }
}
