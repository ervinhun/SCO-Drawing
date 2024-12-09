package dk.easv.drawing.bll;

import dk.easv.drawing.be.Shapes;
import dk.easv.drawing.dal.Saving;

import java.util.List;

public class LoadSave {
    private boolean exists;
    private String fileName;
    private List<Shapes> shapes;
    public LoadSave(String fileName) {
        this.exists = isExists(fileName);
        this.fileName = fileName;
        exists = true;
    }

public LoadSave() {

}

public LoadSave(List<Shapes> shapes, String fileName) {
        exists = isExists(fileName);
        this.fileName = fileName;

}
    public boolean isExists(String fileName) {
        return true;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean save(List<Shapes> shapes) {
        Saving saver = new Saving();
        if (!saver.checkFileIfExists(fileName)) {
            saver.createFile(fileName);
        }
        saver.addToFile(fileName, shapes);
        return true;
    }

    public List<Shapes> load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
