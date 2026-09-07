@file:OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.esnemolasi.app

import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ceil

private val LightColors = lightColorScheme(
    primary = Color(0xFF2F6B4F), onPrimary = Color.White,
    primaryContainer = Color(0xFFE0EDE0), onPrimaryContainer = Color(0xFF183C2A),
    secondary = Color(0xFF536258), background = Color(0xFFF7F8F4),
    surface = Color(0xFFFFFEFA), onBackground = Color(0xFF1D2921),
    onSurface = Color(0xFF1D2921), onSurfaceVariant = Color(0xFF536258),
    surfaceVariant = Color(0xFFEBEFE7), outline = Color(0xFF758178),
    outlineVariant = Color(0xFFDCE3D9), error = Color(0xFFB3261E)
)
private val DarkColors = darkColorScheme(
    primary = Color(0xFFA6D8B7), onPrimary = Color(0xFF143321),
    primaryContainer = Color(0xFF294C36), onPrimaryContainer = Color(0xFFD6EDDC),
    secondary = Color(0xFFB9C8BB), background = Color(0xFF141916),
    surface = Color(0xFF1E2721), onBackground = Color(0xFFEDF3EC),
    onSurface = Color(0xFFEDF3EC), onSurfaceVariant = Color(0xFFB9C8BB),
    surfaceVariant = Color(0xFF2B352D), outline = Color(0xFF819085),
    outlineVariant = Color(0xFF3E4D41), error = Color(0xFFFFB4AB)
)

@Composable
fun EsnemeTheme(mode: String, content: @Composable () -> Unit) {
    val dark = mode == "dark" || (mode == "system" && isSystemInDarkTheme())
    MaterialTheme(colorScheme = if (dark) DarkColors else LightColors,
        typography = Typography(
            headlineLarge = Typography().headlineLarge.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Normal),
            headlineMedium = Typography().headlineMedium.copy(fontFamily = FontFamily.Serif),
            headlineSmall = Typography().headlineSmall.copy(fontFamily = FontFamily.Serif),
            bodyLarge = Typography().bodyLarge.copy(lineHeight = 25.sp)
        ), shapes = Shapes(small = RoundedCornerShape(12.dp), medium = RoundedCornerShape(20.dp), large = RoundedCornerShape(28.dp)),
        content = content)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUi(viewModel: AppViewModel, onRequestNotifications: () -> Unit, onOpenNotificationSettings: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var screen by rememberSaveable { mutableStateOf("home") }
    var detailId by rememberSaveable { mutableStateOf<String?>(null) }
    var confirmEnd by remember { mutableStateOf(false) }
    var resumeAfterDialog by remember { mutableStateOf(false) }
    val snackbar = remember { SnackbarHostState() }
    val requestEnd: () -> Unit = { resumeAfterDialog = state.session?.paused == false; viewModel.pause(); confirmEnd = true }
    val cancelEnd: () -> Unit = { confirmEnd = false; if (resumeAfterDialog) viewModel.resume() }
    LaunchedEffect(state.error) { state.error?.let { snackbar.showSnackbar(it); viewModel.dismissError() } }
    EsnemeTheme(state.settings.theme) {
        val inSession = state.session != null
        val root = screen == "home" || screen == "routines"
        BackHandler(inSession || state.result != null || !root || detailId != null) {
            when { inSession -> requestEnd(); state.result != null -> { viewModel.clearResult(); screen = "home"; detailId = null }
                detailId != null -> detailId = null; else -> screen = "home" }
        }
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            snackbarHost = { SnackbarHost(snackbar) },
            topBar = {
                if (state.ready && state.settings.safetyAccepted && !inSession && state.result == null) {
                    CenterAlignedTopAppBar(
                        title = { Text(when { detailId != null -> "Rutin ayrıntısı"; screen == "settings" -> "Ayarlar"
                            screen == "history" -> "Mola geçmişi"; screen == "help" -> "Yardım ve gizlilik"; else -> "esneme molası" },
                            style = MaterialTheme.typography.titleMedium) },
                        navigationIcon = { if (!root || detailId != null) IconButton(onClick = { if (detailId != null) detailId = null else screen = "home" }) {
                            Icon(Icons.Default.ArrowBack, "Geri") } else Icon(Icons.Default.Spa, null, Modifier.padding(start = 20.dp), tint = MaterialTheme.colorScheme.primary) },
                        actions = { if (root && detailId == null) {
                            IconButton(onClick = { screen = "history" }) { Icon(Icons.Default.History, "Mola geçmişi") }
                            IconButton(onClick = { screen = "settings" }, modifier = Modifier.testTag("open_settings")) { Icon(Icons.Default.Settings, "Ayarlar") }
                        } }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background))
                }
            },
            bottomBar = {
                if (state.ready && state.settings.safetyAccepted && root && detailId == null && !inSession && state.result == null) {
                    NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 0.dp) {
                        NavigationBarItem(screen == "home", { screen = "home" }, { Icon(Icons.Default.Spa, null) }, label = { Text("Mola") })
                        NavigationBarItem(screen == "routines", { screen = "routines" }, { Icon(Icons.Default.GridView, null) }, label = { Text("Rutinler") }, modifier = Modifier.testTag("nav_routines"))
                    }
                }
            }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.TopCenter) {
                when {
                    !state.ready -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    !state.settings.safetyAccepted -> Welcome(viewModel::acceptSafety)
                    state.session != null -> SessionScreen(state.session!!, state.settings,
                        { if (state.session?.paused == true) viewModel.resume() else viewModel.pause() }, viewModel::skip, requestEnd)
                    state.result != null -> Completion(state.result!!) { viewModel.clearResult(); screen = "home"; detailId = null }
                    detailId != null -> RoutineDetail(Catalog.routine(detailId!!) ?: Catalog.routines.first()) { viewModel.startRoutine(it) }
                    screen == "routines" -> RoutinesScreen { detailId = it.id }
                    screen == "settings" -> SettingsScreen(state, { settings ->
                        viewModel.saveSettings(settings)
                        if (settings.reminders && !state.notificationAllowed) onRequestNotifications()
                        screen = "home"
                    }, onRequestNotifications, onOpenNotificationSettings, { screen = "help" }, { viewModel.resetAll(); screen = "home"; detailId = null })
                    screen == "history" -> HistoryScreen(state.history, viewModel::clearHistory)
                    screen == "help" -> HelpScreen()
                    else -> HomeScreen(state, { viewModel.startRoutine(suggestedRoutine(state)) }, { screen = "routines" },
                        { screen = "settings" }, viewModel::muteToday)
                }
            }
        }
        if (confirmEnd && inSession) AlertDialog(onDismissRequest = cancelEnd,
            title = { Text("Molayı bitirelim mi?") }, text = { Text("Şu ana kadarki süre geçmişine kaydedilir. İstediğin zaman yeni bir mola verebilirsin.") },
            confirmButton = { TextButton(onClick = { confirmEnd = false; viewModel.endSession() }, modifier = Modifier.testTag("confirm_end")) { Text("Molayı bitir") } },
            dismissButton = { TextButton(onClick = cancelEnd) { Text(if (resumeAfterDialog) "Devam et" else "Molaya dön") } })
    }
}

