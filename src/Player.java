public class Player
{
	private String name = "";
	private int score = 0;
	private Frame frame = new Frame();
	
	Player()
	{
		reset();
	}

	public void reset()
	{
		name = "";
		score = 0;
		frame.reset();
	}
	
	public void setName(String text)
	{
		name = text;
	}
	
	public String getName()
	{
		return name;
	}

	public int getScore()
	{
		return score;
	}
	
	public Frame getFrame()
	{
		return frame;
	}
	
	public void addScore(int increment)
	{
		score = score + increment;
	}
	
	public String toString()
	{
		return name + ": " + score;
	}
}