import java.io.IOException;

public class Main{
    public static void main(String[] args)throws IOException{
        Lzw l = new Lzw("file.txt", "Resposta.txt");

        l.encoder();
        l.decoder();
    }
}