package com.nikitadevktad.assemblyyao.ui.about

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nikitadevktad.assemblyyao.R

@Composable
fun AboutScreen(

    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.about_the_assembly),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Preview
@Composable
fun PreviewAboutScreen() {
    AboutScreen()
}