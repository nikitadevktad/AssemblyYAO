package com.nikitadevktad.assemblyyao.ui.about

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitadevktad.assemblyyao.R

@Composable
fun AboutSection(
    painter: Int,
    text: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(20.dp),
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(20.dp),
            ),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painter = painterResource(painter),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(25.dp),
                )

                Text(
                    text = stringResource(text),
                    modifier = Modifier.weight(1f),
                )

                IconButton(
                    onClick = { expanded = !expanded },
                ) {
                    if (!expanded) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_forward),
                            contentDescription = "Раскрыть информацию",
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.arrow_downward),
                            contentDescription = "Свернуть информацию",
                        )
                    }
                }
            }
            if (expanded) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAboutSection() {
    AboutSection(
        painter = R.drawable.info,
        text = R.string.about_the_assembly,
        content = {},
    )
}