package com.rxfuel.rxfuelsample.util

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.jakewharton.rxbinding2.view.RxView

import java.util.Collections

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject


/**
 * Created by salah on 7/2/18.
 */

class ReactiveRecylerAdapter<T>(private val observable: Observable<List<T>>, private val viewHolderFactory: ReactiveViewHolderFactory<T>) : RecyclerView.Adapter<ReactiveRecylerAdapter.ReactiveViewHolder<T>>() {
    private var currentList: List<T>? = null
    private val mViewClickSubject = PublishSubject.create<T>()

    val viewClickedObservable: Observable<T>
        get() = mViewClickSubject

    init {
        this.currentList = emptyList()
        this.observable.observeOn(AndroidSchedulers.mainThread()).subscribe { items ->
            this.currentList = items
            this.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, pViewType: Int): ReactiveViewHolder<T> {
        val viewAndHolder = viewHolderFactory.createViewAndHolder(parent, pViewType)
        val viewHolder = viewAndHolder.viewHolder

        RxView.clicks(viewAndHolder.view)
                .takeUntil(RxView.detaches(parent))
                .map { viewHolder.currentItem }
                .subscribe(mViewClickSubject)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ReactiveViewHolder<T>, position: Int) {
        val item = currentList!![position]
        holder.currentItem = item
    }

    override fun getItemCount(): Int {
        return currentList!!.size
    }

    abstract class ReactiveViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract var currentItem: T?
    }

    interface ReactiveViewHolderFactory<T> {
        class ViewAndHolder<T>(val view: View, val viewHolder: ReactiveViewHolder<T>)

        fun createViewAndHolder(parent: ViewGroup, pViewType: Int): ViewAndHolder<T>
    }
}