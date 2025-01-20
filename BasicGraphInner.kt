//Exemplo de uma classe aninhada NÃO estática, ou seja, a classe InnerLine tem acesso a membros privados da classe externa BasicGraphInner

class BasicGraphInner(graphName: String) {
    private var name: String = graphName

    init {
        name = graphName
    }

    inner class InnerLine(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun draw(): Unit {
            println("Drawing line from ($x1:$y1) to ($x2:$y2) for graph $name")
        }
    }

    fun draw(): Unit {
        println("Drawing the graph $name")
    }

}