@Composable
private fun Page(content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.widthIn(max = 620.dp).fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 24.dp).padding(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp), content = content)
}

@Composable
private fun Eyebrow(text: String) { Text(text, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.3.sp) }

@Composable
private fun Heading(text: String) { Text(text, Modifier.semantics { heading() }, style = MaterialTheme.typography.headlineLarge) }

@Composable
private fun Muted(text: String, modifier: Modifier = Modifier) { Text(text, modifier, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) }

@Composable
private fun Primary(text: String, enabled: Boolean = true, tag: String = "", onClick: () -> Unit) {
    Button(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp).then(if (tag.isEmpty()) Modifier else Modifier.testTag(tag)), contentPadding = PaddingValues(horizontal = 22.dp, vertical = 16.dp)) { Text(text, style = MaterialTheme.typography.titleSmall) }
}

@Composable
private fun Note(text: String) {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium) {
        Row(Modifier.fillMaxWidth().padding(18.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(Icons.Default.Info, null, Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
            Muted(text, Modifier.weight(1f))
        }
    }
}

@Composable
private fun Welcome(onAccept: () -> Unit) {
    var understood by rememberSaveable { mutableStateOf(false) }
    Page {
        Spacer(Modifier.height(24.dp))
        Icon(Icons.Default.Spa, null, Modifier.size(42.dp), tint = MaterialTheme.colorScheme.primary)
        Eyebrow("GÜNÜNE KÜÇÜK BİR ARA")
        Heading("Üç dakika.\nKendine bir mola.")
        Text("Masa başındaki gününe sakin bir hareket arası ekle. Hazır rutinler, sade bir rehber ve senin seçtiğin hatırlatmalar.", style = MaterialTheme.typography.bodyLarge)
        Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.large) {
            Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Kendi rahat sınırında", style = MaterialTheme.typography.titleLarge)
                Text("Hareketleri zorlamadan yap, nefesini tutma. Ağrı, baş dönmesi veya uyuşma olursa dur. Rahat gelmeyen hareketi atlayabilirsin.")
                Muted("Bir rahatsızlığın, yakın zamanda yaralanman ya da hareket kısıtlaman varsa başlamadan önce sağlık uzmanına danış.")
            }
        }
        Note("Bu bir prototiptir. Rutin ve çizimler uzman onayı bekleyen örnek içeriktir; tanı, tedavi veya kişiye özel egzersiz önerisi sunmaz.")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = understood, onCheckedChange = { understood = it }, modifier = Modifier.testTag("onboarding_safety").semantics { contentDescription = "Güvenli kullanım bilgisini okudum" })
            Text("Güvenli kullanım bilgisini okudum.", Modifier.weight(1f))
        }
        Primary("İlk molama hazırım", understood, tag = "onboarding_continue", onClick = onAccept)
        Muted("Hesap gerekmez. Rutinler çevrimdışı çalışır. Bildirimleri daha sonra seçebilirsin.")
    }
}

