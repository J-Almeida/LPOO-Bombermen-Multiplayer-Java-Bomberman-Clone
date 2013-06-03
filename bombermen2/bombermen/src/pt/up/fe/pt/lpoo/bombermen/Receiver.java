package pt.up.fe.pt.lpoo.bombermen;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.net.Socket;

public class Receiver<T> implements Runnable
{
    private Socket _socket;
    protected boolean _done;
    private Thread _thread;
    private Queue<T> _messageQueue = new LinkedList<T>();

    public Receiver(Socket socket)
    {
        _socket = socket;

        _done = false;

        _thread = new Thread(this);
        _thread.start();
    }

    void Finish()
    {
        _done = true;
    }

    @Override
    public void run()
    {
        try
        {
            ObjectInputStream in = new ObjectInputStream(_socket.getInputStream());

            while (!_done)
            {
                try
                {
                    @SuppressWarnings("unchecked")
                    T msg = (T) in.readObject();
                    if (_done) break;
                    if (msg == null) continue;

                    synchronized (_messageQueue)
                    {
                        _messageQueue.add(msg);
                    }
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (EOFException e)
                {
                }

            }

            in.close();

        }
        catch (SocketException e)
        {
            _done = true;
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }

    }

    public T Poll()
    {
        synchronized (_messageQueue)
        {
            if (_messageQueue.isEmpty()) return null;

            return _messageQueue.poll();
        }
    }

    public boolean IsEmpty()
    {
        synchronized (_messageQueue)
        {
            return _messageQueue.isEmpty();
        }
    }

}