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

package org.gatein.mop.core.api.workspace;

import org.chromattic.api.RelationshipType;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.Owner;
import org.chromattic.api.annotations.PrimaryType;
import org.gatein.mop.api.content.ContentType;
import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;
import org.gatein.mop.core.api.workspace.content.CustomizationContainer;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "mop:customizationcontext")
public abstract class WorkspaceCustomizationContextImpl implements WorkspaceCustomizationContext {

   // THIS IS A BUG IT SHOULD NOT BE HERE
   @Owner
   @OneToOne(type = RelationshipType.EMBEDDED)
   public abstract WorkspaceObjectImpl getOwner();

   @OneToOne
   @MappedBy("mop:customizations")
   @Owner
   public abstract CustomizationContainer getCustomizations();

   public String getContextType()
   {
      return WorkspaceCustomizationContext.TYPE;
   }

   public String getContextId()
   {
      return getOwner().getObjectId();
   }

   public boolean contains(CustomizationContext that)
   {
      return WorkspaceObjectImpl.contains(this, that);
   }

   public Customization<?> getCustomization(String name)
   {
      return getCustomizations().getCustomization(name);
   }

   public <S> Customization<S> customize(String name, ContentType<S> contentType, String contentId, S state)
   {
      return getCustomizations().customize(name, contentType, contentId, state);
   }

   public <S> Customization<S> customize(String name, Customization<S> customization)
   {
      return getCustomizations().customize(name, customization);
   }

   public String nameOf(Customization customization)
   {
      return getCustomizations().nameOf(customization);
   }
}