@Composable
private fun HomeScreen(state: AppUiState, onStart: () -> Unit, onRoutines: () -> Unit, onSettings: () -> Unit, onMute: () -> Unit) {
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date())
    val done = state.history.filter { it.localDate == today && it.status != "ended_early" }
    val routine = suggestedRoutine(state)
    Page {
        Eyebrow("BİRAZ YER AÇ")
        Heading("İş bekleyebilir.\nSen bir nefes al.")
        Muted("İyi bir mola için büyük bir plan gerekmez.")
        Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.large) {
            Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Eyebrow("BUGÜNÜN MOLASI")
                    Icon(Icons.Default.Spa, null, Modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
                }
                Text(routine.title, style = MaterialTheme.typography.headlineMedium)
                Text(routine.subtitle, color = MaterialTheme.colorScheme.onPrimaryContainer)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { SmallTag("3 dakika"); SmallTag(if (routine.standing) "Ayakta" else "Oturarak") }
                Primary("Molayı başlat", tag = "home_start", onClick = onStart)
            }
        }
        TextButton(onClick = onRoutines, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Başka bir rutin seç"); Spacer(Modifier.width(8.dp)); Icon(Icons.Default.ArrowForward, null, Modifier.size(18.dp)) }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Text("Çalışma ritmin", style = MaterialTheme.typography.titleMedium)
        Muted(when {
            state.mutedToday -> "Bugünkü hatırlatmalar sessizde. Sonraki çalışma gününde planın devam eder."
            !state.settings.reminders -> "Hatırlatmalar kapalı. Molana her zaman kendin başlayabilirsin."
            !state.notificationAllowed -> "Hatırlatmalar için bildirim izni gerekiyor."
            state.nextReminder > 0 -> "Sonraki öneri yaklaşık ${dateTime(state.nextReminder)}. Telefonun pil ayarlarına göre gecikebilir."
            else -> "Hatırlatmalar çalışma günlerine göre planlanır."
        })
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onSettings, modifier = Modifier.weight(1f).heightIn(min = 48.dp)) { Text("Planı düzenle") }
            if (state.settings.reminders && !state.mutedToday) TextButton(onClick = onMute, modifier = Modifier.weight(1f).heightIn(min = 48.dp)) { Text("Bugünü sessize al") }
        }
        if (done.isNotEmpty()) Muted("Bugün ${done.size} mola tamamladın. Her küçük ara kendi başına değerli.")
        else Muted("Bir seri tutturman gerekmiyor. Bugünkü küçük ara yeter.")
    }
}

