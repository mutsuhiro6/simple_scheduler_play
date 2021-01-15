package models

import java.time.{OffsetDateTime, ZoneId}
import java.util.Date

case class ScheduleForm(
                         name: String,
                         beginAt: Date,
                         endAt: Date
                       ) {
  private def toOffsetDateTime(date: Date): OffsetDateTime = {
    OffsetDateTime.ofInstant(date.toInstant, ZoneId.systemDefault())
  }

  def beginAtOffset: OffsetDateTime = toOffsetDateTime(beginAt)
  def endAtOffset: OffsetDateTime = toOffsetDateTime(endAt)

}