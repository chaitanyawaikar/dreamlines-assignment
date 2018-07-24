package dreamlinesio;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloadTest {

    public static void downloadAndUnzipFile(String dirName, String url,String destination) {
        try {
            System.out.println("Downloading document...");
            saveFileFromUrlWithJavaIO(dirName + "\\prices.zip", url);
            System.out.println("Downloaded document.");
            ZipArchive.unZip(dirName + "\\prices.zip", destination);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // Using Java IO
    public static void saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(fileName);

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null)
                in.close();
            if (fout != null)
                fout.close();
        }
    }

}