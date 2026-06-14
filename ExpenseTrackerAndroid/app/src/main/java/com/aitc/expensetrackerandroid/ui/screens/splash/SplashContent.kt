package com.aitc.expensetrackerandroid.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.ui.theme.ExpenseTrackerAndroidThemePreview
import com.aitc.expensetrackerandroid.ui.theme.PrimaryColor
import com.aitc.expensetrackerandroid.ui.theme.SplashBackground
import com.aitc.expensetrackerandroid.ui.theme.SplashBrandText

private const val SplashLogoSize = 80

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(SplashLogoSize.dp),
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.app_name),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashContentPreview() {
    ExpenseTrackerAndroidThemePreview {
        Surface(color = SplashBackground) {
            SplashContent()
        }
    }
}
