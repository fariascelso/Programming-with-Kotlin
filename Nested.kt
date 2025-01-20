
//Exemplo de uma classe aninhada estática

class BasicGraph(val name: String) {
    class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun draw(): Unit {
            println("Drawing line from ($x1:$y1) to ($x2:$y2)")
        }
    }
    fun draw(): Unit {
        println("Drawing the graph $name")
    }
}