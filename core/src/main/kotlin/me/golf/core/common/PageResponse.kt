package me.golf.core.common

data class PageResponse<T>(
    val data: List<T>,
    val total: Int,
) {
    
    companion object {
        fun <T> empty(): PageResponse<T> {
            return PageResponse(
                data = emptyList(),
                total = 0,
            )
        }
        
        fun<T> create(data: List<T>, total: Int): PageResponse<T> {
            return PageResponse(
                data = data,
                total = total,
            )
        }
    }
}
