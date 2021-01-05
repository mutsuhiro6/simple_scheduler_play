package controllers

import java.util.Date

object ScheduleRepository {

  private var schedules: Seq[Schedule] = Seq.empty[Schedule]

  def findAll: Seq[Schedule] = schedules

  def find(date: Date): Seq[Schedule] = {
    schedules
      .filter {
        sche =>
          (sche.start compareTo date) < 0 && (date compareTo sche.end) < 0
      }
  }

  def add(schedule: Schedule): Unit = {
    schedules :+= schedule
  }

}
