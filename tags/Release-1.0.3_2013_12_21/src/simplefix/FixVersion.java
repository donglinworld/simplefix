/**
 * 
 */
package simplefix;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dong
 * 
 */
public enum FixVersion {

    FIX_40("FIX.4.0"),
    FIX_41("FIX.4.1"),
    FIX_42("FIX.4.2"),
    FIX_43("FIX.4.3"),
    FIX_44("FIX.4.4"),
    FIX_50("FIX.5.0"),
    INVALID("");

    static Map<String, FixVersion> _map = new HashMap<String, FixVersion>();
    static {
        for (FixVersion fixVersion : FixVersion.values()) {
            if (!INVALID.equals(fixVersion)) {
                _map.put(String.valueOf(fixVersion.getBeginString()), fixVersion);
            }
        }
    }

    private String _beginString;

    private FixVersion(final String fixValue) {
        _beginString = fixValue;
    }

    public String getBeginString() {
        return _beginString;
    }

    public static FixVersion fromBeginString(final String fixValue) {
        FixVersion fixVersion = _map.get(fixValue);
        return fixVersion == null ? INVALID : fixVersion;
    }

}
