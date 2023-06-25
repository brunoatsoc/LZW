//Aqui importamos as classes que iremos precisar no codigo
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Lzw{
    private String fileName = "file.txt";
    private String fileAnswer;
    private int position = 0;
    private Map<String, Integer> strCode = new HashMap<String, Integer>();
    private List<String> leters = new ArrayList<String>();
    private List<Integer> codes = new ArrayList<Integer>();
    PrintWriter fileout;

    public Lzw(String fileName, String fileAnswer)throws IOException{
        this.fileName = fileName;
        this.fileAnswer = fileAnswer;
        readFile(fileName, leters);
        fileout = new PrintWriter(new FileWriter(fileAnswer));

        //Cria o arquivo
        try {
            File myObj = new File(fileAnswer);
            if(myObj.createNewFile()){
                System.out.println("File created: " + myObj.getName());
            }else{
                System.out.println("File already exists.");
            }
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void encoder(){
        for(int i = 0; i < leters.size(); i++){
            if(!strCode.containsKey(leters.get(i))){
                strCode.put(leters.get(i), ++position);
            }
        }

        int count = 0;
        String p = leters.get(count);
        String c = "";
        count++;

        try{
            for(; count < leters.size(); count++){
                c = leters.get(count);

                if(strCode.containsKey(p + c)){
                    fileout.printf("p = %s, c = %s, p + c  = %s\n", p, c, p + c);
                    p = p + c;
                }else{
                    fileout.printf("p = %s, c = %s, p + c  = %s\n", p, c, p + c);
                    codes.add(strCode.get(p));
                    strCode.put(p + c, ++position);
                    p = c;
                }
            }

            if(strCode.containsKey(p + c)){
                fileout.printf("p = %s, c = %s, p + c  = %s\n", p, c, p + c);
                p = p + c;
            }else{
                fileout.printf("p = %s, c = %s, p + c  = %s\n", p, c, p + c);
                codes.add(strCode.get(p));
                strCode.put(p + c, ++position);
                p = c;
            }

            fileout.printf("\n");

            printMap();
            printCode();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void decoder(){
        String[] str = new String[codes.size()];
        String k = "";

        for(int i = 0; i < codes.size(); i++){
            int var = codes.get(i);

            for (Map.Entry<String, Integer> entry : strCode.entrySet()) {
                if (entry.getValue() == var) {
                    k = entry.getKey();
                    break;
                }
            }

            str[i] = k;
        }
        printDecoder(str);
    }

    private void readFile(String fileName, List<String> leters)throws IOException{
        Scanner scn = new Scanner(new FileReader(fileName));

        try(BufferedReader bfr = new BufferedReader(new FileReader(fileName))){
            int valAscii;

            while((valAscii = bfr.read()) != -1){
                Character c = (char)valAscii;
                String s = c.toString();
                leters.add(s);
            }
        }catch(IOException e){
            System.out.println("Erro on read file: " + e.getMessage());
        }
    }

    private void printCode(){
        try{
            for(int i = 0; i < codes.size(); i++){
                fileout.printf("%d, ", codes.get(i));
            }
            fileout.printf("\n\n");

        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void printDecoder(String[] str){
        try{
            for(int i = 0; i < codes.size(); i++){
                fileout.printf("%s", str[i]);
            }
            fileout.printf("\n");

            fileout.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void printMap(){
        //Escreve a matriz no arquivo
        try{
            for(Map.Entry<String, Integer> me : strCode.entrySet()){
                fileout.println(me.getKey() + ":" + me.getValue());
            }
            fileout.printf("\n");

            System.out.println("success...");
        }catch(Exception e){
            System.out.println(e);
        }
    }
}