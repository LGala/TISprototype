/**
 * libraries.z.jsetl � A Java library that combines the object-oriented
 * programming paradigm with valuable concepts of CLP languages
 *
 * Copyright (C) 2000-2012 libraries.z.jsetl Team.
 *
 * libraries.z.jsetl is distributed under the terms of the GNU Lesser General
 * Public License.
 */

/**
 * CmpIntLVarDomByGlb.java
 * @version 2.3
 *
 */

package libraries.jsetl.comparator;
import libraries.jsetl.IntLVar;
import libraries.jsetl.Interval;

import java.util.Comparator;

/**
 * This class implements a comparator for <code>IntLVar</code> based on the greatest lower bound of their domains.
 * @author Pandan //TODO who????
 *
 **/
 
public class CmpIntLVarDomByGlb implements Comparator<IntLVar> {

    @Override
    public int 
    compare(IntLVar arg0, IntLVar arg1) {
        Interval i0 = arg0.getDomain().convexClosure();
        Interval i1 = arg1.getDomain().convexClosure();
        CmpIntervalsByGlb comp = new CmpIntervalsByGlb();
        return comp.compare(i0, i1);
    }

}
