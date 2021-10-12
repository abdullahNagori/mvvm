package com.uhfsolutions.abl.fragment.marketingCollateral

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.FileProvider
import com.uhfsolutions.abl.adapter.MarketingCollateralItemAdapter
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.MarketingCollateralItemFragmentBinding
import com.uhfsolutions.abl.model.marketingCollateral.MarketingCollateralItem
import com.uhfsolutions.abl.model.marketingCollateral.MarketingCollateralResponse
import com.uhfsolutions.abl.utils.GsonFactory
import kotlinx.android.synthetic.main.marketing_collateral_item_fragment.*
import java.io.File
import java.util.*

class MarketingCollateralItemFragment : BaseDockFragment(), ClickListner {

    lateinit var binding: MarketingCollateralItemFragmentBinding
    private var section: MarketingCollateralResponse? = null
    private lateinit var adapter: MarketingCollateralItemAdapter

    companion object {

        const val ARG_NAME = "marketing_collateral"
        fun newInstance(content: String): MarketingCollateralItemFragment {
            val fragment = MarketingCollateralItemFragment()
            val args = Bundle()
            args.putString(ARG_NAME, content)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        section = GsonFactory.getConfiguredGson()?.fromJson(arguments?.getString(ARG_NAME), MarketingCollateralResponse::class.java)

        initRecyclerView()

        return binding.root
    }

    private fun initView() {
        binding = MarketingCollateralItemFragmentBinding.inflate(layoutInflater)
    }






    private fun initRecyclerView(){
        adapter = MarketingCollateralItemAdapter(requireContext(), this)
        //  Log.i("xxCheck1", "null ${section?.data?.size}")
        section?.let {
            adapter.setList(it.data)
            adapter.notifyDataSetChanged()
            binding.categoryItems.adapter = adapter

            for(i in it.data)
            {
                Log.i("xxCheck2", "${i}")
            }
        }
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val item = data as MarketingCollateralItem

        when {
//            item.file_type.toString().equals("video", true) -> {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://"+item.link)));
//            }
//            item.file_type.toString().equals("image", true) -> {
//                val imageViewDialogFragment = ImageViewDialogFragment()
//                imageViewDialogFragment.link = item.link
//                imageViewDialogFragment.show(childFragmentManager,"")
//            }
            else -> {
                showBanner("Please wait", Constants.SUCCESS)
                // DownloadFiles(requireContext(), webView, item.link)

             //   if (item.file_type.toString() == "document"){
                    item.file_path?.let {
                        openWebBrowser(it)
                   }
//                }else {
//                    DownloadFiles(requireContext(), webView, item.file_path)
//                }
                //downloadFIle(item.link)
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