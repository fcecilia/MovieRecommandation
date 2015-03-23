package example.movies

/**
 * Created by fred on 08/11/2014.
 */

import java.util.Date

import com.explora.executer.sesame.SesameContext
import com.explora.ld.dbpedia.{LiveDBPedia, DBPedia}
import com.explora.pattern.ExploraHelper._
import com.hp.hpl.jena.vocabulary.DB

import scala.concurrent.ExecutionContext.Implicits.global


object SimpleMovieReco2 extends App with DBPedia with  SesameContext{

  val movie = DBEntity("The_Terminator")

  val requestStarringF = s"""
  select distinct ?movies  where {
      <$movie> <http://dbpedia.org/ontology/starring> ?starring.
      ?movies <http://dbpedia.org/ontology/starring> ?starring.
  } """ executeAndGet "movies"

  val requestDirectorF = s"""
  select distinct ?movies where {
      <$movie> <http://dbpedia.org/ontology/director> ?director.
      ?movies <http://dbpedia.org/ontology/director> ?director.
  } """ executeAndGet "movies"


  val requestSubjectF = s"""
  select distinct ?movies where {
      <$movie> <http://purl.org/dc/terms/subject> ?subject.
       ?movies <http://purl.org/dc/terms/subject> ?subject.
  } """ executeAndGet "movies"


  for {
    requestStarring <- requestStarringF
    requestDirector <- requestDirectorF
    requestSubject <- requestSubjectF
  } yield {

    val nodes = requestStarring ++ requestDirector ++ requestSubject

    val movies: List[String] = nodes.onlyEntities.filter(n => n != movie).map(_.uri)

    val by: List[(String, Int)] = movies.groupBy(identity).map(m => (m._1, m._2.size)).toList.sortBy(-_._2).take(10)

    by.foreach(println)

  }

}
