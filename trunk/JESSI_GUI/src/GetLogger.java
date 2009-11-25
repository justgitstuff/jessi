
import java.util.Calendar;
import org.apache.log4j.*;

import org.apache.log4j.Logger;

public class GetLogger {

	private static Logger l;

	public static synchronized Logger getInstance(String... name) {
		if (l == null) {
			l = Logger.getLogger(name[0]);
			PropertyConfigurator.configure(name[1]);
		}
		return l;
	}
}