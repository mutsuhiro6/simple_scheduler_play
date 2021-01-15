package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import java.time.{OffsetDateTime}


class ScheduleSpec extends Specification {

  "Schedule" should {

    val s = Schedule.syntax("s")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Schedule.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Schedule.findBy(sqls.eq(s.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Schedule.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Schedule.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Schedule.findAllBy(sqls.eq(s.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Schedule.countBy(sqls.eq(s.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Schedule.create(name = "MyString", beginAt = null, endAt = null)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Schedule.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Schedule.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Schedule.findAll().head
      val deleted = Schedule.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Schedule.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Schedule.findAll()
      entities.foreach(e => Schedule.destroy(e))
      val batchInserted = Schedule.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
