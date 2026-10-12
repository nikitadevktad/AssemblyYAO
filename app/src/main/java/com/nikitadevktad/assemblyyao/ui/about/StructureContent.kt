package com.nikitadevktad.assemblyyao.ui.about

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun StructureContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.structure_federal_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        PersonCard(
            photoRes = R.drawable.vlidimir_abdulaevitch,
            name = stringResource(R.string.vasiliev_name),
            position = stringResource(R.string.vasiliev_position),
        )
        PersonCard(
            photoRes = R.drawable.alexsander_visilievitch,
            name = stringResource(R.string.darkovich_name),
            position = stringResource(R.string.darkovich_position),
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        Text(
            text = stringResource(R.string.structure_regional_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        PersonCard(
            photoRes = R.drawable.sergey_lvovitch,
            name = stringResource(R.string.talanov_name),
            position = stringResource(R.string.talanov_position),
        )
    }
}

@Composable
private fun PersonCard(
    @DrawableRes photoRes: Int,
    name: String,
    position: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Image(
                painter = painterResource(photoRes),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = position,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
fun PreviewStructureContent() {
    StructureContent()
}