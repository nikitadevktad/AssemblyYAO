package com.nikitadevktad.assemblyyao

import android.app.Application

class AssemblyYaoApplication : Application() {
    val container: AppContainer by lazy { AppContainer(this) }
}
