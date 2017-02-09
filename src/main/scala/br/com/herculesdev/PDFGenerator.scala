package br.com.herculesdev

import java.io.{File, FileInputStream}

import org.apache.pdfbox.pdmodel.edit.PDPageContentStream
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}

object PDFGenerator {
  def convert(dir: File): Unit = {
    if (dir.listFiles().isEmpty) return ()
    dir.listFiles().filter(_.isDirectory).foreach { subdir =>
      val containsImages = filterJpgs(subdir).length > 0
      if (containsImages) compile(subdir)
      else convert(subdir)
    }
  }

  private def filterJpgs(dir: File): List[File] = dir.listFiles().filter(_.getName.endsWith(".jpg")).toList

  private def compile(dir: File): Unit = {
    val pdfName = s"${dir}.pdf"
    generatePDF(dir, pdfName)
  }

  private def generatePDF(dir: File, pdfName: String): Unit = {
    val pdf = new File(pdfName)
    if (!pdf.exists()) {
      val document = new PDDocument()
      val images = dir.listFiles().filter(_.getName.endsWith(".jpg")).sorted
      images.foreach { jpg =>
        val blankPage = new PDPage()
        val stream = new PDPageContentStream(document, blankPage)
        val in = new FileInputStream(jpg)
        val img = new PDJpeg(document, in)
        val borderWidth  = ((600 - img.getWidth) / 2) + 7
        val borderHeight = ((800 - img.getHeight) / 2)
        stream.drawImage(img, borderWidth, borderHeight)
        stream.close()
        document.addPage(blankPage)
      }
      document.save(pdfName)
      document.close()
    }
  }
}