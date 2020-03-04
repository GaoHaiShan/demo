package builder;

public class SpruceUp {
    private String floor;
    private String sofa;
    private String curtain;
    private String wall;

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getSofa() {
        return sofa;
    }

    public void setSofa(String sofa) {
        this.sofa = sofa;
    }

    public String getCurtain() {
        return curtain;
    }

    public void setCurtain(String curtain) {
        this.curtain = curtain;
    }

    public String getWall() {
        return wall;
    }

    public void setWall(String wall) {
        this.wall = wall;
    }

    @Override
    public String
    toString() {
        return "home{" +
                "地板为'" + floor + '\'' +
                ", 沙发为'" + sofa + '\'' +
                ", 窗帘为'" + curtain + '\'' +
                ", 墙壁为'" + wall + '\'' +
                '}';
    }
}
