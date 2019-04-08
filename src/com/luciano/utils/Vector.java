package com.luciano.utils;

import static java.lang.Math.*;

public class Vector {
public  double x;
public double y;
  
  public Vector(){
	  
  }
  
  public Vector(double x, double y){
	  this.x = x;
	  this.y = y;
  }
  
  public void addiction(Vector vector) {
	  x += vector.x;
	  y += vector.y;
  }
  
  public void subtraction(Vector vector) {
	  x -= vector.x;
	  y -= vector.y;
  }
  
  public void division(double val){
	  x /= val;
	  y /= val;
  }
  
  public void multiplication(double val){
	  x *= val;
	  y *= val;
  }
  
  public double dot(Vector v) {
      return x * v.x + y * v.y;
  }
  
  public double magnitude() {
	  return sqrt(pow(x, 2) + pow(y, 2));
  }
  
  public void normalize() {
      double magnitude = magnitude();
      if (magnitude != 0) {
          x /= magnitude;
          y /= magnitude;
      }
  }
  
  public static Vector sub(Vector v, Vector v2) {
      return new Vector(v.x - v2.x, v.y - v2.y);
  }
  
  public static double distance(Vector v, Vector v2) {
      return sqrt(pow(v.x - v2.x, 2) + pow(v.y - v2.y, 2));
  }
  
  public static double angleBetween(Vector v, Vector v2) {
      return acos(v.dot(v2) / (v.magnitude() * v2.magnitude()));
  }
  
  public void limit(double lim) {
      double magnitude = magnitude();
      if (magnitude != 0 && magnitude > lim) {
          x *= lim / magnitude;
          y *= lim / magnitude;
      }
  }
  
  public double heading() {
      return atan2(y, x);
  }
  
}
