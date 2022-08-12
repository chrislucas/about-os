package sample.ktutorial.docs.flow.callbackflow


interface CallbackOperation<T> {
    fun onSucess(data: Result<T>)
    fun onError(error: Throwable?)
}


// https://gist.github.com/kwmt/fdc27a0fb44278b1597a13331693b392
class Operation<T> {

    var callback: CallbackOperation<T>? = null

    suspend fun <T> run(operation: suspend () -> Result<T>) {
        with(operation()) {
            if (this.isSuccess) {
                val s = this.getOrNull()
                //callback?.onSucess(result)
            } else {
                callback?.onError(this.exceptionOrNull())
            }
        }
    }
}

fun main() {

}