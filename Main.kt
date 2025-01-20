import io.kotlintest.matchers.Matcher
import io.kotlintest.matchers.Result
import io.kotlintest.matchers.shouldThrow
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable
import java.math.BigDecimal
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import java.util.function.Supplier
import java.util.logging.Level
import kotlin.concurrent.thread
import kotlin.concurrent.withLock
import kotlin.math.sqrt
import kotlin.reflect.KClass
import kotlin.sequences.Sequence
import kotlin.reflect.KParameter
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

fun main() {

    fun <T> p(value: T) {
        println(value.toString())
    }

    // HIGHER ORDERED FUNCTION

    val integers = listOf(22, 23, 24, 25, 26, 27, 28, 39)

    val evens = mutableListOf<Int>()
    val odds = mutableListOf<Int>()

    for (k in integers) {
        if (k % 2 == 0)
            evens.add(k)
        else
            odds.add(k)
    }

    p(evens)
    p(odds)

    val oddsHOF = integers.filter { it % 2 == 1 }
    val evensHOF = integers.filter { it % 2 == 0 }

    p(oddsHOF)
    p(evensHOF)

    fun bar(): (String) -> String = { str -> str.reversed() }

    val reverse = bar()

    p(reverse("Celso"))
    p(reverse("Farias"))

    fun modulo(k: Int): (Int) -> Boolean = { it % k == 0 }

    val oddsHOF02 = integers.filter(modulo(1))
    val evensHOF02 = integers.filter(modulo(2))
    val mod3 = integers.filter(modulo(3))

    p(oddsHOF02)
    p(evensHOF02)
    p(mod3)

    val isEven: (Int) -> Boolean = modulo(2)

    p("Assignment Function ")

    p(listOf(1, 2, 3, 4).filter(isEven))
    p(listOf(5, 6, 7, 8).filter(isEven))

    val isEvenLiteral: (Int) -> Boolean = { it % 2 == 0 }
    val isEvenLiteralOtherOption = { k: Int -> k % 2 == 0 }

//    val counter = AtomicInteger(0)
//    val cores = Runtime.getRuntime().availableProcessors()
//    val threadPool = Executors.newFixedThreadPool(cores)
//
//    threadPool.submit {
//        println("I am task number ${counter.incrementAndGet()}")
//    }
//
//    var containsNegative = false
//
//    p(integers.forEach {
//        if (it < 0)
//            containsNegative = true
//    })

    //ANONYMOUS FUNCTION

    fun(a: String, b: String): String = a + b

    val evensAnonymousFunction = integers.filter(fun(k: Int) = k % 2 == 0)
    p("evensAnonymousFunction")
    p(evensAnonymousFunction)

    val evensAnonymousFunctionWithoutParameters = integers.filter(fun(k) = k % 2 == 0)
    p("evensAnonymousFunctionWithoutParameters")
    p(evensAnonymousFunctionWithoutParameters)

    //REFERENCE TO HIGHER LEVEL FUNCTIONS

    fun isEven(k: Int): Boolean = k % 2 == 0

    integers.filter { isEven(it) }

    //REFERENCE

    integers.filter(::isEven)

    //REFERENCE FUNCTIONS MEMBERS AND FUNCTIONS OF EXTENSION

    fun Int.isOdd(): Boolean = this % 3 == 0

    integers.filter { it.isOdd() }

    p(integers.filter { it.isOdd() })

    p(integers.filter(Int::isOdd))

    fun foo2(a: Double, b: Double, f: (Double, Double) -> Double) = f(a, b)

    foo2(1.0, 2.0, { a, b -> Math.pow(a, b) })
    p(foo2(1.0, 2.0, Math::pow))

    fun String.equalsIgnoreCase(other: String) = this.toLowerCase() == other.toLowerCase()

    p(
        listOf("foo", "moo", "boo".filter {
            (String::equalsIgnoreCase)("bar", it.toString())
        })
    )

    p(
        listOf("foo", "baz", "BAR").filter("bar"::equalsIgnoreCase)
    )

    fun foo3(fn: String.() -> Boolean) {
        "string".fn()
    }

    val subString = fun String.(substr: String): Boolean = this.contains(substr)
    p("hello".subString("ello"))

    // COMPOSIÇÃO DE FUNÇÕES

    fun <A, B, C> compose(fn1: (A) -> B, fn2: (B) -> C): (A) -> C = { a ->
        val b = fn1(a)
        val c = fn2(b)
        c
    }

    val f = String::length
    val g = Any::hashCode
    val fog = compose(f, g)

    p(fog)

    p(fog("What is the hash of my length"))

    infix operator fun <P1, R, R2> Function1<P1, R>.times(fn: (R) -> R2): (P1) -> R2 = {
        fn(this(it))
    }

    val fog2 = f * g

    p(fog2("Celso").hashCode())

    fun <T : AutoCloseable, U> whitResource(resource: T, fn: (T) -> U): U {
        try {
            return fn(resource)
        } finally {
            resource.close()
        }
    }


    fun characterCount(fileName: String): Int {
        val input = Files.newInputStream(Paths.get(fileName))
        return whitResource(input) {
            input.buffered().reader().readText().length
        }
    }

    fun characterCountKilobytes(fileName: String): Int {
        val input = Files.newInputStream(Paths.get(fileName))
        return whitResource(input, {
            input.buffered().reader().readText().length
        }, { it * 1024 })
    }

    fun characterCountKilobytesExpanded(fileName: String): Int {
        val input = Files.newInputStream(Paths.get(fileName))

        val size = try {
            input.buffered().reader().readText().length
        } finally {
            input.close()
        }

        val fn: (Int) -> Int = { it * 1024 }
        return fn(size)
    }

    val filePath = "/Users/celso.fariasuseblu.com.br/Documents/exemplo.txt"

    p(characterCount(filePath))
    p(characterCountKilobytes(filePath))
    p(characterCountKilobytesExpanded(filePath))

    fun compute(logger: (String) -> Unit) {}

    fun log(level: Level, appender: Appendable, msg: String) {}
    fun logger(level: Level, appender: Appendable, msg: String) {}

    log(Level.WARNING, System.out, "Começando a execução")

    val logger = ::logger.curried()(Level.SEVERE)(System.out)
    logger("my message")

    val logger3: (Level) -> (Appendable) -> (String) -> Unit = ::logger.curried()
    val logger2: (Appendable) -> (String) -> Unit = logger3(Level.SEVERE)
    val logger1: (String) -> Unit = logger2(System.out)

    p(logger)
    logger1("My message")

    p(logger3)
    p(logger2)
    p(logger1)

    //MEMOIZATION

    fun fibonacci(k: Int): Long = when (k) {
        0 -> 1
        1 -> 1
        else -> fibonacci(k - 1) + fibonacci(k - 2)
    }

    p(fibonacci(2))

    val map = mutableMapOf<Int, Long>()

    fun memoizationFibonacci(k: Int): Long {
        return map.getOrPut(k) {
            when (k) {
                0 -> 1
                1 -> 1
                else -> memoizationFibonacci(k - 1) + memoizationFibonacci(k - 2)
            }
        }
    }

    //p(fibonacci(50))
    p(memoizationFibonacci(50))

    val memoizationCharacterCount = memoize(::characterCount)
    val memoizationCharacterCountSintaxePoint = ::characterCount.memoized()

    // EITHER + FOLD

    data class User(val name: String, val admin: Boolean)

    val addresses = Address("Cidade", "78780-000")

    equals("foobar", "foobar")

    "foobar" shouldEqual "foobar"

    val listOfNames: List<String> = listOf("Celso", "Poly", "Andre", "Benjamin", "Caroll")
    listOfNames should contain("Caroll")

    val student = Student4(name = "Estudante 01", studentNumber = "98", email = "email@email.com")

    val validation = student
        .isValidName(student.name) +
            student.isValidStudentNumber(student.studentNumber) +
            student.isValidEmail(student.studentNumber)

    p(validation)
//
//    val postCodes = getCurrentUser().rightProjection()
//        .map { getUserAddresses(it) }
//        .map { addresses }
//        .getOrElse { addresses }


    val withPrivateSetter = WithPrivateSetter(10)
    println("withPrivateSetter: ${withPrivateSetter.SomeProperty}")

//    val withInheritance = WithInheritanceDerived(true)
//    withInheritance.doSomething()
//    println("withInheritance:${withInheritance.isAvailable}")


    val container = Container02()
    container.initProperty(DelayedInstance(10))
    println("with delayed initialization:Number = ${container.delayedInitProperty.number}")

    val record = Record("111")
    println(record.id)

    val measure = Measure()
    measure.writeTimestamp = System.currentTimeMillis()
    println("Current measure taken at: ${measure.writeTimestamp}")

    val propsByMap = PropsByMap()
    println("Props with map: p1=${propsByMap.p1}")
    println("Props with map: p2=${propsByMap.p2}")

    propsByMap.p1 = 100

    println("Props with map: p2=${propsByMap.p1}")

    val player = Player(mapOf("name" to "Alex Jones", "age" to 28, "height" to 1.82))
    p("Player ${player.name} is ${player.age} ages old and is ${player.height} cm tall")

    val trivial = Trivial()
    println("Trivial flag is :${trivial.flag}")

    val withLazyProperty = WithLazyProperty()

    val total = withLazyProperty.foo + withLazyProperty.foo

    p("Lazy Property total: $total")

    val onChange = WithObservableProp()

    onChange.value = 10
    onChange.value = -10

    val positiveVal = OnlyPositiveValues()
    positiveVal.value = 100
    p("positiveVal value is ${positiveVal.value}")

    positiveVal.value = -100
    p("positiveVal value is ${positiveVal.value}")

    positiveVal.value = 111
    p("positiveVal value is ${positiveVal.value}")

    val nonNull = NonNullProp()
    nonNull.value = "Kotlin rocks"
    println("Non null value is: ${nonNull.value}")

    val country = Country("Brazil")
    val city = City("Rio de Janeiro", country)
    val address = Address06("Street 123", "12345", city)
    val person = Person("John Doe", address)

    // Testando a função getCountryName
    val countryName = getCountryName(person)
    val countryNameCompacted = getCountryNameCompacted(person)

    // Exibindo o resultado
    println("Country Name: $countryName")
    println("Country Name: $countryNameCompacted")

    //Operator Force
    val nullableName: String? = "George"
    val name: String = nullableName!!

    val nullableAddress: Address? = null
    val postcode: String = nullableAddress?.postCode ?: "default_postcode"

    //Casting Seguro

    val location: Any = "London"
    val safeString: String? = location as? String
    val safeInt: Int? = location as? Int

    //Optional

    val optionName: Optional<String> = Optional.of("William")
    val empty: Optional<String> = Optional.empty()

    fun lookupAddress(postcode: String): String? {
        return ""
    }

    //KClass
    val name02 = "George"
    val kclass: KClass<out String> = name02::class
    val kclass02: KClass<String> = String::class

    //Reflexion
    fun createInteger(kclass: KClass<PositiveInteger>): PositiveInteger? {
        return kclass.objectInstance
    }
//
//    val props = Properties()
//    props.load(Files.newInputStream(Paths.get("/Users/celso.fariasuseblu.com.br/Documents/exemplo.txt")))
//    val classNames = (props.getProperty("ingesters") ?: "").split(',')
//
//    val ingesters = classNames.map {
//        Class.forName(it).kotlin.createInstance() as Ingester
//    }
//
//    ingesters.forEach { it.ingest() }

    fun <T : Any> printConstructors(kclass: KClass<T>) {
        kclass.constructors.forEach {
            println(it.parameters)
        }
    }

    printConstructors(Kingdom::class)

    fun createKingdom(name: String, ruler: String, peaceful: Boolean): Kingdom {
        val constructor = Kingdom::class.constructors.find {
            it.parameters.size == 3
        } ?: throw RuntimeException("Construtor não compatível")
        return constructor.call(name, ruler, peaceful)
    }

    createKingdom("Celso Farias", "Ruler 01", true)

    p(createKingdom("Celso Farias", "Ruler 01", true))

    fun createPlugin(className: String): Plugin {
        val kclass = Class.forName(className).kotlin
        assert(kclass.constructors.size == 1) { "Only supply plugins with a single constructor" }

        val constructor = kclass.constructors.first()
        assert(constructor.parameters.size == 1) { "Only supply plugins with one parameter" }

        val parameter: KParameter = constructor.parameters.first()

        val map = when (parameter.type.jvmErasure) {
            java.sql.Connection::class -> {
                val conn = DriverManager.getConnection("some_jdbc_connection_url")
                mapOf(parameter to conn)
            }

            java.util.Properties::class -> {
                val props = Properties()
                mapOf(parameter to props)
            }

            java.nio.file.FileSystem::class -> {
                val fs = FileSystems.getDefault()
                mapOf(parameter to fs)
            }

            else -> throw RuntimeException("Unsupported type")
        }
        return constructor.callBy(map) as Plugin
    }

    val kclassCObj = Aircraft::class
    val companionKClass = kclassCObj.companionObject
    val companion = kclassCObj.companionObjectInstance as Aircraft.Companion


    p(kclassCObj)
    p(companionKClass)
    p(companion)
    p(companion.boeing("747", 999).toString())

    val kclassPizza = PizzaOven::class
    val oven: PizzaOven = kclassPizza.objectInstance as PizzaOven

    val kclassRocket = Rocket::class

    p(kclassPizza)
    p(oven)

    val types = Sandwich::class.typeParameters

    types.forEach {
        println("Type ${it.name} has upper bound ${it.upperBounds}")
    }

    p(types)

    val superclasses = ManyParents::class.superclasses
    p(superclasses)

    val allSuperclasses = ManyParents::class.allSuperclasses
    p(allSuperclasses)

    fun <T : Any> printFunctions(kclass: KClass<T>) {
        kclass.functions.forEach {
            println(it.name)
        }
    }

    printFunctions(kclassRocket)

    fun <T : Any> printProperties(kClass: KClass<T>) {
        kclass.memberProperties.forEach {
            println(it.name)
        }
    }

    printProperties(kclassRocket)

    val function = kclassRocket.functions.find { it.name == "explode" }
    val rocket = Rocket()
    function?.call(rocket)

    @Foo
    class MyClass

    @Ispum("Lorem")
    class Zoo

    @Description("This class creates Executor instances")
    class Executors

    val desc = Executors::class.annotations.first() as Description
    val summary = desc.summary

    p(desc)
    p(summary)

    //Genéricos

    fun <T> random(one: T, two: T, three: T): T {
        return one
    }

    fun <T> randomList(vararg elements: T): List<T> {
        return elements.toList()
    }

    val randomGreeting: String = random(
        one = "hello",
        two = "willkommen",
        three = "bonjour"
    )

    randomGreeting.forEach {
        println(it)
    }

    val randomGreetingsList: List<String> = randomList(
        "hello",
        "willkommen",
        "bonjour"
    )

    for (greeting in randomGreetingsList) {
        println(greeting)
    }

    fun <K, V> put(key: K, value: V) {}

    val myMap = MyMap<String, String>()

    // Adicionando pares chave-valor ao mapa
    myMap.put("key1", "value1")
    myMap.put("key2", "value2")
    myMap.put("key3", "value3")

    // Obtendo valores do mapa
    val valueForKey2: String? = myMap.get("key2")
    println("Value for key2: $valueForKey2")

    val seq = Sequence<Boolean>()
    val dict = Dictionary<String, String>()

    p(seq)
    p(dict)

    //Limites superiores

    fun <T : Comparable<T>> min(first: T, second: T): T {
        val k = first.compareTo(second)
        return if (k <= 0) first else second
    }

    val a: Int = min(4, 5)
    val b: String = min("e", "c")

    p(a)
    p(b)

    //Vários limites

    fun <T> minSerializable(first: T, second: T): T
            where T : Comparable<T>, T : Serializable {
        val k = first.compareTo(second)
        return if (k <= 0) first else second
    }

    val yearSerializable = minSerializable(SerializableYear(1969), SerializableYear(2001))

    fun foo(crate: Crate<Fruit>) {
        crate.add(Apple())
    }

    val oranges = Crate(mutableListOf(Orange(), Orange()))
    // foo(oranges)

    val orange: Orange = oranges.last()

    p(orange)

    val oranges02 = Crate(mutableListOf(Orange(), Orange()))
    // isSafe(oranges02) Não compila

    val oranges03 = CovariantCrate(listOf(Orange(), Orange()))

    fun isSafe(crate: CovariantCrate<Orange>): Boolean = crate.elements.all {
        it.isSafeToEat()
    }

    fun isSafe02(crate: Crate<out Fruit>): Boolean = crate.elements.all {
        it.isSafeToEat()
    }
    isSafe02(oranges)
    isSafe(oranges03)

    val oranges04 = Crate(mutableListOf(Orange(), Orange()))
    isSafe02(oranges04)

    val farm: Farm = object : SheepFarm() {}
    val animal1 = farm.get()

    val sheepfarm: Farm = object : SheepFarm() {}
    val animal2 = sheepfarm.get()

    p(animal1)
    p(animal2)

    val stringListener = object : Listener<String> {
        override fun onNext(t: String) {
            println(t)
        }
    }

    val stringStream = EventStream<String>(stringListener)
    stringStream.start()

    val dateListener = object : Listener<Date> {
        override fun onNext(t: Date) {
            println(t)
        }
    }

    val dateStream = EventStream<Date>(dateListener)
    dateStream.start()

    val logginListener = object : Listener<Any> {
        override fun onNext(t: Any) = println(t)
    }

    EventStream<Double>(logginListener).start()
    EventStream<BigDecimal>(logginListener).start()

    //Tipo Nothing

    //Apagemento de tipo

    fun printInts(list: Set<Int>) {
        for (int in list) println(int)
    }

    fun printString(list: Set<String>) {
        for (string in list) println(string)
    }

    val intSet = setOf(1, 2, 3, 4, 5)
    val stringList = setOf("apple", "banana", "orange", "grape")

    printInts(intSet)
    println("-----")
    printString(stringList)

    fun <T : Comparable<T>> max(list: List<T>): T {
        var max = list.first()
        for (t in list) {
            if (t > max)
                max = t
        }
        return max
    }

    p(max(listOf(1, 2, 3)))

    val list = listOf("green", false, 100, "blue")
    val strings = list.collect<String>()

    p(strings)

    printT<Int>(123)

    val savings1 = SavingsAccount(BigDecimal(105), BigDecimal(0.04))
    val savings2 = SavingsAccount(BigDecimal(396), BigDecimal(0.05))

    savings1.compareTo(savings2)

    val trading1 = TradingAccount(BigDecimal(211), true)
    val trading2 = TradingAccount(BigDecimal(853), false)

    trading1.compareTo(trading2)

    val savings4 = SavingsAccount4(BigDecimal(105), BigDecimal(0.04))
    val savings5 = SavingsAccount4(BigDecimal(396), BigDecimal(0.05))

    savings4.compareTo(savings5)

    val trading4 = TradingAccount4(BigDecimal(211), true)
    val trading5 = TradingAccount4(BigDecimal(853), false)

    trading4.compareTo(trading5)

    val list02 = ListGenerics("this").append("is").append("my").append("list")
    val list02exemplo = listOf("this", "is", "my", "list")

    p(list02.size())
    p(list02.head())
    p(list02[1])
    p(list02exemplo.drop(2))

    val blogEntry = BlogEntry(
        title = "Data Classes are here",
        description = "Because Kotlin rulz!",
        publishTime = Date(0),
        approved = true,
        lastUpdated = Date(),
        url = URI("http://packt.com/blog/programming_kotlin/data_classes"),
        commentCount = 3,
        topTags = emptyList(),
        email = "email@email.com"
    )

    p(blogEntry)

    blogEntry.copy(
        title = "Properties in Kotlin",
        description = "Properties are awesome in Kotlin",
        approved = true,
        topTags = listOf("tag1")
    )

    val (
        title,
        description,
        publishTime,
        approved,
        lastUpdated,
        url,
        commentCount,
        topTags,
        email
    ) = blogEntry

    p("Here are the values for each field in the entry: title = $title description = $description")

    for ((x, y, z) in listOf(Vector3(0.2, 0.1, 0.5), Vector3(-12.0, 3.145, 5.100))) {
        println("Coordinates: X = $x, Y = $y, Z = $z")
    }

    val countriesAndCapital = listOf(
        Pair("UK", "London"),
        Pair("France", "Paris"),
        Pair("Australia", "Canberra")
    )

    val colours = listOf(
        Triple("#ff000", "rgb(255,0,0)", "hsl(0, 100%, 50%)"),
        Triple("#ff400", "rgb(255,64,0)", "hsl(15, 100%, 50%)")
    )

    for ((hex, rgb, hsl) in colours) {
        println("HEX = $hex; RGB = $rgb; HSL = $hsl")
    }

    fun <T> itWorks(list: List<T>): Unit {
        println("Java Class Type: ${list.javaClass.canonicalName}")
    }

    val jList = ArrayList<String>()
    jList.add("sample")
    itWorks(jList)
    itWorks(Collections.singletonList(1))

    p(jList)

    //ARRAYS

    val intArray = arrayOf(1, 2, 3, 4)
    println("Int array: ${intArray.joinToString(",")}")
    println("Element at index 1 is: ${intArray[1]}")

    val stringArray = kotlin.arrayOfNulls<String>(3)
    p(stringArray.joinToString(" , "))
    stringArray[0] = "a"
    stringArray[1] = "b"
    stringArray[2] = "c"
    //stringArray[3] = "d" fora dos limites do array

    p("String array: ${stringArray.joinToString(",")}")

    val studentArray = Array<Student2>(2) { index ->
        when (index) {
            0 -> Student2("Alexandra Brook", true, 1)
            1 -> Student2("James Smith", true, 2)
            else -> throw IllegalArgumentException("Too many")
        }

    }

    println("Student array: ${studentArray.joinToString(",")}")
    println("Student at index 0: ${studentArray[0]}")

    val longArray = emptyArray<Long>()
    println("Long array: ${longArray.joinToString(" , ")}")

    val countries = arrayOf("UK", "Germany", "Italy")

    for (country in countries) {
        print("$country;\n")
    }

    val numbers = intArrayOf(10, 20, 30)
    for (i in numbers.indices) {
        numbers[i] *= 10
        p(i)
    }

    val index = Random().nextInt(10)
    if (index in numbers.indices) {
        numbers[index] = index
        p(index)
    }

    println("First element in the IntArray: ${integers.first()}")
    println("Last element in the IntArray: ${integers.last()}")
    println("Take first 3 elements in of the IntArray: ${integers.take(3).joinToString(",")}")
    println("Take last 3 elements in of the IntArray: ${integers.takeLast(3).joinToString(",")}")
    println(
        "Take elements smaller than 5 of the IntArray: ${
            integers.takeWhile {
                it < 5
            }.joinToString(" , ")
        }"
    )
    println(
        "Take ever 3rd element in IntArray: ${
            integers.filterIndexed
            { index, element -> index % 3 == 0 }.joinToString(" , ")
        }"
    )

    val strings02 = integers.map { element -> "item$element" }
    println("Transform each element IntArray into a string: ${strings02.joinToString(" , ")}")

    val charArray = charArrayOf('a', 'b', 'c')
    val tripleCharArray = charArray.flatMap { x -> charArrayOf(x, x, x).asIterable() }
    println("Triple each element in the charArray: ${tripleCharArray.joinToString(" , ")}")

    val longs = longArrayOf(1, 2, 1, 2, 3, 4, 5)
    val hashSet: HashSet<Long> = longs.toHashSet()
    println("Java HashSet: ${hashSet.joinToString(" , ")}")

    val sortedSet: SortedSet<Long> = longs.toSortedSet()
    println("Sorted Set[${sortedSet.javaClass.canonicalName}]: ${sortedSet.joinToString(" , ")}")

    val set: Set<Long> = longs.toSet()
    println("Set[${set.javaClass.canonicalName}]: ${set.joinToString(" , ")}")

    val mutableSet = longs.toMutableSet()
    mutableSet.add(10)
    println("MutableSet[${mutableSet.javaClass.canonicalName}]: ${mutableSet.joinToString(" , ")}")

    val listArray: List<Long> = longs.toList()
    println("List [${listArray.javaClass.canonicalName}]: ${listArray.joinToString(" , ")}")

    val mutableList = longs.toMutableList()
    mutableSet.add(10)
    println("MutableList[${mutableList.javaClass.canonicalName}]: ${mutableList.joinToString(" , ")}")

    val hackedList = (listArray as ArrayList<Long>)
    hackedList.add(100)
    println("List[${listArray.javaClass.canonicalName}]: ${listArray.joinToString(" , ")}")

    val integers02 = arrayOf(1, 2, 3)
    val (i1, i2, i3, i4, i5) = integers
    println("i1: $i1, i2: $i2, i3: $i3, i4: $i4, i5: $i5")

    val intList: List<Int> = listOf()
    println("IntList[${intList.javaClass.canonicalName}]: ${intList.joinToString(" , ")}")

    val emptyList: List<String> = emptyList<String>()
    println("EmptyList[${emptyList.javaClass.canonicalName}]: ${emptyList.joinToString(" , ")}")

    val nonNulls: List<String> = listOfNotNull<String>(null, "a", "b", "c")
    println("Non-Null string lists[${nonNulls.javaClass.canonicalName}]: ${nonNulls.joinToString(" , ")}")

    val doubleList: ArrayList<Double> = arrayListOf(84.88, 100.25, 999.99)
    println("Double list[${doubleList.javaClass.canonicalName}]: ${doubleList.joinToString(" , ")}")

    val cartoonsList: MutableList<String> =
        mutableListOf("Tom & Jerry", "Dexter's Laboratory", "Jhonny Bravo", "Cow & Chicken")
    println("Cartoons list[${cartoonsList.javaClass.canonicalName}]: ${cartoonsList.joinToString(" , ")}")

    cartoonsList.addAll(arrayOf("Ed, Edd n Eddy", "Courage the Cowardly Dog"))
    println("Cartoons list[${cartoonsList.javaClass.canonicalName}]: ${cartoonsList.joinToString(" , ")}")

