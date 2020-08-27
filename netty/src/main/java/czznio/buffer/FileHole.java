package czznio.buffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileHole {


    public static void main(String[] argv)
            throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile ("test.txt", "r");
// Set the file position
        randomAccessFile.seek (1000);
// Create a channel from the file
        FileChannel fileChannel = randomAccessFile.getChannel( );
// This will print "1000"
        System.out.println ("file pos: " + fileChannel.position( ));
// Change the position using the RandomAccessFile object
        randomAccessFile.seek (500);
// This will print "500"
        System.out.println ("file pos: " + fileChannel.position( ));
// Change the position using the FileChannel object
        fileChannel.position (200);
// This will print "200"
        System.out.println ("file pos: " + randomAccessFile.getFilePointer( ));
    }
}
