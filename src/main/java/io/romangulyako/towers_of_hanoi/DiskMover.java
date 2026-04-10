package io.romangulyako.towers_of_hanoi;

public class DiskMover {
    private final PegPrinter printer;

    public DiskMover(PegPrinter printer) {
        this.printer = printer;
    }

    public void moveDisks(Peg from, Peg to, Peg aux, int n) {

        // Если нужно переместить 1 диск - просто перекладываем его на нужный стержень
        if (n==1) {
            Disk disk = from.pop();
            to.push(disk);
            printer.draw();
        } else {                                    // Если перемещается больше одного диска
            moveDisks(from, aux, to, n - 1);    // переносим n-1 диск на вспомогательный стержень
            moveDisks(from, to, aux, 1);        // переносим 1 диск на конечный стержень
            moveDisks(aux, to, from, n - 1);    // переносим диски со вспомогательного на конечный стержень
        }
    }
}
