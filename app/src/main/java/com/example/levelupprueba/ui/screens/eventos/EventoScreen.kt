package com.example.levelupprueba.ui.screens.eventos

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned // esta te permite scrollear la pantalla hacia el mapa
import androidx.compose.ui.layout.positionInParent // esta te permite obtener la posicion del mapa en la pantalla
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import com.example.levelupprueba.model.evento.Evento
import com.example.levelupprueba.model.evento.RecompensaCanje
import com.example.levelupprueba.ui.components.buttons.LevelUpButton
import com.example.levelupprueba.ui.components.cards.LevelUpCard
import com.example.levelupprueba.ui.components.dialogs.LevelUpAlertDialog
import com.example.levelupprueba.ui.components.inputs.LevelUpOutlinedTextField
import com.example.levelupprueba.ui.components.overlays.LevelUpLoadingOverlay
import com.example.levelupprueba.ui.theme.*
import com.example.levelupprueba.viewmodel.EventoViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/* evento screen 
ODIO GENERAR ESTO TAN LARGO es horrible me satura mucho quedo muerto 

al menos esta vez si importe los componentes reutilizables y colores etc 
 */
@Composable
fun EventoScreen(
    viewModel: EventoViewModel,
    contentPadding: PaddingValues
) {
    val estado by viewModel.estado.collectAsState() //el by sirve como un for , recorre el estado y te dice el estado actual
    val dimens = LocalDimens.current //el current es para obtener el valor actual de las dimensiones
    
    // Estado de scroll para animacion automatica hacia el mapa
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    
    // Posicion del mapa en la pantalla (para hacer scroll hacia el)
    var mapaPosition by remember { mutableStateOf(0f) }
    
    // Cuando cambia el evento seleccionado, hacer scroll hacia el mapa
    LaunchedEffect(estado.eventoSeleccionado) {
        estado.eventoSeleccionado?.let {
            // Hacer scroll animado hacia el mapa
            coroutineScope.launch {
                scrollState.animateScrollTo(mapaPosition.toInt())
            }
        }
    }

    //la columna principal mapa arriba, luego lista de eventos
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Usamos el estado de scroll controlado
            .padding(contentPadding)
            .padding(horizontal = dimens.screenPadding, vertical = dimens.screenPadding),
        verticalArrangement = Arrangement.spacedBy(dimens.fieldSpacing)
    ) {
        // Hero Section ,  el hero es la cabecera por si no recuerdan igual creoq aca vamos a colocar la topbar 
        EventoHeroSection(dimens)

        // card de puntos del usuario q tiene los import de vieewmodel 
        PuntosUsuarioCard(
            puntos = estado.puntosUsuario,
            dimens = dimens
        )

        // Mapa OSMDroid (OpenStreetMap) - Equivalente a Leaflet del JS
        MapaEventosOSM(
            eventos = estado.eventos,
            eventoSeleccionado = estado.eventoSeleccionado,
            onMarkerClick = { evento -> 
                viewModel.seleccionarEvento(evento) 
            },
            dimens = dimens
        )

        // Lista de eventos 
        Text(
            text = "Próximos eventos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = TextHigh, // Texto principal del theme LevelUp
            modifier = Modifier.padding(top = 8.dp)
        )

        if (estado.isLoading) {
            LevelUpLoadingOverlay(visible = true)
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                estado.eventos.forEach { evento ->
                    EventoCard(
                        evento = evento,
                        isSelected = estado.eventoSeleccionado?.id == evento.id,
                        onClick = { viewModel.seleccionarEvento(evento) },
                        dimens = dimens
                    )
                }
            }
        }

        // Canje de código 
        CanjeCodigoSection(
            codigoIngresado = estado.codigoIngresado,
            mensaje = estado.mensajeCodigo,
            onCodigoChange = { viewModel.onCodigoChange(it) },
            onCanjear = { viewModel.canjearCodigo() },
            dimens = dimens
        )

        // Recompensas al final
        RecompensasSection(
            recompensas = estado.recompensas,
            puntosUsuario = estado.puntosUsuario,
            onCanjear = { recompensa ->
                val exito = viewModel.canjearRecompensa(recompensa)
            },
            dimens = dimens
        )

        Spacer(modifier = Modifier.height(32.dp))
    }

    //este es por si no se pudo cargar los eventos o algo asi
    estado.error?.let { error ->
        LevelUpAlertDialog(
            onDismissRequest = { },
            title = "Error",
            text = error,
            confirmText = "Reintentar",
            onConfirm = { viewModel.refrescarEventos() },
            dimens = dimens
        )
    }
}

