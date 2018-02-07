package com.rxfuel.rxfuelsample.ui.main

import com.rxfuel.rxfuel.RxFuelProcessor
import io.reactivex.ObservableTransformer

/**
 * Created by salah on 2/2/18.
 */
class MainProcessor : RxFuelProcessor<MainAction, MainResult>() {

    override val processors: HashMap<Class<out MainAction>, ObservableTransformer<out MainAction, out MainResult>>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}