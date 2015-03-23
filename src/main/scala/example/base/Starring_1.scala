package example.base

import com.explora.executer.sesame.SesameContext
import com.explora.ld.dbpedia.LiveDBPedia
import com.explora.pattern.ExploraHelper._

import scala.concurrent.ExecutionContext.Implicits.global


/**
 * Created with IntelliJ IDEA.
 * User: fred
 * Date: 14/08/13
 * Time: 17:29
 */
object Starring_1 extends App with  LiveDBPedia with SesameContext{


  val resultF = "SELECT ?starring WHERE { <http://dbpedia.org/resource/The_Terminator> <http://dbpedia.org/ontology/starring> ?starring}" executeAndGet "starring"


  resultF.foreach { r =>
    r.foreach(println)
  }


}
