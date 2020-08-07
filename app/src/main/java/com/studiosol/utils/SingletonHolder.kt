/**
 * @author Christophe Beyls
 * Classe que encapsula o comportamento genérico de um Singleton fornecendo para outras
 * classes a implmentação do padrão por meio de uma instancia sua.
 * Fonte: https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 *
 */
package com.studiosol.utils

open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}