package com.nikitadevktad.assemblyyao.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikitadevktad.assemblyyao.R

@Composable
fun GoalsContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.logo_anr_cmyk),
            contentDescription = stringResource(R.string.assembly_logo),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(160.dp)
                .height(100.dp)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = stringResource(R.string.title_goals_content),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        Text(
            text = stringResource(R.string.main_text_goals_content),
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 26.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(R.string.our_goals),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp),
        )
        GoalItem(
            number = "01",
            title = stringResource(R.string.goal_memory_title),
            description = stringResource(R.string.goal_memory_description),
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        GoalItem(
            number = "02",
            title = stringResource(R.string.goal_agreement_title),
            description = stringResource(R.string.goal_agreement_description),
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        GoalItem(
            number = "03",
            title = stringResource(R.string.goal_values_title),
            description = stringResource(R.string.goal_values_description),
        )
    }
}

@Composable
private fun GoalItem(
    number: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 26.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview
@Composable
fun PreviewGoalsContent() {
    GoalsContent()
}