class Student3(val firstName: String, val lastName: String) {
    fun loadStudents(): List<String> {
       return listOf("Celso Farias", "Polyana Cristina", "Débora Farias")
    }
//    fun students(nameToMatch: String): List<String> {
//        return loadStudents().filter {
//            it.lastName == nameToMatch
//        }
//    }
}