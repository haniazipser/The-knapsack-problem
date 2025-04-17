import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class Tests{
    @Test
    public void recTest() {
        //pojemnosc, 0, wagi, 0,ile

        int pojemnosc = 3;
        int ile = 5;
        int wagi[]= {1, 2, 3, 4, 5};
        Source.brak  = true;
        Source.maska = new boolean [ile];//maska bitowa trzymajaca informacje ktore wagi wzielismy
        Source.szukanaPojemnosc = pojemnosc;
        Source.Rec(pojemnosc, 0, wagi, 0,ile);
        assertArrayEquals(new boolean[]{true,true,false,false,false},Source.maska);
    }
    public void recTest1() {
        //pojemnosc, 0, wagi, 0,ile

        int pojemnosc = 10;
        int ile = 6;
        int wagi[]= {3, 6, 2, 4, 9, 5};
        Source.brak  = true;
        Source.maska = new boolean [ile];//maska bitowa trzymajaca informacje ktore wagi wzielismy
        Source.szukanaPojemnosc = pojemnosc;
        Source.Rec(pojemnosc, 0, wagi, 0,ile);
        assertArrayEquals(new boolean[]{true,true,false,false,false},Source.maska);
    }

    public void recTest2() {
        //pojemnosc, 0, wagi, 0,ile

        int pojemnosc = 10;
        int ile = 6;
        int wagi[]= {3, 6, 2, 4, 9, 5};
        Source.brak  = true;
        Source.maska = new boolean [ile];//maska bitowa trzymajaca informacje ktore wagi wzielismy
        Source.szukanaPojemnosc = pojemnosc;
        Source.Rec(pojemnosc, 0, wagi, 0,ile);
        assertArrayEquals(new boolean[]{true,true,false,false,false},Source.maska);
    }

    @Test
    public void iterTest() {
        int pojemnosc = 21;
        int ile = 3;
        int wagi[]= {5,6,7};
        Symulacja.Iter(pojemnosc, wagi, ile);
        assertArrayEquals(new boolean[]{false,false,false},Symulacja.maska);
    }

    @Test
    public void iterTest1() {
        int pojemnosc = 20;
        int ile = 5;
        int wagi[]= { 11, 8, 7, 6, 5};
        Source.Rec(pojemnosc, 0, wagi, 0,ile);
        assertArrayEquals(new boolean[]{false,true,true,false,true},Symulacja.maska);
    }


    @Test
    public void iterTest2() {

        int pojemnosc = 7;
        int ile = 4;
        int wagi[]= { 6, 5, 3, 2};
        Source.brak  = true;
        Source.maska = new boolean [ile];//maska bitowa trzymajaca informacje ktore wagi wzielismy
        Source.szukanaPojemnosc = pojemnosc;
        Symulacja.Iter(pojemnosc, wagi, ile);

        assertArrayEquals(new boolean[]{false,true,false,true},Symulacja.maska);
    }

}
