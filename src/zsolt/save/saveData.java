package zsolt.save;

public class saveData {

    String name;
    int score;
    int hits;
    int shots;
    int missed;
    double accuracy;

    public saveData(){}

    public saveData(String name, int score, int hits, int shots, int missed, double accuracy) {
        this.name = name;
        this.score = score;
        this.hits = hits;
        this.shots = shots;
        this.missed = missed;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getMissed() {
        return missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
