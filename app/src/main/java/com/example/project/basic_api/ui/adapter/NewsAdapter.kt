package com.example.project.basic_api.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project.R
import com.example.project.basic_api.data.model.Article
import com.example.project.databinding.ActivityWebView2Binding
import com.example.project.databinding.HomeNewsHorizontalItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val articles = mutableListOf<Article>()

    fun setArticles(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeNewsHorizontalItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    class NewsViewHolder(private val binding: HomeNewsHorizontalItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.newsHoriTitle.text = article.title

            // Handle card click to show WebView in dialog
            binding.card.setOnClickListener {
                showWebViewDialog(binding.root.context, article.url)
            }

            Glide.with(binding.newsHoriImage.context)
                .load(article.urlToImage)
                .into(binding.newsHoriImage)
        }

        private fun showWebViewDialog(context: Context, url: String?) {
            if (url.isNullOrEmpty()) return

            // Inflate dialog layout with WebView
            val dialogBinding = ActivityWebView2Binding.inflate(LayoutInflater.from(context))
            val webView = dialogBinding.webView
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(url)

            // Create and show dialog
            AlertDialog.Builder(context)
                .setView(dialogBinding.root)
                .setPositiveButton("Tutup") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

}