//    (intList as AbstractList<Int>).set(0,999999)
//    println("IntList[${intList.javaClass.canonicalName}]: ${intList.joinToString(" , ")}")

    (nonNulls as java.util.ArrayList).addAll(arrayOf("x", "y"))
    println("countries list[${nonNulls.javaClass.canonicalName}]: ${nonNulls.joinToString(" , ")}")

    val hacked: List<Int> = listOfNotNull(0, 1)
    CollectionsJ.dangerousCall(hacked)
    println("Hacked list[${hacked.javaClass.canonicalName}]: ${hacked.joinToString(" , ")}")

    val planets = listOf(
        PlanetCollections("Mercury", 57910000),
        PlanetCollections("Venus", 108200000),
        PlanetCollections("Earth", 149600000),
        PlanetCollections("Mars", 227940000),
        PlanetCollections("Jupiter", 778330000),
        PlanetCollections("Saturn", 1424600000),
        PlanetCollections("Uranus", 2873550000),
        PlanetCollections("Neptune", 4501000000),
        PlanetCollections("Pluto", 5945900000)
    )

    println(planets.last())
    println(planets.first())
    println(planets.get(4))
    println(planets.isEmpty())
    println(planets.isNotEmpty())
    println(planets.asReversed())
    println(planets.elementAtOrNull(10))

    planets.zip(
        arrayOf(
            4800, 12100, 12750, 6800, 142800, 120660, 51800, 49500, 3300
        )
    ).forEach {
        val (planet, diameter) = it
        println("${planet.name}'s diameter is $diameter km")
    }

    val reversePlanetName = planets.foldRight(StringBuilder()) { planet, builder ->
        builder.append(planet.name)
        builder.append(";")
    }

    println(reversePlanetName)