// la cabecera de la pantalla pero debajo de la principal creo q se vera
@Composable
fun EventoHeroSection(dimens: com.example.levelupprueba.ui.theme.Dimens) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Eventos Level-Up Gamer",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = BrandSecondary // Color cyan colortokens
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Participa en actividades gamer a nivel nacional y acumula puntos LevelUp asistiendo a eventos oficiales",
            style = MaterialTheme.typography.titleMedium,
            color = TextMedium, // Texto secundario del theme
            textAlign = TextAlign.Center
        )
    }
}

//luego de hacer toda la base importamos dentro de esas cosas estos componentes  reutilizables
@Composable
fun PuntosUsuarioCard(
    puntos: Int,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    // Usamos Card de Material3 porque LevelUpCard no soporta color personalizado
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Stars,
                    contentDescription = "Puntos",
                    tint = BrandSecondary, // Color cyan LevelUp
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Tus puntos LevelUp",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextMedium // Texto secundario
                    )
                    Text(
                        text = "$puntos puntos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = BrandSuccess // Verde para resaltar puntos
                    )
                }
            }
        }
    }
}

@Composable
fun EventoCard(
    evento: Evento,
    isSelected: Boolean,
    onClick: () -> Unit,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    // Usamos Card de Material3 porque necesitamos color dinámico según selección
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                MaterialTheme.colorScheme.primaryContainer 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = evento.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextHigh, // Texto principal blanco
                    modifier = Modifier.weight(1f)
                )
                
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = BrandSuccess // Verde para puntos
                ) {
                    Text(
                        text = "+${evento.puntos} pts",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextHigh, // Texto blanco sobre verde
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Place,
                    contentDescription = "Lugar",
                    modifier = Modifier.size(16.dp),
                    tint = BrandSecondary // Cyan para iconos
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = evento.lugar,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMedium // Texto secundario
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Dirección",
                    modifier = Modifier.size(16.dp),
                    tint = BrandSecondary // Cyan para iconos
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = evento.direccion,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMedium // Texto secundario
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Fecha",
                    modifier = Modifier.size(16.dp),
                    tint = BrandSecondary // Cyan para iconos
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Fecha: ${evento.fecha} — ${evento.hora}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMedium // Texto secundario
                )
            }
        }
    }
}


//ACA LA IMPORTACION INICIAL DEL MAPA 🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩

/**
 * Mapa OSMDroid (OpenStreetMap) con marcadores de eventos.
 * Equivalente a initLeaflet() del JavaScript.
 * 
 * Funcionalidad:
 * - Muestra Chile con zoom 5 (vista país completo)
 * - Agrega marcador por cada evento con coordenadas GPS exactas del JS
 * - Click en marcador → selecciona evento y muestra popup
 * - Cuando se selecciona evento → centra mapa y hace zoom 12
 * - Usa tiles de OpenStreetMap (gratuito, sin API key)
 */
