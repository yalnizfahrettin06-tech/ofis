# Esneme Molası — Android ürün, deneyim ve geliştirme planı

**Belge sürümü:** 1.0  
**Hazırlanma ve internet araştırması tarihi:** 8 Eylül 2026  
**Platform:** Android telefon; tablet ve katlanabilir ekran uyumluluğu  
**Çalışma adı:** Esneme Molası  
**Belge türü:** Ürün gereksinimleri + kullanıcı deneyimi + görsel tasarım + teknik mimari + içerik üretimi + test ve yayın planı  
**Durum:** Uygulanabilir tasarım önerisi. Kod, klinik onay, kullanıcı testi veya yayımlanmış uygulama değildir.

> **Ürün cümlesi:** Çalışma gününde, uygun bir anda, üç dakikalık yönlendirmeli bir hareket molası vermeyi kolaylaştıran; hesap gerektirmeyen, çevrimdışı çalışan, sakin bir Android uygulaması.

## İçindekiler

1. [Belgenin kapsamı ve kaynak görsel](#bolum-01)
2. [Fikrin analizi ve temel kararlar](#bolum-02)
3. [Araştırma bulguları ve rekabet](#bolum-03)
4. [Hedef kullanıcılar ve ihtiyaçlar](#bolum-04)
5. [Ürün ilkeleri ve başarı tanımı](#bolum-05)
6. [MVP, sonraki sürümler ve kapsam sınırları](#bolum-06)
7. [Bilgi mimarisi ve gezinme](#bolum-07)
8. [Uçtan uca kullanıcı akışları](#bolum-08)
9. [Ekranların ayrıntılı tanımı](#bolum-09)
10. [Sade görsel tasarım sistemi](#bolum-10)
11. [Uygulama içi hareketler ve mikro etkileşimler](#bolum-11)
12. [Hareket rehberinin görsel üretimi](#bolum-12)
13. [Erişilebilirlik ve kapsayıcılık](#bolum-13)
14. [İçerik güvenliği ve uzman denetimi](#bolum-14)
15. [Hareket havuzu ve 12 rutin](#bolum-15)
16. [Seans motoru ve süre sözleşmesi](#bolum-16)
17. [Hatırlatma ve çalışma takvimi](#bolum-17)
18. [Hareketsizlik algılama kararı](#bolum-18)
19. [Bildirim tasarımı ve izin akışı](#bolum-19)
20. [Android mimarisi ve teknoloji seçimi](#bolum-20)
21. [Veri modeli ve saklama](#bolum-21)
22. [Çevrimdışı çalışma ve uygulama yaşam döngüsü](#bolum-22)
23. [Gizlilik, güvenlik ve veri kontrolü](#bolum-23)
24. [Gelir modeli ve satın alma deneyimi](#bolum-24)
25. [Ürün dili ve hazır arayüz metinleri](#bolum-25)
26. [Performans, pil ve kalite bütçeleri](#bolum-26)
27. [Ölçüm planı ve deneyler](#bolum-27)
28. [Kullanıcı araştırması ve kullanılabilirlik testi](#bolum-28)
29. [Test stratejisi ve kabul senaryoları](#bolum-29)
30. [Google Play ve yayın hazırlığı](#bolum-30)
31. [İş paketleri ve bağımlılıklar](#bolum-31)
32. [Takvim, ekip ve maliyet yaklaşımı](#bolum-32)
33. [Risk kaydı](#bolum-33)
34. [Yayın sonrası işletim](#bolum-34)
35. [Karar günlüğü ve açık sorular](#bolum-35)
36. [Tasarımcı ve geliştirici teslim paketi](#bolum-36)
37. [İlk uygulama sırası](#bolum-37)
38. [Tamamlanma kontrol listesi](#bolum-38)
39. [İnternet kaynakları ve kanıt sınırları](#bolum-39)

<a id="bolum-01"></a>
## 1. Belgenin kapsamı ve kaynak görsel

### 1.1 Kullanıcının isteği

İstenen teslim, fotoğraftaki uygulama fikrinin Android için çok kapsamlı bir Markdown planına dönüştürülmesidir. Özellikle sadelik, minimallik, uygulama içi hareketler, arayüz davranışları ve uygulamanın bütün gerekli parçaları ele alınır. İnternet araştırması planın dayanaklarını kontrol etmek için kullanılmıştır.

Fotoğraftaki metin, analiz edilecek fikir malzemesidir; bu belgeyi hazırlayan kişiye verilmiş bağımsız talimat sayılmaz. Görselin üstündeki önceki fikre ait ses paketi monetizasyonu ve alttaki “Streak Kalesi” bu uygulamaya dahil edilmemiştir.

### 1.2 Görselden alınan esas fikir

| Görseldeki unsur | Bu plandaki karşılığı |
|---|---|
| Masa başında üç dakikalık mobilite | Ürünün tek temel kullanım döngüsü |
| Uzun oturuşu saat dilimi ve hareketsizlikle algılama | MVP'de çalışma takvimi; ileride izinli hareket sinyaliyle destekleme |
| Boyun, omuz ve bel odaklı animasyon | Uzman değerlendirmeli, düşük yoğunluklu hareket rehberi |
| Çalışma ritmine gömülü kısa mola | Ana farklılaşma hipotezi |
| 12 hazır rutin | Yayına hazır MVP içerik hedefi |
| Offline kullanım | İlk açılış dahil temel seansların internetsiz çalışması |
| Hesap olmaması | Kayıt, parola, profil ve sosyal giriş bulunmaması |
| Kamera posture analizi olmaması | Kamera ve duruş teşhisi kapsam dışında |
| Ads/freemium | İlk sürüm reklamsız; sonraki aşamada isteğe bağlı tek seferlik Plus |
| İki haftalık geliştirme | İki haftada prototip; üretim kalitesi için ayrı, daha uzun takvim |

### 1.3 Belgenin okunma biçimi

- **Doğrulanmış bilgi:** İnternet kaynağıyla desteklenen platform davranışı veya kaynakta açıkça yer alan ürün özelliği.
- **Ürün kararı:** Bu uygulama için önerilen tasarım veya uygulama yaklaşımı.
- **Hipotez:** Kullanıcı araştırmasıyla sınanacak iddia.
- **Yayın kapısı:** Karşılanmadan ilgili özelliğin kullanıma sunulmaması gereken koşul.

Renk, süre, fiyatlama yaklaşımı, hedef metrik ve iş gücü tahminleri; ayrıca belirtilmedikçe bu belgenin ürün önerileridir. Kaynakların bu uygulamayı veya aşağıdaki rutinleri doğruladığı anlamına gelmez.

<a id="bolum-02"></a>
## 2. Fikrin analizi ve temel kararlar

### 2.1 Asıl problem

Masa başında çalışan kişinin çoğu zaman ihtiyacı daha büyük bir egzersiz kütüphanesi değildir. İşe ara verme anını hatırlamak, o anda ne yapacağını seçmek ve hareketi kısa sürede tamamlamak zor olabilir. Uygulama bu üç kararı küçültür:

1. **Ne zaman?** Kullanıcının belirlediği çalışma aralığında nazik öneri.
2. **Ne yapacağım?** Varsayılan olarak hazır ve uygun bir rutin.
3. **Ne kadar sürecek?** Duraklatma ve atlama yoksa tam 180 saniyelik rehber.

### 2.2 Ürünün güçlü tarafları

- Üç dakikalık öneri, uzun antrenmana kıyasla küçük bir zaman talebidir; benimsenme avantajı bir hipotezdir.
- Çevrimdışı içerik, asansörde, interneti zayıf ofiste veya seyahatte aynı deneyimi korur.
- Hesapsız başlangıç, ilk mola öncesindeki yükü azaltır.
- Türkçe, sakin ve iş ortamına uygun içerik belirgin bir yerelleştirme fırsatı sunar.
- Rutinlerin kısa olması, ekran süresini artırmayı ürün hedefi olmaktan çıkarır.

### 2.3 Fikrin zayıf veya yanıltıcı varsayımları

**“Uzun oturuşu algılar” ifadesi fazla güçlüdür.** Telefon masada kalırken kişi yürüyebilir. Telefon hareket ederken kişi oturabilir. MVP, oturma ölçtüğünü söylemez.

**“Bu tür uygulama yok” varsayımı doğrulanmamıştır.** Moova kısa hareket molaları ve planlı hatırlatmalar sunar. Farklılaşma, kategorinin yokluğu üzerine kurulamaz. [Moova](https://getmoova.app/)

**“İki haftada biter” ancak dar bir prototip için makuldür.** On iki rutinin yazımı, animasyonları, uzman kontrolü, Android arka plan koşulları ve mağaza hazırlığı ayrı emek ister.

**“Üç dakika sağlık sorununu çözer” iddiası yapılmaz.** Ürün genel hareket alışkanlığını destekler. Üç dakika, klinik olarak belirlenmiş kişisel doz değil, deneyim tasarımı tercihidir.

### 2.4 Önerilen kesin ürün kararları

| Kod | Karar | Neden |
|---|---|---|
| D01 | Android'e özgü Kotlin + Jetpack Compose | Yerel erişilebilirlik, sistem gezinmesi ve bildirimlerle uyum |
| D02 | Hesap ve sunucu olmadan temel kullanım | Basitlik ve veri minimizasyonu |
| D03 | Ana sayfada tek baskın eylem | “3 dakikalık mola”yı hemen başlatmak |
| D04 | İlk sürümde sensörle oturma tahmini yok | Yanlış iddia ve gereksiz izinleri önlemek |
| D05 | Hatırlatıcı ekranı kendi kendine açmaz | Kullanıcının işini ve Android sınırlarını gözetmek |
| D06 | İlk sürümde reklam yok | Mola öncesi ve sonrasında dikkat bölünmesini önlemek |
| D07 | Animasyon, ses ve metin eşdeğer rehberler | Farklı ortamlar ve erişim ihtiyaçları |
| D08 | Seans arka plana geçince durur | Kullanıcı görmediği hareketlerin yapılmış sayılmaması |
| D09 | Zamanlama yaklaşık; seans süresi hassas | İki farklı zamanlama problemini doğru çözmek |
| D10 | İçerik uzman değerlendirmesi olmadan yayımlanmaz | Özellikle boyun ve gövde hareketlerinde güvenilir içerik |

<a id="bolum-03"></a>
## 3. Araştırma bulguları ve rekabet

### 3.1 Doğrulanan bulguların ürün etkisi

| Bulgu | Sonuç | Kaynak |
|---|---|---|
| WHO, hareketsiz geçirilen zamanı sınırlamayı ve daha fazla fiziksel aktiviteyi destekliyor | Genel hareket molası yaklaşımı makul; uygulamanın kendi etkinliği kanıtlanmış sayılmaz | [WHO fiziksel aktivite](https://www.who.int/news-room/fact-sheets/detail/physical-activity) |
| NHS, bazı hafif oturarak hareketler için örnek yönlendirmeler yayımlıyor | İçerik araştırmasına başlangıç; nihai koreografi ayrı uzman incelemesi ister | [NHS oturarak egzersizler](https://www.nhs.uk/live-well/exercise/sitting-exercises/) |
| Android STILL, cihazın hareket etmediğini belirtir | Kişinin oturduğuna dair kesin mesaj yazılmaz | [DetectedActivity](https://developers.google.com/android/reference/com/google/android/gms/location/DetectedActivity) |
| Android arka plan alarmı ve işleri pil kısıtlarına tabidir | “Tam 60 dakikada bir” garantisi verilmez | [AlarmManager](https://developer.android.com/develop/background-work/services/alarms) |
| Android 13+ bildirim izni ister | İzin, hatırlatıcı kurulurken anlamlı bağlamda istenir | [Bildirim izni](https://developer.android.com/develop/ui/compose/notifications/notification-permission) |
| Sağlık içeriği sunan uygulamalar Play sağlık politikası kapsamına girebilir | Beyan, gizlilik metni ve doğru fayda anlatımı yayın işidir | [Play sağlık politikası](https://support.google.com/googleplay/android-developer/answer/16679511?hl=en) |

### 3.2 Küçük rekabet taraması

Bu bölüm pazarın tamamını veya rakiplerin bütün platformlarını kapsayan araştırma değildir. Fiyat, indirme sayısı ve elde tutma oranı karşılaştırması yapılmamıştır.

| Alternatif | Kaynakta gözlenen özellik | Kullanıcı neden seçebilir? | Bizim sınanacak farkımız |
|---|---|---|---|
| Moova; eski StretchMinder adresi buraya yönleniyor | Üç dakikalık molalar, rehberli hareketler, planlı hatırlatmalar | Daha geniş içerik ve hazır programlar | Türkçe, küçük kapsam, çevrimdışı ilk kullanım, çok düşük karar yükü |
| Jan Hovancik'in Stretchly uygulaması | Bilgisayarda çalışırken mola hatırlatma | Çalışma zaten bilgisayarda gerçekleşir | Telefon üzerindeki açıklayıcı hareket rehberi |
| Kullanıcının mevcut alarmı | Burada belirli bir alarm ürünü incelenmedi; kullanıcı alternatifi olarak ele alındı | Yeni uygulama öğrenmeye gerek kalmaması | Hatırlatıcıdan uygulamaya kadar kesintisiz rehber |
| Kısa egzersiz videosu | Belirli bir video hizmeti incelenmedi | Ücretsiz içerik bulma alışkanlığı | Arama, reklam ve süre seçimi yükünün azalması |

Rakip kaynakları: [Moova](https://getmoova.app/), [Stretchly resmî sitesi](https://hovancik.net/stretchly/). Aynı isimli farklı mağaza uygulamaları bu masaüstü Stretchly ürünüyle özdeş kabul edilmemiştir.

### 3.3 Konumlandırma

**Önerilen ifade:** “Çalışma gününe sığan üç dakikalık hareket molaları.”

Destekleyici vaatler: “Hesap açmadan başla”, “İnternet olmadan devam et”, “Hatırlatmaları kendi saatlerine göre ayarla.”

Kaçınılacak ifadeler: “Boyun ağrısını tedavi eder”, “Duruşunu düzeltir”, “Oturduğunu kesin algılar”, “Rakipsiz ilk uygulama”, “Her gün yapmazsan kazanımların silinir.”

<a id="bolum-04"></a>
## 4. Hedef kullanıcılar ve ihtiyaçlar

### 4.1 Birincil segment

Yetişkin, gününün önemli kısmını masa başında geçiren, düşük yoğunluklu hareket molası vermek isteyen, uygulama içinde program kurmakla uğraşmak istemeyen kişi. İlk dil Türkçe; yaş, meslek veya cinsiyet için gereksiz profil bilgisi toplanmaz.

### 4.2 Tasarım personelarının kapsamı

Aşağıdakiler görüşme sonucu oluşturulmuş gerçek personelar değil, tasarım senaryolarıdır.

| Persona | Bağlam | Zorluk | Tasarım karşılığı |
|---|---|---|---|
| Yoğun ofis çalışanı | Açık ofis, sık toplantı | Ses açamaz, mola kaçırır | Sessiz rehber, “1 saat sessize al” |
| Evden çalışan | Uzun odak blokları | Zamanı fark etmez | Çalışma penceresi, erteleme, sade bildirim |
| Serbest çalışan | Değişken çalışma günleri | Sabit mesai uymaz | Hatırlatmaları geçici durdurma; V1.1'de “Bugün çalışıyorum” |
| Büyük yazı kullanan kişi | Telefon biraz uzakta | Küçük sayaç ve kontroller zor | Büyük süre, erişilebilir düğmeler, metin ölçekleme |
| Animasyona hassas kişi | Hareketli arayüz rahatsız eder | Sürekli dönen çizimler | Azaltılmış hareket ve sabit adım kartları |

### 4.3 Temel kullanıcı işleri

- Çalışmamı fazla bölmeden kısa bir hareket molası vermek istiyorum.
- Telefonu masaya bıraktığımda ne yapacağımı anlayabilmek istiyorum.
- O anda uygun değilsem suçluluk hissetmeden ertelemek istiyorum.
- Boyun hareketi yapmak istemediğimde onu kolayca atlayabilmek istiyorum.
- İnternet veya üyelik yüzünden molamın yarıda kalmamasını istiyorum.
- Bildirimleri kapattığımda uygulamanın hâlâ işe yaramasını istiyorum.

### 4.4 İlk sürümün çözmediği ihtiyaçlar

Rehabilitasyon, teşhis, ameliyat sonrası program, kişiye özel ağrı değerlendirmesi, çocuklara özel egzersiz, gebelik programı, spor performansı takibi ve klinik izlem. İleri ihtiyaçları olan kişiler için uygulama uygun program belirlediğini iddia etmez.

<a id="bolum-05"></a>
## 5. Ürün ilkeleri ve başarı tanımı

1. **Mola, ana üründür.** Ana ekran içerik pazarı veya istatistik panosuna dönüşmez.
2. **Kontrol kullanıcıdadır.** Hatırlatma önerir; seans kullanıcı başlatmadan hareket etmez.
3. **Sadelik bilgi gizlemek değildir.** Aktif kontrolün adı görünür; temel işler yalnızca jestlere bağlanmaz.
4. **Kısa ama aceleci olmayan deneyim.** Hareketi yetiştirmek için hızlanma baskısı yaratılmaz.
5. **Sıfır ceza.** Seri kaybetme, kırmızı kaçırma işaretleri ve suçlayıcı bildirim yoktur.
6. **Gerektiğinde kolay vazgeçme.** “Bitir” ve “Atla” saklanmaz.
7. **İzin vermeden de değer.** Manuel seans bütün izinler kapalıyken çalışır.
8. **Az veri, açık davranış.** Ölçülmeyen oturma süresi ölçülmüş gibi gösterilmez.

**Ana başarı tanımı:** Kullanıcının seçtiği çalışma günlerinde uygulama aracılığıyla rahatça tamamlayabildiği hareket molaları. Uygulamada geçirilen dakika tek başına başarı değildir.

**Dengeleyici göstergeler:** Bildirimleri kapatma, hızlı çıkış, rahatsızlık nedeniyle bitirme ve erteleme sıklığı. Tamamlama artarken rahatsızlık veya bildirim bıkkınlığı artıyorsa deneyim başarılı sayılmaz.

<a id="bolum-06"></a>
## 6. MVP, sonraki sürümler ve kapsam sınırları

### 6.1 İki haftalık prototip

Ana ekran, bir temsilî rutin, seans oynatıcı, üç temel kontrol, çalışma saati formu, gerçek cihazda yaklaşık bildirim denemesi, çevrimdışı statik içerik. Bu sürüm geliştirilebilirliği ve deneyimi gösterir; 12 onaylı rutinli mağaza ürünü değildir.

### 6.2 Yayına hazır MVP — zorunlu kapsam

| Gereksinim | Öncelik | Tamamlanma koşulu |
|---|---|---|
| F01 Hesapsız ilk açılış | P0 | Kullanıcı kayıt olmadan ilk rutine girebilir |
| F02 Birincil üç dakikalık mola | P0 | Kurulumdan sonra ana ekrandan tek dokunuş |
| F03 12 hazır rutin | P0 | Her biri içerik ve animasyon değerlendirmesini geçmiş |
| F04 Metin ve animasyon rehberi | P0 | İnternetsiz ve büyük yazıyla çalışır |
| F05 Duraklat, devam et, atla, bitir | P0 | Sayaç, ses ve görüntü eşzamanlı kalır |
| F06 Çalışma günü, saati ve aralığı | P0 | Geçersiz saatler kaydedilemez |
| F07 Yaklaşık hatırlatmalar | P0 | İzin, yeniden başlatma, saat değişimi testleri geçer |
| F08 Ertele ve bugün sessize al | P0 | Eski plan etkisizleşir, yinelenen bildirim oluşmaz |
| F09 Erişilebilirlik | P0 | TalkBack ve büyük yazı temel akışları tamamlar |
| F10 Yerel geçmiş | P0 | Tam ve kısmi seans ayrılır |
| F11 Gizlilik ve yerel veri silme | P0 | Biriken kişisel kullanım kayıtları silinir |
| F12 Açık/koyu/sistem teması | P0 | İki temada anlam ve okunabilirlik korunur |
| F13 Sesli kısa ipuçları | P1 | Kalite yetişmezse MVP sonrası; metin rehberi eksiksiz kalır |
| F14 Favori rutinler | P1 | En çok birkaç tercih; ana ekranı kalabalıklaştırmaz |

### 6.3 V1.1

Plus satın alma, birden çok çalışma profili, kısa yol/widget, favoriler, kullanıcı tarafından kapatılabilen hareket grupları, isteğe bağlı ses paketi. P1 ses rehberi yetişmemişse burada tamamlanır. Bu paketler ilk sürümün 12 temel rutinini geriye dönük kilitlemez.

### 6.4 V1.2 araştırma alanları

İzinli Activity Recognition sinyali, isteğe bağlı öneri saatlerini uyarlama, ikinci dil, kullanıcı yönetimli içerik güncellemeleri. Sensör özelliği; hatalı sınıflandırma, pil ve kullanıcı anlayışı testinden geçmeden yayın takvimine konmaz.

### 6.5 Bilinçli olarak dışarıda

Kamera, mikrofon kaydı, GPS, duruş skoru, tıbbi teşhis, yapay zekâ koçu, sosyal akış, liderlik tablosu, kalori, kilo, zorunlu abonelik, giyilebilir entegrasyonu, bilgisayara uzaktan müdahale, takvim okuma ve sürekli ön plan servisi. Bunlar temel işe kanıtlanmış katkı göstermeden eklenmez.

<a id="bolum-07"></a>
## 7. Bilgi mimarisi ve gezinme

### 7.1 Birincil yapı

İki alt gezinme öğesi: **Mola** ve **Rutinler**. Ayarlar, Mola ekranının sağ üstündeki erişilebilir simgeden açılır. Geçmiş, ana ekrandaki “Bu hafta” özetinden açılan ikincil sayfadır. Ayrı profil sekmesi yoktur.

```text
İlk açılış
  ├─ Kısa tanıtım → Güvenli kullanım → İlk mola
  └─ Hatırlatıcı kur → Çalışma planı → Bildirim izni

Mola
  ├─ 3 dakikalık mola → Hazırlık → Seans → Tamamlandı
  ├─ Sonraki hatırlatma → Plan ayarları
  ├─ Bu hafta → Yerel geçmiş
  └─ Ayarlar
       ├─ Çalışma planı
       ├─ Ses, titreşim, görünüm
       ├─ Erişilebilir rehber
       ├─ Güvenli kullanım ve kaynaklar
       ├─ Veriler ve gizlilik
       └─ Yardım / Uygulama hakkında

Rutinler
  └─ Rutin ayrıntısı → Başlat → Hazırlık → Seans
```

### 7.2 Geri davranışı

- Alt sekmeler arasında geçiş, önceki sekmenin kaydırma konumunu korur.
- Ayrıntı sayfasında geri, rutin listesine ve aynı konuma döner.
- Alt panel açıkken geri yalnızca paneli kapatır.
- Aktif seansta geri, seansı duraklatıp çıkış seçimini gösterir.
- Tamamlanma ekranından geri ana Mola ekranına döner; bitmiş seansı yeniden açmaz.
- Bildirimden açılmış uygulamada geri, beklenmedik boş sayfa oluşturmadan Mola ekranına götürür.
- Öngörülü geri hareketi desteklenir; iptal edilen geri jesti seansı bitirmez.

### 7.3 Derin bağlantı sözleşmesi

Bildirim doğrudan seans girişini açan Activity PendingIntent kullanır. Rutin kimliği geçersizse güvenli varsayılan rutin seçilir ve hazırlık ekranı gösterilir. Eski veya yinelenmiş bildirim dokunuşu ikinci aktif seans yaratamaz. Harici bağlantı seansı kendiliğinden başlatmaz; kullanıcı eylemi gerekir.

<a id="bolum-08"></a>
## 8. Uçtan uca kullanıcı akışları

### 8.1 İlk kullanım: en kısa yol

1. Açılışta sistem splash ekranı; yapay bekleme yok.
2. “Çalışma gününe küçük bir mola.” Tek cümle ve sakin bir çizim.
3. “İlk molamı dene” birincil, “Hatırlatmaları ayarla” ikincil.
4. İlk denemede kısa güvenli kullanım özeti: rahat hareket sınırı, zorlamama, rahatsızlıkta durma.
5. “Hazırım” seçilince 180 saniyelik rehber başlar; ilk 10 saniye yerleşmedir.
6. Bitişte “Molan tamamlandı.” Ardından isteğe bağlı hatırlatma kurulumu.

İlk deneyimin önüne doğum tarihi, hedef kilo, ağrı anketi, üyelik, reklam veya ödeme sayfası konmaz.

### 8.2 Günlük manuel mola

Mola ekranı → “3 dakikalık mola” → 10 saniyelik hazırlık → hareketler → kapanış → ana ekran. Güvenli kullanım özeti daha önce görülmüşse tekrar modal olarak açılmaz; kısa hatırlatma seans ekranında erişilebilir kalır.

### 8.3 Bildirimden mola

Bildirim: “Kısa bir mola için uygun musun?” → “Başla” → uygulama açılır → mevcut seans yoksa hazırlık başlar. Ekran kilitliyse Android'in kilit açma davranışı izlenir. Bildirim kendi kendine ekranı kaplayan egzersiz açmaz.

### 8.4 Toplantıdayken

Bildirim → “15 dk ertele” → bildirim kapanır → yaklaşık yeni zaman planlanır. İkinci kez ertelemek mümkündür; azarlayan metin yoktur. “Bugün sessize al”, bir sonraki seçili çalışma gününe kadar otomatik önerileri durdurur. Manuel mola açıktır.

### 8.5 Bir hareket uygun gelmediğinde

Seans → “Atla” → kalan hareket süresi çıkarılır → bir sonraki hazırlık/geçiş adımı. Kullanıcı neden belirtmek zorunda değildir. “Bitir” seçilirse seans durur; isteğe bağlı tek soruluk çıkış nedeni, hareket bittikten sonra gösterilebilir. Acil durdurma başka bir soruya bağlı değildir.

### 8.6 Bildirim izni reddedilirse

Plan kaydedilir ama ana ekran durumu “Hatırlatmalar için bildirim izni kapalı” olur. “Ayarları aç” ikincil eylemdir. Her açılışta tekrar izin penceresi gösterilmez. “3 dakikalık mola” normal çalışır.

### 8.7 Seans sırasında telefon görüşmesi veya başka uygulama

Uygulama görünürlüğü kaybolunca sayaç ve rehber duraklar. Geri dönüşte “Molan duraklatıldı” ve “Devam et” gösterilir. Geçen arka plan süresi egzersiz olarak sayılmaz. Gerçek çağrı durumunu izlemek için telefon izni istenmez.

### 8.8 Çevrimdışı ilk açılış

Uçak modunda yeni kurulum açılır → temel tanıtım, 12 rutin ve rehberler çalışır → harici kaynak bağlantıları açılmak istendiğinde bağlantı gerektiği belirtilir. Uygulama ilk seans için dosya indirmeyi beklemez.

<a id="bolum-09"></a>
## 9. Ekranların ayrıntılı tanımı

### S01 — Karşılama

**Amaç:** Değeri tek bakışta göstermek. Üstte küçük ürün adı, ortada tek çizim, altta kısa açıklama ve birincil eylem. Kaydırmalı üç-beş sayfalık tanıtım yok.

**Eylemler:** İlk molamı dene; Hatırlatmaları ayarla. Android geri uygulamadan çıkabilir. İnternet olmaması ayrı hata değildir.

### S02 — Güvenli kullanım özeti

Üç kısa madde, “Ayrıntıları oku” bağlantısı ve “Anladım, devam et” düğmesi. Onay, tıbbi uygunluk kontrolü veya sorumluluk devri olarak sunulmaz. Kullanıcının sağlık beyanı alınmaz. Metnin görüldüğü sürüm yerelde tutulur; önemli içerik değişikliğinde yeniden gösterilir.

### S03 — Mola ana ekranı

```text
┌──────────────────────────────────┐
│ Esneme Molası                 ⚙  │
│                                  │
│ Kendine 3 dakika ayır             │
│ Kısa, sakin bir hareket molası.   │
│                                  │
│          [sade çizim]             │
│                                  │
│ [       3 dakikalık mola       ]  │
│ Bugünkü öneri: Masa başı dengesi  │
│                                  │
│ Sonraki hatırlatma: yaklaşık 11.00│
│                                  │
│ Bu hafta 4 mola               >  │
│                                  │
│          Mola     Rutinler        │
└──────────────────────────────────┘
```

Bu şema yerleşim tarifidir; gerçek font ölçeğine göre satırlar genişler. Ana içerik dar ekranda kaydırılabilir. Başlat düğmesi ilk görünümde yer alır; büyük fontta düğmeyi görünür tutmak için dekoratif çizim küçülür.

**Durumlar:** Yeni kullanıcı; hatırlatma kapalı; çalışma saatleri dışında; bugün sessizde; izin kapalı; önceki seans duraklatılmış; yerel kayıt hatası. Aynı anda tüm uyarılar gösterilmez. Öncelik: devam eden seans → kayıt sorunu → bildirim durumu → sonraki zaman.

**Öneri seçimi:** Son tamamlanan rutini hemen tekrarlamayan, uygun rutinler arasında sırayla dönen deterministik seçim. Gizli sağlık puanı veya yapay zekâ kişiselleştirmesi yok.

### S04 — Rutinler

Başlık, yatay kaydırma gerektirmeyen 3–4 filtre ve tek sütunlu liste. Kart başına ad, “3 dk”, “Oturarak/Ayakta”, en fazla bir kısa açıklama. İlk sürümde arama kutusu gerekmez; 12 içerik görünür filtrelerle erişilebilirdir.

**Filtreler:** Tümü, Oturarak, Ayakta. Bölge seçimi gerekirse alt panelde eklenir; iki sıra kalabalık etiket zorunlu değildir. Son filtre hatırlanır. Sonuç yoksa “Bu seçimde rutin yok” ve “Filtreleri temizle”.

### S05 — Rutin ayrıntısı

Rutin adı, 3 dakika etiketi, ortam, gerekli alan, altı hareketin kısa sırası, güvenli kullanım bağlantısı ve sabit alt “Başlat” düğmesi. Uzun açıklama düğmeyi örtmez. Hareket adına dokunmak statik önizleme ve kısa metin açar. Önizleme seans kaydı oluşturmaz.

### S06 — Hazırlık ve aktif seans

```text
┌──────────────────────────────────┐
│ Bitir                  Ses kapalı│
│                                  │
│        Omuzları rahatlat         │
│            Adım 2 / 6            │
│                                  │
│        [hareket rehberi]         │
│                                  │
│ Omuzlarını rahat bir aralıkta     │
│ yavaşça hareket ettir.            │
│                                  │
│             00:18                │
│ Toplam kalan 02:03               │
│ ━━━━━━━────────────────────────  │
│                                  │
│ [       Duraklat       ] [Atla]   │
└──────────────────────────────────┘
```

Aktif adım başlığı, görsel rehber, kısa yönerge ve büyük kontrol aynı görsel önceliği paylaşmaz: önce hareket ve yönerge, sonra süre, sonra ikincil kontroller. Ekranın herhangi yerine dokunmak gizli bir eylem yapmaz.

**Hazırlık:** 10 saniye; kullanıcı yerleşir. “Hazırlığı atla” kalan süreyi kısaltır. **Hareket:** Altı blok. **Geçiş:** Sıradaki hareket ve pozisyon değişimi. **Kapanış:** 5 saniye, sakin bitiş.

### S07 — Duraklatma

Rehber bulunduğu pozda donar; metin “Duraklatıldı” olur. Birincil “Devam et”, ikincil “Molayı bitir”. Sistem kaynaklı duraklama nedenini kısa gösterir. Durdurma panelinin arkasındaki süre ilerlemez.

### S08 — Bitirme seçimi

Aktif seansın “Bitir” kontrolü önce anında duraklatır. Alt panel: “Molayı burada bitirebilirsin.” Eylemler: “Bitir” ve “Devam et”. Rahatsızlık nedeniyle çıkışta başka engel yoktur. Seans zaten sona ermişse panel açılmaz.

### S09 — Tamamlandı / Kısmi mola

Tam akış: “Molan tamamlandı.” Kısmi: “Bugün kendine kısa bir ara verdin.” Gerçek aktif süre ve tamamlanan adım sayısı gösterilir; atlanan süre yapılmış kabul edilmez. Birincil “Günüme dön”. İkincil isteğe bağlı “Hatırlatmaları ayarla”. Konfeti, otomatik değerlendirme penceresi ve ödeme ekranı yok.

### S10 — Çalışma planı

Hatırlatmalar anahtarı; gün seçimi; başlangıç ve bitiş; isteğe bağlı tek sessiz aralık; 45/60/90 dakika seçimi. Varsayılan öneri: hafta içi 09.00–18.00, 60 dakika, 12.00–13.00 sessiz aralık. Bu değerler kullanıcıya gösterilir; kullanıcı kaydetmeden bildirim etkinleşmez.

Form kaydetmeyle uygulanır; geri çıkışta değiştirilmiş form varsa “Değişiklikleri kaydet / Vazgeç / Düzenlemeye dön”. Günlerin hiçbiri seçilmezse kaydetme yerine açıklayıcı doğrulama. MVP'de geceyi aşan vardiya desteklenmez; bitiş başlangıçtan sonra olmalıdır. Vardiya gereksinimi V1.1 değerlendirmesidir.

### S11 — Ayarlar

Gruplar: Hatırlatmalar; Rehber; Görünüm; Veriler ve gizlilik; Yardım. Tema: Sistem/Açık/Koyu. Rehber: Ses, titreşim, azaltılmış hareket. Her seçenek mevcut değerini gösterir. Sistem bildirim kanalı ayarına doğrudan bağlantı vardır.

### S12 — Geçmiş

“Bu hafta” toplamı, basit gün satırları, son 30 günlük seanslar. Grafiğe bağımlı değildir. Tamamlanan ve kısmi molalar ayrı etiketlenir. Hiç kayıt yoksa “İlk molan burada görünecek.” Silinen günler başarısızlık olarak kırmızı gösterilmez.

### S13 — Veriler ve gizlilik

Yerelde tutulanlar, dışarı gönderilenler, saklama süresi, verileri silme ve gizlilik metni. “Mola geçmişini sil” ile “Uygulamayı sıfırla” ayrı işlemler. Sıfırlama hatırlatmaları da kapatır ve ilk kuruluma döner. Satın alma eklendiğinde Play tarafından tutulan işlem kayıtlarının bu işlemle silinmediği açıklanır.

### S14 — Yardım ve bildirim sorunu

Sıra: Uygulama izni → kanal açık mı → çalışma planı etkin mi → bugün sessizde mi → cihaz pil kısıtları. “Test bildirimi gönder” yalnızca kullanıcı dokununca çalışır ve zamanlanmış hatırlatmanın yerini almaz. Test bildirimi başarısı, gelecekteki zamanlamayı garanti etmez.

### S15 — Plus, yalnızca sonraki sürüm

Üç kısa fayda, Play'den alınan yerel fiyat, “Tek seferlik ödeme” açıklaması, satın al, geri yükle ve belirgin kapat kontrolü. Kullanıcı bu sayfaya Ayarlar'dan veya açıkça Plus özelliği seçerek gelir. İlk mola, seans ve güvenlik ekranına yerleştirilmez.

<a id="bolum-10"></a>
## 10. Sade görsel tasarım sistemi

### 10.1 Görsel yön

Sıcak nötr zemin, koyu okunabilir metin, tek yeşil vurgu ve düz yüzeyler. Sağlık kliniği görünümü, yoğun spor salonu estetiği, neon renkler ve yüksek kontrastlı motivasyon afişleri kullanılmaz. Minimal görünüm, çok ince ve okunmayan gri metin anlamına gelmez.

### 10.2 Tasarım tokenları

| Token | Açık tema adayı | Koyu tema adayı | Kullanım |
|---|---|---|---|
| background | #F7F8F4 | #141916 | Ana zemin |
| surface | #FFFFFF | #1E2721 | Panel ve kart |
| textPrimary | #1D2921 | #EDF3EC | Başlık ve temel metin |
| textSecondary | #536258 | #B9C8BB | Yardımcı bilgi |
| primary | #2F6B4F | #A6D8B7 | Birincil eylem |
| onPrimary | #FFFFFF | #143321 | Birincil eylem metni |
| outline | #758178 | #819085 | Kontrol sınırları |
| error | #B3261E | #FFB4AB | Hata; atlanan mola için kullanılmaz |

Renkler üretim öncesi gerçek bileşen çiftleriyle ölçülecek adaylardır. Opaklık uygulandıktan sonraki kontrast da kontrol edilir. Normal metinde en az 4.5:1, büyük metinde 3:1 ve anlam taşıyan kontrol/grafiklerde 3:1 iç hedef olarak benimsenir; bu tek başına tam WCAG uygunluğu iddiası değildir. [WCAG 2.2](https://www.w3.org/TR/WCAG22/)

### 10.3 Tipografi

Sistem sans serif; ek font indirme yok. Ana başlık 28sp, sayfa başlığı 24sp, bölüm başlığı 20sp, gövde 16sp, yardımcı metin 14sp, büyük sayaç 40–48sp. Kritik yönergeler 16sp altına düşmez. Sayaçta rakamlar değişirken yatay sıçrama yapmayan rakam stili tercih edilir. Türkçe İ/ı/ş/ğ karakterleri kontrol edilir.

Metin için mutlak yükseklik verilmez. Büyük yazıda kart ve düğmeler büyür. 200% yazı ölçeği ve en geniş erişilebilir ekran ölçeği temel akışlarda denenir. Harf aralığıyla bütün başlıkları büyüten dekoratif kullanım yoktur.

### 10.4 Yerleşim

4dp temel ölçek; ana boşluklar 8, 12, 16, 24, 32dp. Telefon kenarı 20–24dp; kart içi 16–20dp. Düğme yüksekliği normal durumda 56dp; dokunma hedefi en az 48dp. Kart yarıçapı 16dp, panel 24dp. Fazla gölge yerine boşluk ve yüzey farkı kullanılır.

Sistem durum ve gezinme alanlarının iç boşlukları korunur. Edge-to-edge çizimde içerik çentik ve sistem çubuğunun altına saklanmaz. Tabletlerde metin satırı makul genişlikte tutulur; ekranın tamamına yayılıp okunurluk azalmaz.

### 10.5 Bileşen kuralları

- **PrimaryButton:** Sayfada bir baskın eylem; yükleme varsa metin korunur, çift dokunma engellenir.
- **SecondaryButton:** Ana eylemle yarışmayan çerçeveli veya tonal yüzey.
- **TextAction:** Silme gibi sonuçlu eylem yanlışlıkla küçük bağlantıya saklanmaz.
- **RoutineRow:** Küçük çizim, başlık, iki bilgi; tüm satır tek odak hedefi.
- **StatusRow:** Simge + kısa durum + gerekirse tek düzeltme eylemi.
- **BottomSheet:** Kısa seçimler; uzun yasal metin ayrı sayfa.
- **Snackbar:** Kaydedildi gibi geçici geri bildirim; tek güvenlik bilgisi taşıyıcısı değildir.
- **Progress:** Toplam süre çizgisi; ana sayaç çevresinde sürekli titreşen halka yok.

### 10.6 Minimalizm kontrolü

Bir ekran tasarım incelemesinde şu sorular sorulur: İlk bakışta yapılacak iş belli mi? Aynı bilgi iki kez gösteriliyor mu? İkincil bir tercih ayarlara taşınabilir mi? Sembolün anlamı yazı olmadan belirsiz mi? Boşluk içerikten çok yer kaplayıp eylemi ekran dışına atıyor mu? Koyu temada dekorasyon hareketi okumayı zorlaştırıyor mu?

<a id="bolum-11"></a>
## 11. Uygulama içi hareketler ve mikro etkileşimler

### 11.1 Amaç

Arayüz animasyonu konum ve durum değişimini açıklamalıdır. Egzersiz rehberi ise fiziksel hareketin yönünü ve temposunu anlatır. Bu iki animasyon türü birbirinden bağımsız yönetilir. Kullanıcı azaltılmış hareket seçtiğinde gezinme geçişleri kapanabilir; rehber metin ve sabit pozlarla kullanılmaya devam eder.

### 11.2 Etkileşim tarifleri

| Etkileşim | Normal davranış | Süre adayı | Azaltılmış hareket |
|---|---|---|---|
| Düğmeye basma | Material basılı durum / hafif renk değişimi | 80–120 ms | Yalnız renk |
| Sayfa açma | Kısa solma + en fazla 8dp konum değişimi | 180–220 ms | Anlık veya 80 ms solma |
| Alt panel | Sistemle uyumlu alttan giriş | 220–280 ms | Anlık / kısa solma |
| Filtre değişimi | Liste içeriğinde kısa solma | 120–160 ms | Anlık |
| Seans adımı değişimi | Eski rehber durur, geçiş metni, yeni rehber | 160–220 ms | Sabit yeni poz |
| Duraklatma | Çizim aynı karede durur; kontrol değişir | Anlık | Aynı |
| Seans tamamlanması | Sade onay simgesi görünür | 180 ms | Anlık |
| Hata | Alan altında açıklama | Anlık | Aynı |
| Veri silme | Liste boş duruma geçer | 120 ms | Anlık |

Bu süreler ürün tasarımı önerisidir. Yavaş cihazda süreyi büyütmek yerine pahalı efekt kaldırılır. Uygulamada sıçrayan kartlar, sürekli parlayan düğme, nefes alır gibi ölçeklenen bütün sayfa ve rastgele yay animasyonları bulunmaz.

### 11.3 Dokunma ve jest sözleşmesi

Kaydırma liste gezmek içindir; seans hareketini yanlışlıkla değiştirmez. Atlamak için etiketli düğme gerekir. Uzun basma zorunlu bir işi açmaz. Çift dokunma, iki seans veya iki kayıt oluşturmaz. Üç nokta menüsünde kullanıcıyı durduracak temel kontroller saklanmaz.

### 11.4 Ses ve titreşim

Varsayılan ses kapalı; kullanıcı açarsa kısa yerel ipuçları. Titreşim varsayılan sistem tercihine saygılı ve uygulama içinde kapatılabilir. Her saniye tık sesi veya titreşim yok. İsteğe bağlı tek hafif uyarı geçişte, tek uyarı bitişte. Rahatsız Etmeyin ve sistem ses seviyesi aşılmaz.

Ses odağı kaybında ipucu durur; başka medya sesinin üzerine yüksek ses bindirilmez. Dış uygulamanın sesini değiştirme izni veya kontrolü istenmez. Sesin yüklenememesi seansı engellemez; metin rehberi sürer.

### 11.5 Teknik uygulama yönü

Compose durum odaklı geçişleri kullanılır; animasyon başlatma iş kurallarını tetiklemez. Seans sayacı tek otoritedir. Bir animasyonun bitiş callback'i veri tabanına “seans tamamlandı” yazmaz. Android'in Compose animasyon API'leri bu ayrımın görsel katmanını kurmak için kullanılabilir. [Compose animasyon rehberi](https://developer.android.com/develop/ui/compose/animation/quick-guide)

<a id="bolum-12"></a>
## 12. Hareket rehberinin görsel üretimi

### 12.1 Karakter yaklaşımı

Sade, yetişkin insan oranlarını koruyan, cinsiyet ve performans vurgusu yapmayan iki boyutlu karakter. Kol, omuz, gövde ve başın yönü açıkça seçilir. Dekoratif çöp adam anatomik yönü belirsiz bırakıyorsa kullanılmaz. Rahat hareket açıklığı gösterilir; aşırı esneme, yaylanma ve eklem sınırına zorlama çizilmez.

### 12.2 Her hareket için teslim varlıkları

1. Başlangıç pozu.
2. Hareketin ara ve son pozları.
3. Rahat başlangıca dönüş.
4. Uygun kamera açısı: ön, yan veya üç çeyrek.
5. Gerekirse tek yön oku.
6. Normal rehber animasyonu.
7. Azaltılmış hareket için 2–3 sabit poz.
8. Eşdeğer Türkçe yönerge.
9. TalkBack için kısa eylem açıklaması.
10. Uzmanın tarihli görsel ve metin değerlendirme kaydı.

### 12.3 Sağ-sol açıklığı

Yönergede sağ ve sol, kullanıcının kendi bedeni için kullanılır. Karakterin karşıdan gösterildiği sahnelerde ayna algısı test edilir. Görseli rastgele yatay çevirmek yerine `userSide` ve `viewAngle` içerik verisinde açıkça tanımlanır. Boyun gibi yönün önemli olduğu hareketlerde yan veya üç çeyrek görünüm tercih edilebilir.

### 12.4 Döngü ve zaman

Fiziksel hareketin ritmi uzman tarafından belirlenir. “Arayüz daha canlı dursun” diye hızlandırılmaz. Döngü giriş, hareket, gerekiyorsa kısa bekleme ve dönüş fazlarını içerir. Toplam blok süresi dolarken karakter uygunsuz bir ara pozda ani kesilmez; içerik süreleri tam döngüye uygun tasarlanır veya güvenli geçiş pozu tanımlanır.

### 12.5 Dosya ve motor kararı

İlk tercih: yerel vektör tabanlı kontrollü animasyon; küçük hareket havuzu için Compose çizimleri veya lisansı doğrulanmış bir animasyon oynatıcı. Seçim, bir boyun ve bir tam gövde denemesinin görüntü kalitesi, kare süresi, dosya boyutu ve uzman düzeltme kolaylığı ölçülerek yapılır. Lottie/Rive gibi ek motorlar yalnızca ölçülen ihtiyaç varsa alınır; plan belirli sürümün güncelliğini varsaymaz.

Yüksek çözünürlüklü uzun videolar ilk tercih değildir. Bir çizim bozulursa metin ve sabit pozlara düşülür. Bu yedek de eksikse hareket sunulmaz; hatalı rehberle devam edilmez.

### 12.6 İçerik üretim hattı

Hareket taslağı → uzman metin değerlendirmesi → düşük ayrıntılı poz çizimi → uzman poz değerlendirmesi → animasyon → telefonda anlaşılabilirlik testi → ses/metin eşleştirmesi → sürümleme → paketleme. İnternet sitelerindeki görsel ve sesler lisans kontrolü olmadan kopyalanmaz; kaynak göstermek yeniden kullanım izni yerine geçmez.

<a id="bolum-13"></a>
## 13. Erişilebilirlik ve kapsayıcılık

Android'in erişilebilir Compose bileşenleri başlangıçtır; özel kontroller ayrıca denetlenir. Dokunma alanları en az 48dp olarak tasarlanır. [Compose erişilebilirlik varsayılanları](https://developer.android.com/develop/ui/compose/accessibility/api-defaults)

### 13.1 TalkBack

- Okuma sırası: adım adı → yönerge → kalan süre bilgisi → kontroller.
- Her saniye canlı bölge duyurusu yapılmaz; adım değişimi ve kullanıcı isteğiyle süre okunur.
- Sayaç görsel güncellenirken ekran okuyucu konuşmasını kesmez.
- Dekoratif çizimler odak almaz; rehberin eşdeğer açıklaması vardır.
- “Ses kapalı, açmak için çift dokun” gibi durumu içeren etiket kullanılır.
- Panel açılınca odak panelin başlığına taşınır; kapanınca açan kontrole döner.
- Seansın otomatik adım ilerlemesi, kontrol odağını beklenmedik şekilde değiştirmez.

### 13.2 Görme, işitme ve motor ihtiyaçlar

Anlam yalnızca renkle taşınmaz. Sesli yönergenin yazılı eşdeğeri vardır. İki elle kullanım veya hassas sürükleme gerekmez. Fiziksel klavye ve Switch Access ile temel akış tamamlanır. Küçük ekranda “Atla” ve “Duraklat” dokunma alanları çakışmaz.

### 13.3 Azaltılmış hareket

Sistem animasyon tercihine uygun davranış ve uygulama içinde “Rehberi sabit adımlarla göster” seçeneği. Sabit pozlar arasında kullanıcı isteğine veya yumuşak zamanlı değişime göre geçilir; yanıp sönme yoktur. Bu modda süre ve yönerge aynı anlamı taşır. İhtiyaç halinde kullanıcı duraklatıp bir adımı daha uzun okuyabilir.

### 13.4 Farklı bedenler ve ortamlar

Ayakta rutinler açık etiketlenir; oturarak seçenekler eşit görünürlükte sunulur. Her kullanıcıya her hareketin uygun olduğu varsayılmaz. Tekerlekli sandalye kullanan kişilere uygunluk, yalnızca “oturarak” etiketiyle ilan edilmez; ayrıca uzman ve kullanıcı değerlendirmesi gerekir. Telefonu elde tutmayı gerektiren hareket seçilmez.

<a id="bolum-14"></a>
## 14. İçerik güvenliği ve uzman denetimi

### 14.1 İçerik statüsü

Bu belgedeki hareket adları ve rutin sıraları **ürün/içerik taslaklarıdır**. Klinik olarak onaylanmış kişisel egzersiz reçetesi değildir. Yayına girecek hareket aralığı, tekrar temposu, tutma süresi, pozisyon ve istisnalar nitelikli fizyoterapist veya uygun sağlık uzmanıyla kesinleştirilir. Boyun ve bel odaklı içeriklerde bu değerlendirme kritik bağımlılıktır.

WHO'nun genel fiziksel aktivite önerileri bu uygulamanın 180 saniyelik rutinlerinin etkinliğini doğrulamaz. NHS örnekleri araştırmaya başlangıç sağlar; kaynakta olmayan birleşik rutinler bu planın önerisidir. [WHO](https://www.who.int/publications/i/item/9789240014886), [NHS](https://www.nhs.uk/live-well/exercise/sitting-exercises/)

### 14.2 Ürün içinde kısa güvenli kullanım metni taslağı

“Hareketleri rahat hissettiğin aralıkta yap ve kendini zorlama. Ağrı, baş dönmesi veya uyuşma hissedersen dur. Bir sağlık durumun, yakın zamanda yaralanman veya ameliyatın varsa başlamadan önce bir sağlık uzmanına danış. Bu uygulama tanı veya tedavi sunmaz.”

Bu metin yayından önce uzman ve mağaza politika incelemesinden geçer. Kullanıcıdan hastalık listesi veya sağlık geçmişi toplamak için kullanılmaz.

### 14.3 Hareket seçimi ilkeleri

- Düşük yoğunluk, sade yönerge, kısa ve anlaşılır sıra.
- Desteksiz denge gerektiren veya düşme riskini artıran hareketler ilk pakette yer almaz.
- Sandalye kullanılan içerikte sabit, kaymayan ve uygun destek sağlayan oturma yüzeyi önerisi açıklanır; tekerlekli ofis sandalyesinde her hareketin uygun olduğu varsayılmaz.
- Boyna elle baskı, tam boyun çevirme daireleri, ani dönüş, zorlayıcı bel bükme ve yaylanma hareketleri taslak havuzda bulunmaz.
- Egzersiz sırasında telefona eğilme ihtiyacını azaltacak büyük yönerge ve ses seçeneği sağlanır.
- Rahatsızlık hisseden kullanıcıya “devam et, açılırsın” benzeri tavsiye verilmez.
- Nefes tutma veya süre yetiştirme hedefi oluşturulmaz.

### 14.4 Uzman değerlendirme formu

| Alan | Beklenen kayıt |
|---|---|
| Hareket kimliği ve sürümü | Metin ve animasyon aynı sürümde |
| Amaç | Genel mobilite / hafif hareket farkındalığı gibi sınırlı ifade |
| Başlangıç pozisyonu | Destek, ayak/kol konumu, çevresel ihtiyaç |
| Hareket açıklığı | Sayısal veya nitel, uzman tarafından belirlenmiş |
| Tempo ve tekrar | 25 saniyelik bloğa uygunluğu ayrıca kontrol edilmiş |
| Uygun olmayan durumlar | Genel kullanıcı metnine dönüştürülebilen uyarılar |
| Alternatif | Onaylı daha basit seçenek veya atlama |
| Görsel doğruluk | Her ana poz ve dönüş yolu incelenmiş |
| Dil | Tedavi vaadi, zorlama veya belirsiz yön bulunmuyor |
| Sonuç | Taslak / düzeltme gerekli / yayın için onaylı |

### 14.5 İçerik sorunu bildirilirse

Kullanıcı seansı engellenmeden bitirebilir. Geri bildirim isteğe bağlıdır ve sağlık ayrıntısı yazmaya zorlamaz. Şüpheli içerik sonraki paketle devre dışı bırakılır. Tamamen çevrimdışı kurulumların uzaktan anında güncellenemeyeceği kabul edilir; bu nedenle ilk paket incelemesi güçlü tutulur. İnternet yokken çalışan bir “acil uzaktan kapatma” varmış gibi varsayılmaz.

<a id="bolum-15"></a>
## 15. Hareket havuzu ve 12 rutin

### 15.1 Süre şablonu

Standart rehber: **10 saniye yerleşme + 6 × 25 saniye hareket + 5 × 3 saniye geçiş + 5 saniye kapanış = 180 saniye.** Bu zamanlama ürün önerisidir; uzman 25 saniyeyi belirli bir hareket için uygun bulmazsa ilgili rutin, yine 180 saniyeyi sağlayan değişken bloklarla yeniden düzenlenir.

Bir blok iki taraf içeriyorsa taraf değiştirme blok içinde açıkça tanımlanır; “25 saniye” her tarafa ayrıca uygulanmaz. Taraf süresi, dönüş ve dinlenme fazları içerik verisinde yer alır. İki taraflı hareketler aynı uzunlukta tekrar yapmaya zorlanmaz; uzman temposu belirleyicidir.

### 15.2 Aday hareket havuzu

Aşağıdaki tabloda kesin klinik talimat yerine içerik ve görsel üretim kapsamı tanımlanır. Onaylanmayan hareket yerine yeni onaylı hareket seçilir; sayıyı tamamlamak için zayıf içerik yayımlanmaz.

| Kimlik | Çalışma adı | Pozisyon | Görsel odak | Uzmanın netleştireceği konu |
|---|---|---|---|---|
| H01 | Rahat oturuşa yerleşme | Oturarak | Ayakların desteklenmesi, rahat gövde | Destek seçenekleri |
| H02 | Omuzları gevşetme | Oturarak/ayakta | Omuzun küçük kontrollü hareketi | Hareket yönü ve tempo |
| H03 | Kürek kemiği farkındalığı | Oturarak/ayakta | Üst sırt ve omuz hizası | Sıkıştırma şiddeti ve açıklık |
| H04 | Rahat baş dönüşü | Oturarak | Başın sınırlı sağ/sol yönü | Uygun açıklık, dönüş ve taraf zamanı |
| H05 | Göğüs bölgesini açma | Oturarak/ayakta | Kol ile gövdenin ilişkisi | Omuz uygunluğu ve kol açısı |
| H06 | Hafif üst gövde dönüşü | Oturarak | Kalça sabitliği ve gövde yönü | Bel/boyun istisnaları |
| H07 | El açma ve kapama | Oturarak/ayakta | Parmakların rahat hareketi | Tekrar temposu |
| H08 | Bilek hareket farkındalığı | Oturarak | El-bilek hizası | Baskısız açıklık |
| H09 | Ön kolu rahatlatma | Oturarak | Dirsek ve bilek konumu | Zorlamasız varyant |
| H10 | Ayak bileğini hareket ettirme | Oturarak | Ayak yönü, destek | Taraf düzeni ve açı |
| H11 | Oturarak hafif ayak kaldırma | Oturarak | Kontrollü alt ekstremite hareketi | Denge ve sandalye koşulları |
| H12 | Kontrollü kol uzatma | Oturarak/ayakta | Kolun rahat erişim alanı | Omuz kısıtlarına alternatif |
| H13 | Ayakta rahat duruş | Ayakta | Ayak-gövde dengesi | Destek gereksinimi |
| H14 | Destekli ağırlık aktarımı | Ayakta | Küçük kontrollü yer değiştirme | Denge ve destek koşulları |
| H15 | Destekli topuk hareketi | Ayakta | Ayak ve destek ilişkisi | Denge, tekrar ve alternatif |
| H16 | Rahat nefes ve gevşeme | Oturarak/ayakta | Dinlenme, zorlamasız nefes | Nefes tutturmayan yönerge |

H04–H06 ve H10–H11 gibi adaylar için NHS'nin oturarak egzersiz örnekleri araştırma girdisidir; buradaki adlandırma, süre ve birleşimler NHS programı olarak sunulmaz. [NHS oturarak egzersizler](https://www.nhs.uk/live-well/exercise/sitting-exercises/)

### 15.3 On iki rutin taslağı

Her satır altı hareket bloğunu sırasıyla gösterir. Başlangıç ve kapanış ortak süre şablonundadır. Aynı hareket birden çok rutinde yer alabilir; 12 rutin için 72 ayrı animasyon gerekmez. Ancak her birleşim ayrıca değerlendirilir.

| Rutin | Ad | Ortam | Altı blok | Kullanıcıya kısa açıklama |
|---|---|---|---|---|
| R01 | Masa başı dengesi | Oturarak | H01, H02, H03, H07, H10, H16 | Güne kısa bir hareket arası |
| R02 | Omuzlara mola | Oturarak | H01, H02, H03, H05, H12, H16 | Üst beden için sakin bir sıra |
| R03 | Boyun çevresine nazik mola | Oturarak | H01, H02, H04, H03, H07, H16 | Küçük ve rahat hareketler |
| R04 | Ellere kısa ara | Oturarak | H01, H07, H08, H09, H02, H16 | Klavye arasına sığan mola |
| R05 | Üst gövde hareketi | Oturarak | H01, H02, H06, H05, H07, H16 | Yerinden ayrılmadan hareket |
| R06 | Ayaklara hareket | Oturarak | H01, H10, H11, H10, H02, H16 | Alt beden için kısa bir sıra |
| R07 | Toplantı arası | Oturarak | H01, H07, H02, H03, H10, H16 | Küçük hareketlerle ara ver |
| R08 | Ekrandan kısa uzaklaşma | Oturarak | H01, H02, H12, H07, H10, H16 | Sesli rehber varsa gözün ekranda kalmasın |
| R09 | Ayakta yenilenme | Ayakta | H13, H02, H14, H07, H12, H16 | Uygun alanda ayakta mola |
| R10 | Destekli ayakta mola | Ayakta | H13, H14, H15, H02, H03, H16 | Sabit destek yakınında hareket |
| R11 | Öğleden sonra arası | Oturarak | H01, H05, H03, H08, H10, H16 | İşin ortasında üç dakika |
| R12 | Çalışma günü kapanışı | Oturarak | H01, H02, H07, H10, H03, H16 | Günü sakin bir arayla tamamla |

R08 adı bir göz egzersizi veya göz sağlığı yararı iddiası içermez. R03 varsayılan ilk rutin değildir; kullanıcı boyun odaklı içeriğe bilinçli girer. “Bel ağrısı çözümü” adında rutin oluşturulmaz; üst gövde hareketiyle sınırlı anlatım kullanılır.

### 15.4 Rutinler birbirinden nasıl ayrılacak?

Yalnızca isim değiştirmek yeterli değildir. Hareket sırası, hedeflenen bağlam, görsel açı ve kısa ipuçları anlamlı farklılık taşımalıdır. Kullanıcı testinde “Hepsi aynı geliyor” geri bildirimi alınırsa rutin sayısı pazarlama gerekçesiyle korunmaz; içerik kalitesi artırılır. Yayın hedefi 12 anlamlı rutindir.

### 15.5 İçerik şeması

```json
{
  "routineId": "R01",
  "version": 1,
  "locale": "tr-TR",
  "title": "Masa başı dengesi",
  "position": "seated",
  "reviewStatus": "draft",
  "plannedDurationMs": 180000,
  "preparationMs": 10000,
  "transitionMs": 3000,
  "closingMs": 5000,
  "blocks": [
    {"movementId": "H01", "durationMs": 25000},
    {"movementId": "H02", "durationMs": 25000},
    {"movementId": "H03", "durationMs": 25000},
    {"movementId": "H07", "durationMs": 25000},
    {"movementId": "H10", "durationMs": 25000},
    {"movementId": "H16", "durationMs": 25000}
  ]
}
```

Bu bir veri sözleşmesi örneğidir. Release derlemesi `draft` içeriği kabul etmez. Hareket dosyasında ayrıca fazlar, kullanıcı tarafı, görsel, sabit pozlar, metin, ses, alternatif kimlik ve değerlendirme sürümü yer alır. İçerik doğrulayıcı toplam süreyi ve tüm referansları kontrol eder.

<a id="bolum-16"></a>
## 16. Seans motoru ve süre sözleşmesi

### 16.1 Durum makinesi

```text
IDLE → PREPARING → RUNNING ↔ PAUSED
                       ↓
                  TRANSITION → RUNNING
                       ↓
                    CLOSING → COMPLETED

PREPARING / RUNNING / TRANSITION / CLOSING
  ├─ kullanıcı bitirir → ENDED_EARLY
  ├─ görünürlük kaybı → PAUSED
  └─ süreç kaybı sonrası dönüş → RECOVERABLE_PAUSED
```

Geçiş sırasında duraklatma desteklenir. Tek aktif seans kuralı vardır. Durum olayları bir reducer/denetleyici üzerinden sıralanır; ses, çizim ve sayaç farklı durum kaynaklarına bağlanmaz.

### 16.2 Zamanlama yöntemi

Seans ilerlemesi monotonik saatten hesaplanır; sistem duvar saatinin değişmesi sayacı etkilemez. UI her kare veya saniye yenilenebilir ama “1 saniye eksilt” döngüsü asıl veri değildir. Duraklama anında aktif süre sabitlenir; devamda yeni monotonik başlangıç alınır. Animasyon fazı aktif adımın geçen süresinden türetilir.

Uygulama rotasyonunda veya pencere boyutu değiştiğinde seans yeniden başlamaz. Görünür çoklu pencerede pencere odağı kaybı tek başına duraklatma nedeni sayılmaz; gerçek görünürlük ve ses odağı politikası ayrılır. MVP'de görünürlük kaybında otomatik duraklatma açık davranıştır.

### 16.3 Sürelerin anlamı

| Alan | Anlam |
|---|---|
| plannedDurationMs | Orijinal 180 saniyelik rehber |
| activeGuidanceMs | Hazırlık, hareket, geçiş ve kapanışın gerçekten oynayan bölümleri |
| activeMovementMs | Yalnız hareket bloklarının oynayan bölümleri |
| pausedMs | Duraklamada geçen süre; hareket sayılmaz |
| skippedMs | Atlamayla çıkarılmış süre |
| wallDurationMs | Başlatma ile bitirme arasındaki gerçek toplam süre |

Hiç atlama yoksa 180 saniyelik rehberin hareket bölümü 150 saniyedir. Ürün “180 saniye egzersiz yaptın” demez; “3 dakikalık mola” der. Duraklama varsa duvar süresi üç dakikadan uzun olabilir.

### 16.4 Atlama ve tamamlama

Atla, mevcut hareketin kalan kısmını çıkarır; sonraki hareket için gerekli geçişi korur. Son hareket atlanırsa kapanışa geçilir. Geriye kalan toplam süre yeniden hesaplanır. Hazırlık atlama, hareket atlamayla aynı istatistik değildir.

Tüm hareket blokları oynandıysa ve kapanışa ulaşıldıysa `completed`; hareket atlandıysa `finished_with_skips`; kullanıcı erken bitirdiyse `ended_early`. Ekranda bunlar anlaşılır Türkçe etiketlere çevrilir. Tamamlanma düğmesine tekrar dokunmak aynı `sessionId` ile ikinci kayıt oluşturmaz.

### 16.5 Süreç ölümü ve geri yükleme

Adım geçişinde, duraklatmada ve sınırlı aralıklı checkpoint'te aktif durum yerel olarak saklanır. Her animasyon karesinde disk yazımı yapılmaz. Süreç öldürülürse son checkpoint'ten **duraklatılmış** geri dönülür; görünmeyen sürenin tamamlandığı varsayılmaz. Kayıp olabilecek en fazla birkaç saniye için 5 saniyelik checkpoint hedefi ölçülür.

Yeniden başlatılmış telefonda eski monotonik zamanlar kullanılmaz. En son saklanan aktif süre esas alınır. Önerilen geri dönme süresi 30 dakika; daha eski aktif kayıt kısmi seansa dönüştürülür ve yeni başlangıç önerilir. Bu eşik tıbbi kural değil ürün kararıdır.

### 16.6 Yedek davranış

Animasyon dosyası açılamazsa doğrulanmış statik rehber kullanılır. Metin de yoksa seans duraklar ve “Bu hareket yüklenemedi. Atlayabilir veya molayı bitirebilirsin.” gösterilir. Sessizce rastgele başka hareket yapılmaz.

<a id="bolum-17"></a>
## 17. Hatırlatma ve çalışma takvimi

### 17.1 Kullanıcıya verilen söz

“Seçtiğin çalışma saatlerinde yaklaşık aralıklarla mola öneririz. Telefonun pil ayarları nedeniyle bildirimler gecikebilir.” Belirli saniyede kesintisiz alarm hizmeti vaadi verilmez.

**Önerilen varsayılanlar:** Hafta içi 09.00–18.00; 60 dakika aralık; 12.00–13.00 sessiz saat; günde en fazla 6 otomatik öneri; kullanıcı seçerse 45 veya 90 dakika. Sayılar ürün tercihidir, sağlık önerisi değildir. Günlük sınır yalnızca otomatik önerilere uygulanır; manuel mola sınırlanmaz.

### 17.2 Zamanlama modeli

Kalıcı planlama için tek seferlik **yaklaşık AlarmManager** olayları kullanılır. Uzun iş veya bakım gerekiyorsa WorkManager ayrıdır. Seans sayacı WorkManager'a bağlanmaz. Periyodik WorkManager'ın minimum 15 dakika aralığı ve ertelenebilirliği onu hassas sayaç yapmaz. [WorkManager](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started/define-work)

Normal öneride `setWindow` ile aday başlangıçtan itibaren 15 dakikalık tolerans penceresi; ertelemede `setAndAllowWhileIdle` gibi yaklaşık seçenek gerçek cihaz deneyiyle değerlendirilir. Uygulama Doze'u sürekli aşmak için alarm zinciri kurmaz. Android pil kısıtları altında bu pencerenin de aşılabileceği kabul edilir. Exact alarm izinleri MVP'de istenmez. [Alarm zamanlama](https://developer.android.com/develop/background-work/services/alarms)

### 17.3 Tek sonraki olay ilkesi

Bir anda tek mantıksal `nextReminder` tutulur. Tüm gün için onlarca aktif alarm yığılmaz. Her plan değişikliğinde `scheduleVersion` artırılır; eski sürümle gelen olay etkisizdir. Yürütme anında mevcut plan yeniden okunur. Alarmın kurulmuş olması, bildirimin görülmüş olması demek değildir.

### 17.4 Kural sırası

1. Hatırlatmalar etkin mi?
2. Uygulama bildirimi ve ilgili kanal kullanılabilir mi?
3. Olay güncel plan sürümüne ait mi?
4. Kullanıcı bugün sessize almış veya geçici durdurmuş mu?
5. Yerel gün, çalışma penceresi ve sessiz aralık uygun mu?
6. Aktif/duraklatılmış yakın tarihli seans var mı?
7. Son mola veya öneriden beri gerekli aralık geçti mi?
8. Günlük otomatik öneri sınırı doldu mu?
9. Bu `occurrenceId` daha önce işlendi mi?
10. Olay çok gecikmiş mi? Uygunsa bir bildirim oluştur; değilse eski olayı tüketip geleceği planla.

İzin kapalıysa sık alarm kurmaya devam edilmez. Uygulamaya dönüşte izin ve plan uzlaştırılır. Sistem ayarlarında yeniden izin verilmesi anında arka planda yakalanmış varsayılmaz; sonraki uygulama açılışı güvenilir yeniden kurma noktasıdır.

### 17.5 Aday zaman hesabı

Günün ilk adayı çalışma başlangıcı + seçilen aralıktır. Sonraki aday, son gerçek öneri zamanı veya son manuel mola bitişi gibi en yeni bastırma olayından sonra aralık dolduğunda hesaplanır. Sessiz aralığa denk gelirse sessiz aralık bitişine taşınır. Çalışma sonuna sığmazsa sonraki seçili güne geçilir. Öğle sonundaki ilk öneri tam sessiz saat biter bitmez zorunlu değildir; önceki olaydan minimum aralık korunur.

Manuel seans başlatılınca mevcut bildirim iptal edilir; aktif seans boyunca yenisi gösterilmez. Seans bitişi normal aralığı yeniden başlatır. Çok erken çıkışta da kullanıcıya hemen yeni bildirim atılmaz; bitiş bir bastırma olayıdır.

### 17.6 Gecikme politikası

Normal öneri, aday zamandan 30 dakikadan fazla gecikmişse “kaçırılmış mola” olarak gösterilmez; tüketilir ve şimdiye göre yeni aday hesaplanır. Eşik ürün tercihi ve pilotta ayarlanacaktır. Çalışma dışına taşan bildirim kesin olarak bastırılır. Birikmiş alarmlar art arda yayımlanmaz.

Erteleme hedefi geçmiş ve normal sonraki aday yaklaşmışsa iki olay birleştirilir. Erteleme yeni bir kullanıcı isteğidir ama günlük rahatsızlık bütçesini delmek için kullanılmaz: günde 6 normal öneri + kullanıcı isteğiyle en fazla 3 erteleme teslim denemesi. Sınır sonrası uygulama içinden manuel başlangıç sürer. Bu sınır ayarlarda sade açıklanır; sessizce kaybolmuş hata gibi bırakılmaz.

### 17.7 Erteleme ayrıntıları

“15 dk ertele”, eylemin işlendiği andan 15 dakika sonrası için yaklaşık hedef üretir. Tek ertelenmiş olay vardır. Yeniden erteleme önceki hedefi değiştirir. Sessiz aralık veya iş bitişiyle çakışırsa bir sonraki uygun zaman seçilir; ana ekranda gerçek planlanan zaman görünür. “Bugün sessize al” ertelemeyi de iptal eder.

### 17.8 Takvim uç durumları

| Durum | Beklenen davranış |
|---|---|
| Cihaz yeniden başlar | İlk kilit açılışı sonrasında plan uzlaştırılır, eski olaylar taşınmaz |
| Saat dilimi değişir | Yerel çalışma saatleri korunur, gelecek anlar yeniden hesaplanır |
| Saat elle ileri/geri alınır | Gelecek plan yenilenir; seans süresi etkilenmez |
| Yaz/kış saati boşluğu | Olmayan yerel saat ilk geçerli ana taşınır |
| Tekrarlanan yerel saat | Tek occurrence; ikinci aynı yerel aralıkta kopya bildirim yok |
| Uygulama güncellenir | Kayıtlı plan yeniden kurulur |
| Force-stop | İşletim sisteminin durdurmasına saygı; yeniden açılışta onarılır |
| Bildirim kaydırılıp kapatılır | Ek tekrar yok; bir sonraki normal öneri beklenir |
| Kullanıcı Rutinler'de gezinir | Sistem bildirimi yerine sayfa içinde sessiz öneri; seans sırasında yok |
| Gün seçimi kaldırılır | İlgili gelecek olay iptal edilir |
| Sessiz saat tüm mesaiyi kaplar | Kaydetmede açıklama; uygun öneri zamanı olmadığı gösterilir |
| Cihaz uzun süre kapalıdır | Açılınca eski molalar biriktirilmez |

### 17.9 Saf kural motoru örneği

```text
reconcile(now, settings, ledger, session):
    if disabled or notificationUnavailable:
        cancelPending()
        return OFF
    if activeSession or pausedUntil > now:
        scheduleNextEligibleAfterBlock()
        return SUPPRESSED
    candidate = computeNextEligibleTime(now, settings, ledger)
    persist(candidate, settings.version)
    replacePendingAlarm(candidate)

onAlarm(occurrenceId, scheduleVersion):
    reloadCurrentState()
    rejectStaleDuplicateOrOutsideWindow()
    atomicallyClaimOccurrence()
    postOrSuppressNotification()
    recordAttemptWithoutClaimingUserSawIt()
    reconcile(currentTime, ...)
```

Alarm kaydı ile işletim sistemi bildirim çağrısı tek veritabanı işlemi olamaz. Çökme sonrası aynı occurrence için sabit notification ID kullanılır; tekrar deneme yeni bildirim yığını oluşturmaz. Çifte öneri önleme ve yeniden uzlaştırma idempotent tasarlanır.

<a id="bolum-18"></a>
## 18. Hareketsizlik algılama kararı

### 18.1 MVP neden takvim temelli?

Google'ın `STILL` sabiti cihazın sabitliğini belirtir. Buradan insanın kaç dakika oturduğunu güvenilir biçimde çıkarmak mümkün değildir. Bu uygulamada sensör verisi olmadan bile “60 dakikadır hareketsizsin” mesajı yazılmayacaktır. [DetectedActivity](https://developers.google.com/android/reference/com/google/android/gms/location/DetectedActivity)

### 18.2 Gelecekteki yardımcı sinyal

İsteğe bağlı Activity Recognition, yürüyüş gibi bazı durumlarda öneriyi bastırmaya yardımcı olabilir. Bunun için kullanıcıya amaç açıkça anlatılır ve gerektiği Android sürümlerinde fiziksel aktivite izni istenir. API geçişleri ve filtreleme davranışı araştırılır. [ActivityRecognitionClient](https://developers.google.com/android/reference/com/google/android/gms/location/ActivityRecognitionClient)

Önerilen güven sırası: kullanıcının açık tercihi → aktif seans / sessize alma → çalışma saatleri → varsa hareket sinyali. “Bilinmiyor” durumu oturma sayılmaz; takvim davranışına geri dönülür. Telefon masadayken yürüyen kişiye yanlış öneri yapılabileceği kullanıcıya açıkça anlatılır.

### 18.3 Pilot kabul koşulları

- Telefon masada, cepte, çantada ve şarjda ayrı denenir.
- Google Play services bulunmayan cihazda manuel ve takvim özellikleri sürer.
- İzin geri alınırsa ana deneyim bozulmaz.
- Sensör özelliği açık/kapalı günler arasında bildirim uygunluğu ve pil etkisi karşılaştırılır.
- Kullanıcıların özelliğin oturma ölçmediğini anlayıp anlamadığı test edilir.
- Ürün katkısı küçükse özellik çıkarılır; “akıllı” etiketi uğruna tutulmaz.

Health Connect, Usage Access, Accessibility Service veya konum izni sensör eksikliğini dolaylı yoldan aşmak için eklenmez.

<a id="bolum-19"></a>
## 19. Bildirim tasarımı ve izin akışı

### 19.1 Bildirim içeriği

**Başlık:** “Kısa bir mola?”  
**Metin:** “Uygunsan 3 dakikalık hareket molan hazır.”  
**Eylemler:** Başla · 15 dk ertele · Bugün sessize al.

Kilit ekranı gizliliği için rutin adı, ağrı veya sağlık bilgisi gösterilmez. Bildirim sayısı rozetiyle kaçırılan molalar biriktirilmez. Kanal adı “Mola hatırlatmaları”; ses varsayılan olarak kapalı veya düşük müdahaleli kanal tasarımıyla pilotta doğrulanır. Heads-up görünme, DND ve kanal sesi üzerinde son söz sistem/kullanıcıdadır.

### 19.2 İzin isteme zamanı

Kullanıcı hatırlatıcı planını kaydettiğinde açıklama: “Seçtiğin saatlerde mola önermek için bildirim izni gerekiyor.” Ardından Android 13+ sistem izni. Reddedildiğinde form kaybı yoktur; manuel kullanım devam eder. Sistem izin penceresinin kapatılması onay sayılmaz. [Android bildirim izni](https://developer.android.com/develop/ui/compose/notifications/notification-permission)

### 19.3 Açılış davranışı

Başla eylemi doğrudan Activity PendingIntent kullanır; alıcı/servis üzerinden ekran açan bildirim trampoline kurulmaz. Uygulama arka plandan tam ekran açmaya çalışmaz. [Android arka plan Activity güvenliği](https://developer.android.com/guide/components/activities/secure-bal), [Android 12 davranışları](https://developer.android.com/about/versions/12/behavior-changes-12)

### 19.4 Sessize alma seçenekleri

Bildirimde üç eylem sınırı korunur. Daha ayrıntılı seçenekler uygulamadaki “Hatırlatmalar” panelindedir: 1 saat, bugün, tamamen kapat. Sessize alma açık durum ve yeniden başlayacak zamanla gösterilir. “Bugün” yerel takvim günüdür; bir sonraki seçili çalışma günü başlangıcıyla normal davranışa döner.

<a id="bolum-20"></a>
## 20. Android mimarisi ve teknoloji seçimi

### 20.1 Platform hedefi

**Önerilen minSdk:** 26; daha eski cihazlar için ihtiyaç araştırması yapılmadan kapsam genişletilmez. **targetSdk:** Araştırma tarihinde yeni telefon uygulamaları için geçerli Google Play gereksinimini karşılayacak şekilde en az API 36. Google'ın sayfası, 31 Ağustos 2026'dan itibaren yeni uygulama/güncellemelerde Android 16/API 36 gerekliliğini bildiriyor. Yayın gününde yeniden kontrol edilir. **compileSdk:** Seçilen kararlı araç zinciriyle uyumlu ve targetSdk'den düşük olmayan sürüm. [Play hedef API koşulları](https://support.google.com/googleplay/android-developer/answer/11926878?hl=en-GB_ALL)

Kotlin, Compose BOM ve Android Gradle Plugin için “en yeni” diye sabit tahmin yazılmaz. Uygulama başladığında birlikte uyumlu kararlı sürümler seçilir, sürüm kataloğunda sabitlenir ve bağımlılık güncellemeleri kontrollü yapılır.

### 20.2 Katmanlar

```text
Compose ekranları
  ↓ UI event / ↑ UiState
ViewModel
  ↓
SessionEngine / ReminderPolicy / RoutineSelector
  ↓
Repository arayüzleri
  ├─ Yerel rutin manifesti ve asset'ler
  ├─ Room: seans, olay ve checkpoint
  └─ DataStore: kullanıcı tercihleri

Android adaptörleri
  ├─ AlarmManager
  ├─ NotificationManager
  ├─ Zaman / yaşam döngüsü
  └─ Ses ve titreşim
```

Durum tek yönde akar; iş mantığı UI bileşenleri içinde dağılmaz. Repository ve ViewModel sınırları Android mimari önerileriyle uyumludur; bu küçük uygulamada gereksiz katmanlar eklenmez. [Android mimari önerileri](https://developer.android.com/topic/architecture/recommendations)

### 20.3 Paket veya modül düzeni

İlk aşamada tek uygulama modülü, iç paketler: `core/design`, `core/time`, `data`, `domain/session`, `domain/reminder`, `feature/home`, `feature/routines`, `feature/session`, `feature/settings`. Ekip ve derleme ihtiyacı büyürse bağımsız Gradle modüllerine ayrılır. Küçük MVP için on beş modüllü yapı zorunlu değildir.

### 20.4 Teknoloji karar tablosu

| Alan | Seçim | Gerekçe |
|---|---|---|
| Dil | Kotlin | Android yerel geliştirme |
| Arayüz | Jetpack Compose + Material 3 | Duruma bağlı ekranlar ve erişilebilir bileşenler |
| Eşzamanlılık | Coroutines / Flow | Yaşam döngüsüyle uyumlu durum akışı |
| Yapılandırılmış kayıt | Room | Seanslar, benzersiz kimlikler, migration |
| Tercihler | DataStore | Küçük ve tutarlı ayar modeli |
| Rutin içerikleri | Sürümlü yerel JSON + asset | Çevrimdışı paket ve doğrulama |
| Yaklaşık öneriler | AlarmManager adaptörü | Kullanıcı planına göre gelecek olay |
| Bakım işleri | Gerektiğinde WorkManager | Zamanı esnek yerel temizlik/uzlaştırma |
| Bağımlılık sağlama | Başlangıçta açık constructor injection | Küçük uygulamada izlenebilirlik |
| Saat | Test edilebilir Clock arayüzü | Saat dilimi ve zaman testleri |
| Ağ | MVP çekirdeğinde gerekmez | Sunucusuz temel ürün |

### 20.5 Adaptif arayüz

Telefon dikeyde tek sütun. Yatay veya geniş pencerede seans rehberi solda, yönerge ve kontroller sağda olabilir. Rutin listesi geniş ekranda liste-ayrıntı düzenine geçebilir. Düzen cihaz adına değil kullanılabilir pencere genişliğine göre seçilir. Pencere değişimi aktif seansı kesmez. [Farklı ekran boyutları](https://developer.android.com/develop/ui/compose/layouts/adaptive/support-different-display-sizes)

### 20.6 Dış bağımlılık politikası

Her SDK için amacı, veri erişimi, lisansı, boyut etkisi ve bakım durumu kaydedilir. Temel uygulamaya reklam, uzaktan analitik ve hata izleme SDK'sı varsayılan olarak eklenmez. Harici kütüphane olmadan çözülebilen küçük görsel işler için yeni izleme yüzeyi oluşturulmaz.

<a id="bolum-21"></a>
## 21. Veri modeli ve saklama

### 21.1 Temel varlıklar

| Varlık | Alanlar | Saklama |
|---|---|---|
| UserPreferences | tema, ses, titreşim, azaltılmış hareket, görülen güvenlik sürümü | DataStore |
| WorkSchedule | seçili günler, yerel saatler, sessiz aralık, interval, enabled, version | DataStore |
| ReminderControl | pausedUntil, mutedLocalDate, sonraki occurrence | Room veya tek tutarlı plan deposu |
| Routine | id, version, dil, pozisyon, bloklar, inceleme durumu | Salt okunur asset |
| Movement | id, fazlar, metinler, asset yolları, uzman sürümü | Salt okunur asset |
| Session | id, routineId/version, başlangıç/bitiş, durum, aktif süreler | Room |
| SessionStep | sessionId, sıra, hareket, oynanan/atlanan süre | Room |
| ActiveCheckpoint | sessionId, durum, adım, elapsed, boot işareti, kayıt zamanı | Room |
| ReminderOccurrence | id, planVersion, hedef, attempt zamanı, action, durum | Room |
| Entitlement | ürün ve doğrulama durumu | Yalnız satın alma sürümünde |

### 21.2 Tutarlılık kuralları

`Session.id` benzersizdir. Bir seansın bitişi ve son adım kayıtları transaction içinde tamamlanır. `ReminderOccurrence.id` iki kez claim edilemez. Ayar sürümüyle oluşturulmuş alarm, başka sürüm altında uygulanmaz. Süre alanları negatif olamaz. Son bitiş başlangıçtan önce görünüyorsa duvar saati değişimi işaretlenir; monotonik aktif süre korunur.

### 21.3 Yerel zaman modeli

Çalışma saatleri `LocalTime`, günler haftanın günleri, yürütme anları `Instant`, hesaplama bölgesi `ZoneId` olarak temsil edilir. Seans istatistiğinde gerçekleştiği yerel tarih ve saat dilimi de tutulur; seyahat sonrası geçmiş kayıtlar başka güne sessizce kaymaz. Saat formatı cihazın 12/24 saat tercihine uyar.

### 21.4 Saklama süreleri

Öneri: Seans ve adım ayrıntıları 90 gün; hatırlatma teknik olayları 7 gün; aktif checkpoint bitişte silinir; tercih kullanıcı silene kadar kalır. Geçmiş ekranı son 30 günü gösterir. Otomatik temizleme uygulama açılışında veya esnek bakım işiyle yapılır. Sonsuz geçmiş vaadi verilmez.

### 21.5 Veri silme

“Geçmişi sil” seansları, adımları ve ilgili yerel özetleri siler. “Tüm yerel verileri sıfırla” alarm/bildirimi iptal eder, çalışan yerel görevleri durdurur, tercihleri ve aktif kayıtları temizler. Silme sürerken kullanıcı aynı anda yeni seans başlatamaz. İşlem başarısızsa başarı mesajı verilmez.

### 21.6 Şema yükseltme

Room migration testleri gerçek eski şemayla yürütülür. Üretimde hata olduğunda tüm geçmişi otomatik silen destructive migration kullanılmaz. Rutin sürümü değişse de eski seansın rutin adı/sürüm bilgisi okunabilir kalır. Eski checkpoint yeni koreografiye doğrudan bağlanmaz; güvenli kısmi bitişe alınır.

<a id="bolum-22"></a>
## 22. Çevrimdışı çalışma ve uygulama yaşam döngüsü

### 22.1 Çevrimdışı sözleşme

Kurulum paketi 12 rutin, onaylı animasyon, sabit poz, Türkçe metin ve varsa temel sesleri içerir. Ana ekran, seans, geçmiş ve takvim düzenleme ağ beklemez. Harici sağlık kaynakları, gizlilik web sayfası ve gelecekteki satın alma işlemleri bağlantı gerektirebilir; gizlilik özeti uygulama içinde de bulunur.

### 22.2 Yaşam döngüsü matrisi

| Olay | Sayaç | Ses/animasyon | Veri |
|---|---|---|---|
| Ekran döner | Devam | Aynı faz | Yeniden başlangıç yok |
| Pencere boyutu değişir | Devam | Yeni yerleşim | Durum korunur |
| Uygulama görünmez olur | Duraklar | Durur | Checkpoint |
| Ekran kilitlenir | Duraklar | Durur | Checkpoint |
| Süreç öldürülür | Son kayıt korunur | Kapalı | Geri dönüş duraklatılmış |
| Cihaz yeniden başlar | Otomatik devam yok | Kapalı | Kısmi / kurtarılabilir |
| Kullanıcı duraklatır | Durur | Aynı poz | Duraklama kaydı |
| App güncellenir | Otomatik devam yok | Kapalı | İçerik sürümü kontrol edilir |

### 22.3 Ekranı açık tutma

Yalnızca görünür ve oynayan seans sırasında pencere seviyesinde ekranı açık tutma kullanılır. Duraklamada makul kısa süre sonrası sistemin ekran kapatma davranışı geri gelir. Sürekli CPU wake lock veya servis çalıştırılmaz. Kullanıcı telefonu kilitlerse kilit açılmaya zorlanmaz.

### 22.4 Bozuk yerel durum

Depolama doluysa seans rehberi çalışabilir; geçmiş yazılamıyorsa “Bu mola geçmişe kaydedilemedi” açıkça gösterilir. Rutin manifesti bozuksa doğrulanmış gömülü güvenli yedek yoksa seans açılmaz. Hata durumunda internetten kontrolsüz içerik indirilmez.

<a id="bolum-23"></a>
## 23. Gizlilik, güvenlik ve veri kontrolü

### 23.1 MVP veri yaklaşımı

Hesap, ad, e-posta, doğum tarihi, konum, kamera, mikrofon ve sağlık geçmişi toplanmaz. Kullanım geçmişi cihazda tutulur. MVP otomatik kullanım verisi göndermez. Kullanıcı geri bildirim vermek isterse hangi bilgi paylaşacağını kendisi seçer.

### 23.2 İzin matrisi

| İzin / yetki | MVP | Amaç / karar |
|---|---|---|
| POST_NOTIFICATIONS | İsteğe bağlı | Hatırlatmalar |
| RECEIVE_BOOT_COMPLETED | Gerekli teknik bildirim | Yeniden başlatma sonrası planı kurma |
| VIBRATE | Yalnız uygulanırsa | İsteğe bağlı hafif geri bildirim |
| ACTIVITY_RECOGNITION | Hayır | Gelecek pilotta ayrı onay |
| SCHEDULE_EXACT_ALARM / USE_EXACT_ALARM | Hayır | Yaklaşık öneri yeterli |
| CAMERA / RECORD_AUDIO | Hayır | Kamera analizi veya kayıt yok |
| Konum / takvim / kişiler | Hayır | Temel iş için gerekmiyor |
| Bildirim erişimi / Accessibility Service | Hayır | Başka uygulamaları izleme yok |
| INTERNET | Çekirdek için gerekmez | Sonraki ağ/ödeme özelliğinde manifest yeniden incelenir |

Manifest birleştirme çıktısı da denetlenir; bir kütüphanenin beklenmedik eklediği izin kaldırılır veya gerekçelendirilir.

### 23.3 Yedekleme ve “cihazda kalır” ifadesi

Android otomatik yedekleme ve cihazdan cihaza aktarım davranışı açıkça yapılandırılmalıdır. Oturum verisi ve teknik olaylar bulut yedeğinden ve desteklenen aktarım kurallarından çıkarılır; eski/yeni Android kuralları birlikte test edilir. Üretici davranışlarının tümüne mutlak garanti verilmez. Gizlilik metni “uygulama sunucularımıza göndermeyiz” gibi doğrulanabilir ifade kullanır. [Android Auto Backup](https://developer.android.com/identity/data/autobackup)

### 23.4 Güvenlik kontrolleri

Uygulama verisi özel depolamadadır. İç alıcılar ve bileşenler gerekmedikçe dışa açılmaz. PendingIntent bayrakları açık belirlenir; kimlikler tahmin edilse bile geçersiz/süresi geçmiş eylem reddedilir. Harici intent parametreleri içerik kimliği açısından doğrulanır. Günlüklerde sağlık notları, satın alma tokenları ve gereksiz cihaz tanımlayıcıları bulunmaz.

İleride sunucu eklenirse TLS, veri minimizasyonu, kimlik doğrulama, sır yönetimi ve saklama süreçleri ayrı tasarlanır. Uygulama içine servis hesabı veya API özel anahtarı gömülmez.

### 23.5 Politika ve açıklık

Sağlık içeriği ve kullanılan izinler doğrultusunda Play sağlık beyanı, Data safety formu ve gizlilik metni birbiriyle tutarlı hazırlanır. Gizlilik politikası erişilebilir bir web sayfasında ve uygulamada bulunur. Sağlık yararı iddiaları gerçek içerik sınırını aşmaz. [Google Play sağlık politikası](https://support.google.com/googleplay/android-developer/answer/16679511?hl=en)

Bu plan hukuki uygunluk görüşü değildir; dağıtım ülkeleri, ticari yapı ve sonraki veri akışlarına göre gerekli mevzuat değerlendirmesi yayın iş paketine eklenir. Henüz doğrulanmamış ülke bazlı hukuki zorunluluklar kesin kural diye yazılmamıştır.

<a id="bolum-24"></a>
## 24. Gelir modeli ve satın alma deneyimi

### 24.1 Öneri

İlk MVP reklamsız ve ücretsiz temel deneyim sunar. Kullanım değeri doğrulandıktan sonra **tek seferlik Plus** denenir. Ürünün düzenli sunucu maliyeti ve sürekli yüksek hacimli içerik üretimi yokken zorunlu abonelik eklemek güçlü bir gerekçe gerektirir.

### 24.2 Ücretsiz / Plus sınırı

| Ücretsiz kalır | Plus adayı |
|---|---|
| 12 temel rutin | Yeni bağlama göre ek rutin paketleri |
| Standart çalışma takvimi | Birden fazla çalışma profili |
| Bütün seans kontrolleri | Ek ses seçenekleri |
| Metin, erişilebilirlik ve güvenlik | İsteğe bağlı kişisel rutin sıralama |
| Temel çevrimdışı kullanım | Genişletilmiş yerel geçmiş seçenekleri |
| Veri silme ve gizlilik | Kozmetik tema seçenekleri |

Güvenli hareket alternatifi ve erişilebilirlik için ödeme istenmez. Ücretsiz içerik sonradan kilitlenmez. “Çevrimdışı” ana değer olduğundan yalnız ücretli kullanıcılara ayrılmaz.

### 24.3 Reklam seçeneğinin değerlendirilmesi

Fotoğraftaki ads önerisi otomatik uygulanmaz. Reklam dikkati böler, SDK ve veri yönetimi maliyeti getirir, çevrimdışı deneyimle çatışabilir. Seans öncesi geçiş reklamı, seans içinde banner, mola sonrası zorunlu video ve bildirim reklamı ürün ilkelerine aykırıdır. Gelir yetersizse önce fiyat/değer sınanır; reklam eklemek varsayılan çözüm değildir.

### 24.4 Satın alma durumları

Yerel fiyat Google Play ürün ayrıntısından alınır; sabit para tutarı arayüze gömülmez. Durumlar: yükleniyor, hazır, ödeme açılıyor, beklemede, satın alındı, iptal, başarısız, geri yükleniyor. Kullanıcı iptali hata veya suçlayıcı uyarı değildir. Bekleyen işlem tamamlanmadan Plus açılmaz.

Tamamlanan alım doğrulandıktan sonra hak verilir ve uygun acknowledgement akışı uygulanır. Billing'in bu iş akışları güncel resmî kılavuz üzerinden uygulanır. [Play Billing entegrasyonu](https://developer.android.com/google/play/billing/integrate)

### 24.5 Hesapsızlık ve satın alma doğrulaması

“Hesap yok”, uygulama hesabı yok demektir; Play satın alma kullanıcının Play hesabına bağlı olabilir. Kullanıcı satın almaları geri yükleyebilmelidir. Güçlü iade/iptal doğrulaması için V1.1'de küçük bir hak doğrulama servisi değerlendirilebilir; bu durum veri akışı ve gizlilik metnini değiştirir.

Yalnız istemci doğrulaması seçilirse çevrimdışı önbellek ve iade gecikmesi riski açık teknik borç olarak kabul edilir. Ağ yok diye daha önce doğrulanmış temel haklar aniden kilitlenmez. Uygulama silinip yeniden kurulursa yeni satın alma/geri yükleme için bağlantı gerekebilir. Alınan içerik lisansı ile indirilen dosyanın bulunması ayrı durumdur.

### 24.6 Fiyat araştırması

Bu belgede doğrulanmamış güncel fiyat tahmini verilmez. Beta kullanıcılarına değer algısı sorulur; en az iki fiyat/değer paketi test edilir. Karar; ödeme dönüşümü, iade, destek yükü ve kullanıcı güveniyle birlikte değerlendirilir. Gelir hesabı: ücretli kullanıcı × net birim gelir − içerik üretimi − doğrulama/işletim − destek. Vergi ve mağaza komisyonu ilgili gerçek hesap koşullarından alınır.

<a id="bolum-25"></a>
## 25. Ürün dili ve hazır arayüz metinleri

### 25.1 Dil ilkeleri

Türkçe doğal, kısa ve sakin olmalıdır. Teknik API adları kullanıcı ekranlarına taşınmaz. “Optimal mobilite protokolün hazır” yerine “Molan hazır” kullanılır. Kullanıcıya her ekranda adla seslenmek için isim istenmez. Sağlık iddiaları, başarı baskısı ve emir kipinin sert kullanımı sınırlandırılır.

### 25.2 Metin kataloğu

| Yer | Önerilen metin |
|---|---|
| Karşılama başlığı | Çalışma gününe küçük bir mola. |
| Karşılama açıklaması | Üç dakikalık, sade hareket rehberleri. Hesap açmadan başlayabilirsin. |
| Ana eylem | 3 dakikalık mola |
| İlk kullanım | İlk molamı dene |
| Plan daveti | Molalarını hatırlatalım mı? |
| Plan açıklaması | Çalışma saatlerini seç. Uygun olduğunda mola önerelim. |
| Zaman bilgisi | Sonraki hatırlatma: yaklaşık 11.00 |
| Mesai dışında | Hatırlatmalar seçtiğin çalışma saatlerinde devam edecek. |
| Bugün sessizde | Bugün hatırlatmalar sessizde. |
| İzin kapalı | Hatırlatmalar için bildirim izni kapalı. |
| İzin düzeltme | Bildirim ayarlarını aç |
| Hazırlık | Telefonunu görebileceğin bir yere bırak ve rahatça yerleş. |
| Duraklama | Molan duraklatıldı. |
| Devam | Devam et |
| Atlama | Atla |
| Bitirme paneli | Molayı burada bitirebilirsin. |
| Tamamlandı | Molan tamamlandı. |
| Kısmi tamamlandı | Bugün kendine kısa bir ara verdin. |
| Ana ekrana dönüş | Günüme dön |
| Boş geçmiş | İlk molan burada görünecek. |
| Ağ gerektiren bağlantı | Bu sayfayı açmak için internet bağlantısı gerekiyor. |
| Geçmiş yazılamadı | Bu mola geçmişe kaydedilemedi. |
| Animasyon yedeği | Rehber sabit adımlarla gösteriliyor. |
| Silme başlığı | Mola geçmişi silinsin mi? |
| Silme açıklaması | Bu cihazdaki mola kayıtları silinecek. İşlem geri alınamaz. |
| İzin reddi sonrası | İstersen molalarını kendin başlatabilirsin. |

### 25.3 Kaçınılacak dil

“Yine kaçırdın”, “Serini mahvettin”, “Hemen ayağa kalk”, “Boyun ağrına kesin çözüm”, “Doktora ihtiyacın yok”, “Hareketsizliğini tespit ettik”, “Alarmın kesin çalacak”, “Sağlık puanın düşük”.

### 25.4 Yerelleştirme hazırlığı

Bütün metinler kaynak dosyasında tutulur. Süre ve sayılar string birleştirmeyle yazılmaz; çoğul ve yerel sayı/saat formatı kullanılır. Animasyonun içine Türkçe yazı gömülmez. İkinci dilde metnin %30 uzaması ve sağdan sola yerleşim altyapısı tasarımda hesaba katılır; MVP çeviri kapsamı Türkçedir.

<a id="bolum-26"></a>
## 26. Performans, pil ve kalite bütçeleri

Aşağıdaki değerler doğrulanmış sonuç değil, geliştirme sırasında ölçülecek başlangıç hedefleridir. Donanım, Android sürümü, ekran yenileme hızı ve test yöntemi her raporda belirtilir.

| Alan | Başlangıç hedefi | Ölçüm |
|---|---|---|
| Soğuk açılış | Orta sınıf referans cihazda p95 < 2 saniye kullanılabilir ana ekran | Tekrarlı Macrobenchmark |
| Manuel başlatma | Dokunmadan hazırlık görüntüsüne p95 < 300 ms, sıcak uygulama | Trace + cihaz kaydı |
| Seans zaman hatası | Duraklatmasız 180 saniyede < 250 ms mantıksal sapma | Test saati + gerçek cihaz |
| Animasyon | Referans 60 Hz cihazda belirgin takılma yok; ölçülen jank < %1 hedef | Frame timing |
| Kurulum/indirme | Temel paket için 35 MB altında ilk hedef | Release AAB boyut raporu |
| Bellek | Referans cihazda seans RSS için 150 MB altı başlangıç bütçesi | Profiler |
| Pil | 8 saat planlı kullanımda eşlenmiş boşta teste göre medyan < 1 yüzde puan ek kayıp hedefi | Aynı cihazda tekrarlı A/B |
| Arka plan | Sürekli servis, döngüsel sensör ve sürekli wake lock yok | Sistem izleri |
| Kararlılık | Beta hedefi ≥ %99,5 crash-free session | İzinli/uygun raporlama ve test |

Pil yüzdesi kaba ölçümdür; tek bir cihazın tek günlük farkından sonuç çıkarılmaz. Ekranı açık seans tüketimi ve arka plan tüketimi ayrı raporlanır. Bildirim gecikmeleri hata saklamak için ortalamaya gömülmez; p50/p95 ve üretici dağılımı gösterilir.

### 26.1 Performans uygulama kuralları

Animasyon dosyaları ilk kareyi geciktirmeyecek şekilde hazırlanır. Büyük varlıklar sadece gerektiğinde açılır. Her saniyelik sayaç bütün ekranı gereksiz yeniden çizmez. Disk ve içerik okuma ana iş parçacığını bloke etmez. Seans boyunca ağ isteği yapılmaz. Tüm görsel efektler düşük özellikli referans telefonda denenir.

<a id="bolum-27"></a>
## 27. Ölçüm planı ve deneyler

### 27.1 Hesapsız ölçüm sınırı

MVP otomatik telemetri göndermez. Bu nedenle küresel D7/D30 elde tutma, bildirim açılma ve kişi bazlı kullanım oranları kendiliğinden biliniyor sayılamaz. İlk pilot, gönüllü kullanıcı görüşmeleri ve kullanıcının isteğiyle paylaşacağı sınırlı yerel test özetleriyle yürütülür. Mağazanın sağladığı toplu teknik göstergeler erişilebildiği ölçüde ayrı kullanılır.

### 27.2 Yerel olay sözlüğü

| Olay | Asgari alanlar | Anlam |
|---|---|---|
| onboarding_completed | sürüm | İlk giriş tamamlandı |
| session_started | sessionId, routineId, kaynak | Rehber başladı |
| session_paused | neden sınıfı | Kullanıcı veya görünürlük |
| step_skipped | movementId, sıra | Hareket atlandı |
| session_finished | durum, active süre | Tam/kısmi sonuç |
| reminder_attempted | occurrenceId, zaman farkı | Bildirim oluşturma girişimi |
| reminder_action | başla/ertele/sessiz | Kullanıcının bildirim eylemi |
| permission_state_changed | izin açık/kapalı | Uygulama tarafından gözlenen durum |
| local_write_failed | hata sınıfı | İçerik değil teknik hata |

“Bildirim görüldü” olayı, yalnızca sistem çağrısı başarılı diye üretilmez. Ekranda gerçekten okunma ölçülmüyorsa görüntülenme oranı raporlanmaz. Yerel olayların dışarı aktarımı varsayılan kapalıdır; hassas serbest metin içermez.

### 27.3 Metrik tanımları

- **İlk mola dönüşümü:** Pilot içinde ilk seansı başlatan / ilk açılışa katılan kişi.
- **Tam akış oranı:** Atlamasız biten seans / başlayan seans. Hazırlıktan hemen çıkış ayrıca görülür.
- **Kısmi mola oranı:** Atlayarak veya erken biten / başlayan seans.
- **Bildirim eylem oranı:** Eylem gelen occurrence / oluşturma girişimi. Bu, gerçek görülme oranı değildir.
- **Uygunluk değerlendirmesi:** Kullanıcının örnek bildirimleri “uygun zamanda” değerlendirmesi.
- **Bildirim yükü:** Kişi başına öneri sayısı, erteleme ve sessize alma.
- **Güvenli çıkış:** Kullanıcının rahatsızlık anında durdurma kontrolünü yardımsız bulması.
- **Tekrar kullanım:** Pilot takibinde sonraki çalışma haftasında en az bir mola; ölçüm kapsamı açık belirtilir.

### 27.4 İlk pilot için karar eşikleri

Bunlar sektör standardı değildir. İlk tasarım turunda 5–8 kişiyle hata bulma, daha sonra 20–30 kişiyle 2 haftalık saha pilotu önerilir. Küçük örneklemde istatistiksel etkinlik iddiası yapılmaz.

İlk seans başlatma görevinde en az %80 yardımsız başarı; durdurma görevinde katılımcıların tamamının kontrolü bulması; kritik metinlerde “oturmayı ölçüyor” yanılgısı olmaması hedeflenir. Hedef tutmazsa özellik eklemek yerine akış düzeltilir.

### 27.5 Deney sırası

1. Ana eylem metni: “3 dakikalık mola” mı “Molaya başla” mı daha anlaşılır?
2. İlk mola önce mi, çalışma planı önce mi daha az yük yaratıyor?
3. 60/90 dakika öneri aralığı hangi iş bağlamında daha kabul edilebilir?
4. Ana ekranda yalnız sonraki öneri mi, kısa haftalık özet de mi yararlı?
5. Sessiz animasyon ve kısa sesli rehberin kullanım kolaylığı.

Aynı anda renk, süre, metin ve bildirim sayısı değiştirilmez. Sağlık güvenliği sınırları dönüşüm artırma deneyi yapılacak alan değildir.

<a id="bolum-28"></a>
## 28. Kullanıcı araştırması ve kullanılabilirlik testi

### 28.1 Kod öncesi görüşmeler

6–8 yetişkin masa başı çalışanıyla: En son ne zaman mola verdin? Neyi kullanıyorsun? Bildirimleri neden kapatırsın? Masada ayağa kalkmak veya ses açmak mümkün mü? Üç dakika gerçekçi mi? Uygulamaya hangi verileri vermek istemezsin? Sorular ürün fikrini övecek yanıt üretmek yerine mevcut davranışı anlamalıdır.

### 28.2 Prototip görevleri

| Görev | Gözlenecek sorun |
|---|---|
| Hesap açmadan ilk molayı başlat | Gereksiz kararlar, belirsiz eylem |
| Sesi açmadan hareketi anla | Görsel/yönerge yeterliliği |
| Bir hareketi atla | Kontrol bulunabilirliği |
| Seansı hemen durdur | Yanlış dokunma ve çıkış engeli |
| Salı/Perşembe 10.00–17.00 planla | Form anlaşılabilirliği |
| Bugün bildirim isteme | Geçici/kalıcı kapatma ayrımı |
| İzin reddedip mola yap | İzin bağımlılığı |
| Büyük yazıyla aynı görevleri yap | Taşma, kayıp düğme |
| Geçmişi sil | Sonucun anlaşılması |

### 28.3 Masa üstü kullanım testi

Telefon yaklaşık kol mesafesinde, elde tutulmadan değerlendirilir. Görsel açı, ışık, telefon dayanağı ve sesli/sessiz ortam değişir. Kullanıcı telefon ekranına yaklaşmak zorunda kalıyorsa sayaçtan önce yönerge ve hareket büyütülür. Hareket uygunluğu ve fiziksel uygulama değerlendirmesi uzman gözetimli çalışmadan ayrı “kullanılabilirlik başarısı” diye sonuçlandırılmaz.

### 28.4 Araştırma çıktısı

Her sorun: görev, gözlem, etki, öncelik, önerilen düzeltme, yeniden test sonucu. “Kullanıcı beğendi” tek başına yeterli değildir. Katılımcı alıntıları izinle ve kimliksiz kullanılır. Uygulamayı kullanan kişilerin tıbbi ayrıntıları sıradan tasarım notlarına yazılmaz.

<a id="bolum-29"></a>
## 29. Test stratejisi ve kabul senaryoları

### 29.1 Test katmanları

**Birim:** Seans durumu, süre hesabı, hatırlatma uygunluğu, tarih/saat, içerik manifesti. **Entegrasyon:** Room işlemleri, migration, DataStore ve plan sürümü, alarm alıcısı. **Arayüz:** Başlat/atla/duraklat/bitir, geri, izin reddi, ayarlar. **Cihaz:** Doze, pil kısıtı, reboot, süreç ölümü, ses odağı. **İçerik:** Uzman kontrolü ve doğru metin-görsel eşleşmesi.

### 29.2 Asgari cihaz matrisi

| Grup | Kapsam |
|---|---|
| Eski desteklenen sürüm | API 26 emülatör ve mümkünse fiziksel cihaz |
| Alarm sınırı | API 31/32 |
| Bildirim izni | API 33 |
| Yeni sistem davranışları | API 34/35/36 |
| Referans Android | Pixel/AOSP davranışına yakın fiziksel cihaz |
| Üretici özelleştirmesi | En az bir Samsung ve bir Xiaomi/benzeri kısıtlı pil yönetimi cihazı |
| Düşük kaynak | Düşük/orta RAM ve 60 Hz ekran |
| Geniş ekran | Tablet/katlanabilir emülatör veya cihaz |
| Erişilebilirlik | TalkBack, Switch Access, büyük font, hareket azaltma |

Marka belirtilmesi garanti veya olumsuz genelleme değildir; farklı arka plan davranışlarını örnekleme amacındadır. Yayın raporunda gerçekten test edilen model ve yazılım sürümü yazılır.

### 29.3 Öncelikli kabul senaryoları

| Test | Verilen / eylem | Beklenen sonuç |
|---|---|---|
| T01 | Yeni kurulum, internet kapalı | İlk seans başlayabilir |
| T02 | Bildirim izni reddedilir | Manuel rutinler eksiksiz çalışır |
| T03 | Ana düğmeye hızlı çift dokunulur | Tek aktif seans |
| T04 | 180 saniye kesintisiz seans | Süre doğru, tek sonuç kaydı |
| T05 | 20 saniye duraklatılır | Aktif süreye eklenmez |
| T06 | İkinci hareket atlanır | Kalan süre azalır, kısmi etiket |
| T07 | Son hareket atlanır | Kapanış, sonra kısmi sonuç |
| T08 | Hazırlık atlanır | Hazırlık süresi çıkar, hareket tamamlanması ayrı tutulur |
| T09 | Seans içinde geri | Sayaç durur; çıkış seçimi gelir |
| T10 | Geri jesti iptal edilir | Seans kaybolmaz |
| T11 | Uygulama arka plana alınır | Rehber duraklar |
| T12 | Ekran döndürülür | Adım/süre korunur |
| T13 | Süreç öldürülüp açılır | Son checkpoint'ten duraklatılmış geri dönüş |
| T14 | Sistem saati seans sırasında değişir | Aktif süre sapmaz |
| T15 | Gün değişimi / saat dilimi | Eski bildirim kopyalanmaz |
| T16 | Çalışma saati dışında alarm gelir | Bildirim bastırılır |
| T17 | Sessiz aralıkta alarm gelir | Yeni uygun zaman hesaplanır |
| T18 | Plan değişir, eski alarm gelir | Sürüm uyuşmazlığı nedeniyle etkisiz |
| T19 | Aynı occurrence iki kez alınır | Kopya öneri oluşmaz |
| T20 | Bildirimden erteleme | Tek yeni hedef; eski bildirim kapanır |
| T21 | Bugün sessize al | Normal ve ertelenmiş öneri kapanır |
| T22 | Manuel mola sonrası eski alarm | Yeni aralık dolmadan bildirim yok |
| T23 | Doze'da gecikmiş alarm | Eski molalar üst üste gösterilmez |
| T24 | Telefon yeniden başlar | Gelecek plan geri gelir, geçmiş birikmez |
| T25 | Force-stop ve yeniden açılış | Plan uzlaştırılır, sahte garanti verilmez |
| T26 | Kanal kapalı, uygulama izni açık | Doğru sorun açıklanır |
| T27 | Ses varlığı bozuk | Metinle devam, çökme yok |
| T28 | Animasyon bozuk | Sabit rehber veya güvenli duraklama |
| T29 | Depolama dolu | Yazılamayan geçmiş açık belirtilir |
| T30 | Bütün yerel veriler silinir | Alarm, checkpoint, geçmiş ve ayarlar temizlenir |
| T31 | 200% font ve dar ekran | Kritik kontroller ulaşılabilir |
| T32 | TalkBack ile seans | Sürekli sayaç duyurusu yok; bitirme bulunabilir |
| T33 | Rutin sürümü değişir | Eski kayıt okunur; checkpoint körlemesine sürmez |
| T34 | Bildirimde eski rutin kimliği | Güvenli giriş; çökme yok |
| T35 | App görünürken alarm | Aktif seansta kesinti yok |
| T36 | Hiç çalışma günü seçilmez | Açıklayıcı form doğrulaması |
| T37 | Koyu tema, renk körlüğü kontrolü | Durum yalnız renge bağlı değil |
| T38 | Satın alma beklemede, V1.1 | Hak erken açılmaz |
| T39 | Satın alma iptal, V1.1 | Temel mola etkilenmez |
| T40 | Satın alma geri yükleme, V1.1 | Doğrulanmış hak tekrar erişilir |

### 29.4 Otomatik içerik doğrulaması

Her release'de: 12 benzersiz rutin; tüm hareket referansları mevcut; toplam süre 180000 ms; taraf fazları blok süresini aşmıyor; negatif veya sıfır zorunlu süre yok; metin/animasyon/sabit yedek mevcut; yalnız onaylı içerik; kullanılmayan büyük asset raporu; Türkçe kaynak anahtarları eksiksiz. Yanlış içerik paketi derlemeden geçemez.

### 29.5 Yayını durduran kusurlar

Yanlış anatomik yön; görünmez seansın yapılmış sayılması; bitirme kontrolünün erişilememesi; sürekli/çoğalan bildirim; izinsiz veri gönderimi; seans başlatmada çökme; sessiz saati ihlal eden tekrarlanabilir hata; yıkıcı migration; temel çevrimdışı akışın çalışmaması. Küçük hizalama farkları aynı öncelikte değildir.

<a id="bolum-30"></a>
## 30. Google Play ve yayın hazırlığı

### 30.1 Mağaza paketi

Uygulama adı için marka ve benzer ad kontrolü; ikon; kısa/uzun açıklama; gerçek uygulamadan ekran görüntüleri; destek iletişimi; erişilebilir gizlilik web sayfası; içerik derecelendirmesi; sağlık uygulaması beyanı; Data safety; reklam ve satın alma beyanları; imzalı AAB; sürüm notları. Bu plan bir geliştirici hesabı oluşturmaz veya uygulamayı yayımlamaz.

### 30.2 Açıklama taslağı

**Kısa açıklama:** “Çalışma gününe sığan 3 dakikalık, çevrimdışı hareket molaları.”

**Uzun açıklamanın sırası:** Ne yapar → üç dakikalık rehber → 12 rutin → kişisel çalışma saatlerinde yaklaşık hatırlatma → hesap gerektirmeyen çevrimdışı kullanım → kullanıcı kontrolü → sağlık sınırı. Sensörle kesin oturma takibi veya tedavi vaadi yazılmaz.

### 30.3 Ekran görüntüsü hikâyesi

1. “Kendine 3 dakika ayır” — Mola ana ekranı.
2. “Hareketi kolayca takip et” — Büyük animasyon ve yönerge.
3. “Çalışma saatlerine göre” — Plan sayfası.
4. “Oturarak veya ayakta” — Rutin listesi.
5. “Hesap açmadan, çevrimdışı” — Gerçek temel kullanım ekranı.

Mağaza görsellerinde uygulamada olmayan özellik gösterilmez. Önizleme tasarımları yayımlanmış ürün ekranı gibi kullanılmaz.

### 30.4 Güncel platform kapıları

Araştırma tarihi itibarıyla API 36 hedefi planlanır; yükleme gününde Play koşulları yeniden açılıp kontrol edilir. Yeni kişisel geliştirici hesaplarında hesabın durumuna göre en az 12 test kullanıcısının 14 gün kesintisiz katıldığı kapalı test ve üretim erişimi başvurusu gerekebilir. Bunun her hesaba aynı biçimde uygulandığı varsayılmaz. [API koşulları](https://support.google.com/googleplay/android-developer/answer/11926878?hl=en-GB_ALL), [Yeni kişisel hesap test koşulları](https://support.google.com/googleplay/android-developer/answer/14151465?hl=en-GB)

### 30.5 Aşamalı yayın

İç test → kapalı test → yayın kapıları → düşük oranlı üretim dağıtımı → teknik gözlem → kademeli artış. Yeni uygulamada hesabın sunduğu dağıtım seçenekleri doğrulanır; oranlar Console imkânları ve örneklem büyüklüğüne göre seçilir. Yayın tarihi mağaza incelemesinden kesin geçiş garantisi değildir.

### 30.6 Kaynak ve hak denetimi

Animasyon, ikon, seslendirme, yazı tipi ve içerik lisansları belgelenir. Rakip markaları uygulama adına veya yanıltıcı anahtar kelimelere dönüştürülmez. Uzman adı ancak izin ve gerçek katkı varsa kullanılır; “uzman onaylı” metni somut değerlendirme kaydıyla desteklenir.

<a id="bolum-31"></a>
## 31. İş paketleri ve bağımlılıklar

| Paket | Teslim | Bağımlılık | Kabul / sahibi rolü |
|---|---|---|---|
| WP01 Ürün çerçevesi | Kapsam, ilkeler, kullanıcı hipotezleri | Yok | Ürün sorumlusu |
| WP02 Teknik deney | 180 sn motor + gerçek cihaz alarm denemesi | WP01 | Android geliştirici; sınırlar belgeli |
| WP03 Akış prototipi | İlk kullanım, mola, plan, çıkış | WP01 | Tasarımcı; görev testleri |
| WP04 İçerik taslağı | 16 hareket adayı, 12 sıra | WP01 | İçerik + uzman |
| WP05 Görsel rehber denemesi | İki hareket, statik yedek | WP04 | Tasarım/animasyon + uzman |
| WP06 Tasarım sistemi | Bileşenler ve iki tema | WP03 | Tasarımcı; kontrast ve font kontrolü |
| WP07 Uygulama iskeleti | Gezinme, depo, tercih | WP02, WP06 | Android geliştirici |
| WP08 Seans motoru | Süre, pause, skip, lifecycle | WP02, WP07 | Birim ve cihaz testleri |
| WP09 Hatırlatıcı | Takvim, izin, receiver, idempotency | WP02, WP07 | Zaman senaryoları |
| WP10 İçerik üretimi | 12 onaylı rutin, bütün varlıklar | WP04, WP05 | Uzman + animasyon |
| WP11 Geçmiş/gizlilik | Yerel kayıt, silme, yedek kuralları | WP07, WP08 | Veri testleri |
| WP12 Erişilebilirlik | TalkBack, büyük yazı, statik rehber | WP06–WP10 | QA + kullanıcı testi |
| WP13 Beta | Cihaz matrisi, saha gözlemleri | WP08–WP12 | QA/ürün |
| WP14 Mağaza hazırlığı | Beyanlar, metin, AAB | WP10–WP13 | Ürün + yayın sorumlusu |
| WP15 İlk yayın | İzleme ve destek planı | WP14 | Yayın kapıları tamam |
| WP16 Plus | Billing + hak yönetimi | MVP'de değer doğrulama | Ayrı V1.1 kapsamı |

**Kritik yol:** Teknik zamanlama deneyi ve içerik uzman onayı erken başlamalıdır. Görsel tasarımı bitirip sonra Android'de bildirim garantisi olmadığını fark etmek; bütün animasyonları üretip sonra hareketlerin düzeltilmesi gerekmek en pahalı tekrar iş riskleridir.

### 31.1 Örnek geliştirilebilir iş kartları

**DEV-01 / Seans zaman motoru:** Sahte saatle 180 saniyeyi üret; duraklama dışla; atlama hesapla; idempotent bitiş. Kabul: T03–T08, T14.

**DEV-02 / Plan politikası:** Gün/saat/sessiz aralık ve günlük bütçeyle sonraki uygun zamanı hesapla. Kabul: T15–T18, T36; yaz/kış saati testleri.

**DEV-03 / Bildirim köprüsü:** Tek occurrence, eylemler ve izin durumu. Kabul: T19–T26, T34–T35.

**DES-01 / Seans ekranı:** Dar ve geniş ekran, iki tema, 200% yazı, odak sırası. Kabul: Kritik kontroller kaybolmuyor; durdurma yardımsız bulunuyor.

**CNT-01 / R01 onayı:** Metin, poz, faz, süre, statik alternatif, uzman kayıtları. Kabul: Draft yok, toplam 180 saniye, kaynak ve hak bilgisi mevcut.

**QA-01 / Yaşam döngüsü:** Rotasyon, kilit, süreç ölümü, güncelleme, reboot. Kabul: T11–T14, T24, T33.

<a id="bolum-32"></a>
## 32. Takvim, ekip ve maliyet yaklaşımı

### 32.1 Fotoğraftaki iki haftanın gerçekçi karşılığı

**İki haftada beklenebilir çıktı:** Tıklanabilir tasarım, bir veya birkaç taslak rutin, çalışan seans motoru, basit takvim ve gerçek cihazda yaklaşık hatırlatma prototipi.

**İki haftada güvenle vaat edilmemesi gereken çıktı:** 12 uzman onaylı özgün animasyonlu rutin, geniş cihaz testi, eksiksiz erişilebilirlik, mağaza beyanları ve kesin üretim yayını. Kişisel hesap kapalı test gereksinimi bile takvime ayrıca süre ekleyebilir.

### 32.2 Önerilen 8 haftalık temel senaryo

Varsayım: Bir deneyimli Android geliştirici; yarı zamanlı ürün/tasarım desteği; erişilebilir uzman; animasyon desteği ve test kullanıcıları. İşler rol düzeyinde örtüşebilir. Bu çizelge teklif veya kesin teslim garantisi değildir.

| Hafta | Ana çıktı | Çıkış kapısı |
|---|---|---|
| 1 | Araştırma, akış taslağı, alarm ve seans deneyi | Teknik varsayımlar gerçek cihazda sınandı |
| 2 | Tasarım sistemi, ilk rutin prototipi, hareket taslağı | İlk kullanılabilirlik turu; uzman ilk değerlendirmesi |
| 3 | Uygulama iskeleti, seans kontrolleri, yerel veri | Temel çevrimdışı seans çalışıyor |
| 4 | Plan, bildirim, sessize alma, lifecycle | Kritik zamanlama testleri geçiyor |
| 5 | 12 rutin üretimi/entegrasyonu, geçmiş ve ayarlar | İçerik onayları tamamlanmaya yaklaşıyor |
| 6 | Erişilebilirlik, performans, cihaz testleri | P0 kusurlar kapandı; beta adayı |
| 7 | Kapalı beta, mağaza materyali, düzeltmeler | Gerçek kullanım verisi ve teknik rapor |
| 8 | Beta devamı, final kontrol, yayın başvurusu | Tüm yayın kapıları; inceleme süresi ayrıca |

İçerik yetişmez veya testte ana akış değişirse 10–12 haftaya uzama makuldür. Tek kişinin tasarım, animasyon, kod, içerik ve QA'yı yaptığı senaryoda toplam süre daha da uzayabilir. Süreyi korumak için güvenlik ve erişilebilirlik çıkarılmaz; Plus gibi sonraki sürüm işleri ertelenir.

### 32.3 İlk iki haftanın günlük taslağı

| İş günü | Çıktı |
|---|---|
| 1 | Kapsam kararları, görüşme soruları, teknik risk listesi |
| 2 | Yaklaşık alarm ve bildirim izni deneyi |
| 3 | Seans süresi, duraklama ve atlama deneyi |
| 4 | Ana ekran ve seans düşük ayrıntılı tasarım |
| 5 | Çalışma planı ve geri/çıkış akışı |
| 6 | İlk uzman içerik değerlendirmesi; çizim taslağı |
| 7 | Bir rutinin çevrimdışı oynatılması |
| 8 | Yaşam döngüsü ve büyük yazı düzeltmeleri |
| 9 | 5–8 kişiyle görev testi, kritik sorun toplama |
| 10 | Prototip düzeltmesi, üretim takvimi ve kapsam onayı |

### 32.4 İş gücü tahmini

| İş | Kişi-gün aralığı |
|---|---:|
| Ürün/araştırma | 3–5 |
| UX/UI | 5–8 |
| Android temel geliştirme | 18–28 |
| İçerik yazımı ve uzman iterasyonları | 4–8 |
| Animasyon/sabit görsel üretimi | 6–12 |
| QA, erişilebilirlik, düzeltmeler | 6–10 |
| Mağaza ve yayın hazırlığı | 2–4 |
| **Toplam efor** | **44–75 kişi-gün** |

Takvim süresi efor toplamına eşit değildir; işler farklı kişilerle örtüşebilir. Belirsizlik için ayrıca %20–30 plan rezervi değerlendirilir. Bütçe = rol başına kişi-gün × mutabık kalınan günlük ücret + lisans/uzman/seslendirme + cihaz/test gideri + rezerv. Belirli piyasa ücreti araştırılmadığı için TL tutarı uydurulmamıştır.

<a id="bolum-33"></a>
## 33. Risk kaydı

| Risk | Olasılık / etki | Erken sinyal | Önlem | Sorumlu |
|---|---|---|---|---|
| Telefon sabitliği oturma sanılır | Yüksek / yüksek | Kullanıcı “beni takip ediyor” diyor | Takvim temeli, dürüst dil | Ürün |
| Bildirimler üretici/pil nedeniyle gecikir | Yüksek / yüksek | Cihazlar arasında fark | Yaklaşık vaat, cihaz matrisi, sorun ekranı | Android |
| Bildirim bıkkınlığı | Orta / yüksek | Sessize alma artar | Sınır, sakin metin, kolay kapatma | Ürün |
| Yanlış/anlaşılmaz hareket | Orta / kritik | Uzman veya kullanıcı yönü karıştırır | Çok aşamalı görsel onay | İçerik |
| Rutinler birbirine çok benzer | Orta / orta | “İsimler farklı, içerik aynı” | Bağlam ve sıra iyileştirmesi | İçerik |
| İki haftalık süre baskısı | Yüksek / yüksek | QA ve içerik erteleniyor | Prototip/MVP ayrımı | Ürün |
| Animasyon paketi büyür | Orta / orta | Dosya ve açılış bütçesi aşılır | Erken örnek, varlık yeniden kullanımı | Tasarım/Android |
| Geçmiş hatalı tamamlanır | Orta / yüksek | Arka planda süre sayılır | Tek motor, checkpoint, test | Android |
| İzin kapalıyken çalışmıyor sanılır | Orta / orta | İlk kullanıcı terk ediyor | Manuel akış, açık durum | Tasarım |
| Ticari fark zayıf | Orta / yüksek | Mevcut araç tercih edilir | Erken görüşme, küçük pilot | Ürün |
| Sağlık iddiası mağaza sorunu | Orta / yüksek | Açıklamada tedavi vaadi | Metin/politika kontrolü | Yayın |
| SDK beklenmedik veri yollar | Düşük–orta / yüksek | Manifest/ağ incelemesi | Az bağımlılık, release denetimi | Android |
| Offline içerik sorunu uzaktan giderilemez | Düşük / yüksek | Eski pakette riskli hareket | Güçlü ön inceleme, güncelleme planı | İçerik/Yayın |
| Gelir temel değer oluşmadan eklenir | Orta / orta | İlk seans ödeme engeli | Plus'ı erteleme | Ürün |
| Tasarım büyük fontta bozulur | Orta / yüksek | Düğmeler kaybolur | En başta ölçek testi | Tasarım/QA |

Kritik riskte “bilinen sorun” etiketiyle yayın yapılmaz. Önlem sahibi ve test kanıtı olmadan risk kapatılmış sayılmaz.

<a id="bolum-34"></a>
## 34. Yayın sonrası işletim

### 34.1 İlk 72 saat

Mağaza kaynaklı çökme/ANR göstergeleri, destek geri bildirimi, seans açılışı, izin ve bildirim sorunları kontrol edilir. Küçük örneklemde tek düşük puanla ürün yönü değiştirilmez; tekrar eden işlevsel sorunlar önceliklendirilir. Sağlık içeriğiyle ilgili ciddi geri bildirim ayrı hızlı inceleme alır.

### 34.2 İlk 30 gün

Haftalık: teknik hata eğilimi, bildirim uygunluğu, kullanıcıların bıraktığı akış, erişilebilirlik sorunları. İlk sürümde yeni özellik yağmuru yerine temel akış düzeltmeleri. Plus yalnızca ana değer ve tekrar kullanım için yeterli işaret varsa planlanır.

### 34.3 Destek kategorileri

Bildirim gelmiyor; fazla bildirim; ses; hareket anlaşılmıyor; hareket uygun gelmiyor; veri silme; satın alma (sonraki sürüm); diğer. Destek formunda kişisel sağlık geçmişi zorunlu alan değildir. Teknik rapor paylaşımı önizlenebilir ve kullanıcı tarafından başlatılır.

### 34.4 Olay müdahalesi

| Seviye | Örnek | Müdahale |
|---|---|---|
| Kritik | Hatalı yönlendirme, izinsiz veri, bitirilemeyen seans | Dağıtımı durdur; düzeltme ve içerik incelemesi |
| Yüksek | Yaygın açılış çökmesi, bildirim fırtınası | Yayını genişletme; hotfix |
| Orta | Bazı cihazda gecikme, belirli ses sorunu | Yeniden üret, planlı düzeltme, destek açıklaması |
| Düşük | Görsel hizalama, küçük dil hatası | Sıradaki bakım sürümü |

Mağazada sürüm geri alma imkânı sınırlı olabileceğinden, daha yüksek sürüm kodlu düzeltme paketi hazırlanır. Offline kullanıcıya anında müdahale edilemeyeceği olay planında açık kalır.

### 34.5 Bakım ritmi

Android yeni sürüm davranışları, Play politikaları, bağımlılık güncellemeleri ve içerik uzman gözden geçirmesi düzenli kontrol edilir. İçerik değişikliği yalnız yazılım değişikliği değildir; metin, animasyon, ses ve yedek poz beraber sürümlenir. Bu belge kendi kendine güncellenmez; ürün sorumlusu karar günlüğünü sürdürür.

<a id="bolum-35"></a>
## 35. Karar günlüğü ve açık sorular

### 35.1 Bu planda varsayılan olarak çözülenler

| Konu | Seçilen yön | Yeniden değerlendirme koşulu |
|---|---|---|
| İsim | Esneme Molası çalışma adı | Marka/mağaza isim kontrolü |
| Dil | Türkçe ilk sürüm | İkinci dil için gerçek talep |
| Platform | Native Android | Ayrı iOS talebi ve kaynak |
| Hesap | Yok | Cihazlar arası eşitleme için açık ihtiyaç |
| Algılama | Takvim | Sensör pilotu anlamlı fayda gösterirse |
| Ücret | Temel MVP ücretsiz | Kullanım değeri doğrulanınca |
| Reklam | Yok | Ancak ilkeler ve veri etkisi yeniden değerlendirilirse |
| Rutin süresi | Standart 180 sn | Kullanıcı/uzman geri bildirimi |
| Seans arka planı | Duraklar | Ekransız kullanım ayrı ürün ihtiyacı olursa |
| Geçmiş | Yerel, sınırlı saklama | Açık kullanıcı talebi |
| Çalışma takvimi | Aynı gün içinde tek pencere | Vardiyalı çalışma talebi |
| Ölçüm | Yerel ve gönüllü pilot | Ayrı telemetri tasarımı/onayı |

### 35.2 Geliştirme öncesi netleştirilecekler

- Hangi nitelikli uzman içerik değerlendirmesini üstlenecek?
- Animasyonlar kim tarafından ve hangi lisansla üretilecek?
- Yayıncı hesabı ve dağıtım ülkeleri neler?
- Geliştirici hesabına hangi kapalı test koşulları uygulanıyor?
- Sesli rehber MVP'ye yetişecek mi, V1.1'e mi kalacak?
- Gerçek cihaz testi için hangi modeller mevcut?
- Renk adayları gerçek bileşenlerde kontrast ölçümünü geçiyor mu?
- Çalışma penceresi ve varsayılan aralık pilotta anlaşılır mı?
- Satın alma eklenecekse doğrulama servisi kurulacak mı?

Bu sorular planın hazırlanmasını engellemez; ilgili uygulama veya yayın işinin başlaması için sahiplenilmesi gereken kararlardır. Kullanıcıdan her küçük uygulama detayı için onay istemek yerine belge varsayılanlarıyla ilerlenir; kapsamı, maliyeti ve veri akışını değiştiren kararlar görünür tutulur.

<a id="bolum-36"></a>
## 36. Tasarımcı ve geliştirici teslim paketi

### 36.1 Tasarım teslimleri

- S01–S15 ekranları; MVP ve sonraki sürüm ayrımı görünür.
- Açık ve koyu tema bileşenleri.
- Telefon dar/geniş, yatay ve tablet varyantları.
- Büyük yazı ve azaltılmış hareket örnekleri.
- Normal, boş, hata, izin kapalı, duraklatılmış durumlar.
- Her eylemin hedefi ve geri davranışı.
- Animasyon süreleri, easing kararı ve hareketsiz karşılık.
- Hareket çizimlerinin açı, yön ve faz tanımları.
- Metin kaynak anahtarları ve erişilebilir etiketler.

### 36.2 Geliştirici teslimleri

Derlenebilir kaynak; sürüm kataloğu; ortam kurulum açıklaması; imzalama sırlarının kod dışında tutulması; test komutları; test edilmiş cihaz listesi; içerik doğrulayıcı; şema migration'ları; alarm/seans durum diyagramı; release kontrol listesi; üçüncü taraf lisans listesi. Bu plan mevcut kod teslimi değildir; üretilecek paketin tanımıdır.

### 36.3 İçerik teslimleri

Rutin manifesti, hareket manifestleri, her varlık sürümü, uzman değerlendirme kayıtları, ses/metin eşleştirmesi, statik yedekler, hak/lisans belgeleri ve eksik içerik listesi. Dosya adlarında sadece okunabilir ad değil sabit kimlik de kullanılır; kullanıcıya görünen rutin adının değişmesi teknik kimliği değiştirmez.

### 36.4 Gereksinim izlenebilirliği

| Ana gereksinim | Ekran | Teknik parça | Test |
|---|---|---|---|
| Hesapsız ilk kullanım | S01–S03 | Onboarding/preferences | T01–T02 |
| Üç dakikalık mola | S06–S09 | SessionEngine | T03–T14 |
| 12 offline rutin | S04–S06 | ContentRepository | İçerik doğrulama, T27–T28 |
| Çalışma ritmi | S10, S14 | ReminderPolicy/Scheduler | T15–T26 |
| Kullanıcı kontrolü | S07–S08 | Seans ve hatırlatma olayları | T05–T10, T20–T21 |
| Yerel geçmiş | S12 | SessionRepository | T29–T30, T33 |
| Sadelik/erişilebilirlik | Bütün ekranlar | Design system/semantics | T31–T32, T37 |
| Gizlilik | S13 | Veri silme/manifest/backup | T30 + release incelemesi |
| Plus, V1.1 | S15 | Billing/entitlement | T38–T40 |

<a id="bolum-37"></a>
## 37. İlk uygulama sırası

1. R01 için tek rutin ve içerik manifestini taslak olarak oluştur.
2. Sahte saatle seans motorunu ve süre kurallarını doğrula.
3. Fiziksel cihazda alarm/izin/Doze denemesini yap; gecikme sınırlarını kaydet.
4. S03 ana ekran ve S06 seans ekranını tasarım tokenlarıyla kur.
5. Duraklatma, atlama, bitirme ve süreç geri dönüşünü tamamla.
6. Çalışma planı ve bildirim eylemlerini ekle.
7. İlk hareket görsellerini uzmana incelet; motor kararını netleştir.
8. Rutin listesini ve ayrıntıyı bağla; ardından 12 onaylı rutini entegre et.
9. Geçmiş, ayarlar, veri silme ve gizlilik ekranlarını tamamla.
10. Erişilebilirlik, farklı ekranlar ve üretici cihaz testlerini bitir.
11. Kapalı beta ve mağaza işlerini yürüt.
12. Tüm yayın kapıları geçildiğinde dağıtım başvurusunu hazırla.

Bu sıra, görsel olarak tamamlanmış ama gerçek Android koşullarında çalışmayan bir ürün üretme riskini azaltır. Her aşama somut, çalıştırılabilir ve incelenebilir çıktı üretir.

<a id="bolum-38"></a>
## 38. Tamamlanma kontrol listesi

### Ürün ve deneyim

- [ ] İlk açılışta hesap veya ödeme engeli yok.
- [ ] Ana ekranda tek baskın başlatma eylemi var.
- [ ] Rutin seçmeden varsayılan mola başlayabiliyor.
- [ ] Bildirim reddedilince manuel deneyim eksiksiz.
- [ ] Geri, duraklatma, atlama ve bitirme davranışları tutarlı.
- [ ] Ceza, seri kaybı ve suçlayıcı dil yok.

### İçerik

- [ ] 12 rutin anlamlı biçimde ayrışıyor.
- [ ] Her rutin ve hareketin güncel uzman değerlendirmesi var.
- [ ] 180 saniye hesabı bütün rutinlerde doğru.
- [ ] Metin, animasyon ve sesin sağ-sol yönü tutarlı.
- [ ] Statik rehberler ve erişilebilir açıklamalar mevcut.
- [ ] Klinik etkinlik veya tedavi vaadi yok.
- [ ] Varlık lisansları ve izinler belgeli.

### Android ve veri

- [ ] Bildirim planı reboot, saat dilimi ve plan değişiminde uzlaşıyor.
- [ ] Eski/çift olaylar yeni bildirim oluşturmuyor.
- [ ] Sessiz saatler ve günlük bütçe korunuyor.
- [ ] Arka planda seans ilerlemiyor.
- [ ] Çevrimdışı ilk kullanım tamamlanabiliyor.
- [ ] Şema yükseltmesi geçmişi koruyor.
- [ ] Veri silme alarm, kayıt ve checkpoint'i kapsıyor.
- [ ] Yedekleme ve manifest son release üzerinde incelendi.

### Kalite ve yayın

- [ ] TalkBack, büyük yazı ve azaltılmış hareket testleri geçti.
- [ ] Gerçek cihaz performans/pil sonuçları kaydedildi.
- [ ] P0/kritik kusur kalmadı.
- [ ] Sağlık beyanı, gizlilik ve Data safety tutarlı.
- [ ] Güncel hedef API ve hesap test koşulları yeniden doğrulandı.
- [ ] Mağaza ekranları gerçek ürünle aynı.
- [ ] Destek, içerik sorunu ve hotfix süreçleri hazır.

<a id="bolum-39"></a>
## 39. İnternet kaynakları ve kanıt sınırları

**Erişim tarihi: 8 Eylül 2026.** Aşağıdaki kaynaklar bu belge hazırlanırken internetten incelendi. Kaynaklar ürün önerilerini ayırt etmek ve platform/sağlık bağlamını doğrulamak için kullanıldı. Bu liste klinik literatürün sistematik incelemesi, tüm rakiplerin taraması veya hukuki uygunluk denetimi değildir.

| No | Kaynak | Bu planda desteklediği konu | Desteklemediği iddia |
|---|---|---|---|
| K01 | [WHO — Physical activity](https://www.who.int/news-room/fact-sheets/detail/physical-activity) | Genel aktivite ve sedanter zamanı azaltma çerçevesi | Bu uygulamanın ağrı tedavisi |
| K02 | [WHO — Guidelines at a glance](https://www.who.int/publications/i/item/9789240014886) | Genel kılavuz bağlamı | 3 dakikalık özel rutinlerin klinik doğrulanması |
| K03 | [NHS — Sitting exercises](https://www.nhs.uk/live-well/exercise/sitting-exercises/) | Hafif oturarak hareket örnekleri ve uygunluk bağlamı | Buradaki 12 rutinin NHS onayı |
| K04 | [Moova](https://getmoova.app/) | Kısa mola, rehber ve planlı hatırlatma rakibi | Pazar payı veya gelir başarısı |
| K05 | [Stretchly](https://hovancik.net/stretchly/) | Bilgisayarda mola hatırlatma alternatifi | Android'de aynı ürünün tüm özellikleri |
| K06 | [DetectedActivity](https://developers.google.com/android/reference/com/google/android/gms/location/DetectedActivity) | STILL cihaz sabitliğidir | Kesin insan oturuşu ölçümü |
| K07 | [ActivityRecognitionClient](https://developers.google.com/android/reference/com/google/android/gms/location/ActivityRecognitionClient) | Aktivite sinyali/transition araştırması | Her cihazda kusursuz sınıflandırma |
| K08 | [Schedule alarms](https://developer.android.com/develop/background-work/services/alarms) | Yaklaşık/kesin alarm ayrımı ve kısıtlar | Her koşulda tam zamanında teslim |
| K09 | [Define WorkManager requests](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started/define-work) | Periyodik iş aralığı ve esneklik | Hassas egzersiz sayacı |
| K10 | [Notification runtime permission](https://developer.android.com/develop/ui/compose/notifications/notification-permission) | Android 13+ izin davranışı | Kullanıcının bildirimi okuduğu bilgisi |
| K11 | [Activity security / BAL](https://developer.android.com/guide/components/activities/secure-bal) | Arka plan ekran açılışı sınırları | Egzersiz için zorunlu tam ekran yetkisi |
| K12 | [Android 12 behavior changes](https://developer.android.com/about/versions/12/behavior-changes-12) | Bildirim trampoline kısıtı | Bütün üreticilerde aynı zamanlama |
| K13 | [Android architecture recommendations](https://developer.android.com/topic/architecture/recommendations) | UI durumu ve katman sınırları | Tek zorunlu klasör yapısı |
| K14 | [Compose animations](https://developer.android.com/develop/ui/compose/animation/quick-guide) | Görsel geçiş araçları | Egzersiz temposunun tıbbi uygunluğu |
| K15 | [Compose accessibility defaults](https://developer.android.com/develop/ui/compose/accessibility/api-defaults) | Dokunma alanı ve semantik temel | Uygulamanın otomatik tam erişilebilirliği |
| K16 | [WCAG 2.2](https://www.w3.org/TR/WCAG22/) | Kontrast ve erişim hedefleri | Bu planın sertifikalı uygunluğu |
| K17 | [Adaptive display sizes](https://developer.android.com/develop/ui/compose/layouts/adaptive/support-different-display-sizes) | Pencere boyutuna uyarlama | Yalnız telefon dikeyine kilitleme |
| K18 | [Android Auto Backup](https://developer.android.com/identity/data/autobackup) | Yedek/aktarım kuralları | Tüm üreticilerde mutlak yerel kalma garantisi |
| K19 | [Play health content policy](https://support.google.com/googleplay/android-developer/answer/16679511?hl=en) | Sağlık beyanı, gizlilik ve doğru iddialar | Tek metinle tüm hukuki uygunluk |
| K20 | [Health app categories](https://support.google.com/googleplay/android-developer/answer/13996367?hl=en) | Sağlık/fitness kapsamı ve açıklamalar | Her izin için otomatik uygunluk |
| K21 | [Play target API requirements](https://support.google.com/googleplay/android-developer/answer/11926878?hl=en-GB_ALL) | Araştırma tarihindeki telefon API koşulu | Gelecekte değişmeyeceği garantisi |
| K22 | [Personal developer testing requirements](https://support.google.com/googleplay/android-developer/answer/14151465?hl=en-GB) | Hesaba bağlı kapalı test kapısı | Her hesaba aynı şart uygulanması |
| K23 | [Play Billing integration](https://developer.android.com/google/play/billing/integrate) | Satın alma durumu ve işlem yaşam döngüsü | Bu ürünün güncel fiyatı veya gelir tahmini |

### 39.1 Araştırmadan türetilen sonuç ile tasarım tercihi arasındaki sınır

**Kaynaklarla desteklenen sonuçlar:** Benzer mola ürünleri vardır; STILL insanın oturduğunu kanıtlamaz; Android bildirim ve arka plan davranışları kısıtlıdır; sağlık içeriği dikkatli beyan gerektirir.

**Bu belgeye özgü tercihler:** İki alt sekme, 180 saniyelik şablon, 12 rutin sırası, 60 dakika varsayılan aralık, günlük bildirim bütçesi, renkler, tek seferlik Plus ve 8 haftalık temel takvim. Bunlar kullanıcı testi, uzman incelemesi ve teknik ölçümle geliştirilmelidir.

**Son ürün hedefi:** Kullanıcı uygulamayı açtığında ne yapacağını hemen anlasın; hareket sırasında rehberi rahatça takip etsin; istediği anda durabilsin ve birkaç dakika sonra işine dönebilsin. Bütün sonraki özellikler bu deneyimi güçlendirdiği ölçüde değerlidir.
