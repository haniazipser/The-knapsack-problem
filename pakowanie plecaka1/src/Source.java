//Hanna Zipser
/*
Opis programu:
Wersja rekurencyjna:
W kazdym wywolaniu funkcji rozpatrujemy dwa przypadki: przypadek ze wzielismy przedmiot i przypadek w ktorym go nie wzielismy. Dla obu sytuacji wywolujemy rekurencje
dalej, sprawdzajac kolejny przedmiot. Jesli nasza tymczasowa waga plecaka osiegnela docelowa wage, konczymy rekurencje. Reukrencja konczy tez wywolywania, jesli tymczasowa
waga przekroczy pojemnosc naszego plecaka lub skoncza nam sie przedmioty. Dzieki tym warunkom mamy troche mniej wywolan. W ten sposob pesymistycznie sprawdzimy kazda
mozliwa kombinacje, czyli bedziemy miec 2^n wywolan. Aby w przypadku znalezienia rozwiazania odtworzyc zabrane przedmioty, korzystam z maski bitowej. Jesli dany
przedmiot chce wziac, w masce pod tym samym indeksem ustawiam true, a jesli nie to false. Po zakonczeniu rekurencji wystarczy "przylozyc" maske do tablicy wag, i
wyswietlic jedynie te elementy, gdzie maska ma ustawione true.

Symluacja rekurencji:
Logicznie symulacja dziala identycznie jak wersja rekurencyjna. Korzysta z wlasnego stosu, na ktory odkladane sa parametry wywolania funkcji, czyli w tym przypadku
indeks aktualnie sprawdzanego elementu oraz aktualna waga. Na stosie przechowuje rowniez adres powrotu, informacje potrzebna do wyznaczenia zachowania programu
po zdjeciu danego segmentu ze stosu. Podobnie jak na wykladzie symulacja opiera sie na wywolywanym w petli switchu, sprawdzajacym w ktorym miejscu kodu sie znajdujemy.
Label 1 odpowiada pierwszemy wywolaniu funckji oraz tworzy segment danych z adresem 6, gdzie label 6 konczy cala funckje. Gdy zdejmiemy ten element ze stosu, caly
program ulegnie zakonczeniu. Label 2 i 3 odpowiadaja zabieraniu danego elementu. Label 2 zawiera kod funkcji, a label 3 tworzy nowy segment danych, wklada go na stos
i wywoluje funcje rekurencyjnie. Jesli pierwsza rekurencja odpowiadajaca za zabieranie elementow sie zakonczy (label 4), wywolana zostanie rekurencja, ktora pomija
element (label 5). Nastepnie wroci do label 2. W ten sposob symulujemy funckje rekurencyjna.
 */
import java.util.Scanner;

class Parametry{ //parametry wywolania funckji
    int indeks;//indeks aktualnie sprawdzanego
    int pojemnosc;//aktualna waga plecaka
    int adres;//adres segmentu danych
    Parametry(int indeks, int pojemnosc, int adres){//kontruktor
        this.indeks = indeks;
        this.adres = adres;
        this.pojemnosc = pojemnosc;
    }
}
class StosRekurencja{ //stos wywolan rekurencyjnych
    Parametry tab[];//tablica parametrow
    int n;//indeks wolnego elementu
    StosRekurencja(int ile){//kontruktor
        tab = new Parametry[ile];
        n = 0;
    }
    void Push(Parametry a){//wloz na stos
        tab [n] = a;
        n++;
    }
    Parametry Pop(){//zdejmij ze stosu
        n--;
        return tab[n];
    }

    Parametry Peek(){//zajrzyj na szczyt stosu
        return tab [n - 1];
    }
}

class Symulacja{//klasa symulacji rekurencji
    static int szukanaPojemnosc;
    static int wagi[];
    static boolean maska[];//maska bitowa przechowujaca informacje o tym ktore elementy wzielismy
    static int ile; //ilosc elementow
    static StosRekurencja stos; //stos wywolan rekurenccyjnych
    static int label; //aktualne miejsce w kodzie
    static Parametry p; //parametry wywolania
    public static void WyswietlIter(int wagi[], boolean maska[], int ile){//wyswietlenie zawartosci plecaka

        StringBuilder wynik = new StringBuilder();
        wynik.append("ITER: " + szukanaPojemnosc+ " =");
        for (int i = 0; i < ile; i++){
            if (maska[i]){ //jesli element oznaczony w masce jako wziety
                wynik.append(" ").append(wagi[i]);
            }
        }
        System.out.println(wynik.toString());
    }

