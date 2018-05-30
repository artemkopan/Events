package com.artemkopan.data.network

import com.google.android.gms.tasks.Task
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.schedulers.Schedulers


fun <T> fromTask(task: Task<T>, scheduler: Scheduler = Schedulers.io()) =
        Single.create(SingleTask(task))
                .subscribeOn(scheduler)
                .observeOn(scheduler)!!


private class SingleTask<T>(private val task: Task<T>) : SingleOnSubscribe<T> {

    override fun subscribe(emitter: SingleEmitter<T>) {
        task.addOnSuccessListener {
            if (!emitter.isDisposed) {
                emitter.onSuccess(it)
            }
        }.addOnFailureListener {
            if (!emitter.isDisposed) {
                emitter.onError(it)
            }
        }

    }

}
