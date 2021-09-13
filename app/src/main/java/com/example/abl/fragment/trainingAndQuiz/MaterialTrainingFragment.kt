package com.example.abl.fragment.trainingAndQuiz

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.abl.R
import com.example.abl.adapter.MaterialAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.ComprehensiveTrainingFragmentBinding
import com.example.abl.model.trainingAndQuiz.Material
import com.example.abl.model.trainingAndQuiz.Training
import kotlinx.android.synthetic.main.marketing_collateral_item_fragment.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MaterialTrainingFragment : BaseDockFragment(), ClickListner {

    lateinit var binding: ComprehensiveTrainingFragmentBinding
    var materialList: ArrayList<Material>? = null
    lateinit var adapter: MaterialAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        arguments?.getParcelable<Training>(Constants.TRAINING_DETAILS).let {
           materialList = it?.materials as ArrayList<Material>?
           initRecyclerView(materialList)
       }

        adapter = MaterialAdapter(requireContext(), this)

        binding.attemptQuiz.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList(Constants.MATERIAL_LIST, materialList)
            navigateToFragment(R.id.action_nav_comprehensive_to_nav_training_quiz, bundle)
        }

        return binding.root
    }

    private fun initView() {
        binding = ComprehensiveTrainingFragmentBinding.inflate(layoutInflater)
    }

    private fun initRecyclerView(materialList: List<Material>?){
        adapter = MaterialAdapter(requireContext(), this)
        adapter.setList(materialList!!)
        binding.materialRv.adapter = adapter
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val item = data as Material
        when {
            else -> {
                showBanner("Please wait", Constants.SUCCESS)
         //       if (item.title == "document"){
                    item.material_url.let { openWebBrowser(it) }
//                }else {
//                    DownloadFiles(requireContext(), webView, item.material_url)
//                }
            }
        }
    }

    fun openWebBrowser(url : String) {

        var webpage = Uri.parse(url)
        if (!url.startsWith("http://") && !url!!.startsWith("https://")) {
            var a = "http://$url"
            webpage = Uri.parse(a)
        }else{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (context?.let { intent.resolveActivity(it.packageManager) } != null) {
            context?.startActivity(intent)
        }
    }

    fun DownloadFiles(context: Context, mWebView: WebView, link: String?) {
        if (link != null) {
            mWebView.webViewClient = WebViewClient()
            mWebView.settings.loadsImagesAutomatically = true
            mWebView.settings.javaScriptEnabled = true
            mWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            mWebView.loadUrl(
                link
            )
            mWebView.setDownloadListener {   url: String?, userAgent: String?, contentDisposition: String?, mimeType: String?, contentLength: Long ->
                try {
                    val file = File(
                        Environment.DIRECTORY_DOWNLOADS,
                        URLUtil.guessFileName(url, contentDisposition, mimeType)
                    )
                    val file1 = File(
                        Environment.getExternalStorageDirectory()
                            .toString() + "/" + file.absolutePath
                    )
                    if (file1.exists()) {
                        val uri = FileProvider.getUriForFile(
                            Objects.requireNonNull(requireContext()),
                            Objects.requireNonNull(requireContext())
                                .applicationContext.packageName + ".fileprovider",
                            file1
                        )
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                        return@setDownloadListener
                    } else {
                        val request =
                            DownloadManager.Request(Uri.parse(url))
                        request.setMimeType(mimeType)
                        val cookies =
                            CookieManager.getInstance().getCookie(url)
                        request.addRequestHeader("cookie", cookies)
                        request.addRequestHeader("User-Agent", userAgent)
                        request.setDescription("Downloading File...")
                        request.setTitle(
                            URLUtil.guessFileName(
                                url,
                                contentDisposition,
                                mimeType
                            )
                        ) // file name
                        request.allowScanningByMediaScanner()
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        request.setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            URLUtil.guessFileName(url, contentDisposition, mimeType)
                        )
                        val dm =
                            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        dm.enqueue(request)
                        Toast.makeText(context, "Downloading File", Toast.LENGTH_LONG).show()
                    }
                } catch (e: java.lang.Exception) {
                    Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}