    public static void Iter(int sp, int w[], int i){//funckja rozwiazujaca problem iteracyjnie, przyjmuje szukana pojemnosc, tablice wag oraz ilosc elementow
        szukanaPojemnosc = sp;
        wagi = w;
        ile = i;
        label = 1;
        stos = new StosRekurencja(1000000);//stos do symulacji rekurencji
        maska = new boolean[ile];//maska bitowa trzymajaca informacje o tym ktore przedmioty zostaly zabrane
        boolean brak = true;//brak rozwiazania
        boolean koniec = false;//przerwanie glownej petli
        while (!koniec) {
            switch (label) {
                case 1: // wywolanie poczatkowe
                    p = new Parametry(0, 0, 6); // tworzymy segment (0 , 6)
                    stos.Push(p);
                    label = 2;//idz do wywolania pierwszej rekurencji
                    break;
                case 2:////kod pierwszej rekurencji
                    p = stos.Peek();
                    if (p.pojemnosc == szukanaPojemnosc && brak == true) {//warunek stopu
                        WyswietlIter(wagi, maska, ile);
                        brak = false;
                        label = 6;//zakoncz rekurencje
                    }else if (p.indeks < ile && p.pojemnosc < szukanaPojemnosc && brak == true) {//mamy miejsce w plecaku i dostepne przedmioty
                        maska[p.indeks] = true;//oznacz w masce jako zabrany
                        label = 3;//wywolaj samego siebie rekurencyjnie
                    }else {
                        label = 4;//koniec pierwszej rekurencji
                    }
                    break;
                case 3: // wywolanie pierwszej  rekurencji
                    Parametry nPar = new Parametry(p.indeks + 1, p.pojemnosc+wagi[p.indeks], 5);//noway segment danych
                    stos.Push(nPar);
                    label = 2;
                     // wesccie do metody
                    break;
                case 4://koniec pierwszej rekurencji
                    p = stos.Peek();
                    label = p.adres; // (label==5 lub 6)
                    stos.Pop();//zdjejmij wywolanie ze stosu
                    break;
                case 5://druga rekurencja
                    p = stos.Peek();
                    maska[p.indeks] = false;//ustaw w masce jako nie zabrany
                    p.indeks++;
                    label = 2;
                    break;
                default://6
                    koniec = true;
            }
        }
    }

}

public class Source {
    public static void WyswietlRec(int wagi[], boolean maska[], int ile){//wyswietlanie zawartosci plecaka

        StringBuilder wynik = new StringBuilder();
        wynik.append("REC: " + szukanaPojemnosc+ " =");
        for (int i = 0; i < ile; i++){
            if (maska[i]){//jesli w masce przedmiot jest oznaczony jako wziety
                wynik.append(" ").append(wagi[i]);
            }
        }
        System.out.println(wynik.toString());
    }
    //funkcja przyjmuje pojemnosc plecaka, chwilowe jego zapelnienie, tablice wag, indeks sprawdzanego obecnie przedmiotu, maske bitowa oraz ilosc przedmiotow
    public static void Rec(int szukanaPojemnosc, int pojemnosc, int wagi[], int indeks, int ile){//funckja rozwiazujaca problem rekurencyjnie
        if (pojemnosc == szukanaPojemnosc && brak == true){//zapelnilismy plecak oraz jest to pierwsze wystapienie
            brak = false;//odznacz, ze juz znalezlismy odpowiedz
        }else if (indeks < ile && pojemnosc < szukanaPojemnosc && brak ==true ){//mamy jeszcze miejsce w plecaku oraz dostepne przedmioty
            pojemnosc += wagi [indeks];//wez przedmiot
            maska[indeks] = true;//oznacz w masce jako zabrany
            Rec(szukanaPojemnosc, pojemnosc,wagi,indeks + 1, ile);//sprawdzaj dalej z uwzglednieniem tego przedmiotu
            if(brak == true) {
                pojemnosc -= wagi[indeks];//nie bierz przedmiotu
                maska[indeks] = false;//oznacz w masce jako nie zabrany
                Rec(szukanaPojemnosc, pojemnosc, wagi, indeks + 1, ile);//sprawdzaj dalej nie uwzgledniajac tego przedmiotu
            }
        }
    }

    static boolean brak;//zmienna oznaczajaca brak elementu
    static boolean maska[];
    static int szukanaPojemnosc;
    public static void main(String[] args) {
        //odczyt danych
        Scanner scanner = new Scanner(System.in);
        int z = scanner.nextInt();
        for (int i = 0; i < z; i++){
            brak = true;
            int pojemnosc =scanner.nextInt();
            int ile = scanner.nextInt();//ile elementow
            int wagi[] = new int [ile]; //tablica wag
            for (int j = 0; j < ile; j++){
                wagi [ j ] = scanner.nextInt();
            }

            maska = new boolean [ile];//maska bitowa trzymajaca informacje ktore wagi wzielismy
            szukanaPojemnosc = pojemnosc;
            Rec(pojemnosc, 0, wagi, 0,ile);//funckja rekurencyjna
            if (brak){
                System.out.println("BRAK");
            } else {
                WyswietlRec(wagi, maska, ile); //wyswietlenie
                Symulacja.Iter(szukanaPojemnosc, wagi, ile);//funckja iteracyjna
            }
        }
    }
}

