package com.reloader.coroutines

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

   // , CoroutineScope la implementacion
//    override val coroutineContext: CoroutineContext   // por defecto usa  Dispacher.Main
//        get() = Dispatchers.Main + job
//
// poniendo  Lifecycle.scope  remplaza a esto
//    private lateinit var job: Job




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       // job = SupervisorJob() lifecycleScope lo resume por detras para cuando el activity  se destruya..

        //si utilizamos  solo Job  si falla   una  las demas  corrutinas se van cancelar

        submit.setOnClickListener {

            //definir  donde  ocurrirar  la courutina / en el hilo principal / o hilo secundario para eso se usa  Dispachers

            //GlobalScope  solo funciona  el tiempo de vida de este activity
            lifecycleScope.launch(Dispatchers.Main) {   //dispachers.default  es no poner nada
                //Main  hilo principal
                //DispacherIO  cuando  esta esperando un resultado sea un servicio
                //lo que hace es crear  un nuevo hilo
                val success = withContext(Dispatchers.IO)
                {
                    validateLogin(username.text.toString(), password.text.toString())

                    //funcion de suspension  va  bloquear un momento hasta  que haya  un resultado //Suspend
                }
                toast(if (success as Boolean) "Success" else "Failure") // aqui sale error porque  un toast solo ocurre en un hilo principal
            }


        }
    }

    private fun validateLogin(user: String, pass: String): Any {

        Thread.sleep(2000)

        return user.isNotEmpty() && pass.isNotEmpty()
    }


    private fun Context.toast(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {

        //job.cancel() elimina por el lifecycle
        super.onDestroy()
    }
}
