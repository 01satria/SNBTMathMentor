package com.snbt.mathmentor.data.local

import com.snbt.mathmentor.data.local.entity.DayEntity
import com.snbt.mathmentor.domain.model.*

object RoadmapDataSource {

    val dayTitles = mapOf(
        1 to "Operasi Bilangan Dasar + Pecahan & Desimal",
        2 to "Persen, Perbandingan & Rasio",
        3 to "Aritmetika Sosial (Untung-Rugi, Bunga, Pajak)",
        4 to "Himpunan & Pola Bilangan",
        5 to "Logika Bilangan & Deret",
        6 to "Persamaan & Pertidaksamaan Sederhana",
        7 to "Fungsi Linier Dasar",
        8 to "Review Fase 1 + Mini Quiz 50 Soal",
        9 to "Geometri Datar: Segitiga & Segi Empat",
        10 to "Lingkaran & Bangun Gabungan",
        11 to "Geometri Ruang: Kubus, Balok, Tabung",
        12 to "Review Geometri + Tryout PK Mini",
        13 to "Persamaan Kuadrat",
        14 to "Fungsi Kuadrat & Parabola",
        15 to "Sistem Persamaan Linear",
        16 to "Pertidaksamaan & Nilai Mutlak",
        17 to "Trigonometri Dasar",
        18 to "Sudut & Transformasi Geometri",
        19 to "Luas Permukaan Bangun Ruang",
        20 to "Volume Bangun Ruang",
        21 to "Koordinat Kartesius & Jarak",
        22 to "Review Aljabar Lanjutan",
        23 to "Statistika: Rata-rata & Median",
        24 to "Modus, Kuartil & Simpangan",
        25 to "Diagram Statistika",
        26 to "Peluang Dasar",
        27 to "Peluang Kondisional",
        28 to "Review Statistika & Peluang",
        29 to "Penalaran Pola & Barisan",
        30 to "Tabel Data & Grafik",
        31 to "Logika Kuantitatif",
        32 to "Aritmetika Cerita Lanjutan",
        33 to "Framework SPEED Part 1",
        34 to "Framework SPEED Part 2",
        35 to "Review Penalaran Matematika",
        36 to "Tryout Full PK 20 Soal",
        37 to "Tryout Full PM 20 Soal",
        38 to "Tryout Campur + Final Review + Strategi H-1"
    )

    fun getAllDayEntities(): List<DayEntity> {
        return (1..38).map { day ->
            DayEntity(
                dayNumber = day,
                title = dayTitles[day] ?: "Materi Hari $day"
            )
        }
    }

    fun getDayContent(dayNumber: Int): DayContent {
        return when (dayNumber) {
            1 -> day1Content()
            2 -> day2Content()
            3 -> day3Content()
            else -> defaultDayContent(dayNumber)
        }
    }

