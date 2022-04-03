package com.lindevhard.felia.ui.wallet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.lindevhard.felia.R
import com.lindevhard.felia.ui.R as CommonUI
import com.lindevhard.felia.component.wallet.detail.WalletDetail
import com.lindevhard.felia.ui.components.appbar.AppBarScaffold
import com.lindevhard.felia.ui.components.button.PrimaryButton
import com.lindevhard.felia.ui.theme.mainText
import com.lindevhard.felia.utils.BalanceUtils
import com.lindevhard.felia.wallet.main.domain.model.WalletDetailDomain

@Composable
fun WalletDetailUi(component: WalletDetail) {
    val state by component.models.subscribeAsState()

    val title = if (state is WalletDetail.Model.Loaded) {
        (state as WalletDetail.Model.Loaded).wallet.name
    } else {
        stringResource(id = R.string.msg_wallet)
    }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AppBarScaffold(
            title = title,
            onBackPress = { component.onBackClicked() },
            modifier = Modifier.weight(1f),
        ) {
            when (state) {
                is WalletDetail.Model.Loaded -> {
                    WalletDetailUi(wallet = (state as WalletDetail.Model.Loaded).wallet)
                }
                WalletDetail.Model.Loading -> Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }

        if (state is WalletDetail.Model.Loaded) {
            PrimaryButton(
                text = stringResource(id = R.string.msg_receive),
                onClick = { component.onReceiveClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            PrimaryButton(
                text = stringResource(id = R.string.msg_send),
                onClick = { component.onSendClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
fun WalletDetailUi(wallet: WalletDetailDomain) {
    val balanceText = BalanceUtils
        .getScaledValueMinimal(wallet.balance, wallet.decimals, 8) + " ${wallet.symbol}"

    val fiatBalanceText =
        "≈ " + BalanceUtils.getScaledValue(wallet.fiatBalance, wallet.decimals, 2) + " $"

    Column(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .background(
                    MaterialTheme.colors.surface.copy(alpha = 0.8f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row {
                Text(text = wallet.asset.type, style = MaterialTheme.typography.body1)
            }
            Image(
                painter = rememberImagePainter(wallet.logo),
                contentDescription = "Asset logo",
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .size(60.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = balanceText,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.mainText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = fiatBalanceText,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.mainText.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        wallet.marketData?.let { market ->
            val price = "$" + BalanceUtils.getScaledValue(
                market.price.toBigDecimal(),
                0,
                2
            )

            val priceChange = BalanceUtils.getScaledValue(
                market.priceChange30d.toBigDecimal(),
                0,
                2
            ) + "%"

            val isMarketUp = market.priceChange30d >= 0

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .background(
                        MaterialTheme.colors.surface.copy(alpha = 0.8f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = CommonUI.drawable.ic_coinmarketcap),
                        contentDescription = "Asset Logo",
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .wrapContentSize()
                            .scale(1.4f)
                            .offset(x=16.dp)
                            .clip(CircleShape)
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = price,
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.mainText,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = priceChange,
                            style = MaterialTheme.typography.subtitle2,
                            color = if(isMarketUp) Color.Green else Color.Red,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .background(
                    MaterialTheme.colors.surface.copy(alpha = 0.8f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.msg_about),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.mainText,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            Text(
                text = wallet.asset.description,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.mainText,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}