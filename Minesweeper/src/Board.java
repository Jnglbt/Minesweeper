import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board
{
    private Cell[][] cells;
    private int cellID = 0;
    private int side = 8;
    private int limit = side - 2;

    public void setBoard()
    {
        JFrame frame = new JFrame();
        frame.add(addCells());

        plantMines();
        setCellValues();

        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setCellValues()
    {
        for (int i = 0; i < side; i++)
        {
            for (int j = 0; j < side; j++)
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
            random = (int)(Math.random() * (side*side));
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
        JPanel panel = new JPanel(new GridLayout(side, side));
        cells = new Cell[side][side];
        for (int i = 0; i < side; i++)
        {
            for(int j = 0; j < side; j++)
            {
                cells[i][j] = new Cell(this);
                cells[i][j].setId(getID());
                panel.add(cells[i][j].getButton());
            }
        }
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
        for (int i = 0; i < side; i++)
        {
            for (int j = 0; j < side; j++)
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