@Composable
fun MapaEventosOSM(
    eventos: List<Evento>,
    eventoSeleccionado: Evento?,
    onMarkerClick: (Evento) -> Unit,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    val context = LocalContext.current
    
    // Configurar OSMDroid una sola vez (equivalente a mapInitialized en el JS)
    DisposableEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
        onDispose { }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        AndroidView(
            factory = { ctx ->
                MapView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    
                    // Configurar mapa (equivalente a L.map() en Leaflet)
                    setTileSource(TileSourceFactory.MAPNIK) // OpenStreetMap
                    setMultiTouchControls(true) // Habilitar zoom con gestos
                    
                    // Centro en Chile con zoom 5 (equivalente a setView([-35.6751, -71.543], 5))
                    controller.setZoom(5.0)
                    controller.setCenter(GeoPoint(-35.6751, -71.543))
                    
                    // Agregar marcador por cada evento (equivalente a forEach en el JS)
                    eventos.forEach { evento ->
                        val marker = Marker(this).apply {
                            // Posición del marcador (equivalente a L.marker([lat, lng]))
                            position = GeoPoint(evento.latitud, evento.longitud)
                            title = evento.titulo
                            
                            // Popup con detalles (equivalente a bindPopup() en Leaflet)
                            snippet = buildString {
                                append(evento.lugar)
                                append("\n")
                                append(evento.direccion)
                                append("\n")
                                append("Fecha: ${evento.fecha} — ${evento.hora}")
                                append("\n")
                                append("+${evento.puntos} pts LevelUp")
                            }
                            
                            // Centrar ancla del marcador
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            
                            // Click en marcador (equivalente a marker.on('click'))
                            setOnMarkerClickListener { clickedMarker, _ ->
                                onMarkerClick(evento)
                                // Mostrar popup automáticamente
                                clickedMarker.showInfoWindow()
                                true
                            }
                        }
                        
                        // Agregar marcador al mapa (equivalente a addTo(map))
                        overlays.add(marker)
                    }
                    
                    // Si hay eventos, centrar en el primero al cargar
                    eventos.firstOrNull()?.let { primerEvento ->
                        controller.setCenter(GeoPoint(primerEvento.latitud, primerEvento.longitud))
                        controller.setZoom(12.0)
                    }
                }
            },
            update = { mapView ->
                // Actualizar cuando cambia el evento seleccionado
                // Equivalente a map.setView() y resaltarItemLista() del JS
                eventoSeleccionado?.let { evento ->
                    mapView.controller.animateTo(
                        GeoPoint(evento.latitud, evento.longitud)
                    )
                    mapView.controller.setZoom(12.0)
                    
                    // Abrir popup del marcador seleccionado
                    mapView.overlays
                        .filterIsInstance<Marker>()
                        .find { marker -> 
                            marker.position.latitude == evento.latitud &&
                            marker.position.longitude == evento.longitud
                        }
                        ?.showInfoWindow()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
//ACA LA IMPORTACION FINAL  DEL MAPA 🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩🟩
// MANTENER PLACEHOLDER COMO BACKUP POR SI OSMDroid FALLA
@Composable
fun MapaEventosPlaceholder(
    eventoSeleccionado: Evento?,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    LevelUpCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Map,
                    contentDescription = "Mapa",
                    modifier = Modifier.size(64.dp),
                    tint = BrandSecondary // Cyan LevelUp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mapa de eventos",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextHigh // Texto principal
                )
                eventoSeleccionado?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it.ciudad,
                        style = MaterialTheme.typography.bodyMedium,
                        color = BrandSecondary // Cyan para destacar ciudad
                    )
                }
            }
        }
    }
}

