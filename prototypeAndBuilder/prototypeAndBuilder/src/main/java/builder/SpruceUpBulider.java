package builder;

import java.util.HashMap;
import java.util.Map;

public class SpruceUpBulider {
    private Map<String,String> bulideInfo = new HashMap<>();
    public SpruceUpBulider createFloor(String floor) {
        this.bulideInfo.put("floor",floor);
        return this;
    }
    public SpruceUpBulider createSofa(String sofa) {
        this.bulideInfo.put("sofa",sofa);
        return this;
    }
    public SpruceUpBulider createCurtain(String curtain) {
        this.bulideInfo.put("curtain",curtain);
        return this;
    }
    public SpruceUpBulider createWall(String wall) {
        this.bulideInfo.put("wall",wall);
        return this;
    }
    public SpruceUp bulider(){
        SpruceUp res = new SpruceUp();

        if(bulideInfo.containsKey("floor")){res.setFloor(bulideInfo.get("floor")); }
        else { res.setFloor("水泥地板"); }
        System.out.println("地板已经铺好了");

        if(bulideInfo.containsKey("wall")){ res.setWall(bulideInfo.get("wall")); }
        else { res.setWall("白色墙壁"); }
        System.out.println("墙面已经刷完了");

        if(bulideInfo.containsKey("sofa")){
            res.setSofa(bulideInfo.get("sofa"));
            System.out.println("沙发摆放完毕");
        }
        if(bulideInfo.containsKey("curtain")){
            res.setCurtain(bulideInfo.get("curtain"));
            System.out.println("窗帘挂好了");
        }
        System.out.println("装修完成");
        return res;
    }
}
