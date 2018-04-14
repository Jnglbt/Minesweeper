import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board
{
    static final int SIDE = 8;
    static final int MINES;
    private Cell[][] cells;
    cells = new Cell[SIDE][SIDE];
    private int cellID = 0;
    private int limit = SIDE - 2;
    IntStream.range(0, SIDE).forEach(i -> {
        IntStream.range(0, SIDE).forEach(j -> cells[i][j] = new Cell(this));
    });
    init();

    private void init()
    {
        plantMines();
        setCellValues();
    }

    private void forEach(Consumer<Cell> consumer)
    {
        Stream.of(cells).forEach(row -> Stream.of(row).forEach(consumer));
    }

    public void setBoard()
    {
        JFrame frame = new JFrame();
        frame.add(addCells());

        //plantMines();
        //setCellValues();

        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    void reveal(Color color)
    {
        forEach(cell -> cell.reveal(color));
    }

    boolean isDone()
    {
        int[] result = new int[1];
        forEach(cell -> {if (cell.isEmpty()) { result[0]++; }});
        return result[0] == MINES;
    }


    private void plantMines()
    {
        Random random = new Random();
        int counter = 0;
        while (counter != MINES)
        {
            counter += cells[random.nextInt(SIDE)][random.nextInt(SIDE)].setMine();
        }
    }

    public void setCellValues()
    {
        for (int i = 0; i < SIDE; i++)
        {
            for (int j = 0; j < SIDE; j++)
            {
                if (cells[i][j].getValue() != -1)
                {
                    if (j >= 1 && cells[i][j-1].getValue() == -1)
                        cells[i][j].incrementValue();
                    if (j <= limit && cells[i][j+1].getValue() == -1)
                        cells[i][j].incrementValue();
                    if (i >= 1 && cells[i-1][j].getValue() == -1)
                        cells[i][j].incrementValue();
                    if (i <= limit && cells[i+1][j].getValue() == -1)
                        cells[i][j].incrementValue();
                    if (i >= 1 && j >= 1 && cells[i-1][j-1].getValue() == -1)
                        cells[i][j].incrementValue();
                    if (i <= limit && j <= limit && cells[i+1][j+1].getValue() == -1)
                        cells[i][j].incrementValue();
                    if (i >= 1 && j <= limit && cells[i-1][j+1].getValue() == -1)
                        cells[i][j].incrementValue();
                    if (i <= limit && j >= 1 && cells[i+1][j-1].getValue() == -1)
                        cells[i][j].incrementValue();
                }
            }
        }
    }

    public void plantMines()
    {
        ArrayList<Integer> loc = generateMinesLocation(10);
        for (int i : loc)
        {
            getCell(i).setValue(-1);
        }
    }

    public Cell getCell(int id)
    {
        for (Cell[] a : cells)
        {
            for (Cell b : a)
            {
                if (b.getId() == id)
                    return b;
            }
        }
        return null;
    }

    public ArrayList<Integer> generateMinesLocation(int q)
    {
        ArrayList<Integer> loc = new ArrayList<>();
        int random;
        for (int i = 0; i < q;)
        {
            random = (int)(Math.random() * (SIDE*SIDE));
            if (!loc.contains(random))
            {
                loc.add(random);
                i++;
            }
        }
        return loc;
    }



    private JPanel addCells()
    {
        JPanel panel = new JPanel(new GridLayout(SIDE, SIDE));
        forEach(cell -> panel.add(cell.getButton()));
        /*
        cells = new Cell[side][side];
        for (int i = 0; i < side; i++)
        {
            for(int j = 0; j < side; j++)
            {
                cells[i][j] = new Cell(this);
                cells[i][j].setId(getID());
                panel.add(cells[i][j].getButton());
            }
        }*/
        return panel;
    }

    public int getID()
    {
        int id = cellID;
        cellID++;
        return id;
    }

    public void fail()
    {
        for (Cell[] a : cells)
        {
            for (Cell b : a)
            {
                b.reveal();
            }
        }
    }

    public void scanForEmptyCells()
    {
        for (int i = 0; i < SIDE; i++)
        {
            for (int j = 0; j < SIDE; j++)
            {
                if (!cells[i][j].isNotChecked())
                {
                    if (j >= 1 && cells[i][j-1].isEmpty())
                        cells[i][j-1].checkCell();
                    if (j <= limit && cells[i][j+1].isEmpty())
                        cells[i][j+1].checkCell();
                    if (i >= 1 && cells[i-1][j].isEmpty())
                        cells[i-1][j].checkCell();
                    if (i <= limit && cells[i+1][j].isEmpty())
                        cells[i+1][j].checkCell();
                    if (i >= 1 && j >= 1 && cells[i-1][j-1].isEmpty())
                        cells[i-1][j-1].checkCell();
                    if (i <= limit && j <= limit && cells[i+1][j+1].isEmpty())
                        cells[i+1][j+1].checkCell();
                    if (i >= 1 && j <= limit && cells[i-1][j+1].isEmpty())
                        cells[i-1][j+1].checkCell();
                    if (i <= limit && j >= 1 && cells[i+1][j-1].isEmpty())
                        cells[i+1][j-1].checkCell();
                }
            }
        }
    }
}
