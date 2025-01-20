import java.util.concurrent.Delayed

class Container02 {
    lateinit var delayedInitProperty: DelayedInstance

    fun initProperty(instance: DelayedInstance) {
        this.delayedInitProperty = instance
    }
}

class DelayedInstance (val number: Int)