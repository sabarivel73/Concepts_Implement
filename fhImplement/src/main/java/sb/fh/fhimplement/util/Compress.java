package sb.fh.fhimplement.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Compress {

    public static byte[] compress(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp =  new byte[4*1024];
        try{
            while (!deflater.finished()) {
                int count = deflater.deflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            return outputStream.toByteArray();
        } finally {
            deflater.end();
            try{
                outputStream.close();
            } catch (Exception ex) {}
        }
    }

    public static byte[] decompress(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp =  new byte[4*1024];
        try{
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                if(count == 0){
                    if(inflater.needsInput()) break;
                    if(inflater.needsDictionary()) throw new DataFormatException("Dictionary required");
                }
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ex) {}
        finally {
            inflater.end();
        }
        return outputStream.toByteArray();
    }

}
