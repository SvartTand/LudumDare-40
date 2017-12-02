package com.svarttand.ludumdare40.map;

import com.badlogic.gdx.math.Vector2;

public class PolygonChecker {
	
	//public enum Side{LEFT,RIGHT,NONE};
	
	
	public static boolean inside_convex_polygon(Vector2 point, Vector2[] polygon){
		 //Cannot be part of empty polygon
	    if (polygon.length == 0)
	    {
	        return false;
	    }

	    //With 1-pt polygon, only if it's the point
	    if (polygon.length == 1)
	    {
	        return polygon[0].x == point.x && polygon[0].y == point.y;
	    }

	    //n>2 Keep track of cross product sign changes
	    int pos = 0;
	    int neg = 0;

	    for (int i = 0; i < polygon.length; i++)
	    {
	        //If point is in the polygon
	        if (polygon[i].x == point.x && polygon[i].y == point.y)
	            return true;

	        //Form a segment between the i'th point
	        float x1 = polygon[i].x;
	        float y1 = polygon[i].y;

	        //And the i+1'th, or if i is the last, with the first point
	        int i2 = i < polygon.length - 1 ? i + 1 : 0;

	        float x2 = polygon[i2].x;
	        float y2 = polygon[i2].y;

	        float x = point.x;
	        float y = point.y;

	        //Compute the cross product
	        float d = (x - x1)*(y2 - y1) - (y - y1)*(x2 - x1);

	        if (d > 0) pos++;
	        if (d < 0) neg++;

	        //If the sign changes, then point is outside
	        if (pos > 0 && neg > 0)
	            return false;
	    }

	    //If no change in direction, then on same side of all segments, and thus inside
	    return true;
	}
}
