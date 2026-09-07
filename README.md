# Esneme Molası

**Çalışma gününe sığan üç dakikalık hareket molaları.** Kotlin ve Jetpack Compose ile hazırlanmış, Android 8.0 ve üzeri için çalışan uygulama prototipi.

[![Android APK](https://github.com/yalnizfahrettin06-tech/ofis/actions/workflows/android.yml/badge.svg)](https://github.com/yalnizfahrettin06-tech/ofis/actions/workflows/android.yml)

## APK nasıl alınır?

1. [Actions → Android APK](https://github.com/yalnizfahrettin06-tech/ofis/actions/workflows/android.yml) sayfasını açın.
2. Son başarılı çalıştırmayı seçin. İsterseniz **Run workflow** ile yeni derleme başlatın.
3. **Artifacts → esneme-molasi-apk** arşivini indirin; GitHub oturumu gerekebilir.
4. ZIP içindeki **esneme-molasi-preview.apk** dosyasını Android telefona aktarın ve açın.

Bilgisayarınızda Android Studio, Gradle, emülatör veya SDK çalıştırmanız gerekmez. Bütün derleme ve test işleri GitHub sunucusunda yapılır. İndirilen APK hata ayıklama anahtarıyla imzalanmış kurulum yapılabilir bir önizlemedir; Play Store üretim paketi değildir. Android, dosyayı açtığınız uygulama için kurulum izni isteyebilir.

Önizleme imza anahtarı Actions önbelleğinde korunur. Önbellek sıfırlanırsa Android eski APK üzerine güncellemeyi reddedebilir; bu durumda eski önizlemeyi kaldırmak yerel verileri siler. Üretimde ayrı, kalıcı ve gizli bir imzalama anahtarı kullanılmalıdır.

## Bu sürümde

- 12 hazır rutin; 16 ayrı hareket türü ve çevrimdışı vektör rehberi.
- 10 saniye hazırlık, altı adet 25 saniyelik hareket, beş adet 3 saniyelik geçiş, 5 saniye kapanış.
- Monotonik saatle çalışan 180 saniyelik seans motoru; duraklat, devam, atla ve bitir.
- Görünürlük kaybında duraklatma; süreç kaybında saklanmış durumdan güvenli geri dönüş.
- Sade Mola/Rutinler gezinmesi, açık/koyu/sistem teması ve azaltılmış hareket.
- Çalışma günleri, saatleri, 45/60/90 dakika aralık ve sessiz öğle arası.
- Yaklaşık Android hatırlatmaları; 15 dakika erteleme ve bugün sessize alma.
- Room ile yerel geçmiş ve checkpoint; DataStore ile tercihler.
- Hesap, reklam, telemetri, kamera, mikrofon, konum ve ağ izni bulunmaz.
- Türkçe güvenlik/gizlilik açıklamaları ve uygulama içinden veri silme.

## İçerik durumu

**Rutinler ve çizimler uzman onayı bekleyen prototip içerikleridir.** Uygulama tanı, tedavi, rehabilitasyon veya kişiye özel egzersiz programı sunmaz. Genel hareket molası deneyimini gösterir. Rahat olmayan hareketi atlayın; ağrı, baş dönmesi veya uyuşmada bırakın. Sağlık durumuna uygunluk değerlendirmesi için sağlık uzmanına danışın.

Bu sınırlama, uygulama içindeki ilk kullanım ekranında da görünür. “Uzman onaylı” veya “oturduğunu algılar” iddiası bulunmaz. Üretim yayını için hareket metni, yönü, temposu ve animasyonların fizyoterapist incelemesi zorunludur.

## Plan ve uygulama farkları

Tam kaynak ürün planı [plan.md](plan.md) dosyasındadır. Bu teslim planın **işleyen prototip** kapsamıdır. Ücretli Plus, seslendirme, favoriler, vardiya profilleri, Activity Recognition, bulut eşitleme ve mağaza yayını eklenmedi. Sabit rehber modu tek pozla çalışır; uzman onaylı çoklu poz üretimi sonraki içerik aşamasıdır.

Rutinler doğrulanabilir Kotlin katalog verisi olarak paketlenmiştir; dış JSON indirme ve içerik sunucusu yoktur. Room şeması v2'dir; ilk önizleme şemasından kayıtları koruyan v1→v2 migration ve emülatör testi bulunur. Seans kayıtları 90 gün tutulur, ekran son 100 kaydı gösterir. Üretim öncesinde tablo şema ihracı ve migration kapsamı genişletilmelidir.

Hatırlatmalar saniyesi saniyesine alarm değildir. Android uyku/pil kısıtları gecikme yaratabilir. Çalışma dışına taşan ve çok geciken öneriler yığılmaz. Force-stop sonrası uygulama yeniden açılana kadar bildirim beklenmemelidir. Telefonun sabitliği veya kullanıcının oturma süresi ölçülmez. Uygulama açıkken ayrıca sistem bildirimi gösterilmez.

## Geliştirme ve doğrulama

- JDK 17, Gradle 8.13, Android Gradle Plugin 8.11.1.
- Kotlin/Compose compiler 2.1.20, Compose BOM 2025.04.01.
- compileSdk/targetSdk 36, minSdk 26.
- GitHub Actions birim testleri, Android lint ve APK derlemesi çalıştırır.
- İkinci iş Android 35 emülatöründe onboarding → rutin → duraklat/devam → bitir → ayarlar akışını sınar ve ekran görüntüsü üretir.
- Sonuçlar Actions'ın **quality-reports** ve **emulator-test-evidence** artifacts bölümlerindedir. Başarısız bir çalıştırma testlerin geçtiği anlamına gelmez.

Bu depodaki workflow Gradle sürümünü resmî setup-gradle action ile sağlar; Gradle wrapper JAR'ı depoya eklenmemiştir. Geliştirici ortamında aynı Gradle sürümüyle `gradle testDebugUnitTest lintDebug assembleDebug` kullanılabilir. Kullanıcının bilgisayarında bu komutu çalıştırması gerekmez.

Fiziksel cihazda pil, çeşitli üreticilerde bildirim davranışı, TalkBack ve uzman içerik doğrulaması; emülatör testinden ayrı yayın kapılarıdır. Bunlar yapılmadan mağazaya hazır/klinik olarak doğrulanmış ürün iddiasında bulunulmaz.

## Teknik yapı

`SessionEngine` Android'den bağımsız süre ve geçiş mantığıdır. `ReminderPolicy` yerel takvim hesabıdır. `ReminderScheduler` sistem alarmı ve bildirim adaptörüdür. `AppViewModel` tek seansın UI durumunu yönetir. `LocalStore` Room/DataStore katmanıdır. `Catalog` içerik, `MotionGuide` vektör rehber, `AppUi` arayüzdür.

## Gizlilik

Uygulama sunucuya veri göndermez ve INTERNET izni istemez. Seanslar uygulamanın özel alanında saklanır; bulut yedeği ve cihaz aktarımı için dışlama kuralları bulunur. Üretici yedekleme davranışlarının tamamına mutlak garanti verilmez. Geçmişi silmek yalnız mola kayıtlarını; tüm verileri sıfırlamak ayar, checkpoint ve planlanan hatırlatmaları da temizler.

Hiçbir GitHub erişim anahtarı kaynaklara, APK'ya veya iş akışına eklenmez. Workflow yalnız kendi sınırlı GitHub kimliğini kullanır.

## Birincil kaynaklar

- [Android alarm zamanlama](https://developer.android.com/develop/background-work/services/alarms)
- [Android bildirim izni](https://developer.android.com/develop/ui/compose/notifications/notification-permission)
- [Android SystemClock](https://developer.android.com/reference/android/os/SystemClock)
- [Android AGP 8.11 uyumluluk](https://developer.android.com/build/releases/agp-8-11-0-release-notes)
- [NHS oturarak hareket örnekleri](https://www.nhs.uk/live-well/exercise/sitting-exercises/)
- [WHO fiziksel aktivite çerçevesi](https://www.who.int/news-room/fact-sheets/detail/physical-activity)

Kaynaklar uygulamadaki rutinlerin klinik onayı değildir. Başlangıç araştırması ve ayrıntılı kaynak sınırları plan.md içinde kayıtlıdır.