@Composable
fun CanjeCodigoSection(
    codigoIngresado: String,
    mensaje: String,
    onCodigoChange: (String) -> Unit,
    onCanjear: () -> Unit,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    // Usamos Card de Material3 porque necesitamos color surfaceVariant
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Canjea tu código de evento",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextHigh // Texto principal blanco
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            //horizontal    
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TextField ocupa el espacio disponible
                OutlinedTextField(
                    value = codigoIngresado,
                    onValueChange = onCodigoChange,
                    label = { Text("Código de evento", fontSize = dimens.bodySize) },
                    placeholder = { Text("LVUP-SANTIAGO-100") },
                    modifier = Modifier.weight(1f), //  Toma espacio sobrante
                    singleLine = true
                )
                
                // Usamos Button de Material3 (no LevelUp) porque LevelUpButton tiene fillMaxWidth() hardcodeado
                Button(
                    onClick = onCanjear,
                    modifier = Modifier.wrapContentWidth() // Solo el ancho necesario
                ) {
                    Text("Canjear", fontSize = dimens.bodySize)
                }
            }
            
            if (mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensaje,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (mensaje.contains("canjeado")) 
                        BrandSuccess // Verde para exito 
                    else 
                        BrandError // Rojo para error
                )
            }
        }
    }
}

@Composable
fun RecompensasSection(
    recompensas: List<RecompensaCanje>,
    puntosUsuario: Int,
    onCanjear: (RecompensaCanje) -> Unit,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    var showDialog by remember { mutableStateOf(false) }
    var recompensaSeleccionada by remember { mutableStateOf<RecompensaCanje?>(null) }
    var mensajeDialog by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Canjea tus puntos LevelUp",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = BrandSecondary, // Cyan característico
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        
        Text(
            text = "Participa en eventos y gana puntos LevelUp. Canjéalos por descuentos o productos.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextMedium, // Texto secundario
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(400.dp)
        ) {
            items(recompensas) { recompensa ->
                RecompensaCard(
                    recompensa = recompensa,
                    puntosUsuario = puntosUsuario,
                    onClick = {
                        recompensaSeleccionada = recompensa
                        if (puntosUsuario >= recompensa.costo) {
                            onCanjear(recompensa)
                            mensajeDialog = "¡Recompensa canjeada con éxito! Se han descontado ${recompensa.costo} puntos."
                        } else {
                            mensajeDialog = "No tienes suficientes puntos. Necesitas ${recompensa.costo - puntosUsuario} puntos más."
                        }
                        showDialog = true
                    },
                    dimens = dimens
                )
            }
        }
    }

    if (showDialog) {
        LevelUpAlertDialog(
            onDismissRequest = { showDialog = false },
            title = if (puntosUsuario >= (recompensaSeleccionada?.costo ?: 0)) 
                "¡Éxito!" 
            else 
                "Puntos insuficientes",
            text = mensajeDialog,
            confirmText = "Aceptar",
            onConfirm = { showDialog = false },
            dimens = dimens
        )
    }
}

@Composable
fun RecompensaCard(
    recompensa: RecompensaCanje,
    puntosUsuario: Int,
    onClick: () -> Unit,
    dimens: com.example.levelupprueba.ui.theme.Dimens
) {
    val tienePuntosSuficientes = puntosUsuario >= recompensa.costo

    // Usamos Card de Material3 porque necesitamos color dinámico
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (tienePuntosSuficientes) 
                MaterialTheme.colorScheme.surface 
            else 
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = when (recompensa.tipo) {
                    com.example.levelupprueba.model.evento.TipoRecompensa.DESCUENTO -> Icons.Filled.Discount
                    com.example.levelupprueba.model.evento.TipoRecompensa.GIFT_CARD -> Icons.Filled.CardGiftcard
                    com.example.levelupprueba.model.evento.TipoRecompensa.PRODUCTO -> Icons.Filled.ShoppingCart
                },
                contentDescription = recompensa.titulo,
                modifier = Modifier.size(48.dp),
                tint = BrandSecondary // Cyan para iconos
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = recompensa.titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextHigh, // Texto principal
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Costo: ${recompensa.costo} pts",
                style = MaterialTheme.typography.bodyMedium,
                color = BrandWarning // Amarillo para destacar costo
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LevelUpButton(
                text = "Canjear",
                onClick = onClick,
                enabled = tienePuntosSuficientes && recompensa.disponible,
                modifier = Modifier.fillMaxWidth(),
                dimens = dimens
            )
        }
    }
}

