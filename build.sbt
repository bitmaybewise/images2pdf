import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "br.com.herculesdev",
      scalaVersion := "2.12.1",
      version      := "1.0.0"
    )),
    name := "Images2PDF",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += pdfBox,
    libraryDependencies += imgScalr
  )
