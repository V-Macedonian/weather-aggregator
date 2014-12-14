package controllers

import javax.inject.{Singleton, Inject}
import services.UUIDGenerator
import org.slf4j.{LoggerFactory, Logger}
import play.api.mvc._
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Instead of declaring an object of Application as per the template project, we must declare a class given that
 * the application context is going to be responsible for creating it and wiring it up with the UUID generator service.
 * @param uuidGenerator the UUID generator service we wish to receive.
 */
@Singleton
class Application @Inject() (uuidGenerator: UUIDGenerator) extends Controller {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Application])

  def index = Action {
    logger.info("Serving index page...")
    Ok(views.html.index())
  }

  def plainXML(city: String, days: Int) = Action.async {
    val dictionary = Map(
        "Poltava" -> 1,
        "Полтава" -> 1,
        "Cherkasy" -> 2,
        "Черкасы" -> 2,
        "Zhytomyr" -> 3,
        "Житомирr" -> 3,
        "Summy" -> 4,
        "Сумы" -> 4,
        "Chernihiv" -> 5,
        "Чернигов" -> 5,
        "Rivne" -> 6,
        "Ровно" -> 6,
        "Donetsk" -> 7,
        "Донецк" -> 7,
        "Kherson" -> 8,
        "Херсон" -> 8,
        "Chernivci" -> 9,
        "Черновцы" -> 9,
        "Vinnytsia" -> 10,
        "Винница" -> 10,
        "Mykolaiv" -> 11,
        "Николаев" -> 11,
        "Khmelnytskyi" -> 12,
        "Хмельницк" -> 12,
        "Ternopil" -> 13,
        "Тернопиль" -> 13,
        "Kirovohrad" -> 14,
        "Кировоград" -> 14,
        "Lyiv" -> 15,
        "Львов" -> 15,
        "Dnipropetrovsk" -> 16,
        "Днепропетровск" -> 16,
        "Zaporizhia" -> 17,
        "ЗЗапорожье" -> 17,
        "Berdyansk" -> 18,
        "Бердянск" -> 18,
        "Kharkiv" -> 19,
        "Харьков" -> 19,
        "Mariupol" -> 20,
        "Мариуполь" -> 20,
        "Yalta" -> 21,
        "Ялта" -> 21,
        "Sevastopol" -> 22,
        "Севастополь" -> 22,
         "Kyiv" -> 23,
         "Киев" -> 23,
        "Simferopol" -> 24,
        "Симферополь" -> 24,
        "Odessa" -> 25,
        "Одесса" -> 25,
        "Eupatoria" -> 26,
        "Евпатория" -> 26
    )
    dictionary.get(city) match {
      case Some(cityCode) =>  
        WS.url(s"http://xml.weather.co.ua/1.2/forecast/$cityCode")
          .withQueryString("dayf" -> days.toString)
          .get()
          .map { response =>
             Ok(response.xml)
          }
      case None => 
        Future.successful(NotFound(s"$city is not a ukrainian city"))
    }
  }
  
  def randomUUID = Action {
    logger.info("calling UUIDGenerator...")
    Ok(uuidGenerator.generate.toString)
  }

}
