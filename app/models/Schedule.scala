package models

import scalikejdbc._

import java.time.{OffsetDateTime, ZoneId}
import java.util.Date

case class  Schedule(
  id: Int,
  name: String,
  beginAt: OffsetDateTime,
  endAt: OffsetDateTime) {

  def save()(implicit session: DBSession = Schedule.autoSession): Schedule = Schedule.save(this)(session)

  def destroy()(implicit session: DBSession = Schedule.autoSession): Int = Schedule.destroy(this)(session)

}


object Schedule extends SQLSyntaxSupport[Schedule] {

  override val tableName = "schedule"

  override val columns = Seq("id", "name", "begin_at", "end_at")

  def apply(s: SyntaxProvider[Schedule])(rs: WrappedResultSet): Schedule = apply(s.resultName)(rs)
  def apply(s: ResultName[Schedule])(rs: WrappedResultSet): Schedule = {
    Schedule(
      id = rs.get(s.id),
      name = rs.get(s.name),
      beginAt = rs.get(s.beginAt),
      endAt = rs.get(s.endAt)
    )
  }

  val s = Schedule.syntax("s")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Schedule] = {
    withSQL {
      select.from(Schedule as s).where.eq(s.id, id)
    }.map(Schedule(s.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Schedule] = {
    withSQL(select.from(Schedule as s)).map(Schedule(s.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Schedule as s)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Schedule] = {
    withSQL {
      select.from(Schedule as s).where.append(where)
    }.map(Schedule(s.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Schedule] = {
    withSQL {
      select.from(Schedule as s).where.append(where)
    }.map(Schedule(s.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Schedule as s).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    beginAt: OffsetDateTime,
    endAt: OffsetDateTime)(implicit session: DBSession = autoSession): Schedule = {
    val generatedKey = withSQL {
      insert.into(Schedule).namedValues(
        column.name -> name,
        column.beginAt -> beginAt,
        column.endAt -> endAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Schedule(
      id = generatedKey.toInt,
      name = name,
      beginAt = beginAt,
      endAt = endAt)
  }

  def batchInsert(entities: collection.Seq[Schedule])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        Symbol("name") -> entity.name,
        Symbol("beginAt") -> entity.beginAt,
        Symbol("endAt") -> entity.endAt))
    SQL("""insert into schedule(
      name,
      begin_at,
      end_at
    ) values (
      {name},
      {beginAt},
      {endAt}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: Schedule)(implicit session: DBSession = autoSession): Schedule = {
    withSQL {
      update(Schedule).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.beginAt -> entity.beginAt,
        column.endAt -> entity.endAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Schedule)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Schedule).where.eq(column.id, entity.id) }.update.apply()
  }

}
