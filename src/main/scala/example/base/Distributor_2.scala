package example.base

import com.explora.executer.sesame.SesameContext
import com.explora.ld.dbpedia.LiveDBPedia
import com.explora.model.Entity
import com.explora.pattern.ExploraHelper._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Distributor_2 extends App with LiveDBPedia with SesameContext {


  val movie = DBEntity("The_Matrix")

  val starring: Entity = "http://dbpedia.org/property/distributor".entity

  val distributorF = movie valueOf starring


  val result = for {
    list <- distributorF
    entities: List[Entity] = list.onlyEntities
    onlyLiterals = list.onlyLiterals.map(_.value)
    name = "http://dbpedia.org/property/name".entity
    namesEntity <- Future.sequence(entities.map(_ valueOf name)).map(_.flatten)
  } yield {
      (namesEntity.onlyLiterals.map(_.value) ++ onlyLiterals).distinct
    }


  result.map(println)


}