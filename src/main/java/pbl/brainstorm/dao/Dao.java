package pbl.brainstorm.dao;

import java.io.IOException;

public interface Dao<T> extends AutoCloseable {

    public abstract T read() throws IOException, ClassNotFoundException;

    public abstract void write(T inObj) throws IOException;

}
