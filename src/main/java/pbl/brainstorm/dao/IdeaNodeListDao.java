package pbl.brainstorm.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import pbl.brainstorm.IdeaNode;

public class IdeaNodeListDao implements Dao<List<IdeaNode>>, AutoCloseable {

    public IdeaNodeListDao(final String fileName) throws FileNotFoundException, IOException {

        this.fileName = fileName;

    }

    @Override
    public List<IdeaNode> read() throws IOException, ClassNotFoundException {

        fis = new FileInputStream(fileName);
        ois = new ObjectInputStream(fis);

        return (List<IdeaNode>) ois.readObject();

    }

    @Override
    public void write(final List<IdeaNode> inObj) throws IOException {

        fos = new FileOutputStream(fileName);
        oos = new ObjectOutputStream(fos);

        oos.writeObject(inObj);

    }

    @Override
    public void close() throws Exception {

        if (oos != null) {

            oos.close();

        }

        if (ois != null) {

            ois.close();

        }

    }

    @Override
    public void finalize() throws Exception {

        close();

    }

    private FileOutputStream fos = null;
    private FileInputStream fis = null;

    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    private final String fileName;

}
