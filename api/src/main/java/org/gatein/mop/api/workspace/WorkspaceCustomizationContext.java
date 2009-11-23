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
package org.gatein.mop.api.workspace;

import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.content.ContentType;

/**
 * A workspace customization context define root customization contexts.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface WorkspaceCustomizationContext extends CustomizationContext
{

   /** The context type returned that must be returned by implementations of the {@link #getContextType()} method. */
   String TYPE = "workspace";

   /**
    * The root customization.
    *
    * @param name the customization name
    * @return the root customization
    */
   Customization<?> getCustomization(String name);

   /**
    * Configure a root customization for the specified content.
    *
    * @param name        the customization name
    * @param contentType the content type
    * @param contentId   the content id
    * @param state       the customization state
    * @param <S>         the content state typa parameter
    * @return the customization
    */
   <S> Customization<S> customize(String name, ContentType<S> contentType, String contentId, S state);

   /**
    * Configure a customization for extending the specified customization.
    *
    * @param name          the customization name
    * @param customization the customization to extend
    * @param <S>           the content state typa parameter
    * @return the customization
    */
   <S> Customization<S> customize(String name, Customization<S> customization);


   /**
    * Returns the customization name related to this context or null if the customization is not related to this
    * context.
    *
    * @param customization the customization related to this context
    * @return the customization name
    */
   String nameOf(Customization customization);

}
