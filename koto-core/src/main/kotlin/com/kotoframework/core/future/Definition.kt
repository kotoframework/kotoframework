package com.kotoframework.core.future

import com.kotoframework.beans.Unknown
import com.kotoframework.function.associate.AssociateAction
import com.kotoframework.function.associate.AssociateWhere
import com.kotoframework.function.select.SelectAction
import com.kotoframework.function.select.SelectWhere
import com.kotoframework.interfaces.KPojo

/**
 * Created by ousc on 2022/7/19 21:59
 */
typealias Statement<T, E> = (T) -> E
typealias Statement2<T, E> = (T, E, AssociateAction<T, E, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement3<T, E, F> = (T, E, F, AssociateAction<T, E, F, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement4<T, E, F, G> = (T, E, F, G, AssociateAction<T, E, F, G, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement5<T, E, F, G, H> = (T, E, F, G, H, AssociateAction<T, E, F, G, H, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement6<T, E, F, G, H, I> = (T, E, F, G, H, I, AssociateAction<T, E, F, G, H, I, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement7<T, E, F, G, H, I, J> = (T, E, F, G, H, I, J, AssociateAction<T, E, F, G, H, I, J, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement8<T, E, F, G, H, I, J, K> = (T, E, F, G, H, I, J, K, AssociateAction<T, E, F, G, H, I, J, K, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, K, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement9<T, E, F, G, H, I, J, K, L> = (T, E, F, G, H, I, J, K, L, AssociateAction<T, E, F, G, H, I, J, K, L, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, K, L, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement10<T, E, F, G, H, I, J, K, L, M> = (T, E, F, G, H, I, J, K, L, M, AssociateAction<T, E, F, G, H, I, J, K, L, M, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, K, L, M, Unknown, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement11<T, E, F, G, H, I, J, K, L, M, N> = (T, E, F, G, H, I, J, K, L, M, N, AssociateAction<T, E, F, G, H, I, J, K, L, M, N, Unknown, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, K, L, M, N, Unknown, Unknown, Unknown, Unknown, Unknown>
typealias Statement12<T, E, F, G, H, I, J, K, L, M, N, O> = (T, E, F, G, H, I, J, K, L, M, N, O, AssociateAction<T, E, F, G, H, I, J, K, L, M, N, O, Unknown, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, K, L, M, N, O, Unknown, Unknown, Unknown, Unknown>
typealias Statement13<T, E, F, G, H, I, J, K, L, M, N, O, P> = (T, E, F, G, H, I, J, K, L, M, N, O, P, AssociateAction<T, E, F, G, H, I, J, K, L, M, N, O, P, Unknown, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, K, L, M, N, O, P, Unknown, Unknown, Unknown>
typealias Statement14<T, E, F, G, H, I, J, K, L, M, N, O, P, Q> = (T, E, F, G, H, I, J, K, L, M, N, O, P, Q, AssociateAction<T, E, F, G, H, I, J, K, L, M, N, O, P, Q, Unknown, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, K, L, M, N, O, P, Q, Unknown, Unknown>
typealias Statement15<T, E, F, G, H, I, J, K, L, M, N, O, P, Q, R> = (T, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, AssociateAction<T, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, Unknown>) -> AssociateWhere<T, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, Unknown>
typealias Statement16<T, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S> = (T, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, AssociateAction<T, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>) -> AssociateWhere<T, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S>
