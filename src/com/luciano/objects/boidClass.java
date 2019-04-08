package com.luciano.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Random;
import com.luciano.utils.*;
import static java.lang.Math.*;


public class boidClass {
	
	static final Random r = new Random();
    static final Vector migrate = new Vector(0.02, 0);
    public static final int size = 4;
    static final Path2D shape = new Path2D.Double();
    
    final double maxForce, maxSpeed;
    
    public Vector location;
	Vector velocity;
	Vector acceleration;
    private boolean included = true;
    
    /**
     * Desenho para definir a forma do boids, a ser utilizado 
     */
    static {
        shape.moveTo(0, -size * 2);
        shape.lineTo(-size, size * 2);
        shape.lineTo(size, size * 2);
        shape.closePath();
    }
    
    /**
     * Construtor padrão para iniciar o boids, neles está incluindo a velocidade máxima, e uma velocidade randômica padrão
     * @param x Pixel inicial do boid
     * @param y Pixel inicial do boid
     */
    
    public boidClass(double x, double y) {
        acceleration = new Vector();
        velocity = new Vector(r.nextInt(3) + 1, r.nextInt(3) - 1);
        location = new Vector(x, y);
        maxSpeed = 3.0;
        maxForce = 0.05;
    }
    
    
    /**
     * Atualiza a velocidade do boid automaticamente
     */
    void update() {
        velocity.addiction(acceleration);
        velocity.limit(maxSpeed);
        location.addiction(velocity);
        acceleration.multiplication(0);
    }
    
    
 
    void applyForce(Vector force) {
        acceleration.addiction(force);
    }
 
    Vector seek(Vector target) {
    	Vector steer = Vector.sub(target, location);
        steer.normalize();
        steer.multiplication(maxSpeed);
        steer.subtraction(velocity);
        steer.limit(maxForce);
        return steer;
    }
 
    void flock(Graphics2D g, List<boidClass> boids) {
        view(g, boids);
 
        Vector rule1 = separation(boids);
        Vector rule2 = alignment(boids);
        Vector rule3 = cohesion(boids);
 
        rule1.multiplication(2.5);
        rule2.multiplication(1.5);
        rule3.multiplication(1.3);
 
        applyForce(rule1);
        applyForce(rule2);
        applyForce(rule3);
        applyForce(migrate);
    }
 
    void view(Graphics2D g, List<boidClass> boids) {
        double sightDistance = 100;
        double peripheryAngle = PI * 0.85;
 
        for (boidClass b : boids) {
            b.included = false;
 
            if (b == this)
                continue;
 
            double d = Vector.distance(location, b.location);
            if (d <= 0 || d > sightDistance)
                continue;
 
            Vector lineOfSight = Vector.sub(b.location, location);
 
            double angle = Vector.angleBetween(lineOfSight, velocity);
            if (angle < peripheryAngle)
                b.included = true;
        }
    }
 
    Vector separation(List<boidClass> boids) {
        double desiredSeparation = 25;
 
        Vector steer = new Vector(0, 0);
        int count = 0;
        for (boidClass b : boids) {
            if (!b.included)
                continue;
 
            double d = Vector.distance(location, b.location);
            if ((d > 0) && (d < desiredSeparation)) {
                Vector diff = Vector.sub(location, b.location);
                diff.normalize();
                diff.division(d);       
                steer.addiction(diff);
                count++;
            }
        }
        if (count > 0) {
            steer.division(count);
        }
 
        if (steer.magnitude() > 0) {
            steer.normalize();
            steer.multiplication(maxSpeed);
            steer.subtraction(velocity);
            steer.limit(maxForce);
            return steer;
        }
        return new Vector(0, 0);
    }
 
    Vector alignment(List<boidClass> boids) {
        double preferredDist = 50;
 
        Vector steer = new Vector(0, 0);
        int count = 0;
 
        for (boidClass b : boids) {
            if (!b.included)
                continue;
 
            double d = Vector.distance(location, b.location);
            if ((d > 0) && (d < preferredDist)) {
                steer.addiction(b.velocity);
                count++;
            }
        }
 
        if (count > 0) {
            steer.division(count);
            steer.normalize();
            steer.multiplication(maxSpeed);
            steer.subtraction(velocity);
            steer.limit(maxForce);
        }
        return steer;
    }
 
    Vector cohesion(List<boidClass> boids) {
        double preferredDist = 50;
 
        Vector target = new Vector(0, 0);
        int count = 0;
 
        for (boidClass b : boids) {
            if (!b.included)
                continue;
 
            double d = Vector.distance(location, b.location);
            if ((d > 0) && (d < preferredDist)) {
                target.addiction(b.location);
                count++;
            }
        }
        if (count > 0) {
            target.division(count);
            return seek(target);
        }
        return target;
    }
 
    void draw(Graphics2D g) {
        AffineTransform save = g.getTransform();
 
        g.translate(location.x, location.y);
        g.rotate(velocity.heading() + PI / 2);
        g.setColor(Color.white);
        g.fill(shape);
        g.setColor(Color.black);
        g.draw(shape);
 
        g.setTransform(save);
    }
 
    public void run(Graphics2D g, List<boidClass> boids, int w, int h) {
        flock(g, boids);
        update();
        draw(g);
    }

}
