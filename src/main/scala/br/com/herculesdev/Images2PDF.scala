package br.com.herculesdev

import java.io.File

object Images2PDF {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("You must provide the path of the images")
      return ()
    }
    val dirName = args(0)
    val dir = new File(dirName)
    if (!dir.isDirectory) {
      println("The parameter passed is not a folder")
      return ()
    }
    PDFGenerator.convert(dir)
  }
}
