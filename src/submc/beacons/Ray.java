package submc.beacons;

import org.bukkit.Location;

public class Ray {

	private Location l1;
	private Location l2;
	
	public Ray(Location l1, Location l2) {
		this.l1 = l1;
		this.l2 = l2;
	}
	
	public Location getPointOnRay(int radius) {
		return findLineSphereIntersections(l1, l2, l1, radius)[0];
	}
	
	private static Location[] findLineSphereIntersections( Location linePoint0, Location linePoint1, Location circleCenter, double circleRadius )
    {
        
        double cx = circleCenter.getX();
        double cy = circleCenter.getY();
        double cz = circleCenter.getZ();

        double px = linePoint0.getX();
        double py = linePoint0.getY();
        double pz = linePoint0.getZ();

        double vx = linePoint1.getX() - px;
        double vy = linePoint1.getY() - py;
        double vz = linePoint1.getZ() - pz;

        double A = vx * vx + vy * vy + vz * vz;
        double B = 2.0 * (px * vx + py * vy + pz * vz - vx * cx - vy * cy - vz * cz);
        double C = px * px - 2 * px * cx + cx * cx + py * py - 2 * py * cy + cy * cy +
                   pz * pz - 2 * pz * cz + cz * cz - circleRadius * circleRadius;

        // discriminant
        double D = B * B - 4 * A * C;

        if ( D < 0 )
        {
            return null;
        }

        double t1 = ( -B - Math.sqrt ( D ) ) / ( 2.0 * A );

        Location solution1 = new Location( linePoint0.getWorld(),linePoint0.getX() * ( 1 - t1 ) + t1 * linePoint1.getX(),
                                         linePoint0.getY() * ( 1 - t1 ) + t1 * linePoint1.getY(),
                                         linePoint0.getZ() * ( 1 - t1 ) + t1 * linePoint1.getZ() );
        if ( D == 0 )
        {
            return new Location[] { solution1 };
        }

        double t2 = ( -B + Math.sqrt( D ) ) / ( 2.0 * A );
        Location solution2 = new Location(linePoint0.getWorld(), linePoint0.getX() * ( 1 - t2 ) + t2 * linePoint1.getX(),
                                         linePoint0.getY() * ( 1 - t2 ) + t2 * linePoint1.getY(),
                                         linePoint0.getZ() * ( 1 - t2 ) + t2 * linePoint1.getZ() );

        // prefer a solution that's on the line segment itself

        if ( Math.abs( t1 - 0.5 ) < Math.abs( t2 - 0.5 ) )
        {
            return new Location[] { solution1, solution2 };
        }

        return new Location[] { solution2, solution1 };
    }
	
}
