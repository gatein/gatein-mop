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
package org.gatein.mop.core.util;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class Tools
{

   public static <N> List<N> list(Iterator<N> iterable)
   {
      ArrayList<N> set = new ArrayList<N>();
      while (iterable.hasNext())
      {
         set.add(iterable.next());
      }
      return set;
   }

   public static <N> Set<N> set(Iterable<N> iterable)
   {
      HashSet<N> set = new HashSet<N>();
      for (N n : iterable)
      {
         set.add(n);
      }
      return set;
   }

   public static <N> Set<N> set(Iterator<N> iterator)
   {
      HashSet<N> set = new HashSet<N>();
      while (iterator.hasNext())
      {
         set.add(iterator.next());
      }
      return set;
   }

   public static <N> Set<N> set()
   {
      return new HashSet<N>();
   }

   public static <N> Set<N> set(N object)
   {
      HashSet<N> set = new HashSet<N>();
      set.add(object);
      return set;
   }

   public static <N> Set<N> set(N... objects) throws NullPointerException
   {
      if (objects == null)
      {
         throw new NullPointerException();
      }
      HashSet<N> set = new HashSet<N>();
      for (N object : objects)
      {
         set.add(object);
      }
      return set;
   }

   public static int max(int value, int... values)
   {
      int max = value;
      for (int v : values)
      {
         if (v > max)
         {
            max = v;
         }
      }
      return max;
   }

   public static <E> Iterator<E> iterator(final Iterator<? extends E>... iterators)
   {
      return new Iterator<E>()
      {

         int index = 0;

         public boolean hasNext()
         {
            while (index < iterators.length)
            {
               if (iterators[index].hasNext())
               {
                  return true;
               }
               index++;
            }
            return false;
         }

         public E next()
         {
            while (index < iterators.length)
            {
               if (iterators[index].hasNext())
               {
                  return iterators[index].next();
               }
               index++;
            }
            throw new NoSuchElementException();
         }

         public void remove()
         {
            throw new UnsupportedOperationException();
         }
      };
   }
}
