package com.kotoframework.core.future

/**
 * Created by ousc on 2022/7/19 21:59
 */
typealias Statement<T, E> = (T) -> E
typealias Statement2<T, E, F> = (T, E) -> F
typealias Statement3<T, E, F, G> = (T, E, F) -> G
typealias Statement4<T, E, F, G, H> = (T, E, F, G) -> H
typealias Statement5<T, E, F, G, H, I> = (T, E, F, G, H) -> I
typealias Statement6<T, E, F, G, H, I, J> = (T, E, F, G, H, I) -> J
typealias Statement7<T, E, F, G, H, I, J, K> = (T, E, F, G, H, I, J) -> K
typealias Statement8<T, E, F, G, H, I, J, K, L> = (T, E, F, G, H, I, J, K) -> L
typealias Statement9<T, E, F, G, H, I, J, K, L, M> = (T, E, F, G, H, I, J, K, L) -> M
typealias Statement10<T, E, F, G, H, I, J, K, L, M, N> = (T, E, F, G, H, I, J, K, L, M) -> N