@Composable
private fun SmallTag(text: String) {
    Surface(color = MaterialTheme.colorScheme.surface.copy(alpha = .7f), shape = RoundedCornerShape(30.dp)) {
        Text(text, Modifier.padding(horizontal = 12.dp, vertical = 7.dp), style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
private fun RoutinesScreen(onOpen: (Routine) -> Unit) {
    var filter by rememberSaveable { mutableStateOf("Tümü") }
    Page {
        Eyebrow("KÜÇÜK HAREKETLER")
        Heading("Sana uyan\nbir mola.")
        Muted("${Catalog.routines.size} kısa rutin. Her biri 3 dakika; seçim tamamen sana ait.")
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Tümü", "Oturarak", "Ayakta").forEach { item -> FilterChip(selected = filter == item, onClick = { filter = item }, label = { Text(item) }) }
        }
        Catalog.routines.filter { filter == "Tümü" || (filter == "Ayakta") == it.standing }.forEachIndexed { index, routine ->
            Card(onClick = { onOpen(routine) }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), modifier = Modifier.fillMaxWidth().testTag("routine_card_${routine.id}")) {
                Row(Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("%02d".format(index + 1), style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                        Text(routine.title, style = MaterialTheme.typography.titleMedium)
                        Muted(routine.subtitle)
                        Text("3 dk · ${if (routine.standing) "Ayakta" else "Oturarak"}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    }
                    Icon(Icons.Default.ChevronRight, null, Modifier.size(20.dp))
                }
            }
        }
        Muted("Rutinler genel hareket molası içindir. Rahat gelmeyen bir hareketi uygulama.")
    }
}

@Composable
private fun RoutineDetail(routine: Routine, onStart: (Routine) -> Unit) {
    Page {
        Eyebrow("3 DAKİKALIK HAREKET ARASI")
        Heading(routine.title)
        Text(routine.subtitle, style = MaterialTheme.typography.bodyLarge)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { SmallTag(if (routine.standing) "Ayakta" else "Oturarak"); SmallTag("${routine.moves.size} hareket") }
        Note(if (routine.standing) "Çevrende güvenli bir alan aç. Gerektiğinde sabit bir masa veya duvardan destek al."
            else "Sabit, tekerleksiz bir sandalye seç. Ayaklarını yere rahatça yerleştir.")
        Text("Bu molada", style = MaterialTheme.typography.titleLarge)
        routine.moves.forEachIndexed { index, id ->
            val movement = Catalog.movements[id]
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("${index + 1}", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(movement?.name ?: id, style = MaterialTheme.typography.titleMedium)
                    Muted(movement?.cue ?: "Rahat hareket aralığında kal.")
                }
            }
        }
        Muted("Süreye hazırlık ve geçişler dahildir. İstediğin zaman duraklatabilir, hareket atlayabilir veya bitirebilirsin.")
        Primary("Bu molayı başlat", tag = "detail_start", onClick = { onStart(routine) })
    }
}

@Composable
private fun SessionScreen(session: SessionUi, settings: Settings, onPause: () -> Unit, onSkip: () -> Unit, onEnd: () -> Unit) {
    val progress = (1f - session.totalRemainingMs / 180_000f).coerceIn(0f, 1f)
    Column(Modifier.widthIn(max = 620.dp).fillMaxSize()) {
        Box(Modifier.weight(1f).fillMaxWidth()) {
            Page {
                Text(session.routine.title, style = MaterialTheme.typography.titleMedium)
                LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(4.dp), trackColor = MaterialTheme.colorScheme.outlineVariant)
                Eyebrow(if (session.paused) "MOLA DURAKLATILDI" else when (session.phase) {
                    "preparation" -> "YERLEŞ VE HAZIRLAN"; "transition" -> "SIRADAKİ HAREKET"; "closing" -> "MOLAYI TAMAMLIYORUZ"
                    else -> "HAREKET ${(session.step + 1).coerceIn(1, session.routine.moves.size)} / ${session.routine.moves.size}"
                })
                Text(when (session.phase) { "preparation" -> "Kendine yer aç."; "closing" -> "Yavaşça dinlen."; else -> session.movement?.name ?: "Bir nefes al." },
                    style = MaterialTheme.typography.headlineMedium, modifier = Modifier.semantics { heading() })
                Surface(shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.surfaceVariant) {
                    MotionGuide(movement = session.movement, standing = session.routine.standing, elapsedMs = session.elapsedInStepMs,
                        playing = !session.paused && session.phase == "movement", reducedMotion = settings.reducedMotion,
                        modifier = Modifier.fillMaxWidth().height(220.dp))
                }
                Text(session.cue, style = MaterialTheme.typography.bodyLarge)
                if (session.phase == "movement") session.movement?.detail?.let { Muted(it) }
                FlowRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(36.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column { Text(timer(session.stepRemainingMs), style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary); Muted("bu adımda kalan") }
                    Column { Text(timer(session.totalRemainingMs), style = MaterialTheme.typography.titleLarge); Muted("molada kalan") }
                }
                Muted(if (session.paused) "Acele yok. Hazır olduğunda devam edebilirsin." else "Hareket rahat gelmiyorsa atla. Ağrı veya baş dönmesinde molayı bitir.")
            }
        }
        Surface(color = MaterialTheme.colorScheme.background, shadowElevation = 3.dp) {
            Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Primary(if (session.paused) "Devam et" else "Duraklat", tag = if (session.paused) "resume_session" else "pause_session", onClick = onPause)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = onSkip, enabled = session.phase == "movement", modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("skip_step")) { Text("Hareketi atla") }
                    TextButton(onClick = onEnd, modifier = Modifier.weight(1f).heightIn(min = 48.dp).testTag("end_session")) { Text("Bitir") }
                }
            }
        }
    }
}