//    val amount = listOf(
//        ShoppingItem(
//            id = "1",
//            name = "Intel i7-950 Quad-Core Processsor",
//            price = BigDecimal("319.76"),
//            quantity = 1
//        ),
//        ShoppingItem(
//            id = "2",
//            name = "Samsung 750 EVO 250 GB 2.5 inch SSD",
//            price = BigDecimal("71.21"),
//            quantity = 1
//        )
//    ).foldRight(BigDecimal.ZERO) { item, amount ->
//        amount + BigDecimal(item.quantity) * item.price
//    }
//
//    println(amount)

    planets.map { it.distance }

    val listMap = listOf(
        listOf(10, 20),
        listOf(14, 18),
        emptyList()
    )

    val increment = { x: Int -> x + 1 }
    p(listMap.flatMap { it.map(increment) })

    val chars = listOf('a', 'd', 'c', 'd', 'a')
    val (c1, c2, c3, c4, c5) = chars
    println("($c1, $c2, $c3, $c4, $c5")

    val array: Array<Char> = chars.toTypedArray()
    val arrayBetter: CharArray = chars.toCharArray()
    val setList: Set<Char> = chars.toSet()
    val charsMutable: MutableList<Char> = chars.toMutableList()

    val carsMap: Map<String, String> = mapOf(
        "a" to "Aston Martin",
        "b" to "BMW",
        "m" to "Mercedes",
        "f" to "Ferrari"
    )

    p("cars [${carsMap.javaClass.canonicalName}: $carsMap")
    p("car maker starting with 'F': ${carsMap["f"]}")
    p("car maker starting with 'X': ${carsMap["X"]}")

    val states: MutableMap<String, String> = mutableMapOf(
        "AL" to "Alabama",
        "AK" to "Alaska",
        "AZ" to "Arizona"
    )

    states += ("CA" to "California")

    p("States [${states.javaClass.canonicalName}: $states")
    p("States keys: ${states.keys}")
    p("States values: ${states.values}")

    val customers: java.util.HashMap<Int, CustomerMap> = hashMapOf(
        1 to CustomerMap(
            name = "Dina",
            lastName = "Kreps",
            id = 1
        ), 2 to CustomerMap(
            name = "Andy",
            lastName = "Smith",
            id = 2
        )
    )

    val linkedHashMap: java.util.LinkedHashMap<String, String> = linkedMapOf(
        "red" to "#FF0000",
        "azure" to "#F0FFFF",
        "white" to "#FFFFFF"
    )

    val sortedMap: java.util.SortedMap<Int, String> = sortedMapOf(
        4 to "d",
        1 to "a",
        3 to "c",
        2 to "b",
    )

    println("Sorted map[${sortedMap.javaClass.canonicalName}]: $sortedMap")

    customers.mapKeys { it.toString() }
    customers.map { it.key * 10 to it.value.id }
    customers.mapValues { it.value.lastName }
    customers.flatMap { (it.value.name + it.value.lastName).toSet() }.toSet()
    linkedHashMap.filterKeys { it.contains("r") }
    states.filterNot { it.value.startsWith("C") }

    val intSets: Set<Int> = setOf(1, 21, 21, 2, 6, 3, 2)

    println("Set of integers[${intSets.javaClass.canonicalName}]: $intSets")

    val hashSets: java.util.HashSet<Book> = hashSetOf(
        Book(
            author = "Jules Verne",
            title = "Arround the World in 80 Days Paperback",
            year = 2014,
            isbn = "978-1503215153"
        ),
        Book(
            author = "George R.R Martin",
            title = "Series: Game of Thrones: The Graphic Novel (Book 1)",
            year = 2012,
            isbn = "978-0440423218"
        ),
        Book(
            author = "J.K Rowling",
            title = "Harry Potter And The Globet Of Fire (Book 4) Hardcover",
            year = 2000,
            isbn = "978-0439139595"
        ),
        Book(
            author = "Jules Verne",
            title = "Arround the World in 80 Days Paperback",
            year = 2014,
            isbn = "978-1503215153"
        ),
    )

    println("Set of books: $hashSets")

    val sortedIntegers: java.util.TreeSet<Int> = sortedSetOf(11, 0, 9, 11, 9, 8)
    println("Sorted set of integer: $sortedIntegers")

    val charSet: java.util.LinkedHashSet<Char> = linkedSetOf('a', 'x', 'a', 'z', 'a')
    println("Set of characters: $charSet")

    val longSet: MutableSet<Long> = mutableSetOf(
        20161028141216,
        20161029121211,
        20161029121211
    )

    println("Set of longs[${longSet.javaClass.canonicalName}]: $longSet")

    println(intSets.contains(9999))
    println(intSets.contains(1))
    println(
        hashSets.contains(
            Book(
                author = "Jules Verne",
                title = "Arround the World in 80 Days Paperback",
                year = 2014,
                isbn = "978-1503215153"
            )
        )
    )

    println(intSets.first())
    println(sortedIntegers.last())
    println(charSet.drop(2))
    println(intSets.plus(10))
    println(intSets.minus(21))
    println(intSets.minus(-1))
    println(intSets.average())
    println(longSet.plus(11))
    println(longSet)

    println(hashSets.map { Pair(it.author, it.title) })
    println(
        hashSets.flatMap {
            it.title.asIterable()
        }.toSortedSet()
    )

    val longList: List<Long> = longSet.toList()
    val longsMutableList = longSet.toMutableList()
    val doNot = longSet.toLongArray()
    val rightArray = longSet.toTypedArray()

    p("longList = $longList")
    p("longsMutableList = $longsMutableList")
    p("doNot = $doNot")
    p("rightArray = $rightArray")

    val carManuFactures: MutableList<String> = mutableListOf(
        "Masserati",
        "Aston Martin",
        "McLaren",
        "Ferrari",
        "Koenigsegg"
    )

    val carsView: List<String> = carManuFactures

    println("Cars view: $carsView")

    val capitals = listOf(
        "London",
        "Tokyo",
        "Instambul",
        "Bucharest"
    )
    p(capitals[2])

    val countriesIndex = mapOf(
        "BRA" to "Brazil",
        "ARG" to "Argentina",
        "ITA" to "Italy"
    )

    p(countriesIndex["BRA"])
    p(countriesIndex["UK"])

    val charSequence: Sequence<Char> = charArrayOf('a', 'b', 'c').asSequence()
    println("Char sequence: [${charSequence.javaClass.canonicalName}]: ${charSequence.joinToString { " , " }}")
    println("Char sequence: [${charSequence.javaClass.name}]: ${charSequence.joinToString { " , " }}")

    val longSequence: Sequence<Long> = listOf(12000L, 11L, -1999L).asSequence()

    println("Long Sequence: [${longSequence.javaClass.canonicalName}]: ${longSequence.joinToString { " , " }}")
    println("Long Sequence: [${longSequence.javaClass.name}]: ${longSequence.joinToString { " , " }}")

    val mapSequence: Sequence<Map.Entry<Int, String>> = mapOf(
        1 to "A",
        2 to "B",
        3 to "C"
    ).asSequence()

    println("Map Sequence: [${mapSequence.javaClass.canonicalName}]: ${mapSequence.joinToString { " , " }}")
    println("Map Sequence: [${mapSequence.javaClass.name}]: ${mapSequence.joinToString { " , " }}")

    val setSequence: Sequence<String> = setOf("Anna", "Andrew", "Jack", "Laura", "Anna").asSequence()

    println("Set Sequence: [${setSequence.javaClass.canonicalName}]: ${setSequence.joinToString { " , " }}")
    println("Set Sequence: [${setSequence.javaClass.name}]: ${setSequence.joinToString { " , " }}")

    val intSequence = sequenceOf(1, 2, 3, 4, 5)
    println("Sequence of integers: [${intSequence.javaClass.canonicalName}]: $intSequence")

    val emptySequence: Sequence<Int> = emptySequence<Int>()
    println("Empty Sequence: [${emptySequence.javaClass.canonicalName}]: $emptySequence")

    var nextItem = 0

    val sequence = generateSequence {
        nextItem += 1
        nextItem
    }

    println(
        "Unbound int sequence[${sequence.javaClass.canonicalName}]: ${
            sequence.takeWhile {
                it < 100
            }.joinToString(" , ")
        }"
    )

