import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner wybor = new Scanner(System.in);
        int [][] macierz;
        while (true){
            System.out.println("\n1: Wprowadz z klawiatury");
            System.out.println("2: Wprowadz losowe");
            System.out.println("3: Wyczysc konsole");
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
                    return;
            }
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
        Scanner cin = new Scanner(System.in);
        int[][] macierzDwuWymirowa = new int[n][n];
        for(int i = 0; i<n; i++){
            System.out.print(String.format("Podaj liczby dla %s rzedu: ", i));
            for(int j=0; j<n; j++){
                macierzDwuWymirowa[i][j] = cin.nextInt();
            }
        }
        cin.close();
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