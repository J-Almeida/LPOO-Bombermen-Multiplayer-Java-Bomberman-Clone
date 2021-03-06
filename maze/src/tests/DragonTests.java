package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import logic.Architect;
import logic.DefaultMazeGenerator;
import logic.Direction;
import logic.Dragon;
import logic.Maze;
import logic.RequestMovementEvent;
import model.Position;

import org.junit.BeforeClass;
import org.junit.Test;

import utils.RandomEngine;
import utils.RandomTester;

/**
 * The Class DragonTests (3.2)
 */
public class DragonTests
{
    /** The architect. */
    private static Architect _architect;

    /** The maze. */
    private static Maze _maze;

    /**
     * Sets the up before class.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        _architect = new Architect();
    }

    /**
     * Move dragon success test.
     */
    @Test
    public void MoveDragonSuccessTest()
    {
        _architect.SetMazeGenerator(new DefaultMazeGenerator());
        _architect.ConstructMaze(10, 1, Dragon.Behaviour.Idle);
        _maze = _architect.GetMaze();

        Dragon d = _maze.FindDragons().get(0);
        assertEquals(new Position(1, 3), d.GetPosition());

        _maze.PushEvent(d, new RequestMovementEvent(Direction.North));
        _maze.Update();

        assertEquals(new Position(1, 2), d.GetPosition());
    }

    /**
     * Move dragon failure test.
     */
    @Test
    public void MoveDragonFailureTest()
    {
        _architect.SetMazeGenerator(new DefaultMazeGenerator());
        _architect.ConstructMaze(10, 1, Dragon.Behaviour.Idle);
        _maze = _architect.GetMaze();

        Dragon d = _maze.FindDragons().get(0);
        assertEquals(new Position(1, 3),d.GetPosition());

        _maze.PushEvent(d, new RequestMovementEvent(Direction.West));
        _maze.Update();

        assertEquals(new Position(1, 3),d.GetPosition());
    }

    /**
     * Dragon sleep test.
     */
    @Test
    public void DragonSleepTest()
    {

        _architect.SetMazeGenerator(new DefaultMazeGenerator());
        _architect.ConstructMaze(10, 1, Dragon.Behaviour.Sleepy);
        _maze = _architect.GetMaze();

        Dragon d = _maze.FindDragons().get(0);
        assertFalse(d.IsSleeping());

        d.SetToSleep(2);

        assertTrue(d.IsSleeping());
        Position dragPos = d.GetPosition().clone();
        _maze.Update();
        assertEquals(dragPos, d.GetPosition());
        assertTrue(d.IsSleeping());
        _maze.Update();
        assertFalse(d.IsSleeping());
    }

    /**
     * Dragon random movement test with hero dead at end.
     */
    @Test
    public void DragonRandomMovementTestWithHeroDeadAtEnd()
    {

        _architect.SetMazeGenerator(new DefaultMazeGenerator());
        _architect.ConstructMaze(10, 1, Dragon.Behaviour.RandomMovement);
        _maze = _architect.GetMaze();
        RandomTester r = new RandomTester();
        RandomEngine.SetRandom(r);

        Dragon d = _maze.FindDragons().get(0);

        r.PushInt(0);
        Position dragPos = d.GetPosition().clone();
        _maze.Update();
        assertEquals(dragPos, d.GetPosition());

        r.PushInt(2);
        dragPos = d.GetPosition().clone();
        _maze.Update();
        assertEquals(new Position(dragPos.X, dragPos.Y + 1), d.GetPosition());

        r.PushInt(3);
        dragPos = d.GetPosition().clone();
        _maze.Update();
        assertEquals(dragPos, d.GetPosition());

        r.PushInt(3);
        dragPos = d.GetPosition().clone();
        _maze.Update();
        assertEquals(dragPos, d.GetPosition());

        r.PushInt(1);
        dragPos = d.GetPosition().clone();
        _maze.Update();
        assertEquals(new Position(dragPos.X, dragPos.Y - 1), d.GetPosition());

        r.PushInt(1);
        dragPos = d.GetPosition().clone();
        assertTrue(_maze.FindHero().IsAlive());
        _maze.Update();
        assertEquals(new Position(dragPos.X, dragPos.Y - 1), d.GetPosition());
        assertNull(_maze.FindHero());
        assertTrue(d.IsAlive());
    }

    /**
     * Dragon random movement test with hero alive at end.
     */
    @Test
    public void DragonRandomMovementTestWithHeroAliveAtEnd()
    {
        _architect.SetMazeGenerator(new DefaultMazeGenerator());
        _architect.ConstructMaze(10, 1, Dragon.Behaviour.RandomMovement);
        _maze = _architect.GetMaze();
        RandomTester r = new RandomTester();
        RandomEngine.SetRandom(r);

        r.PushInt(1);
        assertTrue(_maze.FindHero().IsAlive());
        _maze.FindHero().EquipSword(true);
        assertTrue(_maze.FindHero().IsArmed());
        _maze.Update();
        assertTrue(_maze.FindHero().IsAlive());
        assertTrue(_maze.FindDragons().isEmpty());
    }
}
