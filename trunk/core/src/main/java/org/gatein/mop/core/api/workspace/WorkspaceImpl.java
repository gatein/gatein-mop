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

import org.chromattic.api.annotations.NodeMapping;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.FindById;
import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.WorkspaceObject;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.content.ContentType;
import org.gatein.mop.core.api.workspace.content.CustomizationContainer;

import java.util.Collection;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@NodeMapping(name = "mop:workspace")
public abstract class WorkspaceImpl extends WorkspaceObjectImpl implements Workspace, WorkspaceCustomizationContext {

  public ObjectType<? extends Workspace> getObjectType() {
    return ObjectType.WORKSPACE;
  }

  // Abstract **********************************************************************************************************

  @OneToOne
  @MappedBy("portalsites")
  public abstract PortalSiteContainer getPortalSites();

  @OneToOne
  @MappedBy("groupsites")
  public abstract GroupSiteContainer getGroupSites();

  @OneToOne
  @MappedBy("usersites")
  public abstract UserSiteContainer getUserSites();

  @OneToOne
  @MappedBy("sharedsites")
  public abstract UserSiteContainer getSharedSites();

  @OneToOne
  @MappedBy("customizations")
  public abstract CustomizationContainer getCustomizations();

  // CustomizationContextResolver implementation ***********************************************************************

  @FindById
  public abstract WorkspaceObject getObject(String objectId);

  @FindById
  public abstract CustomizationContext resolveContext(String contextId);

  // WorkspaceCustomizationContext implementation **********************************************************************

  public String getContextType() {
    return WorkspaceCustomizationContext.TYPE;
  }

  public String getContextId() {
    return getObjectId();
  }

  public boolean contains(CustomizationContext that) {
    return contains(this, that);
  }

  public Customization<?> getCustomization(String name) {
    return getCustomizations().getCustomization(name);
  }

  public <S> Customization<S> customize(String name, ContentType<S> contentType, String contentId, S state) {
    return getCustomizations().customize(name, contentType, contentId, state);
  }

  public <S> Customization<S> customize(String name, Customization<S> customization) {
    return getCustomizations().customize(name, customization);
  }

  // Workspace implementation ******************************************************************************************

  @SuppressWarnings("unchecked")
  private <S extends Site> SiteContainer<S> getSiteContainer(ObjectType<S> siteType) {
    if (siteType == ObjectType.PORTAL_SITE) {
      return (SiteContainer<S>)getPortalSites();
    } else if (siteType == ObjectType.GROUP_SITE) {
      return (SiteContainer<S>)getGroupSites();
    } else if (siteType == ObjectType.USER_SITE) {
      return (SiteContainer<S>)getUserSites();
    } else if (siteType == ObjectType.SHARED_SITE) {
      return (SiteContainer<S>)getSharedSites();
    } else {
      throw new UnsupportedOperationException();
    }
  }

  public <S extends Site> S getSite(ObjectType<S> siteType, String siteName) {
    SiteContainer<S> sites = getSiteContainer(siteType);
    return sites.getSite(siteName);
  }

  public Collection<Site> getSites() {
    throw new UnsupportedOperationException();
  }

  public <S extends Site> Collection<S> getSites(ObjectType<S> siteType) {
    SiteContainer<S> sites = getSiteContainer(siteType);
    return sites.getAllSites();
  }

  public <S extends Site> S addSite(ObjectType<S> siteType, String name) {
    SiteContainer<S> sites = getSiteContainer(siteType);
    return sites.addSite(name);
  }

  public <O extends WorkspaceObject> O getObject(ObjectType<O> type, String id) {
    WorkspaceObject object = getObject(id);
    if (object != null) {
      return type.cast(object);
    } else {
      return null;
    }
  }

  public Site getSharedSite() {
    return getSite(ObjectType.SHARED_SITE, "default");
  }
}
