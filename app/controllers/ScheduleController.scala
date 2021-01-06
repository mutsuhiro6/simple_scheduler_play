package controllers

import play.api.data.Form
import play.api.data.Forms.{date, mapping, text}
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, ControllerComponents}

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.{Inject, Singleton}

@Singleton
class ScheduleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  private[this] val addForm = Form(
    mapping(
      "title" -> text(minLength = 1, maxLength = 30),
      "start" -> date("yyyy-MM-dd"),
      "end" -> date("yyyy-MM-dd")
    )
    (Schedule.apply)
    (Schedule.unapply)
      .verifying("invalid start and end date", a => (a.start compareTo a.end) < 0)
  )

  def list(date: Option[String]) = Action { implicit request =>
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val sches = date
      .map { dateObj =>
        ScheduleRepository.find(sdf.parse(dateObj))
      }
      .getOrElse {
        ScheduleRepository.find(new Date())
      }
    Ok {
      views.html.list(sches)
    }
  }

  def addPage = Action { implicit request =>
    Ok(views.html.add(addForm))
  }

  def add = Action { implicit request =>
    addForm.bindFromRequest.fold (
      error => BadRequest(views.html.add(error)),
      addRequest => {
        ScheduleRepository.add(addRequest)
        Redirect("/")
      }
    )
  }
}
