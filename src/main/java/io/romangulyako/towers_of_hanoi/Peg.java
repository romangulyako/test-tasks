package io.romangulyako.towers_of_hanoi;

import java.util.LinkedList;
import java.util.List;

public class Peg {
    private final LinkedList<Disk> disks = new LinkedList<>();
    private final String name;

    public Peg(int diskAmount, String name) {
        for (int i = diskAmount; i>0; i--) {
            Disk disk = new Disk(i);
            disks.addLast(disk);
        }
        this.name = name;
    }

    public int getDiskStackSize() {
        return disks.size();
    }

    public String getName() {
        return name;
    }

    public List<Disk> getDisks() {
        return disks;
    }

    public void push(Disk disk) {
        System.out.printf("Диск %d помещен на стержень %s%n", disk.r(), name);
        if (!disks.isEmpty() && disk.r()>disks.getLast().r()) {
            throw new WrongDiskSizeException(
                    String.format( "Диск размером %d, помещаемый на стержень %s, " +
                                    " больше предыдущего диска размером %d",
                            disk.r(), name, disks.getLast().r()));
        }
        disks.addLast(disk);
    }

    public Disk pop() {
        if (disks.isEmpty()) {
            throw new IllegalStateException("На стержне не осталось дисков!");
        }
        Disk disk = disks.removeLast();
        System.out.printf("Диск %d снят со стержня %s%n", disk.r(), name);
        return disk;
    }
}
