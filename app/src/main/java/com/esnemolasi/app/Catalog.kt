package com.esnemolasi.app

/** Movement and animation drafts. These are not an expert-approved exercise prescription. */
enum class Motion {
    SETTLE, SHOULDERS, SCAPULA, NECK, CHEST, TWIST, HANDS, WRISTS,
    FOREARMS, ANKLES, MARCH, REACH, STAND, SHIFT, HEELS, BREATHE
}

data class Movement(
    val id: String,
    val name: String,
    val cue: String,
    val detail: String,
    val motion: Motion
)

data class Routine(
    val id: String,
    val title: String,
    val subtitle: String,
    val standing: Boolean,
    val moves: List<String>
)

object Catalog {
    const val reviewStatus = "draft"
    const val contentNotice = "Prototip içerik · Hareketler ve çizimler henüz bir sağlık uzmanı tarafından onaylanmadı."
    const val safetyNotice = "Hareketleri rahat hissettiğin aralıkta yap; kendini zorlama. Ağrı, baş dönmesi veya uyuşma hissedersen dur. Bir sağlık durumun, yakın zamanda yaralanman veya ameliyatın varsa başlamadan önce bir sağlık uzmanına danış. Bu uygulama tanı veya tedavi sunmaz."
    const val seatedNotice = "Sabit, kaymayan bir sandalye kullan. Ayaklarını rahatça destekle ve telefonunu görmek için öne eğilmek zorunda kalmayacağın bir yere koy."
    const val standingNotice = "Kaymayan bir zeminde, sabit bir masa veya tezgâhın yanında dur. Hareket sırasında desteği elinin altında tut. Tekerlekli sandalye destek için uygun değildir."

    val movements: Map<String, Movement> = listOf(
        Movement(
            "H01", "Rahat oturuşa yerleş", "Ayaklarını destekle, omuzlarını serbest bırak.",
            "Sabit bir sandalyeye rahatça otur. Ayakların yerde ya da uygun bir destekte olsun. Dikleşmek için kendini kasma; rahat bir başlangıç bul.",
            Motion.SETTLE
        ),
        Movement(
            "H02", "Omuzlarını gevşet", "Omuzlarını yavaşça biraz kaldır, sonra bırak.",
            "Kolların rahatça aşağıda kalsın. Omuzlarını küçük bir aralıkta yukarı taşı ve başlangıca dön. Hareketi zorlamadan, nefesini tutmadan yap.",
            Motion.SHOULDERS
        ),
        Movement(
            "H03", "Üst sırtını fark et", "Omuzlarını hafifçe geriye al, sonra serbest bırak.",
            "Dirseklerin bedenine yakınken kürek kemiklerini nazikçe birbirine yaklaştırmayı dene. Kuvvetle sıkıştırma ve belini çukurlaştırma. Rahat başlangıca dön.",
            Motion.SCAPULA
        ),
        Movement(
            "H04", "Başını nazikçe çevir", "Başını küçük bir aralıkta çevir, ortaya dön.",
            "Gövden karşıya bakarken başını yavaşça bir yana çevir, merkeze dön ve diğer yana geç. Ellerinle baskı uygulama; daire çizme. Rahat gelmiyorsa bu adımı atla.",
            Motion.NECK
        ),
        Movement(
            "H05", "Göğüs bölgesine alan aç", "Kollarını biraz yana aç, rahatça geri getir.",
            "Dirseklerin hafif bükülü kalsın. Kollarını omuz yüksekliğinin altında, sana rahat gelen aralıkta yana aç. Göğsünü zorlamadan başlangıca dön.",
            Motion.CHEST
        ),
        Movement(
            "H06", "Üst gövdeni hafifçe çevir", "Kalçan sabit kalsın; gövdeni küçük bir aralıkta çevir.",
            "Ayakların destekliyken üst gövdeni nazikçe bir yana, sonra merkeze döndür. Diğer yana geç. Sandalyeden güç alarak çekme; rahat gelmiyorsa adımı atla.",
            Motion.TWIST
        ),
        Movement(
            "H07", "Ellerine kısa bir ara ver", "Parmaklarını aç, sonra gevşekçe kapat.",
            "Dirseklerini rahat bir konumda tut. Parmaklarını zorlamadan aç ve ellerini sıkmadan kapat. Bileklerini nötr ve omuzlarını rahat tutmaya çalış.",
            Motion.HANDS
        ),
        Movement(
            "H08", "Bileklerini fark et", "Ellerini bilekten küçük bir aralıkta hareket ettir.",
            "Ön kollarını rahatça destekleyebilirsin. Ellerini yavaşça biraz yukarı ve aşağı yönlendir. Diğer elinle çekme ya da bastırma; rahat aralıkta kal.",
            Motion.WRISTS
        ),
        Movement(
            "H09", "Ön kollarını rahatlat", "Avuçlarını yavaşça yukarı ve aşağı çevir.",
            "Dirseklerini bedenine yakın tut. Ön kollarını rahat bir aralıkta çevirerek avuçlarının yönünü değiştir. Omuzlarını kaldırma ve bileklerini zorlamadan nötr tut.",
            Motion.FOREARMS
        ),
        Movement(
            "H10", "Ayak bileklerini hareket ettir", "Bir ayağının ucunu hafifçe kaldır ve indir.",
            "Oturarak bir topuğunu yerde destekle; ayak ucunu rahat bir aralıkta yukarı taşı ve indir. Sonra diğer ayağı dene. Büyük daireler çizmene gerek yok.",
            Motion.ANKLES
        ),
        Movement(
            "H11", "Ayaklarını sırayla kaldır", "Bir ayağını biraz kaldır, indir; diğerine geç.",
            "Sabit sandalyede dengeni koruyarak bir ayağını yerden az miktarda kaldır ve yavaşça indir. Sonra diğer ayağı dene. Zor geliyorsa ayaklarını destekli tutup bu adımı atla.",
            Motion.MARCH
        ),
        Movement(
            "H12", "Kollarını rahatça uzat", "Bir kolunu öne uzat, geri getir; diğerine geç.",
            "Kolunu omuz yüksekliğinin altında, rahat bir mesafeye doğru uzat. Gövdeni yana eğmeden başlangıca dön; diğer kola geç. Erişimini büyütmek zorunda değilsin.",
            Motion.REACH
        ),
        Movement(
            "H13", "Ayakta rahatça yerleş", "Sabit desteğin yanında rahat bir duruş bul.",
            "Kaymayan zeminde ayaklarını rahatça yerleştir. Dizlerini kilitleme; omuzlarını serbest bırak. Gerekirse sabit masa veya tezgâhı elinle destek olarak kullan.",
            Motion.STAND
        ),
        Movement(
            "H14", "Ağırlığını nazikçe aktar", "Desteği tutarak ağırlığını biraz yana taşı ve dön.",
            "Sabit bir masa veya tezgâhı tut. İki ayağın da yerde kalırken ağırlığını küçük bir aralıkta bir yana, sonra diğer yana aktar. Dengen rahat değilse bu adımı atla.",
            Motion.SHIFT
        ),
        Movement(
            "H15", "Topuklarını hafifçe kaldır", "Sabit desteği tut; topuklarını biraz kaldır ve indir.",
            "İki elin sabit masa veya tezgâh üzerinde destekli olsun. Ayak uçların yerde kalırken topuklarını az miktarda kaldır, yavaşça geri indir. Dengeyi zorlamadan yap; gerekirse atla.",
            Motion.HEELS
        ),
        Movement(
            "H16", "Nefes al, gevşe", "Nefesini doğal akışında bırak, bedenini dinlendir.",
            "Omuzlarını ve ellerini serbest bırak. Nefesini tutmadan, belli bir ritmi yakalamaya çalışmadan dinlen. İstersen gözlerini ekrandan ayır; bu adımın hareket hedefi yok.",
            Motion.BREATHE
        )
    ).associateBy { it.id }

