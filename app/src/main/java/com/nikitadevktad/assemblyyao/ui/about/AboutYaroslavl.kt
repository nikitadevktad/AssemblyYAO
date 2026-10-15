package com.nikitadevktad.assemblyyao.ui.about

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitadevktad.assemblyyao.R

@Composable
fun AboutYaroslavl(
    modifier: Modifier = Modifier,
) {
    val facts = listOf(
        YaroslavlFact(R.string.yaroslavl_founded_value, R.string.yaroslavl_founded_label),
        YaroslavlFact(R.string.yaroslavl_area_value, R.string.yaroslavl_area_label),
        YaroslavlFact(R.string.yaroslavl_population_value, R.string.yaroslavl_population_label),
        YaroslavlFact(R.string.yaroslavl_center_value, R.string.yaroslavl_center_label),
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.yaroslavl_banner_three),
            contentDescription = stringResource(R.string.yaroslavl_photo_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(20.dp)),
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.yaroslavl_region_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(R.string.yaroslavl_region_intro),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        FactsGrid(facts = facts)
        YaroslavlInfoBlock(
            title = stringResource(R.string.yaroslavl_history_title),
            body = stringResource(R.string.yaroslavl_history_text),
        )
        YaroslavlInfoBlock(
            title = stringResource(R.string.yaroslavl_culture_title),
            body = stringResource(R.string.yaroslavl_culture_text),
        )
        YaroslavlInfoBlock(
            title = stringResource(R.string.yaroslavl_places_title),
            body = stringResource(R.string.yaroslavl_places_text),
        )
    }
}

@Composable
private fun FactsGrid(
    facts: List<YaroslavlFact>,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        if (maxWidth >= 720.dp) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                facts.forEach { fact ->
                    FactCard(fact = fact, modifier = Modifier.weight(1f))
                }
            }
        } else if (maxWidth >= 400.dp) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                facts.chunked(2).forEach { rowFacts ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        rowFacts.forEach { fact ->
                            FactCard(fact = fact, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                facts.forEach { fact ->
                    FactCard(fact = fact, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun FactCard(
    fact: YaroslavlFact,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(fact.valueRes),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(
                text = stringResource(fact.labelRes),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Composable
private fun YaroslavlInfoBlock(
    title: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = body,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

private data class YaroslavlFact(
    @StringRes val valueRes: Int,
    @StringRes val labelRes: Int,
)

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun PreviewAboutYaroslavl() {
    AboutYaroslavl()
}
