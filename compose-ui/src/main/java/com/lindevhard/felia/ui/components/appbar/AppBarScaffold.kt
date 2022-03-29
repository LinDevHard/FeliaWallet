package com.lindevhard.felia.ui.components.appbar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lindevhard.felia.ui.R


/**
 * AppBarScaffold displays TopAppBar above specified content. Content is wrapped with
 * scrollable Column. TopAppBar's elevation is animated depending on content scroll state.
 *
 * Back arrow is shown only if [onBackPress] is not null
 *
 * @param onBackPress Call back when back arrow icon is pressed
 */
@Composable
fun AppBarScaffold(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackPress: (() -> Unit)? = null,
    isLoading: Boolean = false,
    backgroundColor: Color = MaterialTheme.colors.background,
    leftButtonPainter: Painter = painterResource(id = R.drawable.ic_arrow_back),
    actions: @Composable RowScope.() -> Unit = {},
    loaderContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val contentColor = contentColorFor(backgroundColor)
    val scrollState: ScrollState = rememberScrollState()

    val elevation by animateDpAsState(
        if (scrollState.value == 0) 0.dp else AppBarDefaults.TopAppBarElevation
    )

    Surface(
        modifier = modifier.statusBarsPadding(),
        color = backgroundColor,
        contentColor = contentColor
    ) {
        val topBar = @Composable {
            AppBar(
                title,
                leftButtonPainter,
                backgroundColor,
                onBackPress,
                actions,
                elevation
            )
        }

        val body = @Composable {
            Column {
                Column(modifier = Modifier.verticalScroll(state = scrollState)) {
                    content()
                }
            }
        }

        SubcomposeLayout { constraints ->
            val layoutWidth = constraints.maxWidth
            val layoutHeight = constraints.maxHeight
            val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

            layout(layoutWidth, layoutHeight) {
                val topBarPlaceables = subcompose(AppBarContent.AppBar, topBar).map {
                    it.measure(looseConstraints)
                }
                val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height ?: 0
                val bodyContentHeight = layoutHeight - topBarHeight

                val bodyContentPlaceables = subcompose(AppBarContent.MainContent) {
                    if (isLoading) {
                        loaderContent()
                    } else {
                        body()
                    }
                }.map { it.measure(looseConstraints.copy(maxHeight = bodyContentHeight)) }

                bodyContentPlaceables.forEach {
                    it.place(0, topBarHeight)
                }
                topBarPlaceables.forEach {
                    it.place(0, 0)
                }
            }
        }
    }
}


@Composable
fun AppBar(
    title: String = "",
    leftButtonPainter: Painter,
    backgroundColor: Color,
    onBackPress: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    elevation: Dp
) {

    var navigationIcon: @Composable (() -> Unit)? = null
    onBackPress?.let {
        navigationIcon = { BackArrow(leftButtonPainter) { onBackPress.invoke() } }
    }

    Surface(
        color = backgroundColor,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = elevation,
        shape = RectangleShape
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppBarDefaults.ContentPadding)
                .height(AppBarHeight)
        ) {
            navigationIcon?.let {
                Row(
                    TitleIconModifier
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                        content = it
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
            }

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }
        }
    }
}

private val AppBarHorizontalPadding = 4.dp

private val TitleIconModifier = Modifier
    .fillMaxHeight()
    .width(72.dp - AppBarHorizontalPadding)

private val AppBarHeight = 56.dp

enum class AppBarContent {
    AppBar,
    MainContent
}
