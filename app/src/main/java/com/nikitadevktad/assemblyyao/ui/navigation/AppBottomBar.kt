package com.nikitadevktad.assemblyyao.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppBottomBar(
    selectedDestination: TopLevelDestination,
    onClickIcon: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        TopLevelDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = destination == selectedDestination,
                onClick = {
                    onClickIcon(destination)
                },
                modifier = Modifier.testTag("bottom_${destination.route}"),
                icon = {
                    Icon(
                        painter = painterResource(destination.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = {
                    Text(text = stringResource(destination.titleRes))
                }
            )

        }
    }
}

@Preview
@Composable
fun PreviewAppBottomBar() {
    AppBottomBar(
        selectedDestination = TopLevelDestination.HOME,
        onClickIcon = {},
    )
}

@Composable
fun AppNavigationRail(
    selectedDestination: TopLevelDestination?,
    onClickIcon: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier) {
        TopLevelDestination.entries.forEach { destination ->
            NavigationRailItem(
                selected = destination == selectedDestination,
                onClick = { onClickIcon(destination) },
                icon = {
                    Icon(
                        painter = painterResource(destination.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = { Text(text = stringResource(destination.titleRes)) },
                alwaysShowLabel = false,
                modifier = Modifier.testTag("rail_${destination.route}"),
            )
        }
    }
}
