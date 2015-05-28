
public interface Sprite {
	public abstract void setTexture(String playerPath);

	public abstract void setXMLMap();

	public abstract void setJasonMap();

	public abstract void update(int player_x, int player_y, int player_x_dir);

	public abstract void draw();
}
