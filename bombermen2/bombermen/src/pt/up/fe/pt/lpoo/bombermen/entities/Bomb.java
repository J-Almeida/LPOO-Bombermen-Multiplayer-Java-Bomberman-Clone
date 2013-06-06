package pt.up.fe.pt.lpoo.bombermen.entities;

import pt.up.fe.pt.lpoo.bombermen.Assets;
import pt.up.fe.pt.lpoo.bombermen.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bomb extends Entity
{
    @Override
    public boolean remove()
    {
        if (super.remove())
        {
            Assets.PlaySound("bomb_explosion");
            return true;
        }

        return false;
    }

    public static TextureRegion Regions[][] = null;
    public static Animation Animation = null;

    private float _stateTime;

    public Bomb(int guid)
    {
        super(Entity.TYPE_BOMB, guid);
        _stateTime = 0;
    }

    @Override
    public void act(float delta)
    {
        super.act(delta);
        _stateTime += delta;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha)
    {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(Animation.getKeyFrame(_stateTime, true), getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}