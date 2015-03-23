package example.movies

/**
 * Created by fred on 09/11/2014.
 */


import com.explora.executer.sesame.SesameContext
import com.explora.ld.dbpedia.DBPedia
import com.explora.pattern.ExploraHelper._

import scala.concurrent.ExecutionContext.Implicits.global


object SimpleMovieReco extends App with DBPedia with SesameContext {

  val movie = DBEntity("The_Terminator")

  private val request: String = s"""
  select ?movies  where {
    {
      <$movie> <http://dbpedia.org/ontology/starring> ?actor.
      ?movies <http://dbpedia.org/ontology/starring> ?actor.
    } UNION {
      <$movie> <http://dbpedia.org/ontology/director> ?director.
      ?movies <http://dbpedia.org/ontology/director> ?director.
    } UNION {
      <$movie> <http://purl.org/dc/terms/subject> ?subject.
       ?movies <http://purl.org/dc/terms/subject> ?subject.
     }
  } """

  println(request)

  request executeAndGet "movies" map { nodes =>

    println()
    val movies: List[String] = nodes.onlyEntities.filter(n => n != movie).map(_.uri)
    val groupBy: Map[String, List[String]] = movies.groupBy(identity)
    val map: Map[String, Int] = groupBy.map(m => (m._1, m._2.size))
    val by = map.toList.sortBy(-_._2).take(10)
    by.foreach(println)

  }

}
