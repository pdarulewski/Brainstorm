package pbl.brainstorm;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(content).append(x).append(y).append(main).toString();

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