@Composable
private fun Completion(record: SessionRecord, onDone: () -> Unit) {
    val early = record.status == "ended_early"
    Page {
        Spacer(Modifier.height(28.dp))
        Icon(if (early) Icons.Default.Spa else Icons.Default.CheckCircle, null, Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
        Eyebrow("KENDİNE AYIRDIĞIN ZAMAN")
        Heading(if (early) "Molanı burada\nbıraktın." else "Küçük bir ara.\nİyi ki verdin.")
        Text(if (early) "Bedenini dinlemek de molanın bir parçası. İstediğin zaman yeniden başlayabilirsin." else "Şimdi gününe kendi hızında dönebilirsin.", style = MaterialTheme.typography.bodyLarge)
        Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.large) {
            Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(record.title, style = MaterialTheme.typography.titleLarge)
                Text(timer(record.activeMs), style = MaterialTheme.typography.displayMedium)
                Text("aktif mola süresi", style = MaterialTheme.typography.labelLarge)
                Muted("Hareket süresi: ${timer(record.movementMs)}${if (record.skippedMoves > 0) " · ${record.skippedMoves} hareket atlandı" else ""}")
            }
        }
        Muted("Molan yalnızca bu cihazdaki geçmişine kaydedildi.")
        Primary("Günüme dön", tag = "result_done", onClick = onDone)
    }
}

