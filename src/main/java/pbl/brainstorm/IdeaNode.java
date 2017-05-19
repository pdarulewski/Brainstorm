package pbl.brainstorm;

public class IdeaNode {

    public IdeaNode(String content, double x, double y) {

        this.content = content;
        this.x = x;
        this.y = y;

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

}
