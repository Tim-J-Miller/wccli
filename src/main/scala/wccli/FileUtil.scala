package wccli

import java.io.File
import scala.io.BufferedSource
import scala.io.Source
import java.io.FileNotFoundException


/**
  * performs basic file IO
  * 
  * all of the file IO logic exists in this util, methods should only return strings or data structures
  */
object FileUtil {

    
    def getTopLevelFiles(): Array[String] = {
        val currentDir = new File(".")
        currentDir.listFiles()
            .filter((f: File) => {f.isFile() && !f.isHidden()})
            .map(_.getName())
    }

    /**
      * retreives the texts content of a file as a string
      * 
      * accessing files is an exception-prone process -- it's very easy for some problems to occur
      * keep this in mind
      *
      * @param filename
      * @return
      */
    def getTextContent(filename: String): String = {

        var openedFile: BufferedSource = null

        try{
            openedFile = Source.fromFile(filename)
            openedFile.getLines().mkString(" ")
        } 
        finally {
            if (openedFile != null) openedFile.close()
        }
    }
}