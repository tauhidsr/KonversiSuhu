package com.sultantauhid3127.assessment1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sultantauhid3127.assessment1.R
import com.sultantauhid3127.assessment1.navigation.Screen
import com.sultantauhid3127.assessment1.ui.theme.Assessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu}) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.menu),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = {showMenu = false}
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.menu_about))},
                            onClick = {
                                showMenu = false
                                navController.navigate(Screen.About.route)
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(16.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(190.dp)
            )

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = Modifier.size(90.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.Convert.route)
                }
            ) {
                Text(text = stringResource(R.string.button_start))
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomeScreenPreview() {
    Assessment1Theme {
        HomeScreen(
            navController = rememberNavController()
        )
    }
}