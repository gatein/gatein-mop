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

package org.gatein.mop.core.api.workspace.content;

import org.gatein.mop.spi.content.ContentProvider;
import org.gatein.mop.spi.content.StateContainer;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class StateContainerImpl<E, I> implements StateContainer<I> {

   /** . */
   private final AbstractCustomization customization;

   /** . */
   private final ContentProvider<E, I> provider;

   /** . */
   private final Class<I> stateType;

   public StateContainerImpl(ContentProvider<E, I> provider, AbstractCustomization customization) {
      this.provider = provider;
      this.stateType = provider.getInternalType();
      this.customization = customization;
   }

   public I getState() {
      return stateType.cast(customization.getCustomizationState());
   }

   public void setState(I state) {
      customization.setCustomizationState((AbstractCustomizationState)state);
   }

   public I create() {
      I i = customization.session.create(stateType);
      setState(i);
      return i;
   }
}
