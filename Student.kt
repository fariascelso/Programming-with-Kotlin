interface StudentFactory {
    fun create(name: String): Student
}
class Student private constructor(val name: String){
    companion object : StudentFactory {
        override fun create(name: String): Student {
            return Student(name)
        }
    }
}

/*

    -> O construtor do tipo Student foi marcado como private, logo, não pode ser chamado de nenhum lugar, exceto de dentro da classe Student ou do companion object

    -> A classe companion tem visibilidade total de todos os métodos e membros de Student.

*/