    private fun day1Content() = DayContent(
        dayNumber = 1,
        title = "Operasi Bilangan Dasar + Pecahan & Desimal",
        objectives = listOf(
            "Menguasai operasi +, -, ×, ÷ pada bilangan bulat",
            "Menghitung pecahan biasa, campuran, dan desimal",
            "Mengkonversi antara bentuk pecahan dan desimal",
            "Menerapkan urutan operasi (BODMAS/PEMDAS)"
        ),
        materialSummary = """
## 📐 Operasi Bilangan Dasar

### Urutan Operasi (BODMAS)
**B**rackets → **O**rder (pangkat) → **D**ivision → **M**ultiplication → **A**ddition → **S**ubtraction

### Bilangan Bulat
- Penjumlahan: tanda sama → tambah, tanda beda → kurang
- Perkalian/Pembagian: tanda sama → positif, tanda beda → negatif

## 🍕 Pecahan

### Pecahan Biasa
$$\frac{a}{b} + \frac{c}{d} = \frac{ad + bc}{bd}$$

### KPK & FPB
- KPK digunakan untuk menyamakan penyebut
- FPB digunakan untuk menyederhanakan pecahan

### Konversi Desimal
- 1/4 = 0,25 | 1/2 = 0,5 | 3/4 = 0,75
- 1/3 ≈ 0,333 | 2/3 ≈ 0,667
        """.trimIndent(),
        keyFormulas = listOf(
            "a/b + c/d = (ad+bc)/bd",
            "a/b × c/d = (a×c)/(b×d)",
            "a/b ÷ c/d = a/b × d/c",
            "Desimal ke persen: × 100%"
        ),
        youtubeVideoId = "dQw4w9WgXcQ",
        youtubeTitle = "Operasi Bilangan & Pecahan untuk UTBK SNBT",
        exercises = listOf(
            Exercise(1, "Hitung: 2/3 + 1/4 = ?", Difficulty.MUDAH, "11/12", "Samakan penyebut: 8/12 + 3/12 = 11/12"),
            Exercise(2, "Hitung: 3/5 × 2/7 = ?", Difficulty.MUDAH, "6/35", "Kalikan pembilang dan penyebut: (3×2)/(5×7) = 6/35"),
            Exercise(3, "Sederhanakan: 24/36", Difficulty.MUDAH, "2/3", "FPB(24,36)=12, maka 24/36 = 2/3"),
            Exercise(4, "0,125 = ... /8", Difficulty.MUDAH, "1", "0,125 = 1/8, jadi jawabannya 1"),
            Exercise(5, "Hitung: 3½ + 2¼ = ?", Difficulty.MUDAH, "5¾", "3+2=5, ½+¼=¾, jadi 5¾"),
            Exercise(6, "(-3) × (-4) + 2 = ?", Difficulty.MUDAH, "14", "(-3)×(-4)=12, lalu 12+2=14"),
            Exercise(7, "Hitung: 5/6 - 1/4 = ?", Difficulty.SEDANG, "7/12", "10/12 - 3/12 = 7/12"),
            Exercise(8, "Jika a = 2/3 dan b = 3/4, hitung a×b + a/b", Difficulty.SEDANG, "1/2 + 8/9 = 25/18", "a×b=1/2, a÷b=(2/3)÷(3/4)=8/9"),
            Exercise(9, "Hitung: (1/2 + 1/3) ÷ (1/4 - 1/6)", Difficulty.SEDANG, "10", "(5/6)÷(1/12) = 5/6 × 12 = 10"),
            Exercise(10, "Konversi 2,375 ke pecahan sederhana", Difficulty.SEDANG, "19/8", "2,375 = 2375/1000 = 19/8"),
            Exercise(11, "Tiga bilangan berurutan dengan jumlah 57. Bilangan terkecil?", Difficulty.SEDANG, "18", "n+(n+1)+(n+2)=57, 3n+3=57, n=18"),
            Exercise(12, "Hitung: 1/1×2 + 1/2×3 + 1/3×4 + ... + 1/9×10", Difficulty.SULIT, "9/10", "Teleskoping: 1-1/10 = 9/10"),
            Exercise(13, "Bilangan prima antara 10 dan 30 ada berapa?", Difficulty.SULIT, "5", "11, 13, 17, 19, 23 → 5 bilangan"),
        ),
        quizQuestions = listOf(
            QuizQuestion(1, "Hasil dari 3/4 + 5/6 adalah...", listOf("8/10", "19/12", "3/5", "7/6"), 1, "Samakan penyebut 12: 9/12+10/12=19/12"),
            QuizQuestion(2, "0,75 dalam bentuk pecahan paling sederhana adalah...", listOf("75/100", "3/4", "15/20", "7/8"), 1, "0,75 = 75/100 = 3/4"),
            QuizQuestion(3, "Jika 2x + 1/2 = 3, maka x = ...", listOf("1/4", "5/4", "3/4", "7/4"), 2, "2x = 5/2, x = 5/4"),
            QuizQuestion(4, "(-2)³ + (-3)² = ...", listOf("-17", "1", "-1", "17"), 2, "-8+9=1"),
            QuizQuestion(5, "KPK dari 12 dan 18 adalah...", listOf("6", "36", "72", "216"), 1, "KPK(12,18)=36"),
            QuizQuestion(6, "Urutan dari kecil ke besar: 0,3 | 1/4 | 2/5", listOf("0,3 < 1/4 < 2/5", "1/4 < 0,3 < 2/5", "2/5 < 0,3 < 1/4", "1/4 < 2/5 < 0,3"), 1, "0,25 < 0,3 < 0,4"),
        ),
        studyDurationMinutes = 60
    )

