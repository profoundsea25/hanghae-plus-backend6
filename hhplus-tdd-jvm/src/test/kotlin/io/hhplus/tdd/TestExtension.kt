package io.hhplus.tdd

import org.mockito.Mockito

// Mockito.any() 직접 사용 시, NPE 발생하여 한 번 Wrapping
fun <T> any(type: Class<T>): T = Mockito.any(type)
