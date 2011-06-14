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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
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

   /**
    * A simplistic implementation, it may not handle all cases but it should handle enough.
    *
    * @param implementation the type for which the parameter requires a resolution
    * @param type the type that owns the parameter
    * @param parameterIndex the parameter index
    * @return the resolved type
    */
   public static Type resolve(Type implementation, Class<?> type, int parameterIndex) {
     if (implementation == null) {
       throw new NullPointerException();
     }

     //
     if (implementation == type) {
       TypeVariable<? extends Class<?>>[] tp = type.getTypeParameters();
       if (parameterIndex < tp.length) {
         return tp[parameterIndex];
       } else {
         throw new IllegalArgumentException();
       }
     } else if (implementation instanceof Class<?>) {
       Class<?> c = (Class<?>) implementation;
       Type gsc = c.getGenericSuperclass();
       Type resolved = null;
       if (gsc != null) {
         resolved = resolve(gsc, type, parameterIndex);
         if (resolved == null) {
           // Try with interface
         }
       }
       return resolved;
     } else if (implementation instanceof ParameterizedType) {
       ParameterizedType pt = (ParameterizedType) implementation;
       Type[] typeArgs = pt.getActualTypeArguments();
       Type rawType = pt.getRawType();
       if (rawType == type) {
         return typeArgs[parameterIndex];
       } else if (rawType instanceof Class<?>) {
         Class<?> classRawType = (Class<?>)rawType;
         Type resolved = resolve(classRawType, type, parameterIndex);
         if (resolved == null) {
           return null;
         } else if (resolved instanceof TypeVariable) {
           TypeVariable resolvedTV = (TypeVariable)resolved;
           TypeVariable[] a = classRawType.getTypeParameters();
           for (int i = 0;i < a.length;i++) {
             if (a[i].equals(resolvedTV)) {
               return resolve(implementation, classRawType, i);
             }
           }
           throw new AssertionError();
         } else {
           throw new UnsupportedOperationException("Cannot support resolution of " + resolved);
         }
       } else {
         throw new UnsupportedOperationException();
       }
     } else {
       throw new UnsupportedOperationException("todo " + implementation + " " + implementation.getClass());
     }
   }
}
