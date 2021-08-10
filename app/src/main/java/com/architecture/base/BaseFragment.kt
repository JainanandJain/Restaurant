package com.architecture.base

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.architecture.util.Util
import com.phelat.navigationresult.BundleFragment
import com.thekhaeng.pushdownanim.PushDownAnim
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class BaseFragment : BundleFragment() {

    internal var duration = 2000
    var commonCallbacks: CommonCallbacks? = null
    fun getViewModelProvider(): MyViewModelProvider = MyViewModelProvider(commonCallbacks as AsyncViewController)

    fun toast(str: String) {
        str.let {
            Toast.makeText(context, str, Toast.LENGTH_LONG).show()
        }
    }

    fun push(view: View, clickListener: View.OnClickListener) {
        PushDownAnim.setPushDownAnimTo(view).setOnClickListener { clickListener }
    }

    override fun onAttach(context: Context) {
        commonCallbacks = context as CommonCallbacks
        super.onAttach(context)
    }

    override fun onDetach() {
        commonCallbacks = null
        super.onDetach()
    }

    fun showProgress() {
        commonCallbacks?.showProgressDialog()
    }

    fun hideProgress() {
        commonCallbacks?.hideProgressDialog()
    }


    fun hideAlertDialog() {
        commonCallbacks?.hideAlertDialog()
    }

    open fun handlingBackPress(): Boolean {
        return findNavController().navigateUp()
    }

    open fun onFilePicked(pickedFileUri: String) {}

    open fun onApiRequestFailed(apiUrl: String, errCode: Int, errorMessage: String): Boolean {
        return false
    }

    open fun onSubscriptionExpired() {

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun navigate(fragmentId: Int, vararg data: Pair<String, Any?>) {
        val b = bundleOf(*data)
        findNavController().navigate(fragmentId, b)
    }

    fun navigate(fragmentId: Int) {
        findNavController().navigate(fragmentId)
    }

    fun getParcelOrNull(fragmentId: Int): Bundle? {
        return commonCallbacks!!.getSharedModel().parcels[fragmentId]
    }

    fun getParcelAndConsume(fragmentId: Int, consumer: (b: Bundle) -> Unit) {
        val parcelMap = commonCallbacks!!.getSharedModel().parcels
        parcelMap[fragmentId]?.apply {
            consumer(this)
            parcelMap.remove(fragmentId)
        }
    }

    open fun putParcel(parcel: Bundle, fragmentId: Int) {
        commonCallbacks!!.getSharedModel().parcels[fragmentId] = parcel
    }

    open fun onReceiveLocation(newLocation: Location?): Boolean {
        return false
    }


    fun showSuccessBar(msg: String) {
        context.let {
            Util.showSuccessSneaker(requireContext(), msg)
        }
    }

    fun showErrorBar(msg: String) {
        context.let {
            Util.showErrorSneaker(requireContext(), msg)
        }
    }

    fun showErrorBar(msg: String, view: View) {
        context.let {
            Util.showErrorSneaker(requireContext(), msg, view)
        }
    }

    fun showWarning(msg: String) {
        context.let {
            Util.showWarningSneaker(requireContext(), msg)
        }
    }

    fun showWarning(msg: String, view: View) {
        context.let {
            Util.showWarningSneaker(requireContext(), msg, view)
        }
    }

    fun goBack(message: String?) {
        try {
            message?.let {
                activity.let {
                    showSuccessBar(message)
                    if(activity!=null) {
                        if ((activity as? BaseActivity) != null) {
                            Handler().postDelayed(
                                { (activity as? BaseActivity)?.forceBack() },
                                duration.toLong()
                            )
                        }
                    }

                }

            }
        } catch (e: Exception) {
            Log.e("Error", e.message + "")
        }

    }

    fun goBack() {
        try {
            if(activity!=null) {
                if ((activity as? BaseActivity) != null) {
                    Handler().postDelayed(
                        { (activity as? BaseActivity)?.forceBack() },
                        duration.toLong()
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.message + "")
        }

    }

    fun goBack(duration: Long) {
        try {
            if(activity!=null) {
                if ((activity as? BaseActivity) != null) {
                    Handler().postDelayed(
                        { (activity as? BaseActivity)?.forceBack() },
                        duration
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.message + "")
        }

    }

    fun <T> nullCheck(str: T?): T? {
        return str ?: "" as T
    }


    fun setClickable(
        textView: TextView,
        subString: String,
        handler: () -> Unit,
        drawUnderline: Boolean = false
    ) {
        val text = textView.text
        val start = text.indexOf(subString)
        val end = start + subString.length

        val span = SpannableString(text)
        span.setSpan(
            ClickHandler(handler, drawUnderline),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.linksClickable = true
        textView.isClickable = true
        textView.movementMethod = LinkMovementMethod.getInstance()

        textView.text = span
    }

    class ClickHandler(
        private val handler: () -> Unit,
        private val drawUnderline: Boolean
    ) : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            if (drawUnderline) {
                super.updateDrawState(ds)
            } else {
                ds.isUnderlineText = false
            }
        }

        override fun onClick(p0: View) {
            handler()
        }

    }

}