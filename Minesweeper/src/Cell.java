import javax.swing.*;
        import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Cell implements ActionListener
{
    private JButton button;
    private Board board;
    private int value;
    private int id;
    private boolean notChecked;

    public Cell(Board board)
    {
        button = new JButton();
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(20, 20));
        button.setMargin(new Insets(0, 0, 0, 0));
        this.board = board;
        notChecked = true;
    }

    int setMine()
    {
        if (!isMine())
        {
            setValue(-1);
            return 1;
        }
        return 0;
    }

    public JButton getButton()
    {
        return button;
    }

    public void setButton(JButton button)
    {
        this.button = button;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void displayValue()
    {
        if (isMine())
        {
            button.setText("\u2600");
            button.setBackground(color);
        }
        else if (value != 0)
        {
            button.setText(String.valueOf(value));
        }
        /*if (value == -1)
        {
            button.setText("\u2600");
            button.setBackground(Color.RED);
        }
        else if (value != 0)
        {
            button.setText(String.valueOf(value));
        }*/
    }


    public void checkCell()
    {
        reveal(null);
        if (isMine() || board.isDone())
            board.reveal(isMine() ? Color.red : Color.green);
        else if (value == 0)
            board.scanForEmptyCells();

        /*button.setEnabled(false);
        displayValue();
        notChecked = false;
        if (value == 0) board.scanForEmptyCells();
        if (value == -1) board.fail();
        */
    }

    boolean isChecked()
    {
        return checked;
    }

    boolean isEmpty()
    {
        return !isChecked() && value == 0;
    }

    boolean isMine()
    {
        return value == -1;
    }

    public void incrementValue()
    {
        value++;
    }

    public boolean isNotChecked()
    {
        return notChecked;
    }

    public void reveal()
    {
        displayValue();
        checked = true;
        button.setEnabled(!checked);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        checkCell();
    }
}
