package com.jamesfchen.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.jamesfchen.main.databinding.ActivityMainBinding
import com.jamesfchen.main.ui.theme.SpacecraftAndroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val mainViewModel by viewModels<MainViewModel>()
    val navCtrl: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return@lazy navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpacecraftAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        Greeting("Android", mainViewModel)
                        Counter(mainViewModel)
                    }
                }
                AndroidViewBinding(factory = ActivityMainBinding::inflate)
            }
        }
//        startActivity(Intent(MainActivity@this, TestActivity::class.java))
//        supportFragmentManager.beginTransaction()
//            .add(android.R.id.content, InfosFragment(), "tag_infos")
//            .add(InfosFragment(), "tag_infos") #当做一种后台fragment
//            .commitAllowingStateLoss()
        Log.d("cjf", "MainActivity#onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("cjf", "MainActivity#onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("cjf", "MainActivity#onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("cjf", "MainActivity#onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("cjf", "MainActivity#onStop")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navCtrl.navigateUp() || super.onSupportNavigateUp()
    }
}

@Composable
fun Greeting(name: String, mainViewModel: MainViewModel) {
    val c = remember {
        mainViewModel.countState
    }
    Text(text = "Hello $name ${c.value}")
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    SpacecraftAndroidTheme {
//        Greeting("Android")
//    }
//}

@Composable
fun Counter(mainViewModel: MainViewModel) {
    val c = remember {
        mainViewModel.countState
    }
    TextButton(onClick = {
        mainViewModel.viewModelScope.launch {
            flow {
                repeat(100) {
                    emit(it)
                    delay(1000)
                }

            }.collect {
                c.value += 1
            }
        }
    }) {
        Text(text = "开始计数")
    }
}


