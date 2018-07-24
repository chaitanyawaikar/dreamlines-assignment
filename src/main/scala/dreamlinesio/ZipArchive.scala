package dreamlinesio

import java.io.{File, FileOutputStream, InputStream, OutputStream}
import java.util.zip.{ZipEntry, ZipFile}

import scala.collection.JavaConversions._

object ZipArchive {

  val BUFSIZE = 4096
  val buffer = new Array[Byte](BUFSIZE)

  def unZip(source: String, targetFolder: String) = {
    println("File unzipping in progress ...")
    FileIsExist(source)
    {
      val zipFile = new ZipFile(source)

      unzipAllFile(zipFile.entries.toList, getZipEntryInputStream(zipFile)_, new File(targetFolder))
    }
    println("Unzipping completed..")
  }

  def FileIsExist(path:String)(expr: => Any) = {
    if(new File(path).exists)
      expr
  }

  /*---------------------------------------------------------------------------------
     * curry method , this methond can get the inputstream of a zip entry from zipFile
     *---------------------------------------------------------------------------------*/
  def getZipEntryInputStream(zipFile: ZipFile)(entry: ZipEntry) = zipFile.getInputStream(entry)

  def unzipAllFile(entryList: List[ZipEntry], getInputStream: (ZipEntry) => InputStream, targetFolder: File): Boolean = {

    entryList match {
      case entry :: entries =>

        if (entry.isDirectory)
          new File(targetFolder, entry.getName).mkdirs
        else
          saveFile(getInputStream(entry), new FileOutputStream(new File(targetFolder, entry.getName)))

        unzipAllFile(entries, getInputStream, targetFolder)

      case _ =>
        true
    }
  }

  /*=============================================================
   *
   * Read InputStream and write the data to OutputStream
   * the recursive method is writeToFile and bufferReader
   *
   *=============================================================*/
  def saveFile(fis: InputStream, fos: OutputStream) = {

    /*--------------------------------------------------------------
   * curry a method , the method can read data from InputStream
   *--------------------------------------------------------------*/
    def bufferReader(fis: InputStream)(buffer: Array[Byte]) = (fis.read(buffer), buffer)

    /*--------------------------------------------------------------
   * Write the data in the buffer to outputstream
   ---------------------------------------------------------------*/
    def writeToFile(reader: (Array[Byte]) => Tuple2[Int, Array[Byte]], fos: OutputStream): Boolean = {
      val (length, data) = reader(buffer)
      if (length >= 0) {
        fos.write(data, 0, length)
        writeToFile(reader, fos)
      } else
        true
    }

    try {
      writeToFile(bufferReader(fis)_, fos)
    } finally {
      fis.close
      fos.close
    }
  }

}