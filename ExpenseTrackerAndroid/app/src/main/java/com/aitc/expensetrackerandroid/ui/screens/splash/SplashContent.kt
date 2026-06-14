package com.aitc.expensetrackerandroid.ui.screens.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.ui.theme.ExpenseTrackerAndroidThemePreview
import com.aitc.expensetrackerandroid.ui.theme.appDimens
import com.aitc.expensetrackerandroid.ui.theme.appTextStyles

@Composable
fun SplashContent(
    showLoading: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val dimens = MaterialTheme.appDimens
    val textStyles = MaterialTheme.appTextStyles

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimens.screenPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_splash_logo),
            contentDescription = null,
            modifier = Modifier.size(dimens.iconLarge * 3),
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(dimens.spacingLg))
        Text(
            text = stringResource(R.string.app_name),
            style = textStyles.screenTitle,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(dimens.spacingSm))
        Text(
            text = stringResource(R.string.splash_tagline),
            style = textStyles.listItemSubtitle,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        if (showLoading) {
            Spacer(modifier = Modifier.height(dimens.spacingXl))
            CircularProgressIndicator(
                modifier = Modifier.size(dimens.iconLarge),
                strokeWidth = dimens.spacingXs,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(dimens.spacingMd))
            Text(
                text = stringResource(R.string.splash_title),
                style = textStyles.caption,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashContentPreview() {
    ExpenseTrackerAndroidThemePreview {
        Surface(color = MaterialTheme.colorScheme.background) {
            SplashContent(showLoading = true)
        }
    }
}
