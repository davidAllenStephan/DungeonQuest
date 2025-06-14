package davidmarino;

/**
 * Class {@code PerlinNoise} generates a procedural texture primitive.
 * For more information about this algorithm read <A href="https://en.wikipedia.org/wiki/Perlin_noise#">https://en.wikipedia.org/wiki/Perlin_noise#</A>
 * @author David Marino
 * @version 14 Jun 2025
 */

public class PerlinNoise {

    private static double[] gradient(int x, int y) {
        int seed = x * 374761393 + y * 668265263; // large, distinct primes
        seed = (seed ^ (seed >> 13)) * 1274126177;
        seed ^= seed >> 16;
        double angle = (seed & 0xFFFF) / 65536.0 * 2 * Math.PI;
        return new double[]{Math.cos(angle), Math.sin(angle)};
    }

    private static double dot(double[] g, double[] d) {
        return g[0] * d[0] + g[1] * d[1];
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    public static double getNoise(double x, double y) {
        int x0 = (int) Math.floor(x); // left
        int x1 = x0 + 1;             // right
        int y0 = (int) Math.floor(y); // bottom
        int y1 = y0 + 1;             // top

//        System.out.println(x + ", " + y + ", " + x0 + ", " + y0 + ", " + x1 + ", " + y1);

        double[] g00 = gradient(x0, y0);
        double[] g10 = gradient(x1, y0);
        double[] g01 = gradient(x0, y1);
        double[] g11 = gradient(x1, y1);

        double dx = x - x0;
        double dy = y - y0;

        double[] d00 = {dx, dy};
        double[] d10 = {dx - 1, dy};
        double[] d01 = {dx, dy - 1};
        double[] d11 = {dx - 1, dy - 1};

        double dot00 = dot(g00, d00);
        double dot10 = dot(g10, d10);
        double dot01 = dot(g01, d01);
        double dot11 = dot(g11, d11);

        double u = fade(dx);
        double v = fade(dy);

        double ix0 = lerp(dot00, dot10, u);
        double ix1 = lerp(dot01, dot11, u);

        double value = lerp(ix0, ix1, v);
        double normalized = (value + 1) / 2.0;
        return normalized;
    }

}
