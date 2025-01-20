import kotlin.properties.Delegates

class NonNullProp {
    var value: String by Delegates.notNull<String>()
}