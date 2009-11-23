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
package org.gatein.mop.core.api.workspace;

import org.chromattic.api.annotations.FormattedBy;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Destroy;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.Page;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.content.ContentType;
import org.gatein.mop.core.api.MOPFormatter;
import org.gatein.mop.core.api.workspace.content.CustomizationContainer;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@FormattedBy(MOPFormatter.class)
public abstract class SiteImpl extends WorkspaceObjectImpl implements Site, WorkspaceCustomizationContext
{

   @OneToOne
   @MappedBy("rootpage")
   public abstract PageImpl getRoot();

   @OneToOne
   @MappedBy("rootnavigation")
   public abstract NavigationImpl getRootNavigation();

   @ManyToOne
   public abstract SiteContainer getSites();

   @Destroy
   public abstract void destroy();

   @OneToOne
   @MappedBy("customizations")
   public abstract CustomizationContainer getCustomizations();

   public abstract ObjectType<? extends Site> getObjectType();

   // Site implementation ***********************************************************************************************

   public WorkspaceImpl getWorkspace()
   {
      SiteContainer sites = getSites();
      return sites.getWorkspace();
   }

   public Page getRootPage()
   {
      return getRoot();
   }

   // WorkspaceCustomizationContext implementation **********************************************************************

   public String getContextType()
   {
      return WorkspaceCustomizationContext.TYPE;
   }

   public String getContextId()
   {
      return getObjectId();
   }

   public boolean contains(CustomizationContext that)
   {
      return contains(this, that);
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
