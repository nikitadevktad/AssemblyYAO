package com.nikitadevktad.assemblyyao.ui.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikitadevktad.assemblyyao.R
import com.nikitadevktad.assemblyyao.ui.memos.ForeignCitizenMemosContent

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    val aboutSections = listOf(
        AboutSectionItem(
            iconRes = R.drawable.target_check,
            titleRes = R.string.goals_objectives,
        ),
        AboutSectionItem(
            iconRes = R.drawable.groups,
            titleRes = R.string.structure,
        ),
        AboutSectionItem(
            iconRes = R.drawable.document_search,
            titleRes = R.string.documents,
        ),
        AboutSectionItem(
            iconRes = R.drawable.document_search,
            titleRes = R.string.memos,
        ),
        AboutSectionItem(
            iconRes = R.drawable.nest_cam,
            titleRes = R.string.about_yaroslavl,
        ),
        AboutSectionItem(
            iconRes = R.drawable.diversity,
            titleRes = R.string.cooperation,
        ),
    )
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val horizontalPadding = if (maxWidth >= 600.dp) 32.dp else 16.dp

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .widthIn(max = 900.dp)
                .verticalScroll(rememberScrollState())
                .padding(
                    start = horizontalPadding,
                    end = horizontalPadding,
                    top = 20.dp,
                    bottom = 32.dp,
                ),
        ) {
            Text(
                text = stringResource(R.string.about_the_assembly),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            aboutSections.forEach { sectionItem ->
                AboutSection(
                    painter = sectionItem.iconRes,
                    text = sectionItem.titleRes,
                ) {
                    when (sectionItem.titleRes) {
                        R.string.goals_objectives -> GoalsContent()
                        R.string.structure -> StructureContent()
                        R.string.documents -> DocumentsContent()
                        R.string.memos -> ForeignCitizenMemosContent()
                        R.string.about_yaroslavl -> AboutYaroslavl()
                        R.string.cooperation -> CooperationContent()
                    }
                }
            }
            AboutQuote()
        }
    }
}

@Preview
@Composable
fun PreviewAboutScreen() {
    AboutScreen()
}
