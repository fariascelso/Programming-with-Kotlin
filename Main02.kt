import java.awt.*
import java.awt.Shape
import java.awt.image.BufferedImage
import java.io.*
import java.lang.reflect.Modifier
import java.math.BigDecimal
import java.math.RoundingMode
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Connection
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.sqrt

fun main02() {

    fun <T> p(value: T) {
        println(value.toString())
    }

    // single-line functions
    fun plusOne(x: Int) = x + 1

// explicits annotations
    val explicitType: Number = 12.3
    p(explicitType)

// creation literal numbers

    val int = 123
    p(int)

    val long = 23456L
    p(long)

    val double = 12.34
    p(double)

    val float = 23.34F
    p(float)

    val hexadecimal = 0xAB
    p(hexadecimal)

    val binary = 0b01010101
    p(binary)

// converting numbers

    val entire = 123
    p(entire)

    val longEntire = entire.toLong()
    p(longEntire)

    val numberFloat = 12.34F
    p(numberFloat)

    val numberDoubleFloat = entire.toFloat()
    p(numberFloat)

// operators bitwise (bit a bit)
    val leftShift = 1 shl 2
    p(leftShift)

    val rightShift = 1 shr 2
    p(rightShift)

    val unsignedShift = 1 ushr 2
    p(unsignedShift)

    val and = 1 and 0x00001111
    p(and)

    val or = 1 or 0x00001111
    p(or)

    val xor = 1 xor 0x00001111
    p(xor)

    val inv = 1.inv()
    p(inv)

// BOOLEAN

    val x = 1
    val y = 2
    val z = 2

    val isTrue = x < y && x < z
    p(isTrue)

    val alsoTrue = x == y || y == z
    p(alsoTrue)

// CHARS

    val string = "string with \n new line"
    p(string)

    val rawString = """
        raw string is super useful for strings that span many lines """

    p(rawString)

//ARRAY

    val array = arrayOf(1, 2, 3)
    p(array[0])

    val perfectSquares = Array(10) { k -> k * k }
    p(perfectSquares)

    val element1 = array[0]
    val element2 = array[1]
    array[2] = 5

    p(element1)
    p(element2)
    p(array[2])

// TEMPLATE STRINGS OR INTERPOLATION STRINGS

    val name = "John Doe"
    val concat = "Hello " + name
    val concatInterpolation = "Interpolation: $concat. O nome tem ${name.length} caracteres"

    p(concat)
    p(concatInterpolation)

//RANGE

    val aToZ = "a".."z"
    val isTrueInterval = "c" in aToZ

    p(isTrueInterval)

    val oneToNine = 1..9
    val isFalseInterval = 11 in oneToNine

    p(isFalseInterval)

    val countingDown = 100.downTo(0)
    p(countingDown)
    val rangeTo = 10.rangeTo(20)
    p(rangeTo)

    val oneToFifty = 1..50
    val addNumbers = oneToFifty.step(2)

    p(oneToFifty)
    p(addNumbers)

    val countingDownEvenNumbers = (2..100).step(2).reversed()
    p(countingDownEvenNumbers)

//LOOPS

//    while (true) {
//        println("This will print out for a long time!")
//    }

    val list = listOf(1, 2, 3, 4)

    for (k in list) {
        println(k)
    }

    val set = setOf(1, 2, 3, 4, 6, 7)

    for (k in set) {
        println(k)
    }

    val oneToTen = 1..10
    for (k in oneToTen) {
        for (j in 1..5) {
            println(k * j)
        }
    }

    val string1 = "print my characteres"
    for (char in string1) {
        println(char)
    }

    for (index in array.indices) {
        println("Element $index is ${array[index]}")
    }

// EXCEPTIONS

    fun readFile(path: Path): Unit {
        val input = Files.newInputStream(path)
        try {
            var byte = input.read()
            while (byte != -1) {
                println(byte)
                byte = input.read()
            }
        } catch (e: IOException) {
            println("Error reading from file. Error was ${e.message}")
        } finally {
            input.close()
        }
    }

    val filePath = Paths.get("/Users/celso.fariasuseblu.com.br/Documents/exemplo.txt")
    readFile(filePath)

//Instantiate class

    val a = File("/mobydick.doc")
    val b = File("/mobydick.doc")

//reference x Structure
    val sameRef = a === b

    val sameStruct = a == b

    p(sameRef)
    p(sameStruct)

//Expression THIS

    class Person(name: String) {
        fun printMe() = println(this)
    }

    val person: Person = Person("Celso Farias")

//Escope

    person.printMe()
    class Building(val address: String) {
        inner class Reception(telephone: String) {
            fun printAddress() = println(this@Building.address)
        }
    }

    val building: Building = Building("Street Palestina")

    building.Reception("66999246159").printAddress()

//Modififiers of visibility

// public | internal | protected | private

    class Person2 {
        public fun age(): Int = 28
    }

    val person2: Person2 = Person2()

    person2.age()

    p(person2.age())

    println("hello".startsWith("h"))

// Flow control with expressions
    val data = Date()
    val today = if (data.year == 2023) true else false
    p(data.year)
    fun isZero(x: Int): Boolean {
        return if (x == 0) true else false
    }

    p(today)
    p(isZero(0))

    val success = try {
        readFile(filePath)
        true
    } catch (e: IOException) {
        false
    }

//Syntax null

    var str: String? = null

    p(str)

//Type checking and casting

    fun isString(any: Any): Boolean {
        return if (any is String) true else false
    }

    p(isString("Test"))

    fun printStringLength(any: Any) {
        if (any is String) {
            println(any.length)
        }
    }

    p(printStringLength("Celso"))

    fun isEmptyString(any: Any): Boolean {
        return any is String && any.length == 0
    }

    p(isEmptyString(""))

    fun isNotStringOrEmpty(any: Any): Boolean {
        return any !is String || any.length == 0
    }

    p(isNotStringOrEmpty(5))

    val any = "/home/users"
    val string2: String? = any as? String
    val file: File? = any as? File

//Expression WHEN

//WHEN with value

    fun whatNumber(x: Int) {
        when (x) {
            0 -> println("x is zero")
            1 -> println("x is 1")
            else -> println("X is neither 0 or 1")
        }
    }

    whatNumber(5)

    fun isMinOrMax(x: Int): Boolean {
        val isZero = when (x) {
            Int.MAX_VALUE -> true
            Int.MIN_VALUE -> true
            else -> false
        }
        return isZero
    }

    println(isMinOrMax(0))

    fun isZeroOrOne(x: Int): Boolean {
        return when (x) {
            0, 1 -> true
            else -> false
        }
    }

    println(isZeroOrOne(1))

    fun isAbs(x: Int): Boolean {
        return when (x) {
            Math.abs(x) -> true
            else -> false
        }
    }

    println(isAbs(5))

    fun isSingleDigit(x: Int): Boolean {
        return when (x) {
            in -9..9 -> true
            else -> false
        }
    }

    p(isSingleDigit(-10))

    fun isDieNumber(x: Int): Boolean {
        return when (x) {
            in listOf(1, 2, 3, 4, 5, 6) -> true
            else -> false
        }
    }

    p(isDieNumber(9))

    fun startWithFoo(any: Any): Boolean {
        return when (any) {
            is String -> any.startsWith("Foo")
            else -> false
        }
    }

    p(startWithFoo("Foo"))

    fun whenWithoutArgs(x: Int, y: Int) {
        when {
            x < y -> println("x is less than y")
            x > y -> println("x is greater than y")
            else -> println("x must equal y")
        }
    }

    p(whenWithoutArgs(5, 6))

// RETURN FUNCTION

    fun addTwoNumbers(a: Int, b: Int): Int {
        return a + b
    }

    fun largestNumbers(a: Int, b: Int, c: Int): Int {
        fun largest(a: Int, b: Int): Int {
            if (a > b) return a
            else return b
        }
        return largest(largest(a, b), largest(b, c))
    }

    p(addTwoNumbers(2, 5))
    p(largestNumbers(1, 2, 3))

    fun printLessThanTwo() {
        val list = listOf(36, 2, 3, 4)
        list.forEach(fun(x) {
            if (x < 2) println(x)
            else return
        })
        println("This line will still execute")
    }

    printLessThanTwo()

    fun printUntilStop() {
        val list = listOf("a", "b", "stop", "c")
        list.forEach stop@{
            if (it == "stop") return@stop
            println(it)
        }
    }

    printUntilStop()

    fun printUntilStop2() {
        val list = listOf("a", "b", "stop", "c")
        list.forEach {
            if (it == "stop") return@forEach
            println(it)
        }
    }

    printUntilStop2()

//TYPE HIERARCHY

// CLASS

    class Human constructor(val firstName: String, val lastName: String, val age: Int?) {}

    val human1 = Human("Celso", "Farias", 28)
    val human2 = Human("John", "Doe", 28)

    p(human1.lastName)
    p(human2)

    class PersonExample(val firstName: String, val lastName: String, val age: Int?) {
        constructor (firstName: String, lastName: String) : this(firstName, lastName, null)

        init {
            require(firstName.trim().length > 0) { "Invalid argument" }
            require(lastName.trim().length > 0) { "Invalid argument" }
            if (age != null) {
                require(age >= 0 && age < 150) { "Invalid age argument" }
            }
        }
    }

    val personagem = PersonExample("Celso", "Farias", 100)

    p(personagem.firstName)
    p(personagem.lastName)
    p(personagem.age)

    val personagem2 = PersonExample("Jane", "Smith")

    p(personagem2.age)

    class Database internal constructor(connection: Connection) {

    }

    class Person3(firstName: String, lastName: String, howOld: Int?) {
        private val name: String
        private val age: Int?

        init {
            this.name = "$firstName, $lastName"
            this.age = howOld
        }

        fun getName(): String = this.name
        fun getAge(): Int? = this.age
    }

    val personagem56 = Person3("Celso", "Farias", 35)

    p(personagem56.getName())
    p(personagem56.getAge())

    val line = BasicGraph.Line(1, 11, 2, 22)
    line.draw()

    val line2 = BasicGraphInner("Teste")

    p(line2.draw())
    p(line2.InnerLine(10, 20, 30, 40).draw())

    val expressionThis = A()

    val expressionThisInnerClass = expressionThis.B().foo("Testando a expressão this")

    p(expressionThisInnerClass)

    val controller = Controller()

//p(controller.enableHook())

    val customer = Customer(id = 1, name = "Celso Farias", address = "Rua Tal")

    p(customer)

    val getDay = Day.values()

    val planet = Planet.values()

    p(planet)

    val w = Word.HELLO
    w.print()

    val firstChar = Static()

    p(firstChar.showFirstCharacter("Kotlin is cool"))

    p(Singleton.doSomething())

    p(SingletonDerive.something().toString())

    p(Student.create("Jack Wallace").name)

    val myDocument = DocumentImpl()

    val fileIS = "/Users/celso.fariasuseblu.com.br/Documents/exemplo.txt"
    val fileInputStreamGlobal: InputStream = FileInputStream(fileIS)

    try {

        val fileInputStream: InputStream = FileInputStream(fileIS)
        val buffer = ByteArray(1024)
        var bytesRead: Int

        // Enquanto houver dados para ler, continue lendo e imprimindo
        while (fileInputStream.read(buffer).also { bytesRead = it } != -1) {
            val data = buffer.copyOfRange(0, bytesRead)
            println(String(data))
        }

        myDocument.save(fileInputStream)

        fileInputStream.close()

    } catch (e: Exception) {
        e.printStackTrace()
    }

    p(myDocument.name)
    p(myDocument.size)
    p(myDocument.version)
    p(myDocument.getDescription())

    val cardVisa = CardType.VISA

    val payment = Payment(BigDecimal("10.50"))

    val cardPayment = CardPayment(
        amount = payment.amount,
        number = "0947",
        expireDate = Date(),
        type = cardVisa
    )

    p(cardPayment.number)
    p(cardPayment.expireDate)
    p(cardPayment.amount)
    p(cardPayment.type)

    val chequePayment = ChequePayment(
        amount = payment.amount,
        name = "ChequePaymentTest",
        bankId = "237"
    )

    val amphibiousCar = AmphibiousCar("Ford")

    p(amphibiousCar.name)
    amphibiousCar.drive()
    amphibiousCar.saill()

    val textDocument = TextDocument("Kotlin Book")

    textDocument.print()
    textDocument.save(fileInputStreamGlobal)

    val derivedContainer = DerivedContainer()
    println("DerivedContainer.fieldA: ${derivedContainer.fieldA}")

//    val container : Container = derivedContainer
//    println("fieldA:${container.fieldA}")

    derivedContainer.javaClass.superclass.getDeclaredFields().forEach { field ->
        field.trySetAccessible()
        println(
            "Field: ${field.name}, ${Modifier.toString(field.modifiers)}, Value = ${field.get(derivedContainer)}"
        )
    }

    derivedContainer.javaClass.getDeclaredFields().forEach { field ->
        field.trySetAccessible()
        println(
            "Field: ${field.name}, ${Modifier.toString(field.modifiers)}, Value = ${field.get(derivedContainer)}"
        )
    }

    val alwaysOne = AlwaysOne()

    p(alwaysOne.someMethod())

    val e1 = Ellipsis()
    e1.Height = 10.0
    e1.Width = 12.0

    val e2 = Ellipsis()
    e2.XLocation = 100
    e2.YLocation = 96

    e1.Height = 21.00
    e1.Width = 19.0

    val r1 = RectangleXX()
    r1.XLocation = 49
    r1.YLocation = 45
    r1.Width = 10.0
    r1.Height = 10.0

    val shapes = listOf<Shape>(e1, e2, r1)
    val selected: Shape? = shapes.firstOrNull { shape -> shape.isHit(50, 52) }
    if (selected == null) {
        println("There is no shape at point (50 , 52)")
    } else {
        println("A shape of type ${selected.javaClass.simpleName} has been selected.")
    }

    val baseB = BaseB("BaseB:value")
    val derivedB = DerivedB()
    derivedB.propertyFoo = "on the spot value"
    println("BaseB:${baseB.propertyFoo}")
    println("DerivedB:${derivedB.propertyFoo}")

    val pngImage = PNGImage()
    val os = ByteArrayOutputStream()
    pngImage.save(os)

    val panel = java.awt.Panel(Rectangle2(10, 100, 30, 100))
    println("Panel height: ${panel.getHeight()}")
    println("Panel witdh: ${panel.getWidth()}")

    val tree = IntBinaryTree.IntBinaryTreeNode(
        IntBinaryTree.IntBinaryTreeNode(
            IntBinaryTree.EmptyNode(),
            2,
            IntBinaryTree.EmptyNode()
        ),
        1,
        IntBinaryTree.IntBinaryTreeNode(
            IntBinaryTree.EmptyNode(),
            3,
            IntBinaryTree.EmptyNode()
        )
    )

    println("Árvore binária: \n Valor = ${tree.value}\nEsquerda = ${tree.left}\nDireita = ${tree.right}")

    fun toCollection(tree: IntBinaryTree): Collection<Int> = when (tree) {
        is IntBinaryTree.EmptyNode -> emptyList<Int>()
        is IntBinaryTree.IntBinaryTreeNode -> toCollection(tree.left) + tree.value + toCollection(tree.right)
    }

    p(toCollection(tree))

//FUNÇÕES

    fun hello(): String = "Hello World"
    fun hello2(name: String, location: String): String = "Hello to you $name at $location"
    p(hello())
    p(hello2("Celso Farias", "Brazil"))

    fun print1(str: String): Unit {
        println(str)
    }

    fun print2(str: String) {
        println(str)
    }

    p(print1("Function with : Unit"))
    p(print2("Function with without"))

    fun square(k: Int) = k * k
    fun square2(k: Int): Int = k * k

    p(square(3))
    p(square2(3))

    fun concat1(a: String, b: String) = a + b
    fun concat2(a: String, b: String): String {
        return a + b
    }

    p(concat1("A", "B"))
    p(concat2("C", "D"))

    val rectangle3 = RectangleObj
    val rectangle4 = RectangleObj02

    rectangle3.printArea(3, 3)
    rectangle4.printArea(4, 4)
    rectangle4.printArea2(5, 5)

    val fizBuzz = FizzBuzz

    fizBuzz.fizzBuzz(9, 27)
    fizBuzz.fizzBuzz2(10, 30)
    fizBuzz.fizzBuzz3(20, 40)
    fizBuzz.fizzBuzz4(500, 1000)

    foo(11)

    val sentence = "a kidness of ravens"

    p(
        sentence.regionMatches(
            thisOffset = 14,
            other = "Red Ravens",
            otherOffset = 4,
            length = 6,
            ignoreCase = true
        )
    )

    fun deleteFiles(filePattern: String, recursive: Boolean, ignoreCase: Boolean, deleteDirectories: Boolean): Unit {}

    deleteFiles("*.jpg", true, true, false)
    deleteFiles("*.jpg", recursive = true, ignoreCase = true, deleteDirectories = false)

    p(
        sentence.endsWith(suffix = "ravens", ignoreCase = true)
    )

    fun createThreadPool(threadCount: Int): ExecutorService {
        return Executors.newFixedThreadPool(threadCount)
    }

    fun createThreadPool(): ExecutorService {
        val threadCount = Runtime.getRuntime().availableProcessors()
        return createThreadPool(threadCount)
    }

    p(createThreadPool().toString())
    p(createThreadPool(1))

    fun divide(divisor: BigDecimal, scale: Int = 0, roundingMode: RoundingMode = RoundingMode.UNNECESSARY): BigDecimal {
        return BigDecimal(10.00)
    }

    p(divide(BigDecimal(12.34)))
    p(divide(BigDecimal(12.34), 8))
    p(divide(BigDecimal(12.34), 8, RoundingMode.HALF_DOWN))
    p(divide(BigDecimal(12.34), roundingMode = RoundingMode.HALF_DOWN))

    val student = Student
    val student2 = Student2(name = "Celso Farias", registered = true, credits = 5)

    p(student)
    p(student2.name)

    val listD = listOf(1, 2, 3)

    val droppedList = listD.drop(2)

    p(listD)
    p(droppedList)

    val sub = Submarine()
    sub.fire()
    sub.submerge()

    fun Any?.safeEquals(other: Any?): Boolean {
        if (this == null && other == null) return true
        if (this == null) return false
        return this.equals(other)
    }

    val actor = Mappings()

    p(actor)
    actor.add("Celso")

    p(actor.toString())
    p(actor.hashCode())

    fun Int.Companion.random(): Int {
        val random = Random()
        return random.nextInt()
    }

    val integerInt = Int.random()

    p(integerInt)

    fun positiveRoot(k: Int): Double {
        require(k >= 0)
        return sqrt(k.toDouble())
    }

    p(positiveRoot(81))

    fun negativeRoot(k: Int): Double {
        return -sqrt(k.toDouble())
    }

    p(negativeRoot(-81))

    fun roots(k: Int): Array<Double> {
        require(k >= 0)
        val root = sqrt(k.toDouble())
        return arrayOf(root, -root)
    }

    p(roots(64))

    val roots = Roots(81.00, -81.00)

    p(roots)

    fun roots3(k: Int): Pair<Double, Double> {
        val root = sqrt(k.toDouble())
        return Pair(root, -root)
    }

    p(roots3(81))

    val (pos, neg) = roots3(16)

    p(pos)
    p(neg)

    val pair = "London" to "UK"

    p(pair)

//    infix fun concat(other: String): String {
//        return this + other
//    }

    val account = Account()
    account.add(100.00)

    val accountInfix = InfixAccount()
    accountInfix add 200.00

    p(account.balance)
    p(accountInfix.balance)

    val pair1 = Pair("london", "paris")
    val pair2 = "london" to "paris"

    val map1 = mapOf(Pair("London", "UK"), Pair("Bucharest", "Romania"))
    val map2 = mapOf("London" to "UK", "Bucharest" to "Romania")

    p(pair1)
    p(pair2)
    p(map1)
    p(map2)

    val array9 = arrayOf(1, 2, 3)
    val element = array9[0]

    p(array9.size)
    p(element)

    val m1 = Matrix(1, 2, 3, 4)
    val m2 = Matrix(5, 6, 7, 8)
    val m3 = m1 + m2
    val m4 = m1.plus(m2)

    println("${m3.a}, ${m3.b}, ${m3.c}, ${m3.d}")
    println("${m4.a}, ${m4.b}, ${m4.c}, ${m4.d}")

    val ints = arrayOf(1, 2, 3, 4)
    val a2 = 5 in ints

    val b5 = ints.contains(3)

    val c = 5 !in ints
    val d = ints.contains(5)

    p(ints)
    p(a2)
    p(b5)
    p(c)
    p(d)

    val list2 = listOf(1, 2, 3, 4)
    val head = list[0]

    p(head)

    val board = ChessBoard()

    board[0, 4] = Piece.Queen

    println(board[0, 4])

    fun newSeed(): Long = 9223372036854775807

    val random = Random(newSeed())
    val longs = listOf(random, random, random)

    p(longs[0])

    val min = Min

    min(1, 2, 3)
    min(1L, 2L, 3)

    p(min)

    val aa = BingoNumber("Key to the Door", 21)
    val bb = BingoNumber("Jump and Jive", 35)
    println(aa < bb)
    println(bb < aa)

    var counter = Counter(1)
    p(counter.k)
    counter = counter + 3
    p(counter.k)
    counter += 2
    p(counter.k)

    val printHello = { println("Hello Baby") }
    printHello()

    val printMessage = { message: String -> println(message) }
    printMessage("My name is")

    fun fibonacci(k: Int): Int = when (k) {
        0 -> 1
        1 -> 1
        else -> fibonacci(k - 1) + fibonacci(k - 2)
    }

    fun fact(k: Int): Int {
        if (k == 0) return 1
        else return k * fact(k - 1)
    }

    fun fact02(k: Int): Int {

        fun factTail(m: Int, n: Int): Int {
            if (m == 0) return n
            else return factTail(m - 1, m * n)
        }

        return factTail(k, 1)
    }

    fun fact03(k: Int): Int {

        tailrec fun factTail(m: Int, n: Int): Int {
            if (m == 0) return n
            else return factTail(m - 1, m * n)
        }

        return factTail(k, 1)
    }

    fun multiPrint(vararg strings: String) {
        for (string in strings)
            println(string)
    }

    fun multiPrint02(prefix: String, vararg strings: String) {
        println(prefix)
        for (string in strings)
            println(string)
    }

    fun multiPrint03(prefix: String, vararg strings: String, suffix: String) {
        println(prefix)
        for (string in strings)
            println(string)

        println(suffix)
    }

    multiPrint("a", "b", "c")
    multiPrint02("Prefix", "a", "b", "c")
    multiPrint03("Start", "a", "b", "c", suffix = "End")

    val arrayStrings = arrayOf("Horse", "Tiger", "Snake", "Elephant")

    multiPrint03("Start", *arrayStrings, suffix = "End")

    val strings = arrayOf("a", "b", "c", "d", "e")

    multiPrint03("Start", *strings, suffix = "end")

    val task = Runnable { println("Running") }

    Thread(task).apply { isDaemon }.start()

    val thread = Thread(task)
    thread.isDaemon
    thread.start()

    val outputPath = Paths.get("/Users/celso.fariasuseblu.com.br/Documents/").let {
        val path = it.resolve("output")
        path.toFile().createNewFile()
        path
    }

    val outputPath2 = Paths.get("/Users/celso.fariasuseblu.com.br/Documents/").run {
        val path = resolve("output")
        path.toFile().createNewFile()
        path
    }

    outputPath.toFile()

    val image = BufferedImage(10, 10, 10)

    val g2: Graphics2D = image.createGraphics()


    g2.stroke = BasicStroke(10F)
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
    g2.background = Color.BLACK

    with(g2) {
        stroke = BasicStroke(10F)
        setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
        background = Color.BLACK
    }

    fun readStringFromDataBase(): String = "Lazy"
    val lazyString = lazy { readStringFromDataBase() }

    p(lazyString.value)

    val input = Files.newInputStream(Paths.get("/Users/celso.fariasuseblu.com.br/Documents/exemplo.txt"))
    val byte = input.use { input.read() }

    repeat(10) { println("Oie") }

    fun neverEmpty(str: String) {
        require(str.isNotEmpty()) { "String should not be empty" }
        println(str)
    }

//neverEmpty("")

    fun <T> printRepeated(t: T, k: Int) {
        for (x in 0..k) {
            println(t)
        }
    }

    printRepeated("9", 5)

    fun <T> choose(t1: T, t2: T, t3: T): T {
        return when (Random().nextInt(3)) {
            0 -> t1
            1 -> t2
            else -> t3
        }
    }

    val r = choose(5, 7, 9)

    p(r)

    val counter2 = AtomicInteger(1)

    fun impure(k: Int): Int {
        return counter2.incrementAndGet() + k
    }

    val named = Named()
    println("My name is " + named.name)
    named.name = "new name"
    println("My name is " + named.name)

    val threadPool = Executors.newFixedThreadPool(4)
    threadPool.submit {
        println("I don't have a lot of work to do")
    }

//    threadPool.submit(Runnable {
//        println("I don't have a lot of work to do")
//    })

    fun cube(n: Int): Int = n * n * n

    p(cube(3))

    Console.clear()
    fun println(array: Array<String>) {}
    fun println(array: Array<Long>) {}

    @Throws(IOException::class)
    fun createDirectory(file: File) {
        if (file.exists())
            throw IOException("Directory already exists")
        file.createNewFile()
    }

    try {
        createDirectory(File("mobydick.txt"))
    } catch (e: Exception) {
        println("Deu ruim")
    }

    fun foo(str: String, fn: (String) -> String) {
        val applied = fn(str)
        println(applied)
    }

    fun foo2(str: String) {
        val reversed = str.reversed()
        println(reversed)
    }

    foo(str = "Higher order functions", fn = { it.reversed() })
    foo2(str = "Higher order functions")

}