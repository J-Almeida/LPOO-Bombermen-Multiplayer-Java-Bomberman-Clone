package pt.up.fe.pt.lpoo.bombermen.messages;

import java.io.Serializable;

public abstract class Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final int SMSG_PING = 0;
    public static final int SMSG_SPAWN = 1;
    public static final int SMSG_MOVE = 2;
    public static final int SMSG_DESTROY = 3;
    public static final int CMSG_MOVE = 4;
    public static final int CMSG_PLACE_BOMB = 5;
    public static final int CMSG_JOIN = 6;
    public static final int CMSG_PING = 7;
    public static final int SMSG_DEATH = 8;
    public static final int SMSG_VICTORY = 9;
    public static final int SMSG_POWER_UP = 10;
    public static final int SMSG_MOVE_DIR = 11;

    public final int Type;

    public Message(int type)
    {
        Type = type;
    }

    @Override
    public abstract String toString();
}
