package yubo.refobj;

import java.util.concurrent.TimeUnit;

/**
 * 1 mark
 * 2 sweep
 * 3, finalize
 * @author yubo
 *
 */
public class SlowFinalizer {

	public static void main(String[] argv) throws Exception
    {
        while (true)
        {
            Object foo = new SlowFinalizer();
        }
    }
	
	// some member variables to take up space -- approx 200 bytes
	double a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w,
			x, y, z;

	protected void finalize() throws Throwable {
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException ignore) {
		}

		super.finalize();
	}
}
