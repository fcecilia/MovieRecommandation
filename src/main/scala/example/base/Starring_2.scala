package example.base

import com.explora.executer.sesame.SesameContext
import com.explora.ld.dbpedia.LiveDBPedia
import com.explora.model.Entity
import com.explora.pattern.ExploraHelper._

import scala.concurrent.ExecutionContext.Implicits.global

object Starring_2 extends App with LiveDBPedia with SesameContext {


  val The_Terminator = "http://dbpedia.org/resource/The_Terminator".entity

  val starringOnt: Entity = "http://dbpedia.org/ontology/starring".entity

  val starringF = The_Terminator valueOf starringOnt

  starringF.foreach { starring =>
    starring.foreach(println)
  }

}