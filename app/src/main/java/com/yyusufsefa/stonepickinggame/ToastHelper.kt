package com.yyusufsefa.stonepickinggame

import android.content.Context
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog

fun Context.toast(string: String){
    SweetAlertDialog(this)
        .setTitleText(string)
        .show()
}


fun Context.toastSuccess(string: String){
    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        .setTitleText(string)
        .show()
}