    private fun day2Content() = DayContent(
        dayNumber = 2,
        title = "Persen, Perbandingan & Rasio",
        objectives = listOf(
            "Menghitung persentase dari suatu nilai",
            "Menyelesaikan soal perbandingan senilai dan berbalik nilai",
            "Mengaplikasikan rasio dalam soal cerita",
            "Menghitung kenaikan dan penurunan persen"
        ),
        materialSummary = """
## 📊 Persen

### Konsep Dasar
$$\text{Persen} = \frac{\text{Bagian}}{\text{Total}} \times 100\%$$

### Kenaikan & Penurunan
- Kenaikan % = (Nilai baru - Nilai awal)/Nilai awal × 100%
- Penurunan % = (Nilai awal - Nilai baru)/Nilai awal × 100%

## ⚖️ Perbandingan

### Senilai
Jika a naik → b naik: a₁/b₁ = a₂/b₂

### Berbalik Nilai  
Jika a naik → b turun: a₁×b₁ = a₂×b₂

## 🔢 Rasio
Rasio a:b:c → bagian a = a/(a+b+c) × total
        """.trimIndent(),
        keyFormulas = listOf(
            "Persen = (Bagian/Total) × 100%",
            "Perbandingan senilai: a₁/a₂ = b₁/b₂",
            "Perbandingan berbalik: a₁×b₁ = a₂×b₂",
            "Bagian rasio = (nilai rasio/total rasio) × jumlah"
        ),
        youtubeVideoId = "dQw4w9WgXcQ",
        youtubeTitle = "Persen & Perbandingan untuk UTBK SNBT 2026",
        exercises = listOf(
            Exercise(1, "25% dari 200 adalah?", Difficulty.MUDAH, "50", "25/100 × 200 = 50"),
            Exercise(2, "Jika harga naik dari 80.000 menjadi 100.000, berapa kenaikan %?", Difficulty.MUDAH, "25%", "(100-80)/80 × 100% = 25%"),
            Exercise(3, "Rasio A:B = 3:5. Jika total = 240, berapa nilai A?", Difficulty.MUDAH, "90", "A = 3/8 × 240 = 90"),
            Exercise(4, "5 pekerja menyelesaikan pekerjaan dalam 12 hari. Berapa hari jika 10 pekerja?", Difficulty.SEDANG, "6 hari", "Berbalik nilai: 5×12=10×x, x=6"),
            Exercise(5, "Harga setelah diskon 20% adalah 80.000. Harga awal?", Difficulty.SEDANG, "100.000", "80% × harga = 80.000, harga = 100.000"),
        ),
        quizQuestions = listOf(
            QuizQuestion(1, "30% dari 450 adalah...", listOf("115", "135", "150", "120"), 1, "30/100 × 450 = 135"),
            QuizQuestion(2, "Perbandingan A:B = 2:3 dan B:C = 4:5. Rasio A:B:C adalah...", listOf("8:12:15", "2:3:5", "4:6:5", "2:4:5"), 0, "A:B=8:12, B:C=12:15, jadi A:B:C=8:12:15"),
            QuizQuestion(3, "Jika x berbanding terbalik dengan y, dan x=4 saat y=6, berapa x saat y=8?", listOf("2", "3", "4,5", "12"), 1, "xy=24, x=24/8=3"),
            QuizQuestion(4, "Sebuah baju didiskon 30% menjadi Rp140.000. Harga aslinya?", listOf("Rp180.000", "Rp200.000", "Rp210.000", "Rp195.000"), 1, "70% × harga = 140.000, harga = 200.000"),
            QuizQuestion(5, "Tiga orang membagi uang dengan rasio 1:2:3. Total = 360.000. Bagian terkecil?", listOf("30.000", "60.000", "90.000", "120.000"), 1, "1/6 × 360.000 = 60.000"),
        ),
        studyDurationMinutes = 60
    )

