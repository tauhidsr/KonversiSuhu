package com.sultantauhid3127.assessment1.ui.screen

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sultantauhid3127.assessment1.R
import com.sultantauhid3127.assessment1.ui.theme.Assessment1Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertScreen(navController: NavController) {
    var suhuInput by rememberSaveable { mutableStateOf("") }
    val jenisSuhuList = listOf(
        stringResource(R.string.celsius),
        stringResource(R.string.fahrenheit),
        stringResource(R.string.kelvin)
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedSuhu by rememberSaveable { mutableStateOf(jenisSuhuList[0]) }

    val suhuTujuanList = listOf(
        stringResource(R.string.celsius),
        stringResource(R.string.fahrenheit),
        stringResource(R.string.kelvin)
    )
    var suhuTujuan by rememberSaveable { mutableStateOf(suhuTujuanList[0]) }

    var hasilKonversi by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState)}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = suhuInput,
                onValueChange = { suhuInput = it},
                label = { Text(stringResource(id = R.string.label_input_suhu))},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {expanded = !expanded}
            ) {
                OutlinedTextField(
                    value = selectedSuhu,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.label_input_suhu))},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false}
                ) {
                    jenisSuhuList.forEach { suhu ->
                        DropdownMenuItem(
                            text = { Text(suhu)},
                            onClick = {
                                selectedSuhu = suhu
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.label_konversi_ke),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )

            suhuTujuanList.forEach { tujuan ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (tujuan == suhuTujuan),
                        onClick = { suhuTujuan = tujuan}
                    )
                    Text(text = tujuan)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val suhu = suhuInput.toDoubleOrNull()
                    if (suhu != null) {
                        val hasil = konversiSuhu(suhu, selectedSuhu, suhuTujuan)
                        hasilKonversi = "$suhu $selectedSuhu = $hasil $suhuTujuan"
                    } else {
                        hasilKonversi = ""
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(R.string.input_invalid),
                                withDismissAction = true
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.convert))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (hasilKonversi.isNotEmpty()) {
                Text(
                    text = hasilKonversi,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, hasilKonversi)
                        }
                        context.startActivity(Intent.createChooser(shareIntent,context.getString(R.string.share_title)))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.share))
                }
            }
        }
    }
}

fun konversiSuhu(suhu: Double, dari: String, ke: String): Double {
    val suhuCelsius = when (dari) {
        "Celsius" -> suhu
        "Fahrenheit" -> (suhu - 32) *5 / 9
        "Kelvin" -> suhu - 273.15
        else -> suhu
    }

    return when (ke) {
        "Celsius" -> suhuCelsius
        "Fahrenheit" -> (suhuCelsius * 9 / 5) + 32
        "Kelvin" -> suhuCelsius +273.15
        else -> suhu
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ConvertScreenPreview() {
    Assessment1Theme {
        ConvertScreen(
            navController = rememberNavController()
        )
    }
}
