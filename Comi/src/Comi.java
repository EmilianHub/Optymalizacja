import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Comi {
    private int[][] macierz;
    private final Scanner wybor = new Scanner(System.in);
    private final Scanner cin = new Scanner(System.in);
    private String path;
    private int dl;
    private int wierzcholekPoczatek = 0;
    private int wierzcholekKoniec = 0;

    public void menu() {
        while (true) {
            System.out.println("\n1: Wprowadz z klawiatury");
            System.out.println("2: Wprowadz losowe");
            System.out.println("3: Zapisz tablice do pliku");
            System.out.println("4: Odczytaj z pliku");
            System.out.println("5: Wyznacz trase");
            System.out.println("0: Wyjscie");
            switch (wybor.nextInt()) {
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
                    String trasa = wyznaczDlugosc(macierz);
                    System.out.println("Trasa: " + trasa);
                    break;
                case 9:
                    return;
            }
        }
    }

    private String wyznaczDlugosc(int[][] macierz){
        System.out.print("Podaj wierzcholek: ");
        wierzcholekPoczatek = wybor.nextInt()-1;
        ArrayList<Integer> d = new ArrayList<>();
        for (int ints : macierz[wierzcholekPoczatek]){
            d.add(ints);
        }
        Integer max = Collections.max(d);
        for(int i = 0; i<d.size(); i++){
            if(d.get(i).equals(max)){
                wierzcholekKoniec = i;
            }
        }
        path = String.format("%s - %s", wierzcholekPoczatek+1, wierzcholekKoniec+1);
        dl = macierz[wierzcholekPoczatek][wierzcholekKoniec] + macierz[wierzcholekKoniec][wierzcholekPoczatek];
        d.set(wierzcholekKoniec, 0);

        return obliczFazy(d);
    }

    private String obliczFazy(ArrayList<Integer> d) {
        Integer max = Collections.max(d);
        int pozycja = 0;
        for(int i = 0; i<d.size(); i++){
            if(d.get(i).equals(max)){
                pozycja = i;
            }
        }

        List<Integer> ks = new ArrayList<>();
        List<Integer> split = Arrays.stream(path.split(" - ")).map(Integer::parseInt).collect(Collectors.toList());

        for(int i = 0; i < split.size()-1; i++){
            int K = macierz[split.get(i)-1][pozycja] + macierz[pozycja][split.get(i+1)-1] - macierz[split.get(i)-1][split.get(i+1)-1];
            ks.add(K);
        }
        int K2 = macierz[wierzcholekKoniec][pozycja] + macierz[pozycja][wierzcholekPoczatek] - macierz[wierzcholekKoniec][wierzcholekPoczatek];
        ks.add(K2);

        int smallerResult = Collections.min(ks);
        path = path.replace(String.valueOf(wierzcholekKoniec+1), pozycja+1 + " - 1");
        dl += smallerResult;
        d.set(pozycja, 0);

        long isDarrayEmpty = d.stream().filter(values -> values > 0).count();
        if(isDarrayEmpty == 0){
            return path + String.format(" - %s", wierzcholekPoczatek+1);
        }
        else {
            return obliczFazy(d);
        }
    }

    private void zapiszDoPlikuONazwie(int[][] macierz) {
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

    public void odczytajZPliku(){
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

    public int[][] wypelnijMacierzLosowo(int n){
        Random random = new Random();
        int[][] macierzDwuWymirowa = new int[n][n];
        for(int i = 0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i == j){
                    macierzDwuWymirowa[i][j] = -999;
                }else {
                    macierzDwuWymirowa[i][j] = random.nextInt(100) + 1;
                }
            }
        }
        return macierzDwuWymirowa;
    }

    public int[][] wypelnijMacierzKlawiatura(int n){
        int[][] macierzDwuWymirowa = new int[n][n];
        for(int i = 0; i<n; i++){
            System.out.print(String.format("Podaj liczby dla %s rzedu: ", i));
            for(int j=0; j<n; j++){
                if(i == j){
                    macierzDwuWymirowa[i][j] = -999;
                }else {
                    macierzDwuWymirowa[i][j] = cin.nextInt();
                }

            }
        }
        return macierzDwuWymirowa;
    }

    public void wypiszMacierz(int[][] macierz){
        for(int i = 0; i< macierz.length; i++){
            for(int j=0; j< macierz.length; j++){
                System.out.print(macierz[i][j] + " ");
            }
            System.out.println();
        }
    }
}