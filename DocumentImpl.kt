import java.io.InputStream
import java.io.OutputStream

class DocumentImpl: Document {

    override val size: Long
        get() = 0

    override fun load(stream: OutputStream) {

    }

    override fun save(input: InputStream) {

    }
    override val version: Long
        get() = 0
}