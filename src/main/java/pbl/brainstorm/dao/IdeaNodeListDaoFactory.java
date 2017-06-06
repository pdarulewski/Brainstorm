package pbl.brainstorm.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import pbl.brainstorm.IdeaNode;

public class IdeaNodeListDaoFactory {

    private IdeaNodeListDaoFactory() {

    }

    public static Dao<List<IdeaNode>> getFileDao(final String fileName) throws FileNotFoundException, IOException {

        return new IdeaNodeListDao(fileName);

    }
}
