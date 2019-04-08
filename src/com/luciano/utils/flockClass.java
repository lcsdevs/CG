package com.luciano.utils;

import java.util.ArrayList;
import java.util.List;

import com.luciano.objects.*;
import java.awt.*;

public class flockClass {
	List<boidClass> boids;
	 
    flockClass() {
        boids = new ArrayList<>();
    }
 
    public void run(Graphics2D g,  int w, int h) {
        for (boidClass b : boids) {
            b.run(g, boids, w, h);
        }
    }
 
    public boolean hasLeftTheBuilding(int w) {
        int count = 0;
        for (boidClass b : boids) {
            if (b.location.x + boidClass.size > w)
                count++;
        }
        return boids.size() == count;
    }
 
    void addBoid(boidClass b) {
        boids.add(b);
    }
 
    public static flockClass spawn(double w, double h, int numBoids) {
    	flockClass flock = new flockClass();
        for (int i = 0; i < numBoids; i++)
            flock.addBoid(new boidClass(w, h));
        return flock;
    }
}
