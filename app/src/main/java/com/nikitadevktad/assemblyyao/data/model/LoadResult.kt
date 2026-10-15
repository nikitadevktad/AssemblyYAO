package com.nikitadevktad.assemblyyao.data.model

data class LoadResult<T>(
    val data: T,
    val isFromCache: Boolean,
)
