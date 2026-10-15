package com.nikitadevktad.assemblyyao.ui.contacts

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.nikitadevktad.assemblyyao.R

@Composable
fun ContactsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val phone = stringResource(R.string.phone_value)
    val email = stringResource(R.string.email_value)
    val address = stringResource(R.string.address_value)

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val horizontalPadding = if (maxWidth >= 600.dp) 32.dp else 16.dp

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .widthIn(max = 840.dp)
                .fillMaxSize()
                .testTag("contacts_screen")
                .verticalScroll(rememberScrollState())
                .padding(
                    start = horizontalPadding,
                    end = horizontalPadding,
                    top = 20.dp,
                    bottom = 32.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.contacts),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(R.string.contacts_intro),
                style = MaterialTheme.typography.bodyLarge,
            )
            ContactCard(
                title = stringResource(R.string.phone),
                value = phone,
                action = stringResource(R.string.call),
                onClick = {
                    val dialNumber = phone.filter { it.isDigit() || it == '+' }
                    context.open(Intent(Intent.ACTION_DIAL, "tel:$dialNumber".toUri()))
                },
            )
            ContactCard(
                title = stringResource(R.string.email),
                value = email,
                action = stringResource(R.string.write),
                onClick = { context.open(Intent(Intent.ACTION_SENDTO, "mailto:$email".toUri())) },
            )
            ContactCard(
                title = stringResource(R.string.address),
                value = address,
                action = stringResource(R.string.show_on_map),
                onClick = {
                    val uri = "geo:0,0?q=${Uri.encode(address)}".toUri()
                    context.open(Intent(Intent.ACTION_VIEW, uri))
                },
            )
            ContactCard(
                title = stringResource(R.string.website),
                value = stringResource(R.string.website_value),
                action = stringResource(R.string.open_website),
                onClick = {
                    context.open(Intent(Intent.ACTION_VIEW, OFFICIAL_SITE.toUri()))
                },
            )
        }
    }
}

@Composable
private fun ContactCard(
    title: String,
    value: String,
    action: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = title, style = MaterialTheme.typography.labelLarge)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
            Button(onClick = onClick) {
                Text(action)
            }
        }
    }
}

private fun Context.open(intent: Intent) {
    runCatching { startActivity(intent) }
}

private const val OFFICIAL_SITE = "https://ассамблея76.рф/"
