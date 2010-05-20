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
package org.gatein.mop.api.content;

/**
 * <p>The customization context defines where customization applies, it can be an entity, an identity, or anything else
 * that is subject to customization.</p> <p/> <p>Contexts are partitionned by their type, i.e all the context that share
 * the same value returned by the invocation of the method {@link #getContextType()} are considered to belong to the
 * same type.</p> <p/> <p>Within a partition it is possible to find out the relationship between two contexts thanks to
 * the {@link #contains(CustomizationContext)} method. <ul> <li>Two contexts are considered equals when they contain
 * each other.</li> <li>Two contexts are not related when no context contains the other one.</li> <li>Otherwise one
 * contexts belongs to the other.</li> </ul> The <tt>contains</tt> relationship is transitive.</p>
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface CustomizationContext
{

   /**
    * Returns the identifier of the context.
    *
    * @return the context identifier
    */
   String getContextId();

   /**
    * Returns the customization context type.
    *
    * @return the customization context type
    */
   String getContextType();

   /**
    * Returns true if the context contains the provided context.
    *
    * @param that the context to test
    * @return a boolean indicated whether the provided context is contained by this context
    */
   boolean contains(CustomizationContext that);

}
