public class CoreFrame extends MyFrame{
    public CoreFrame(String title) {
        super(title);
        setSize(ConfigUtil.getConfigInt("FrameWidth"), ConfigUtil.getConfigInt("FrameHeight"));
    }
}
