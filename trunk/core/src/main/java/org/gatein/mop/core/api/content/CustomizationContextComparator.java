/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.mop.core.api.content;

import org.gatein.mop.api.content.CustomizationContext;

import java.util.Comparator;
import java.util.List;
import java.util.Arrays;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class CustomizationContextComparator implements Comparator<CustomizationContext>
{

   /** . */
   private List<String> contextOrder;

   public CustomizationContextComparator(List<String> contextOrder)
   {
      this.contextOrder = contextOrder;
   }

   public CustomizationContextComparator(String... contextOrder)
   {
      this(Arrays.asList(contextOrder));
   }

   public int compare(CustomizationContext c1, CustomizationContext c2)
   {
      String t1 = c1.getContextType();
      int i1 = contextOrder.indexOf(t1);
      if (i1 == -1)
      {
         throw new ComparisonException("Context type " + t1 + " cannot be used for comparison");
      }

      //
      String t2 = c2.getContextType();
      int i2 = contextOrder.indexOf(t2);
      if (i2 == -1)
      {
         throw new ComparisonException("Context type " + t1 + " cannot be used for comparison");
      }

      //
      if (i1 < i2)
      {
         return -1;
      }
      else if (i1 > i2)
      {
         return 1;
      }

      //
      if (c1.contains(c2))
      {
         if (c2.contains(c1))
         {
            return 0;
         }
         else
         {
            return -1;
         }
      }
      else
      {
         if (c2.contains(c1))
         {
            return 1;
         }
         else
         {
            throw new ComparisonException("Cannot compare customization contexts " + c1 + " and " + c2);
         }
      }
   }
}