//    println("Unbound int sequence[${sequence.javaClass.canonicalName}]: ${sequence.takeWhile {
//        it < 100
//    }.joinToString(" , ")}") --> IllegalStateException: This sequence can be consumed only once.

    val secondSequence = generateSequence(100) { if ((it + 1) % 2 == 0) it + 1 else it + 2 }

    println(
        "Unbound int sequence[${secondSequence.javaClass.canonicalName}]: ${
            secondSequence.takeWhile {
                it < 110
            }.toList()
        }"
    )

    val stream = Thread.currentThread().javaClass.getResourceAsStream("/afile.txt")

    val br = stream?.let { InputStreamReader(it) }?.let { BufferedReader(it) }
    val fileContent = generateSequence { br?.readLine() }.takeWhile { true }

    println("File content: ${fileContent.joinToString(" ")}")

    var prevNumber: Int = 0
    val fibonacci1 = generateSequence(1) {
        val tmp = prevNumber
        prevNumber = it
        it + tmp
    }

    val fibonacci2 = generateSequence(1 to 1) {
        it.second to it.first + it.second
    }.map { it.first }

    println("Fibonacci sequence: ${fibonacci1.take(12).joinToString(" , ")}")
    println("Fibonacci sequence: ${fibonacci2.take(12).joinToString(" , ")}")

    // Chapter 11 - Tests in Kotlin

    fun squareRoot(k: Int): Int {
        require(k >= 0)
        return sqrt(k.toDouble()).toInt()
    }

    shouldThrow<IllegalArgumentException> {
        squareRoot(-1)
    }

    val aImageFile = object : Matcher<File> {
        private val suffixes = setOf("jpeg", "jpg", "gif")

        override fun test(value: File): Result {
            val fileExists = value.exists()
            val hasImageSuffix = suffixes.any {
                value.path.toLowerCase().endsWith(it)
            }

            if (fileExists.not()) {
                return Result(false, "File $value should exist")
            }

            if (hasImageSuffix.not()) {
                return Result(false, "File $value should have a well known image suffix")
            }

            return Result(true, "Test passed")
        }
    }

    fun anImageFile() = object : Matcher<File> {
        override fun test(value: File): Result {
            TODO("Not yet implemented")
        }

    }

