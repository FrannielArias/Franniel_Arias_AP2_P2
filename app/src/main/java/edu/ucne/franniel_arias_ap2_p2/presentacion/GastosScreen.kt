package edu.ucne.franniel_arias_ap2_p2.presentacion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.franniel_arias_ap2_p2.data.remote.Resource
import edu.ucne.franniel_arias_ap2_p2.domain.model.Gastos
import java.text.NumberFormat
import java.util.Locale

@Composable
fun GastosScreen(
    viewModel: GastosViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GastoScreenBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoScreenBody(
    state: GastosUiState,
    onEvent: (GastosUiEvent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(GastosUiEvent.ShowBottonSheet)
                },
                modifier = Modifier.testTag("fab_add")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Gasto")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (val gastos = state.ListaGastos) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is Resource.Success -> {
                    val data = gastos.data ?: emptyList()
                    if (data.isEmpty()) {
                        Text(
                            text = "No hay gastos registrados",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(data) { gastos ->
                                GastoItem(
                                    gastos = gastos,
                                    onClick = {
                                        onEvent(GastosUiEvent.GetGasto(gastos.gastosId ?: 0))
                                        onEvent(GastosUiEvent.ShowBottonSheet)
                                    }
                                )
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = gastos.message ?: "Error al cargar los datos",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                null -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        if (state.isSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    onEvent(GastosUiEvent.HideBottonSheet)
                },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = if (state.gastosId > 0) "Editar Gasto" else "Nuevo Gasto",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    OutlinedTextField(
                        value = state.fecha,
                        onValueChange = { onEvent(GastosUiEvent.FechaChange(it)) },
                        label = { Text("Fecha") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = state.suplidor,
                        onValueChange = { onEvent(GastosUiEvent.SuplidorChange(it)) },
                        label = { Text("Suplidor") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = state.ncf,
                        onValueChange = { onEvent(GastosUiEvent.NcfChange(it)) },
                        label = { Text("NCF") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = state.itbis,
                        onValueChange = { onEvent(GastosUiEvent.ItbisChange(it)) },
                        label = { Text("ITBIS") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = state.monto,
                        onValueChange = { onEvent(GastosUiEvent.MontoChange(it)) },
                        label = { Text("Monto") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedButton(
                            onClick = {
                                onEvent(GastosUiEvent.HideBottonSheet)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = {
                                if (state.fecha.isNotBlank() &&
                                    state.suplidor.isNotBlank() &&
                                    state.ncf.isNotBlank() &&
                                    state.itbis.isNotBlank() &&
                                    state.monto.isNotBlank()
                                ) {
                                    val gastos = Gastos(
                                        gastosId = if (state.gastosId > 0) state.gastosId else null,
                                        fecha = state.fecha,
                                        suplidor = state.suplidor,
                                        ncf = state.ncf,
                                        itbis = state.itbis.toDoubleOrNull() ?: 0.0,
                                        monto = state.monto.toDoubleOrNull() ?: 0.0
                                    )
                                    onEvent(GastosUiEvent.Crear(gastos))
                                }
                            },
                            modifier = Modifier.weight(1f),
                            enabled = state.fecha.isNotBlank() &&
                                    state.suplidor.isNotBlank() &&
                                    state.ncf.isNotBlank() &&
                                    state.itbis.isNotBlank() &&
                                    state.monto.isNotBlank()
                        ) {
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun GastoItem(
    gastos: Gastos,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Suplidor", // Etiqueta pequeña
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = gastos.suplidor, // Nombre grande y en negrita
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = gastos.fecha,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Text(
                text = "NCF: ${gastos.ncf}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Divider(modifier = Modifier.padding(vertical = 4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "ITBIS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = NumberFormat.getCurrencyInstance(Locale.US).format(gastos.itbis),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = NumberFormat.getCurrencyInstance(Locale.US).format(gastos.monto),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GastoScreenPreview() {
    val sampleState = GastosUiState(
        ListaGastos = Resource.Success(
            listOf(
                Gastos(
                    gastosId = 10, fecha = "2025-10-25", suplidor = "Gasolinera Total", ncf = "N349021570", itbis = 4.55, monto = 91.00
                ),
                Gastos(
                    gastosId = 11, fecha = "2025-10-28", suplidor = "Farmacia Cristal", ncf = "C780112345", itbis = 12.00, monto = 240.00
                ),
                Gastos(
                    gastosId = 12, fecha = "2025-11-01", suplidor = "Restaurante El Sabor", ncf = "R567890123", itbis = 3.25, monto = 65.00
                )
            )
        ),
        isSheetVisible = false
    )
    MaterialTheme {
        GastoScreenBody(sampleState) {}
    }
}