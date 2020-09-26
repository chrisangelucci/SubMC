package submc.worldgen.caves;

import org.bukkit.Chunk;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class CaveRandom {

	public Chunk chunk;

    // Note: Smaller frequencies yield slower change (more stretched out)
    //   Larger amplitudes yield greater influence on final void
    // Frequency
    private double f1xz;
    private double f1y;
    // Density
    private int amplitude1 = 100;
    private double subtractForLessThanCutoff;
    // Second pass - small noise
    private double f2xz = 0.25;
    private double f2y = 0.05;
    private int amplitude2 = 2;
    // Third pass - vertical noise
    private double f3xz = 0.025;
    private double f3y = 0.005;
    private int amplitude3 = 20;
    // Position
    private int caveBandBuffer;

    // Noise
    private NoiseGenerator noiseGen1;
    private NoiseGenerator noiseGen2;
    private NoiseGenerator noiseGen3;

    public static int cutoff = 62;
    public static int sxz = 200;
    public static int sy = 100;
    public static int caveBandMin = 6;
    public static int caveBandMax = 50;
    
    public CaveRandom(Chunk chunk) {
        this.chunk = chunk;
        subtractForLessThanCutoff = amplitude1 - cutoff;
        f1xz = 1.0 / sxz;
        f1y = 1.0 / sy;
        if (caveBandMax - caveBandMin > 128) {
            caveBandBuffer = 32;
        } else {
            caveBandBuffer = 16;
        }
        noiseGen1 = new SimplexNoiseGenerator(chunk.getWorld());
        noiseGen2 = new SimplexNoiseGenerator((long) noiseGen1.noise(chunk.getX(), chunk.getZ()));
        noiseGen3 = new SimplexNoiseGenerator((long) noiseGen1.noise(chunk.getX(), chunk.getZ()));
    }

    public boolean isInGiantCave(int x, int y, int z) {
        double xx = (chunk.getX() << 4) | (x & 0xF);
        double yy = y;
        double zz = (chunk.getZ() << 4) | (z & 0xF);

        double n1 = (noiseGen1.noise(xx * f1xz, yy * f1y, zz * f1xz) * amplitude1);
        double n2 = (noiseGen2.noise(xx * f2xz, yy * f2y, zz * f2xz) * amplitude2);
        double n3 = (noiseGen3.noise(xx * f3xz, yy * f3y, zz * f3xz) * amplitude3);
        double lc = linearCutoffCoefficient(y);

        boolean isInCave = n1 + n2 - n3 - lc > cutoff;
        return isInCave;
    }

    private double linearCutoffCoefficient(int y) {
        // Out of bounds
        if (y < caveBandMin || y > caveBandMax) {
            return subtractForLessThanCutoff;
            // Bottom layer distortion
        } else if (y >= caveBandMin && y <= caveBandMin + caveBandBuffer) {
            double yy = y - caveBandMin;
            return (-subtractForLessThanCutoff / (double) caveBandBuffer) * yy + subtractForLessThanCutoff;
            // Top layer distortion
        } else if (y <= caveBandMax && y >= caveBandMax - caveBandBuffer) {
            double yy = y - caveBandMax + caveBandBuffer;
            return (subtractForLessThanCutoff / (double) caveBandBuffer) * yy;
            // In bounds, no distortion
        } else {
            return 0;
        }
    }
	
}