    private fun day3Content() = DayContent(
        dayNumber = 3,
        title = "Aritmetika Sosial (Untung-Rugi, Bunga, Pajak)",
        objectives = listOf(
            "Menghitung untung, rugi, dan persentasenya",
            "Menghitung bunga tunggal dan majemuk",
            "Memahami konsep pajak dan diskon bertingkat",
            "Menyelesaikan soal harga jual dan harga beli"
        ),
        materialSummary = """
## 🏪 Untung & Rugi

$$\text{Untung} = \text{Harga Jual} - \text{Harga Beli}$$
$$\% \text{Untung} = \frac{\text{Untung}}{\text{Harga Beli}} \times 100\%$$

## 🏦 Bunga Tunggal

$$\text{Bunga} = \frac{p \times r \times t}{100}$$

Dimana: p = pokok, r = suku bunga (%), t = waktu (tahun)

## 💰 Bunga Majemuk

$$M = P(1 + r)^t$$

## 🧾 Pajak & Diskon

- Harga setelah diskon = Harga × (1 - diskon%)
- Harga setelah pajak = Harga × (1 + pajak%)
- Diskon bertingkat: kalikan faktor satu per satu
        """.trimIndent(),
        keyFormulas = listOf(
            "Untung = Harga Jual - Harga Beli",
            "%Untung = Untung/Harga Beli × 100%",
            "Bunga = p×r×t/100",
            "Bunga Majemuk: M = P(1+r)ⁿ"
        ),
        youtubeVideoId = "dQw4w9WgXcQ",
        youtubeTitle = "Aritmetika Sosial Lengkap untuk UTBK SNBT",
        exercises = listOf(
            Exercise(1, "Beli Rp80.000, jual Rp100.000. Untung berapa %?", Difficulty.MUDAH, "25%", "Untung=20.000, %=20000/80000×100=25%"),
            Exercise(2, "Modal Rp500.000, bunga 10%/tahun, 2 tahun. Total bunga?", Difficulty.MUDAH, "Rp100.000", "B=500000×10%×2=100.000"),
            Exercise(3, "Harga Rp200.000 didiskon 20% lalu 10%. Harga akhir?", Difficulty.SEDANG, "Rp144.000", "200000×0,8×0,9=144.000"),
        ),
        quizQuestions = listOf(
            QuizQuestion(1, "Pedagang membeli 50 kg mangga Rp8.000/kg, jual Rp10.000/kg. Untung total?", listOf("Rp50.000", "Rp100.000", "Rp150.000", "Rp200.000"), 1, "Untung/kg=2000, total=2000×50=100.000"),
            QuizQuestion(2, "Tabungan Rp1.000.000 dengan bunga 6%/tahun selama 6 bulan. Bunga?", listOf("Rp30.000", "Rp60.000", "Rp90.000", "Rp120.000"), 0, "B=1.000.000×6%×0,5=30.000"),
            QuizQuestion(3, "Harga setelah diskon 25% adalah Rp150.000. Harga asli?", listOf("Rp175.000", "Rp187.500", "Rp200.000", "Rp225.000"), 2, "75%×harga=150.000, harga=200.000"),
            QuizQuestion(4, "PPN 11% dari harga Rp500.000. Total yang dibayar?", listOf("Rp511.000", "Rp550.000", "Rp555.000", "Rp560.000"), 2, "Total=500.000×1,11=555.000"),
            QuizQuestion(5, "Rugi 20% jika jual Rp160.000. Harga beli?", listOf("Rp180.000", "Rp192.000", "Rp200.000", "Rp210.000"), 2, "80%×HB=160.000, HB=200.000"),
        ),
        studyDurationMinutes = 75
    )

    private fun defaultDayContent(dayNumber: Int) = DayContent(
        dayNumber = dayNumber,
        title = dayTitles[dayNumber] ?: "Materi Hari $dayNumber",
        objectives = listOf(
            "Memahami konsep dasar materi hari ini",
            "Mengerjakan latihan soal bertingkat",
            "Lulus quiz mini dengan skor minimal 70%"
        ),
        materialSummary = """
## 📚 Materi Hari $dayNumber

### ${dayTitles[dayNumber] ?: "Materi"}

Konten materi lengkap akan tersedia di versi final aplikasi.
Pastikan kamu membaca buku referensi UTBK SNBT 2026 untuk materi ini.

### Tips Belajar
- Fokus pada rumus-rumus kunci
- Kerjakan soal dari mudah ke sulit
- Catat setiap kesalahan di Error Log
        """.trimIndent(),
        keyFormulas = listOf("Rumus 1", "Rumus 2", "Rumus 3"),
        youtubeVideoId = "dQw4w9WgXcQ",
        youtubeTitle = "Video Materi Hari $dayNumber - UTBK SNBT 2026",
        exercises = (1..10).map { i ->
            Exercise(i, "Soal $i untuk hari $dayNumber", if (i <= 4) Difficulty.MUDAH else if (i <= 7) Difficulty.SEDANG else Difficulty.SULIT, "Jawaban $i", "Penjelasan soal $i")
        },
        quizQuestions = (1..5).map { i ->
            QuizQuestion(i, "Pertanyaan quiz $i hari $dayNumber", listOf("A", "B", "C", "D"), 0, "Penjelasan jawaban $i")
        },
        studyDurationMinutes = 60
    )
}
