package com.reloader.coroutines

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit.setOnClickListener {
            //ejemplo que tengamos  2 servicios  en pararelo

            lifecycleScope.launch(Dispatchers.Main) {

                val success1 = async(Dispatchers.IO)
                {
                    validateLogin1(username.text.toString(), password.text.toString())
                }

                val success2 = async(Dispatchers.IO)
                {
                    validateLogin2(username.text.toString(), password.text.toString())
                }
                toast(if (success1.await() as Boolean && success2.await() as Boolean) "Success" else "Failure") // aqui sale error porque  un toast solo ocurre en un hilo principal
           //await  te da el resultado de esa operación
            }


        }
    }

    private fun validateLogin1(user: String, pass: String): Any {

        Thread.sleep(2000)

        return user.isNotEmpty() && pass.isNotEmpty()
    }

    private fun validateLogin2(user: String, pass: String): Any {

        Thread.sleep(2000)

        return user.isNotEmpty() && pass.isNotEmpty()
    }


    private fun Context.toast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
}
