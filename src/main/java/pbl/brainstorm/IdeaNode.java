package pbl.brainstorm;

import java.io.Serializable;

public class IdeaNode implements Serializable {

    static final long serialVersionUID = 423278748783209732L;

    public IdeaNode(String content, double x, double y, boolean main, IdeaNode parentNode) {

        this.content = content;
        this.x = x;
        this.y = y;
        this.main = main;
        this.parentNode = parentNode;

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

    public IdeaNode getParent() {

        return parentNode;

    }

    private final String content;
    private final double x;
    private final double y;
    private final boolean main;
    private final IdeaNode parentNode;
}
