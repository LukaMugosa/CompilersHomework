package me.ac.ucg.kompajleri;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        String source = "C:\\Users\\Kolrisnik\\Desktop\\PMF\\Kompajleri\\PrviDomaciZadatak\\src\\me\\ac\\ucg\\kompajleri\\test1.txt";
        System.out.println(source);
        try {
            Scanner.init(new InputStreamReader(new FileInputStream(source)));
            Parser.parse();
            System.out.println(Parser.errors + " errors detected");
        } catch (IOException e) {
            System.out.println("-- cannot open input file " + source);
        }
    }
}
