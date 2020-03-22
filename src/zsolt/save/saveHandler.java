package zsolt.save;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class saveHandler {

    public static void save(String name, int score, int hits, int shots, int missed, double accuracy){

        saveData s = new saveData(name, score, hits, shots, missed, accuracy);
        long saves = 0;

        // get file count
        try (Stream<Path> files = Files.list(Paths.get("./save/"))) {
            saves = files.count();
        }catch(Exception e){}

        try{
            // Save file
            FileOutputStream fos = new FileOutputStream(new File("./save/save" + saves + ".xml"));
            XMLEncoder enc = new XMLEncoder(fos);
            enc.writeObject(s);
            enc.close();
            fos.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static saveData[] load(){
        long saves = 0;
        List<saveData> save = new ArrayList<>();

        // get file count
        try (Stream<Path> files = Files.list(Paths.get("./save/"))) {
            saves = files.count();
        }catch(Exception e){}

        // Load files
        try{
            for(int i = 0; i < saves; i++){
                FileInputStream fis = new FileInputStream(new File("./save/save" + i + ".xml"));
                XMLDecoder dec = new XMLDecoder(fis);
                save.add((saveData) dec.readObject());
                dec.close();
                fis.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        // Convert list to array
        saveData[] toReturn = new saveData[save.size()];
        save.toArray(toReturn);

        // From high to low

        for(int i = 0; i < toReturn.length; i++){
            if(i == 0)
                continue;

            if(toReturn[i - 1].getScore() < toReturn[i].getScore()){
                saveData s = toReturn[i];
                toReturn[i] = toReturn[i - 1];
                toReturn[i - 1] = s;
                i = 0;
            }
        }

        return toReturn;
    }

}
