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

import junit.framework.TestCase;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.core.api.content.CustomizationContextComparator;
import org.gatein.mop.core.api.content.ComparisonException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class CustomizationContextComparatorTestCase extends TestCase
{

   /** . */
   private final SimpleCustomizationContext s2 = new SimpleCustomizationContext("foo", 2);

   /** . */
   private final SimpleCustomizationContext s3 = new SimpleCustomizationContext("foo", 3);

   /** . */
   private final SimpleCustomizationContext s4 = new SimpleCustomizationContext("foo", 4);

   /** . */
   private final SimpleCustomizationContext b2 = new SimpleCustomizationContext("bar", 2);

   /** . */
   private final SimpleCustomizationContext b3 = new SimpleCustomizationContext("bar", 3);

   /** . */
   private final SimpleCustomizationContext b4 = new SimpleCustomizationContext("bar", 4);

   public void testKeepOrder()
   {
      CustomizationContextComparator comparator = new CustomizationContextComparator("foo");
      List<SimpleCustomizationContext> contexts = new ArrayList<SimpleCustomizationContext>();
      contexts.add(s2);
      contexts.add(s4);
      Collections.sort(contexts, comparator);
      List<SimpleCustomizationContext> blah = Arrays.asList(s2, s4);
      assertEquals(blah, contexts);
   }

   public void testSort()
   {
      CustomizationContextComparator comparator = new CustomizationContextComparator("foo");
      List<SimpleCustomizationContext> contexts = new ArrayList<SimpleCustomizationContext>();
      contexts.add(s4);
      contexts.add(s2);
      Collections.sort(contexts, comparator);
      List<SimpleCustomizationContext> blah = Arrays.asList(s2, s4);
      assertEquals(blah, contexts);
   }

   public void testFailWhenNotComparable()
   {
      CustomizationContextComparator comparator = new CustomizationContextComparator("foo");
      List<SimpleCustomizationContext> contexts = new ArrayList<SimpleCustomizationContext>();
      contexts.add(s2);
      contexts.add(s3);
      try
      {
         Collections.sort(contexts, comparator);
         fail();
      }
      catch (ComparisonException e)
      {
      }
   }

   public void testFailWhenNonExpectedType()
   {
      CustomizationContextComparator comparator = new CustomizationContextComparator("foo");
      List<SimpleCustomizationContext> contexts = new ArrayList<SimpleCustomizationContext>();
      contexts.add(s2);
      contexts.add(b4);
      try
      {
         Collections.sort(contexts, comparator);
         fail();
      }
      catch (ComparisonException e)
      {
      }
   }

   public void testSortByTypes()
   {
      CustomizationContextComparator comparator = new CustomizationContextComparator("foo", "bar");
      List<SimpleCustomizationContext> contexts = new ArrayList<SimpleCustomizationContext>();
      contexts.add(b4);
      contexts.add(s2);
      contexts.add(s4);
      contexts.add(b2);
      Collections.sort(contexts, comparator);
      List<?> blah = Arrays.asList(s2, s4, b2, b4);
      assertEquals(blah, contexts);
   }

   public void testRemoveDuplicates()
   {
      CustomizationContextComparator comparator = new CustomizationContextComparator("foo");
      SortedSet<SimpleCustomizationContext> contexts = new TreeSet<SimpleCustomizationContext>(comparator);
      contexts.add(s2);
      contexts.add(s2);
      assertEquals(1, contexts.size());
   }

   private static class SimpleCustomizationContext implements CustomizationContext
   {

      /** . */
      private final String type;

      /** . */
      private final int value;

      private SimpleCustomizationContext(String type, int value)
      {
         if (type == null)
         {
            throw new NullPointerException();
         }
         if (value < 1)
         {
            throw new IllegalArgumentException();
         }

         //
         this.type = type;
         this.value = value;
      }

      public String getContextId()
      {
         return type + "/" + value;
      }

      public String getContextType()
      {
         return type;
      }

      public boolean contains(CustomizationContext that)
      {
         if (that instanceof SimpleCustomizationContext)
         {
            SimpleCustomizationContext thatSimple = (SimpleCustomizationContext)that;
            if (thatSimple.type.equals(type))
            {
               return thatSimple.value % value == 0;
            }
         }
         return false;
      }
   }
}
