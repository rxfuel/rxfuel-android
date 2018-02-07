package com.rxfuel.rxfuelsample.ui.repoList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.detaches
import com.rxfuel.rxfuelsample.R
import com.rxfuel.rxfuelsample.model.Repo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by salah on 3/2/18.
 */

class RepoListAdapter(reposObservable: Observable<List<Repo>>) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    private val mViewClickSubject = PublishSubject.create<Repo>()

    fun itemClicks() : Observable<Repo> = mViewClickSubject

    private val repos : MutableList<Repo> = mutableListOf()

    init {
        reposObservable
                .subscribe {
                    repos.clear()
                    repos.addAll(it)
                    notifyDataSetChanged()
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        val viewHolder = RepoViewHolder(view)

        view.clicks()
                .map { viewHolder.currentItem }
                .subscribe(mViewClickSubject)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) = holder.bind(repos[position])

    override fun getItemCount() = repos.size

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var currentItem : Repo

        fun bind(item: Repo) = with(itemView) {
            currentItem = item
            itemView.findViewById<TextView>(R.id.tv_repo_name).text = item.full_name
        }

    }
}