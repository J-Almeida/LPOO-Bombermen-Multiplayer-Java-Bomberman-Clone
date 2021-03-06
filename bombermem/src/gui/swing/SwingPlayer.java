package gui.swing;

import java.awt.Graphics2D;
import java.awt.Point;

import logic.Player;

public class SwingPlayer extends Player implements IDraw
{
    private TiledImage _tiledImage;

    public final static int SIZE_WIDTH = 14;
    public final static int SIZE_HEIGHT = 20;
    public final static int SIZE_REAL_WIDTH = 18;
    public final static int SIZE_REAL_HEIGHT = 26;

    public SwingPlayer(int guid, Point pos, String name)
    {
        super(guid, pos, name);

        _tiledImage = new TiledImage("gui/swing/resources/bomberman.png", SIZE_REAL_WIDTH, SIZE_REAL_HEIGHT);
    }

    @Override
    public void Draw(Graphics2D g)
    {
        int tile = -1;

        if (Dir == null)
            Dir = utils.Direction.South; // default

        switch (Dir)
        {
        case East:
            tile = 3;
            break;
        case North:
            tile = 5;
            break;
        case South:
            tile = 1;
            break;
        case West:
            tile = 7;
            break;
        default:
            return;
        }

        int row = Position.x;
        int col = Position.y;

        g.drawImage(_tiledImage.GetTile(tile, 0), row * 20, col * 20, SIZE_WIDTH, SIZE_HEIGHT, null);
    }
}
