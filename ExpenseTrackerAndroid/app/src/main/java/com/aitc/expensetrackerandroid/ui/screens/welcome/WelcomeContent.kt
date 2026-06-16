package com.aitc.expensetrackerandroid.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aitc.expensetrackerandroid.R
import com.aitc.expensetrackerandroid.ui.theme.ExpenseTrackerAndroidThemePreview
import com.aitc.expensetrackerandroid.ui.theme.appDimens
import com.aitc.expensetrackerandroid.ui.theme.appTextStyles

@Composable
fun WelcomeContent(data: WelcomeUiState, modifier: Modifier = Modifier) {
    val dimens = MaterialTheme.appDimens
    val pagerState = rememberPagerState(
        pageCount = { data.slides.size },
    )
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        WelcomeHeader(modifier = Modifier.defaultPadding())
        Spacer(modifier = Modifier.height(dimens.spacingMd))
        WelcomeBody(
            state = data,
            pagerState = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.weight(1f))
        WelcomeBottom(modifier = Modifier.defaultPadding(), pagerState = pagerState)
        Spacer(modifier = Modifier.height(dimens.spacingMd))
    }
}

@Composable
fun Modifier.defaultPadding() = this.padding(horizontal = MaterialTheme.appDimens.screenPadding)

@Composable
private fun WelcomeHeader(modifier: Modifier = Modifier) {
    val textStyles = MaterialTheme.appTextStyles
    val dimens = MaterialTheme.appDimens

    Row(modifier.padding(vertical = dimens.spacingXs)) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Skip",
            style = textStyles.welcomeSkip,
        )
    }
}

@Composable
private fun WelcomeBody(
    modifier: Modifier = Modifier,
    state: WelcomeUiState?,
    pagerState: PagerState,
) {
    state ?: return

    val textStyles = MaterialTheme.appTextStyles
    val dimens = MaterialTheme.appDimens

    HorizontalPager(
        state = pagerState,
    ) {
        val slide = state.slides[it]
        Column(modifier) {
            Image(
                painter = painterResource(R.drawable.welcome_2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            )
            Spacer(modifier = Modifier.height(dimens.spacingXl))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.screenPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = slide.title,
                    style = textStyles.welcomeTitle.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(dimens.spacingSm))
                Text(
                    text = slide.description,
                    style = textStyles.welcomeBody,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun WelcomeBottom(modifier: Modifier = Modifier, pagerState: PagerState) {
    val textStyles = MaterialTheme.appTextStyles
    val colorScheme = MaterialTheme.colorScheme
    val dimens = MaterialTheme.appDimens

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Continue",
                style = textStyles.button,
            )
        }
        Spacer(modifier = Modifier.height(dimens.spacingSm))
        Row {
            repeat(pagerState.pageCount) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = dimens.spacingXs / 2)
                        .size(8.dp)
                        .background(
                            if (pagerState.currentPage == index) {
                                colorScheme.primary
                            } else {
                                colorScheme.onSurfaceVariant
                            },
                            CircleShape,
                        ),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeContentPreview() {
    ExpenseTrackerAndroidThemePreview {
        WelcomeContent(
            data = WelcomeUiState(
                slides = listOf(
                    WelcomeSlide(
                        title = "Track Every Expense",
                        description = "Record your daily spending effortlessly and stay aware of where your money goes.",
                        image = R.drawable.welcome_1,
                    ),
                    WelcomeSlide(
                        title = "Plan Smarter Budgets",
                        description = "Set monthly budgets, monitor your progress, and avoid overspending with ease.",
                        image = R.drawable.welcome_2,
                    ),
                    WelcomeSlide(
                        title = "Gain Financial Insights",
                        description = "Visualize spending trends, analyze reports, and make informed financial decisions.",
                        image = R.drawable.welcome_3,
                    ),
                ),
            ),
        )
    }
}
