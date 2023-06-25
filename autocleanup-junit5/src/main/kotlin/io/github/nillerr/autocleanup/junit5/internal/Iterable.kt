package io.github.nillerr.autocleanup.junit5.internal

fun <A, B> Iterable<A>.pairWithNotNull(selector: (A) -> B?): List<Pair<A, B>> {
    return mapNotNull { source -> selector(source)?.let { other -> source to other } }
}
