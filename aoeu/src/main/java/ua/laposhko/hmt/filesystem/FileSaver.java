package ua.laposhko.hmt.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Sergey Laposhko
 */
public class FileSaver {


    /**
     * Saves file to the file system.
     *
     * @param uploadedInputStream  the uploaded input stream
     * @param uploadedFileLocation the uploaded file location
     */
    public static void saveFile(InputStream uploadedInputStream,
                                String uploadedFileLocation) {
        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