@Composable
private fun SettingsScreen(state: AppUiState, onSave: (Settings) -> Unit, requestNotifications: () -> Unit,
    openNotificationSettings: () -> Unit, onHelp: () -> Unit, onReset: () -> Unit) {
    var draft by remember(state.settings) { mutableStateOf(state.settings) }
    var validation by remember { mutableStateOf<String?>(null) }
    var reset by remember { mutableStateOf(false) }
    Page {
        Eyebrow("SENİN GÜNÜN, SENİN RİTMİN")
        Heading("Molaya yer aç.")
        ToggleRow("Mola hatırlatmaları", "Seçtiğin çalışma aralığında yaklaşık öneriler.", draft.reminders) { draft = draft.copy(reminders = it) }
        if (draft.reminders && !state.notificationAllowed) {
            Note("Bildirim izni kapalı. İzin vermeden de tüm rutinleri kullanabilirsin.")
            OutlinedButton(onClick = requestNotifications, modifier = Modifier.fillMaxWidth()) { Text("Bildirim izni ver") }
            TextButton(onClick = openNotificationSettings, modifier = Modifier.fillMaxWidth()) { Text("Telefonun bildirim ayarlarını aç") }
        }
        Text("Çalışma günleri", style = MaterialTheme.typography.titleMedium)
        val weekdays = listOf("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar")
        weekdays.forEachIndexed { index, label ->
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = index + 1 in draft.days, onCheckedChange = { chosen -> draft = draft.copy(days = if (chosen) draft.days + (index + 1) else draft.days - (index + 1)) }, modifier = Modifier.semantics { contentDescription = label })
                Text(label, Modifier.weight(1f))
            }
        }
        TimeRow("Mesai başlangıcı", draft.startMinute) { draft = draft.copy(startMinute = it) }
        TimeRow("Mesai bitişi", draft.endMinute) { draft = draft.copy(endMinute = it) }
        Text("Hatırlatma aralığı", style = MaterialTheme.typography.titleMedium)
        Column {
            listOf(45, 60, 90).forEach { interval ->
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = draft.intervalMinutes == interval, onClick = { draft = draft.copy(intervalMinutes = interval) }, modifier = Modifier.semantics { contentDescription = "$interval dakikada bir hatırlat" })
                    Text("$interval dakika", Modifier.weight(1f))
                }
            }
        }
        ToggleRow("Sessiz ara", "Örneğin öğle yemeğinde hatırlatma gönderme.", draft.quietEnabled) { draft = draft.copy(quietEnabled = it) }
        if (draft.quietEnabled) {
            TimeRow("Sessiz ara başlangıcı", draft.quietStart) { draft = draft.copy(quietStart = it) }
            TimeRow("Sessiz ara bitişi", draft.quietEnd) { draft = draft.copy(quietEnd = it) }
        }
        Note("Telefonunun pil tasarrufu bildirimleri geciktirebilir. Bu uygulama oturduğunu veya hareketsizliğini ölçmez; çalışma takvimini kullanır.")
        HorizontalDivider()
        Text("Görünüm ve his", style = MaterialTheme.typography.titleLarge)
        listOf("system" to "Telefonun temasını kullan", "light" to "Açık tema", "dark" to "Koyu tema").forEach { (key, label) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(draft.theme == key, onClick = { draft = draft.copy(theme = key) }, modifier = Modifier.semantics { contentDescription = label }); Text(label, Modifier.weight(1f))
            }
        }
        ToggleRow("Hareketi azalt", "Animasyon yerine sabit rehber çizimi göster.", draft.reducedMotion) { draft = draft.copy(reducedMotion = it) }
        ToggleRow("Hafif titreşim", "Adım değişimlerinde kısa dokunsal işaret.", draft.haptic) { draft = draft.copy(haptic = it) }
        validation?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Primary("Ayarları kaydet", onClick = {
            validation = when {
                draft.days.isEmpty() -> "Çalışma planı için en az bir gün seç."
                draft.startMinute >= draft.endMinute -> "Mesai bitişi başlangıçtan sonra olmalı. Gece vardiyası bu sürümde desteklenmiyor."
                draft.quietEnabled && draft.quietStart >= draft.quietEnd -> "Sessiz ara bitişi başlangıçtan sonra olmalı."
                draft.quietEnabled && draft.quietStart <= draft.startMinute && draft.quietEnd >= draft.endMinute -> "Sessiz ara mesainin tamamını kaplamamalı. Saatleri düzenle veya sessiz arayı kapat."
                else -> null
            }
            if (validation == null) onSave(draft)
        })
        TextButton(onClick = onHelp, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Yardım ve gizlilik") }
        TextButton(onClick = { reset = true }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Tüm yerel verileri sıfırla", color = MaterialTheme.colorScheme.error) }
    }
    if (reset) AlertDialog(onDismissRequest = { reset = false }, title = { Text("Uygulamayı sıfırla?") },
        text = { Text("Mola geçmişin ve ayarların silinir, planlanan hatırlatmalar iptal edilir. Bu işlem geri alınamaz.") },
        confirmButton = { TextButton(onClick = { reset = false; onReset() }) { Text("Tümünü sıfırla") } },
        dismissButton = { TextButton(onClick = { reset = false }) { Text("Vazgeç") } })
}

@Composable
private fun ToggleRow(title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) { Text(title, style = MaterialTheme.typography.titleMedium); Muted(subtitle) }
        Switch(checked = checked, onCheckedChange = onChange, modifier = Modifier.semantics { contentDescription = title })
    }
}

@Composable
private fun TimeRow(label: String, value: Int, onChange: (Int) -> Unit) {
    val context = LocalContext.current
    OutlinedButton(onClick = { TimePickerDialog(context, { _, h, m -> onChange(h * 60 + m) }, value / 60, value % 60, true).show() },
        modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp), shape = MaterialTheme.shapes.small) {
        Text(label, Modifier.weight(1f)); Spacer(Modifier.width(10.dp)); Text("%02d:%02d".format(value / 60, value % 60))
    }
}

