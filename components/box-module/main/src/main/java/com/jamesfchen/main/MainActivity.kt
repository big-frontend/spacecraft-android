package com.jamesfchen.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.jamesfchen.main.ui.theme.SpacecraftAndroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val mainViewModel by viewModels<MainViewModel>()
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
            }
        }
//        startActivity(Intent(MainActivity@this, SpringBoardActivity::class.java))
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content,InfosFragment(), "tag_infos")
//            .add(InfosFragment(), "tag_infos") #当做一种后台fragment
            .commitAllowingStateLoss()
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

