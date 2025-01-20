import javax.xml.stream.events.Characters

class Student4(
    val name: String,
    val studentNumber: String,
    email: String
) {
    fun isValidName(name: String): Validation {
        return if (name.trim().length > 2)
            Valid
        else
            Invalid(listOf("Name $name is too short"))
    }

    fun isValidStudentNumber(studentNumber: String): Validation {
        return if (studentNumber.all { Character.isDigit(it) })
            Valid
        else
            Invalid(listOf("Student number must be only digits: $studentNumber"))
    }

    fun isValidEmail(email: String): Validation {
        return if (email.contains("@"))
            Valid
        else
            Invalid(listOf("Email must contain an '@' symbol"))
    }

}