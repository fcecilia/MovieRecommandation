package example.base

import com.explora.executer.sesame.SesameContext
import com.explora.ld.dbpedia.LiveDBPedia
import com.explora.model.{Entity, Literal}
import com.explora.pattern.ExploraHelper._

import scala.concurrent.ExecutionContext.Implicits.global

object Distributor extends App with LiveDBPedia with SesameContext {


  val movie = DBEntity("The_Matrix")

  val distributor: Entity = "http://dbpedia.org/property/distributor".entity

  val distributorF = movie valueOf distributor

  distributorF.map { tr =>

    tr.foreach {
      case Literal(value, opt) => println(value)
      case Entity(uri) => println(uri)
        }
    }
  

}