//    thread(start = true, name = "mythread") {
//        while (true) {
//            println("Hello, I am running on a thread")
//        }
//    }

//    val t = thread(start = false, name = "mythread") {
//        while (true) {
//            println("Hello, I am running on a thread sometime later")
//        }
//    }
//
//    t.start()
//
//    val queue = LinkedBlockingQueue<Int>()
//
//    val consumerTasks = (1..6).map { ConsumerTask(queue) }
//    val producerTask = ProducerTask(queue)
//
//    val consumerThreads = consumerTasks.map { thread { it.run() } }
//    val producerThread = thread { producerTask.run() }
//
//    consumerTasks.forEach { it.running = false }
//    producerTask.running = false
//
//    val consumerTasksInterruptable = (1..6).map {
//        InterruptableConsumerTask(queue)
//    }
//
//    val producerTasksInterruptable = ProducerTask(queue)
//
//    val consumerThreadsInterruptable = consumerTasks.map {
//        thread { it.run() }
//    }
//
//    consumerThreadsInterruptable.forEach { it.interrupt() }
//    producerTasksInterruptable.running = false
//
//    val executor = java.util.concurrent.Executors.newFixedThreadPool(4)
//    for (k in 1..10) {
//        executor.submit {
//            println("Processing element $k on thread ${Thread.currentThread()}")
//        }
//    }
//
//    executor.shutdown()
//    executor.awaitTermination(1, TimeUnit.MINUTES)
//
//    val obj = Any()
//
//    synchronized(obj) {
//        println("I hold the monitor for $obj")
//    }
//
//    val lock = ReentrantLock()
//    if (lock.tryLock()) {
//        println("I have the lock")
//        lock.unlock()
//    } else {
//        println("I do not have the lock")
//    }
//
//    val lock2 = ReentrantLock()
//    try {
//        lock2.lockInterruptibly()
//        println("I have the lock")
//        lock2.unlock()
//    } catch (e: InterruptedException) {
//        println("I was interrupted")
//    }
//
//    val lock3 = ReentrantLock()
//    lock3.withLock {
//        println("I have the lock")
//    }
//
//    val buffer = mutableListOf<Int>()
//    val maxSize = 8
//
//    (1..2).forEach {
//        thread {
//            val random = Random()
//            while (true) {
//                if (buffer.size < maxSize)
//                    buffer.plus(random.nextInt())
//            }
//        }
//    }
//
//    (1..2).forEach {
//        thread {
//            while (true) {
//                if (buffer.size > 0) {
//                    val item = buffer.remove(0)
//                    println("Consumed item $item")
//                }
//            }
//        }
//    }
//
//    val emptyCount = Semaphore(8)
//    val fillCount = Semaphore(0)
//    val bufferSemaphore = mutableSetOf<Int>()
//
//    thread {
//        val random = Random()
//        while (true) {
//            emptyCount.acquire()
//            bufferSemaphore.plus(random.nextInt())
//            fillCount.release()
//        }
//    }
//
//    thread {
//        while (true) {
//            fillCount.acquire()
//            val item = bufferSemaphore.remove(0)
//            println("Consumed item $item")
//            emptyCount.release()
//        }
//    }
//
//    val emptyCount02 = Semaphore(8)
//    val fillCount02 = Semaphore(0)
//    val mutex = Semaphore(1)
//    val bufferSemaphore02 = mutableSetOf<Int>()
//
//    thread {
//        val random = Random()
//        while (true) {
//            emptyCount02.acquire()
//            mutex.acquire()
//            bufferSemaphore02.plus(random.nextInt())
//            mutex.release()
//            fillCount02.release()
//        }
//    }
//
//    thread {
//        while (true) {
//            fillCount02.acquire()
//            mutex.acquire()
//            val item = bufferSemaphore02.remove(0)
//            mutex.release()
//            println("Consumed item $item")
//            emptyCount.release()
//        }
//    }
//
//    val bufferLinkedBlockingQueue = LinkedBlockingQueue<Int>()
//
//    thread {
//        val random = Random()
//        while (true) {
//            bufferLinkedBlockingQueue.put(random.nextInt())
//        }
//    }
//
//    thread {
//        while (true) {
//            val item = bufferLinkedBlockingQueue.take(0)
//            println("Consumed item $item")
//        }
//    }
//
//    val counterThreads = AtomicLong(0)
//    (1..8).forEach {
//        thread {
//            while (true) {
//                val id = counterThreads.incrementAndGet()
//                println("Creating item with id $id")
//            }
//        }
//    }
//
//    val ref = AtomicReference<Connection>()
//    (1..8).forEach {
//        thread {
//            while (true) {
//                ref.compareAndSet(null, null /*openConnection()*/)
//            }
//        }
//    }

    fun processFeed(feed: Feed) {
        println("Processing feed ${feed.name}")
    }

    val executorFeed = java.util.concurrent.Executors.newCachedThreadPool()

    fun sendNotification() {
        println("Sending Notification")
    }

    val feeds = listOf(
        Feed(name = "Great Vegetable Strore", url = "http://www.greatvegstore.co.uk/items.xml"),
        Feed(name = "Super Food Shop", url = "http://www.superfoodshop.com/products.csv")
    )

    val latch = CountDownLatch(feeds.size)

    for (feed in feeds) {
        executorFeed.submit {
            processFeed(feed)
            latch.countDown()
        }
    }
    latch.await()
    println("All feeds completed")
    sendNotification()

    fun copyUsingBarrier(inputFiles: List<Path>, outputDirectories: List<Path>) {
        val executor = java.util.concurrent.Executors.newFixedThreadPool(outputDirectories.size)
        val barrier = CyclicBarrier(outputDirectories.size)

        for (dir in outputDirectories) {
            executor.submit {
                CopyTask(dir, inputFiles, barrier)
            }
        }
    }

    val inputFiles = listOf<Path>(
        Paths.get("/Users/celso.fariasuseblu.com.br/Documents/test/exemplo1.txt"),
        Paths.get("/Users/celso.fariasuseblu.com.br/Documents/test/exemplo2.txt"),
        Paths.get("/Users/celso.fariasuseblu.com.br/Documents/test/exemplo3.txt")
    )

    val outputDirectories = listOf<Path>(
        Paths.get("/Users/celso.fariasuseblu.com.br/Downloads"),
        Paths.get("/Users/celso.fariasuseblu.com.br/Downloads"),
        Paths.get("/Users/celso.fariasuseblu.com.br/Downloads")
    )

    copyUsingBarrier(inputFiles, outputDirectories)

    println("Todas as cópias foram iniciadas.")

    val executorFuture = java.util.concurrent.Executors.newFixedThreadPool(4)

    val future: Future<Double> = executorFuture.submit(Callable<Double> {
        sqrt(15.64)
    })

    val future02 = CompletableFuture.supplyAsync(Supplier { sqrt(15.64) }, executorFuture)

    future.apply {
        println("The square root has been calculated")
    }

    future02.thenApply {
        println("The square root has been calculated")
    }
    class Card
    class Order {
        var card: Card = Card()
    }


    val order = Order()
    val card = Card()

    fun persistOrder(order: Order): CompletableFuture<String> = TODO()
    fun chargeCard(card: Card): CompletableFuture<Boolean> = TODO()
    fun printInvoice(order: Order): CompletableFuture<Unit> = TODO()

    CompletableFuture.allOf(
        persistOrder(order),
        chargeCard(card),
        printInvoice(order)
    ).thenApply {
        println("Order is saved, charged and printed")
    }
}