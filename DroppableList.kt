import kotlin.jvm.internal.Intrinsics.Kotlin

abstract class DroppableList<E>: ArrayList<E>() {
    fun drop(k: Int): List<E> {
        val resultSize = size - k

        when {
            resultSize <= 0 -> return emptyList<E>()
            else -> {
                val list = ArrayList<E>(resultSize)
                for (index in k..size -1) {
                    list.add(this[index])
                }
                return list
            }
        }
    }

    fun <E> drop(k: Int, list: List<E>) : List<E> {
        val resultSize = list.size - k
        when {
            resultSize <= 0 -> return  emptyList<E>()
            else -> {
                val newList = ArrayList<E>(resultSize)
                for (index in k..size -1) {
                    newList.add(list[index])
                }
                return newList
            }
        }
    }

    fun <E> List<E>.drop(k: Int) : List<E> {
        val resultSize = size - k
        when {
            resultSize <= 0 -> return emptyList<E>()
            else -> {
                val list = ArrayList<E>(resultSize)
                for (index in k..size -1) {
                    list.add(this[index])
                }
                return list
            }
        }
    }
}