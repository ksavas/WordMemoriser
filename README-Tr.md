# WordMemoriser

[Türkçe](https://github.com/ksavas/IddaAnalyser/edit/master/README.md)

WordMemoriser ingilizce kelime bilgisini geliştirmek için geliştirilen bir web uygulamasıdır. Uygulama şu anda hala geliştiriliyor, ancak uygulama şu anda kök ihtiyaçları karşılayabildiği için kullanılabilir durumdadır.

Uygulamanın sunucu tarafı java için jdk 14.0.1 ve Spring 2.3.1.RELEASE ile geiştirildi, uygulamayı paketlemek için Apache Maven 3.6.3 kullanıldı ve veritabanı için Mysql 8.0.20 kullanıldı.
Eğer uygulamayı kullanmak istiyorsanız bilgisayarınızda en az JRE 14.0.1 kurulu olmalıdır. (Eğer uygulamayı kullanmak isterseniz benimle iletişime geçebilirsiniz)

Kullanıcı arayüzü (Web uygulaması) tarafı javascript ve jquery 3.5.1 ile geliştirildi.

Bu uygulama spring boot ile geliştirildi çünkü bu uygulamayı gelecekte microservice mimarisinde kullanmayı planlıyorum. 
Gelecek için yapılan planlar:
- Uygulamayı sadece Türkçe - İngilizce değil bütün diller için kullanmak.
- Kelime dağarcığı dışında gramer yeteneklerininde geliştirilebileceği bir servise bulunması.
- Farklı sınav servisleri sunmak ve sınav sonuçlarını takip etmek (Örneğin Sınav sonuçlarını Puan/Zaman grafiğinde görüntülemek ayrıca tablo görüntüsünde görüntülemek)
- Gramer testi için yapay zekayı kullanarak anlamlı cümleler kurmak.
- Kullanıcılar için bir üyelik sistemi geliştirmek.
- Yukarıda tanımlanan bütün servisleri microservice mimarisi içinde birleştirmek.

## Tanım
WordMemoriser kelime dağarcığını geliştirmek için kullanılan bir web uygulamasıdır, uygulamanın kullanımı;

### Yeni Öğrenilen Kelime
- Kullanıcı yeni bir kelime öğrendiğinde, kelimeyi uygulamaya kaydeder.
- Yeni öğrenilen kelime uygulamaya aşağıdaki bilgilerle birlikte kaydedilir:
  - Türkçe Kelime.
  - İngilizce Kelime.
  - Kelimenin Türkçe Tanımı.
  - Kelimenin İngilizce Tanımı.
  - Kelime Türü (Örneğin isim, fiil, zamir vb.)
  - Bir Örnek.
- Aşağıdaki figür yeni kelime ekleme ekranını temsil eder
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/AddWord.png"><br>


### Sıav
- Eğer uygulama yeterince kelimeyle dolduysa (En az 5 Kelime) kullanıcı kendini yeni kelimelerle test edebilir.
- Testler 6 farklı şekilde yapılır ve dillere göre 2'ye ayrılır:
  - Türkçe'den İngilizceye
    - Kelimeden Kelimeye
    - Kelimeden Anlama
    - Anlamdan Kelimeye
  - İngilizce'den Türkçeye
    - Kelimeden Kelimeye
    - Kelimeden Anlama
    - Anlamdan Kelimeye
- Cevaplanan her kelime cevaba göre +1 veya -1 puan alır.
  - Eğer cevap doğruysa kelime +1 puan kazanır.
  - Eğer cevap yanlışsa kelime -1 puan kazanır.
- Kullanıcı sınavları kelime puanlarına göre filtreleyebilir:
  - Örneğin, kullanıcı 20'den fazla puan alan kelimeleri artık öğrendiğini düşünüp, "En fazla 20 puan alan kelimelerden sınav olmak istiyorum" diyebilir.
  - Veya kullanıcı öğrenemediği kelimelere ağırlık vermek isteyip "En fazla 0 puanı olan kelimelerden sınav olmak istiyorum" diyebilir, yani şimdiye kadar hiç doğru cevaplayamadığı kelimelerden sınav olmak istiyor olabilir.
- Aşağıdaki figür sınav ekranını temsil etmektedir
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/Exam.png"><br>
- Aşağıdaki figür kullanıcı doğru cevabı verdiğinde olacakları temsil etmektedir
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/ExamTrueAnswer.png"><br>
- Aşağıdaki figür kullanıcı yanlış cevabı verdiğinde olacakları temsil etmektedir
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/ExamFalseAnswer.png"><br>

### Sonuçlar
- Kullanıcı Results ekranında kelimelerini anlamları, örnek ve puanlarıyle birlikte görüntüleyebilir ayrıca istediği kelimeyi silebilir.
- Aşağıdaki figür Results sayfasını temsil etmektedir
<img src="https://github.com/ksavas/WordMemoriser/blob/master/SS/Results.png"><br>
