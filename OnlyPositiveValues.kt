import kotlin.properties.Delegates

class OnlyPositiveValues {
    var value: Int by Delegates.vetoable(0) { p, oldNew, newVal -> newVal >=0 }
}