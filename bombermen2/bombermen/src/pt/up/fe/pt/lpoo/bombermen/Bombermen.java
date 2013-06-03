package pt.up.fe.pt.lpoo.bombermen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bombermen implements ApplicationListener
{
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ControlPad _controlPad = null;
    private BitmapFont font;

    private float timeStep;

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        if (Gdx.app.getType() == ApplicationType.Android)
            timeStep = 1 / 45f;
        else
            timeStep = 1 / 60f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT);
        batch = new SpriteBatch();
        font = new BitmapFont();

        Input in = new Input(Game.Instance(), camera);

        if (Gdx.app.getType() == ApplicationType.Android)
        {
            _controlPad = new ControlPad();
            _controlPad.SetSize(Constants.DEFAULT_PAD_WIDTH, Constants.DEFAULT_PAD_HEIGHT);
            in.SetControlPad(_controlPad);
        }

        Gdx.input.setInputProcessor(in);
    }

    @Override
    public void dispose()
    {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render()
    {
        Gdx.gl.glClearColor(16f / 255, 120f / 255, 48f / 255, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();

        // center 15 by 13 cells
        camera.combined.translate(Constants.DEFAULT_WIDTH / 2 - 15 * Constants.CELL_SIZE / 2, Constants.DEFAULT_HEIGHT / 2 - 13 * Constants.CELL_SIZE / 2 - 26, 0);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        Game.Instance().Update();
        Game.Instance().draw(batch);

        batch.end();

        camera.combined.translate(-(Constants.DEFAULT_WIDTH / 2 - 15 * Constants.CELL_SIZE / 2), -(Constants.DEFAULT_HEIGHT / 2 - 13 * Constants.CELL_SIZE / 2 - 26), 0);
        batch.setProjectionMatrix(camera.combined);

        if (_controlPad != null)
        {
            batch.begin();
            _controlPad.draw(batch);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }
}