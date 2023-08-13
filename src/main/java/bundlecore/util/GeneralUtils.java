package bundlecore.util;

import bundlecore.cardmodifiers.chimeracardscrossover.Annotations.Dont_Use_This_Externally;

public class GeneralUtils {

    /**
     * Used to make the mods tab author list work
     */
    @Dont_Use_This_Externally
    public static String arrToString(Object[] arr) {
        if (arr == null)
            return null;
        if (arr.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; ++i) {
            sb.append(arr[i]).append(", ");
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }

}
