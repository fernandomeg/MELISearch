package com.gallardf.melisearch.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gallardf.melisearch.R
import com.gallardf.melisearch.databinding.ViewAlertToastBinding

fun Fragment.instantiateDataProgressBar(subtitle: String): AlertDialog {
    val alert = AlertDialog.Builder(this.requireActivity())
    alert.setCancelable(false)
    val alertView = layoutInflater.inflate(R.layout.view_progress_dialog, null, false)
    with(alertView) {
        val tvSubtitle = this.findViewById<TextView>(R.id.data_progress_subtitle)
        tvSubtitle.text = subtitle
    }
    alert.setView(alertView)
    val dialog = alert.create()
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}

fun Fragment.showErrorToast(message: String? = null) {
    val toast = Toast(requireContext())
    val toastView = ViewAlertToastBinding.inflate(layoutInflater, null, false)
    toastView.notificationToastIcon.setImageResource(R.drawable.ic_cross_filled)
    toastView.notificationToastMessage.setText(R.string.error_unknown_message)
    message?.let { _message ->
        toastView.notificationToastMessage.text = _message
    }
    toastView.notificationToastStrongColor.setBackgroundColor(
        ContextCompat.getColor(requireContext(), R.color.error_toast_strong)
    )
    toastView.notificationToastContainer.setBackgroundColor(
        ContextCompat.getColor(requireContext(), R.color.error_toast_background)
    )
    val tv = TypedValue()
    var actionBarHeight = 0
    if (requireActivity().theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    }
    toast.view = toastView.root
    toast.duration = Toast.LENGTH_LONG
    toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, actionBarHeight)
    toast.show()
}