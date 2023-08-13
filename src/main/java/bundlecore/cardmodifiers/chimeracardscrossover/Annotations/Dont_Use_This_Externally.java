package bundlecore.cardmodifiers.chimeracardscrossover.Annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

/**
 * A class with this annotation should never be used outside the source project.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface Dont_Use_This_Externally {}