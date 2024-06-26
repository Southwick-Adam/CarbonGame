package com.carbon.game;

import org.xguzm.pathfinding.grid.GridCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Route {
    public static Map map;
    public boolean train;
    public HashMap<String, Station> stations = new HashMap<>();
    private final ArrayList<int[]> path = new ArrayList<>();
    public ArrayList<Transit> transitList = new ArrayList<>();

    private final int direction;

    public Route(Map map, boolean t, int dir) {
        train = t;
        Route.map = map;
        direction = dir;
    }

    public void addStation(String coordString, Station s) {
        stations.put(coordString, s);
        s.setRoute(this);
    }

    public void addTransit() {
        int pathSize = path.size();
        int transitNum = 3;
        //adding additional transit cars to the longer stations so theres less wait time
        if (stations.keySet().size() > 6) {
            transitNum = 5;
        } else if (stations.keySet().size() > 5) {
            transitNum = 4;
        }
        for (int n = 0; n < transitNum; n++) {
            transitList.add(new Transit(this, n * pathSize / transitNum, train, direction));
        }
    }

    public void setPath(int[] first, List<GridCell> p) {
        path.add(first);
        for (GridCell gc : p) {
            path.add(new int[]{gc.getX(), gc.getY()});
        }
    }

    public ArrayList<int[]> getPath() {
        return path;
    }

    public void dispose() {
        for (Transit transit : transitList) {
            transit.dispose();
        }
    }
}
