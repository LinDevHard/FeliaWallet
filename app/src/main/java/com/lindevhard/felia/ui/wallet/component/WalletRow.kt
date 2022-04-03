package com.lindevhard.felia.ui.wallet.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.lindevhard.felia.ui.theme.FeliaTheme
import com.lindevhard.felia.ui.theme.mainText
import com.lindevhard.felia.utils.BalanceUtils
import com.lindevhard.felia.wallet.main.domain.model.Wallet

@Composable
fun WalletRow(
    wallet: Wallet,
    modifier: Modifier = Modifier,
) {
    WalletRow(
        logo = wallet.logo,
        name = wallet.name,
        symbol = wallet.symbol,
        balance = BalanceUtils.getScaledValueMinimal(wallet.balance, wallet.decimals, 8),
        fiatBalance = "$" +  BalanceUtils.getScaledValue(wallet.fiatBalance, wallet.decimals, 2),
        modifier = modifier
    )
}

@Composable
fun WalletRow(
    logo: String,
    name: String,
    symbol: String,
    balance: String,
    fiatBalance: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(logo),
                contentDescription = "Asset logo",
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .size(36.dp)
                    .clip(CircleShape)
            )

            Column() {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.mainText,
                    modifier = Modifier
                )

                Text(
                    text = symbol,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.mainText.copy(alpha = 0.7f),
                    modifier = Modifier
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = balance,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.mainText,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = fiatBalance,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.mainText.copy(alpha = 0.7f),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun WalletRowPreview() {
    FeliaTheme() {
        WalletRow(
            logo = "",
            name = "Ethereum",
            symbol = "ETH",
            balance = "0.0032",
            fiatBalance = "214.4",
            modifier = Modifier
        )
    }
}