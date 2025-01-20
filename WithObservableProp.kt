import kotlin.properties.Delegates

class WithObservableProp {
    var value: Int by Delegates.observable(0) {
        p, oldNew, newVal -> onValueChanged()
    }

    private fun onValueChanged() {
        println("value has changed: $value")
    }
}