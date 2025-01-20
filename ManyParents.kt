import java.io.Closeable
import java.io.Serializable

class ManyParents : Serializable, Closeable, java.lang.AutoCloseable {
    override fun close() {
        TODO("Not yet implemented")
    }
}