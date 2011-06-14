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
package org.gatein.mop.core.support;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.gatein.mop.core.api.Attribute;
import org.gatein.mop.core.api.AttributesImpl;
import org.gatein.mop.core.api.MOPService;
import org.gatein.mop.core.api.PathAttribute;
import org.gatein.mop.core.api.content.ContentManagerRegistry;
import org.gatein.mop.core.api.BooleanAttribute;
import org.gatein.mop.core.api.DateAttribute;
import org.gatein.mop.core.api.workspace.GroupSite;
import org.gatein.mop.core.api.workspace.GroupSiteContainer;
import org.gatein.mop.core.api.IntegerAttribute;
import org.gatein.mop.core.api.workspace.LinkImpl;
import org.gatein.mop.core.api.workspace.NavigationContainer;
import org.gatein.mop.core.api.workspace.NavigationImpl;
import org.gatein.mop.core.api.workspace.PageContainer;
import org.gatein.mop.core.api.workspace.PageImpl;
import org.gatein.mop.core.api.workspace.PageLinkImpl;
import org.gatein.mop.core.api.workspace.PortalSite;
import org.gatein.mop.core.api.workspace.PortalSiteContainer;
import org.gatein.mop.core.api.StringAttribute;
import org.gatein.mop.core.api.workspace.Secured;
import org.gatein.mop.core.api.workspace.SiteContainer;
import org.gatein.mop.core.api.workspace.SiteImpl;
import org.gatein.mop.core.api.workspace.TemplatizedImpl;
import org.gatein.mop.core.api.workspace.UIBodyImpl;
import org.gatein.mop.core.api.workspace.UIComponentImpl;
import org.gatein.mop.core.api.workspace.UIContainerImpl;
import org.gatein.mop.core.api.workspace.UIWindowImpl;
import org.gatein.mop.core.api.workspace.URLLinkImpl;
import org.gatein.mop.core.api.workspace.UserSite;
import org.gatein.mop.core.api.workspace.UserSiteContainer;
import org.gatein.mop.core.api.workspace.WorkspaceCustomizationContextImpl;
import org.gatein.mop.core.api.workspace.WorkspaceImpl;
import org.gatein.mop.core.api.workspace.WorkspaceObjectImpl;
import org.gatein.mop.core.api.workspace.content.AbstractCustomization;
import org.gatein.mop.core.api.workspace.content.AbstractCustomizationState;
import org.gatein.mop.core.api.workspace.content.ContextSpecialization;
import org.gatein.mop.core.api.workspace.content.ContextType;
import org.gatein.mop.core.api.workspace.content.ContextTypeContainer;
import org.gatein.mop.core.api.workspace.content.CustomizationContainer;
import org.gatein.mop.core.api.workspace.content.WorkspaceClone;
import org.gatein.mop.core.api.workspace.content.WorkspaceCustomization;
import org.gatein.mop.core.api.workspace.content.WorkspaceSpecialization;
import org.gatein.mop.core.support.content.gadget.Gadget;
import org.gatein.mop.core.support.content.gadget.GadgetContentProvider;
import org.gatein.mop.core.support.content.gadget.GadgetState;
import org.gatein.mop.core.support.content.portlet.PortletContentProvider;
import org.gatein.mop.core.support.content.portlet.PortletPreferenceState;
import org.gatein.mop.core.support.content.portlet.PortletPreferencesState;
import org.gatein.mop.core.support.content.portlet.Preferences;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestMOPService extends MOPService
{

   /** . */
   private final Chromattic chromattic;

   public TestMOPService() throws Exception
   {
      ChromatticBuilder builder = ChromatticBuilder.create();

      //
      builder.setOptionValue(ChromatticBuilder.INSTRUMENTOR_CLASSNAME, "org.chromattic.apt.InstrumentorImpl");

      //
      builder.add(WorkspaceObjectImpl.class);
      builder.add(WorkspaceImpl.class);
      builder.add(UIComponentImpl.class);
      builder.add(UIContainerImpl.class);
      builder.add(UIWindowImpl.class);
      builder.add(UIBodyImpl.class);
      builder.add(PageImpl.class);
      builder.add(PageContainer.class);
      builder.add(NavigationImpl.class);
      builder.add(NavigationContainer.class);
      builder.add(LinkImpl.class);
      builder.add(PageLinkImpl.class);
      builder.add(URLLinkImpl.class);
      builder.add(SiteImpl.class);
      builder.add(SiteContainer.class);
      builder.add(PortalSiteContainer.class);
      builder.add(PortalSite.class);
      builder.add(GroupSiteContainer.class);
      builder.add(GroupSite.class);
      builder.add(UserSiteContainer.class);
      builder.add(UserSite.class);
      builder.add(TemplatizedImpl.class);

      //
      builder.add(AttributesImpl.class);
      builder.add(Attribute.class);
      builder.add(PathAttribute.class);
      builder.add(StringAttribute.class);
      builder.add(BooleanAttribute.class);
      builder.add(IntegerAttribute.class);
      builder.add(DateAttribute.class);

      //
      builder.add(CustomizationContainer.class);
      builder.add(ContextTypeContainer.class);
      builder.add(ContextType.class);
      builder.add(AbstractCustomization.class);
      builder.add(AbstractCustomizationState.class);
      builder.add(ContextSpecialization.class);
      builder.add(WorkspaceCustomization.class);
      builder.add(WorkspaceClone.class);
      builder.add(WorkspaceSpecialization.class);
      builder.add(WorkspaceCustomizationContextImpl.class);

      //
      builder.add(PortletPreferencesState.class);
      builder.add(PortletPreferenceState.class);

      //
      builder.add(GadgetState.class);

      //
      builder.add(Secured.class);

      //
      this.chromattic = builder.build();
   }

   @Override
   protected Chromattic getChromattic()
   {
      return chromattic;
   }

   @Override
   protected void configure(ContentManagerRegistry registry)
   {
      registry.register(Preferences.CONTENT_TYPE, new PortletContentProvider());
      registry.register(Gadget.CONTENT_TYPE, new GadgetContentProvider());
   }
}
