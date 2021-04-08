package wccli

import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException
import scala.collection.mutable.Map

class Cli{

    val commandArgPattern: Regex = "(\\w+)\\s*(.*)".r
    
    def menu(): Unit ={
        printWelcome()
        var continueMenuLoop = true
        while (continueMenuLoop){
            printMenuOptions()
            var input = StdIn.readLine()
            input match {
                case commandArgPattern(cmd, arg) if cmd == "echo" => {
                    println(arg)
                }
                case commandArgPattern(cmd, arg) if cmd == "getFiles" => {
                    FileUtil.getTopLevelFiles().foreach(println)
                }
                case commandArgPattern(cmd, arg) if cmd == "first100chars" => {
                    val x = getFileTextContent(arg).toString
                    println(if (x.length() >= 100) x.substring(0,100) else x)
                }
                case commandArgPattern(cmd, arg) if cmd == "wordcount" => {
                    wordcount(arg)
                }
                case commandArgPattern(cmd, arg) if cmd == "exit" => {
                    continueMenuLoop = false
                }
                case commandArgPattern(cmd, arg) => {
                    println(s"Parsed command $cmd with args $arg did not correspond to an option")
                }
                case _ => {
                    println("Failed to parse a command")
                }
            }

        }
    }

    def printWelcome(): Unit = {
        println("Welcome to WC CLI")
    }

    def printMenuOptions(): Unit = {
        List(
            "Menu Options:",
            "echo [word]: repeats the word back to you",
            "getFiles: lists files in the current directory",
            "first100chars [filename]: reads the first 100 character of the file",
            "wordcount [filename]: counts the number of words in the file",
            "exit exits WC CLI:"
            ).foreach(println)
    }

    def getFileTextContent(filename: String): String = {
        try{
            FileUtil.getTextContent(filename)
        } catch {
            case fnfe: FileNotFoundException => {
                println(s"File not found: ${fnfe.getMessage()}")
                ""
          }
        }
    }

    def wordcount(filename: String) = {
        val wordCountMap = Map[String, Int]()
        //val words = FileUtil.getTextContent(filename).replaceAll("\\p{Punct}+", "").split("\\s+").map(_.toUpperCase())
        val words = getFileTextContent(filename).toString.replaceAll("\\p{Punct}+", "").split("\\s+").map(_.toUpperCase())

        // for(word <- words){
        //     if (wordCountMap.contains(word)){
        //         wordCountMap.update(word, wordCountMap(word)+1)
        //     } else {
        //         wordCountMap.addOne(word, 1)
        //     }
        // }
        // //wordCountMap.foreach({case (word, count) => {println(s"$word: $count")}})
        // wordCountMap.foreachEntry((word: String, count: Int) => {println(s"$word: $count")})

        if (words(0) != "") {
            words.groupMapReduce(identity)(_ => 1)(_ + _)
            .foreach({case (word, count) => {println(s"$word: $count")}})
        }
    }
}