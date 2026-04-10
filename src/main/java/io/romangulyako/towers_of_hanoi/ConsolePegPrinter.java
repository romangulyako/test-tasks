package io.romangulyako.towers_of_hanoi;

import java.util.List;

public class ConsolePegPrinter implements PegPrinter {
    private final String[][] display; // массив, формирующий картинку со стержнями и дисками на них
    private final String PEG_SYMBOL = "▓"; // Символ, который используется при отрисовке всего
    private final int high, width;
    private final int peg1Position, peg2Position, peg3Position; // Позиции стержней по оси x
    private final Peg peg1, peg2, peg3;
    private final StringBuilder sb = new StringBuilder();

    public ConsolePegPrinter(Peg peg1, Peg peg2, Peg peg3) {
        // Создаем массив с высотой, равной числу дисков и шириной, равной тройной ширине самого широкого диска
        int diskStackSize = peg1.getDiskStackSize() + peg2.getDiskStackSize() + peg3.getDiskStackSize();

        //Высота картинки - общее количество дисков + одна строка для имен стержней
        high = diskStackSize+1;

        //Ширина картинки = высота x 2 (чтобы не пересекались диски на соседних стойках)
        // x на три стойки плюс два промежутка между стойками с дисками
        width = high*2*3+2;
        display = new String[high][width]; // картинка со стержнями и дисками
        peg1Position = width/6;
        peg2Position = peg1Position + width/3;
        peg3Position = peg2Position + width/3;
        this.peg1 = peg1;
        this.peg2 = peg2;
        this.peg3 = peg3;
        draw();
    }

    @Override
    public void draw() {
        initArray();
        drawPegs();
        drawDisks(peg1Position, peg1);
        drawDisks(peg2Position, peg2);
        drawDisks(peg3Position, peg3);
        print();
    }

    // Очищает массив для отрисовки изменившейся картинки
    private void initArray() {
        for (int i = 0; i<high; i++) {
            for (int j = 0; j < width; j++) {
                display[i][j] = " ";
            }
        }
    }

    // Отрисовывает стержни
    private void drawPegs() {
        for (int i = 0; i < high; i++) {
            // В верхней строке располагаем имена стержней
            if ((i==0)) {
                display[i][peg1Position] = peg1.getName();
                display[i][peg2Position] = peg2.getName();
                display[i][peg3Position] = peg3.getName();
            }
            else {
                display[i][peg1Position] = PEG_SYMBOL;
                display[i][peg2Position] = PEG_SYMBOL;
                display[i][peg3Position] = PEG_SYMBOL;
            }
        }
    }

    // Отрисовывает диски на заданном стержне
    private void drawDisks(int pegPosition, Peg peg) {
        if (!peg.getDisks().isEmpty()) {
            int size = peg.getDisks().size();
            List<Disk> disks = peg.getDisks();
            for (int i = 0; i<size; i++) {
                for (int j = 0; j<=disks.get(i).r(); j++) {
                    display[high-1-i][pegPosition-j] = PEG_SYMBOL; // рисуем диск слева
                    display[high-1-i][pegPosition+j] = PEG_SYMBOL; // и справа от стержня
                }
            }
        }
    }

    // Выводит картинку со стрежнями и дисками в консоль
    private void print() {
        for (int i = 0; i < high; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(display[i][j]); // помещаем содержимое строки массива в StringBuilder
            }
            System.out.println(sb); // выводим строку на экран
            sb.setLength(0); // очищаем StringBuilder для новой строки
        }
    }
}
