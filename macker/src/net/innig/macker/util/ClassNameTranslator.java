/*______________________________________________________________________________
 *
 * Macker   http://innig.net/macker/
 *
 * Copyright 2002 Paul Cantrell
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License version 2.1, as published
 * by the Free Software Foundation. See the file LICENSE.html for more info.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, including the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the license for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330 / Boston, MA 02111-1307 / USA.
 *______________________________________________________________________________
 */
 
package net.innig.macker.util;

import java.util.*;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

public class ClassNameTranslator
    {
    static public String typeConstantToClassName(String typeName)
        {
        if(arrayExtractorRE.match(typeName))
            {
            if(arrayExtractorRE.getParen(2) != null)
                return (String) primitiveTypeMap.get(arrayExtractorRE.getParen(2));
            if(arrayExtractorRE.getParen(3) != null)
                return resourceToClassName(arrayExtractorRE.getParen(3));
            }
        return resourceToClassName(typeName);
        }
    
    static public String resourceToClassName(String className)
        { return slashRE.subst(classSuffixRE.subst(className, ""), ".").intern(); }
    
    static public String classToResourceName(String resourceName)
        { return (dotRE.subst(resourceName, "/") + ".class").intern(); }
    
    static private RE classSuffixRE, slashRE, dotRE, arrayExtractorRE;
    static private Map/*<String,String>*/ primitiveTypeMap;
    static
        {
        try {
            classSuffixRE = new RE("\\.class$");
            slashRE = new RE("/");
            dotRE = new RE("\\.");
            arrayExtractorRE = new RE("^(\\[+([BSIJCFDZ])|\\[+L(.*);)$");
            }
        catch(RESyntaxException rese)
            { throw new RuntimeException("Can't initialize ClassNameTranslator: " + rese); } 
        
        primitiveTypeMap = new HashMap();
        primitiveTypeMap.put("B", "byte");
        primitiveTypeMap.put("S", "short");
        primitiveTypeMap.put("I", "int");
        primitiveTypeMap.put("J", "long");
        primitiveTypeMap.put("C", "char");
        primitiveTypeMap.put("F", "float");
        primitiveTypeMap.put("D", "double");
        primitiveTypeMap.put("Z", "boolean");
        }
    }
