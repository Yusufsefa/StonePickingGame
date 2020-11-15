package com.yyusufsefa.stonepickinggame

import android.content.Context
import android.widget.Toast

fun Context.toast(string: String){
    Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
}