    val routines: List<Routine> = listOf(
        Routine("R01", "Masa başı dengesi", "Güne kısa bir hareket arası", false,
            listOf("H01", "H02", "H03", "H07", "H10", "H16")),
        Routine("R02", "Omuzlara mola", "Üst beden için sakin bir sıra", false,
            listOf("H01", "H02", "H03", "H05", "H12", "H16")),
        Routine("R03", "Boyun çevresine nazik mola", "Küçük ve rahat hareketler", false,
            listOf("H01", "H02", "H04", "H03", "H07", "H16")),
        Routine("R04", "Ellere kısa ara", "Klavye arasına sığan mola", false,
            listOf("H01", "H07", "H08", "H09", "H02", "H16")),
        Routine("R05", "Üst gövde hareketi", "Yerinden ayrılmadan hareket", false,
            listOf("H01", "H02", "H06", "H05", "H07", "H16")),
        Routine("R06", "Ayaklara hareket", "Alt beden için kısa bir sıra", false,
            listOf("H01", "H10", "H11", "H10", "H02", "H16")),
        Routine("R07", "Toplantı arası", "Küçük hareketlerle ara ver", false,
            listOf("H01", "H07", "H02", "H03", "H10", "H16")),
        Routine("R08", "Ekrandan kısa uzaklaşma", "Ekrana ara vermek için sade bir sıra", false,
            listOf("H01", "H02", "H12", "H07", "H10", "H16")),
        Routine("R09", "Ayakta yenilenme", "Uygun alanda ayakta mola", true,
            listOf("H13", "H02", "H14", "H07", "H12", "H16")),
        Routine("R10", "Destekli ayakta mola", "Sabit destek yakınında hareket", true,
            listOf("H13", "H14", "H15", "H02", "H03", "H16")),
        Routine("R11", "Öğleden sonra arası", "İşin ortasında üç dakika", false,
            listOf("H01", "H05", "H03", "H08", "H10", "H16")),
        Routine("R12", "Çalışma günü kapanışı", "Günü sakin bir arayla tamamla", false,
            listOf("H01", "H02", "H07", "H10", "H03", "H16"))
    )

    /** Unknown or obsolete links safely return to the neutral introductory routine. */
    fun routine(id: String): Routine = routines.firstOrNull { it.id == id } ?: routines.first()
}