@Composable
private fun HistoryScreen(history: List<SessionRecord>, onClear: () -> Unit) {
    var confirm by remember { mutableStateOf(false) }
    Page {
        Eyebrow("KENDİNE AYIRDIĞIN ARALAR")
        Heading("Her mola\nkendi başına değerli.")
        if (history.isEmpty()) {
            Note("Henüz kayıtlı molan yok. İlk molanın ardından süresi ve rutini burada görünür.")
        } else {
            Muted("Bu cihazda ${history.size} kayıt.${if (history.size > 100) " En son 100 mola gösteriliyor." else ""} Seri, puan veya telafi edilecek bir gün yok.")
            history.sortedByDescending { it.endedAt }.take(100).forEach { item ->
                Surface(color = MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium) {
                    Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(item.title, style = MaterialTheme.typography.titleMedium)
                        Muted(dateTime(item.endedAt))
                        Text("${timer(item.activeMs)} · ${when(item.status) { "completed" -> "Tamamlandı"; "finished_with_skips" -> "Atlayarak tamamlandı"; else -> "Erken bitirildi" }}",
                            style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                        if (item.skippedMoves > 0) Muted("${item.skippedMoves} hareket atlandı")
                    }
                }
            }
            TextButton(onClick = { confirm = true }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Mola geçmişini sil", color = MaterialTheme.colorScheme.error) }
        }
    }
    if (confirm) AlertDialog(onDismissRequest = { confirm = false }, title = { Text("Geçmiş silinsin mi?") },
        text = { Text("Bu cihazdaki tüm mola kayıtları silinir. Ayarların korunur. Silinen kayıtlar geri alınamaz.") },
        confirmButton = { TextButton(onClick = { confirm = false; onClear() }) { Text("Geçmişi sil") } },
        dismissButton = { TextButton(onClick = { confirm = false }) { Text("Vazgeç") } })
}

@Composable
private fun HelpScreen() {
    Page {
        Eyebrow("AÇIK VE SADE")
        Heading("Bilmen gerekenler.")
        Text("Bu sürüm hakkında", style = MaterialTheme.typography.titleLarge)
        Text("Esneme Molası, üç dakikalık masa başı hareket araları için geliştirilmiş çalışan bir Android prototipidir. Rutinler ve çizimler uzman onayı bekleyen örneklerdir.")
        Text("Güvenli bir mola", style = MaterialTheme.typography.titleLarge)
        Text("Hareketleri yavaş ve rahat sınırında yap. Ağrı, baş dönmesi ya da uyuşma olursa dur. Uygulama tanı koymaz, ağrı tedavisi sunmaz ve kişiye özel egzersiz programı değildir. Sağlık durumuna uygunluğu için bir uzmana danış.")
        Text("Verilerin cihazında", style = MaterialTheme.typography.titleLarge)
        Text("Hesap, reklam, analiz takibi veya uzak sunucu bağlantısı yoktur. Çalışma takvimin, tercihler ve mola geçmişin yalnızca uygulamanın bu cihazdaki özel alanında saklanır. Kamera, mikrofon, konum veya hareket sensörleri kullanılmaz.")
        Text("Kontrol sende", style = MaterialTheme.typography.titleLarge)
        Text("Geçmiş ekranından kayıtları silebilir, Ayarlar bölümünden tüm yerel verileri sıfırlayabilirsin. Uygulamayı kaldırmak da yerel verileri siler. Bu prototip bulut yedeklemesi kullanmaz.")
        Text("Hatırlatma neden gecikti?", style = MaterialTheme.typography.titleLarge)
        Text("Android, pil tasarrufu ve uyku sırasında bildirimleri geciktirebilir. Mola önerileri yaklaşık zamanlıdır. Hatırlatmaların açık olduğunu, çalışma gün ve saatlerini ve telefonun bildirim iznini kontrol et. Uygulama oturmanı ölçmez.")
        Text("Mola sırasında uygulamadan çıkarsam?", style = MaterialTheme.typography.titleLarge)
        Text("Uygulama arka plana geçince seans duraklar. Geri döndüğünde devam edebilir veya bitirebilirsin. Telefonunu güvenli bir yere bırak ve ekrandaki hareketleri zorlamadan izle.")
        Note("Rutinler ve çizimler kurulum paketindedir. İnternet bağlantısı olmadan da mola verebilirsin.")
    }
}

private fun timer(ms: Long): String {
    val seconds = ceil(ms.coerceAtLeast(0) / 1000.0).toLong()
    return "%d:%02d".format(seconds / 60, seconds % 60)
}

private fun dateTime(ms: Long): String = SimpleDateFormat("d MMM, HH:mm", Locale.forLanguageTag("tr-TR")).format(Date(ms))

private fun suggestedRoutine(state: AppUiState): Routine {
    val completed = state.history.count { it.status == "completed" || it.status == "finished_with_skips" }
    return Catalog.routines[completed % Catalog.routines.size]
}
