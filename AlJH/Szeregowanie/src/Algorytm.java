import java.util.*;

public class Algorytm {
    Scanner cin = new Scanner(System.in);
    private int n;
    private LinkedList<HarmZadan> harmonogram = new LinkedList<>();
    private LinkedList<HarmZadan> n1 = new LinkedList<>();
    private LinkedList<HarmZadan> n2 = new LinkedList<>();

    public void menu() {
        while (true) {
            System.out.println("\n1: Wprowadz z klawiatury");
            System.out.println("2: Wprowadz losowe");
            System.out.println("3: Wyznacz trase");
            System.out.println("0: Wyjscie");
            switch (cin.nextInt()) {
                case 1:
                    stworzHarmonogramKlaw();
                    break;
                case 2:
                    stworzHarmonogramRand();
                    break;
                case 3:
                    wyznaczPrzedzialy();
                    break;
                case 9:
                    return;
            }
        }
    }

    private void stworzHarmonogramKlaw(){
        harmonogram.clear();
        System.out.print("Podaj ilosc zadan: ");
        n = cin.nextInt();
        int i = 1;
        while (i<=n){
            HarmZadan harm = new HarmZadan();

            System.out.println(String.format("Podaj czas na wykonanie %d zadania dla maszyny 1", i));
            harm.setCzasNaPierMasz(cin.nextInt());

            System.out.println(String.format("Podaj czas na wykonanie %d zadania dla maszyny 2", i));
            harm.setCzasNaDruMasz(cin.nextInt());

            harmonogram.add(harm);
            i++;
        };
        wypisz(harmonogram);
    }

    private void stworzHarmonogramRand(){
        harmonogram.clear();
        Random rand = new Random();
        System.out.print("Podaj ilosc zadan: ");
        n = cin.nextInt();
        do {
            HarmZadan harm = new HarmZadan();
            harm.setCzasNaPierMasz(rand.nextInt(100)+1);
            harm.setCzasNaDruMasz(rand.nextInt(100)+1);

            harmonogram.add(harm);
            n--;
        }while(n>0);
        wypisz(harmonogram);
    }

    private void wypisz(LinkedList<HarmZadan> harm){
        harm.stream().forEachOrdered(v -> System.out.print(v.getCzasNaPierMasz() + " "));
        System.out.println();
        harm.stream().forEachOrdered(v -> System.out.print(v.getCzasNaDruMasz() + " "));
    }

    private void wyznaczPrzedzialy(){
        for(HarmZadan h : harmonogram){
            if(h.getCzasNaPierMasz() < h.getCzasNaDruMasz()){
                n1.add(h);
            }else {
                n2.add(h);
            }
        }
        Collections.sort(n1);
        Collections.sort(n2);
        harmonogram.clear();
        harmonogram.addAll(n1);
        harmonogram.addAll(n2);

        wyznaczCzas();
    }

    private void wyznaczCzas(){
        int czasMasz1 = 0;
        int czasMasz2 = 0;
        int T = 0;
        for(HarmZadan h : harmonogram){
            czasMasz1 += h.getCzasNaPierMasz();
            if(czasMasz1 >= czasMasz2){
                czasMasz2 = czasMasz1 + h.getCzasNaDruMasz();
            }else {
                czasMasz2 += h.getCzasNaDruMasz();
            }
        }
        T = czasMasz2 > czasMasz1 ? czasMasz2 : czasMasz1;
        System.out.println("Czas T: " + T);
    }
}
