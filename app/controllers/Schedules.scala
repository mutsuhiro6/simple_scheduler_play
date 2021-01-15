package controllers

import models.{Schedule, ScheduleForm}
import play.api.data.Form
import play.api.data.Forms.{date, mapping, text}
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, ControllerComponents}
import scalikejdbc._

import java.text.SimpleDateFormat
import java.time.{OffsetDateTime, ZoneId}
import javax.inject.{Inject, Singleton}

@Singleton
class Schedules @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  private[this] val addForm = Form(
    mapping(
      "name" -> text(minLength = 1, maxLength = 30),
      "beginAt" -> date("yyyy-MM-dd"),
      "endAt" -> date("yyyy-MM-dd")
    )
    (ScheduleForm.apply)
    (ScheduleForm.unapply)
      .verifying("invalid begin and end date", a => (a.beginAt compareTo a.endAt) <= 0)
  )

  private val s = Schedule.syntax("s")

  def list(date: Option[String]) = Action { implicit request =>
    def where(dateTime: OffsetDateTime): SQLSyntax = {
      sqls.le(s.beginAt, dateTime).and(sqls.ge(s.endAt, dateTime))
    }

    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val sches = date
      .map { dateStr =>
        val dateObj = sdf.parse(dateStr)
        val oDT = OffsetDateTime.ofInstant(dateObj.toInstant, ZoneId.systemDefault())
        Schedule.findAllBy(where(oDT))
      }
      .getOrElse {
        Schedule.findAllBy(where(OffsetDateTime.now()))
      }
    Ok {
      views.html.today(sches)
    }
  }

  def addPage = Action { implicit request =>
    Ok(views.html.add(addForm))
  }

  def add = Action { implicit request =>
    addForm.bindFromRequest.fold(
      error => BadRequest(views.html.add(error)),
      addRequest => {
        val name = addRequest.name
        val beginAt = addRequest.beginAtOffset
        val endAt = addRequest.endAtOffset
        Schedule.create(name, beginAt, endAt)
        Redirect("/")
      }
    )
  }
}
