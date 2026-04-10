package io.romangulyako.towers_of_hanoi;

public class Main {
    public static void main(String[] args) {
        int diskNumber = 10; // количество дисков, которые нужно переместить между стержнями


        Peg peg1 = new Peg(diskNumber, "From"); // Создаем стержень с заданным числом дисков и два пустых
        Peg peg2 = new Peg(0, "Aux");
        Peg peg3 = new Peg(0, "To");

        PegPrinter pg = new ConsolePegPrinter(peg1, peg2, peg3); // Создаем объект для отображения состояния стойки

        DiskMover mover = new DiskMover(pg);
        mover.moveDisks(peg1, peg3, peg2, diskNumber);
    }
}
