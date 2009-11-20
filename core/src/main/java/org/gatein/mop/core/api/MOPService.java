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
package org.gatein.mop.core.api;

import org.chromattic.api.ChromatticBuilder;
import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticSession;
import org.gatein.mop.core.api.workspace.WorkspaceImpl;
import org.gatein.mop.core.api.workspace.UIContainerImpl;
import org.gatein.mop.core.api.workspace.UIWindowImpl;
import org.gatein.mop.core.api.workspace.UIBodyImpl;
import org.gatein.mop.core.api.workspace.PageImpl;
import org.gatein.mop.core.api.workspace.NavigationImpl;
import org.gatein.mop.core.api.workspace.PageLinkImpl;
import org.gatein.mop.core.api.workspace.URLLinkImpl;
import org.gatein.mop.core.api.workspace.PortalSiteContainer;
import org.gatein.mop.core.api.workspace.PortalSite;
import org.gatein.mop.core.api.workspace.GroupSiteContainer;
import org.gatein.mop.core.api.workspace.GroupSite;
import org.gatein.mop.core.api.workspace.PageContainer;
import org.gatein.mop.core.api.workspace.NavigationContainer;
import org.gatein.mop.core.api.workspace.UserSiteContainer;
import org.gatein.mop.core.api.workspace.UserSite;
import org.gatein.mop.core.api.workspace.content.CustomizationContainer;
import org.gatein.mop.core.api.workspace.content.ContextType;
import org.gatein.mop.core.api.workspace.content.ContextTypeContainer;
import org.gatein.mop.core.api.workspace.content.ContextSpecialization;
import org.gatein.mop.core.api.workspace.content.WorkspaceClone;
import org.gatein.mop.core.api.workspace.content.WorkspaceSpecialization;
import org.gatein.mop.core.api.content.ContentManagerRegistry;
import org.gatein.mop.core.api.content.CustomizationContextProviderRegistry;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class MOPService
{

   /** . */
   private Chromattic chrome;

   /** . */
   private ContentManagerRegistry contentManagerRegistry;

   /** . */
   private CustomizationContextProviderRegistry customizationContextResolvers;

   /** . */
   private final ChromatticBuilder builder;

   public MOPService()
   {
      ChromatticBuilder builder = ChromatticBuilder.create();
      builder.setOption(ChromatticBuilder.INSTRUMENTOR_CLASSNAME, "org.chromattic.apt.InstrumentorImpl");
      builder.setOption(ChromatticBuilder.OBJECT_FORMATTER_CLASSNAME, MOPFormatter.class.getName());

      //
      this.builder = builder;
   }

   public CustomizationContextProviderRegistry getCustomizationContextResolvers()
   {
      return customizationContextResolvers;
   }

   public ContentManagerRegistry getContentManagerRegistry()
   {
      return contentManagerRegistry;
   }

   protected void configure(CustomizationContextProviderRegistry registry)
   {
      //
   }

   protected void configure(ChromatticBuilder builder)
   {
      //
   }

   protected void configure(ContentManagerRegistry registry)
   {
      //
   }

   public void start() throws Exception
   {
      builder.add(WorkspaceImpl.class);
      builder.add(UIContainerImpl.class);
      builder.add(UIWindowImpl.class);
      builder.add(UIBodyImpl.class);
      builder.add(PageImpl.class);
      builder.add(PageContainer.class);
      builder.add(NavigationImpl.class);
      builder.add(NavigationContainer.class);
      builder.add(PageLinkImpl.class);
      builder.add(URLLinkImpl.class);
      builder.add(PortalSiteContainer.class);
      builder.add(PortalSite.class);
      builder.add(GroupSiteContainer.class);
      builder.add(GroupSite.class);
      builder.add(UserSiteContainer.class);
      builder.add(UserSite.class);

      //
      builder.add(CustomizationContainer.class);
      builder.add(ContextTypeContainer.class);
      builder.add(ContextType.class);
      builder.add(ContextSpecialization.class);
      builder.add(WorkspaceClone.class);
      builder.add(WorkspaceSpecialization.class);

      //
      configure(builder);

      //
      chrome = builder.build();

      //
      CustomizationContextProviderRegistry customizationContextResolvers = new CustomizationContextProviderRegistry();

      //
      configure(customizationContextResolvers);

      //
      ContentManagerRegistry cmr = new ContentManagerRegistry();

      //
      configure(cmr);

      //
      this.chrome = builder.build();
      this.contentManagerRegistry = cmr;
      this.customizationContextResolvers = customizationContextResolvers;
   }

   public ModelImpl getModel()
   {
      ChromatticSession chromeSession = chrome.openSession();
      return new ModelImpl(
         chromeSession,
         contentManagerRegistry,
         customizationContextResolvers);
   }
}
