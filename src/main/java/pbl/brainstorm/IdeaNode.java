package pbl.brainstorm;

public class IdeaNode {

    public IdeaNode(String content, double x, double y, boolean main) {

        this.content = content;
        this.x = x;
        this.y = y;
        this.main = main;

    }

    public boolean isMain() {

        return main;

    }

    public String getContent() {

        return content;

    }

    public double getX() {

        return x;

    }

    public double getY() {

        return y;

    }

    private final String content;
    private final double x;
    private final double y;
    private final boolean main;
}
