import java.awt.Button
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

class Controller {
    private var clicks: Int = 0

    val button: Button
        get() {
            TODO()
        }

    fun enableHook() {
        //Essa é uma classe interna anônima que estende MouseAdapter. Não tem um nome especificado após a palavra-chave object, tornando-a anônima.
        button.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {clicks++}
        })
    }
}