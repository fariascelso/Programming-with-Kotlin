//Métodos estáticos

/*
Um Singleton é um padrão de projeto (design pattern) que limita a instanciação de uma dada classe a uma instância.
Depois de criada, a instância estará ativa enquanto durar a execução do programa em questão.
*  */

object Singleton {
        private var count = 0
        fun doSomething(): Unit {
            println("Calling a doSomething(${++count} call/-s in total)")
        }
}

open class SingletonParent(var x:Int) {
    fun something(): Unit{
        println("X = $x")
    }
}

object SingletonDerive: SingletonParent(10) {}

