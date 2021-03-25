# WordMemoriser

[English](https://github.com/ksavas/WordMemoriser/blob/master/README.md)

WordMemoriser mikroservis mimarisinde geliştirilen bir web uygulamasıdır. Uygulama hala geliştirilmeye devam etmektedir ancak uygulama şu anda çekirdek ihtiyaçları karşılamaktadır yani kullanılabilir.

Uygulamanın amacı yeni öğrenilen kelimelerle test yaparak ingilizce kelime haznesini geliştirmek üzerinedir. Uygulanın detayları aşağıda belirtilmiştir.

## Teknik Detaylar
- Bu uygulama bir web uygulamasıdır. Bunun anlamı, uygulamanın bir sunucu, bir de istemci tarafı bulunmaktadır. Sunucu tarafı cloud üzerinde host edilecek mikroservis           mimarisinde geliştirilmiştir.
- Uygulamadaki mikroservisler birbirleriyle http üzerinden haberleşmektedir ve bilgiler json formatında taşınmaktadır. (RESTful)
- İstemci tarafı tasarlanırken mvc framework'ünden faydalanılmıştır. Yani bütün business logic java tarafında gerçekleştirilip server tarafından html döndürülmektedir.
  - Mvc Controller'dan döndürülen html, html/css ile geliştirildi ve ayrıca javascript ve jquery 3.5.1 kütüphanesi kullanıldı.
- Uygulamada 5 adet mikroservis bulunmaktadır:
  - Eureka Server: Kayıt olan mikroservislerin listesinin tutulması için.
    - JDK 11, Spring Boot 2.3.8 RELEASE (Container teknolojisini kullanabilmek için fakrklı bir java version'u seçildi)
  - Gateway Service: Diğer mikroservis'leri kullanıcıdan soyutlayan gateway teknolojisini kullanmak için. Gateway olarak Netflix Zuul Gateway kullanıldı.
    - JDK 8, Spring Boot 2.3.10 BUILD-SNAPSHOT
  - Word Service: Spring boot uygulaması olarak ayağa kalkan RESTful web service ve spring mvc uygulaması. Word Service'in diğer mikroservislerle iletişim kurması için RESTful web arayüzü içermektedir. Kendi veritabanı bulunmaktadır.
  - Account Service: Spring boot uygulaması olarak ayağa kalkan RESTful web service ve spring mvc uygulaması. Account Service'in diğer mikroservislerle iletişim kurması için RESTful web arayüzü içermektedir. Kendi veritabanı bulunmaktadır.
- Mysql veritabanı ile ORM teknolojisiyle iletişim kurmak için spring Jpa framework kullanılmıştır.
- Bütün proje, Apache Maven 3.6.3 ile kurulup, paketlenip, dağıtılmaktadır.
- Veri tabanı sunucusu için Mysql 8.0.20 kullanılmıştır (Gelecekte h2db kullanılması planlanmaktadır)
- Logging için Spring Boot Log4j framework'ünden faydalanılmıştır.
- Mvc framework'ünün html ile daha uygun kullanılabilmesi için Thymeleaf framework'ünden faydalanılmıştır.

Projenin genel ihtiyaçlarını 2 mikroservis karşılamaktadır:
- Mvc Service    : Bu servis kullanıcı ve bütün sunucu arasında bir köprü vazifesi görmektedir. Bu servis diğer servislerle iletişim kurup sonucu html olarak kullanıcıya döndürür.
- Word Service   : Word ve test operasyonları için.
- Account Service: Kullanıcılar ve bağlantılı word'ler için.

Aşağıdaki resim projenin mikroservis şeklindeki mimari tasarımını temsil etmektedir:
<img src="https://github.com/ksavas/WordMemoriser/blob/develop/SS/Word%20Memories%20Architecture.png"><br>

Not: Eğer uygulamayı kullanmak istiyorsanız bilgisayarınızda en az JRE 11 kurulu olmalıdır. (Eğer uygulamayı kullanmak isterseniz benimle iletişime geçebilirsiniz)

Kullanıcı arayüzü (Web uygulaması) tarafı javascript ve jquery 3.5.1 ile geliştirildi.

### Gelecek Planları
Gelecek için yapılan planlar:
- Uygulamayı sadece Türkçe - İngilizce değil bütün diller için kullanmak.
- Kelime dağarcığı dışında gramer yeteneklerininde geliştirilebileceği bir servise bulunması.
- Farklı sınav servisleri sunmak ve sınav sonuçlarını takip etmek (Örneğin Sınav sonuçlarını Puan/Zaman grafiğinde görüntülemek ayrıca tablo görüntüsünde görüntülemek)
- Gramer testi için yapay zekayı kullanarak anlamlı cümleler kurmak.

## Proje Detayları
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


### Sınav
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
