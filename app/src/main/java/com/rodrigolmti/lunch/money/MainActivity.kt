package com.rodrigolmti.lunch.money

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rodrigolmti.lunch.money.ui.components.BouncingImageAnimation
import com.rodrigolmti.lunch.money.ui.components.Center
import com.rodrigolmti.lunch.money.ui.components.HorizontalSpacer
import com.rodrigolmti.lunch.money.ui.components.LunchButton
import com.rodrigolmti.lunch.money.ui.components.LunchTextField
import com.rodrigolmti.lunch.money.ui.components.TiledBackgroundScreen
import com.rodrigolmti.lunch.money.ui.components.VerticalSpacer
import com.rodrigolmti.lunch.money.ui.theme.Body
import com.rodrigolmti.lunch.money.ui.theme.ContentBrand
import com.rodrigolmti.lunch.money.ui.theme.ContentDefault
import com.rodrigolmti.lunch.money.ui.theme.Header
import com.rodrigolmti.lunch.money.ui.theme.LunchMoneyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LunchMoneyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GetStatedScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GetStatedScreen() {
    Scaffold(
        modifier = Modifier
    ) {

        var apiToken by rememberSaveable { mutableStateOf("") }

        TiledBackgroundScreen()

        Center {
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = ContentDefault
                ),
                border = BorderStroke(
                    width = Dp.Hairline,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    VerticalSpacer(height = 16.dp)

                    BouncingImageAnimation()

                    VerticalSpacer(height = 16.dp)

                    Text(
                        text = "Welcome Back!",
                        textAlign = TextAlign.Center,
                        style = Header,
                        color = Color.White,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )

                    VerticalSpacer(height = 16.dp)

                    LunchTextField(label = "Api Token", text = apiToken) {
                        apiToken = it
                    }

                    VerticalSpacer(height = 16.dp)

                    LunchButton(label = "LOGIN") {

                    }

                    VerticalSpacer(height = 16.dp)

                    Row {
                        Text(
                            text = "Don't have a API Key?",
                            textAlign = TextAlign.Center,
                            style = Body,
                            color = Color.White
                        )
                        HorizontalSpacer(4.dp)
                        Text(
                            text = "Get one here.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {  },
                            style = Body.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            color = ContentBrand
                        )
                    }

                }
            }
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun GetStatedScreenPreview() {
    LunchMoneyTheme {
        GetStatedScreen()
    }
}
