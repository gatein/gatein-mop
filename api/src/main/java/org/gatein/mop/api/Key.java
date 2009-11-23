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
package org.gatein.mop.api;

/**
 * A key is a typed name. This class is immutable and is therefore thread safe.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class Key<T>
{

   public static <T> Key<T> create(java.lang.String name, ValueType<T> type)
   {
      if (type == null)
      {
         throw new NullPointerException();
      }
      return new Key<T>(name, type);
   }

   /** The key name. */
   private final java.lang.String name;

   /** The type. */
   private final ValueType<T> type;

   /**
    * Creates a new key.
    *
    * @param name the key name
    * @param type the key type
    * @throws NullPointerException if the name is null
    */
   protected Key(java.lang.String name, ValueType<T> type) throws NullPointerException
   {
      if (name == null)
      {
         throw new NullPointerException();
      }
      if (type == null)
      {
         throw new NullPointerException();
      }
      this.name = name;
      this.type = type;
   }

   /**
    * Returns the key type.
    *
    * @return the type
    */
   public ValueType<T> getType()
   {
      return type;
   }

   /**
    * Returns the key name.
    *
    * @return the name
    */
   public final java.lang.String getName()
   {
      return name;
   }

   @Override
   public int hashCode()
   {
      return getType().hashCode() + name.hashCode();
   }

   @Override
   public boolean equals(java.lang.Object obj)
   {
      if (obj == this)
      {
         return true;
      }
      if (obj.getClass() == getClass())
      {
         Key that = (Key)obj;
         return name.equals(that.name);
      }
      return false;
   }

   @Override
   public java.lang.String toString()
   {
      return "Key[name=" + name + ",type=" + getType() + "]";
   }
}
