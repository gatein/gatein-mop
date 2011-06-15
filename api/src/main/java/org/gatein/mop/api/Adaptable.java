/*
 * Copyright (C) 2010 eXo Platform SAS.
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
 * The adapter pattern.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Adaptable
{

   /**
    * Returns an adapter for the specified type.
    *
    * @param adapterType the adapter type class
    * @param <A> the adapter type
    * @return the adapter or null
    */
   <A> A adapt(Class<A> adapterType);

   /**
    * Returns true if the workspace object is adapted to the specified type.
    *
    * @param adapterType the adapter type
    * @return the adaptability of the current object
    */
   <A> boolean isAdapted(Class<A> adapterType);

   /**
    * Removes the adapter from this adaptable object.
    *
    * @param adapterType the adapter type class
    * @param <A> the adapter generic type
    */
   <A> void removeAdapter(Class<A> adapterType);

}
