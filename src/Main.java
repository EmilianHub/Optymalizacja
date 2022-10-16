import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static int[][] macierz;
    public static Scanner wybor = new Scanner(System.in);
    public static Scanner cin = new Scanner(System.in);

    public static void main(String[] args) {
        while (true){
            System.out.println("\n1: Wprowadz z klawiatury");
            System.out.println("2: Wprowadz losowe");
            System.out.println("3: Zapisz tablice do pliku");
            System.out.println("4: Odczytaj z pliku");
            System.out.println("5: Dokonaj redukcji");
            System.out.println("0: Wyjscie");
            switch (wybor.nextInt()){
                case 1:
                    System.out.print("Podaj rozmiar macierzy: ");
                    macierz = wypelnijMacierzKlawiatura(wybor.nextInt());
                    wypiszMacierz(macierz);
                    break;
                case 2:
                    System.out.print("Podaj rozmiar macierzy: ");
                    macierz = wypelnijMacierzLosowo(wybor.nextInt());
                    wypiszMacierz(macierz);
                    break;
                case 3:
                    zapiszDoPlikuONazwie(macierz);
                    break;
                case 4:
                    odczytajZPliku();
                    break;
                case 5:
                    macierz = dokonajRedukcji(macierz);
                    wypiszMacierz(macierz);
                    break;
                case 9:
                    return;
            }
        }
    }

    private static int[][] rozwiazPrzydzial(int[][] macierz){
        int[][] macierzTymczasowa = macierz;
        int zakresloneZera = 0;
        boolean flaga = true;
        while(flaga)
        {
            for(int i = 0; i< macierzTymczasowa.length; i++){
                int napotkaneZera = 0;
                int pozycja = -1;
                for(int j = 0; j< macierzTymczasowa.length; j++){
                    if(macierzTymczasowa[i][j]==0){
                        napotkaneZera++;
                        if(pozycja == -1){
                            pozycja=j;
                        }
                    }
                }
                if(napotkaneZera==1){
                    for (int j=0; j<macierzTymczasowa.length; j++){
                        if(macierzTymczasowa[i][j] == 0){
                            macierzTymczasowa[i][j] = -2;
                        }
                        if(macierzTymczasowa[j][pozycja] == 0){
                            macierzTymczasowa[j][pozycja] = -2;
                        }
                    }
                    macierzTymczasowa[i][pozycja] = -1;
                    zakresloneZera++;
                }
            }
            List<Long> min = Arrays.stream(macierzTymczasowa).map(tablica -> IntStream.of(tablica).filter(value -> value==0).count())
                    .filter(values -> values>0).collect(Collectors.toList());
            if (min.isEmpty()){
                flaga = false;
            }
        }
        if(zakresloneZera == macierz.length)
        {
            return macierzTymczasowa;
        }
        else {
            return zkreslMacierz(macierzTymczasowa);
        }
    }

    private static int[][] zkreslMacierz(int[][] macierz) {
        for(int i = 0; i< macierz.length; i++) {
            boolean wasFound = false;
            for(int j = 0; j< macierz.length; j++){
                if(macierz[i][j]==-1){
                    wasFound=true;
                    break;
                }
            }
            if(!wasFound){
                int drugiWierszZera = 0;
                int kolumnaZera = 0;
                for (int j = 0; j< macierz.length; j++){
                    if(macierz[i][j]==-2) {
                        for (int u = 0; u < macierz.length; u++) {
                            if (macierz[u][j] == -1) {
                                drugiWierszZera = u;
                                kolumnaZera = j;
                                break;
                            }
                        }
                    }
                }
                for (int j = 0; j< macierz.length; j++){
                    for (int u = 0; u < macierz.length; u++) {
                        if (macierz[j][u] < 0) {
                            macierz[j][u] = 0;
                        }
                    }
                }
                for(int j = 0; j< macierz.length; j++){
                    if(macierz[j][kolumnaZera] > 0){
                        macierz[j][kolumnaZera] = -macierz[j][kolumnaZera];
                    }
                    else {
                        macierz[j][kolumnaZera] = 0;
                    }
                }
                for(int p = 0; p < macierz.length; p++){
                    for (int l = 0; l < macierz.length; l++){
                        if(p != drugiWierszZera && p != i){
                            if(macierz[p][l] < 0){
                                macierz[p][l] = 100+(-macierz[p][l]);
                            }
                            else {
                                macierz[p][l] = -macierz[p][l];
                            }
                        }
                    }
                }
            }
        }
        return powiekszMacierz(macierz);
    }

    private static int[][] powiekszMacierz(int[][] macierz) {
        int min = macierz[0][0];
        for(int i = 0; i< macierz.length; i++) {
            for (int[] ints : macierz) {
                if (min > ints[i] && ints[i] > 0) {
                    min = ints[i];
                }
            }
        }
        for(int i=0; i<macierz.length; i++){
            for (int j=0; j<macierz.length; j++){
                if(macierz[i][j] < 0){
                    macierz[i][j] = -macierz[i][j];
                }
                else if(macierz[i][j] > 100){
                    macierz[i][j] = macierz[i][j]-100+min;
                }
                else if(macierz[i][j] != 0) {
                    macierz[i][j] -= min;
                }
            }
        }
        return rozwiazPrzydzial(macierz);
    }

    private static int[][] dokonajRedukcjiwKolumnie(int [][] macierz){
        for(int i = 0; i< macierz.length; i++) {
            int min = macierz[i][i];
            for (int[] ints : macierz) {
                if (min > ints[i]) {
                    min = ints[i];
                }
            }
            for(int j = 0; j< macierz.length; j++){
                int wartoscMacierzy = macierz[j][i];
                if(wartoscMacierzy > 0){
                    wartoscMacierzy -= min;
                }
                if(wartoscMacierzy < 0){
                    wartoscMacierzy = 0;
                }
                macierz[j][i] = wartoscMacierzy;
            }
        }
        return rozwiazPrzydzial(macierz);
    }

    private static int[][] dokonajRedukcji(int[][] macierz) {
        if (Objects.isNull(macierz)){
            System.out.println("Macierz jest pusta\nProces przerwany");
            return null;
        }
        LinkedList<Integer> min = Arrays.stream(macierz).map(tablica -> Arrays.stream(tablica).min().getAsInt())
                .collect(Collectors.toCollection(LinkedList::new));
        for(int i = 0; i< macierz.length; i++){
            for(int j = 0; j< macierz.length; j++){
                int wartoscMacierzy = macierz[i][j];
                if(wartoscMacierzy > 0){
                    wartoscMacierzy -= min.get(i);
                }
                if(wartoscMacierzy < 0){
                    wartoscMacierzy = 0;
                }
                macierz[i][j] = wartoscMacierzy;
            }
        }
        return dokonajRedukcjiwKolumnie(macierz);
    }

    private static void zapiszDoPlikuONazwie(int[][] macierz) {
        if (Objects.isNull(macierz)){
            System.out.println("Macierz jest pusta\nZapis do pliku przerwany");
            return;
        }
        try{
            System.out.print("Podaj nazwe pliku: ");
            String nazwaPliku = cin.nextLine();
            File file = new File(nazwaPliku);
            if(!file.exists()){
                System.out.println("Plik zostal utworzony");
                file.createNewFile();
            }
            if(file.canWrite()){
                FileWriter fileWriter = new FileWriter(file);
                Formatter formatter = new Formatter(fileWriter);
                for(int i = 0; i< macierz.length; i++){
                    for(int j=0; j< macierz.length; j++){
                        if(j == macierz.length-1){
                            formatter.format("%d", macierz[i][j]);
                        }
                        else {
                            formatter.format("%d ", macierz[i][j]);
                        }
                    }
                    if(i != macierz.length-1){
                        formatter.format("\r\n");
                    }
                }
                formatter.close();
                fileWriter.close();
            }
            System.out.println("Plik zostal zapisany");
        }catch (Exception e){
            System.out.println("Wystapil problem podczas zapisu do pliku");
        }
    }

    public static void odczytajZPliku(){
        try{
            System.out.print("Podaj nazwe pliku: ");
            String nazwaPliku = cin.nextLine();
            File file = new File(nazwaPliku);
            if (file.exists()){
                System.out.print("Podaj rozmiar macierzy: ");
                int rozmiarMacierzy = wybor.nextInt();
                macierz = new int[rozmiarMacierzy][rozmiarMacierzy];
                Scanner fileScanner = new Scanner(file);
                while(fileScanner.hasNextLine()){
                    for (int i = 0; i<rozmiarMacierzy; i++){
                        for (int j = 0; j<rozmiarMacierzy; j++){
                            macierz[i][j] = fileScanner.nextInt();
                        }
                    }
                }
                fileScanner.close();
                wypiszMacierz(macierz);
            }
            else{
                System.out.println("Plik nie istnieje");
            }
        }catch (Exception e){
            System.out.println("Wystapil blad podczas odczytu pliku");
        }
    }

    public static int[][] wypelnijMacierzLosowo(int n){
        Random random = new Random();
        int[][] macierzDwuWymirowa = new int[n][n];
        for(int i = 0; i<n; i++){
            for(int j=0; j<n; j++){
                macierzDwuWymirowa[i][j] = random.nextInt(100) +1;
            }
        }
        return macierzDwuWymirowa;
    }

    public static int[][] wypelnijMacierzKlawiatura(int n){
        int[][] macierzDwuWymirowa = new int[n][n];
        for(int i = 0; i<n; i++){
            System.out.print(String.format("Podaj liczby dla %s rzedu: ", i));
            for(int j=0; j<n; j++){
                macierzDwuWymirowa[i][j] = cin.nextInt();
            }
        }
        return macierzDwuWymirowa;
    }

    public static void wypiszMacierz(int[][] macierz){
        for(int i = 0; i< macierz.length; i++){
            for(int j=0; j< macierz.length; j++){
                System.out.print(macierz[i][j] + " ");
            }
            System.out.println();
        }
    }
}